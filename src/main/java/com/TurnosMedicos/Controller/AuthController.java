package com.TurnosMedicos.Controller;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.TurnosMedicos.Dto.AuthResponse;

import com.TurnosMedicos.Dto.AuthRequest;
import com.TurnosMedicos.security.jwt.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;

	public AuthController(AuthenticationManager authenticatinManager, JwtService jwtService) {
		this.authenticationManager = authenticatinManager;
		this.jwtService = jwtService;

	}

	@PostMapping("/login")
	public AuthResponse login(@RequestBody AuthRequest request) {
		
		 System.out.println("USER REQUEST: " + request.getUsername());
		 System.out.println("PASS REQUEST: " + request.getPassword());
		 
		 Authentication auth = authenticationManager.authenticate(
		            new UsernamePasswordAuthenticationToken(
		                    request.getUsername(),
		                    request.getPassword()
		            )
		    );

		    UserDetails userDetails = (UserDetails) auth.getPrincipal();

		    String token = jwtService.generatedToken(userDetails);

		    String role = userDetails.getAuthorities()
		            .iterator()
		            .next()
		            .getAuthority();

		    return new AuthResponse(
		            token,
		            userDetails.getUsername(),
		            role
		    );
	}

}
