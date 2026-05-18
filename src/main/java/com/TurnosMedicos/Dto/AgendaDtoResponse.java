package com.TurnosMedicos.Dto;

public class AgendaDtoResponse {

	private String hora;
	private String estado; // libre o ocupado
	private String paciente;

	public AgendaDtoResponse(String hora, String estado, String paciente) {

		this.hora = hora;
		this.estado = estado;
		this.paciente = paciente;
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
