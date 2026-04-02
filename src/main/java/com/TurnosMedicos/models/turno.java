package com.TurnosMedicos.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "turnos")
public class Turno implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "la fecha es obligatoria es obligatoria")
	@Future(message = "la fecha debe ser futura")
	@Column(nullable = false)
	private LocalDate fecha;

	@NotNull(message = "la hora es obligatoria ")
	@Column(nullable = false)
	private LocalTime hora;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private EstadoTurno estado;

	// relacion con el paciente

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "paciente_id", nullable = false)
	private paciente paciente;

	// Relacion con el medico

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "medico_id", nullable = false)
	private Medico medico;
	
	@ManyToOne
	@JoinColumn(name = "organizacion_id", nullable = false)
	private Organizacion organizacion;

	public Turno() {
		this.estado = EstadoTurno.RESERVADO;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public LocalTime getHora() {
		return hora;
	}

	public void setHora(LocalTime hora) {
		this.hora = hora;
	}

	public EstadoTurno getEstado() {
		return estado;
	}

	public void setEstado(EstadoTurno estado) {
		this.estado = estado;
	}

	public paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(paciente paciente) {
		this.paciente = paciente;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Organizacion getOrganizacion() {
		return organizacion;
	}

	public void setOrganizacion(Organizacion organizacion) {
		this.organizacion = organizacion;
	}
	
	

	
}
