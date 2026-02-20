package com.buensabor.restaurantapp.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoListResponseDto {
    private Long idProducto;
    private String nombre;
    private BigDecimal precio;
    private String imgUrl;
    private Boolean estado;
    private Integer idCategoria;
    private String categoriaNombre; // solo mostramos el nombre de la categoría
}
