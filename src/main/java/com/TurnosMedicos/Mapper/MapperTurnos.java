package com.TurnosMedicos.Mapper;

import com.TurnosMedicos.Dto.MedicoResponseDto;
import com.TurnosMedicos.Dto.PacienteResponseDto;
import com.TurnosMedicos.Dto.TurnoResponseDto;
import com.TurnosMedicos.models.turno;

public class MapperTurnos {

	public static TurnoResponseDto toDTO(turno t) {

		TurnoResponseDto turnoDto = new TurnoResponseDto();

		turnoDto.setId(t.getId());
		turnoDto.setEstado(t.getEstado());
		turnoDto.setFecha(t.getFecha());
		turnoDto.setHora(t.getHora());
		
		MedicoResponseDto medicoDto = new MedicoResponseDto();
		medicoDto.setNombre(t.getMedico().getNombre());
		medicoDto.setNombre(t.getMedico().getApellido());
		medicoDto.setEspecialidad(t.getMedico().getEspecialidad().getNombre());
		
		PacienteResponseDto pacienteDto = new PacienteResponseDto();
		pacienteDto.setNombre(t.getPaciente().getNombre());
		pacienteDto.setApellido(t.getPaciente().getApellido());
		pacienteDto.setDni(t.getPaciente().getApellido());
		
		turnoDto.setMedico(medicoDto);
		turnoDto.setPaciente(pacienteDto);
		
		return turnoDto;
		


	}

}
