package com.TurnosMedicos.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TurnosMedicos.models.Disponibilidad;

public interface DisponibilidadRepository extends JpaRepository<Disponibilidad, Long>{

	 List<Disponibilidad> findByMedicoIdAndOrganizacionId(Long medicoId, Long orgId);
}
