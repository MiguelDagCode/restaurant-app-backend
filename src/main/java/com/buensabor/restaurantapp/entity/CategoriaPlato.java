package com.buensabor.restaurantapp.entity;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "categorias_platos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriaPlato implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nombre;

}
