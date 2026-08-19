package com.TurnosMedicos.Dto;

import jakarta.validation.constraints.NotBlank;

public class EspecialidadRequest {
	
	public EspecialidadRequest() {}	 

	@NotBlank(message="el campo nombre debe ser obligatorio")
	private String nombre;
	
	
	private String descripcion;

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}
