package com.TurnosMedicos.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.TurnosMedicos.Dto.TurnoResponseDto;
import com.TurnosMedicos.Dto.turnoRequestDTO;
import com.TurnosMedicos.Exception.HorarioNoDisponibleException;
import com.TurnosMedicos.Exception.RecursoNoEncontradoException;
import com.TurnosMedicos.Exception.TurnoDuplicadoException;
import com.TurnosMedicos.Exception.TurnoFechaImvalidaException;
import com.TurnosMedicos.Mapper.MapperTurnos;
import com.TurnosMedicos.Repository.medicoRepository;
import com.TurnosMedicos.Repository.pacienteRepository;
import com.TurnosMedicos.Repository.turnoRepository;
import com.TurnosMedicos.Specification.TurnoSpecification;
import com.TurnosMedicos.models.EstadoTurno;
import com.TurnosMedicos.models.Turno;
import com.TurnosMedicos.models.medico;
import com.TurnosMedicos.models.paciente;


@Service
public class turnoService {

	private final medicoRepository medicoRepo;
	private final turnoRepository turnoRepo;
	private final pacienteRepository pacienteRepo;

	public turnoService(turnoRepository turnoRepo, medicoRepository medicoRepo, pacienteRepository pacienteRepo) {

		this.medicoRepo = medicoRepo;
		this.turnoRepo = turnoRepo;
		this.pacienteRepo = pacienteRepo;
	}

	public List<Turno> listarTurnos() {
		return turnoRepo.findAll();
	}

	public Turno guardar(Turno tu) {
		return turnoRepo.save(tu);
	}

	public Turno cancelar(Long id) {
		Turno turnoBuscado = turnoRepo.findById(id)
				.orElseThrow(() -> new RecursoNoEncontradoException("Turno no encontrado"));
		turnoBuscado.setEstado(EstadoTurno.CANCELADO);

		return turnoRepo.save(turnoBuscado);

	}

	// servicio para crear un turno
	public Turno crearTurno(Turno tu) {
		Long medicoId = tu.getMedico().getId();

		boolean existe = turnoRepo.existsByMedicoIdAndFechaAndHora(medicoId, tu.getFecha(), tu.getHora());

		if (existe) {
			throw new TurnoDuplicadoException("El medico ya tiene un turno reservado en esa hora y fecha");
		}

		// vuelve al estado inicial
		tu.setEstado(EstadoTurno.PENDIENTE);

		return turnoRepo.save(tu);
	}

	// servicio para crear un turno pero usando Dtos y mappers

	public TurnoResponseDto CrearElTurno(turnoRequestDTO dto) {

		// buscamos al medico
		medico medico = medicoRepo.findById(dto.getMedicoId())
				.orElseThrow(() -> new RecursoNoEncontradoException("Médico no encontrado"));
		// bucamos al paciente
		paciente paciente = pacienteRepo.findById(dto.getPacienteId())
				.orElseThrow(() -> new RecursoNoEncontradoException("Paciente no encontrado"));

		// validamos Fecha Y hora del turno y horario disponible del medico

		ValidarFechaYHora(dto);
		ValidarHorarioDisponible(dto.getHora());
		ValidarTurnoActivo(medico.getId(), dto.getFecha(), dto.getHora());

		// 2️⃣ Validación de duplicados (MISMA lógica)
		boolean existe = turnoRepo.existsByMedicoIdAndFechaAndHora(medico.getId(), dto.getFecha(), dto.getHora());

		if (existe) {
			throw new TurnoDuplicadoException("El médico ya tiene un turno en esa fecha y hora");
		}

		// 3️⃣ Armar la entidad
		Turno t = new Turno();
		t.setMedico(medico);
		t.setPaciente(paciente);
		t.setEstado(EstadoTurno.PENDIENTE);
		t.setFecha(dto.getFecha());
		t.setHora(dto.getHora());

		// 4️⃣ Guardar y mapear a DTO
		Turno turnoGuardado = turnoRepo.save(t);

		return MapperTurnos.toDTO(turnoGuardado);

	}

	// servicio para marcar un turno atendido

	public Turno marcarTurnoComoAtendido(Long id) {
		Turno turnoBuscado = turnoRepo.findById(id)
				.orElseThrow(() -> new RecursoNoEncontradoException("turno no encontrado"));

		if (turnoBuscado.getEstado() == EstadoTurno.CANCELADO) {
			throw new IllegalStateException("Error no se puede atender un turno cancelado");
		}

		turnoBuscado.setEstado(EstadoTurno.ATENDIDO);
		return turnoRepo.save(turnoBuscado);

	}

	// metodo para validar que no sean fechas pasadas

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

	// metodo para validar el horario disponible

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

	// metodo para validar el turno activo

	public void ValidarTurnoActivo(Long medicoId, LocalDate fecha, LocalTime hora) {

		boolean ocupado = turnoRepo.existsByMedicoIdAndFechaAndHoraAndEstadoNot(medicoId, fecha, hora,
				EstadoTurno.CANCELADO);

		if (ocupado) {
			throw new TurnoDuplicadoException("el medico ya tiene turno asignado en ese horario");

		}
	}

	// metedo para listar los turno por algun parametro (page)

	public Page<TurnoResponseDto> listarTurnos(Long medicoId, LocalDate fecha, EstadoTurno estado, Pageable pageable,  LocalDate fechaDesde,
	        LocalDate fechaHasta) {
	

		Specification<Turno> spec = Specification
	            .where(TurnoSpecification.tieneMedico(medicoId))
	            .and(TurnoSpecification.tieneFecha(fecha))
	            .and(TurnoSpecification.tieneEstado(estado))
	            .and(TurnoSpecification.fechaEntre(fechaDesde, fechaHasta));

	    Page<Turno> page = turnoRepo.findAll(spec, pageable);

	    return page.map(MapperTurnos::toDTO);

	  

	}
}
