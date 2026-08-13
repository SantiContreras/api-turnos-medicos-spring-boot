package com.TurnosMedicos.models;


import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="organizaciones")
public class Organizacion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nombre;
	
	@Column(nullable = false)
	private boolean activa ;
	
	@Column(nullable = false)
	private String direccion ;
	
	@Column(nullable = false)
	private String telefono ;
	
	@Column(nullable = false)
	private String email ;
	
	@Column(nullable = false)
	private String logo ;
	
	@Column(nullable = false)
	private String ciudad ;
	
	@Column(nullable = false)
	private String provincia;
	
	@Column(nullable = false)
	private String pais ;
	

	
	public String getDireccion() {
		return direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getEmail() {
		return email;
	}

	public String getLogo() {
		return logo;
	}

	public String getCiudad() {
		return ciudad;
	}

	public String getProvincia() {
		return provincia;
	}

	public String getPais() {
		return pais;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}


	
	//relacion con usuario
	@OneToMany(mappedBy="organizacion")
	private List<Usuario> usuarios;

	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public boolean isActiva() {
		return activa;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	

}
