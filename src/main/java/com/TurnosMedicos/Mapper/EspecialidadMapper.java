package com.TurnosMedicos.Mapper;

import com.TurnosMedicos.Dto.EspecialidadRequest;
import com.TurnosMedicos.Dto.EspecialidadResponse;
import com.TurnosMedicos.models.especialidad;

public class EspecialidadMapper {

	 //=========================================================
	//================ metodo para convertir request en Entity==
	//==========================================================
	
	
	public static especialidad toEntity(EspecialidadRequest request) {

		especialidad esp = new especialidad();

		// convertimos en un una entidad el request
		esp.setDescripcion(request.getDescripcion());
		esp.setNombre(request.getNombre());

		// creamos el response
		
		return esp;
	}
	
	//==============================================================
	//================== metodo para convertir entity en ResponseDto ========
	//=======================================================================
	
	public static EspecialidadResponse toResponse(especialidad esp) {
		// convertimos la entidad en response 
		EspecialidadResponse response = new EspecialidadResponse();
		response.setDescripcion(esp.getDescripcion());
		response.setId(esp.getId());
		response.setNombre(esp.getNombre());
		
		//retornamos el response 
		return response;
	}
}
