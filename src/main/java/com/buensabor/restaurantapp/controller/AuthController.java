package com.buensabor.restaurantapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buensabor.restaurantapp.dto.AuthRequest;
import com.buensabor.restaurantapp.dto.AuthResponse;
import com.buensabor.restaurantapp.entity.Usuario;
import com.buensabor.restaurantapp.security.JwtUtil;
import com.buensabor.restaurantapp.service.UsuarioService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UsuarioService usuarioService; 

 // Controlador principal para manejar la autenticación (login)
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody AuthRequest loginRequest) {
        // Autenticar al usuario
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getCorreo(), loginRequest.getContrasena())
        );

        //Busca el usuario para obtener datos adicionales (nombre, apellidos)
        Usuario usuario = usuarioService.buscarByUsuario(loginRequest.getCorreo());

        // Genera el JWT si la autenticación es exitosa
        String token = jwtUtil.generateToken(authentication, usuario); 
        
        //Devuelve el token en la respuesta
        return ResponseEntity.ok(new AuthResponse(token));
    }
    
}
