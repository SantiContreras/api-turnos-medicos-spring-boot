package com.TurnosMedicos.Dto;

import java.time.LocalTime;

import com.TurnosMedicos.models.DiaSemana;

public class DisponibilidadRequestDto {

	
	private Long medicoId;
	private DiaSemana diaSemana;
	private LocalTime horaInicio;
	private LocalTime horaFin;
	private int duracionMinutos ; // 15 , 30
	public Long getMedicoId() {
		return medicoId;
	}
	public DiaSemana getDiaSemana() {
		return diaSemana;
	}
	public LocalTime getHoraInicio() {
		return horaInicio;
	}
	public LocalTime getHoraFin() {
		return horaFin;
	}
	public int getDuracionMinutos() {
		return duracionMinutos;
	}
	public void setMedicoId(Long medicoId) {
		this.medicoId = medicoId;
	}
	public void setDiaSemana(DiaSemana diaSemana) {
		this.diaSemana = diaSemana;
	}
	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
	}
	public void setHoraFin(LocalTime horaFin) {
		this.horaFin = horaFin;
	}
	public void setDuracionMinutos(int duracionMinutos) {
		this.duracionMinutos = duracionMinutos;
	}
	
	
	
	
}
