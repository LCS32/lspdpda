package com.lspdpda.demo.service;

import java.util.List;
import java.util.Optional;

import javax.management.InstanceNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lspdpda.demo.models.entity.Civil;
import com.lspdpda.demo.models.entity.Investigacion;
import com.lspdpda.demo.models.enums.EstadoInvestigacion;
import com.lspdpda.demo.models.repository.CivilRepository;
import com.lspdpda.demo.models.repository.InvestigacionRepository;

@Service
public class InvestigacionServiceImpl implements IInvestigacionService {

	@Autowired
	private InvestigacionRepository investigacionRepository;

	@Autowired
	private CivilRepository civilRepository;

	@Override
	public List<Investigacion> listarInvestigaciones() {
		return (List<Investigacion>) investigacionRepository.findAll();
	}

	@Override
	public List<Investigacion> listarInvestigacionesActivas(Investigacion investigacion) {
		return investigacionRepository.findAllByEstadoIs(investigacion);
	}

	@Override
	@Transactional
	public void crear(Investigacion investigacion) throws IllegalStateException {
		// Busco las investigaciones activas del pavo
		List<Investigacion> investigacionesFound = investigacionRepository
				.findAllByCivilIsAndEstadoIs(investigacion.getCivil(), EstadoInvestigacion.ACTIVA);
		// Si ya tiene alguyna abierta
		if (investigacionesFound.size() > 0) {
			// lanzo error
			throw new IllegalStateException();
		}
		// por si viene con el estado vacio
		if (investigacion.getEstado() == null) {
			investigacion.setEstado(EstadoInvestigacion.ACTIVA);
		}
		Optional<Civil> civilInvestigado = civilRepository.findById(investigacion.getCivil().getId_civil());
		if (civilInvestigado.isPresent()) {
			Civil civil = civilInvestigado.get();
			civil.setInvestigacion(true);
			civil = civilRepository.save(civil);
			investigacion.setCivil(civil);
		} else {
			throw new IllegalStateException();
		}
		// si no tiene ninguna abierta, sigo sin problema
		investigacionRepository.save(investigacion);
	}

	@Override
	public void editar(Investigacion investigacion) throws IllegalStateException {
		Investigacion investigacionBD = buscarPorId(investigacion.getId_investigacion());
		if (investigacionBD != null && investigacionBD.getEstado() == EstadoInvestigacion.CERRADA) {
			throw new IllegalStateException();
		}
		if (investigacion.getEstado() == null) {
			investigacion.setEstado(EstadoInvestigacion.ACTIVA);
		}
		investigacionRepository.save(investigacion);
	}

	@Override
	public Investigacion buscarPorId(long id) {
		return investigacionRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void eliminar(Long id) throws InstanceNotFoundException {

		Optional<Investigacion> investigacionOpcional = investigacionRepository.findById(id);
		if (!investigacionOpcional.isPresent()) {
			throw new InstanceNotFoundException();
		}
		Investigacion investigacion = investigacionOpcional.get();
		Optional<Civil> civilInvestigado = civilRepository.findById(investigacion.getCivil().getId_civil());
		if (civilInvestigado.isPresent()) {
			Civil civil = civilInvestigado.get();
			civil.setInvestigacion(false);
			civil = civilRepository.save(civil);
			investigacion.setCivil(civil);
		}

		investigacionRepository.deleteById(id);
	}

	@Override
	public void cerrarInvestigacion(Investigacion investigacion) {
		investigacion.setEstado(EstadoInvestigacion.CERRADA);

		Optional<Civil> civilInvestigado = civilRepository.findById(investigacion.getCivil().getId_civil());
		if (civilInvestigado.isPresent()) {
			Civil civil = civilInvestigado.get();
			civil.setInvestigacion(false);
			civil = civilRepository.save(civil);
			investigacion.setCivil(civil);

		}

		investigacionRepository.save(investigacion);

	}

}
