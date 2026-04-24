package com.TurnosMedicos.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping
    public DisponibilidadResponseDto crear(
            @RequestBody DisponibilidadRequestDto dto,
            HttpServletRequest request) {

        String token = request.getHeader("Authorization").substring(7);
        Long orgId = jwtService.extractOrganizacion(token);

        return service.crearDisponibilidad(dto, orgId);
    }

    @GetMapping
    public List<DisponibilidadResponseDto> listar(
            @RequestParam Long medicoId,
            HttpServletRequest request) {

        String token = request.getHeader("Authorization").substring(7);
        Long orgId = jwtService.extractOrganizacion(token);

        return service.listarDisponibilidad(medicoId, orgId);
    }

    @DeleteMapping("/{id}")
    public void eliminar(
            @PathVariable Long id,
            HttpServletRequest request) {

        String token = request.getHeader("Authorization").substring(7);
        Long orgId = jwtService.extractOrganizacion(token);

        service.eliminarDisponibilidad(id, orgId);
    }
}
