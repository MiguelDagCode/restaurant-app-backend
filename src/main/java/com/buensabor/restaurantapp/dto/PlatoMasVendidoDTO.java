package com.buensabor.restaurantapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlatoMasVendidoDTO {
    private String nombrePlato;
    private Long totalVendidos;
}