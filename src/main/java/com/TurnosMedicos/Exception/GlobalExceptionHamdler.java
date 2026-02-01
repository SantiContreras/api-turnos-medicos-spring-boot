package com.TurnosMedicos.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.TurnosMedicos.Dto.ErrorResponse;

import jakarta.el.MethodNotFoundException;
import jakarta.validation.Valid;

@RestControllerAdvice
public class GlobalExceptionHamdler {

	// 🔴 404
	public ResponseEntity<ErrorResponse> manejarNoEncontrado(RecursoNoEncontradoException ex) {

		ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	// validations
	@Valid
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> manejarValidacion(MethodArgumentNotValidException ex) {

		String mensaje = ex.getBindingResult().getFieldError().getDefaultMessage();

		ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), mensaje);

		return ResponseEntity.badRequest().body(error);

	}

	// 🔴 Errores generales
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> manejarExcepcionGeneral(Exception ex) {

		ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error interno del servidor");

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}

	// 🔴 Errores de turnos duplicados
	@ExceptionHandler(TurnoDuplicadoException.class)
	public ResponseEntity<ErrorResponse> manejarTurnosDuplicados(TurnoDuplicadoException ex) {

		ErrorResponse error = new ErrorResponse(

				HttpStatus.CONFLICT.value(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
	}

	// 🔴 Errores de estados invalidos

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<ErrorResponse> manejarEstadoInvalido(IllegalStateException ex) {

		ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());

		return ResponseEntity.badRequest().body(error);
	}

	// 🔴 Errores de Fechas invalidas

	@ExceptionHandler(TurnoFechaImvalidaException.class)
	public ResponseEntity<ErrorResponse> manejarFechaInvalida(TurnoFechaImvalidaException ex) {
		ErrorResponse error = new ErrorResponse(
				HttpStatus.BAD_REQUEST.value(),
				ex.getMessage()
				);
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	// 🔴 Controlar el horario de disponibilidad 
	
	@ExceptionHandler(HorarioNoDisponibleException.class)
	public ResponseEntity<ErrorResponse> manejarHorariosDisponibles( HorarioNoDisponibleException ex){
		ErrorResponse error = new ErrorResponse(
				HttpStatus.BAD_REQUEST.value(),
				ex.getMessage()
				);
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

}
