package com.buensabor.restaurantapp.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buensabor.restaurantapp.model.Rol;
import com.buensabor.restaurantapp.repository.RolRepository;
import com.buensabor.restaurantapp.service.RolService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {
    
    private final RolRepository rolRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Rol> buscarPorId(Integer id) {
        return rolRepository.findById(id);
    }
}