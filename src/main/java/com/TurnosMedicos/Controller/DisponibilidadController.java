package com.TurnosMedicos.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TurnosMedicos.Dto.DisponibilidadRequestDto;
import com.TurnosMedicos.Dto.DisponibilidadResponseDto;
import com.TurnosMedicos.Service.DisponibilidadService;
import com.TurnosMedicos.security.jwt.JwtService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/disponibilidad")
public class DisponibilidadController {

	private final DisponibilidadService service;
	private final JwtService jwtService;

	public DisponibilidadController(DisponibilidadService service, JwtService jwtService) {
		this.service = service;
		this.jwtService = jwtService;
	}

	// ==========================================================
	// ==================== CREAR DISPONIBILIDAD ================
	// ==========================================================
	@PostMapping
	public DisponibilidadResponseDto crear(@RequestBody DisponibilidadRequestDto dto,
			@RequestAttribute("orgId") Long orgId) {

		return service.crearDisponibilidad(dto, orgId);
	}

	// ==========================================================
	// ==================== LISTAR ================
	// ==========================================================
	@GetMapping
	public List<DisponibilidadResponseDto> listar(@RequestParam Long medicoId, @RequestAttribute("orgId") Long orgId) {

		return service.listarDisponibilidad(medicoId, orgId);
	}

	// ==========================================================
	// ==================== ELIMINAR DISPONIBILIDAD ================
	// ==========================================================

	@DeleteMapping("/{id}")
	public void eliminar(@PathVariable Long id, @RequestAttribute("orgId") Long orgId) {

		service.eliminarDisponibilidad(id, orgId);
	}
}
