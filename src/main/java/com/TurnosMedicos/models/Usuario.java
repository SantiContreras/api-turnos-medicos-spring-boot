package com.TurnosMedicos.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;
	private String password;

	private String rol; // medico , paciente , admin

	@ManyToOne
	@JoinColumn(name = "organizacion_id", nullable = false)
	private Organizacion organizacion;
	
	private boolean activo = true;

	public Usuario() {
	};

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public Organizacion getOrganizacion() {
		return organizacion;
	}

	public void setOrganizacion(Organizacion organizacion) {
		this.organizacion = organizacion;
	}

	public boolean getActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	
	
	

}
