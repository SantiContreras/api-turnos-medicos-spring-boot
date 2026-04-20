package com.TurnosMedicos.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.TurnosMedicos.models.EstadoTurno;
import com.TurnosMedicos.models.Turno;


public interface turnoRepository extends JpaRepository<Turno, Long> ,JpaSpecificationExecutor<Turno> {

	List<Turno> findByFecha(LocalDate fecha);

	List<Turno> findByMedicoId(Long medicoId);

	boolean existsByMedicoIdAndFechaAndHora(

			Long medicoId, LocalDate fecha, LocalTime hora

	);

	boolean existsByMedicoIdAndFechaAndHoraAndEstadoNot(Long medicoId, LocalDate fecha, LocalTime hora,
			EstadoTurno estado);

	Page<Turno> findByMedicoId(Long medicoId, Pageable pageable);

	Page<Turno> findByFecha(LocalDate fecha, Pageable pageable);

	Page<Turno> findByEstado(EstadoTurno estado, Pageable pageable);
	
	//cuando haga la query en turno medico tambien va a traer el medico paciente y especialidad, en vez de hacer varios Select.Aora los agrupa en joins.
	@Override
    @EntityGraph(attributePaths = {
            "medico",
            "medico.especialidad", 
            "paciente"
    })
	Page<Turno> findAll(Specification<Turno> spec, Pageable pageable);
	
	Page<Turno> findByOrganizacionId(Long orgId, Pageable pageable);
	
	List<Turno> findByMedicoIdAndFecha(Long medicoId , LocalDate fecha);
	
	List<Turno> findByMedicoIdAndFechaAndOrganizacionId( Long medicoId, LocalDate fecha,Long orgId);

}
