package com.buensabor.restaurantapp.service;

import com.buensabor.restaurantapp.dto.response.CategoriaConProductosDto;
import com.buensabor.restaurantapp.dto.response.ProductoListResponseDto;
import com.buensabor.restaurantapp.model.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    List<ProductoListResponseDto> listarTodosDto();
    List<ProductoListResponseDto> listarActivosDto();
    Optional<ProductoListResponseDto> buscarPorIdDto(Long id);
    Producto registrar(Producto producto);
    Producto actualizar(Long id, Producto producto);
    void cambiarEstado(Long id);
    List<ProductoListResponseDto> listarPorCategoria(Integer idCategoria);
    List<Producto> findByCategoriaIdCategoriaAndEstadoTrue(Integer idCategoria);
    List<CategoriaConProductosDto> listarCategoriasConProductosActivos();
}
