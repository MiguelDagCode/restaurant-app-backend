package com.buensabor.restaurantapp.service.impl;

import com.buensabor.restaurantapp.entity.RolUsuario;
import com.buensabor.restaurantapp.repository.RolUsuarioRepository;
import com.buensabor.restaurantapp.service.RolUsuarioService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class RolUsuarioServiceImpl implements RolUsuarioService {

    private final RolUsuarioRepository repository;

    public RolUsuarioServiceImpl(RolUsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<RolUsuario> listarTodos() {
        return repository.findAll();
    }

    @Override
    public RolUsuario guardar(RolUsuario rol) {
        if (repository.existsByNombreIgnoreCase(rol.getNombre())) {
            throw new DataIntegrityViolationException("Rol ya existe");
        }
        return repository.save(rol);
    }

    @Override
    public RolUsuario actualizar(Integer id, RolUsuario rol) {
        RolUsuario existente = repository.findById(id).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        existente.setNombre(rol.getNombre());
        return repository.save(existente);
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}
