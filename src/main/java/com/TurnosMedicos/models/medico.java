package com.TurnosMedicos.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumns;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="medicos")
public class Medico implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "el campo nombre no debe estar vacio")
	@Column(nullable = false)
	private String nombre;
	
	@NotBlank(message = "el campo apellid no debe estar vacio")
	@Column(nullable = false)
	private String apellido;
	
	@NotBlank(message = "el campo matricula no debe estar vacio")
	@Column(nullable = false)
	private String matricula;
	
	// Relacion muchos a uno
	@ManyToOne
	@JoinColumn(name = "especialidad_id",nullable = false)
	private especialidad especialidad;
	
	@ManyToOne
	@JoinColumn(name="organizacion_id",nullable = false)
	private Organizacion organizacion;

	 public Organizacion getOrganizacion() {
		return organizacion;
	}

	public void setOrganizacion(Organizacion organizacion) {
		this.organizacion = organizacion;
	}

	public Medico() {
	    }

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

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public especialidad getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(especialidad especialidad) {
		this.especialidad = especialidad;
	}
	 
	 
}
