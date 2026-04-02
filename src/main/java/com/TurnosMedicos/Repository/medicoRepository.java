package com.TurnosMedicos.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TurnosMedicos.models.Medico;


public interface medicoRepository extends JpaRepository<Medico, Long> {
	
	
}
