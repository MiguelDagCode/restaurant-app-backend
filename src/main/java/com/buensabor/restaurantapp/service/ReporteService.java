package com.buensabor.restaurantapp.service;

import java.util.List;

import com.buensabor.restaurantapp.dto.CategoriaMasVendidaDTO;
import com.buensabor.restaurantapp.dto.PlatoMasVendidoDTO;

public interface ReporteService {
	List<CategoriaMasVendidaDTO> listarCategoriasMasVendidas();
	
	List<PlatoMasVendidoDTO> listarPlatosMasVendidosPorCategoria(Integer categoriaId);
}
