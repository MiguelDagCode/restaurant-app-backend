package com.buensabor.restaurantapp.service;

import java.util.Optional;

import com.buensabor.restaurantapp.model.Usuario;

public interface UsuarioService {
    Usuario registrar(Usuario usuario);
    Optional<Usuario> buscarPorCorreo(String correo);
}