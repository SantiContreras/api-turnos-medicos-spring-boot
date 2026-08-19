package com.TurnosMedicos.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.TurnosMedicos.Dto.EspecialidadRequest;
import com.TurnosMedicos.Dto.EspecialidadResponse;
import com.TurnosMedicos.Mapper.EspecialidadMapper;
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
	
	//===============================================================
	//=========== servicio para crear una nueva especialidad ==========
	//===============================================================
	public EspecialidadResponse crearEspecialidad(EspecialidadRequest request) {
		//convertimos el requet
		especialidad esp = EspecialidadMapper.toEntity(request);
		//guardamos en la base de datos
		especialidad especialidadGuardada = espRepo.save(esp);
		// convertimos en response
		return EspecialidadMapper.toResponse(especialidadGuardada);
	}
	
	//================================================================
	//======== servicio para listar todas las especialidades =========
	//================================================================
	
	public List<EspecialidadResponse> listarEspecialidades (){
		
		//creamamo la listar response
		List<EspecialidadResponse> listaResponse = new ArrayList<>();
		//buscamos en la base de datos y retornamos a una lista de Especialidades ;
		List<especialidad> lista = espRepo.findAll();
		//recorremos la lista para mapperala 
		
		for(especialidad esp : lista) {
			//mappeamos a respuesta y la agregamos 
			EspecialidadResponse response = EspecialidadMapper.toResponse(esp);
			//agregamos a la lista respuesta 
			listaResponse.add(response);
		}
		return listaResponse;
	}
	
	//===============================================================
	//======= servicio para obtener una especialidad por id =========
	//===============================================================
	public EspecialidadResponse especialidadPorId(Long id) {
		// buscamos la especialidad 
		especialidad esp = espRepo.findById(id).orElseThrow(()-> new RuntimeException("no se encuentra dicha especialidad en la base de datos "));
		//hacemos el mapper
		EspecialidadResponse response = EspecialidadMapper.toResponse(esp);
		
		return response;
	}
	
	//==============================================================
	//============== servicio para actualizar una especialidad =====
	//==============================================================
	
	public EspecialidadResponse actualizarEspecialidad(Long id , EspecialidadRequest request ) {
		//buscamos la especialidad que vamos a actualizar
		especialidad esp = espRepo.findById(id).orElseThrow(()-> new RuntimeException("no se encuentra dicha especialidad en la base de datos "));
		
		//seteamos los valores
		esp.setDescripcion(request.getDescripcion());
		esp.setNombre(request.getNombre());
		
		
		//guardamos la especialidad 
		especialidad especialidadGuardada = espRepo.save(esp);
		
		//retornamos en response
		EspecialidadResponse response = EspecialidadMapper.toResponse(especialidadGuardada);
		return  response;
	}
	
	//=================================================================================
	//=============== servicio para borrar logicamente una especialidad ===============
	//=================================================================================
	
	public EspecialidadResponse eliminarLogicamente(Long id) {
		//buscamos la especialidad
		especialidad esp = espRepo.findById(id).orElseThrow(()-> new RuntimeException("no se encuentra dicha especialidad en la base de datos "));
		//cambiamos los valosres
		esp.setActivo(false);
		//guardamos en la base de datos
		especialidad espEliminadaLog = espRepo.save(esp);
		//retornamos la respuesta 
		EspecialidadResponse respuesta = EspecialidadMapper.toResponse(espEliminadaLog);
		return respuesta;
	}
	
	//==================================================================================
	//=====================Metodo para activar una especialidad ========================
	//==================================================================================
	
	 public EspecialidadResponse activarEspecialidad(Long id) {
			//buscamos la especialidad
			especialidad esp = espRepo.findById(id).orElseThrow(()-> new RuntimeException("no se encuentra dicha especialidad en la base de datos "));
			//cambiamos los valosres
			esp.setActivo(true);
			//guardamos en la base de datos
			especialidad espEliminadaLog = espRepo.save(esp);
			//retornamos la respuesta 
			EspecialidadResponse respuesta = EspecialidadMapper.toResponse(espEliminadaLog);
			return respuesta;
	 }
}
