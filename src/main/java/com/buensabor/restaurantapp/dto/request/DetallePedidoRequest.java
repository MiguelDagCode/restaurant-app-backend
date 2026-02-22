package com.buensabor.restaurantapp.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DetallePedidoRequest {
    @NotNull(message = "El producto es obligatorio")
    private Long idProducto;

    @Min(value = 1, message = "La cantidad mínima es 1")
    private Integer cantidad;

    private String observaciones; 
}