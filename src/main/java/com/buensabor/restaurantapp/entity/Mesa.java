package com.buensabor.restaurantapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

import com.buensabor.restaurantapp.enums.EstadoMesa;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "mesas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mesa implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private Integer numero;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoMesa estado = EstadoMesa.LIBRE;

    @OneToMany(mappedBy = "mesa")
    @JsonManagedReference
    private List<Pedido> pedidos;

}