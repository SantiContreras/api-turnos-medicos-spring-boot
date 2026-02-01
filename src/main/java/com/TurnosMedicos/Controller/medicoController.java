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

import com.TurnosMedicos.Service.medicoService;
import com.TurnosMedicos.models.medico;

import jakarta.validation.Valid;

@RestController()
@RequestMapping("/api/medicos")
@CrossOrigin("*")
public class medicoController {

	private final medicoService medSer;

	public medicoController(medicoService medSer) {
		this.medSer = medSer;
	}

	// get
	@GetMapping
	public List<medico> listar() {

		return medSer.listar();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public medico crear(@Valid @RequestBody medico medico) {
		return medSer.guardar(medico);
	}
	
	

}
