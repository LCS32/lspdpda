package com.lspdpda.demo.service;

import java.util.List;
import java.util.Optional;

import javax.management.InstanceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lspdpda.demo.models.entity.Agente;
import com.lspdpda.demo.models.entity.Expediente;
import com.lspdpda.demo.models.repository.AgenteRepository;
import com.lspdpda.demo.models.repository.ExpedienteRepository;

@Service
public class ExpedienteServiceImpl implements IExpedienteService {

	@Autowired
	private ExpedienteRepository expedienteRepository;

	@Autowired
	private AgenteRepository agenteRepository;

	@Override
	public List<Expediente> listarExpedientes() {
		return (List<Expediente>) expedienteRepository.findAll();
	}

	@Override
	public void guardar(Expediente expediente) {

		Optional<Agente> agenteInvestigado = agenteRepository.findById(expediente.getAgente().getId_agente());
		if (agenteInvestigado.isPresent()) {
			Agente agente = agenteInvestigado.get();
			agente.setExpediente(true);
			agente = agenteRepository.save(agente);
			expediente.setAgente(agente);

		}
		expedienteRepository.save(expediente);
	}

	@Override
	public Expediente buscarPorId(long id) {
		return expedienteRepository.findById(id).orElse(null);
	}

	@Override
	public void eliminar(Long id) throws InstanceNotFoundException {
		Optional<Expediente> expedienteOpcional = expedienteRepository.findById(id);
		if (!expedienteOpcional.isPresent()) {
			throw new InstanceNotFoundException();
		}
		Expediente expediente = expedienteOpcional.get();
		Optional<Agente> agenteInvestigado = agenteRepository.findById(expediente.getAgente().getId_agente());
		if (agenteInvestigado.isPresent()) {
			Agente agente = agenteInvestigado.get();
			agente.setExpediente(false);
			agente = agenteRepository.save(agente);
			expediente.setAgente(agente);
		}
		expedienteRepository.deleteById(id);
	}

}
