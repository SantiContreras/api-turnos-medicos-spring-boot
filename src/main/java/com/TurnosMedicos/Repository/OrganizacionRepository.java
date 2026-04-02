package com.TurnosMedicos.Repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.TurnosMedicos.models.Organizacion;
import com.TurnosMedicos.models.Turno;

public interface OrganizacionRepository extends JpaRepository<Organizacion, Long> {

	
}
