package com.TurnosMedicos.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.TurnosMedicos.Dto.EspecialidadRequest;
import com.TurnosMedicos.Dto.EspecialidadResponse;
import com.TurnosMedicos.Dto.OrganizacionResponse;
import com.TurnosMedicos.Service.especialidadService;
import com.TurnosMedicos.models.especialidad;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/especialidades")
@CrossOrigin("*")
public class especialidadController {

	private final especialidadService espSer;

	public especialidadController(especialidadService espSer) {
		this.espSer = espSer;
	}

	// ==============================================================
	// ============= post para crear una especialidad ===============
	// ===============================================================
	@PostMapping
	@PreAuthorize("hasRole('ADMIN_GLOBAL')")
	public ResponseEntity<EspecialidadResponse> crear(@Valid @RequestBody EspecialidadRequest request) {

		return ResponseEntity.status(HttpStatus.CREATED).body(espSer.crearEspecialidad(request));

	}

	// ========================================================================
	// =============== get para obtener la lista completa =====================
	// ========================================================================
	@GetMapping
	@PreAuthorize("hasRole('ADMIN_GLOBAL')")
	public ResponseEntity<List<EspecialidadResponse>> listarTodas() {
		List<EspecialidadResponse> lista = espSer.listarEspecialidades();

		return ResponseEntity.ok(lista);
	}

	// =======================================================================
	// ========= obtenemos una especialidad por id ===========================
	// =======================================================================

	@GetMapping("/buscar/{id}")
	@PreAuthorize("hasRole('ADMIN_GLOBAL')")
	public ResponseEntity<EspecialidadResponse> busquedaPorid(@PathVariable Long id) {

		return ResponseEntity.ok(espSer.especialidadPorId(id));
	}

	// =======================================================================
	// ======================= end point para actualizar =====================
	// =======================================================================

	@PutMapping("/actualizar/{id}")
	@PreAuthorize("hasRole('ADMIN_GLOBAL')")
	public ResponseEntity<EspecialidadResponse> actualizar(@PathVariable Long id,
			@Valid @RequestBody EspecialidadRequest request) {

		return ResponseEntity.ok(espSer.actualizarEspecialidad(id, request));
	}

	// =======================================================================
	// =============== End point borrar logicamente una especialidad =========
	// =======================================================================

	@PatchMapping("/desactivar/{id}")
	@PreAuthorize("hasRole('ADMIN_GLOBAL')")
	public ResponseEntity<EspecialidadResponse> desactivar(@PathVariable Long id) {

		return ResponseEntity.ok(espSer.eliminarLogicamente(id));
	}
	
	// =======================================================================
	// =============== End point activar una especialidad ====================
	// =======================================================================
	

	@PatchMapping("/desactivar/{id}")
	@PreAuthorize("hasRole('ADMIN_GLOBAL')")
	public ResponseEntity<EspecialidadResponse> activar(@PathVariable Long id) {

		return ResponseEntity.ok(espSer.activarEspecialidad(id));
	}
	

}
