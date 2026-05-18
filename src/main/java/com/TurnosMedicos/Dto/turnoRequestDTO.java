package com.TurnosMedicos.Dto;

import java.time.LocalDate;
import java.time.LocalTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class turnoRequestDTO {

	@Schema(example = "2026-01-10", description = "Fecha del turno")
	@NotNull(message="La fecha  es obligatorio")
	private LocalDate fecha;

	@Schema(example = "10:30", description = "Hora del turno")
	@NotNull(message="La hora  es obligatorio")
	private LocalTime hora;

	@Schema(example = "1", description = "ID del médico")
	@NotNull(message="el medico es obligatorio")
	private Long medicoId;

	@Schema(example = "5", description = "ID del paciente")
	@NotNull(message="el paciente es obligatorio")
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
