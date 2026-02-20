package com.buensabor.restaurantapp.controller;

import com.buensabor.restaurantapp.dto.response.CategoriaConProductosDto;
import com.buensabor.restaurantapp.dto.response.ProductoListResponseDto;
import com.buensabor.restaurantapp.model.Producto;
import com.buensabor.restaurantapp.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping("/dto/{id}")
    public ResponseEntity<ProductoListResponseDto> buscarPorIdDto(@PathVariable Long id) {
        return productoService.buscarPorIdDto(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/dto")
    public ResponseEntity<List<ProductoListResponseDto>> listarTodosDto() {
        return ResponseEntity.ok(productoService.listarTodosDto());
    }

    @GetMapping("/dto/activos")
    public ResponseEntity<List<ProductoListResponseDto>> listarActivosDto() {
        return ResponseEntity.ok(productoService.listarActivosDto());
    }


    @PostMapping
    public ResponseEntity<Producto> registrar(@RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.registrar(producto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.actualizar(id, producto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> cambiarEstado(@PathVariable Long id) {
        productoService.cambiarEstado(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/dto/categoria/{idCategoria}")
    public ResponseEntity<List<ProductoListResponseDto>> listarPorCategoria(@PathVariable Integer idCategoria) {
        return ResponseEntity.ok(productoService.listarPorCategoria(idCategoria));
    }
    
    @GetMapping("/categorias/con-productos")
    public ResponseEntity<List<CategoriaConProductosDto>> listarCategoriasConProductosActivos() {
        return ResponseEntity.ok(productoService.listarCategoriasConProductosActivos());
    }


}
