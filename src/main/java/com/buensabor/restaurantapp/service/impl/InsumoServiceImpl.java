package com.buensabor.restaurantapp.service.impl;

import com.buensabor.restaurantapp.model.Insumo;
import com.buensabor.restaurantapp.repository.InsumoRepository;
import com.buensabor.restaurantapp.service.InsumoService;
import com.buensabor.restaurantapp.util.BusinessException;
import com.buensabor.restaurantapp.util.ModeloNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InsumoServiceImpl implements InsumoService {

    private final InsumoRepository insumoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Insumo> findAll() {
        return insumoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Insumo> findById(Long idInsumo) {
        return insumoRepository.findById(idInsumo);
    }

    @Override
    @Transactional
    public Insumo insert(Insumo insumo) {
        String nombreFormateado = insumo.getNombre().trim().toUpperCase();

        if (insumoRepository.existsByNombreIgnoreCase(nombreFormateado)) {
            throw new BusinessException("El insumo '" + nombreFormateado + "' ya se encuentra registrado.");
        }

        insumo.setNombre(nombreFormateado);
        insumo.setUnidadMedida(insumo.getUnidadMedida().trim().toUpperCase());
        
        // Regla de negocio: Si no envían stock actual, inicia en 0
        if (insumo.getStockActual() == null) {
            insumo.setStockActual(BigDecimal.ZERO);
        }

        return insumoRepository.save(insumo);
    }

    @Override
    @Transactional
    public Insumo update(Long idInsumo, Insumo insumo) {
        Insumo insumoExistente = insumoRepository.findById(idInsumo)
                .orElseThrow(() -> new ModeloNotFoundException("No se encontró el Insumo con ID: " + idInsumo));

        String nombreFormateado = insumo.getNombre().trim().toUpperCase();

        // Validar que el nuevo nombre no le pertenezca a OTRO insumo diferente
        Optional<Insumo> insumoDuplicado = insumoRepository.findByNombreIgnoreCase(nombreFormateado);
        if (insumoDuplicado.isPresent() && !insumoDuplicado.get().getIdInsumo().equals(idInsumo)) {
            throw new BusinessException("El nombre '" + nombreFormateado + "' ya está en uso por otro insumo.");
        }

        insumoExistente.setNombre(nombreFormateado);
        insumoExistente.setStockMinimo(insumo.getStockMinimo());
        insumoExistente.setUnidadMedida(insumo.getUnidadMedida().trim().toUpperCase());
        
        // Nota: El stock_actual normalmente NO se actualiza por aquí directamente, 
        // sino mediante ingresos (compras) o egresos (ventas/mermas). 
        // Si quieres permitir actualizarlo manualmente por corrección de inventario:
        if (insumo.getStockActual() != null) {
            insumoExistente.setStockActual(insumo.getStockActual());
        }

        return insumoRepository.save(insumoExistente);
    }
}