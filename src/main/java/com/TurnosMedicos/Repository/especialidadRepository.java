package com.TurnosMedicos.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TurnosMedicos.models.especialidad;

public interface especialidadRepository extends JpaRepository<especialidad, Long>{
	
//buscamos la lista de especialidades solo activas
List<especialidad> findByActivoTrue();

}
