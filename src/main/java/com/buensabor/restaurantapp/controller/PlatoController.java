package com.buensabor.restaurantapp.controller;

import com.buensabor.restaurantapp.entity.Plato;
import com.buensabor.restaurantapp.service.PlatoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/platos")
@CrossOrigin("*")
public class PlatoController {

    private final PlatoService service;
    public PlatoController(PlatoService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<List<Plato>> listar() {
        List<Plato> all = service.listarTodos();
        return all.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plato> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Plato> crear(@RequestBody Plato plato) {
        Plato creado = service.guardar(plato);
        return ResponseEntity.created(URI.create("/api/platos/" + creado.getId())).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Plato> actualizar(@PathVariable Integer id, @RequestBody Plato plato) {
        return ResponseEntity.ok(service.actualizar(id, plato));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.ok().body("Plato eliminado");
    }
}