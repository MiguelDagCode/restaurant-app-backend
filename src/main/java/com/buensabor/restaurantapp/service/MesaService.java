package com.buensabor.restaurantapp.service;

import com.buensabor.restaurantapp.entity.Mesa;
import com.buensabor.restaurantapp.enums.EstadoMesa;
import java.util.List;

public interface MesaService {
    List<Mesa> listarTodas();
    Mesa buscarPorId(Integer id);
    Mesa registrar(Mesa mesa);
    Mesa cambiarEstado(Integer id, EstadoMesa estado);
    Mesa actualizar(Integer id, Mesa mesa);
    void eliminar(Integer id);
}
