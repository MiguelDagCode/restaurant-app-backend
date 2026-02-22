package com.buensabor.restaurantapp.service;

import com.buensabor.restaurantapp.model.Insumo;

import java.util.List;
import java.util.Optional;

public interface InsumoService {
    List<Insumo> findAll();
    Optional<Insumo> findById(Long idInsumo);
    Insumo insert(Insumo insumo);
    Insumo update(Long idInsumo, Insumo insumo);
}