
package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clinic")
public class ClinicController {

	private VetService		vetService;

	private ClinicService	clinicService;


	@Autowired
	public ClinicController(final VetService vetService, final ClinicService clinicService) {
		this.vetService = vetService;
		this.clinicService = clinicService;
	}

	@GetMapping()
	public String listClinic(final ModelMap modelMap) {
		String vista = "clinic/list";
		Iterable<Clinic> clinics = this.clinicService.findAll();
		modelMap.addAttribute("clinics", clinics);
		return vista;
	}

	@GetMapping(value = "/{clinicId}")
	public String showClinic(@PathVariable("clinicId") final Integer clinicId, final Map<String, Object> model) {
		Clinic clinic = new Clinic();
		Optional<Clinic> c = this.clinicService.findClinicById(clinicId);
		if (c.isPresent()) {
			clinic = c.get();
		}
		model.put("clinic", clinic);
		return "clinic/show";
	}

	@PostMapping(value = "/{clinicId}")
	public String showClinic(@Valid final Clinic clinic, final BindingResult result, final Map<String, Object> model) {
		if (result.hasErrors()) {
			model.put("clinic", clinic);
			return "clinic/show";
		} else {
			this.clinicService.saveClinic(clinic);
			return "redirect:/clinic";
		}
	}

	@GetMapping(value = "/{clinicId}/vets")
	public String showClinicVets(@PathVariable("clinicId") final Integer clinicId, final Map<String, Object> model) {
		Vets vets = new Vets();
		vets.getVetList().addAll(this.vetService.findVetByClinicId(clinicId));
		model.put("vets", vets);
		return "vets/vetList";
	}

	@GetMapping(path = "/new")
	public String crearClinic(final ModelMap modelMap) {
		Clinic clinic = new Clinic();
		modelMap.put("clinic", clinic);
		return "clinic/show";
	}

	@PostMapping(path = "/new")
	public String guardarClinic(@Valid final Clinic clinic, final BindingResult result, final ModelMap modelMap) {
		String vista = "clinic/list";
		if (result.hasErrors()) {
			modelMap.addAttribute("clinic", clinic);
			return "clinic/show";
		} else {
			this.clinicService.saveClinic(clinic);
			modelMap.addAttribute("message", "Hotel successfully saved!");
			vista = this.listClinic(modelMap);
		}
		return vista;
	}

	@GetMapping(value = "/{clinicId}/delete")
	public String deleteClinic(@PathVariable("clinicId") final Integer clinicId, final Map<String, Object> model) {
		Clinic clinic = new Clinic();
		Optional<Clinic> c = this.clinicService.findClinicById(clinicId);
		if (c.isPresent()) {
			clinic = c.get();
			List<Vet> vets = this.vetService.findVetByClinicId(clinicId);
			if (vets != null && !vets.isEmpty()) {
				for (Vet v : vets) {
					v.setClinic(null);
					this.vetService.saveVet(v);
				}
			}
			this.clinicService.delete(clinic.getId());
		} else {
			model.put("message", "This vet don't exist");
		}
		return "redirect:/clinic";
	}
}
