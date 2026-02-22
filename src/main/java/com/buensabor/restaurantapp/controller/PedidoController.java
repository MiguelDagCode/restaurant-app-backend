package com.buensabor.restaurantapp.controller;

import com.buensabor.restaurantapp.dto.request.PedidoRequest;
import com.buensabor.restaurantapp.model.Pedido;
import com.buensabor.restaurantapp.service.impl.PedidoServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
}