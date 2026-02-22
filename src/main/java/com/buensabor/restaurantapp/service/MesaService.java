package com.buensabor.restaurantapp.service;

import com.buensabor.restaurantapp.model.Mesa;

import java.util.List;
import java.util.Optional;

public interface MesaService {
    List<Mesa> findAll();
    Optional<Mesa> findById(Integer idMesa);
    Mesa insert(Mesa mesa);
    Mesa update(Integer idMesa, Mesa mesa);
    void enviarAMantenimiento(Integer idMesa); // Tu eliminar lógico
}