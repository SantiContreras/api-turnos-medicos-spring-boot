package com.TurnosMedicos.Dto;

public class AgendaDto {

	
	private String hora;
	private String estado; // Libre / ocupado
	
	 public AgendaDto(String hora, String estado) {
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
