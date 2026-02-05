package com.TurnosMedicos.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.TurnosMedicos.Dto.TurnoResponseDto;
import com.TurnosMedicos.Dto.turnoRequestDTO;
import com.TurnosMedicos.Service.turnoService;
import com.TurnosMedicos.models.EstadoTurno;
import com.TurnosMedicos.models.Turno;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/turnosMedicos")
@CrossOrigin("*")
@Tag(name = "Turnos", description = "Gestión de turnos médicos")
public class turnoController {

	private final turnoService turSer;

	public turnoController(turnoService turSer) {
		this.turSer = turSer;
	}

	// get

	/*
	 * // post
	 * 
	 * @PostMapping
	 * 
	 * @ResponseStatus(HttpStatus.CREATED) public turno crear(@Valid @RequestBody
	 * turno tu) {
	 * 
	 * return turSer.guardar(tu); }
	 */

	// post
	@Operation(summary = "Crear turno", description = "Crea un turno validando que no exista otro en el mismo horario")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Turno creado"),
			@ApiResponse(responseCode = "409", description = "Turno duplicado"),
			@ApiResponse(responseCode = "404", description = "Médico o paciente inexistente") })
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<TurnoResponseDto> crear(@Valid @RequestBody turnoRequestDTO tu) {

		return ResponseEntity.status(HttpStatus.CREATED).body(turSer.CrearElTurno(tu));
	}

	@PutMapping("/{id}/cancelar")
	public Turno cancelar(@PathVariable Long id) {
		return turSer.cancelar(id);
	}

	@PutMapping("/{id}/atender")
	public Turno atender(@PathVariable Long id) {
		return turSer.marcarTurnoComoAtendido(id);
	}

	@Operation(summary = "Listar turnos", description = "Permite listar turnos con filtros opcionales y paginación")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Listado de turnos"),
			@ApiResponse(responseCode = "400", description = "Parámetros inválidos") })
	@GetMapping
	public Page<TurnoResponseDto> listarTurnos(

			@Parameter(description = "ID del médico") @RequestParam(required = false) Long medicoId,
			@Parameter(description = "ID del paciente") @RequestParam(required = false) Long pacienteId,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
			@RequestParam(required = false) LocalDate fechaDesde, @RequestParam(required = false) LocalDate fechaHasta,
			@RequestParam(required = false) EstadoTurno estado, Pageable pageable) {

		return turSer.listarTurnos(medicoId, fecha, estado, pageable, fecha, fecha);

	}

}
