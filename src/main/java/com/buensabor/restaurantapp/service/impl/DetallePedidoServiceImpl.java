package com.buensabor.restaurantapp.service.impl;

import com.buensabor.restaurantapp.entity.DetallePedido;
import com.buensabor.restaurantapp.repository.DetallePedidoRepository;
import com.buensabor.restaurantapp.service.DetallePedidoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class DetallePedidoServiceImpl implements DetallePedidoService {

    private final DetallePedidoRepository repository;

    public DetallePedidoServiceImpl(DetallePedidoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<DetallePedido> listarTodos() {
        return repository.findAll();
    }

    @Override
    public DetallePedido buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Detalle no encontrado"));
    }

    @Override
    public List<DetallePedido> listarPorPedido(Integer pedidoId) {
        return repository.findByPedidoId(pedidoId);
    }

    @Override
    public DetallePedido guardar(DetallePedido detalle) {
        // asegúrate que detalle.getPedido() y detalle.getPlato() estén seteados
        return repository.save(detalle);
    }

    @Override
    public DetallePedido actualizar(Integer id, DetallePedido detalle) {
        DetallePedido existente = buscarPorId(id);
        existente.setCantidad(detalle.getCantidad());
        existente.setEstado(detalle.getEstado());
        return repository.save(existente);
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}
