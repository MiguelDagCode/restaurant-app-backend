package com.buensabor.restaurantapp.service;

import com.buensabor.restaurantapp.dto.PedidoDTO;
import com.buensabor.restaurantapp.entity.Pedido;
import com.buensabor.restaurantapp.enums.EstadoPedido;
import java.util.List;

public interface PedidoService {
    List<Pedido> listarTodos();
    Pedido buscarPorId(Integer id);
    Pedido registrar(Pedido pedido);
    Pedido actualizar(Integer id, Pedido pedido);
    void eliminar(Integer id);
    Pedido cambiarEstado(Integer id, EstadoPedido estado);
    List<Pedido> listarPorEstado(EstadoPedido estado);
    
    List<PedidoDTO> listarPedidos();
    PedidoDTO obtenerPedido(Integer id);
    PedidoDTO crearPedido(PedidoDTO pedidoDTO);
    void eliminarPedido(Integer id);
}
