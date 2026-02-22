package com.buensabor.restaurantapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buensabor.restaurantapp.model.DetallePedido;
import com.buensabor.restaurantapp.model.DetallePedidoId;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, DetallePedidoId>{

}
