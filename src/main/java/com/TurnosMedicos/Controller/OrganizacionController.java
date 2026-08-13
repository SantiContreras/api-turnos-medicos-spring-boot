package com.TurnosMedicos.Controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TurnosMedicos.Dto.OrganizacionRequest;
import com.TurnosMedicos.Dto.OrganizacionResponse;
import com.TurnosMedicos.Repository.OrganizacionRepository;
import com.TurnosMedicos.Service.OrganizacionService;
import com.TurnosMedicos.models.Organizacion;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/admin/organizaciones")
public class OrganizacionController {

	private final OrganizacionService organizacionService;
	

    public OrganizacionController( OrganizacionService organizacionService) {
       
        this.organizacionService = organizacionService;
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN_GLOBAL')")
    public ResponseEntity<OrganizacionResponse> CrearOrganizacion( @Valid @RequestBody OrganizacionRequest request) {
    	
    	OrganizacionResponse nuevaOrg = organizacionService.crearOrganizacion(request);
    	
		return ResponseEntity.status(HttpStatus.CREATED).body(nuevaOrg);
    }
    
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN_GLOBAL')")
    public ResponseEntity<List<OrganizacionResponse>> listar(){
    	
    	List<OrganizacionResponse> lista = organizacionService.listarOrganizaciones();
    	
		return ResponseEntity.ok(lista);
    	
    	
    }
    

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN_GLOBAL')")
    public ResponseEntity<OrganizacionResponse> buscarPorid(@PathVariable Long id){
    	
    	return ResponseEntity.ok(organizacionService.buscarPorId(id));
    }
    
    @PutMapping("/actualizar/{id}")
    @PreAuthorize("hasRole('ADMIN_GLOBAL')")
    public ResponseEntity<OrganizacionResponse> actualizar(@Valid @RequestBody OrganizacionRequest request , @PathVariable Long id ) {
    	
    	return ResponseEntity.ok(organizacionService.actualizarOrganizacion(id, request));
    	
    }
    
    @PatchMapping("/desactivar/{id}")
    @PreAuthorize("hasRole('ADMIN_GLOBAL')")
    public ResponseEntity<OrganizacionResponse> desactivar(@PathVariable Long id){
    	
    	return ResponseEntity.ok(organizacionService.desactivarOrganizacion(id));
    }
    
    @PatchMapping("/activar/{id}")
    @PreAuthorize("hasRole('ADMIN_GLOBAL')")
    public ResponseEntity<OrganizacionResponse> activar(@PathVariable Long id){
    	
    	return ResponseEntity.ok(organizacionService.activarOrganizacion(id));
    }
    
    @GetMapping("/activas")
    @PreAuthorize("hasRole('ADMIN_GLOBAL')")
    public ResponseEntity<List<OrganizacionResponse>>  listaActivas(){
    	
    	return ResponseEntity.ok(organizacionService.listarOrganizacionesActivas());
    }
    
    @GetMapping("/buscar")
    @PreAuthorize("hasRole('ADMIN_GLOBAL')")
    public ResponseEntity<List<OrganizacionResponse>> busquedaPorNombre(@RequestParam String nombre){
    	return ResponseEntity.ok(organizacionService.buscarPorNombre(nombre));
    }
    
    @GetMapping("/paginado")
    @PreAuthorize("hasRole('ADMIN_GLOBAL')")
    public ResponseEntity<Page<OrganizacionResponse>> listarPaginado(
    		@PageableDefault(page = 0 ,size=10, sort="nombre" , direction= Sort.Direction.ASC) Pageable pageable
    		
    		){
    	
    	return ResponseEntity.ok(organizacionService.listaOrganizacionPaginada(pageable));
    }
}
