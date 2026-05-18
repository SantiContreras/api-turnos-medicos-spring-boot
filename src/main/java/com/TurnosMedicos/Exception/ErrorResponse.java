package com.TurnosMedicos.Exception;

import java.time.LocalDateTime;

public class ErrorResponse {
	
	private int status;
	private String mensaje;
	private LocalDateTime timeStamp;
	
	public ErrorResponse(int status, String mensaje) {
        this.status = status;
        this.mensaje = mensaje;
        this.timeStamp = LocalDateTime.now();
    }

    public int getStatus() { return status; }
    public String getMensaje() { return mensaje; }
    public LocalDateTime getTimestamp() { return timeStamp; }

}
