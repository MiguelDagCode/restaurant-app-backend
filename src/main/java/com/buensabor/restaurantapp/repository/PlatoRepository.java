package com.buensabor.restaurantapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.buensabor.restaurantapp.entity.Plato;
import java.util.List;

@Repository
public interface PlatoRepository extends JpaRepository<Plato, Integer> {
    List<Plato> findByCategoriaId(Integer categoriaId);
}
