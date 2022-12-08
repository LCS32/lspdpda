package com.lspdpda.demo.models.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lspdpda.demo.models.entity.Agente;
import com.lspdpda.demo.models.entity.Civil;
import com.lspdpda.demo.models.entity.Investigacion;
import com.lspdpda.demo.models.enums.EstadoInvestigacion;

@Repository
public interface InvestigacionRepository extends CrudRepository<Investigacion, Long> {
	

	List<Investigacion> findAllByEstadoIs(Investigacion investigacion);
	List<Investigacion> findAllByCivilIsAndEstadoIs(Civil civil, EstadoInvestigacion estado); 
	Optional<Investigacion> findFirstByAgenteIsAndEstadoIs(Agente agente, EstadoInvestigacion estado);
	

}
