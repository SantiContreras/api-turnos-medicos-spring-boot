package com.TurnosMedicos.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class OrganizacionRequest {
	
	

	@NotBlank(message = "el campo no puede ser en blanco")
	private String nombre;

	@Email(message = "Email inválido")
	@NotBlank(message = "el campo no puede ser en blanco")
	private String email;

	@NotBlank(message = "el campo no puede ser en blanco")
	private String direccion;

	@NotBlank(message = "el campo no puede ser en blanco")
	private String telefono;

	@NotBlank(message = "el campo no puede ser en blanco")
	private String logo;
	
	@NotBlank(message = "el campo no puede ser en blanco")
	private String ciudad;
	
	@NotBlank(message = "el campo no puede ser en blanco")
	private String provincia;
	
	public String getCiudad() {
		return ciudad;
	}

	public String getProvincia() {
		return provincia;
	}

	public String getPais() {
		return Pais;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public void setPais(String pais) {
		Pais = pais;
	}

	@NotBlank(message = "el campo no puede ser en blanco")
	private String Pais;
	
	public OrganizacionRequest() {}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getLogo() {
		return logo;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	
}
