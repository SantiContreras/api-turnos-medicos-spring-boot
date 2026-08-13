package com.TurnosMedicos.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.TurnosMedicos.Dto.MedicoRequest;
import com.TurnosMedicos.Dto.MedicoResponseDto;
import com.TurnosMedicos.Service.medicoService;
import com.TurnosMedicos.models.Medico;

import jakarta.validation.Valid;

@RestController()
@RequestMapping("/api/medicos")
@CrossOrigin("*")
public class medicoController {

	private final medicoService medSer;

	public medicoController(medicoService medSer) {
		this.medSer = medSer;
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN_GLOBAL')")
	public ResponseEntity<List<MedicoResponseDto>> listar() {

		return ResponseEntity.ok(medSer.listar());

	}

	@GetMapping("/organizacion/{organizacionId}")
	@PreAuthorize("hasRole('ADMIN_GLOBAL')")
	public ResponseEntity<List<MedicoResponseDto>> listarOrganizacion(@PathVariable Long organizacionId) {

		return ResponseEntity.ok(medSer.listarPorOrganizacion(organizacionId));
	}

	@PostMapping
	public ResponseEntity<MedicoResponseDto> crearMedico(@Valid @RequestBody MedicoRequest request) {

		return ResponseEntity.status(HttpStatus.CREATED).body(medSer.crearMedico(request));
	}

	@GetMapping("/buscar/{id}")
	@PreAuthorize("hasRole('ADMIN_GLOBAL')")
	public ResponseEntity<MedicoResponseDto> buscarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(medSer.buscarMedicoPorId(id));
	}

	@PutMapping("/actualizar/{id}")
	@PreAuthorize("hasRole('ADMIN_GLOBAL')")
	public ResponseEntity<MedicoResponseDto> actualizar(@PathVariable Long id,
			@Valid @RequestBody MedicoRequest request) {
		return ResponseEntity.ok(medSer.actualizarMedico(request, id));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN_GLOBAL')")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		medSer.eliminarMedico(id);
		return ResponseEntity.noContent().build();
	}
	
	@PatchMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN_GLOBAL')")
	public ResponseEntity<Void> eliminarLogicamente(@PathVariable Long id) {
		medSer.eliminarLogicamente(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/medicosActivos/{organizacionId}")
	@PreAuthorize("hasRole('ADMIN_GLOBAL')")
	public ResponseEntity<List<MedicoResponseDto>> listarOrganizacionActivos(@PathVariable Long organizacionId) {

		return ResponseEntity.ok(medSer.medicosActivos(organizacionId));
	}
	
	@PatchMapping("/activar/{id}")
	@PreAuthorize("hasRole('ADMIN_GLOBAL')")
	public ResponseEntity<Void> activarMedico(@PathVariable  Long id){
		medSer.activarMedico(id);
		return ResponseEntity.noContent().build();
	}

}
