
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetHistory;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.service.PetHistoryService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller

public class PetHistoryController {

	@Autowired
	private PetHistoryService	petHistoryService;

	@Autowired
	private PetService			petService;

	@Autowired
	private VetService			vetService;


	@GetMapping("/vet/{vetId}/pets/{petId}/pethistory")
	public String listadoPetHistory(@PathVariable("petId") final Integer petId, final ModelMap modelMap) {
		String vista = "petHistory/petHistoryList";
		Iterable<PetHistory> hotels = this.petHistoryService.findPetHistoryByPetId(petId);
		Pet p = this.petService.findPetById(petId);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentPrincipalName = (User) authentication.getPrincipal();
		Vet v = this.vetService.findVetByUserId(currentPrincipalName.getUsername()).get(0);

		modelMap.addAttribute("petHistories", hotels);
		modelMap.addAttribute("vet", v);
		modelMap.addAttribute("pet", p);
		return vista;
	}

	@GetMapping("/vet/{vetId}/pets/{petId}/pethistory/{petHistoryId}")
	public String petHistoryShow(@PathVariable("petId") final Integer petId, @PathVariable("petHistoryId") final Integer petHistoryId, final ModelMap modelMap) {
		String vista = "petHistory/editPetHistory";
		Optional<PetHistory> hotels = this.petHistoryService.findPetHistoryById(petHistoryId);
		Pet p = this.petService.findPetById(petId);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentPrincipalName = (User) authentication.getPrincipal();
		Vet v = this.vetService.findVetByUserId(currentPrincipalName.getUsername()).get(0);

		modelMap.addAttribute("vet", v);
		modelMap.addAttribute("pet", p);
		modelMap.addAttribute("petHistory", hotels.get());
		return vista;
	}

	@PostMapping(path = "/vet/{vetId}/pets/{petId}/pethistory/{petHistoryId}")
	public String petHistoryEdit(@Valid final PetHistory petHistory, final BindingResult result, final ModelMap modelMap) {
		if (result.hasErrors()) {
			modelMap.addAttribute("petHistory", petHistory);
			return "petHistory/editPetHistory";
		} else {
			Optional<PetHistory> vet = this.petHistoryService.findPetHistoryById(petHistory.getId());
			petHistory.setPets(vet.get().getPets());
			this.petHistoryService.savePetHistory(petHistory);
		}
		return "redirect:/vet/{vetId}/pets/{petId}/pethistory";
	}

	@GetMapping(path = "/vet/{vetId}/pets/{petId}/pethistory/new")
	public String crearPetHistory(@PathVariable("petId") final Integer petId, @PathVariable("vetId") final Integer vetId, final ModelMap modelMap) {
		String view = "petHistory/editPetHistory";
		PetHistory pethistory = new PetHistory();
		Pet p = this.petService.findPetById(petId);
		Optional<Vet> vets = this.vetService.findVetById(vetId);
		pethistory.setPets(p);
		modelMap.addAttribute("petHistory", pethistory);
		modelMap.addAttribute("vets", vets.get());
		return view;
	}

	@PostMapping(path = "/vet/{vetId}/pets/{petId}/pethistory/new")
	public String salvarHotel(@PathVariable("petId") final Integer petId, @Valid final PetHistory petHistory, final BindingResult result, final ModelMap modelMap) {
		if (result.hasErrors()) {
			modelMap.addAttribute("petHistory", petHistory);
			return "petHistory/editPetHistory";
		} else {
			Pet p = this.petService.findPetById(petId);
			petHistory.setPets(p);
			LocalDate local = LocalDate.now();
			petHistory.setDate(local);
			this.petHistoryService.savePetHistory(petHistory);
			modelMap.addAttribute("message", "Hotel successfully saved!");
		}
		return "redirect:/vet/{vetId}/pets/{petId}/pethistory";
	}

	@GetMapping(value = "/vet/{vetId}/pets/{petId}/pethistory/{petHistoryId}/delete")
	public String deletePetHistory(@PathVariable("petHistoryId") final Integer petHistoryId, final ModelMap model) {
		Optional<PetHistory> vet = this.petHistoryService.findPetHistoryById(petHistoryId);
		if (vet.isPresent()) {
			this.petHistoryService.delete(petHistoryId);
		} else {
			model.put("message", "This vet don't exist");
		}

		return "redirect:/vet/{vetId}/pets/{petId}/pethistory";
	}

}
