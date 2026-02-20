package com.buensabor.restaurantapp.config;

import com.buensabor.restaurantapp.model.Categoria;
import com.buensabor.restaurantapp.model.Producto;
import com.buensabor.restaurantapp.model.Rol;
import com.buensabor.restaurantapp.model.Usuario;
import com.buensabor.restaurantapp.repository.CategoriaRepository;
import com.buensabor.restaurantapp.repository.ProductoRepository;
import com.buensabor.restaurantapp.repository.RolRepository;
import com.buensabor.restaurantapp.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;
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

        // Crear categorías por defecto
        Categoria entradas = crearCategoriaSiNoExiste("Entradas");
        Categoria fondo = crearCategoriaSiNoExiste("Platos de Fondo");
        Categoria postres = crearCategoriaSiNoExiste("Postres");
        Categoria bebidas = crearCategoriaSiNoExiste("Bebidas");

        // Crear productos de ejemplo
        crearProductoSiNoExiste("Ceviche Clásico", new BigDecimal("25.00"), "img/ceviche.jpg", entradas);
        crearProductoSiNoExiste("Lomo Saltado", new BigDecimal("30.00"), "img/lomo.jpg", fondo);
        crearProductoSiNoExiste("Suspiro a la Limeña", new BigDecimal("15.00"), "img/suspiro.jpg", postres);
        crearProductoSiNoExiste("Chicha Morada", new BigDecimal("8.00"), "img/chicha.jpg", bebidas);

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

    private Categoria crearCategoriaSiNoExiste(String nombreCategoria) {
        return categoriaRepository.findAll().stream()
                .filter(c -> c.getNombre().equalsIgnoreCase(nombreCategoria))
                .findFirst()
                .orElseGet(() -> {
                    Categoria categoria = Categoria.builder()
                            .nombre(nombreCategoria)
                            .activo(true)
                            .build();
                    categoriaRepository.save(categoria);
                    System.out.println(" > Categoría creada: " + nombreCategoria);
                    return categoria;
                });
    }

    private void crearProductoSiNoExiste(String nombre, BigDecimal precio, String imgUrl, Categoria categoria) {
        productoRepository.findAll().stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .ifPresentOrElse(
                        p -> System.out.println(" > El producto ya existe (" + nombre + ")."),
                        () -> {
                            Producto producto = Producto.builder()
                                    .nombre(nombre)
                                    .precio(precio)
                                    .imgUrl(imgUrl)
                                    .categoria(categoria)
                                    .estado(true)
                                    .build();
                            productoRepository.save(producto);
                            System.out.println(" > Producto creado: " + nombre);
                        }
                );
    }
}
