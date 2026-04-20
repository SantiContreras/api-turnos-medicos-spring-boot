package com.TurnosMedicos.models;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name="Disponibilidades")
@Entity
public class Disponibilidad {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="medicoId", nullable = false)
	private Medico medico ;
	
	@ManyToOne
	@JoinColumn(name = "organizacion_id", nullable = false)
	private Organizacion organizacion;
	
	public Organizacion getOrganizacion() {
		return organizacion;
	}

	public void setOrganizacion(Organizacion organizacion) {
		this.organizacion = organizacion;
	}

	@Enumerated(EnumType.STRING)
	private  DiaSemana diaSemana;
	
	private LocalTime horaInicio;
	
	private LocalTime horaFinal;
	
	private Integer duracionMinutos; //15 , 30 , 45 

	public Long getId() {
		return id;
	}

	public Medico getMedico() {
		return medico;
	}

	public DiaSemana getDiaSemana() {
		return diaSemana;
	}

	public LocalTime getHoraInicio() {
		return horaInicio;
	}

	public LocalTime getHoraFinal() {
		return horaFinal;
	}

	public Integer getDuracionMinutos() {
		return duracionMinutos;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public void setDiaSemana(DiaSemana diaSemana) {
		this.diaSemana = diaSemana;
	}

	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
	}

	public void setHoraFinal(LocalTime horaFinal) {
		this.horaFinal = horaFinal;
	}

	public void setDuracionMinutos(Integer duracionMinutos) {
		this.duracionMinutos = duracionMinutos;
	}
	
	

}
