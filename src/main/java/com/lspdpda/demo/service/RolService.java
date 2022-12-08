package com.lspdpda.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lspdpda.demo.enums.RolNombre;
import com.lspdpda.demo.models.entity.Rol;
import com.lspdpda.demo.models.repository.RolRepository;

@Service
@Transactional
public class RolService {

	@Autowired
	RolRepository rolRepository;

	public void save(Rol rol) {
		rolRepository.save(rol);
	}

	public Optional<Rol> getByRolNombre(RolNombre rolNombre) {
		return rolRepository.findByRolNombre(rolNombre);

	}

	public boolean existisByRolNombre(RolNombre rolNombre) {
		return rolRepository.existsByRolNombre(rolNombre);

	}

}
