package com.buensabor.restaurantapp.service;

import com.buensabor.restaurantapp.entity.Pago;
import java.util.List;

public interface PagoService {
    List<Pago> listarTodos();
    Pago buscarPorId(Integer id);
    Pago registrar(Pago pago);
    Pago actualizar(Integer id, Pago pago);
    void eliminar(Integer id);
    Pago buscarPorPedido(Integer pedidoId);
}
