package com.buensabor.restaurantapp.service;

import com.buensabor.restaurantapp.entity.RolUsuario;
import java.util.List;

public interface RolUsuarioService {
    List<RolUsuario> listarTodos();
    RolUsuario guardar(RolUsuario rol);
    RolUsuario actualizar(Integer id, RolUsuario rol);
    void eliminar(Integer id);
}
