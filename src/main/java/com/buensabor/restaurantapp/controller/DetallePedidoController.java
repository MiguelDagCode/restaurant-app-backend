package com.buensabor.restaurantapp.controller;

import com.buensabor.restaurantapp.entity.DetallePedido;
import com.buensabor.restaurantapp.service.DetallePedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/detalles")
@CrossOrigin("*")
public class DetallePedidoController {

    private final DetallePedidoService service;
    public DetallePedidoController(DetallePedidoService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<List<DetallePedido>> listar() {
        List<DetallePedido> r = service.listarTodos();
        return r.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(r);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallePedido> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<DetallePedido>> porPedido(@PathVariable Integer pedidoId) {
        List<DetallePedido> r = service.listarPorPedido(pedidoId);
        return r.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(r);
    }

    @PostMapping
    public ResponseEntity<DetallePedido> crear(@RequestBody DetallePedido detalle) {
        DetallePedido creado = service.guardar(detalle);
        return ResponseEntity.created(URI.create("/api/detalles/" + creado.getId())).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetallePedido> actualizar(@PathVariable Integer id, @RequestBody DetallePedido detalle) {
        return ResponseEntity.ok(service.actualizar(id, detalle));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.ok().body("Detalle eliminado");
    }
}
