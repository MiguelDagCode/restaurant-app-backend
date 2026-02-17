package com.buensabor.restaurantapp.controller;

import com.buensabor.restaurantapp.dto.request.LoginRequest;
import com.buensabor.restaurantapp.dto.request.RegisterRequest;
import com.buensabor.restaurantapp.dto.response.AuthResponse;
import com.buensabor.restaurantapp.model.Rol;
import com.buensabor.restaurantapp.model.Usuario;
import com.buensabor.restaurantapp.security.jwt.JwtService;
import com.buensabor.restaurantapp.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        // 1. Mapeo Manual (O usar MapStruct)
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setCorreo(request.getCorreo());
        usuario.setTelefono(request.getTelefono());
        usuario.setPassword(request.getPassword()); // Se encriptará en el servicio
        
        // Creamos un rol temporal solo con el ID para pasarlo al servicio
        Rol rolAux = new Rol();
        rolAux.setIdRol(request.getIdRol());
        usuario.setRol(rolAux);

        // 2. Lógica de Negocio
        Usuario usuarioCreado = usuarioService.registrar(usuario);

        // 3. Generar Token (Para que quede logueado automáticamente al registrarse)
        String token = generarTokenConClaims(usuarioCreado);

        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        // 1. Autenticar (Spring Security)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getPassword())
        );

        // 2. Buscar Usuario (Ya sabemos que existe si pasó la línea anterior)
        Usuario usuario = usuarioService.buscarPorCorreo(request.getCorreo()).orElseThrow();

        // 3. Generar Token con Claims
        String token = generarTokenConClaims(usuario);

        return ResponseEntity.ok(new AuthResponse(token));
    }

    // Método auxiliar para no repetir código
    private String generarTokenConClaims(Usuario usuario) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("idUsuario", usuario.getIdUsuario());
        extraClaims.put("nombre", usuario.getNombre() + " " + usuario.getApellido());
        extraClaims.put("rol", usuario.getRol().getNombreRol());
        // Agregamos permisos o cualquier otra data necesaria para el Front
        
        return jwtService.generateToken(extraClaims, usuario);
    }
}