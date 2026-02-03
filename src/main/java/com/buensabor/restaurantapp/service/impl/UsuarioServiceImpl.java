package com.buensabor.restaurantapp.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.buensabor.restaurantapp.entity.Usuario;
import com.buensabor.restaurantapp.enums.EstadoUsuario;
import com.buensabor.restaurantapp.repository.UsuarioRepository;
import com.buensabor.restaurantapp.service.UsuarioService;


@Service
public class UsuarioServiceImpl implements UserDetailsService, UsuarioService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    UsuarioServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    
    // No necesitas inyectar PasswordEncoder aquí, ya que Spring Security lo gestiona
    // Puedes inyectarlo en tu servicio de registro de usuarios, pero no en este método de carga.

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        // Buscar al usuario por su correo electrónico
        Usuario usuario = usuarioRepository.findByCorreo(correo)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + correo));

        // Obtener el rol del usuario
        String rol = usuario.getRol().getNombre().toUpperCase(); 
        String rolConPrefijo = "ROLE_" + rol; 

        // Verificar si el usuario está activo
        boolean estaActivo = EstadoUsuario.activo.name().equalsIgnoreCase(usuario.getEstado().name());

        // Crear y devolver el objeto UserDetails con las credenciales y roles
        // Spring Security usará el PasswordEncoder para comparar la contraseña del formulario
        // con la que tú le devuelves aquí.
        return new User(
                usuario.getCorreo(),                 // nombre de usuario (login)
                usuario.getContrasena(),             // contraseña (cifrada)
                estaActivo,                          // si está habilitado o no
                true, true, true,                    // cuenta no expirada, no bloqueada, credenciales válidas
                Collections.singletonList(
                    new SimpleGrantedAuthority(rolConPrefijo)) // lista de roles del usuario
        );
    }

	@Override
	public Usuario buscarByUsuario(String correo) {
		return usuarioRepository.findByCorreo(correo).orElse(null);
	}

    @Override
    public Usuario registrar(Usuario usuario) {
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new RuntimeException("Correo ya registrado");
        }
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario buscarPorId(Integer id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Override
    public void eliminar(Integer id) {
    	usuarioRepository.deleteById(id);
    }
}
