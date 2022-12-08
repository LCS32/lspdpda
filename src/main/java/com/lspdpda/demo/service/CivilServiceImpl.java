package com.lspdpda.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lspdpda.demo.models.entity.Civil;
import com.lspdpda.demo.models.repository.CivilRepository;

@Service
public class CivilServiceImpl implements ICivilService {

	@Autowired
	private CivilRepository civilRepository;

	@Override
	public List<Civil> listarCiviles() {
		return (List<Civil>) civilRepository.findAll();
	}

	@Override
	public void guardar(Civil civil) {

		civilRepository.save(civil);

	}

	@Override
	public void crear(Civil civil) throws IllegalArgumentException {
		if (checkIfNameAndSurnameExists(civil.getNombre(), civil.getApellido())) {
			throw new IllegalArgumentException();
		} else {
			civil.setInvestigacion(false);
			guardar(civil);
		}
	}

	@Override
	public Civil buscarPorId(long id) {
		return civilRepository.findById(id).orElse(null);
	}

	@Override
	public void eliminar(Long id) {
		civilRepository.deleteById(id);

	}

	public boolean checkIfNameAndSurnameExists(String nombre, String apellido) {
		Optional<Civil> civil = civilRepository.findByNombreIsAndApellidoIs(nombre, apellido);
		return civil.isPresent();
	}

	@Override
	public void editar(Civil civil) throws IllegalStateException {
		Optional<Civil> antiguo = civilRepository.findById(civil.getId_civil());
		if (!antiguo.isPresent()) {
			throw new IllegalStateException();
		}
		civil.setInvestigacion(antiguo.get().getInvestigacion());
		civilRepository.save(civil);

	}

}
