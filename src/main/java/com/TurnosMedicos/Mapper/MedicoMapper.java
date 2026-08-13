package com.TurnosMedicos.Mapper;

import com.TurnosMedicos.Dto.MedicoRequest;
import com.TurnosMedicos.Dto.MedicoResponseDto;
import com.TurnosMedicos.models.Medico;

public class MedicoMapper {

	
	//==========================================
	//========= Convierte request en entity ====
	//==========================================
	
	public static Medico toEntity(MedicoRequest request) {
		
		Medico medico = new Medico();
		
		medico.setNombre(request.getNombre());
		medico.setApellido(request.getApellido());
		medico.setMatricula(request.getMatricula());
		
		
		return medico;
	}
	
	//==========================================
	//========= una entidad en responseDto ====
	//==========================================
	
	public static MedicoResponseDto toResponse(Medico medico) {
		
		MedicoResponseDto response = new MedicoResponseDto();
		
		response.setId(medico.getId());
		response.setApellido(medico.getApellido());
		response.setNombre(medico.getNombre());
		response.setMatricula(medico.getMatricula());
		if (medico.getEspecialidad() != null) {
		    response.setEspecialidadId(
		        medico.getEspecialidad().getId()
		    );
		}

		if (medico.getOrganizacion() != null) {
		    response.setOrganizacionId(
		        medico.getOrganizacion().getId()
		    );
		}
		
		return response;
	}
}
