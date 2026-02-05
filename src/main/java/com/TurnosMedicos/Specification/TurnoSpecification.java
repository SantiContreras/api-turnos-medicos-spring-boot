package com.TurnosMedicos.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.TurnosMedicos.models.EstadoTurno;
import com.TurnosMedicos.models.Turno;

import jakarta.persistence.criteria.Predicate;

public class TurnoSpecification {

	public static Specification<Turno> tieneMedico(Long medicoId) {

		return (root, query, cb) -> medicoId == null ? null : cb.equal(root.get("medico").get("id"), medicoId);
	}

	public static Specification<Turno> tieneFecha(LocalDate fecha) {
		return (root, query, cb) -> fecha == null ? null : cb.equal(root.get("fecha"), fecha);
	}

	public static Specification<Turno> tieneEstado(EstadoTurno estado) {
		return (root, query, cb) -> estado == null ? null : cb.equal(root.get("estado"), estado);
	}

	public static Specification<Turno> fechaEntre(LocalDate fechaDesde, LocalDate fechaHasta) {

		return (root, query, cb) -> {

			if (fechaDesde == null && fechaHasta == null) {
				return null;
			}

			if (fechaDesde != null && fechaHasta != null) {
				return cb.between(root.get("fecha"), fechaDesde, fechaHasta);
			}

			if (fechaDesde != null) {
				return cb.greaterThanOrEqualTo(root.get("fecha"), fechaDesde);
			}

			return cb.lessThanOrEqualTo(root.get("fecha"), fechaHasta);
		};
	}

}
