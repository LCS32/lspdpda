package com.lspdpda.demo.service;

import java.util.List;
import com.lspdpda.demo.models.entity.Agente;

public interface IAgenteService {
	
	public List<Agente> listarTodos();
	public void guardar(Agente agente);
	public Agente buscarPorId(Long id);
	public void eliminar(Long id);
	void crear(Agente agente) throws IllegalArgumentException;
	public void editar(Agente agente) throws IllegalStateException;
	public List<Agente> listarAgenteSuspendido(boolean suspendidos);
	public List<Agente> listarAgenteActivo(boolean suspendidos);

}
