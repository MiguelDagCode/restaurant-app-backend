package com.buensabor.restaurantapp.controller;

import com.buensabor.restaurantapp.dto.request.PedidoRequest;
import com.buensabor.restaurantapp.dto.response.PedidoResponse;
import com.buensabor.restaurantapp.model.Pedido;
import com.buensabor.restaurantapp.service.impl.PedidoServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoServiceImpl pedidoService;

    @PostMapping
    @PreAuthorize("hasAnyRole('MOZO', 'ADMIN')")
    public ResponseEntity<Pedido> registrarPedido(@Valid @RequestBody PedidoRequest request) {
        
        Pedido nuevoPedido = pedidoService.crearPedido(request);
        return new ResponseEntity<>(nuevoPedido, HttpStatus.CREATED);
    }
 //

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CAJERO', 'MOZO', 'COCINERO')")
    public ResponseEntity<List<PedidoResponse>> listarPedidos() {
        return ResponseEntity.ok(pedidoService.listarTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CAJERO', 'MOZO')")
    public ResponseEntity<PedidoResponse> obtenerPedidoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.obtenerPorId(id));
    }
    
    @PatchMapping("/{id}/cobrar")
    @PreAuthorize("hasAnyRole('ADMIN', 'CAJERO')")
    public ResponseEntity<PedidoResponse> cobrarPedido(@PathVariable Long id) {
        PedidoResponse pedidoCobrado = pedidoService.cobrarPedido(id);
        return ResponseEntity.ok(pedidoCobrado);
    }
    
    @PatchMapping("/{id}/iniciar-preparacion")
    @PreAuthorize("hasAnyRole('ADMIN', 'COCINERO')")
    public ResponseEntity<Void> iniciarPreparacion(@PathVariable Long id) {
        pedidoService.iniciarPreparacion(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/finalizar-preparacion")
    @PreAuthorize("hasAnyRole('ADMIN', 'COCINERO')")
    public ResponseEntity<Void> finalizarPreparacion(@PathVariable Long id) {
        pedidoService.finalizarPreparacion(id);
        return ResponseEntity.noContent().build();
    }
}