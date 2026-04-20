package com.TurnosMedicos.Dto;

public class AgendaDtoResponse {

	private String hora;
	private String estado; // libre o ocupado

	public AgendaDtoResponse(String hora, String estado) {

		this.hora = hora;
		this.estado = estado;
	}

	public String getHora() {
		return hora;
	}

	public String getEstado() {
		return estado;
	}

}
