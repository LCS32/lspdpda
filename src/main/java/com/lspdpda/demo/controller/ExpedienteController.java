package com.lspdpda.demo.controller;

import java.util.List;

import javax.management.InstanceNotFoundException;
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
import com.lspdpda.demo.models.entity.Expediente;
import com.lspdpda.demo.service.IAgenteService;
import com.lspdpda.demo.service.IExpedienteService;

@Controller
@RequestMapping("/views/expedientes")

public class ExpedienteController {

	@Autowired
	private IExpedienteService expedienteService;
	@Autowired
	private IAgenteService agenteService;

	@GetMapping("/")
	public String listarExpedientes(Model model) {

		List<Expediente> listadoExpediente = expedienteService.listarExpedientes();
		List<Agente> listadoAgente = agenteService.listarTodos();

		model.addAttribute("titulo", "Expedientes Abiertos");
		model.addAttribute("expediente", listadoExpediente);
		model.addAttribute("agentes", listadoAgente);

		return "/views/expedientes/listar_expedientes";
	}

	@GetMapping("/create")
	public String crear(Model model) {

		Expediente expediente = new Expediente();
		List<Agente> listadoAgente = agenteService.listarTodos();

		model.addAttribute("titulo", "Formulario: Nuevo Expediente");
		model.addAttribute("expediente", expediente);
		model.addAttribute("agentes", listadoAgente);

		return "/views/expedientes/frmCrearExpediente";
	}

	@PostMapping("/save")
	public String guardar(@Valid @ModelAttribute Expediente expediente, BindingResult result, Model model,
			RedirectAttributes attribute) {

		if (result.hasErrors()) {

			model.addAttribute("titulo", "Formulario: Nuevo Expediente");
			model.addAttribute("expediente", expediente);
			System.out.println("Hay errores en el formulario");
			return "/views/expedientes/frmCrearExpediente";

		}
		expedienteService.guardar(expediente);
		System.out.println("Investigacion guardada con exito");
		attribute.addFlashAttribute("success", "Expediente guardado con exito");
		return "redirect:/views/expedientes/";
	}

	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") Long idExpediente, Model model, RedirectAttributes attributte) {

		Expediente expediente = null;
		List<Agente> listadoAgente = agenteService.listarTodos();

		if (idExpediente > 0) {
			expediente = expedienteService.buscarPorId(idExpediente);

			if (expediente == null) {
				System.out.println("Error: El ID de Expediente no existe");
				attributte.addFlashAttribute("error", "Atención: El ID de Expediente no existe");
				return "redirect:/views/expedientes/";
			}
		} else {
			System.out.println("Error: El con ID de Expediente");
			attributte.addFlashAttribute("error", "Atención: Error con el ID del Expediente");
			return "redirect:/views/expedientes/";
		}

		model.addAttribute("agentes", listadoAgente);
		model.addAttribute("titulo", "Formulario: Editar Investigacion");
		model.addAttribute("expediente", expediente);
		return "/views/expedientes/frmCrearExpediente";
	}

	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") Long idExpediente, RedirectAttributes attributte) {

		Expediente expediente = null;
		if (idExpediente > 0) {
			expediente = expedienteService.buscarPorId(idExpediente);

			if (expediente == null) {
				System.out.println("Error: El ID de Expediente no existe");
				attributte.addFlashAttribute("error", "Atención: El ID de Expediente no existe");
				return "redirect:/views/expedientes/";
			}
		} else {
			System.out.println("Error: El con ID de Expediente");
			attributte.addFlashAttribute("error", "Atención: El ID de expediente no existe");
			return "redirect:/views/expedientes/";
		}

		try {
			expedienteService.eliminar(idExpediente);
		} catch (InstanceNotFoundException e) {
			System.out.println("Error: El con ID de Expediente");
			attributte.addFlashAttribute("error", "Atención: El ID de expediente no existe");
			return "redirect:/views/expedientes/";
		}
		System.out.println("Expediente eliminado con exito");
		attributte.addFlashAttribute("warning", "Expediente eliminado con exito");

		return "redirect:/views/expedientes/";
	}

}
