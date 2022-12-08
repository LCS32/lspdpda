package com.lspdpda.demo.service;

import java.util.List;

import javax.management.InstanceNotFoundException;

import com.lspdpda.demo.models.entity.Expediente;

public interface IExpedienteService {
	
	public List<Expediente> listarExpedientes();
	public void guardar(Expediente expediente);
	public Expediente buscarPorId(long id);
	public void eliminar(Long id) throws InstanceNotFoundException;

}
