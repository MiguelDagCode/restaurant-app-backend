package com.buensabor.restaurantapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_detalle_pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetallePedido implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private DetallePedidoId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPedido") // Vincula con idPedido del EmbeddedId
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY) // LAZY: Solo cargamos info del producto si se pide
    @MapsId("idProducto") // Vincula con idProducto del EmbeddedId
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
    
    //RAMA PRUEBA
}