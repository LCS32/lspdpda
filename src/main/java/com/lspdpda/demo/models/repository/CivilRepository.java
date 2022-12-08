package com.lspdpda.demo.models.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lspdpda.demo.models.entity.Civil;

@Repository
public interface CivilRepository extends CrudRepository<Civil, Long> {

	Optional<Civil> findByNombreIsAndApellidoIs(String nombre, String apellido);

}
