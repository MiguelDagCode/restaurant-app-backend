package com.buensabor.restaurantapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_receta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Receta implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private RecetaId id;

    // @MapsId vincula las relaciones con los campos del EmbeddedId
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idProducto")
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idInsumo")
    @JoinColumn(name = "id_insumo", nullable = false)
    private Insumo insumo;

    @Column(name = "cantidad_insumo", nullable = false, precision = 10, scale = 3)
    private BigDecimal cantidadInsumo;

    @Column(name = "activo", nullable = false)
    private Boolean activo;
}