package com.TurnosMedicos.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.TurnosMedicos.Repository.medicoRepository;
import com.TurnosMedicos.models.Medico;


@Service
public class medicoService {

	private medicoRepository medRep;

	public medicoService(medicoRepository medRep) {
		
		this.medRep = medRep;
	}
	
	//listar 
	
	public List<Medico> listar(){
		return medRep.findAll();	
		}
	
	//guardar
	
	public Medico guardar(Medico me) {
		return medRep.save(me);
	}
	
	
}
