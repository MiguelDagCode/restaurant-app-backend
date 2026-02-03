package com.buensabor.restaurantapp.service.impl;

import com.buensabor.restaurantapp.entity.CategoriaPlato;
import com.buensabor.restaurantapp.repository.CategoriaRepository;
import com.buensabor.restaurantapp.service.CategoriaService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaServiceImpl(CategoriaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CategoriaPlato> listarCategorias() {
        return repository.findAll();
    }

    @Override
    public Optional<CategoriaPlato> buscarPorNombre(String nombre) {
        return repository.findByNombreIgnoreCase(nombre);
    }

    @Override
    public CategoriaPlato buscarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public CategoriaPlato guardar(CategoriaPlato categoria) {
        if (categoria.getNombre() == null || categoria.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre requerido");
        }
        if (repository.existsByNombreIgnoreCase(categoria.getNombre())) {
            throw new DataIntegrityViolationException("Ya existe una categoría con ese nombre.");
        }
        return repository.save(categoria);
    }

    @Override
    public CategoriaPlato actualizar(Integer id, CategoriaPlato categoria) {
        CategoriaPlato existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        if (repository.existsByNombreIgnoreCase(categoria.getNombre())
            && !existente.getNombre().equalsIgnoreCase(categoria.getNombre())) {
            throw new DataIntegrityViolationException("Nombre ya en uso");
        }
        existente.setNombre(categoria.getNombre());
        return repository.save(existente);
    }

    @Override
    public void eliminar(Integer id) {
        CategoriaPlato c = repository.findById(id).orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        repository.delete(c);
    }
}
