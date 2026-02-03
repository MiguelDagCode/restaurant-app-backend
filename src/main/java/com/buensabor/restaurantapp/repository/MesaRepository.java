package com.buensabor.restaurantapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.buensabor.restaurantapp.entity.Mesa;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Integer> { }
