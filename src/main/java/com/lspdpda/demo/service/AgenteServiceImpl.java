package com.lspdpda.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lspdpda.demo.models.entity.Agente;
import com.lspdpda.demo.models.repository.AgenteRepository;

@Service
public class AgenteServiceImpl implements IAgenteService {

	@Autowired
	private AgenteRepository agenteRepository;

	@Override
	public List<Agente> listarTodos() {

		return (List<Agente>) agenteRepository.findAll();
	}

	@Override
	/**
	 * Guarda el agente en la BD
	 * 
	 * @param @agente El objeto agente a guardar en base de datos
	 */
	public void guardar(Agente agente) {
		agenteRepository.save(agente);

	}

	@Override
	public void crear(Agente agente) throws IllegalArgumentException {
		if (checkIfNameAndSurnameExists(agente.getNombre(), agente.getApellido())) {
			throw new IllegalArgumentException();
		} else {
			agente.setExpediente(false);
			guardar(agente);
		}
	}

	@Override
	public Agente buscarPorId(Long id) {

		return agenteRepository.findById(id).orElse(null);
	}

	@Override
	public void eliminar(Long id) {
		agenteRepository.deleteById(id);

	}

	public boolean checkIfNameAndSurnameExists(String nombre, String apellido) {
		Optional<Agente> agente = agenteRepository.findByNombreIsAndApellidoIs(nombre, apellido);
		return agente.isPresent();
	}

	@Override
	public void editar(Agente agente) throws IllegalStateException {
		Optional<Agente> antiguo = agenteRepository.findById(agente.getId_agente());
		if (!antiguo.isPresent()) {
			throw new IllegalStateException();
		}
		agente.setExpediente(antiguo.get().getExpediente());
		agenteRepository.save(agente);

	}

	@Override
	public List<Agente> listarAgenteSuspendido(boolean suspendidos) {
		return agenteRepository.findBySuspendidos(true);
	}

	@Override
	public List<Agente> listarAgenteActivo(boolean suspendidos) {
		return agenteRepository.findBySuspendidos(false);
	}

}
