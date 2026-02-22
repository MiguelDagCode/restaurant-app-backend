package com.buensabor.restaurantapp.seeds;

import com.buensabor.restaurantapp.model.Categoria;
import com.buensabor.restaurantapp.model.Insumo;
import com.buensabor.restaurantapp.model.Producto;
import com.buensabor.restaurantapp.model.Receta;
import com.buensabor.restaurantapp.model.RecetaId;
import com.buensabor.restaurantapp.repository.CategoriaRepository;
import com.buensabor.restaurantapp.repository.InsumoRepository;
import com.buensabor.restaurantapp.repository.ProductoRepository;
import com.buensabor.restaurantapp.repository.RecetaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Component
@Order(2)
@RequiredArgsConstructor
public class CatalogoDataInitializer implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;
    private final InsumoRepository insumoRepository; // Inyectamos el repo de Insumos
    private final RecetaRepository recetaRepository;
    
    @Override
    @Transactional
    public void run(String... args) {
        System.out.println("--- INICIANDO CARGA DE DATOS MAESTROS (CATÁLOGO E INVENTARIO) ---");

        // 1. Crear categorías por defecto
        Categoria entradas = crearCategoriaSiNoExiste("Entradas");
        Categoria fondo = crearCategoriaSiNoExiste("Platos de Fondo");
        Categoria postres = crearCategoriaSiNoExiste("Postres");
        Categoria bebidas = crearCategoriaSiNoExiste("Bebidas");

        // 2. Crear productos de ejemplo
        crearProductoSiNoExiste("Ceviche Clásico", new BigDecimal("25.00"), "img/ceviche.jpg", entradas);
        crearProductoSiNoExiste("Lomo Saltado", new BigDecimal("30.00"), "img/lomo.jpg", fondo);
        crearProductoSiNoExiste("Suspiro a la Limeña", new BigDecimal("15.00"), "img/suspiro.jpg", postres);
        crearProductoSiNoExiste("Chicha Morada", new BigDecimal("8.00"), "img/chicha.jpg", bebidas);

        // 3. Crear Insumos (Pensados estratégicamente para las recetas de los productos anteriores)
        System.out.println("--- Cargando Insumos Base ---");
        
        // Insumos para Ceviche
        crearInsumoSiNoExiste("Pescado Blanco", new BigDecimal("15.500"), new BigDecimal("3.000"), "KG");
        crearInsumoSiNoExiste("Limón", new BigDecimal("10.000"), new BigDecimal("2.000"), "KG");
        crearInsumoSiNoExiste("Cebolla Roja", new BigDecimal("8.000"), new BigDecimal("1.500"), "KG");
        
        // Insumos para Lomo Saltado
        crearInsumoSiNoExiste("Lomo de Res", new BigDecimal("20.000"), new BigDecimal("5.000"), "KG");
        crearInsumoSiNoExiste("Papa Amarilla", new BigDecimal("30.000"), new BigDecimal("10.000"), "KG");
        crearInsumoSiNoExiste("Tomate", new BigDecimal("5.000"), new BigDecimal("1.000"), "KG");
        crearInsumoSiNoExiste("Arroz", new BigDecimal("50.000"), new BigDecimal("15.000"), "KG");
        
        // Insumos para Postres y Bebidas
        crearInsumoSiNoExiste("Leche Evaporada", new BigDecimal("24.000"), new BigDecimal("6.000"), "LT");
        crearInsumoSiNoExiste("Leche Condensada", new BigDecimal("12.000"), new BigDecimal("3.000"), "LT");
        crearInsumoSiNoExiste("Maíz Morado", new BigDecimal("15.000"), new BigDecimal("3.000"), "KG");
        crearInsumoSiNoExiste("Azúcar Blanca", new BigDecimal("25.000"), new BigDecimal("5.000"), "KG");

     // 4. Crear Recetas (Vincular Productos con Insumos)
        System.out.println("--- Vinculando Recetas ---");
        
        // Receta: Ceviche Clásico
        crearRecetaSiNoExiste("Ceviche Clásico", "Pescado Blanco", new BigDecimal("0.250")); // 250g
        crearRecetaSiNoExiste("Ceviche Clásico", "Limón", new BigDecimal("0.100")); // 100g de limón
        crearRecetaSiNoExiste("Ceviche Clásico", "Cebolla Roja", new BigDecimal("0.050")); // 50g

        // Receta: Lomo Saltado
        crearRecetaSiNoExiste("Lomo Saltado", "Lomo de Res", new BigDecimal("0.200"));
        crearRecetaSiNoExiste("Lomo Saltado", "Papa Amarilla", new BigDecimal("0.300"));
        crearRecetaSiNoExiste("Lomo Saltado", "Tomate", new BigDecimal("0.080"));
        crearRecetaSiNoExiste("Lomo Saltado", "Cebolla Roja", new BigDecimal("0.080"));
        crearRecetaSiNoExiste("Lomo Saltado", "Arroz", new BigDecimal("0.150"));

        // Receta: Suspiro a la Limeña
        crearRecetaSiNoExiste("Suspiro a la Limeña", "Leche Evaporada", new BigDecimal("0.150"));
        crearRecetaSiNoExiste("Suspiro a la Limeña", "Leche Condensada", new BigDecimal("0.150"));
        crearRecetaSiNoExiste("Suspiro a la Limeña", "Azúcar Blanca", new BigDecimal("0.050"));

        // Receta: Chicha Morada
        crearRecetaSiNoExiste("Chicha Morada", "Maíz Morado", new BigDecimal("0.200"));
        crearRecetaSiNoExiste("Chicha Morada", "Limón", new BigDecimal("0.020"));
        crearRecetaSiNoExiste("Chicha Morada", "Azúcar Blanca", new BigDecimal("0.050"));

        System.out.println("--- CARGA DE DATOS MAESTROS CATALOGO FINALIZADA ---");
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

    private void crearInsumoSiNoExiste(String nombre, BigDecimal stockActual, BigDecimal stockMinimo, String unidadMedida) {
        insumoRepository.findByNombreIgnoreCase(nombre)
                .ifPresentOrElse(
                        i -> System.out.println(" > El insumo ya existe (" + nombre + ")."),
                        () -> {
                            Insumo insumo = Insumo.builder()
                                    .nombre(nombre.toUpperCase()) // Estandarizamos a mayúsculas
                                    .stockActual(stockActual)
                                    .stockMinimo(stockMinimo)
                                    .unidadMedida(unidadMedida.toUpperCase())
                                    .build();
                            insumoRepository.save(insumo);
                            System.out.println(" > Insumo creado: " + nombre + " | Stock: " + stockActual + " " + unidadMedida);
                        }
                );
    }
    private void crearRecetaSiNoExiste(String nombreProducto, String nombreInsumo, BigDecimal cantidad) {
        // 1. Buscamos el producto y el insumo por nombre
        Optional<Producto> productoOpt = productoRepository.findAll().stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombreProducto))
                .findFirst();
                
        Optional<Insumo> insumoOpt = insumoRepository.findByNombreIgnoreCase(nombreInsumo);

        if (productoOpt.isPresent() && insumoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            Insumo insumo = insumoOpt.get();
            RecetaId recetaId = new RecetaId(producto.getIdProducto(), insumo.getIdInsumo());

            // 2. Verificamos si la llave compuesta ya existe
            if (!recetaRepository.existsById(recetaId)) {
                Receta receta = Receta.builder()
                        .id(recetaId)
                        .producto(producto)
                        .insumo(insumo)
                        .cantidadInsumo(cantidad)
                        .activo(true)
                        .build();
                recetaRepository.save(receta);
                System.out.println(" > Ingrediente añadido a " + nombreProducto + ": " + cantidad + " de " + nombreInsumo);
            }
        } else {
            System.out.println(" > ERROR al crear receta: No se encontró el producto o el insumo (" + nombreProducto + " / " + nombreInsumo + ")");
        }
    }
}