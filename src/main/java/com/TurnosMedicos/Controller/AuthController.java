package com.TurnosMedicos.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
	public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {

		try {
			System.out.println("USER REQUEST: " + request.getUsername());
			System.out.println("PASS REQUEST: " + request.getPassword());

			Authentication auth = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

			Usuario usuario = usuarioRepository.findByUsername(request.getUsername()).orElseThrow();

			UserDetails userDetails = (UserDetails) auth.getPrincipal();
			Long organizacionId = null;

			if (usuario.getOrganizacion() != null) {
				organizacionId = usuario.getOrganizacion().getId();
			}

			String token = jwtService.generatedToken(userDetails, organizacionId);
			String role = userDetails.getAuthorities().iterator().next().getAuthority();

			return ResponseEntity.ok(new AuthResponse(token, userDetails.getUsername(), role));
		} catch (BadCredentialsException e) {

			return ResponseEntity.status(401).body(new AuthResponse("usuario o contraseña incorrecta"));
		}

		catch (Exception e) {
			return ResponseEntity.status(500).body(new AuthResponse("Error dinterno del servidor"));
		}
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
