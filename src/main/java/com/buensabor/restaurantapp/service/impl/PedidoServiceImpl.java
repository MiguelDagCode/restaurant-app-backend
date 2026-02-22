package com.buensabor.restaurantapp.service.impl;

import java.math.BigDecimal;
import java.net.Authenticator.RequestorType;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buensabor.restaurantapp.dto.request.DetallePedidoRequest;
import com.buensabor.restaurantapp.dto.request.PedidoRequest;
import com.buensabor.restaurantapp.enums.EstadoMesa;
import com.buensabor.restaurantapp.enums.EstadoPedido;
import com.buensabor.restaurantapp.enums.TipoDocumento;
import com.buensabor.restaurantapp.model.DetallePedido;
import com.buensabor.restaurantapp.model.DetallePedidoId;
import com.buensabor.restaurantapp.model.Insumo;
import com.buensabor.restaurantapp.model.Mesa;
import com.buensabor.restaurantapp.model.Pedido;
import com.buensabor.restaurantapp.model.Producto;
import com.buensabor.restaurantapp.model.Receta;
import com.buensabor.restaurantapp.model.Usuario;
import com.buensabor.restaurantapp.repository.DetallePedidoRepository;
import com.buensabor.restaurantapp.repository.InsumoRepository;
import com.buensabor.restaurantapp.repository.MesaRepository;
import com.buensabor.restaurantapp.repository.PedidoRepository;
import com.buensabor.restaurantapp.repository.ProductoRepository;
import com.buensabor.restaurantapp.repository.RecetaRepository;
import com.buensabor.restaurantapp.repository.UsuarioRepository;
import com.buensabor.restaurantapp.service.PedidoService;
import com.buensabor.restaurantapp.util.BusinessException;
import com.buensabor.restaurantapp.util.ModeloNotFoundException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PedidoServiceImpl implements PedidoService{

	private final PedidoRepository pedidoRepository;
    private final DetallePedidoRepository detallePedidoRepository;
    private final MesaRepository mesaRepository;
    private final ProductoRepository productoRepository;
    private final RecetaRepository recetaRepository;
    private final InsumoRepository insumoRepository;
	
	@Override
	@Transactional
	public Pedido crearPedido(PedidoRequest request) {
		//1. Validar Mesa
		Mesa mesa = mesaRepository.findById(request.getIdMesa())
				.orElseThrow(()-> new ModeloNotFoundException("Mesa seleccionada ya no esta libre"));
		
		//2. Obtener Mozo usando JWT
		Usuario mozoLogueado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		mesa.setEstado(EstadoMesa.OCUPADA);
	
		//3. Crear Pedido
		Pedido pedido = new Pedido();
		pedido.setMesa(mesa);
		pedido.setUsuario(mozoLogueado);
		pedido.setNombresRazonSocial(request.getNombresCliente());
		pedido.setTipoDoc(request.getTipoDoc() != null ? request.getTipoDoc() : TipoDocumento.DNI);
		pedido.setNumDoc(request.getNumDoc());
		pedido.setTelefonoCliente(request.getTelefonoCliente());
		pedido.setEstadoPedido(EstadoPedido.PENDIENTE);
		
		BigDecimal totalPedido = BigDecimal.ZERO;
		
		pedido = pedidoRepository.saveAndFlush(pedido);
		
		for (DetallePedidoRequest detReq : request.getDetalles()) {
            
            Producto producto = productoRepository.findById(detReq.getIdProducto())
                    .orElseThrow(() -> new ModeloNotFoundException("Producto no encontrado: " + detReq.getIdProducto()));

            // Guardar Detalle 
            
            DetallePedido detalle = new DetallePedido();
            //Detalee
            detalle.setId(new DetallePedidoId());
            detalle.setPedido(pedido);
            detalle.setProducto(producto);
            detalle.setCantidad(detReq.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio()); 
            detalle.setObservaciones(detReq.getObservaciones());
            detallePedidoRepository.save(detalle);

            // Sumar al total general del pedido
            BigDecimal subtotal = producto.getPrecio().multiply(new BigDecimal(detReq.getCantidad()));
            totalPedido = totalPedido.add(subtotal);

            //Descontar Inventario leyendo la Receta
            List<Receta> recetaPlato = recetaRepository.findByProducto_IdProducto(producto.getIdProducto());
            
            for (Receta r : recetaPlato) {
                Insumo insumo = r.getInsumo();
                
                BigDecimal cantidadADescontar = r.getCantidadInsumo().multiply(new BigDecimal(detReq.getCantidad()));
                BigDecimal nuevoStock = insumo.getStockActual().subtract(cantidadADescontar);

                // Validación 
                if (nuevoStock.compareTo(BigDecimal.ZERO) < 0) {
                    throw new BusinessException("Stock insuficiente de '" + insumo.getNombre() + 
                            "' para preparar " + detReq.getCantidad() + "x '" + producto.getNombre() + "'.");
                }

                insumo.setStockActual(nuevoStock);
                insumoRepository.save(insumo); 
            }
        }
        pedido.setTotal(totalPedido);
        return pedidoRepository.save(pedido);
	}

}
