package com.buensabor.restaurantapp.config;

import com.buensabor.restaurantapp.model.Rol;
import com.buensabor.restaurantapp.model.Usuario;
import com.buensabor.restaurantapp.repository.RolRepository;
import com.buensabor.restaurantapp.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;



@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // Configuración de usuarios por defecto
    @Value("${app.admin.email:admin@delifast.com}")
    private String adminEmail;
    @Value("${app.admin.password:admin123}")
    private String adminPassword;

    @Value("${app.cajero.email:cajero@delifast.com}")
    private String cajeroEmail;
    @Value("${app.cajero.password:cajero123}")
    private String cajeroPassword;

    @Value("${app.cocinero.email:cocinero@delifast.com}")
    private String cocineroEmail;
    @Value("${app.cocinero.password:cocinero123}")
    private String cocineroPassword;

    @Value("${app.mozo.email:mozo@delifast.com}")
    private String mozoEmail;
    @Value("${app.mozo.password:mozo123}")
    private String mozoPassword;

    @Override
    @Transactional
    public void run(String... args) {
        System.out.println("--- INICIANDO CARGA DE DATOS MAESTROS ---");

        // Crear roles
        Rol rolAdmin = crearRolSiNoExiste("ADMIN");
        Rol rolCajero = crearRolSiNoExiste("CAJERO");
        Rol rolCocinero = crearRolSiNoExiste("COCINERO");
        Rol rolMozo = crearRolSiNoExiste("MOZO");

        // Crear usuarios por defecto
        crearUsuarioSiNoExiste("Administrador", "Sistema", adminEmail, adminPassword, rolAdmin);
        crearUsuarioSiNoExiste("Cajero", "Principal", cajeroEmail, cajeroPassword, rolCajero);
        crearUsuarioSiNoExiste("Cocinero", "Principal", cocineroEmail, cocineroPassword, rolCocinero);
        crearUsuarioSiNoExiste("Mozo", "Principal", mozoEmail, mozoPassword, rolMozo);

        System.out.println("--- CARGA DE DATOS MAESTROS FINALIZADA ---");
    }

    private Rol crearRolSiNoExiste(String nombreRol) {
        return rolRepository.findByNombreRol(nombreRol)
                .orElseGet(() -> {
                    Rol rol = Rol.builder()
                            .nombreRol(nombreRol)
                            .activo(true)
                            .build();
                    System.out.println(" > Creando rol: " + nombreRol);
                    return rolRepository.save(rol);
                });
    }

    private void crearUsuarioSiNoExiste(String nombre, String apellido, String correo, String password, Rol rol) {
        usuarioRepository.findByCorreo(correo).ifPresentOrElse(
                u -> System.out.println(" > El usuario ya existe (" + correo + "). No se realizaron cambios."),
                () -> {
                    Usuario usuario = Usuario.builder()
                            .nombre(nombre)
                            .apellido(apellido)
                            .correo(correo)
                            .telefono("999999999")
                            .password(passwordEncoder.encode(password))
                            .rol(rol)
                            .estado(true)
                            .build();
                    usuarioRepository.save(usuario);
                    System.out.println(" > Usuario " + rol.getNombreRol() + " creado con éxito: " + correo);
                }
        );
    }
}
