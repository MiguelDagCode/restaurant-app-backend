package com.buensabor.restaurantapp.service.impl;

import com.buensabor.restaurantapp.entity.Pago;
import com.buensabor.restaurantapp.repository.PagoRepository;
import com.buensabor.restaurantapp.service.PagoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class PagoServiceImpl implements PagoService {

    private final PagoRepository repository;

    public PagoServiceImpl(PagoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Pago> listarTodos() {
        return repository.findAll();
    }

    @Override
    public Pago buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Pago no encontrado"));
    }

    @Override
    public Pago registrar(Pago pago) {
        // calcula monto si quieres verificar que coincida con pedido
        pago.setPedido(pago.getPedido());
        return repository.save(pago);
    }

    @Override
    public Pago actualizar(Integer id, Pago pago) {
        Pago existente = buscarPorId(id);
        existente.setNombreCliente(pago.getNombreCliente());
        existente.setDniRuc(pago.getDniRuc());
        existente.setTipoDocumento(pago.getTipoDocumento());
        existente.setNumeroDocumento(pago.getNumeroDocumento());
        existente.setMontoTotal(pago.getMontoTotal());
        return repository.save(existente);
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Pago buscarPorPedido(Integer pedidoId) {
        return repository.findByPedidoId(pedidoId).orElse(null);
    }
}
