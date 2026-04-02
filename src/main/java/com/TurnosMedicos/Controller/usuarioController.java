package com.TurnosMedicos.Controller;

import java.net.PasswordAuthentication;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TurnosMedicos.Dto.CambiarRolRequest;
import com.TurnosMedicos.Dto.usuarioRequest;
import com.TurnosMedicos.Dto.usuarioResponse;
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
	        user.setActivo(true);
	        
	    // aca creamos la organizacion automaticamente   
	    Organizacion org = new Organizacion();
        org.setId(orgId);
        user.setOrganizacion(org);

        return usuarioRepository.save(user);
		
	}
	
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public List<usuarioResponse> listarUsuarios(HttpServletRequest request){
		
		String token =  request.getHeader("Authorization").substring(7);
		
		Long idOrg = jwtService.extractOrganizacion(token);
		
	    List<Usuario> ListaUsuarios = usuarioRepository.findByOrganizacionId(idOrg);
	    
	    return ListaUsuarios.stream()
	    		.map(u-> new usuarioResponse(u.getId(), u.getUsername(), u.getRol()
	    				
	    				))
	    		.toList();
		
		
	}
	
	@PutMapping("/{id}/desactivar")
	@PreAuthorize("hasRole('ADMIN')")
	public String  DesactivarUsuario(@PathVariable Long id , HttpServletRequest request) {
		
		String token = request.getHeader("Authorization").substring(7);
		Long idOrg = jwtService.extractOrganizacion(token);
		
		Usuario user = usuarioRepository.findById(id).orElseThrow(()-> new RuntimeException("no se encontro el usuario"));
		
		if(!user.getOrganizacion().getId().equals(idOrg)) {
			throw new RuntimeException("no pertenece a una organizacion");
		}
		
		user.setActivo(false);
		usuarioRepository.save(user);
		
		return "El usuario se desactivo";
	}
	
	@PutMapping("/{id}/rol")
	@PreAuthorize("hasRole('ADMIN')")
	public String CambiarRol(@PathVariable Long id , HttpServletRequest HttpRequest ,@RequestBody CambiarRolRequest request) {
		
		String token = HttpRequest.getHeader("Authorization").substring(7);
		Long idOrg = jwtService.extractOrganizacion(token);
		
		Usuario user = usuarioRepository.findById(id).orElseThrow(()-> new RuntimeException("no se encontro el usuario"));
		

		if(!user.getOrganizacion().getId().equals(idOrg)) {
			throw new RuntimeException("no pertenece a una organizacion");
		}
		
	
		if (!request.getRol().equals("USER") && !request.getRol().equals("ADMIN")) {
		    throw new RuntimeException("Rol inválido");
		}
		
		user.setRol(request.getRol());
		usuarioRepository.save(user);
		
		return "El usuario cambio de Rol";
	}
	
	
}
