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
import com.lspdpda.demo.models.entity.Civil;
import com.lspdpda.demo.models.entity.Delito;
import com.lspdpda.demo.models.entity.Investigacion;
import com.lspdpda.demo.service.IAgenteService;
import com.lspdpda.demo.service.ICivilService;
import com.lspdpda.demo.service.IDelitoService;
import com.lspdpda.demo.service.IInvestigacionService;

@Controller
@RequestMapping("/views/investigaciones")

public class InvestigacionController {

	@Autowired
	private IInvestigacionService investigacionService;
	@Autowired
	private IDelitoService delitoService;
	@Autowired
	private IAgenteService agenteService;
	@Autowired
	private ICivilService civilService;

	@GetMapping("/")
	public String listarInvestigaciones(Model model) {

		List<Investigacion> listadoinvestigaciones = investigacionService.listarInvestigaciones();
		List<Delito> listadoDelitos = delitoService.listarDelitos();
		List<Agente> listadoAgente = agenteService.listarTodos();
		List<Civil> listadoCivil = civilService.listarCiviles();

		model.addAttribute("titulo", "Investigaciones abiertas");
		model.addAttribute("investigacion", listadoinvestigaciones);
		model.addAttribute("delitos", listadoDelitos);
		model.addAttribute("agentes", listadoAgente);
		model.addAttribute("civiles", listadoCivil);

		return "/views/investigaciones/listar_investigaciones";
	}

	@GetMapping("/create")
	public String crear(Model model) {

		Investigacion investigacion = new Investigacion();
		List<Agente> listadoAgente = agenteService.listarTodos();
		List<Civil> listadoCivil = civilService.listarCiviles();

		model.addAttribute("titulo", "Formulario: Nueva Investigacion");
		model.addAttribute("investigacion", investigacion);
		model.addAttribute("agentes", listadoAgente);
		model.addAttribute("civiles", listadoCivil);

		return "/views/investigaciones/frmCrearInvestigacion";
	}

	@PostMapping("/save")
	public String guardar(@Valid @ModelAttribute Investigacion investigacion, BindingResult result, Model model,
			RedirectAttributes attribute) {

		if (result.hasErrors()) {

			model.addAttribute("titulo", "Formulario: Nueva Investigacion");
			model.addAttribute("investigacion", investigacion);
			System.out.println("Hay errores en el formulario");
			return "/views/investigaciones/frmCrearInvestigacion";

		}
		try { // testeamos que se puede crear bajo las condiciones que indique el servicio
			investigacionService.crear(investigacion);
		} catch (IllegalStateException e) {
			// Si el servicio no permite esa creacion
			// Aqui cojo el error, y se lo muestro al usuario
			attribute.addFlashAttribute("danger", "Este civil ya tiene una investigacion Activa");
			return "redirect:/views/investigaciones/create";
		}
		System.out.println("Investigacion guardada con exito");
		attribute.addFlashAttribute("success", "Investigacion guardado con exito");
		return "redirect:/views/investigaciones/";
	}

	@PostMapping("/edit")
	public String editar(@Valid @ModelAttribute Investigacion investigacion, BindingResult result, Model model,
			RedirectAttributes attribute) {

		if (result.hasErrors()) {

			model.addAttribute("titulo", "Formulario: Nueva Investigacion");
			model.addAttribute("investigacion", investigacion);
			System.out.println("Hay errores en el formulario");
			return "/views/investigaciones/frmCrearInvestigacion";

		}
		try {
			investigacionService.editar(investigacion);
		} catch (IllegalStateException e) {
			attribute.addFlashAttribute("danger", "No se debe editar una investigacion cerrada");
			return "redirect:/views/investigaciones/";
		}
		System.out.println("Investigacion guardada con exito");
		attribute.addFlashAttribute("success", "Investigacion guardado con exito");
		return "redirect:/views/investigaciones/";
	}

	@GetMapping("/close/{id}")
	public String cerrarInvestigacion(@PathVariable("id") Long idInvestigacion, Model model,
			RedirectAttributes attributtes) {
		if (idInvestigacion > 0) {
			Investigacion investigacion = investigacionService.buscarPorId(idInvestigacion);

			if (investigacion == null) {
				System.out.println("Error: El ID de Investigacion no existe");
				attributtes.addFlashAttribute("error", "Atención: El ID de Investigacion no existe");
				return "redirect:/views/investigaciones/";
			} else {
				investigacionService.cerrarInvestigacion(investigacion);
			}
		} else {
			System.out.println("Error: Error con ID de Investigacion");
			attributtes.addFlashAttribute("error", "Atención: Error con el ID del Investigacion");
			return "redirect:/views/investigaciones/";
		}
		return "redirect:/views/investigaciones/";
	}

	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") Long idInvestigacion, Model model, RedirectAttributes attributte) {

		Investigacion investigacion = null;
		List<Agente> listadoAgente = agenteService.listarTodos();
		List<Civil> listadoCivil = civilService.listarCiviles();

		if (idInvestigacion > 0) {
			investigacion = investigacionService.buscarPorId(idInvestigacion);

			if (investigacion == null) {
				System.out.println("Error: El ID de Investigacion no existe");
				attributte.addFlashAttribute("error", "Atención: El ID de Investigacion no existe");
				return "redirect:/views/investigaciones/";
			}
		} else {
			System.out.println("Error: Error con ID de Investigacion");
			attributte.addFlashAttribute("error", "Atención: Error con el ID del Investigacion");
			return "redirect:/views/investigaciones/";
		}

		model.addAttribute("agentes", listadoAgente);
		model.addAttribute("civiles", listadoCivil);
		model.addAttribute("titulo", "Formulario: Editar Investigacion");
		model.addAttribute("investigacion", investigacion);
		return "/views/investigaciones/frmEditarInvestigacion";
	}

	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") Long idInvestigacion, RedirectAttributes attributte) {

		Investigacion investigacion = null;
		if (idInvestigacion > 0) {
			investigacion = investigacionService.buscarPorId(idInvestigacion);

			if (investigacion == null) {
				System.out.println("Error: El ID de Investigacion no existe");
				attributte.addFlashAttribute("error", "Atención: El ID de Investigacion no existe");
				return "redirect:/views/investigaciones/";
			}
		} else {
			System.out.println("Error: El con ID de Civil");
			attributte.addFlashAttribute("error", "Atención: El ID de Civil no existe");
			return "redirect:/views/investigaciones/";
		}

		try {
			investigacionService.eliminar(idInvestigacion);
		} catch (InstanceNotFoundException e) {
			System.out.println("Error: El con ID de Civil");
			attributte.addFlashAttribute("error", "Atención: El ID de Invesigacion no existe");
			return "redirect:/views/investigaciones/";
		}
		System.out.println("Civil eliminado con exito");
		attributte.addFlashAttribute("warning", "Investigacion eliminado con exito");

		return "redirect:/views/investigaciones/";
	}

	@GetMapping("/delitos/{id}")
	public String buscarDelitosDeUnCivilPorSuID(@PathVariable("id") Long idCivil, Model model,
			RedirectAttributes attributte) {
		// Datos del civil
		Civil civil = civilService.buscarPorId(idCivil);
		if (civil == null) {
			attributte.addFlashAttribute("error", "Atención: El ID de Civil no existe");
			return "redirect:/views/investigaciones/";
		}
		// Delitos del civil
		List<Delito> listadoDelitos = delitoService.buscarDelitosDeUnCivilNoArchivados(civil);
		int totalEnMultas;
		try {
			totalEnMultas = delitoService.calcularSumaDelitos(idCivil);
		} catch (InstanceNotFoundException e) {
			attributte.addFlashAttribute("error", "Atención: El ID de Civil no existe");
			return "redirect:/views/investigaciones/";
		}
		model.addAttribute("titulo", "Delitos cometidos por " + civil.getNombre() + " " + civil.getApellido());
		model.addAttribute("delitos", listadoDelitos);
		model.addAttribute("importe", totalEnMultas);

		return "/views/investigaciones/investigaciones_delitos";
	}

}
