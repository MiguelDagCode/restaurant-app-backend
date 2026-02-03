package com.buensabor.restaurantapp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buensabor.restaurantapp.dto.CategoriaMasVendidaDTO;
import com.buensabor.restaurantapp.dto.PlatoMasVendidoDTO;
import com.buensabor.restaurantapp.service.ReporteService;

@RequestMapping("/api/reportes")
@CrossOrigin(origins ="*")
@RestController
public class ReporteController {
    
	private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    // 🔹 Endpoint 1: categorías más vendidas
    @GetMapping("/categorias")
    public List<CategoriaMasVendidaDTO> getCategoriasMasVendidas() {
        return reporteService.listarCategoriasMasVendidas();
    }

    // 🔹 Endpoint 2: platos más vendidos dentro de una categoría
    @GetMapping("/categorias/{categoriaId}/platos")
    public List<PlatoMasVendidoDTO> getPlatosPorCategoria(@PathVariable Integer categoriaId) {
        return reporteService.listarPlatosMasVendidosPorCategoria(categoriaId);
    }
}
