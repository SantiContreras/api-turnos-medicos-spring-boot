package com.TurnosMedicos.Repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.TurnosMedicos.models.Organizacion;
import com.TurnosMedicos.models.Turno;

public interface OrganizacionRepository extends JpaRepository<Organizacion, Long> {

	
	Optional<Organizacion> findByNombre(String nombre);
	
	// este metodo se crea para evitar duplicaciones 
	boolean existsByNombre(String nombre);
	
	//este metodo busca las organizaciones activas
	List<Organizacion> findByActivaTrue();
	
	//este metodo busca las organizaciones desactivadas
    List<Organizacion> findByActivaFalse();
	
    
    //este metodo busca las organizaciones por parametros ingresado por el usuario
    List<Organizacion> findByNombreContainingIgnoreCase(String nombre);
	
}
