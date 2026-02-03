package com.buensabor.restaurantapp.repository;

import com.buensabor.restaurantapp.dto.PlatoMasVendidoDTO;
import com.buensabor.restaurantapp.entity.DetallePedido;
import com.buensabor.restaurantapp.dto.CategoriaMasVendidaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<DetallePedido, Integer> {

    // 🔹 Reporte 1: Categorías más vendidas
	@Query("""
		    SELECT new com.buensabor.restaurantapp.dto.CategoriaMasVendidaDTO(
		        dp.plato.categoria.id,
		        dp.plato.categoria.nombre,
		        SUM(dp.cantidad)
		    )
		    FROM DetallePedido dp
		    GROUP BY dp.plato.categoria.id, dp.plato.categoria.nombre
		    ORDER BY SUM(dp.cantidad) DESC
		    """)
		List<CategoriaMasVendidaDTO> obtenerCategoriasMasVendidas();

    // 🔹 Reporte 2: Platos más vendidos por categoría
    @Query("""
        SELECT new com.buensabor.restaurantapp.dto.PlatoMasVendidoDTO(
            dp.plato.nombre,
            SUM(dp.cantidad)
        )
        FROM DetallePedido dp
        WHERE dp.plato.categoria.id = :idCategoria
        GROUP BY dp.plato.nombre
        ORDER BY SUM(dp.cantidad) DESC
        """)
    List<PlatoMasVendidoDTO> obtenerPlatosMasVendidosPorCategoria(Integer idCategoria);
}
