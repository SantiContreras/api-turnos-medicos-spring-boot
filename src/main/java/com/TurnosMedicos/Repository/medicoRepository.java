package com.TurnosMedicos.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TurnosMedicos.models.Medico;


public interface medicoRepository extends JpaRepository<Medico, Long> {
	
	//metodo para listar los medicos de una organizacion
	 List<Medico> findByOrganizacionId(Long organizacionId);
	 
	 
	 //metodo para buscar por nombre
	 Optional<Medico> findByNombre(String nombre);
	 
	 //metodo para buscar por Apellido 
	 Optional<Medico> findByApellido(String apellido);
	 
	 //metodo para listar medicos activos
	 List<Medico> findByActivoTrue();
	 
	//metodo para listar los medicos de una organizacion
	List<Medico> findByOrganizacionIdAndActivoTrue(Long organizacionId);
	 
	 
	 
}
