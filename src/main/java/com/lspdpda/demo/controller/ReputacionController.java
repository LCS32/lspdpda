package com.lspdpda.demo.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lspdpda.demo.models.entity.Reputacion;
import com.lspdpda.demo.service.IDelitoService;

@Controller
@RequestMapping("/views/reputacion")
public class ReputacionController {
	
	@Autowired
	private IDelitoService delitoService;
	
	@GetMapping("/")
	public String listarReputacion(Model model) {
		List<Reputacion> reputaciones = delitoService.getAllReputacion();
		Collections.sort(reputaciones, Comparator.comparing(Reputacion::getNumeroDelitos).reversed());
		model.addAttribute("titulo", "Reputacion Civiles");
		model.addAttribute("reputaciones", reputaciones);
		return "/views/reputacion/listar_reputacion";
	}
	


}
