package com.buensabor.restaurantapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buensabor.restaurantapp.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}
