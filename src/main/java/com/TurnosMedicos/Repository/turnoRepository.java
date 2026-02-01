package com.TurnosMedicos.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.TurnosMedicos.models.EstadoTurno;
import com.TurnosMedicos.models.turno;

public interface turnoRepository extends JpaRepository<turno, Long> {

	List<turno> findByFecha(LocalDate fecha);

	List<turno> findByMedicoId(Long medicoId);

	boolean existsByMedicoIdAndFechaAndHora(

			Long medicoId, LocalDate fecha, LocalTime hora

	);

	boolean existsByMedicoIdAndFechaAndHoraAndEstadoNot(Long medicoId, LocalDate fecha, LocalTime hora,
			EstadoTurno estado);

	Page<turno> findByMedicoId(Long medicoId, Pageable pageable);

	Page<turno> findByFecha(LocalDate fecha, Pageable pageable);

	Page<turno> findByEstado(EstadoTurno estado, Pageable pageable);
	
	

}
