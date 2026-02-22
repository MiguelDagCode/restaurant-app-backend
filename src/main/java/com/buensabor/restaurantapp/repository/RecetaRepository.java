package com.buensabor.restaurantapp.repository;

import com.buensabor.restaurantapp.model.Receta;
import com.buensabor.restaurantapp.model.RecetaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, RecetaId> {
    // Busca todos los ingredientes (receta) de un producto en particular
    List<Receta> findByProducto_IdProducto(Long idProducto);
}
