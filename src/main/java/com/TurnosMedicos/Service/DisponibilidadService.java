package com.TurnosMedicos.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.TurnosMedicos.Dto.DisponibilidadRequestDto;
import com.TurnosMedicos.Dto.DisponibilidadResponseDto;
import com.TurnosMedicos.Exception.HorarioNoDisponibleException;
import com.TurnosMedicos.Repository.DisponibilidadRepository;
import com.TurnosMedicos.Repository.medicoRepository;
import com.TurnosMedicos.Repository.turnoRepository;
import com.TurnosMedicos.models.Disponibilidad;
import com.TurnosMedicos.models.Medico;
import com.TurnosMedicos.models.Turno;

@Service
public class DisponibilidadService {

	
	private final DisponibilidadRepository dispoRepo;
	private final medicoRepository medicoRepo;
	private final turnoRepository turnoRepo;
	
	  // 🔥 CONSTRUCTOR
    public DisponibilidadService(DisponibilidadRepository dispoRepo,
                                 medicoRepository medicoRepo,
                                 turnoRepository turnoRepo) {
        this.dispoRepo = dispoRepo;
        this.medicoRepo = medicoRepo;
        this.turnoRepo = turnoRepo;
    }
    
    // ==================================================================
    // 🔎 VALIDAR SI EL HORARIO EXISTE EN LA DISPONIBILIDAD DEL MEDICO
    // ==================================================================
    
    public void ValidarTurnosConMedico(Long medicoId,LocalDate fecha, LocalTime hora ,Long orgId) {
    	
    	DayOfWeek dia =  fecha.getDayOfWeek();
    	
    	List<Disponibilidad> disponibilidades = dispoRepo.findByMedicoIdAndOrganizacionId(medicoId, orgId);
    	
    	// FILTRAR POR ORGANIZACION 
    	
    	disponibilidades = disponibilidades.stream()
    			.filter(d -> d.getMedico().getOrganizacion().getId().equals(orgId))
    			.toList();
    	

        boolean disponible = disponibilidades.stream().anyMatch(d ->
                d.getDiaSemana().name().equals(dia.name()) &&
                !hora.isBefore(d.getHoraInicio()) &&
                !hora.isAfter(d.getHoraFinal())
        );
        
        if (!disponible) {
            throw new HorarioNoDisponibleException("El médico no atiende en ese día/hora");
        }
    	
    }
    
    // ==================================================================
    // 📅 OBTENER HORARIOS DISPONIBLES PARA EL FRONT
    // ==================================================================
    
    public List<String> ObtenerHorariosDisponibles(Long medicoId , LocalDate fecha , Long orgId){
    	
    	// VALIDAR MEDICO 
    	
    	Medico medico = medicoRepo.findById(medicoId)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

        if (!medico.getOrganizacion().getId().equals(orgId)) {
            throw new RuntimeException("El medico no pertenece a la organización");
        }
        
        //TRER DISPONIBILIDAD 
        List<Disponibilidad> disponibilidades = dispoRepo.findByMedicoIdAndOrganizacionId(medicoId, orgId);
        
        // 3️⃣ FILTRAR POR ORGANIZACIÓN
        disponibilidades = disponibilidades.stream()
                .filter(d -> d.getMedico().getOrganizacion().getId().equals(orgId))
                .toList();

        if (disponibilidades.isEmpty()) {
            return Collections.emptyList();
        }
        
        
        // 4️⃣ FILTRAR POR DIA
        DayOfWeek day = fecha.getDayOfWeek();

        List<Disponibilidad> delDia = disponibilidades.stream()
                .filter(d -> d.getDiaSemana().name().equals(day.name()))
                .toList();

        if (delDia.isEmpty()) {
            return Collections.emptyList();
        }
        
        // 5️⃣ GENERAR TODOS LOS HORARIOS POSIBLES
        List<LocalTime> horarios = new ArrayList<>();

        for (Disponibilidad d : delDia) {

            LocalTime hora = d.getHoraInicio();

            while (hora.isBefore(d.getHoraFinal())) {
                horarios.add(hora);
                hora = hora.plusMinutes(d.getDuracionMinutos());
            }
        }
        
        // 6️⃣ TRAER TURNOS OCUPADOS
        List<Turno> turnosOcupados =
                turnoRepo.findByMedicoIdAndFechaAndOrganizacionId(medicoId, fecha, orgId);

        // 7️⃣ CONVERTIR A SET (para búsqueda rápida)
        Set<LocalTime> ocupados = turnosOcupados.stream()
                .map(Turno::getHora)
                .collect(Collectors.toSet());

        // 8️⃣ FILTRAR SOLO LOS LIBRES 🔥 (ACÁ TENÍAS EL BUG ANTES)
        List<LocalTime> disponibles = horarios.stream()
                .filter(h -> !ocupados.contains(h))
                .toList();

        // 9️⃣ DEVOLVER COMO STRING (para frontend)
        return disponibles.stream()
                .map(LocalTime::toString)
                .toList();


    }
    
    //============================================================
    //============ LISTAR DISPONIBILIDAD =========================
    // ===========================================================
    
    public List<DisponibilidadResponseDto> listarDisponibilidad(Long medicoId, Long orgId) {

        List<Disponibilidad> lista = dispoRepo.findByMedicoIdAndOrganizacionId(medicoId , orgId);

        return lista.stream()
                .filter(d -> d.getMedico().getOrganizacion().getId().equals(orgId))
                .map(d -> new DisponibilidadResponseDto(
                        d.getId(),
                        d.getMedico().getNombre(),
                        d.getDiaSemana(),
                        d.getHoraInicio() + " - " + d.getHoraFinal(),
                        d.getDuracionMinutos()
                ))
                .toList();
    }
    
    
    //============================================================
    //============ CREAR  DISPONIBILIDAD =========================
    // ===========================================================
    
    public DisponibilidadResponseDto crearDisponibilidad(DisponibilidadRequestDto dto, Long orgId) {

        Medico medico = medicoRepo.findById(dto.getMedicoId())
                .orElseThrow(() -> new RuntimeException("Medico no encontrado"));

        if (!medico.getOrganizacion().getId().equals(orgId)) {
            throw new RuntimeException("El medico no pertenece a la organización");
        }

        // VALIDACIÓN PRO 🔥 (evita solapamientos)
        List<Disponibilidad> existentes = dispoRepo.findByMedicoIdAndOrganizacionId(medico.getId() , orgId);

        boolean solapado = existentes.stream().anyMatch(d ->
                d.getDiaSemana() == dto.getDiaSemana() &&
                dto.getHoraInicio().isBefore(d.getHoraFinal()) &&
                dto.getHoraFin().isAfter(d.getHoraInicio())
        );

        if (solapado) {
            throw new RuntimeException("Ya existe una disponibilidad en ese rango horario");
        }

        Disponibilidad d = new Disponibilidad();
        d.setOrganizacion(null);
        d.setMedico(medico);
        d.setDiaSemana(dto.getDiaSemana());
        d.setHoraInicio(dto.getHoraInicio());
        d.setHoraFinal(dto.getHoraFin());
        d.setDuracionMinutos(dto.getDuracionMinutos());
     // 🔥 CLAVE
        d.setOrganizacion(medico.getOrganizacion());

        Disponibilidad guardado = dispoRepo.save(d);

        return new DisponibilidadResponseDto(
                guardado.getId(),
                medico.getNombre(),
                guardado.getDiaSemana(),
                guardado.getHoraInicio() + " - " + guardado.getHoraFinal(),
                guardado.getDuracionMinutos()
        );
    }
    
    
    
    //============================================================
    //============ ELIMINAR DISPONIBILIDAD =========================
    // ===========================================================
    
    public void eliminarDisponibilidad(Long id, Long orgId) {

        Disponibilidad d = dispoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("No encontrada"));

        if (!d.getMedico().getOrganizacion().getId().equals(orgId)) {
            throw new RuntimeException("No pertenece a tu organización");
        }

        dispoRepo.delete(d);
    }
    
}
