package com.buensabor.restaurantapp.service.impl;

import com.buensabor.restaurantapp.dto.response.CategoriaConProductosDto;
import com.buensabor.restaurantapp.dto.response.ProductoListResponseDto;
import com.buensabor.restaurantapp.model.Categoria;
import com.buensabor.restaurantapp.model.Producto;
import com.buensabor.restaurantapp.repository.CategoriaRepository;
import com.buensabor.restaurantapp.repository.ProductoRepository;
import com.buensabor.restaurantapp.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;


    @Override
    @Transactional(readOnly = true)
    public Optional<ProductoListResponseDto> buscarPorIdDto(Long id) {
        return productoRepository.findById(id)
                .map(p -> ProductoListResponseDto.builder()
                        .idProducto(p.getIdProducto())
                        .nombre(p.getNombre())
                        .precio(p.getPrecio())
                        .imgUrl(p.getImgUrl())
                        .estado(p.getEstado())
                        .idCategoria(p.getCategoria().getIdCategoria())   // ahora incluimos el id
                        .categoriaNombre(p.getCategoria().getNombre())
                        .build()
                );
    }


    @Override
    @Transactional
    public Producto registrar(Producto producto) {
        // Validar categoría existente
        Categoria categoria = categoriaRepository.findById(producto.getCategoria().getIdCategoria())
                .orElseThrow(() -> new RuntimeException("La categoría seleccionada no existe"));
        producto.setCategoria(categoria);

        producto.setEstado(true); // Activo por defecto
        return productoRepository.save(producto);
    }

    @Override
    @Transactional
    public Producto actualizar(Long id, Producto productoDatos) {
        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        productoExistente.setNombre(productoDatos.getNombre());
        productoExistente.setPrecio(productoDatos.getPrecio());
        productoExistente.setImgUrl(productoDatos.getImgUrl());

        // Validar cambio de categoría
        if (!productoExistente.getCategoria().getIdCategoria().equals(productoDatos.getCategoria().getIdCategoria())) {
            Categoria nuevaCategoria = categoriaRepository.findById(productoDatos.getCategoria().getIdCategoria())
                    .orElseThrow(() -> new RuntimeException("La nueva categoría no existe"));
            productoExistente.setCategoria(nuevaCategoria);
        }

        return productoRepository.save(productoExistente);
    }

    @Override
    @Transactional
    public void cambiarEstado(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setEstado(!producto.getEstado()); // Toggle activo/inactivo
        productoRepository.save(producto);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ProductoListResponseDto> listarTodosDto() {
        return productoRepository.findAll().stream()
                .map(p -> ProductoListResponseDto.builder()
                        .idProducto(p.getIdProducto())
                        .nombre(p.getNombre())
                        .precio(p.getPrecio())
                        .imgUrl(p.getImgUrl())
                        .estado(p.getEstado())
                        .categoriaNombre(p.getCategoria().getNombre())
                        .build()
                ).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoListResponseDto> listarActivosDto() {
        return productoRepository.findByEstadoTrue().stream()
                .map(p -> ProductoListResponseDto.builder()
                        .idProducto(p.getIdProducto())
                        .nombre(p.getNombre())
                        .precio(p.getPrecio())
                        .imgUrl(p.getImgUrl())
                        .estado(p.getEstado())
                        .categoriaNombre(p.getCategoria().getNombre())
                        .build()
                ).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoListResponseDto> listarPorCategoria(Integer idCategoria) {
        return productoRepository.findByCategoriaIdCategoriaAndEstadoTrue(idCategoria).stream()
                .map(p -> ProductoListResponseDto.builder()
                        .idProducto(p.getIdProducto())
                        .nombre(p.getNombre())
                        .precio(p.getPrecio())
                        .imgUrl(p.getImgUrl())
                        .estado(p.getEstado())
                        .idCategoria(p.getCategoria().getIdCategoria())
                        .categoriaNombre(p.getCategoria().getNombre())
                        .build()
                ).toList();
    }


	@Override
	public List<Producto> findByCategoriaIdCategoriaAndEstadoTrue(Integer idCategoria) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	@Transactional(readOnly = true)
	public List<CategoriaConProductosDto> listarCategoriasConProductosActivos() {
	    // Traemos solo categorías activas
	    List<Categoria> categoriasActivas = categoriaRepository.findByActivoTrue();

	    return categoriasActivas.stream()
	            .map(categoria -> {
	                // Traemos productos activos de esa categoría
	                List<ProductoListResponseDto> productosActivos = productoRepository
	                        .findByCategoriaIdCategoriaAndEstadoTrue(categoria.getIdCategoria())
	                        .stream()
	                        .map(p -> ProductoListResponseDto.builder()
	                                .idProducto(p.getIdProducto())
	                                .nombre(p.getNombre())
	                                .precio(p.getPrecio())
	                                .imgUrl(p.getImgUrl())
	                                .estado(p.getEstado())
	                                .idCategoria(categoria.getIdCategoria())
	                                .categoriaNombre(categoria.getNombre())
	                                .build()
	                        ).toList();

	                return CategoriaConProductosDto.builder()
	                        .idCategoria(categoria.getIdCategoria())
	                        .nombre(categoria.getNombre())
	                        .productos(productosActivos)
	                        .build();
	            })
	            .toList();
	}


}
