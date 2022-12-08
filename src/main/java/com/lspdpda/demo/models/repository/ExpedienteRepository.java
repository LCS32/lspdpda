package com.lspdpda.demo.models.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lspdpda.demo.models.entity.Expediente;

@Repository
public interface ExpedienteRepository extends CrudRepository<Expediente, Long> {

}
