package com.TurnosMedicos.Dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Información del paciente")
public class PacienteResponseDto {

	@Schema(example = "5")
	private Long id;
	@Schema(example = "María")
	private String nombre;
	@Schema(example = "Gómez")
	private String apellido;
	@Schema(example = "30123456")
	private String dni;

	@Schema(example = "3624789966")
	private String telefono;

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

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

}
