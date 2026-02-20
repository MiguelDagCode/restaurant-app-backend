package com.buensabor.restaurantapp.service.impl;

import com.buensabor.restaurantapp.dto.request.UsuarioCreateDto;
import com.buensabor.restaurantapp.dto.request.UsuarioUpdateDto;
import com.buensabor.restaurantapp.dto.response.UsuarioListResponseDto;
import com.buensabor.restaurantapp.dto.response.UsuarioResponseDto;
import com.buensabor.restaurantapp.model.Rol;
import com.buensabor.restaurantapp.model.Usuario;
import com.buensabor.restaurantapp.repository.RolRepository;
import com.buensabor.restaurantapp.repository.UsuarioRepository;
import com.buensabor.restaurantapp.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

 // --- ENTIDAD COMPLETA ---
    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    @Override
    @Transactional
    public Usuario registrar(Usuario usuario) {
        // 1. Validar correo único
        if (usuarioRepository.findByCorreo(usuario.getCorreo()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }

        // 2. Validar y Asignar Rol Real
        // El objeto 'usuario' trae un Rol "parcial" (solo ID) desde el Controller.
        // Buscamos el Rol completo en la BD.
        Rol rolDb = rolRepository.findById(usuario.getRol().getIdRol())
                .orElseThrow(() -> new RuntimeException("El rol seleccionado no existe"));
        usuario.setRol(rolDb);

        // 3. Encriptar Contraseña (OBLIGATORIO AL CREAR)
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // 4. Activar por defecto
        usuario.setEstado(true);

        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public Usuario actualizar(Integer id, Usuario usuarioDatosNuevos) {
        // 1. Buscar usuario existente
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2. Validar cambio de correo
        // Si el correo nuevo es diferente al actual, verificar que no esté usado por otro
        if (!usuarioExistente.getCorreo().equals(usuarioDatosNuevos.getCorreo()) &&
            usuarioRepository.findByCorreo(usuarioDatosNuevos.getCorreo()).isPresent()) {
            throw new RuntimeException("El nuevo correo ya está en uso por otro usuario");
        }

        // 3. Actualizar datos básicos
        usuarioExistente.setNombre(usuarioDatosNuevos.getNombre());
        usuarioExistente.setApellido(usuarioDatosNuevos.getApellido());
        usuarioExistente.setCorreo(usuarioDatosNuevos.getCorreo());
        usuarioExistente.setTelefono(usuarioDatosNuevos.getTelefono());

        // 4. Actualizar Rol (Si cambió)
        if (!usuarioExistente.getRol().getIdRol().equals(usuarioDatosNuevos.getRol().getIdRol())) {
            Rol nuevoRol = rolRepository.findById(usuarioDatosNuevos.getRol().getIdRol())
                    .orElseThrow(() -> new RuntimeException("El nuevo rol no existe"));
            usuarioExistente.setRol(nuevoRol);
        }

        // 5. Actualizar Contraseña (OPCIONAL)
        // Si el campo password viene con texto, lo actualizamos. Si viene null/vacío, lo ignoramos.
        if (usuarioDatosNuevos.getPassword() != null && !usuarioDatosNuevos.getPassword().isBlank()) {
            usuarioExistente.setPassword(passwordEncoder.encode(usuarioDatosNuevos.getPassword()));
        }

        return usuarioRepository.save(usuarioExistente);
    }

    @Override
    @Transactional
    public void cambiarEstado(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        // Toggle: Si es true -> false, Si es false -> true
        usuario.setEstado(!usuario.getEstado());
        
        usuarioRepository.save(usuario);
    }
    
    // --- USO DTO ---
 // --- LISTADO PARA TABLA ---
    @Override
    @Transactional(readOnly = true)
    public List<UsuarioListResponseDto> listarTodosDto() {
        return usuarioRepository.findAll().stream()
                .map(u -> UsuarioListResponseDto.builder()
                        .idUsuario(u.getIdUsuario())
                        .nombresCompletos(u.getNombre() + " " + u.getApellido())
                        .correo(u.getCorreo())
                        .nombreRol(u.getRol().getNombreRol())
                        .telefono(u.getTelefono())
                        .estado(u.getEstado())
                        .build()
                ).collect(Collectors.toList());
    }

    // --- DETALLE PARA FORMULARIO ---
    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioResponseDto> buscarPorIdDto(Integer id) {
        return usuarioRepository.findById(id)
                .map(u -> UsuarioResponseDto.builder()
                        .idUsuario(u.getIdUsuario())
                        .nombre(u.getNombre())
                        .apellido(u.getApellido())
                        .correo(u.getCorreo())
                        .telefono(u.getTelefono())
                        .idRol(u.getRol().getIdRol()) // Importante para el frontend
                        .estado(u.getEstado())
                        .build()
                );
    }

    // --- CREAR ---
    @Override
    @Transactional
    public UsuarioResponseDto registrarDto(UsuarioCreateDto dto) {
        if (usuarioRepository.findByCorreo(dto.getCorreo()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }

        Rol rol = rolRepository.findById(dto.getIdRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        Usuario usuario = Usuario.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .correo(dto.getCorreo())
                .telefono(dto.getTelefono())
                .password(passwordEncoder.encode(dto.getPassword()))
                .rol(rol)
                .estado(true)
                .build();

        Usuario guardado = usuarioRepository.save(usuario);
        // Retornamos el DTO de respuesta reutilizando lógica si quieres, o manual
        return buscarPorIdDto(guardado.getIdUsuario()).get();
    }

    // --- ACTUALIZAR ---
    @Override
    @Transactional
    public UsuarioResponseDto actualizarDto(Integer id, UsuarioUpdateDto dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Validar correo único solo si cambió
        if (dto.getCorreo() != null && !usuario.getCorreo().equals(dto.getCorreo())) {
            if (usuarioRepository.findByCorreo(dto.getCorreo()).isPresent()) {
                throw new RuntimeException("El correo ya está en uso");
            }
            usuario.setCorreo(dto.getCorreo());
        }

        if (dto.getNombre() != null) usuario.setNombre(dto.getNombre());
        if (dto.getApellido() != null) usuario.setApellido(dto.getApellido());
        if (dto.getTelefono() != null) usuario.setTelefono(dto.getTelefono());

        // Actualizar Rol
        if (dto.getIdRol() != null && !dto.getIdRol().equals(usuario.getRol().getIdRol())) {
            Rol nuevoRol = rolRepository.findById(dto.getIdRol())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
            usuario.setRol(nuevoRol);
        }

        // Password Opcional
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        Usuario actualizado = usuarioRepository.save(usuario);
        return buscarPorIdDto(actualizado.getIdUsuario()).get();
    }

}