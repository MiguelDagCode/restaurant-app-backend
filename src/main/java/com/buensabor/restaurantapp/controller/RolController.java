package com.buensabor.restaurantapp.controller;

import com.buensabor.restaurantapp.model.Rol;
import com.buensabor.restaurantapp.service.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class RolController {

    private final RolService rolService;

    @GetMapping
    public ResponseEntity<List<Rol>> listar() {
        return ResponseEntity.ok(rolService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rol> buscarPorId(@PathVariable Integer id) {
        return rolService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Rol> crear(@RequestBody Rol rol) {
        // Spring construye el objeto Rol desde el JSON.
        // Ignoramos si envían ID o activo, el servicio lo controla.
        return new ResponseEntity<>(rolService.guardar(rol), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rol> actualizar(@PathVariable Integer id, @RequestBody Rol rol) {
        // Pasamos el ID de la URL y el objeto del Body
        return ResponseEntity.ok(rolService.actualizar(id, rol));
    }

    @PatchMapping("/{id}/cambiar-estado")
    public ResponseEntity<Void> cambiarEstado(@PathVariable Integer id) {
        rolService.cambiarEstado(id);
        return ResponseEntity.noContent().build();
    }
}