package com.buensabor.restaurantapp.repository;

import com.buensabor.restaurantapp.model.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Integer> {
    boolean existsByNumeroMesa(String numeroMesa);
    Optional<Mesa> findByNumeroMesa(String numeroMesa);
}