package com.buensabor.restaurantapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buensabor.restaurantapp.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
