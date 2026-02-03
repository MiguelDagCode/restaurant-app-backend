package com.buensabor.restaurantapp.controller;

import com.buensabor.restaurantapp.entity.Pedido;
import com.buensabor.restaurantapp.enums.EstadoPedido;
import com.buensabor.restaurantapp.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin("*")
public class PedidoController {

    private final PedidoService service;
    public PedidoController(PedidoService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<List<Pedido>> listar() {
        List<Pedido> all = service.listarTodos();
        return all.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Pedido> crear(@RequestBody Pedido pedido) {
        Pedido creado = service.registrar(pedido);
        return ResponseEntity.created(URI.create("/api/pedidos/" + creado.getId())).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> actualizar(@PathVariable Integer id, @RequestBody Pedido pedido) {
        return ResponseEntity.ok(service.actualizar(id, pedido));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.ok().body("Pedido eliminado");
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Pedido> cambiarEstado(@PathVariable Integer id, @RequestParam EstadoPedido estado) {
        return ResponseEntity.ok(service.cambiarEstado(id, estado));
    }


    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Pedido>> listarPorEstado(@PathVariable EstadoPedido estado) {
        List<Pedido> list = service.listarPorEstado(estado);
        return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
    }
}
