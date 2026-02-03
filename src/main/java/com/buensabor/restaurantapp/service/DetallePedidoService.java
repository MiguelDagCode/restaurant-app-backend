package com.buensabor.restaurantapp.service;

import com.buensabor.restaurantapp.entity.DetallePedido;
import java.util.List;

public interface DetallePedidoService {
    List<DetallePedido> listarTodos();
    DetallePedido buscarPorId(Integer id);
    List<DetallePedido> listarPorPedido(Integer pedidoId);
    DetallePedido guardar(DetallePedido detalle);
    DetallePedido actualizar(Integer id, DetallePedido detalle);
    void eliminar(Integer id);
}
