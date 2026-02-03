package com.buensabor.restaurantapp.controller;

import com.buensabor.restaurantapp.entity.RolUsuario;
import com.buensabor.restaurantapp.service.RolUsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin("*")
public class RolUsuarioController {

    private final RolUsuarioService service;
    public RolUsuarioController(RolUsuarioService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<List<RolUsuario>> listar() {
        List<RolUsuario> r = service.listarTodos();
        return r.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(r);
    }

    @PostMapping
    public ResponseEntity<RolUsuario> crear(@RequestBody RolUsuario rol) {
        RolUsuario creado = service.guardar(rol);
        return ResponseEntity.created(URI.create("/api/roles/" + creado.getId())).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolUsuario> actualizar(@PathVariable Integer id, @RequestBody RolUsuario rol) {
        return ResponseEntity.ok(service.actualizar(id, rol));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.ok().body("Rol eliminado");
    }
}