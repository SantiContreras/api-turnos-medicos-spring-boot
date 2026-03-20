package com.TurnosMedicos.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TurnosMedicos.Dto.OrganizacionRequest;
import com.TurnosMedicos.Repository.OrganizacionRepository;
import com.TurnosMedicos.models.Organizacion;

@RestController
@RequestMapping("/admin")
public class OrganizacionController {

	private final OrganizacionRepository organizacionRepository;
	

    public OrganizacionController(OrganizacionRepository organizacionRepository) {
        this.organizacionRepository = organizacionRepository;
    }
    
    @PostMapping("/organizaciones")
    @PreAuthorize("hasRole('ADMIN_GLOBAL')")
    public Organizacion CrearOrganizacion(@RequestBody OrganizacionRequest request) {
    	
    	Organizacion org = new Organizacion();
    	org.setNombre(request.getNombre());
    	org.setActiva(true);
    	return organizacionRepository.save(org);
    }
	
}
