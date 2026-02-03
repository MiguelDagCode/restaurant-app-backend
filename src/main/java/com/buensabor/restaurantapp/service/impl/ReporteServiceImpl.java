package com.buensabor.restaurantapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.buensabor.restaurantapp.dto.CategoriaMasVendidaDTO;
import com.buensabor.restaurantapp.dto.PlatoMasVendidoDTO;
import com.buensabor.restaurantapp.repository.ReporteRepository;
import com.buensabor.restaurantapp.service.ReporteService;

import jakarta.transaction.Transactional;

@Service
public class ReporteServiceImpl implements ReporteService{

    private final ReporteRepository reporteRepository;

    public ReporteServiceImpl(ReporteRepository reporteRepository) {
        this.reporteRepository = reporteRepository;
    } 
    
	@Override
	public List<CategoriaMasVendidaDTO> listarCategoriasMasVendidas() {
		// TODO Auto-generated method stub
		return reporteRepository.obtenerCategoriasMasVendidas();
	}

	@Override
	public List<PlatoMasVendidoDTO> listarPlatosMasVendidosPorCategoria(Integer categoriaId) {
		// TODO Auto-generated method stub
		return reporteRepository.obtenerPlatosMasVendidosPorCategoria(categoriaId);
	}

}
