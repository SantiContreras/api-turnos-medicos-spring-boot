package com.TurnosMedicos.Service;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.TurnosMedicos.Dto.OrganizacionRequest;
import com.TurnosMedicos.Dto.OrganizacionResponse;
import com.TurnosMedicos.Mapper.OrganizacionMapper;
import com.TurnosMedicos.Repository.OrganizacionRepository;
import com.TurnosMedicos.models.Organizacion;

@Service
public class OrganizacionService {

	private final OrganizacionRepository orgRep;

	public OrganizacionService(OrganizacionRepository orgRep) {
		this.orgRep = orgRep;
	}

	// ==========================================================================
	// ============= METODO PARA CREAR UNA ORGANIZACION =========================
	// ==========================================================================

	public OrganizacionResponse crearOrganizacion(OrganizacionRequest request) {

		// primero verificamos que exista la organizacion
		boolean existe = orgRep.existsByNombre(request.getNombre());
		if (existe) {
			throw new RuntimeException("Error la organizacion ya existe");
		}

		// segundo para creamos el objeto para guardar en la base de datos.

		Organizacion org = OrganizacionMapper.toEntity(request);

		// ahora guardamos en la base de datos llamamos al repositorio.

		Organizacion guardada = orgRep.save(org);

		// retornamos el objeto de devolver mappeado.
		return OrganizacionMapper.toResponse(guardada);

	}

	// ==========================================================================
	// ============= METODO PARA LISTAR UNA ORGANIZACION =========================
	// ==========================================================================
	public List<OrganizacionResponse> listarOrganizaciones() {

		// obtenemos todas las organizaciones
		List<Organizacion> organizaciones = orgRep.findAll();

		// la mappeamos pero primero usamos un for , en otra vamos a usar un stream

		List<OrganizacionResponse> respuesta = new ArrayList<>();

		// recorremos la lista de organizaciones

		for (Organizacion org : organizaciones) {

			respuesta.add(OrganizacionMapper.toResponse(org));
		}

		// retornamos la lista mappeada

		return respuesta;
	}

	// ==========================================================================
	// ============= METODO PARA LISTAR UNA ORGANIZACION POR ID =================
	// =========================================================================
	public OrganizacionResponse buscarPorId(Long id) {
		// buscamos la organizacion
		Organizacion organizacion = orgRep.findById(id)
				.orElseThrow(() -> new RuntimeException("no se encuentra la organizacion"));

		// retornamos el mapper con la respuesta
		return OrganizacionMapper.toResponse(organizacion);
	}

	// ==========================================================================
	// ============= METODO PARA ACTUALIZAR UNA ORGANIZACION =================
	// =========================================================================

	public OrganizacionResponse actualizarOrganizacion(Long id, OrganizacionRequest request) {
		// recibimos el id y buscamos la organizacion
		Organizacion organizacion = orgRep.findById(id)
				.orElseThrow(() -> new RuntimeException("no se encuentra la organizacion en la base de datos"));

		// modificamos los atributos de la organizacion encontrada
		organizacion.setNombre(request.getNombre());

		organizacion.setEmail(request.getEmail());

		organizacion.setDireccion(request.getDireccion());

		organizacion.setTelefono(request.getTelefono());

		organizacion.setLogo(request.getLogo());

		organizacion.setCiudad(request.getCiudad());

		organizacion.setProvincia(request.getProvincia());

		organizacion.setPais(request.getPais());

		// guardamos el objeto
		orgRep.save(organizacion);

		// retornamos la respuesta
		return OrganizacionMapper.toResponse(organizacion);

	}

	// =========================================================================
	// ======= METODO PARA ELIMINAR / DESACTIVAR LOGICAMENTE UNA ORGANIZACION ==
	// =========================================================================

	public OrganizacionResponse desactivarOrganizacion(Long id) {
		// buscamos la organizacion
		Organizacion org = orgRep.findById(id)
				.orElseThrow(() -> new RuntimeException("La organizacion no se encuentra en la base de datos"));

		// seteamos su atributo en este caso estado = desactivado
		org.setActiva(false);

		// guardamos los cambios

		Organizacion guardada = orgRep.save(org);

		// retornamos la respuesta

		return OrganizacionMapper.toResponse(guardada);

	}

	// =========================================================================
	// ======= METODO PARA ACTIVAR LOGICAMENTE UNA ORGANIZACION ==
	// =========================================================================
	public OrganizacionResponse activarOrganizacion(Long id) {
		// buscamos la organizacion
		Organizacion org = orgRep.findById(id)
				.orElseThrow(() -> new RuntimeException("La organizacion no se encuentra en la base de datos"));

		// seteamos su atributo en este caso estado = desactivado
		org.setActiva(true);

		// guardamos los cambios

		Organizacion guardada = orgRep.save(org);

		// retornamos la respuesta

		return OrganizacionMapper.toResponse(guardada);

	}

	// =========================================================================
	// ======= METODO BUSCAR LAS ORGANIZACIONES ACTIVAS ========================
	// =========================================================================

	public List<OrganizacionResponse> listarOrganizacionesActivas() {
		// buscamos las organizaciones
		List<Organizacion> organizaciones = orgRep.findByActivaTrue();

		// creamos una lista para mappearlas y devolverlas
		List<OrganizacionResponse> lista = new ArrayList<>();

		// recorremos la lista de organizaciones y mappeamos
		for (Organizacion org : organizaciones) {
			OrganizacionResponse orgResp = OrganizacionMapper.toResponse(org);
			lista.add(orgResp);

		}

		// retornamos la lista
		return lista;

	}

	// =========================================================================
	// ======= METODO BUSCAR POR ENTRADA DE TEXTO POR EL USUARIO ===============
	// =========================================================================

	public List<OrganizacionResponse> buscarPorNombre(String nombre) {
		// buscamos con el parametro ingresado
		List<Organizacion> organizaciones = orgRep.findByNombreContainingIgnoreCase(nombre);
		// creamos una lista para mappearlas y devolverlas
		List<OrganizacionResponse> lista = new ArrayList<>();
		// recorremos la lista de organizaciones y mappeamos
		for (Organizacion org : organizaciones) {
			OrganizacionResponse orgResp = OrganizacionMapper.toResponse(org);
			lista.add(orgResp);

		}
		
		//retornamos
		return lista;

	}
	

	// =========================================================================
	// ======= METODO LISTAR LAS ORGANIZACIONES POR PAGINACION ===============
	// =========================================================================
	
	public Page<OrganizacionResponse> listaOrganizacionPaginada(Pageable pageable){
		
		//buscamos todas las organizaciones 
		Page<Organizacion> pagina = orgRep.findAll(pageable); 
		
		//devolvemos la pagina con los datos convertidos 
		return pagina.map(OrganizacionMapper::toResponse);
	}

}
