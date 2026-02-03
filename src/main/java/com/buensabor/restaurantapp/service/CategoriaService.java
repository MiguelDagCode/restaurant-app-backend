package com.buensabor.restaurantapp.service;

import com.buensabor.restaurantapp.entity.CategoriaPlato;
import java.util.List;
import java.util.Optional;

public interface CategoriaService {
    List<CategoriaPlato> listarCategorias();
    Optional<CategoriaPlato> buscarPorNombre(String nombre);
    CategoriaPlato buscarPorId(Integer id);
    CategoriaPlato guardar(CategoriaPlato categoria);
    CategoriaPlato actualizar(Integer id, CategoriaPlato categoria);
    void eliminar(Integer id);
}
