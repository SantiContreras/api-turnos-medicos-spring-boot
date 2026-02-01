package com.TurnosMedicos.Dto;

import java.time.LocalDate;
import java.time.LocalTime;

import io.swagger.v3.oas.annotations.media.Schema;

public class turnoRequestDTO {

	@Schema(example = "2026-01-10", description = "Fecha del turno")
	private LocalDate fecha;

	@Schema(example = "10:30", description = "Hora del turno")
	private LocalTime hora;

	@Schema(example = "1", description = "ID del médico")
	private Long medicoId;

	@Schema(example = "5", description = "ID del paciente")
	private Long pacienteId;

	// setter and getter
	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public LocalTime getHora() {
		return hora;
	}

	public void setHora(LocalTime hora) {
		this.hora = hora;
	}

	public Long getMedicoId() {
		return medicoId;
	}

	public void setMedicoId(Long medicoId) {
		this.medicoId = medicoId;
	}

	public Long getPacienteId() {
		return pacienteId;
	}

	public void setPacienteId(Long pacienteId) {
		this.pacienteId = pacienteId;
	}

}
