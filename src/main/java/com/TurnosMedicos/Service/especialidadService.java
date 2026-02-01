package com.TurnosMedicos.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.TurnosMedicos.Repository.especialidadRepository;
import com.TurnosMedicos.models.especialidad;

@Service
public class especialidadService {

	
	private  final especialidadRepository espRepo;

	public especialidadService(especialidadRepository espRepo) {
		
		this.espRepo = espRepo;
	}
	
	
	
	// metodo para obtener todas las especialidades
	
	
	public List<especialidad> listar(){
		return espRepo.findAll();
	}
	
	public especialidad guardar(especialidad esp) {
		return espRepo.save(esp);
				
	}
	
	
	
}
