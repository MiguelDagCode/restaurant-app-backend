package com.buensabor.restaurantapp.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PedidoResponse {
    private Long idPedido;
    private Integer idMesa;
    private String numeroMesa;
    private String nombreMozo;
    private String nombresCliente;
    private String tipoDoc;
    private String numDoc;
    private String telefonoCliente;
    private String estadoPedido;
    private LocalDateTime fechaApertura;
    private BigDecimal total;
    
    // Lista de detalles anidada para que Angular tenga todo en 1 sola llamada
    private List<DetallePedidoResponse> detalles; 
}