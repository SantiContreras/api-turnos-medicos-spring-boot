package com.TurnosMedicos.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MedicoRequest {
	
	@NotBlank(message="el nombre no puede estar vacion")
	private String nombre;
	@NotBlank(message="el campo apellido no puede estar vacio")
	private String apellido;
	
	@NotBlank(message="Edl campo matricula no puede estar vacia")
	private String matricula; 
	
	@NotNull(message="La especialidad es obligatoria")
	private Long especialidadId;
	
	@NotNull(message="La organizacion es obligatoria")
	private Long organizacionId;
	
	public MedicoRequest() {}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public String getMatricula() {
		return matricula;
	}

	public Long getEspecialidadId() {
		return especialidadId;
	}

	public Long getOrganizacionId() {
		return organizacionId;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public void setEspecialidadId(Long especialidadId) {
		this.especialidadId = especialidadId;
	}

	public void setOrganizacionId(Long organizacionId) {
		this.organizacionId = organizacionId;
	}
	
	

}
