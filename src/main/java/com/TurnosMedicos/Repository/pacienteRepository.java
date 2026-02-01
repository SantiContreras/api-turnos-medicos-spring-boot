package com.TurnosMedicos.Repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.TurnosMedicos.models.paciente;

public interface pacienteRepository extends JpaRepository<paciente, Long> {

	
}
