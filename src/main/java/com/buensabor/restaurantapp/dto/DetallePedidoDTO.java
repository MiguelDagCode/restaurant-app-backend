package com.buensabor.restaurantapp.dto;

import com.buensabor.restaurantapp.enums.EstadoDetalle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetallePedidoDTO {
    private Integer id;
    private Integer platoId;
    private Integer cantidad;
    private EstadoDetalle estado;
}