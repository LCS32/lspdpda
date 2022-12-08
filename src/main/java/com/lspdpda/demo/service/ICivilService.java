package com.lspdpda.demo.service;

import java.util.List;

import com.lspdpda.demo.models.entity.Civil;

public interface ICivilService {

	public List<Civil> listarCiviles();
	public void guardar(Civil civil);
	public Civil buscarPorId(long id);
	public void eliminar(Long id);
	void crear(Civil civil) throws IllegalArgumentException;
	public void editar(Civil civil) throws IllegalStateException;
	
}
