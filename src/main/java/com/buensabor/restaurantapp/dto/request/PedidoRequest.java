package com.buensabor.restaurantapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

import com.buensabor.restaurantapp.enums.TipoDocumento;

@Data
public class PedidoRequest {
    @NotNull(message = "La mesa es obligatoria")
    private Integer idMesa;

    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String nombresCliente;

    // Puedes agregar validaciones para tipoDoc y numDoc si los haces obligatorios
    private TipoDocumento tipoDoc;
    private String numDoc;
    private String telefonoCliente;
    
    @NotEmpty(message = "El pedido debe tener al menos un producto")
    private List<DetallePedidoRequest> detalles;
}