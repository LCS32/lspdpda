package com.lspdpda.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lspdpda.demo.models.entity.Agente;
import com.lspdpda.demo.service.IAgenteService;

@Controller
@RequestMapping("views/agentes")
public class AgenteController {

	@Autowired
	private IAgenteService agenteService;

	@GetMapping("/")
	public String listarAgentes(Model model) {
		List<Agente> listadoAgentes = agenteService.listarAgenteActivo(false);

		model.addAttribute("titulo", "Lista de Agentes Activos");
		model.addAttribute("agentes", listadoAgentes);

		return "/views/agentes/listar";
	}

	@GetMapping("/create")
	public String crear(Model model) {

		Agente agente = new Agente();

		model.addAttribute("titulo", "Formulario: Nuevo Agente");
		model.addAttribute("agente", agente);

		return "/views/agentes/frmCrear";
	}

	@PostMapping("/save")
	public String crear(@Valid @ModelAttribute Agente agente, BindingResult result, Model model,
			RedirectAttributes attribute) {
		agente.setExpediente(false);
		if (result.hasErrors()) {

			model.addAttribute("titulo", "Formulario: Nuevo Agente");
			model.addAttribute("agente", agente);
			System.out.println("Hay errores en el formulario");
			return "/views/agentes/frmCrear";

		}
		try {
			agenteService.crear(agente);
		} catch (IllegalArgumentException e) {
			attribute.addFlashAttribute("danger", "Ya existe un agente con ese nombre y apellidos");
			return "redirect:/views/civiles/create";
		}
		System.out.println("Agente guardado con exito");
		attribute.addFlashAttribute("success", "Agente guardado con exito");
		return "redirect:/views/agentes/";
	}

	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") Long idAgente, Model model, RedirectAttributes attributte) {

		Agente agente = null;
		if (idAgente > 0) {
			agente = agenteService.buscarPorId(idAgente);

			if (agente == null) {
				System.out.println("Error: El ID de Agente no existe");
				attributte.addFlashAttribute("error", "Atención: El ID de Agente no existe");
				return "redirect:/views/agentes/";
			}
		} else {
			System.out.println("Error: El con ID de Agente");
			attributte.addFlashAttribute("error", "Atención: Error con el ID del Agente");
			return "redirect:/views/agentes/";
		}

		model.addAttribute("titulo", "Formulario: Editar Agente");
		model.addAttribute("agente", agente);
		return "/views/agentes/frmEditarAgente";
	}

	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") Long idAgente, RedirectAttributes attributte) {

		Agente agente = null;
		if (idAgente > 0) {
			agente = agenteService.buscarPorId(idAgente);

			if (agente == null) {
				System.out.println("Error: El ID de Agente no existe");
				attributte.addFlashAttribute("error", "Atención: El ID de Agente no existe");
				return "redirect:/views/agentes/";
			}
		} else {
			System.out.println("Error: El con ID de Agente");
			attributte.addFlashAttribute("error", "Atención: El ID de Agente no existe");
			return "redirect:/views/agentes/";
		}

		agenteService.eliminar(idAgente);
		System.out.println("Agente eliminado con exito");
		attributte.addFlashAttribute("warning", "Agente eliminado con exito");

		return "redirect:/views/agentes/";
	}

	@PostMapping("/edit")
	public String editar(@Valid @ModelAttribute Agente agente, BindingResult result, Model model,
			RedirectAttributes attribute) {

		if (result.hasErrors()) {

			model.addAttribute("titulo", "Formulario: Editar Agente");
			model.addAttribute("agente", agente);
			System.out.println("Hay errores en el formulario");
			return "/views/investigaciones/frmEditarAgente";

		}
		try {
			agenteService.editar(agente);
		} catch (IllegalStateException e) {
			attribute.addFlashAttribute("danger", "No se debe editar una investigacion cerrada");
			return "redirect:/views/agentes/";
		}
		System.out.println("Agente guardado con éxito");
		attribute.addFlashAttribute("success", "Agente guardado con éxito");
		return "redirect:/views/agentes/";
	}

	@GetMapping("/suspendidos")
	public String listarSuspendidos(Model model) {
		List<Agente> listadoSuspendidos = agenteService.listarAgenteSuspendido(true);

		model.addAttribute("titulo", "Lista de Agentes Suspendidos");
		model.addAttribute("agentesus", listadoSuspendidos);

		return "/views/agentes/listarAgenteSuspendido";
	}

}
