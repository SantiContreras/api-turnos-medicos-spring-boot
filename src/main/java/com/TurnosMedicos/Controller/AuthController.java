package com.TurnosMedicos.Controller;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.TurnosMedicos.Dto.AuthResponse;
import com.TurnosMedicos.Dto.RegisterRequest;
import com.TurnosMedicos.Repository.UsuarioRepository;
import com.TurnosMedicos.models.Usuario;
import com.TurnosMedicos.Dto.AuthRequest;
import com.TurnosMedicos.security.jwt.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;
	

	public AuthController(AuthenticationManager authenticatinManager, JwtService jwtService,
			UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		this.authenticationManager = authenticatinManager;
		this.jwtService = jwtService;
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;

	}

	@PostMapping("/login")
	public AuthResponse login(@RequestBody AuthRequest request) {

		System.out.println("USER REQUEST: " + request.getUsername());
		System.out.println("PASS REQUEST: " + request.getPassword());

		Authentication auth = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		Usuario usuario = usuarioRepository
		        .findByUsername(request.getUsername())
		        .orElseThrow();
		
		UserDetails userDetails = (UserDetails) auth.getPrincipal();

		String token = jwtService.generatedToken(userDetails, usuario.getOrganizacion().getId());

		String role = userDetails.getAuthorities().iterator().next().getAuthority();

		return new AuthResponse(token, userDetails.getUsername(), role);
	}

	@PostMapping("/register")
	public String register(@RequestBody RegisterRequest request) {
		
		Usuario usuario = new Usuario();
		usuario.setUsername(request.getUsername());
		usuario.setPassword(request.getPassword());
		usuario.setRol(request.getRole());
		
		usuarioRepository.save(usuario);
		
		return "El usuario se registro correctamente";
		
	}

}
