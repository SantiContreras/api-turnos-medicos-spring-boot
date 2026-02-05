package com.TurnosMedicos.Mapper;

import com.TurnosMedicos.Dto.MedicoResponseDto;
import com.TurnosMedicos.Dto.PacienteResponseDto;
import com.TurnosMedicos.Dto.TurnoResponseDto;
import com.TurnosMedicos.models.Turno;


public class MapperTurnos {

	public static TurnoResponseDto toDTO(Turno t) {

		TurnoResponseDto turnoDto = new TurnoResponseDto();

		turnoDto.setId(t.getId());
		turnoDto.setEstado(t.getEstado());
		turnoDto.setFecha(t.getFecha());
		turnoDto.setHora(t.getHora());

		// ===== MEDICO =====
		if (t.getMedico() != null) {
			MedicoResponseDto medicoDto = new MedicoResponseDto();
			medicoDto.setNombre(t.getMedico().getNombre());
			medicoDto.setApellido(t.getMedico().getApellido());

			if (t.getMedico().getEspecialidad() != null) {
				medicoDto.setEspecialidad(t.getMedico().getEspecialidad().getNombre());
			}

			turnoDto.setMedico(medicoDto);
		}

		// ===== PACIENTE =====
		if (t.getPaciente() != null) {
			PacienteResponseDto pacienteDto = new PacienteResponseDto();
			pacienteDto.setNombre(t.getPaciente().getNombre());
			pacienteDto.setApellido(t.getPaciente().getApellido());
			pacienteDto.setDni(t.getPaciente().getDni());

			turnoDto.setPaciente(pacienteDto);
		}

		return turnoDto;

	}

}
