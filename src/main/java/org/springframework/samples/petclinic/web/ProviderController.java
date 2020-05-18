
package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.model.Provider;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.service.ProviderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/providers")
public class ProviderController {

	private ProviderService	providerService;

	private ClinicService	clinicService;


	@Autowired
	public ProviderController(final ProviderService providerService, final ClinicService clinicService) {
		this.providerService = providerService;
		this.clinicService = clinicService;
	}

	@ModelAttribute("clinics")
	public Collection<Clinic> populatePetTypes() {
		return this.clinicService.findAll();
	}

	@GetMapping()
	public String listadoProviders(final ModelMap modelMap) {
		String vista = "providers/listadoProviders";
		Iterable<Provider> providers = this.providerService.findAll();
		Iterable<Clinic> clinic = this.clinicService.findAll();
		modelMap.addAttribute("clinic", clinic);
		modelMap.addAttribute("provider", providers);
		return vista;
	}

	@GetMapping(path = "/new")
	public String crearProvider(final ModelMap modelMap) {
		String view = "providers/editProvider";
		modelMap.addAttribute("provider", new Provider());
		return view;
	}

	@PostMapping(path = "/save")
	public String salvarProvider(@Valid final Provider provider, final BindingResult result, final ModelMap modelMap) {
		String view = "providers/listadoProviders";
		if (result.hasErrors()) {
			modelMap.addAttribute("provider", provider);
			return "providers/editProvider";
		} else {
			this.providerService.save(provider);
			modelMap.addAttribute("message", "Provider successfully saved!");
			view = "redirect:/providers/";
		}
		return view;
	}

	@GetMapping(path = "/delete/{providerId}")
	public String borrarProvider(@PathVariable("providerId") final int providerId, final ModelMap modelMap) {
		String view = "providers/listadoProviders";
		Provider provider = this.providerService.findProviderById(providerId);
		this.providerService.delete(provider);
		modelMap.addAttribute("message", "Hotel successfully deleted!");
		view = this.listadoProviders(modelMap);
		return view;
	}

	@GetMapping(path = "/{providerId}/edit")
	public String actualizarProvider(@PathVariable("providerId") final int providerId, final ModelMap modelMap) {
		String view = "providers/editProvider";
		Provider provider = this.providerService.findProviderById(providerId);
		modelMap.addAttribute(provider);
		return view;
	}

	@PostMapping(value = "/{providerId}/edit")
	public String actualizarProviderPost(@Valid final Provider provider, final BindingResult result, @PathVariable("providerId") final int providerId, final ModelMap modelMap) {
		String view = "providers/editProvider";
		if (result.hasErrors()) {
			modelMap.addAttribute("provider", provider);
			return view;
		} else {
			provider.setId(providerId);
			this.providerService.save(provider);
			return "redirect:/providers/{providerId}";
		}
	}

	@GetMapping(value = "/{providerId}")
	public String showProvider(@PathVariable("providerId") final Integer providerId, final Map<String, Object> model) {
		Provider provider = this.providerService.findProviderById(providerId);
		model.put("provider", provider);
		return "providers/editProvider";
	}

	@PostMapping(value = "/{providerId}")
	public String showProvider(@Valid final Provider provider, final BindingResult result, final Map<String, Object> model) {
		if (result.hasErrors()) {
			return "providers/editProvider";
		} else {
			this.providerService.save(provider);
			;
			return "redirect:/show/{providerId}";
		}
	}

}
