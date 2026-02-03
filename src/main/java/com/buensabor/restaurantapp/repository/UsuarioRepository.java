package com.buensabor.restaurantapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buensabor.restaurantapp.entity.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByCorreo(String correo);
    boolean existsByCorreo(String correo);

}