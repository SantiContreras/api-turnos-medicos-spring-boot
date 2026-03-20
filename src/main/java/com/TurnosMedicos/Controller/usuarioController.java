package com.TurnosMedicos.Controller;

import java.net.PasswordAuthentication;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TurnosMedicos.Dto.usuarioRequest;
import com.TurnosMedicos.Repository.UsuarioRepository;
import com.TurnosMedicos.models.Organizacion;
import com.TurnosMedicos.models.Usuario;
import com.TurnosMedicos.security.jwt.JwtService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/usuarios")
@PreAuthorize("hasRole('ADMIN')")
public class usuarioController {

	private final UsuarioRepository usuarioRepository;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEndoder;

	public usuarioController(UsuarioRepository usuarioRepository, JwtService jwtService,
			PasswordEncoder passwordEncoder) {
		this.usuarioRepository = usuarioRepository;
		this.jwtService = jwtService;
		this.passwordEndoder = passwordEncoder;
	}
	
	@PostMapping
	public Usuario crearUsuario(@RequestBody usuarioRequest request ,HttpServletRequest HttpRequest) {
		
		String token =  HttpRequest.getHeader("Authorization").substring(7);
		 Long orgId = jwtService.extractOrganizacion(token);
		 

	        Usuario user = new Usuario();
	        user.setUsername(request.getUsername());
	        user.setPassword(passwordEndoder.encode(request.getPassword()));
	        user.setRol(request.getRol());

	    // aca creamos la organizacion automaticamente   
	    Organizacion org = new Organizacion();
        org.setId(orgId);
        user.setOrganizacion(org);

        return usuarioRepository.save(user);
		
	}
	
	
}
