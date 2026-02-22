package com.buensabor.restaurantapp.service.impl;

import com.buensabor.restaurantapp.dto.request.RecetaRequest;
import com.buensabor.restaurantapp.dto.response.RecetaResponse;
import com.buensabor.restaurantapp.model.Insumo;
import com.buensabor.restaurantapp.model.Producto;
import com.buensabor.restaurantapp.model.Receta;
import com.buensabor.restaurantapp.model.RecetaId;
import com.buensabor.restaurantapp.repository.InsumoRepository;
import com.buensabor.restaurantapp.repository.ProductoRepository;
import com.buensabor.restaurantapp.repository.RecetaRepository;
import com.buensabor.restaurantapp.service.RecetaService;
import com.buensabor.restaurantapp.util.BusinessException;
import com.buensabor.restaurantapp.util.ModeloNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecetaServiceImpl implements RecetaService {

    private final RecetaRepository recetaRepository;
    private final ProductoRepository productoRepository;
    private final InsumoRepository insumoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<RecetaResponse> findAll() {
        return recetaRepository.findAll().stream() 
        		.map(this::mapearADto)
        		.collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecetaResponse> findByProducto(Long idProducto) {
        return recetaRepository.findByProducto_IdProducto(idProducto).stream()
        		.map(this::mapearADto)
        		.collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RecetaResponse> findById(Long idProducto, Long idInsumo) {
        return recetaRepository.findById(new RecetaId(idProducto, idInsumo))
        		.map(this::mapearADto);
    }

    @Override
    @Transactional
    public RecetaResponse insert(RecetaRequest request) {
        RecetaId recetaId = new RecetaId(request.getIdProducto(), request.getIdInsumo());

        // Validar que no exista ya este insumo en este producto
        if (recetaRepository.existsById(recetaId)) {
            throw new BusinessException("Este insumo ya forma parte de la receta de este producto.");
        }

        // Validar que existan las entidades foráneas
        Producto producto = productoRepository.findById(request.getIdProducto())
                .orElseThrow(() -> new ModeloNotFoundException("El producto con ID " + request.getIdProducto() + " no existe."));
        
        Insumo insumo = insumoRepository.findById(request.getIdInsumo())
                .orElseThrow(() -> new ModeloNotFoundException("El insumo con ID " + request.getIdInsumo() + " no existe."));

        Receta receta = Receta.builder()
                .id(recetaId)
                .producto(producto)
                .insumo(insumo)
                .cantidadInsumo(request.getCantidadInsumo())
                .activo(true)
                .build();

        recetaRepository.save(receta);
        return mapearADto(receta);
    }

    @Override
    @Transactional
    public RecetaResponse update(RecetaRequest receta) {
        RecetaId recetaId = new RecetaId(receta.getIdProducto(), receta.getIdInsumo());

        Receta recetaExistente = recetaRepository.findById(recetaId)
                .orElseThrow(() -> new ModeloNotFoundException("No se encontró la relación en la receta."));

        // En un update de tabla intermedia, normalmente solo se cambia la cantidad o el estado
        recetaExistente.setCantidadInsumo(receta.getCantidadInsumo());

        recetaRepository.save(recetaExistente);
        return mapearADto(recetaExistente);
    }
    
    private RecetaResponse mapearADto(Receta receta) {
        return RecetaResponse.builder()
                .idProducto(receta.getProducto().getIdProducto())
                .nombreProducto(receta.getProducto().getNombre())
                .categoria(receta.getProducto().getCategoria().getNombre()) // Si Categoría es LAZY, esto la carga
                .idInsumo(receta.getInsumo().getIdInsumo())
                .nombreInsumo(receta.getInsumo().getNombre())
                .stockActual(receta.getInsumo().getStockActual())
                .unidadMedida(receta.getInsumo().getUnidadMedida())
                .cantidadInsumo(receta.getCantidadInsumo())
                .build();
    }

}