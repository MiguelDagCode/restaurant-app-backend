package com.buensabor.restaurantapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buensabor.restaurantapp.model.DetallePedido;
import com.buensabor.restaurantapp.model.DetallePedidoId;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, DetallePedidoId>{
	List<DetallePedido> findByPedido_IdPedido(Long idPedido);
}
