package com.buensabor.restaurantapp.service;

import com.buensabor.restaurantapp.dto.request.RecetaRequest;
import com.buensabor.restaurantapp.dto.response.RecetaResponse;

import java.util.List;
import java.util.Optional;

public interface RecetaService {
    List<RecetaResponse> findAll();
    List<RecetaResponse> findByProducto(Long idProducto); // Listar por plato
    Optional<RecetaResponse> findById(Long idProducto, Long idInsumo);
    RecetaResponse insert(RecetaRequest request);
    RecetaResponse update(RecetaRequest request);
}