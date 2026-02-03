package com.buensabor.restaurantapp.service.impl;

import com.buensabor.restaurantapp.entity.Plato;
import com.buensabor.restaurantapp.repository.PlatoRepository;
import com.buensabor.restaurantapp.service.PlatoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class PlatoServiceImpl implements PlatoService {

    private final PlatoRepository repository;

    public PlatoServiceImpl(PlatoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Plato> listarTodos() {
        return repository.findAll();
    }

    @Override
    public Plato buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Plato no encontrado"));
    }

    @Override
    public List<Plato> listarPorCategoria(Integer categoriaId) {
        return repository.findByCategoriaId(categoriaId);
    }

    @Override
    public Plato guardar(Plato plato) {
        return repository.save(plato);
    }

    @Override
    public Plato actualizar(Integer id, Plato plato) {
        Plato existente = repository.findById(id).orElseThrow(() -> new RuntimeException("Plato no encontrado"));
        existente.setNombre(plato.getNombre());
        existente.setDescripcion(plato.getDescripcion());
        existente.setPrecio(plato.getPrecio());
        existente.setCategoria(plato.getCategoria());
        existente.setUrlImagen(plato.getUrlImagen());
        return repository.save(existente);
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}
