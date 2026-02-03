package com.buensabor.restaurantapp.service;

import com.buensabor.restaurantapp.entity.Plato;
import java.util.List;

public interface PlatoService {
    List<Plato> listarTodos();
    Plato buscarPorId(Integer id);
    List<Plato> listarPorCategoria(Integer categoriaId);
    Plato guardar(Plato plato);
    Plato actualizar(Integer id, Plato plato);
    void eliminar(Integer id);
}
