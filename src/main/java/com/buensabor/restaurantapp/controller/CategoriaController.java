package com.buensabor.restaurantapp.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import com.buensabor.restaurantapp.entity.CategoriaPlato;
import com.buensabor.restaurantapp.service.CategoriaService;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    // ✅ Listar todas las categorías
    @GetMapping
    public ResponseEntity<List<CategoriaPlato>> listar() {
        List<CategoriaPlato> categorias = categoriaService.listarCategorias();
        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.ok(categorias); // 200
    }

    // ✅ Obtener categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaPlato> obtenerPorId(@PathVariable Integer id) {
        CategoriaPlato categoria = categoriaService.buscarPorId(id);
        if (categoria == null) {
            return ResponseEntity.notFound().build(); // 404
        }
        return ResponseEntity.ok(categoria); // 200
    }

    // ✅ Crear nueva categoría
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody CategoriaPlato categoria) {
        if (categoria.getNombre() == null || categoria.getNombre().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El nombre de la categoría es obligatorio."); // 400
        }

        CategoriaPlato nueva = categoriaService.guardar(categoria);
        return ResponseEntity.ok(nueva); // 200 (podrías usar 201 Created si prefieres)
    }

    // ✅ Actualizar categoría existente
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody CategoriaPlato categoria) {
        CategoriaPlato actualizada = categoriaService.actualizar(id, categoria);
        return ResponseEntity.ok(actualizada);
    }

    // ✅ Eliminar categoría
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        CategoriaPlato categoria = categoriaService.buscarPorId(id);

        if (categoria == null) {
            return ResponseEntity.notFound().build(); // 404
        }

        categoriaService.eliminar(id);
        return ResponseEntity.noContent().build(); 
    }
}