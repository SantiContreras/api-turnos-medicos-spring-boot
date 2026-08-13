package com.TurnosMedicos.Service;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.mapper.Mapper;
import org.springframework.stereotype.Service;

import com.TurnosMedicos.Repository.OrganizacionRepository;
import com.TurnosMedicos.Repository.especialidadRepository;
import com.TurnosMedicos.Repository.medicoRepository;
import com.TurnosMedicos.models.Medico;
import com.TurnosMedicos.models.especialidad;
import com.TurnosMedicos.models.Organizacion;

import java.util.List;

import com.TurnosMedicos.Dto.MedicoRequest;
import com.TurnosMedicos.Dto.MedicoResponseDto;
import com.TurnosMedicos.Mapper.MedicoMapper;

@Service
public class medicoService {

	private medicoRepository medRep;
	private especialidadRepository espRep;
	private OrganizacionRepository orgRep;

	public medicoService(medicoRepository medRep, especialidadRepository espRep, OrganizacionRepository orgRep) {

		this.medRep = medRep;
		this.espRep = espRep;
		this.orgRep = orgRep;
	}

	// ===========================================================================
	// ======================= metodo para listar todos los medicos ==============
	// ===========================================================================

	public List<MedicoResponseDto> listar() {

		// obtenemos todos los medicos
		List<Medico> listaMedicos = medRep.findAll();
		// creamos la lista para devolver

		List<MedicoResponseDto> listaResponse = new ArrayList<>();
		// recorremos la lista de medicos

		for (Medico med : listaMedicos) {

			// mappeamos y agregamos a la lista de Respuesta
			listaResponse.add(MedicoMapper.toResponse(med));
		}

		return listaResponse;
	}

	// =============================================================================================
	// ========= metodo para listar todos los medicos de una determinada
	// organizacion ==============
	// =============================================================================================
	public List<MedicoResponseDto> listarPorOrganizacion(Long organizacionId) {

		// obtenemos todos los medicos de una organizacion
		List<Medico> listaMedicos = medRep.findByOrganizacionId(organizacionId);

		// creamos la lista respuesta
		List<MedicoResponseDto> listaRespuesta = new ArrayList<>();

		// recorremos la lista y mapeamos
		for (Medico med : listaMedicos) {
			listaRespuesta.add(MedicoMapper.toResponse(med));
		}

		return listaRespuesta;

	}

	// ==========================================================================================
	// ============================== metodo para crear un medico
	// ===============================
	// ==========================================================================================

	public MedicoResponseDto crearMedico(MedicoRequest request) {
		// buscamos la especialidad del medico
		especialidad especialidad = espRep.findById(request.getEspecialidadId())
				.orElseThrow(() -> new RuntimeException("no se encuentra la especialidad seleccionada"));
		// buscamos la organizacion y le damos al medico
		Organizacion organizacion = orgRep.findById(request.getOrganizacionId())
				.orElseThrow(() -> new RuntimeException("no se encuentra la organizacion seleccionada"));
		// Mappeamos el request y lo pasamos a entity
		Medico medico = MedicoMapper.toEntity(request);
		// ahora agregamos la especialidad del medico y la organizacion a la que le
		// damos.

		medico.setEspecialidad(especialidad);
		medico.setOrganizacion(organizacion);

		// guardamos el medico creado
		Medico medicoCreado = medRep.save(medico);

		// retornamos la respuesta

		return MedicoMapper.toResponse(medicoCreado);

	}

	// ==========================================================================================
	// ============================== metodo para buscar un medico por su id
	// ====================
	// ==========================================================================================

	public MedicoResponseDto buscarMedicoPorId(Long id) {
		// buscamos el medico en la base de datos
		Medico medicoBuscado = medRep.findById(id)
				.orElseThrow(() -> new RuntimeException("el medico no se encuentra en la base de datos"));

		// mappeamos en respuesta

		MedicoResponseDto respuesta = MedicoMapper.toResponse(medicoBuscado);

		// retornamos la respuesta

		return respuesta;
	}

	// ========================================================================================
	// ================================ actualizar un medico
	// ==================================
	// ========================================================================================

	public MedicoResponseDto actualizarMedico(MedicoRequest request, Long id) {

		// buscamos al medico por su id
		Medico medicoBuscado = medRep.findById(id)
				.orElseThrow(() -> new RuntimeException("el medico no se encuentra en la base de datos"));

		// buscamos la especialidad
		especialidad especialidad = espRep.findById(request.getEspecialidadId())
				.orElseThrow(() -> new RuntimeException("no se encuentra la especialidad seleccionada"));
		// buscamos la organizacion y le damos al medico
		Organizacion organizacion = orgRep.findById(request.getOrganizacionId())
				.orElseThrow(() -> new RuntimeException("no se encuentra la organizacion seleccionada"));

		// setteamos los campos para actualizarlos

		medicoBuscado.setApellido(request.getApellido());
		medicoBuscado.setEspecialidad(especialidad);
		medicoBuscado.setMatricula(request.getMatricula());
		medicoBuscado.setOrganizacion(organizacion);
		medicoBuscado.setNombre(request.getNombre());

		// guardamos los cambios

		Medico medicoActualizado = medRep.save(medicoBuscado);

		return MedicoMapper.toResponse(medicoActualizado);
	}

	// ========================================================================================
	// ================================ metodo para eliminar fisicamente un medico
	// ============
	// ========================================================================================

	public void eliminarMedico(Long id) {

		// buscamos al medico por su id
		Medico medicoEliminado = medRep.findById(id)
				.orElseThrow(() -> new RuntimeException("el medico no se encuentra en la base de datos"));
		//

		// eliminamos el registro de la base de datos
		medRep.delete(medicoEliminado);

	}

	// ========================================================================================
	// ================================ metodo para eliminar logicamente un medico
	// ============
	// ========================================================================================

	public void eliminarLogicamente(Long id) {
		// buscamos al medico por su id
		Medico medicoEliminado = medRep.findById(id)
				.orElseThrow(() -> new RuntimeException("el medico no se encuentra en la base de datos"));

		medicoEliminado.setActivo(false);

		medRep.save(medicoEliminado);
	}

	// ==============================================================
	// ============ metodo para buscar solo los medicos activos======
	// ==============================================================

	public List<MedicoResponseDto> medicosActivos(Long id) {

		// obtenemos todos los medicos de una organizacion
		List<Medico> listaMedicos = medRep.findByOrganizacionIdAndActivoTrue(id);

		// creamos la lista respuesta
		List<MedicoResponseDto> listaRespuesta = new ArrayList<>();

		// recorremos la lista y mapeamos
		for (Medico med : listaMedicos) {
			listaRespuesta.add(MedicoMapper.toResponse(med));
		}

		return listaRespuesta;
	}
	
	//=================================================================
	//================== activar medico ===============================
	//=================================================================
	
	public void activarMedico(Long id) {
		
		//buscamos el medico
		Medico medicoBuscado = medRep.findById(id).orElseThrow(()-> new RuntimeException("el medico buscado no se encuentra en la base de datos"));
		
		//activamos en medico
		medicoBuscado.setActivo(true);
		
		//guardamos los cambios
		medRep.save(medicoBuscado);
		
		
	}
}
