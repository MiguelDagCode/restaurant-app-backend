package com.buensabor.restaurantapp.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.buensabor.restaurantapp.enums.EstadoPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
    private Integer id;
    private Integer mozoId;
    private Integer mesaId;
    private LocalDateTime fecha;
    private EstadoPedido estado;
    private List<DetallePedidoDTO> detalles;
}