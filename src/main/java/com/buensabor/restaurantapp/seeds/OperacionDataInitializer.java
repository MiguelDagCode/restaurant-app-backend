package com.buensabor.restaurantapp.seeds;

import com.buensabor.restaurantapp.enums.EstadoMesa;
import com.buensabor.restaurantapp.model.Mesa;
import com.buensabor.restaurantapp.repository.MesaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Order(3) // Se ejecuta en tercer lugar (después de Seguridad y Catálogo/Inventario)
@RequiredArgsConstructor
public class OperacionDataInitializer implements CommandLineRunner {

    private final MesaRepository mesaRepository;

    @Override
    @Transactional
    public void run(String... args) {
        System.out.println("--- 3. INICIANDO CARGA DE MESAS (OPERACIONES) ---");

        // Creamos las mesas siguiendo el patrón de la interfaz gráfica
        crearMesaSiNoExiste("1", 4);
        crearMesaSiNoExiste("2", 2);
        crearMesaSiNoExiste("3", 6);
        crearMesaSiNoExiste("4", 4);
        crearMesaSiNoExiste("5", 2);
        crearMesaSiNoExiste("6", 8); // Mesa grande
        crearMesaSiNoExiste("7", 4);
        crearMesaSiNoExiste("8", 4);
        crearMesaSiNoExiste("9", 6);

        System.out.println("--- 3. CARGA DE MESAS FINALIZADA ---");
    }

    private void crearMesaSiNoExiste(String numeroMesa, int capacidad) {
        mesaRepository.findByNumeroMesa(numeroMesa).ifPresentOrElse(
                m -> System.out.println(" > La mesa " + numeroMesa + " ya existe."),
                () -> {
                    Mesa mesa = Mesa.builder()
                            .numeroMesa(numeroMesa)
                            .capacidad(capacidad)
                            .estado(EstadoMesa.LIBRE) // Todas nacen libres
                            .build();
                    mesaRepository.save(mesa);
                    System.out.println(" > Mesa creada: " + numeroMesa + " (Capacidad: " + capacidad + ")");
                }
        );
    }
}