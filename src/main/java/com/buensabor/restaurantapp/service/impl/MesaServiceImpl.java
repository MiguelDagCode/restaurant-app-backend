package com.buensabor.restaurantapp.service.impl;

import com.buensabor.restaurantapp.entity.Mesa;
import com.buensabor.restaurantapp.enums.EstadoMesa;
import com.buensabor.restaurantapp.repository.MesaRepository;
import com.buensabor.restaurantapp.service.MesaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class MesaServiceImpl implements MesaService {

    private final MesaRepository repository;

    public MesaServiceImpl(MesaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Mesa> listarTodas() {
        return repository.findAll();
    }

    @Override
    public Mesa buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Mesa no encontrada"));
    }

    @Override
    public Mesa registrar(Mesa mesa) {
        mesa.setEstado(EstadoMesa.LIBRE);
        return repository.save(mesa);
    }

    @Override
    public Mesa cambiarEstado(Integer id, EstadoMesa estado) {
        Mesa m = buscarPorId(id);
        m.setEstado(estado);
        return repository.save(m);
    }

    @Override
    public Mesa actualizar(Integer id, Mesa mesa) {
        Mesa existente = buscarPorId(id);
        existente.setNumero(mesa.getNumero());
        existente.setEstado(mesa.getEstado());
        return repository.save(existente);
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}