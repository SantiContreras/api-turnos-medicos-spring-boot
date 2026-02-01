package com.TurnosMedicos.Dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta de error")
public class ErrorResponse {

	private int status;
	private String mensaje;
	private LocalDateTime timestamp;
	
	public ErrorResponse(int status, String mensaje) {
		
		this.status = status;
		this.mensaje = mensaje;
		this.timestamp = LocalDateTime.now();
	}

	public int getStatus() {
		return status;
	}



	public String getMensaje() {
		return mensaje;
	}



	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	
	
	
	
	
	
}
