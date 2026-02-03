package com.buensabor.restaurantapp.service;

import java.util.List;

import com.buensabor.restaurantapp.entity.Usuario;

public interface UsuarioService {
	public Usuario buscarByUsuario(String correo);
    Usuario registrar(Usuario usuario);
    List<Usuario> listarUsuarios();
    Usuario buscarPorId(Integer id);
    void eliminar(Integer id);

}
