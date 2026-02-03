package com.buensabor.restaurantapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buensabor.restaurantapp.entity.CategoriaPlato;

public interface CategoriaRepository extends JpaRepository<CategoriaPlato, Integer> {
    Optional<CategoriaPlato> findByNombreIgnoreCase(String nombre);
    boolean existsByNombreIgnoreCase(String nombre);
}