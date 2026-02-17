package com.buensabor.restaurantapp.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tb_usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario implements Serializable, UserDetails { // Implementamos la interfaz

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "correo", nullable = false, unique = true, length = 100)
    private String correo;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "estado", nullable = false)
    private Boolean estado;

    // --- MÉTODOS DE USER DETAILS ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Spring Security espera roles con el prefijo "ROLE_"
        // Asumimos que rol.getNombreRol() devuelve algo como "ADMIN", "MOZO"
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol.getNombreRol().toUpperCase()));
    }

    @Override
    public String getUsername() {
        // Usamos el correo como identificador de login
        return this.correo;
    }

    @Override
    public String getPassword() {
        // Retorna la contraseña hasheada
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Como 'estado' es Boolean, usamos Boolean.TRUE.equals para evitar NullPointerException si fuera null
        return Boolean.TRUE.equals(this.estado);
    }
}