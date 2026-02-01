package com.TurnosMedicos.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.TurnosMedicos.Service.PacienteService;
import com.TurnosMedicos.models.paciente;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pacientes")
@CrossOrigin("*")
public class pacienteController {

	private final PacienteService pacSer;

	public pacienteController(PacienteService paSer) {
		this.pacSer = paSer;
	}
	
	//get listar los pacientes
	@GetMapping
	public List<paciente> listar() {
		return pacSer.Listartodos();
	}
	
	
	//post guardar algun paciente o modificar
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public paciente crer(@Valid @RequestBody paciente pa) {
		return pacSer.guardar(pa);
	}
	
	
	
	
}
