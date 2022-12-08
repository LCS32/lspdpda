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

import com.lspdpda.demo.models.entity.Civil;
import com.lspdpda.demo.models.entity.Investigacion;
import com.lspdpda.demo.service.ICivilService;
import com.lspdpda.demo.service.IInvestigacionService;

@Controller
@RequestMapping("views/civiles")
public class CivilController {

	@Autowired
	private ICivilService civilService;
	@Autowired
	private IInvestigacionService investigacionService;

	@GetMapping("/")
	public String listarCiviles(Model model) {

		List<Civil> listadoCiviles = civilService.listarCiviles();
		List<Investigacion> listadoInvestigacion = investigacionService.listarInvestigaciones();

		model.addAttribute("titulo", "Lista de Civiles");
		model.addAttribute("civiles", listadoCiviles);
		model.addAttribute("investigacion", listadoInvestigacion);
		return "/views/civiles/listar";
	}

	@GetMapping("/create")
	public String crear(Model model) {

		Civil civil = new Civil();

		model.addAttribute("titulo", "Formulario: Nuevo civil");
		model.addAttribute("civil", civil);

		return "/views/civiles/frmCrear";
	}

	@PostMapping("/save")
	public String crear(@Valid @ModelAttribute Civil civil, BindingResult result, Model model,
			RedirectAttributes attribute) {
		civil.setInvestigacion(false);
		if (result.hasErrors()) {

			model.addAttribute("titulo", "Formulario: Nuevo Civil");
			model.addAttribute("civil", civil);
			System.out.println("Hay errores en el formulario");
			return "/views/civiles/frmCrear";

		}
		try {
			civilService.crear(civil);
		} catch (IllegalArgumentException e) {
			attribute.addFlashAttribute("danger", "Ya existe un civil con ese nombre y apellidos");
			return "redirect:/views/civiles/create";
		}
		System.out.println("Civil guardado con exito");
		attribute.addFlashAttribute("success", "Civil guardado con exito");
		return "redirect:/views/civiles/";
	}

	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") Long idCivil, Model model, RedirectAttributes attributte) {

		Civil civil = null;
		if (idCivil > 0) {
			civil = civilService.buscarPorId(idCivil);

			if (civil == null) {
				System.out.println("Error: El ID de Civil no existe");
				attributte.addFlashAttribute("error", "Atención: El ID de Civil no existe");
				return "redirect:/views/civiles/";
			}
		} else {
			System.out.println("Error: El con ID de Civil");
			attributte.addFlashAttribute("error", "Atención: Error con el ID del Civil");
			return "redirect:/views/civiles/";
		}

		model.addAttribute("titulo", "Formulario: Editar Civil");
		model.addAttribute("civil", civil);
		return "/views/civiles/frmEditarCivil";
	}

	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") Long idCivil, RedirectAttributes attributte) {

		Civil civil = null;
		if (idCivil > 0) {
			civil = civilService.buscarPorId(idCivil);

			if (civil == null) {
				System.out.println("Error: El ID de Civil no existe");
				attributte.addFlashAttribute("error", "Atención: El ID de Civil no existe");
				return "redirect:/views/civiles/";
			}
		} else {
			System.out.println("Error: El con ID de Civil");
			attributte.addFlashAttribute("error", "Atención: El ID de Civil no existe");
			return "redirect:/views/civiles/";
		}

		civilService.eliminar(idCivil);
		System.out.println("Civil eliminado con exito");
		attributte.addFlashAttribute("warning", "Civil eliminado con exito");

		return "redirect:/views/civiles/";
	}

	@PostMapping("/edit")
	public String editar(@Valid @ModelAttribute Civil civil, BindingResult result, Model model,
			RedirectAttributes attribute) {

		if (result.hasErrors()) {

			model.addAttribute("titulo", "Formulario: Editar Civil");
			model.addAttribute("civil", civil);
			System.out.println("Hay errores en el formulario");
			return "/views/investigaciones/frmEditarCivil";

		}
		try {
			civilService.editar(civil);
		} catch (IllegalStateException e) {
			attribute.addFlashAttribute("danger", "No se debe editar una investigacion cerrada");
			return "redirect:/views/civiles/";
		}
		System.out.println("Civil guardado con éxito");
		attribute.addFlashAttribute("success", "Civil guardado con éxito");
		return "redirect:/views/civiles/";
	}

}
