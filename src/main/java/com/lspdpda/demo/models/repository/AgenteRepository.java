package com.lspdpda.demo.models.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lspdpda.demo.models.entity.Agente;

@Repository
public interface AgenteRepository extends CrudRepository<Agente, Long> {

	Optional<Agente> findByNombreIsAndApellidoIs(String nombre, String apellido);
	List<Agente> findBySuspendidos(Boolean suspendidos);

}
