package com.buensabor.restaurantapp.service.impl;

import com.buensabor.restaurantapp.dto.DetallePedidoDTO;
import com.buensabor.restaurantapp.dto.PedidoDTO;
import com.buensabor.restaurantapp.entity.DetallePedido;
import com.buensabor.restaurantapp.entity.Pedido;
import com.buensabor.restaurantapp.enums.EstadoPedido;
import com.buensabor.restaurantapp.repository.MesaRepository;
import com.buensabor.restaurantapp.repository.PedidoRepository;
import com.buensabor.restaurantapp.repository.PlatoRepository;
import com.buensabor.restaurantapp.repository.UsuarioRepository;
import com.buensabor.restaurantapp.service.PedidoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final MesaRepository mesaRepository;
    private final PlatoRepository platoRepository;

    // ✅ Constructor con todas las dependencias necesarias
    public PedidoServiceImpl(
            PedidoRepository pedidoRepository,
            UsuarioRepository usuarioRepository,
            MesaRepository mesaRepository,
            PlatoRepository platoRepository
    ) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.mesaRepository = mesaRepository;
        this.platoRepository = platoRepository;
    }

    @Override
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    @Override
    public Pedido buscarPorId(Integer id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }

    @Override
    public Pedido registrar(Pedido pedido) {
        pedido.setEstado(EstadoPedido.PENDIENTE);
        if (pedido.getDetalles() != null) {
            for (DetallePedido d : pedido.getDetalles()) {
                d.setPedido(pedido);
            }
        }
        if (pedido.getPago() != null) {
            pedido.getPago().setPedido(pedido);
        }
        return pedidoRepository.save(pedido);
    }

    @Override
    public Pedido actualizar(Integer id, Pedido pedido) {
        Pedido existente = buscarPorId(id);
        existente.setMesa(pedido.getMesa());
        existente.setMozo(pedido.getMozo());
        existente.setEstado(pedido.getEstado());
        existente.getDetalles().clear();

        if (pedido.getDetalles() != null) {
            for (DetallePedido d : pedido.getDetalles()) {
                d.setPedido(existente);
                existente.getDetalles().add(d);
            }
        }

        return pedidoRepository.save(existente);
    }

    @Override
    public void eliminar(Integer id) {
        pedidoRepository.deleteById(id);
    }

    @Override
    public Pedido cambiarEstado(Integer id, EstadoPedido estado) {
        Pedido p = buscarPorId(id);
        p.setEstado(estado);
        return pedidoRepository.save(p);
    }

    @Override
    public List<Pedido> listarPorEstado(EstadoPedido estado) {
        return pedidoRepository.findByEstado(estado);
    }

    @Override
    public List<PedidoDTO> listarPedidos() {
        return pedidoRepository.findAllWithDetalles().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PedidoDTO obtenerPedido(Integer id) {
        Pedido pedido = pedidoRepository.findByIdWithDetalles(id);
        return convertToDTO(pedido);
    }

    @Override
    public PedidoDTO crearPedido(PedidoDTO pedidoDTO) {
        Pedido pedido = new Pedido();
        pedido.setMozo(usuarioRepository.findById(pedidoDTO.getMozoId()).orElseThrow());
        pedido.setMesa(mesaRepository.findById(pedidoDTO.getMesaId()).orElseThrow());
        pedido.setEstado(pedidoDTO.getEstado());

        List<DetallePedido> detalles = pedidoDTO.getDetalles().stream().map(detDTO -> {
            DetallePedido det = new DetallePedido();
            det.setPedido(pedido);
            det.setPlato(platoRepository.findById(detDTO.getPlatoId()).orElseThrow());
            det.setCantidad(detDTO.getCantidad());
            det.setEstado(detDTO.getEstado());
            return det;
        }).collect(Collectors.toList());

        pedido.setDetalles(detalles);
        Pedido saved = pedidoRepository.save(pedido);
        return convertToDTO(saved);
    }

    @Override
    public void eliminarPedido(Integer id) {
        pedidoRepository.deleteById(id);
    }

    private PedidoDTO convertToDTO(Pedido pedido) {
        return new PedidoDTO(
                pedido.getId(),
                pedido.getMozo().getIdUsuario(),
                pedido.getMesa().getId(),
                pedido.getFecha(),
                pedido.getEstado(),
                pedido.getDetalles().stream()
                        .map(d -> new DetallePedidoDTO(
                                d.getId(),
                                d.getPlato().getId(),
                                d.getCantidad(),
                                d.getEstado()
                        )).collect(Collectors.toList())
        );
    }
}
