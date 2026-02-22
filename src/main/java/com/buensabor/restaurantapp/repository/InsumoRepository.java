package com.buensabor.restaurantapp.repository;

import com.buensabor.restaurantapp.model.Insumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InsumoRepository extends JpaRepository<Insumo, Long> {
    boolean existsByNombreIgnoreCase(String nombre);
    Optional<Insumo> findByNombreIgnoreCase(String nombre);
}