package com.buensabor.restaurantapp.service.impl;

import com.buensabor.restaurantapp.dto.response.ProductoListResponseDto;
import com.buensabor.restaurantapp.model.Categoria;
import com.buensabor.restaurantapp.model.Producto;
import com.buensabor.restaurantapp.repository.CategoriaRepository;
import com.buensabor.restaurantapp.repository.ProductoRepository;
import com.buensabor.restaurantapp.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Categoria> listarActivas() {
        return categoriaRepository.findByActivoTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Categoria> buscarPorId(Integer id) {
        return categoriaRepository.findById(id);
    }

    @Override
    @Transactional
    public Categoria registrar(Categoria categoria) {
        categoria.setActivo(true); // Por defecto activa
        return categoriaRepository.save(categoria);
    }

    @Override
    @Transactional
    public Categoria actualizar(Integer id, Categoria categoriaDatos) {
        Categoria categoriaExistente = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        categoriaExistente.setNombre(categoriaDatos.getNombre());
        return categoriaRepository.save(categoriaExistente);
    }

    @Override
    @Transactional
    public void cambiarEstado(Integer idCategoria) {
        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Boolean nuevoEstado = !categoria.getActivo();
        categoria.setActivo(nuevoEstado);

        // Cascada: actualizar productos vía repositorio
        List<Producto> productos = productoRepository.findByCategoriaIdCategoria(idCategoria);
        productos.forEach(p -> p.setEstado(nuevoEstado));

        categoriaRepository.save(categoria);
        productoRepository.saveAll(productos);
    }


}
