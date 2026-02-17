package com.buensabor.restaurantapp.service.impl;

import com.buensabor.restaurantapp.model.Rol;
import com.buensabor.restaurantapp.model.Usuario;
import com.buensabor.restaurantapp.repository.UsuarioRepository;
import com.buensabor.restaurantapp.service.RolService;
import com.buensabor.restaurantapp.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolService rolService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Usuario registrar(Usuario usuario) {
        // 1. Validaciones
        if (usuarioRepository.findByCorreo(usuario.getCorreo()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }

        // 2. Asignar Rol (Validando que exista)
        // Ojo: usuario.getRol().getIdRol() viene del mapeo en el Controller
        Rol rol = rolService.buscarPorId(usuario.getRol().getIdRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        usuario.setRol(rol);

        // 3. Encriptar Password
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        
        // 4. Setear estado por defecto
        usuario.setEstado(true);

        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }
}