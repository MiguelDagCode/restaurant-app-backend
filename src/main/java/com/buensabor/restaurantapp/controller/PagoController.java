package com.buensabor.restaurantapp.controller;

import com.buensabor.restaurantapp.entity.Pago;
import com.buensabor.restaurantapp.service.PagoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin("*")
public class PagoController {

    private final PagoService service;
    public PagoController(PagoService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<List<Pago>> listar() {
        List<Pago> r = service.listarTodos();
        return r.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(r);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pago> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Pago> crear(@RequestBody Pago pago) {
        Pago creado = service.registrar(pago);
        return ResponseEntity.created(URI.create("/api/pagos/" + creado.getId())).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pago> actualizar(@PathVariable Integer id, @RequestBody Pago pago) {
        return ResponseEntity.ok(service.actualizar(id, pago));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.ok().body("Pago eliminado");
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<Pago> porPedido(@PathVariable Integer pedidoId) {
        Pago p = service.buscarPorPedido(pedidoId);
        if (p == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(p);
    }
}