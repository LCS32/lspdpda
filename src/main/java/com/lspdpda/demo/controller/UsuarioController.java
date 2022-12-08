package com.lspdpda.demo.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import com.lspdpda.demo.enums.RolNombre;
import com.lspdpda.demo.models.entity.Rol;
import com.lspdpda.demo.models.entity.Usuario;
import com.lspdpda.demo.service.RolService;
import com.lspdpda.demo.service.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private RolService rolService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/registro")
	public String registro() {
		return "registro";
	}

	@PostMapping("/registrar")
	public ModelAndView registar(String nombreUsuario, String password) {
		ModelAndView mv = new ModelAndView();
		if (StringUtils.isEmpty(nombreUsuario)) {
			mv.setViewName("/registro");
			mv.addObject("error", "el nombre no puede estar vacio");
			return mv;
		}
		if (StringUtils.isEmpty(password)) {
			mv.setViewName("/registro");
			mv.addObject("error", "la contraseña no puede estar vacio");
			return mv;
		}
		if (usuarioService.existsByNombreUsuario(nombreUsuario)) {
			mv.setViewName("/registro");
			mv.addObject("error", "el nombre de usuario ya existe");
			return mv;
		}

		Usuario usuario = new Usuario();
		usuario.setNombreUsuario(nombreUsuario);
		usuario.setPassword(passwordEncoder.encode(password));
		Rol rolUser = rolService.getByRolNombre(RolNombre.ROLE_USER).get();
		Set<Rol> roles = new HashSet<>();
		roles.add(rolUser);
		usuario.setRoles(roles);
		usuarioService.save(usuario);
		mv.setViewName("/login");
		mv.addObject("registroOK",
				"Cuenta creada con éxito, " + usuario.getNombreUsuario() + ", puedes iniciar sesión");
		return mv;

	}


}
