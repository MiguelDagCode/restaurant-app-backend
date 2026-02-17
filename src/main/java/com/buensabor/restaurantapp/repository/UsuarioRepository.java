package com.buensabor.restaurantapp.repository;

import com.buensabor.restaurantapp.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    // Usamos 'findByCorreo' porque en tu UserDetails definimos que el 'username' es el correo
    Optional<Usuario> findByCorreo(String correo);
}