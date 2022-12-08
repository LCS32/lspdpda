package com.lspdpda.demo.service;

import java.util.List;

import javax.management.InstanceNotFoundException;

import com.lspdpda.demo.models.entity.Civil;
import com.lspdpda.demo.models.entity.Delito;
import com.lspdpda.demo.models.entity.Reputacion;

public interface IDelitoService {

	public List<Delito> listarDelitos();

	public void guardar(Delito delito);

	public Delito buscarPorId(Long id);

	public void eliminar(Long id);

	public List<Delito> buscarDelitosDeUnCivil(Civil civil);

	public List<Delito> buscarDelitosDeUnCivilNoArchivados(Civil civil);

	public List<Reputacion> getAllReputacion();

	public int calcularSumaDelitos(Long id_civil) throws InstanceNotFoundException;

}
