package com.buensabor.restaurantapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.buensabor.restaurantapp.entity.RolUsuario;

public interface RolUsuarioRepository extends JpaRepository<RolUsuario, Integer> {
    Optional<RolUsuario> findByNombreIgnoreCase(String nombre);
    boolean existsByNombreIgnoreCase(String nombre);

}