package com.buensabor.restaurantapp.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequest {
	
	@NotBlank
	@Email
    private String correo;
    
	@NotBlank
	private String contrasena;
}
