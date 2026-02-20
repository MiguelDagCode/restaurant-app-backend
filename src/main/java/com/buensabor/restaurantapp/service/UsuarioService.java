package com.buensabor.restaurantapp.service;

import com.buensabor.restaurantapp.dto.request.UsuarioCreateDto;
import com.buensabor.restaurantapp.dto.request.UsuarioUpdateDto;
import com.buensabor.restaurantapp.dto.response.UsuarioListResponseDto;
import com.buensabor.restaurantapp.dto.response.UsuarioResponseDto;
import com.buensabor.restaurantapp.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    
    // --- LECTURA ---
    List<Usuario> listarTodos();
    Optional<Usuario> buscarPorId(Integer id);
    Optional<Usuario> buscarPorCorreo(String correo); // Método útil para seguridad interna

    // --- ESCRITURA ---
    Usuario registrar(Usuario usuario);
    Usuario actualizar(Integer id, Usuario usuario);
    void cambiarEstado(Integer id); // Eliminación lógica (Toggle)
    
    // --- DTOS ---
     List<UsuarioListResponseDto> listarTodosDto();
     Optional<UsuarioResponseDto> buscarPorIdDto(Integer id);
     UsuarioResponseDto registrarDto (UsuarioCreateDto dto);
     UsuarioResponseDto actualizarDto(Integer id, UsuarioUpdateDto dto); 
}