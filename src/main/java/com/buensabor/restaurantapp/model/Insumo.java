package com.buensabor.restaurantapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_insumo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Insumo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_insumo")
    private Long idInsumo;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "stock_actual", nullable = false, precision = 10, scale = 3)
    private BigDecimal stockActual;

    @Column(name = "stock_minimo", nullable = false, precision = 10, scale = 3)
    private BigDecimal stockMinimo;

    @Column(name = "unidad_medida", nullable = false, length = 20)
    private String unidadMedida;
}