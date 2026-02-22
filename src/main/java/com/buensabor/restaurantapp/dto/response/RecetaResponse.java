package com.buensabor.restaurantapp.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class RecetaResponse {
    // Datos del Producto (Plato)
    private Long idProducto;
    private String nombreProducto;
    private String categoria;

    // Datos del Insumo (Ingrediente)
    private Long idInsumo;
    private String nombreInsumo;
    private BigDecimal stockActual;
    private String unidadMedida;

    // Datos de la Receta (Relación)
    private BigDecimal cantidadInsumo;
}