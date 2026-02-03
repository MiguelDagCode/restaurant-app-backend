package com.buensabor.restaurantapp.entity;


import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolUsuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRolUsuario")
    private Integer id;

    @Column(name = "rolUsuario", nullable = false, length = 50)
    private String nombre;
}