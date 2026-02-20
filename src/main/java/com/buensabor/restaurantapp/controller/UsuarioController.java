package com.buensabor.restaurantapp.controller;

import com.buensabor.restaurantapp.dto.request.UsuarioCreateDto;
import com.buensabor.restaurantapp.dto.request.UsuarioUpdateDto;
import com.buensabor.restaurantapp.dto.response.UsuarioListResponseDto;
import com.buensabor.restaurantapp.dto.response.UsuarioResponseDto;
import com.buensabor.restaurantapp.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios") // Ruta base clara
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")// EL GUARDIÁN DE SEGURIDAD
public class UsuarioController {

    private final UsuarioService usuarioService;

    // 1. LISTADO (Para la tabla principal)
    @GetMapping
    public ResponseEntity<List<UsuarioListResponseDto>> listar() {
        return ResponseEntity.ok(usuarioService.listarTodosDto());
    }

    // 2. DETALLE (Para rellenar el formulario de edición)
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> obtenerPorId(@PathVariable Integer id) {
        return usuarioService.buscarPorIdDto(id)
                .map(ResponseEntity::ok) // Si existe, devuelve 200 y el cuerpo
                .orElse(ResponseEntity.notFound().build()); // Si no, 404
    }

    // 3. CREAR (Valida el DTO UsuarioCreateDto)
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> crear(@Valid @RequestBody UsuarioCreateDto dto) {
        UsuarioResponseDto nuevoUsuario = usuarioService.registrarDto(dto);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    // 4. ACTUALIZAR (Valida el DTO UsuarioUpdateDto)
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> actualizar(
            @PathVariable Integer id, 
            @Valid @RequestBody UsuarioUpdateDto dto
    ) {
        return ResponseEntity.ok(usuarioService.actualizarDto(id, dto));
    }

    // 5. CAMBIAR ESTADO (Toggle)
    // Usamos PATCH porque modificamos solo una parte del recurso (el estado)
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> cambiarEstado(@PathVariable Integer id) {
        usuarioService.cambiarEstado(id);
        return ResponseEntity.noContent().build();
    }
}