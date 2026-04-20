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

import com.TurnosMedicos.Dto.AgendaDto;
import com.TurnosMedicos.Dto.AgendaDtoResponse;
import com.TurnosMedicos.Dto.TurnoResponseDto;
import com.TurnosMedicos.Dto.turnoRequestDTO;
import com.TurnosMedicos.Service.turnoService;
import com.TurnosMedicos.models.EstadoTurno;
import com.TurnosMedicos.models.Turno;
import com.TurnosMedicos.security.jwt.JwtService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/turnosMedicos")
@CrossOrigin("*")
@Tag(name = "Turnos", description = "Gestión de turnos médicos")
public class turnoController {

	private final turnoService turSer;
	private final JwtService jwtService;

	public turnoController(turnoService turSer, JwtService jwtService) {
		this.turSer = turSer;
		this.jwtService = jwtService;
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

	// ================== END POINT CREAR TURNO
	// ===================================================
	@Operation(summary = "Crear turno", description = "Crea un turno validando que no exista otro en el mismo horario")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Turno creado"),
			@ApiResponse(responseCode = "409", description = "Turno duplicado"),
			@ApiResponse(responseCode = "404", description = "Médico o paciente inexistente") })
	@PostMapping("disponibilidad")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<TurnoResponseDto> crear(@Valid @RequestBody turnoRequestDTO tu, HttpServletRequest request) {

		String token = request.getHeader("Authorization").substring(7);
		Long idOrg = jwtService.extractOrganizacion(token);

		return ResponseEntity.status(HttpStatus.CREATED).body(turSer.CrearElTurno(tu, idOrg));
	}

	// =================== END POINT CANCELAR TURNO
	// =================================
	@PutMapping("/{id}/cancelar")
	public Turno cancelar(@PathVariable Long id, HttpServletRequest request) {

		String token = request.getHeader("Authorization").substring(7);
		Long orgId = jwtService.extractOrganizacion(token);

		return turSer.cancelar(id, orgId);
	}

	// =================== END POINT ATENDER TURNO
	// ==========================================
	@PutMapping("/{id}/atender")
	public Turno atender(@PathVariable Long id, HttpServletRequest request) {

		String token = request.getHeader("Authorization").substring(7);
		Long orgId = jwtService.extractOrganizacion(token);

		return turSer.marcarTurnoComoAtendido(id);
	}

	// ================== END POINT LISTAR TURNOS
	// ===================================================
	@Operation(summary = "Listar turnos", description = "Permite listar turnos con filtros opcionales y paginación")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Listado de turnos"),
			@ApiResponse(responseCode = "400", description = "Parámetros inválidos") })
	@GetMapping
	public Page<TurnoResponseDto> listarTurnos(

			@RequestParam(required = false) Long medicoId, @RequestParam(required = false) Long pacienteId,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta,
			@RequestParam(required = false) EstadoTurno estado, Pageable pageable, HttpServletRequest request) {

		String token = request.getHeader("Authorization").substring(7);
		Long orgId = jwtService.extractOrganizacion(token);

		return turSer.listarTurnos(medicoId, pacienteId, fecha, estado, pageable, fechaDesde, fechaHasta, orgId);
	}

	// ================== END POINT AGENDA
	// ===================================================

	/*@GetMapping("/agenda")
	public List<AgendaDto> agenda(@RequestParam Long medicoId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha, HttpServletRequest request) {

		String token = request.getHeader("Authorization").substring(7);
		Long orgId = jwtService.extractOrganizacion(token);

		return turSer.obtenerAgenda(medicoId, fecha, orgId);

	}*/

	// ================ END POINT OBTENER TURNOS DISPONIBLES
	// =================================

	@GetMapping("/disponibles")
	public List<String> obtenerDisponibles(@RequestParam Long medicoId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha, HttpServletRequest request) {

		String token = request.getHeader("Authorization").substring(7);
		Long orgId = jwtService.extractOrganizacion(token);

		return turSer.ObtenerHorariosDisponibles(medicoId, fecha, orgId);

	}
	
	// END POINT PARA ORTENER AGENDA DE TURNOS DISPONIBLES MAS COMPLETO
	
	@GetMapping("/agenda")
	public List<AgendaDtoResponse> obtenerAgenda(
	        @RequestParam Long medicoId,
	        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
	        HttpServletRequest request
	) {

	    String token = request.getHeader("Authorization").substring(7);
	    Long orgId = jwtService.extractOrganizacion(token);

	    return turSer.obtenerAgendaVisual(medicoId, fecha, orgId);
	}
}
