package com.buensabor.restaurantapp.controller;

import com.buensabor.restaurantapp.model.Insumo;
import com.buensabor.restaurantapp.service.InsumoService;
import com.buensabor.restaurantapp.util.ModeloNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/insumos")
@RequiredArgsConstructor
public class InsumoController {

    private final InsumoService insumoService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'COCINERO')")
    public ResponseEntity<List<Insumo>> findAll() {
        return ResponseEntity.ok(insumoService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COCINERO')")
    public ResponseEntity<Insumo> findById(@PathVariable Long id) {
        Insumo insumo = insumoService.findById(id)
                .orElseThrow(() -> new ModeloNotFoundException("Insumo no encontrado con ID: " + id));
        return ResponseEntity.ok(insumo);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Insumo> insert(@Valid @RequestBody Insumo insumo) {
        return new ResponseEntity<>(insumoService.insert(insumo), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Insumo> update(@PathVariable Long id, @Valid @RequestBody Insumo insumo) {
        return ResponseEntity.ok(insumoService.update(id, insumo));
    }
}