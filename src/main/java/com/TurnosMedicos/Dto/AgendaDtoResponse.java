package com.TurnosMedicos.Dto;

public class AgendaDtoResponse {

	private Long turnoId; // 🔥 CLAVE
	private String hora;
	private String estado; // libre o ocupado
	private String paciente;

	public AgendaDtoResponse(Long turnoId ,String hora, String estado, String paciente ) {
		this.turnoId = turnoId;
		this.hora = hora;
		this.estado = estado;
		this.paciente = paciente;
	}
	
	public Long getTurnoId() {
		return turnoId;
	}

	public String getHora() {
		return hora;
	}

	public String getEstado() {
		return estado;
	}
	
	public String getPaciente() {
		return paciente;
	}

}
