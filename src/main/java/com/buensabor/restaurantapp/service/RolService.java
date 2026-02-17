package com.buensabor.restaurantapp.service;

import java.util.Optional;

import com.buensabor.restaurantapp.model.Rol;

public interface RolService {
    Optional<Rol> buscarPorId(Integer id);
}