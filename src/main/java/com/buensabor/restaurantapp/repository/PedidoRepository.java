package com.buensabor.restaurantapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.buensabor.restaurantapp.entity.Pedido;
import com.buensabor.restaurantapp.enums.EstadoPedido;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByEstado(EstadoPedido estado);
    List<Pedido> findByMesaId(Integer mesaId);
    List<Pedido> findByMozoIdUsuario(Integer idUsuario);
    // 🔹 Obtener todos los pedidos con sus detalles (mozo, mesa, platos)
    @Query("""
        SELECT DISTINCT p FROM Pedido p
        JOIN FETCH p.mozo m
        JOIN FETCH p.mesa me
        LEFT JOIN FETCH p.detalles d
        LEFT JOIN FETCH d.plato pl
    """)
    List<Pedido> findAllWithDetalles();

    // 🔹 Buscar un pedido específico con sus detalles
    @Query("""
        SELECT p FROM Pedido p
        JOIN FETCH p.mozo m
        JOIN FETCH p.mesa me
        LEFT JOIN FETCH p.detalles d
        LEFT JOIN FETCH d.plato pl
        WHERE p.id = :id
    """)
    Pedido findByIdWithDetalles(@Param("id") Integer id); // ✅ Param annotation requerida   
}
