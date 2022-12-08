package com.lspdpda.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.management.InstanceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lspdpda.demo.models.entity.Civil;
import com.lspdpda.demo.models.entity.Delito;
import com.lspdpda.demo.models.entity.Reputacion;
import com.lspdpda.demo.models.repository.CivilRepository;
import com.lspdpda.demo.models.repository.DelitoRepository;

@Service
public class DelitoServiceImpl implements IDelitoService {

	@Autowired
	private DelitoRepository delitoRepository;

	@Autowired
	private CivilRepository civilRepository;

	@Override
	public List<Delito> listarDelitos() {
		return (List<Delito>) delitoRepository.findAll();
	}

	@Override
	public void guardar(Delito delito) {
		delitoRepository.save(delito);
	}

	@Override
	public Delito buscarPorId(Long id) {
		return delitoRepository.findById(id).orElse(null);
	}

	@Override
	public void eliminar(Long id) {
		delitoRepository.deleteById(id);
	}

	@Override
	public List<Delito> buscarDelitosDeUnCivil(Civil civil) {
		return delitoRepository.findAllByCivilIs(civil);
	}

	@Override
	public List<Delito> buscarDelitosDeUnCivilNoArchivados(Civil civil) {
		return delitoRepository.findAllByCivilIsAndArchivadoIsFalse(civil);
	}

	@Override
	public List<Reputacion> getAllReputacion() {
		Iterable<Delito> todosLosDelitos = delitoRepository.findAll();
		Map<Civil, Long> result = new HashMap<>();
		todosLosDelitos.forEach((delito) -> {
			if (result.containsKey(delito.getCivil())) {
				result.put(delito.getCivil(), result.get(delito.getCivil()) + 1);
			} else {
				result.put(delito.getCivil(), 1L);
			}
		});
		List<Reputacion> reputaciones = new ArrayList<Reputacion>();
		for (Map.Entry<Civil, Long> entry : result.entrySet()) {
			Reputacion reputacion = new Reputacion();
			reputacion.setCivil(entry.getKey());
			reputacion.setNumeroDelitos(entry.getValue());
			reputaciones.add(reputacion);
		}
		return reputaciones;
	}

	@Override
	public int calcularSumaDelitos(Long id_civil) throws InstanceNotFoundException {
		Optional<Civil> civil = civilRepository.findById(id_civil);
		if (!civil.isPresent()) {
			throw new InstanceNotFoundException();
		}
		List<Delito> delitos = delitoRepository.findAllByCivilIs(civil.get());
		int resultado = 0;
		for (Delito x : delitos) {
			resultado = resultado + x.getMulta();
		}
		return resultado;
	}

}
