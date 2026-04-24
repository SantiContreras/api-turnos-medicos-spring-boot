package com.TurnosMedicos.Dto;

import com.TurnosMedicos.models.DiaSemana;

public class DisponibilidadResponseDto {

	private Long id;
	private String medico;
	private DiaSemana diaSemana;
	private String rango;
	private Integer duracion;
	
	public DisponibilidadResponseDto(Long id, String medico, DiaSemana diaSemana, String rango, Integer duracion) {
	
		this.id = id;
		this.medico = medico;
		this.diaSemana = diaSemana;
		this.rango = rango;
		this.duracion = duracion;
	}

	public Long getId() {
		return id;
	}

	public String getMedico() {
		return medico;
	}

	public DiaSemana getDiaSemana() {
		return diaSemana;
	}

	public String getRango() {
		return rango;
	}

	public Integer getDuracion() {
		return duracion;
	}

	
	
	
	
	
	
	
}
