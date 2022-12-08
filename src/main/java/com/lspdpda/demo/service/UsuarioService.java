package com.lspdpda.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lspdpda.demo.models.entity.Usuario;
import com.lspdpda.demo.models.repository.UsuarioRepository;

@Service
@Transactional
public class UsuarioService {

	@Autowired
	UsuarioRepository usuarioRepository;

	public List<Usuario> lista() {
		return usuarioRepository.findAll();
	}

	public Optional<Usuario> getById(int id) {
		return usuarioRepository.findById(id);
	}

	public Optional<Usuario> getByNombreUsuario(String nombreUsuario) {
		return usuarioRepository.findByNombreUsuario(nombreUsuario);
	}

	public void save(Usuario usuario) {
		usuarioRepository.save(usuario);
	}

	public boolean existsById(int id) {
		return usuarioRepository.existsById(id);
	}

	public boolean existsByNombreUsuario(String nombreUsuario) {
		return usuarioRepository.existsByNombreUsuario(nombreUsuario);
	}

}
