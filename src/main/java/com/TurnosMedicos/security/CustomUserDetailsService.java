package com.TurnosMedicos.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.TurnosMedicos.Repository.UsuarioRepository;
import com.TurnosMedicos.models.Usuario;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	
	private final UsuarioRepository usuarioRepository;
	
	public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
	
	@Override
	public UserDetails loadUserByUsername(String username)
	        throws UsernameNotFoundException {

		  Usuario usuario = usuarioRepository.findByUsername(username)
		            .orElseThrow(() ->
		                    new UsernameNotFoundException("Usuario no encontrado"));

		    // 👇 DEBUG TEMPORAL
		    System.out.println("USERNAME BD: " + usuario.getUsername());
		    System.out.println("PASSWORD BD: " + usuario.getPassword());
		    System.out.println("ROL BD: " + usuario.getRol());

		    return User.builder()
		            .username(usuario.getUsername())
		            .password(usuario.getPassword())
		            .roles(usuario.getRol())   // ojo con esto, después lo vemos
		            .build();
	}


	
	

}
