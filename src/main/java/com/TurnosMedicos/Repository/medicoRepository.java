package com.TurnosMedicos.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.TurnosMedicos.models.medico;

public interface medicoRepository extends JpaRepository<medico, Long> {
	
	
}
