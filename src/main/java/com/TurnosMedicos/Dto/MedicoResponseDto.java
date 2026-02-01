package com.TurnosMedicos.Dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class MedicoResponseDto {

	@Schema(example = "1")
	private Long id;

	@Schema(example = "Juan")
	private String nombre;


    @Schema(example = "Pérez")
	private String apellido;

	@Schema(example = "Cardiología")
	private String especialidad;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

}
