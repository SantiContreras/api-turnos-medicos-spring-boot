package com.TurnosMedicos.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TurnosMedicos.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario , Long> {

	Optional<Usuario> findByUsername(String username);
	List<Usuario> findByOrganizacionId(Long organizacionId);
}
