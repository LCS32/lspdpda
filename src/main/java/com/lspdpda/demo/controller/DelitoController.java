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
import com.lspdpda.demo.models.entity.Civil;
import com.lspdpda.demo.models.entity.Delito;
import com.lspdpda.demo.service.IAgenteService;
import com.lspdpda.demo.service.ICivilService;
import com.lspdpda.demo.service.IDelitoService;

@Controller
@RequestMapping("/views/delitos")
public class DelitoController {
	
	@Autowired
	private IDelitoService delitoService;
	@Autowired
	private IAgenteService agenteService;
	@Autowired
	private ICivilService civilService;

	@GetMapping("/")
	public String listarDelitos(Model model) {
		List<Delito> listadoDelitos = delitoService.listarDelitos();
		List<Agente> listadoAgente = agenteService.listarTodos();
		List<Civil> listadoCivil = civilService.listarCiviles();

		model.addAttribute("titulo", "Lista de Delitos");
		model.addAttribute("delitos", listadoDelitos);
		model.addAttribute("agentes", listadoAgente);
		model.addAttribute("civiles", listadoCivil);

		return "/views/delitos/listar";
	}

	@GetMapping("/create")
	public String crear(Model model) {

		Delito delito = new Delito();
		List<Agente> listadoAgente = agenteService.listarTodos();
		List<Civil> listadoCivil = civilService.listarCiviles();

		model.addAttribute("titulo", "Formulario: Nuevo Delito");
		model.addAttribute("delito", delito);
		model.addAttribute("agentes", listadoAgente);
		model.addAttribute("civiles", listadoCivil);
		return "/views/delitos/frmCrear";
	}

	@PostMapping("/save")
	public String guardar(@Valid @ModelAttribute Delito delito, BindingResult result, Model model, RedirectAttributes attribute) {
		if (result.hasErrors()) {

			model.addAttribute("titulo", "Formulario: Nuevo Delito");
			model.addAttribute("delito", delito);
			System.out.println("Hay errores en el formulario");
			return "/views/delitos/frmCrear";

		}

		delitoService.guardar(delito);
		System.out.println("Delito guardado con exito");
		attribute.addFlashAttribute("success", "Delito guardado con exito");
		return "redirect:/views/delitos/";
	}

	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") Long idDelito, Model model, RedirectAttributes attributte) {
		
		List<Agente> listadoAgente = agenteService.listarTodos();
		List<Civil> listadoCivil = civilService.listarCiviles();
		
		model.addAttribute("agentes", listadoAgente);
		model.addAttribute("civiles", listadoCivil);

		Delito delito = null;
		if (idDelito > 0) {
			delito = delitoService.buscarPorId(idDelito);

			if (delito == null) {
				System.out.println("Error: El ID de Delito no existe");
				attributte.addFlashAttribute("error", "Atención: El ID de Delito no existe");
				return "redirect:/views/delitos/";
			}
			}else {
				System.out.println("Error: El con ID de Delito");
				attributte.addFlashAttribute("error", "Atención: Error con el ID del Delito");
				return "redirect:/views/delitos/";
			}
		

		model.addAttribute("titulo", "Formulario: Editar Delito");
		model.addAttribute("delito", delito);
		return "/views/delitos/frmCrear";
	}

	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") Long idDelito, RedirectAttributes attributte) {
		
		Delito delito = null;
		if (idDelito > 0) {
			delito = delitoService.buscarPorId(idDelito);

			if (delito == null) {
				System.out.println("Error: El ID de Delito no existe");
				attributte.addFlashAttribute("error", "Atención: El ID de Delito no existe");
				return "redirect:/views/delitos/";
			}
			}else {
				System.out.println("Error: El con ID de Delito");
				attributte.addFlashAttribute("error", "Atención: El ID de Delito no existe");
				return "redirect:/views/delitos/";
			}

		delitoService.eliminar(idDelito);
		System.out.println("Delito eliminado con exito");
		attributte.addFlashAttribute("warning", "Delito eliminado con exito");

		return "redirect:/views/delitos/";
	}


}
