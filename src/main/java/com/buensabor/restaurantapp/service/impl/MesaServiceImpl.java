package com.buensabor.restaurantapp.service.impl;

import com.buensabor.restaurantapp.enums.EstadoMesa;
import com.buensabor.restaurantapp.model.Mesa;
import com.buensabor.restaurantapp.repository.MesaRepository;
import com.buensabor.restaurantapp.service.MesaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MesaServiceImpl implements MesaService {

    private final MesaRepository mesaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Mesa> findAll() {
        return mesaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Mesa> findById(Integer idMesa) {
        return mesaRepository.findById(idMesa);
    }

    @Override
    @Transactional
    public Mesa insert(Mesa mesa) {
        String numeroFormateado = mesa.getNumeroMesa().trim().toUpperCase();

        if (mesaRepository.existsByNumeroMesa(numeroFormateado)) {
            throw new RuntimeException("La mesa número '" + numeroFormateado + "' ya existe.");
        }

        mesa.setNumeroMesa(numeroFormateado);
        mesa.setEstado(EstadoMesa.LIBRE); // Regla de negocio: Toda mesa nueva nace LIBRE
        return mesaRepository.save(mesa);
    }

    @Override
    @Transactional
    public Mesa update(Integer idMesa, Mesa mesa) {
        Mesa mesaExistente = mesaRepository.findById(idMesa)
                .orElseThrow(() -> new RuntimeException("No se encontró la Mesa con ID: " + idMesa));

        String numeroFormateado = mesa.getNumeroMesa().trim().toUpperCase();

        // Validar que si cambiamos el número, no choque con OTRA mesa
        Optional<Mesa> mesaDuplicada = mesaRepository.findByNumeroMesa(numeroFormateado);
        if (mesaDuplicada.isPresent() && !mesaDuplicada.get().getIdMesa().equals(idMesa)) {
            throw new RuntimeException("El número de mesa '" + numeroFormateado + "' ya está ocupado por otra mesa.");
        }

        mesaExistente.setNumeroMesa(numeroFormateado);
        mesaExistente.setCapacidad(mesa.getCapacidad());
        
        // Si el administrador desea cambiar el estado manualmente (ej. de Ocupada a Libre por error del mozo)
        if (mesa.getEstado() != null) {
            mesaExistente.setEstado(mesa.getEstado());
        }

        return mesaRepository.save(mesaExistente);
    }

    @Override
    @Transactional
    public void enviarAMantenimiento(Integer idMesa) {
        Mesa mesaExistente = mesaRepository.findById(idMesa)
                .orElseThrow(() -> new RuntimeException("No se encontró la Mesa con ID: " + idMesa));

        // Validación opcional: ¿Se puede mandar a mantenimiento una mesa ocupada? 
        if (mesaExistente.getEstado() == EstadoMesa.OCUPADA || mesaExistente.getEstado() == EstadoMesa.POR_PAGAR) {
            throw new RuntimeException("No se puede enviar a mantenimiento una mesa que está actualmente en uso o por pagar.");
        }

        mesaExistente.setEstado(EstadoMesa.MANTENIMIENTO);
        mesaRepository.save(mesaExistente);
    }
}