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

import com.TurnosMedicos.Service.especialidadService;
import com.TurnosMedicos.models.especialidad;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/especialidades")
@CrossOrigin("*")
public class especialidadController {

	
	private final especialidadService espSer ;
	
	
	
	public especialidadController(especialidadService espSer) {
		this.espSer = espSer;
	}

	//get
	@GetMapping
	public List<especialidad> listar(){
		return espSer.listar();
	}
	
	//post
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public especialidad crear(@Valid @RequestBody especialidad esp) {
		return espSer.guardar(esp);
	}
	
}
