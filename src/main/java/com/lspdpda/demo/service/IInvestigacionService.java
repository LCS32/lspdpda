package com.lspdpda.demo.service;

import java.util.List;

import javax.management.InstanceNotFoundException;

import com.lspdpda.demo.models.entity.Investigacion;



public interface IInvestigacionService {

	public List<Investigacion> listarInvestigaciones();
	public void crear(Investigacion investigacion) throws IllegalStateException;
	public void editar(Investigacion investigacion) throws IllegalStateException;
	public Investigacion buscarPorId(long id);
	public void eliminar(Long id) throws InstanceNotFoundException;
	public void cerrarInvestigacion(Investigacion investigacion);
	public List<Investigacion> listarInvestigacionesActivas(Investigacion investigacion);
	
}
