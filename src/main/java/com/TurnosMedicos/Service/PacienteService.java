package com.TurnosMedicos.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.TurnosMedicos.Repository.pacienteRepository;
import com.TurnosMedicos.models.paciente;

@Service
public class PacienteService {

	private pacienteRepository pacienteRepo;

	// vendria ser como el contructor de esta clase
	public PacienteService(pacienteRepository pacienteRepo) {
		this.pacienteRepo = pacienteRepo;
	}
	
	//llama al repositorio para listar todos los pacientes
	public List<paciente>Listartodos(){
		return pacienteRepo.findAll();
		
	}
	
	//llama al repositorio con el metodo save para guardar un paciente
	public paciente guardar(paciente pa) {
		
		return pacienteRepo.save(pa);
	}
	
	

}
