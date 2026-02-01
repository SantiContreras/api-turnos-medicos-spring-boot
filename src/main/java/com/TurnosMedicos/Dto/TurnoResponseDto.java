package com.TurnosMedicos.Dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.TurnosMedicos.models.EstadoTurno;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta de un turno médico")
public class TurnoResponseDto {

	@Schema(example = "10")
	private Long id;
	@Schema(example = "2026-01-10")
	private LocalDate fecha;

	@Schema(example = "10:30")
	private LocalTime hora;

	@Schema(example = "ACTIVO")
	private EstadoTurno estado;

	@Schema(description = "Datos del médico asignado al turno")
	private MedicoResponseDto medico;
	
	@Schema(description = "Datos del paciente del turno")
	private PacienteResponseDto paciente;

	public MedicoResponseDto getMedico() {
		return medico;
	}

	public void setMedico(MedicoResponseDto medico) {
		this.medico = medico;
	}

	public PacienteResponseDto getPaciente() {
		return paciente;
	}

	public void setPaciente(PacienteResponseDto paciente) {
		this.paciente = paciente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public EstadoTurno getEstado() {
		return estado;
	}

	public void setEstado(EstadoTurno estado) {
		this.estado = estado;
	}

}
