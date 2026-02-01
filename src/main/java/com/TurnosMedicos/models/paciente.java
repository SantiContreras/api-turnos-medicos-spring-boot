package com.TurnosMedicos.models;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

@Entity 
@Table(name="pacientes")
public class paciente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank(message = "el campo no debe estar vacio")
	@Column(nullable = false)
	private String nombre;
	
	@NotBlank(message = "El campo es obligatorio")
	private String apellido;
	
	@NotBlank(message = "el paciente debe tener si o si dni")
	@Column(unique=true , nullable = false)
	private String dni;
	
	@Email(message = "El email debe ser en un formato correcto")
	@Column(unique=true)
	private String email;
	
	@NotBlank(message = "el paciente debe tener si o si dni")
	private String telefono;
	
	@Past(message = "la fecha debe ser pasada")
	private LocalDate fechaNacimento;

	public paciente() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public LocalDate getFechaNacimento() {
		return fechaNacimento;
	}

	public void setFechaNacimento(LocalDate fechaNacimento) {
		this.fechaNacimento = fechaNacimento;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
}
