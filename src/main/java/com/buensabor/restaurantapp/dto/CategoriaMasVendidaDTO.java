package com.buensabor.restaurantapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaMasVendidaDTO {
    private Integer idCategoria;
	private String nombreCategoria;
    private Long totalVendidos;
}