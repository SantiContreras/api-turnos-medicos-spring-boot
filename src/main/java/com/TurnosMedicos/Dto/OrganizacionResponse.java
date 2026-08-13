package com.TurnosMedicos.Dto;

public class OrganizacionResponse {

	private Long id ; 
	private String nombre;
	private String direccion;
	private String telefono;
	private String email;
	private String logo;
	private String ciudad;
	private String provincia;
	private String Pais;
	private boolean activa;
	
	public OrganizacionResponse() {}

	public Long getId() {
		return id;
	}
	

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

	public String getNombre() {
		return nombre;
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

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getEmail() {
		return email;
	}

	public boolean isActiva() {
		return activa;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public void setActiva(boolean activa) {
		this.activa = activa;
	}
	
	
}
