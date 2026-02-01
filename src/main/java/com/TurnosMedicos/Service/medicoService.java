package com.TurnosMedicos.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.TurnosMedicos.Repository.medicoRepository;
import com.TurnosMedicos.models.medico;

@Service
public class medicoService {

	private medicoRepository medRep;

	public medicoService(medicoRepository medRep) {
		
		this.medRep = medRep;
	}
	
	//listar 
	
	public List<medico> listar(){
		return medRep.findAll();	
		}
	
	//guardar
	
	public medico guardar(medico me) {
		return medRep.save(me);
	}
	
	
}
