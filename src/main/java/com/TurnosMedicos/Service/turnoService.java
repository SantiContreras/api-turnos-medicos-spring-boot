package com.TurnosMedicos.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.catalina.mapper.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.TurnosMedicos.Dto.AgendaDto;
import com.TurnosMedicos.Dto.AgendaDtoResponse;
import com.TurnosMedicos.Dto.TurnoResponseDto;
import com.TurnosMedicos.Dto.turnoRequestDTO;
import com.TurnosMedicos.Exception.HorarioNoDisponibleException;
import com.TurnosMedicos.Exception.RecursoNoEncontradoException;
import com.TurnosMedicos.Exception.TurnoDuplicadoException;
import com.TurnosMedicos.Exception.TurnoFechaImvalidaException;
import com.TurnosMedicos.Mapper.MapperTurnos;
import com.TurnosMedicos.Repository.DisponibilidadRepository;
import com.TurnosMedicos.Repository.medicoRepository;
import com.TurnosMedicos.Repository.pacienteRepository;
import com.TurnosMedicos.Repository.turnoRepository;
import com.TurnosMedicos.Specification.TurnoSpecification;
import com.TurnosMedicos.models.Disponibilidad;
import com.TurnosMedicos.models.EstadoTurno;
import com.TurnosMedicos.models.Medico;
import com.TurnosMedicos.models.Organizacion;
import com.TurnosMedicos.models.Turno;

import com.TurnosMedicos.models.paciente;

@Service
public class turnoService {

	private final medicoRepository medicoRepo;
	private final turnoRepository turnoRepo;
	private final pacienteRepository pacienteRepo;
	private final DisponibilidadRepository dispoRepo;

	public turnoService(turnoRepository turnoRepo, medicoRepository medicoRepo, pacienteRepository pacienteRepo,
			DisponibilidadRepository dispoRepo) {

		this.medicoRepo = medicoRepo;
		this.turnoRepo = turnoRepo;
		this.pacienteRepo = pacienteRepo;
		this.dispoRepo = dispoRepo;
	}

	public List<Turno> listarTurnos() {
		return turnoRepo.findAll();
	}

	public Turno guardar(Turno tu) {
		return turnoRepo.save(tu);
	}

	// ==================================================================
	// ===================CANCELAR TURNO ================================
	// ==================================================================

	public Turno cancelar(Long id, Long idOrg) {

		// buscamos el turno
		Turno turno = turnoRepo.findById(id).orElseThrow(() -> new RuntimeException("Turno no encontrado"));
		// verifgicamos que pertenesca a esa organizaacion
		if (!turno.getOrganizacion().getId().equals(idOrg)) {
			throw new RuntimeException("No pertenece a tu organización");
		}

		turno.setEstado(EstadoTurno.CANCELADO);

		return turnoRepo.save(turno);

	}

	// ==================================================================
	// ===================CREAR TURNO 1 ================================
	// ==================================================================
	public TurnoResponseDto crearTurno(turnoRequestDTO turnoRequest, Long idOrg) {

		paciente paciente = pacienteRepo.findById(turnoRequest.getPacienteId())
				.orElseThrow(() -> new RuntimeException("paciente no encontrado"));

		if (!paciente.getOrganizacion().getId().equals(idOrg)) {
			throw new RuntimeException("Paciente no pertenece a tu organización");
		}
		Medico medico = medicoRepo.findById(turnoRequest.getMedicoId())
				.orElseThrow(() -> new RuntimeException("Medico no encontrado"));

		if (!medico.getOrganizacion().getId().equals(idOrg)) {
			throw new RuntimeException("El medico no pertenece a tu organización");
		}

		Turno turno = new Turno();
		turno.setFecha(turnoRequest.getFecha());
		turno.setHora(turnoRequest.getHora());
		turno.setPaciente(paciente);
		turno.setMedico(medico);

		Organizacion org = new Organizacion();
		org.setId(idOrg);
		turno.setOrganizacion(org);

		Long medicoId = turnoRequest.getMedicoId();

		boolean existe = turnoRepo.existsByMedicoIdAndFechaAndHora(medicoId, turnoRequest.getFecha(),
				turnoRequest.getHora());

		if (existe) {
			throw new TurnoDuplicadoException("El medico ya tiene un turno reservado en esa hora y fecha");
		}

		// vuelve al estado inicial
		turno.setEstado(EstadoTurno.PENDIENTE);

		return MapperTurnos.toDTO(turnoRepo.save(turno));
	}

	// ==================================================================
	// ===================CANCELAR TURNO 2 ================================
	// ==================================================================
	public TurnoResponseDto CrearElTurno(turnoRequestDTO dtoRequest, Long idOrg) {

		// buscamos al medico y el paciente y verificamos tambien que exista a esa
		// organizacion
		paciente paciente = pacienteRepo.findById(dtoRequest.getPacienteId())
				.orElseThrow(() -> new RuntimeException("paciente no encontrado"));

		if (!paciente.getOrganizacion().getId().equals(idOrg)) {
			throw new RuntimeException("Paciente no pertenece a tu organización");
		}
		Medico medico = medicoRepo.findById(dtoRequest.getMedicoId())
				.orElseThrow(() -> new RuntimeException("Medico no encontrado"));

		if (!medico.getOrganizacion().getId().equals(idOrg)) {
			throw new RuntimeException("El medico no pertenece a tu organización");
		}

		// validamos Fecha Y hora del turno y horario disponible del medico

		ValidarFechaYHora(dtoRequest);
		ValidarHorarioDisponible(dtoRequest.getHora());
		ValidarTurnoActivo(medico.getId(), dtoRequest.getFecha(), dtoRequest.getHora());

		// 2️⃣ Validación de duplicados (MISMA lógica)
		boolean existe = turnoRepo.existsByMedicoIdAndFechaAndHora(medico.getId(), dtoRequest.getFecha(),
				dtoRequest.getHora());

		if (existe) {
			throw new TurnoDuplicadoException("El médico ya tiene un turno en esa fecha y hora");
		}

		ValidarTurnoConMedico(medico, dtoRequest.getFecha(), dtoRequest.getHora(), idOrg);

		Organizacion org = new Organizacion();
		org.setId(idOrg);

		// 3️⃣ Armar la entidad
		Turno t = new Turno();
		t.setMedico(medico);
		t.setPaciente(paciente);
		t.setEstado(EstadoTurno.PENDIENTE);
		t.setFecha(dtoRequest.getFecha());
		t.setHora(dtoRequest.getHora());
		t.setOrganizacion(org);

		// 4️⃣ Guardar y mapear a DTO
		Turno turnoGuardado = turnoRepo.save(t);

		return MapperTurnos.toDTO(turnoGuardado);

	}

	// ==================================================================
	// ===================MARCAR TURNO COMO ATENDIDO
	// ================================
	// ==================================================================

	public Turno marcarTurnoComoAtendido(Long id) {
		Turno turnoBuscado = turnoRepo.findById(id)
				.orElseThrow(() -> new RecursoNoEncontradoException("turno no encontrado"));

		if (turnoBuscado.getEstado() == EstadoTurno.CANCELADO) {
			throw new IllegalStateException("Error no se puede atender un turno cancelado");
		}

		turnoBuscado.setEstado(EstadoTurno.ATENDIDO);
		return turnoRepo.save(turnoBuscado);

	}

	// ==================================================================
	// ===========VALIDAR QUE NO SON FECHAS PASADAS ====================
	// ==================================================================

	public void ValidarFechaYHora(turnoRequestDTO dto) {

		LocalDate hoy = LocalDate.now();
		LocalTime hora = LocalTime.now();

		if (dto.getFecha().isBefore(hoy)) {
			throw new TurnoFechaImvalidaException("No se puede crear turnos con fechas pasadas");
		}

		if (dto.getFecha().isEqual(hoy) && dto.getHora().isBefore(hora)) {
			throw new TurnoFechaImvalidaException("No se puede crear turnos con una hora pasada");

		}
	}

	// ==================================================================
	// ===================VALIDAR HORARIO DISPONIBLE ====================
	// ==================================================================

	public void ValidarHorarioDisponible(LocalTime hora) {
		LocalTime inicio = LocalTime.of(8, 0);
		LocalTime fin = LocalTime.of(18, 0);

		if (hora.isBefore(inicio) || hora.isAfter(fin)) {
			throw new HorarioNoDisponibleException("El horario debe estar entre las 8 hs y las 18 hs");
		}

		if (hora.getMinute() % 30 != 0) {
			throw new HorarioNoDisponibleException("Los turnos no deben ser mas de 30 minutos");

		}

	}

	// ==================================================================
	// ===================VALIDADAR TURNO ACTIVO=========================
	// ==================================================================

	public void ValidarTurnoActivo(Long medicoId, LocalDate fecha, LocalTime hora) {

		boolean ocupado = turnoRepo.existsByMedicoIdAndFechaAndHoraAndEstadoNot(medicoId, fecha, hora,
				EstadoTurno.CANCELADO);

		if (ocupado) {
			throw new TurnoDuplicadoException("el medico ya tiene turno asignado en ese horario");

		}
	}

	// ==================================================================
	// ===================LISTAR TURNO ================================
	// ==================================================================

	public Page<TurnoResponseDto> listarTurnos(Long medicoId, Long pacienteId, LocalDate fecha, EstadoTurno estado,
			Pageable pageable, LocalDate fechaDesde, LocalDate fechaHasta, Long idOrg) {

		Specification<Turno> spec = Specification.where(TurnoSpecification.perteneceAOrganizacion(idOrg)) // 🔥 CLAVE
																											// MULTI-TENANT
				.and(TurnoSpecification.tieneMedico(medicoId)).and(TurnoSpecification.tienePaciente(pacienteId))
				.and(TurnoSpecification.tieneFecha(fecha)).and(TurnoSpecification.tieneEstado(estado))
				.and(TurnoSpecification.fechaEntre(fechaDesde, fechaHasta));

		Page<Turno> page = turnoRepo.findAll(spec, pageable);

		return page.map(MapperTurnos::toDTO);
	}

	// ==================================================================
	// ===================AGENDA DEL MEDICO =============================
	// ==================================================================

	public List<AgendaDto> obtenerAgenda(Long medicoId, LocalDate fecha, Long orgId) {

		// 1. traer el turno existente
		List<Turno> turnos = turnoRepo.findByMedicoIdAndFecha(medicoId, fecha);

		// 2. Filtrar por organizacion .

		turnos = turnos.stream().filter(t -> t.getOrganizacion().getId().equals(orgId)).toList();

		// 3. Crear lista de horarios (ej : 8:00 am hasta 18:00 am cada 30 minutos)

		List<AgendaDto> agenda = new ArrayList<>();

		LocalTime inicio = LocalTime.of(8, 0);
		LocalTime fin = LocalTime.of(18, 0);

		while (!inicio.isAfter(fin)) {
			LocalTime horaActual = inicio;

			boolean ocupado = turnos.stream()
					.anyMatch(t -> t.getHora().equals(horaActual) && t.getEstado() != EstadoTurno.CANCELADO);

			agenda.add(new AgendaDto(horaActual.toString(), ocupado ? "ocupado" : "libre"));

			inicio = inicio.plusMinutes(30);
		}

		return agenda;

	}

	public void ValidarTurnoConMedico(Medico medico, LocalDate fecha, LocalTime hora, Long orgId) {

		DayOfWeek dia = fecha.getDayOfWeek();

		List<Disponibilidad> disponibilidades = dispoRepo.findByMedicoIdAndOrganizacionId(medico.getId(), orgId);

		boolean disponible = disponibilidades.stream().anyMatch(d -> d.getDiaSemana().name().equals(dia.name())
				&& !hora.isBefore(d.getHoraInicio()) && !hora.isAfter(d.getHoraFinal()));
	}

	public List<String> ObtenerHorariosDisponibles(Long medicoId, LocalDate fecha, Long orgId) {

		// 1️⃣ Validar médico
		Medico medico = medicoRepo.findById(medicoId).orElseThrow(() -> new RuntimeException("Médico no encontrado"));
		// validamos si pertenece a la organizacion
		if (!medico.getOrganizacion().getId().equals(orgId)) {
			throw new RuntimeException("El medico no pertenece a la organizacion");
		}

		// Obtener disponibilidad del medico

		List<Disponibilidad> disponibilidades = dispoRepo.findByMedicoIdAndOrganizacionId(medicoId, orgId);

		if (disponibilidades.isEmpty()) {
			return Collections.emptyList();
		}

		// 4° FILTRAR POR DIA DE LA SEMANA

		DayOfWeek day = fecha.getDayOfWeek();
		List<Disponibilidad> delDia = disponibilidades.stream().filter(d -> d.getDiaSemana().name().equals(day.name()))
				.toList();

		if (delDia.isEmpty()) {
			return Collections.emptyList();
		}

		// 5° GENERAR TODOS LOS HORARIOS POSIBLES

		List<LocalTime> horarios = new ArrayList<>();

		for (Disponibilidad d : delDia) {
			LocalTime hora = d.getHoraInicio();

			while (hora.isBefore(d.getHoraFinal())) {
				horarios.add(hora);
				hora = hora.plusMinutes(d.getDuracionMinutos());
			}
		}

		// 6° OBTENER TURNOS OCUPADOS

		List<Turno> turnosOcupados = turnoRepo.findByMedicoIdAndFechaAndOrganizacionId(medicoId, fecha, orgId);

		// 7° EXTRAER HORARIOS OCUPADOS

		Set<LocalTime> ocupados = turnosOcupados.stream().map(Turno::getHora).collect(Collectors.toSet());

		// 8° FILTRAR DISPONIBLES

		List<LocalTime> disponibles = horarios.stream().filter(h -> !ocupados.contains(h)).toList();

		// 9° RETORNAR EN STRING PARA EL FRONT END
		return disponibles.stream().map(LocalTime::toString).toList();

	}

	// ==================================================================
	// ===================AGENDA VISUAL=============================
	// ==================================================================

	public List<AgendaDtoResponse> obtenerAgendaVisual(Long medicoId, LocalDate fecha, Long orgId) {

		// validamos el medico

		Medico medico = medicoRepo.findById(orgId)
				.orElseThrow(() -> new RuntimeException("El medico no pertenece a la organizacion"));

		if (!medico.getOrganizacion().getId().equals(orgId)) {
			throw new RuntimeException("El medico no pertenece a la organización");
		}

		// obtener disponibilidades
		List<Disponibilidad> disponibilidades = dispoRepo.findByMedicoIdAndOrganizacionId(medicoId, orgId);

		if (disponibilidades.isEmpty())
			return Collections.emptyList();

		// filtramos por dia

		DayOfWeek day = fecha.getDayOfWeek();

		List<Disponibilidad> delDia = disponibilidades.stream().filter(d -> d.getDiaSemana().name().equals(day.name()))
				.toList();

		if (delDia.isEmpty())
			return Collections.emptyList();

		// generar todos los horarios disponibles

		List<LocalTime> horarios = new ArrayList<>();

		for (Disponibilidad d : delDia) {
			LocalTime hora = d.getHoraInicio();

			while (hora.isBefore(d.getHoraFinal())) {
				horarios.add(hora);
				hora = hora.plusMinutes(d.getDuracionMinutos());
			}
		}

		// 5️⃣ obtener turnos ocupados
		List<Turno> turnos = turnoRepo.findByMedicoIdAndFechaAndOrganizacionId(medicoId, fecha, orgId);

		// ⚠️ STREAM IMPORTANTE ACÁ
		Set<LocalTime> ocupados = turnos.stream().map(Turno::getHora).collect(Collectors.toSet());

		// 6️⃣ armar respuesta FINAL (ACA ESTA LA MAGIA 🔥)
		return horarios.stream()
				.map(hora -> new AgendaDtoResponse(hora.toString(), ocupados.contains(hora) ? "OCUPADO" : "LIBRE"))
				.toList();
	}

}
