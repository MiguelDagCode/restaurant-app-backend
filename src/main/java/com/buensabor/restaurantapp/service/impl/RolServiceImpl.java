package com.buensabor.restaurantapp.service.impl;

import com.buensabor.restaurantapp.model.Rol;
import com.buensabor.restaurantapp.repository.RolRepository;
import com.buensabor.restaurantapp.service.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Rol> listarTodos() {
        return rolRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Rol> buscarPorId(Integer id) {
        return rolRepository.findById(id);
    }

    @Override
    @Transactional
    public Rol guardar(Rol rol) {
        if (rolRepository.findByNombreRol(rol.getNombreRol()).isPresent()) {
            throw new RuntimeException("El nombre del rol ya existe");
        }
        rol.setActivo(true); // Siempre nace activo
        return rolRepository.save(rol);
    }

    @Override
    @Transactional
    public Rol actualizar(Integer id, Rol rolDatosNuevos) {
        // 1. Buscamos el rol antiguo en la BD
        Rol rolExistente = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        // 2. Validamos nombre duplicado (solo si el nombre cambió)
        if (!rolExistente.getNombreRol().equals(rolDatosNuevos.getNombreRol()) &&
            rolRepository.findByNombreRol(rolDatosNuevos.getNombreRol()).isPresent()) {
            throw new RuntimeException("El nombre del rol ya está en uso");
        }

        // 3. Actualizamos SOLO los campos permitidos
        // No tocamos el ID ni el estado activo/inactivo aquí
        rolExistente.setNombreRol(rolDatosNuevos.getNombreRol());

        return rolRepository.save(rolExistente);
    }

    @Override
    @Transactional
    public void cambiarEstado(Integer id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        rol.setActivo(!rol.getActivo()); // Toggle
        rolRepository.save(rol);
    }
}