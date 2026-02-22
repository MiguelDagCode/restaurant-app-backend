package com.buensabor.restaurantapp.service;

import com.buensabor.restaurantapp.dto.request.PedidoRequest;
import com.buensabor.restaurantapp.model.Pedido;

public interface PedidoService {

	public Pedido crearPedido(PedidoRequest request);
}
