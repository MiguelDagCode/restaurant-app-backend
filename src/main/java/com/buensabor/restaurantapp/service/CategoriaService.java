package com.buensabor.restaurantapp.service;

import com.buensabor.restaurantapp.dto.response.ProductoListResponseDto;
import com.buensabor.restaurantapp.model.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {
    List<Categoria> listarTodas();
    List<Categoria> listarActivas();
    Optional<Categoria> buscarPorId(Integer id);
    Categoria registrar(Categoria categoria);
    Categoria actualizar(Integer id, Categoria categoria);
    void cambiarEstado(Integer id); // Eliminación lógica
    

}
