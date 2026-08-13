package com.TurnosMedicos.Mapper;

import org.springframework.stereotype.Component;

import com.TurnosMedicos.Dto.OrganizacionRequest;
import com.TurnosMedicos.Dto.OrganizacionResponse;
import com.TurnosMedicos.models.Organizacion;

@Component
public class OrganizacionMapper {

	/*
	 * Convierte un Request en una Entity 
	 * 
	 * */
	
	public static Organizacion toEntity(OrganizacionRequest request) {
		Organizacion org = new Organizacion();
		
		org.setNombre(request.getNombre());
		org.setTelefono(request.getTelefono());
		org.setDireccion(request.getDireccion());
		org.setEmail(request.getEmail());
		org.setLogo(request.getLogo());
		org.setCiudad(request.getCiudad());
		org.setProvincia(request.getProvincia());
		org.setPais(request.getPais());
		
		//creamos siempre con activa 
		
		org.setActiva(true);
		
		return org;
	}
	
	/**
     * Convierte una Entity en un ResponseDTO
     */
    public static OrganizacionResponse toResponse(Organizacion organizacion) {

        OrganizacionResponse response = new OrganizacionResponse();

        response.setId(organizacion.getId());
        response.setNombre(organizacion.getNombre());
        response.setDireccion(organizacion.getDireccion());
        response.setTelefono(organizacion.getTelefono());
        response.setEmail(organizacion.getEmail());
        response.setLogo(organizacion.getLogo());
        response.setCiudad(organizacion.getCiudad());
        response.setProvincia(organizacion.getProvincia());
        response.setPais(organizacion.getPais());
        response.setActiva(organizacion.isActiva());

        return response;
    }
	
}
