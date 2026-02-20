package com.buensabor.restaurantapp.repository;

import com.buensabor.restaurantapp.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByEstadoTrue(); // Solo productos activos
    List<Producto> findByCategoriaIdCategoriaAndEstadoTrue(Integer idCategoria);
    List<Producto> findByCategoriaIdCategoria(Integer idCategoria);

}
