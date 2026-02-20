package com.buensabor.restaurantapp.service;

import com.buensabor.restaurantapp.model.Rol;
import java.util.List;
import java.util.Optional;

public interface RolService {
    List<Rol> listarTodos();
    Optional<Rol> buscarPorId(Integer id);
    Rol guardar(Rol rol); // Create
    Rol actualizar(Integer id, Rol rol); // Update (Mantenemos ID separado por seguridad)
    void cambiarEstado(Integer id); // Delete Lógico
}