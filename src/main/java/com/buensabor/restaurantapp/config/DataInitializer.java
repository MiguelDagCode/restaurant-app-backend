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

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // Valores por defecto (se pueden sobreescribir en Railway)
    @Value("${app.admin.email:admin@delifast.com}")
    private String adminEmail;

    @Value("${app.admin.password:admin123}")
    private String adminPassword;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("--- INICIANDO CARGA DE DATOS MAESTROS ---");

        // 1. Crear Roles si no existen
        Rol rolAdmin = crearRolSiNoExiste("ADMIN");
        crearRolSiNoExiste("MOZO");
        crearRolSiNoExiste("COCINERO");
        crearRolSiNoExiste("CAJERO");

        // 2. Crear Usuario Admin si no existe
        crearUsuarioAdminSiNoExiste(rolAdmin);
        
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

    private void crearUsuarioAdminSiNoExiste(Rol rolAdmin) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByCorreo(adminEmail);

        if (usuarioExistente.isEmpty()) {
            Usuario admin = Usuario.builder()
                    .nombre("Administrador")
                    .apellido("Sistema")
                    .correo(adminEmail)
                    .telefono("999999999")
                    .password(passwordEncoder.encode(adminPassword)) // ¡Hash importante!
                    .rol(rolAdmin)
                    .estado(true)
                    .build();
            usuarioRepository.save(admin);
            System.out.println(" > Usuario ADMIN creado con éxito: " + adminEmail);
        } else {
            System.out.println(" > El usuario ADMIN ya existe (" + adminEmail + "). No se realizaron cambios.");
        }
    }
}