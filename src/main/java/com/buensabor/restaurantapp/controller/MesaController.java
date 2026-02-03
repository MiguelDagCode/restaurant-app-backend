package com.buensabor.restaurantapp.controller;

import com.buensabor.restaurantapp.entity.Mesa;
import com.buensabor.restaurantapp.enums.EstadoMesa;
import com.buensabor.restaurantapp.service.MesaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/mesas")
@CrossOrigin("*")
public class MesaController {

    private final MesaService service;
    public MesaController(MesaService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<List<Mesa>> listar() {
        List<Mesa> all = service.listarTodas();
        return all.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mesa> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Mesa> crear(@RequestBody Mesa mesa) {
        Mesa creado = service.registrar(mesa);
        return ResponseEntity.created(URI.create("/api/mesas/" + creado.getId())).body(creado);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Mesa> cambiarEstado(@PathVariable Integer id, @RequestParam EstadoMesa estado) {
        return ResponseEntity.ok(service.cambiarEstado(id, estado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mesa> actualizar(@PathVariable Integer id, @RequestBody Mesa mesa) {
        return ResponseEntity.ok(service.actualizar(id, mesa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.ok().body("Mesa eliminada");
    }
}
