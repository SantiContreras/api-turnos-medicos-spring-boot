package com.TurnosMedicos.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// Error General
	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse RuntimeError(RuntimeException ex) {
		return new ErrorResponse(400, ex.getMessage());
	}

	// turno duplicado
	@ExceptionHandler(TurnoDuplicadoException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ErrorResponse manejarDuplicado(TurnoDuplicadoException ex) {
		return new ErrorResponse(409, ex.getMessage());
	}

	// 🔴 HORARIO NO DISPONIBLE
	@ExceptionHandler(HorarioNoDisponibleException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse manejarHorario(HorarioNoDisponibleException ex) {
		return new ErrorResponse(400, ex.getMessage());
	}

	// 🔴 FECHA INVÁLIDA
	@ExceptionHandler(TurnoFechaImvalidaException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse manejarFecha(TurnoFechaImvalidaException ex) {
		return new ErrorResponse(400, ex.getMessage());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse manejarValidaciones(MethodArgumentNotValidException ex) {

	    String mensaje = ex.getBindingResult()
	            .getFieldErrors()
	            .stream()
	            .findFirst()
	            .map(error -> error.getDefaultMessage())
	            .orElse("Error de validación");

	    return new ErrorResponse(400, mensaje);
	}

}
