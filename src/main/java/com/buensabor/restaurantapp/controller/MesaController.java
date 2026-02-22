package com.buensabor.restaurantapp.controller;

import com.buensabor.restaurantapp.model.Mesa;
import com.buensabor.restaurantapp.service.MesaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/mesas")
@RequiredArgsConstructor
public class MesaController {

    private final MesaService mesaService;

    // --- ACCESO: MOZOS, CAJEROS Y ADMINISTRADORES ---
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO', 'CAJERO')")
    public ResponseEntity<List<Mesa>> findAll() {
        List<Mesa> mesas = mesaService.findAll();
        List<Mesa> response = mesas;
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MOZO', 'CAJERO')")
    public ResponseEntity<Mesa> findById(@PathVariable Integer id) {
        Mesa mesa = mesaService.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada con ID: " + id));
        Mesa response =  mesa;
        return ResponseEntity.ok(response);
    }

    // --- ACCESO: SOLO ADMINISTRADORES ---

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Mesa> insert(@Valid @RequestBody Mesa mesa) {
        Mesa nuevaMesa = mesaService.insert(mesa);
        Mesa response = nuevaMesa;
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Mesa> update(@PathVariable Integer id, @Valid @RequestBody Mesa mesa) {
        Mesa mesaActualizada = mesaService.update(id, mesa);
        Mesa response =  mesaActualizada;
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/mantenimiento")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> enviarAMantenimiento(@PathVariable Integer id) {
        mesaService.enviarAMantenimiento(id);
        return ResponseEntity.ok().build();
    }
}