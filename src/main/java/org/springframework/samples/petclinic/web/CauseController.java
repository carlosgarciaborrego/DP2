
package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.service.CauseService;
import org.springframework.samples.petclinic.service.DonationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/causes")
public class CauseController {

	private CauseService causeService;

	private DonationService donationService;


	@Autowired
	public CauseController(final CauseService causeService, final DonationService donationService) {
		super();
		this.causeService = causeService;
		this.donationService = donationService;
	}
	
	@GetMapping()
	public String listadoCauses(final ModelMap modelMap) {
		String vista = "causes/listadoCauses";
		Iterable<Cause> causes = this.causeService.findAll();

		for (Cause cause : causes) {
			Double amount = 0.0;
			List<Donation> donations = donationService.findByIdCause(cause);
			
			if (!donations.isEmpty()) {
				for (Donation d : donations) {
					amount = amount + d.getAmount();
				}
			}
			cause.setBudgetArchivied(amount);
		}

		modelMap.addAttribute("cause", causes);
		return vista;
	}

	@GetMapping(path = "/new")
	public String crearCause(final ModelMap modelMap) {
		String view = "causes/editCause";
		modelMap.addAttribute("cause", new Cause());
		return view;
	}

	@PostMapping(path = "/save")
	public String salvarCause(@Valid final Cause cause, final BindingResult result, final ModelMap modelMap) {
		String view = "causes/listadoCauses";
		if (result.hasErrors()) {
			modelMap.addAttribute("cause", cause);
			return "causes/editCause";
		} else {

			this.causeService.save(cause);
			modelMap.addAttribute("message", "Cause  successfully saved!");
			view = this.listadoCauses(modelMap);
		}
		return view;
	}

	@GetMapping(path = "/delete/{causeId}")
	public String borrarCause(@PathVariable("causeId") final int causeId, final ModelMap modelMap) {
		String view = "causes/listadoCauses";
		Cause cause = this.causeService.findCauseById(causeId);
		this.causeService.delete(cause);
		modelMap.addAttribute("message", "Cause successfully deleted!");
		view = this.listadoCauses(modelMap);
		return view;
	}

	@GetMapping(path = "/{causeId}/edit")
	public String actualizarCause(@PathVariable("causeId") final int causeId, final ModelMap modelMap) {
		String view = "causes/editCause";
		Cause cause = this.causeService.findCauseById(causeId);
		modelMap.addAttribute(cause);
		return view;
	}
	
	@PostMapping(value = "/{causeId}/edit")
	public String actualizarCausePost(@Valid final Cause cause, final BindingResult result, @PathVariable("causeId") final int causeId, final ModelMap modelMap) {
		String view = "causes/editCause";
		if (result.hasErrors()) {
			modelMap.addAttribute("cause", cause);
			return view;
		} else {
			cause.setId(causeId);
			this.causeService.save(cause);
			return "redirect:/causes/{causeId}";
		}
	}

	@GetMapping(value = "/{causeId}")
	public String showCause(@PathVariable("causeId") final Integer causeId, final Map<String, Object> model) {
		Cause cause = this.causeService.findCauseById(causeId);
		
		Double amount = 0.0;
		List<Donation> donations = this.donationService.findByIdCause(cause);

		if (donations.isEmpty()) {
			for (Donation d : donations) {
				amount = amount + d.getAmount();
			}
		}
		cause.setBudgetArchivied(amount);
		model.put("cause", cause);
		//		model.put("", amount);
		return "causes/editCause";
	}

	@PostMapping(value = "/{causeId}")
	public String showCause(@Valid final Cause cause, final BindingResult result, final Map<String, Object> model) {
		if (result.hasErrors()) {
			return "causes/editCause";
		} else {
			this.causeService.save(cause);
			;
			return "redirect:/show/{causeId}";
		}
	}
}
