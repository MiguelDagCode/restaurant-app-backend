package com.buensabor.restaurantapp.controller;

import com.buensabor.restaurantapp.dto.request.RecetaRequest;
import com.buensabor.restaurantapp.dto.response.RecetaResponse;
import com.buensabor.restaurantapp.model.Receta;
import com.buensabor.restaurantapp.service.RecetaService;
import com.buensabor.restaurantapp.util.ModeloNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recetas")
@RequiredArgsConstructor
public class RecetaController {

    private final RecetaService recetaService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'COCINERO')")
    public ResponseEntity<List<RecetaResponse>> findAll() {
        return ResponseEntity.ok(recetaService.findAll());
    }

    // Endpoint muy útil para buscar los ingredientes de un solo plato
    @GetMapping("/producto/{idProducto}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COCINERO')")
    public ResponseEntity<List<RecetaResponse>> findByProducto(@PathVariable Long idProducto) {
        return ResponseEntity.ok(recetaService.findByProducto(idProducto));
    }

    // Búsqueda por Llave Compuesta
    @GetMapping("/producto/{idProducto}/insumo/{idInsumo}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COCINERO')")
    public ResponseEntity<RecetaResponse> findById(@PathVariable Long idProducto, @PathVariable Long idInsumo) {
    	RecetaResponse receta = recetaService.findById(idProducto, idInsumo)
                .orElseThrow(() -> new ModeloNotFoundException("Receta no encontrada"));
        return ResponseEntity.ok(receta);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RecetaResponse> insert(@Valid @RequestBody RecetaRequest request) {
        return new ResponseEntity<>(recetaService.insert(request), HttpStatus.CREATED);
    }

    // Actualizar cantidad (Enviamos solo el número en un parámetro para simplificar)
    @PutMapping("/producto/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RecetaResponse> update(@RequestBody RecetaRequest request) { // Recibe la nueva cantidad por la URL (ej: ?cantidad=2.5)
        return ResponseEntity.ok(recetaService.update(request));
    }
}