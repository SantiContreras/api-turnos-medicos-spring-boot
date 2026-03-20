package com.TurnosMedicos.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TurnosMedicos.models.Organizacion;

public interface OrganizacionRepository extends JpaRepository<Organizacion, Long> {

	
}
