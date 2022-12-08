package com.lspdpda.demo.models.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lspdpda.demo.models.entity.Civil;
import com.lspdpda.demo.models.entity.Delito;

@Repository
public interface DelitoRepository extends CrudRepository<Delito, Long> {

	List<Delito> findAllByCivilIs(Civil civil); // select * from delito where id_civil = X

	List<Delito> findAllByCivilIsAndArchivadoIsFalse(Civil civil);

}
