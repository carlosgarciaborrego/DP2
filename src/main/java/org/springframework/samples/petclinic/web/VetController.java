/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
public class VetController {

	private final VetService	vetService;
	private static final String	VIEWS_VET_CREATE_OR_UPDATE_FORM	= "vet/createOrUpdateVetForm";
	private final ClinicService	clinicService;


	//	@Autowired
	//	private UserService			userService;
	//

	@ModelAttribute("clinics")
	public Collection<Clinic> populatePetTypes() {
		return this.clinicService.findAll();
	}

	@Autowired
	public VetController(final VetService vetService, final ClinicService clinicService) {
		this.vetService = vetService;
		this.clinicService = clinicService;
	}

	@GetMapping(value = {
		"/vets"
	})
	public String showVetList(final Map<String, Object> model) {
		// Here we are returning an object of type 'Vets' rather than a collection of Vet
		// objects
		// so it is simpler for Object-Xml mapping
		Vets vets = new Vets();
		vets.getVetList().addAll(this.vetService.findVets());
		model.put("vets", vets);
		return "vets/vetList";
	}

	@GetMapping(value = {
		"/vets.xml"
	})
	public @ResponseBody Vets showResourcesVetList() {
		// Here we are returning an object of type 'Vets' rather than a collection of Vet
		// objects
		// so it is simpler for JSon/Object mapping
		Vets vets = new Vets();
		vets.getVetList().addAll(this.vetService.findVets());
		return vets;
	}

	@GetMapping(value = "/vet/new")
	public String initCreationForm(final Map<String, Object> model) {
		Vet vet = new Vet();
		model.put("vet", vet);
		return VetController.VIEWS_VET_CREATE_OR_UPDATE_FORM;
	}

	@GetMapping(value = "/vet/show/{vetId}")
	public String showVet(@PathVariable("vetId") final Integer vetId, final Map<String, Object> model) {
		Vet vet = new Vet();
		Optional<Vet> vets = this.vetService.findVetById(vetId);
		if (vets.isPresent()) {
			vet = vets.get();
		}

		model.put("vet", vet);
		return VetController.VIEWS_VET_CREATE_OR_UPDATE_FORM;
	}

	@GetMapping(value = "/vet/profile")
	public String showVetProfile(final Map<String, Object> model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentPrincipalName = (User) authentication.getPrincipal();
		Vet v = this.vetService.findVetByUserId(currentPrincipalName.getUsername()).get(0);
		List<Pet> pets = this.vetService.findPetsByVetId(v.getId());
		model.put("pets", pets);
		model.put("vet", v);
		return "pets/petList";
	}

	@PostMapping(value = "/vet/show/{vetId}")
	public String showVet(@Valid final Vet vet, final BindingResult result, final Map<String, Object> model) {
		if (result.hasErrors()) {
			model.put("vet", vet);
			return VetController.VIEWS_VET_CREATE_OR_UPDATE_FORM;
		} else {
			this.vetService.saveVet(vet);
			return "redirect:/vets";
		}
	}

	@PostMapping(value = "/vet/new")
	public String processCreationForm(@Valid final Vet vet, final BindingResult result, final ModelMap model) {
		if (result.hasErrors()) {
			model.put("vet", vet);
			return VetController.VIEWS_VET_CREATE_OR_UPDATE_FORM;
		} else {
			//creating owner, user and authorities
			this.vetService.saveVet(vet);

			return "redirect:/vets";
		}
	}

	@GetMapping(value = "/vet/delete/{vetId}")
	public String deleteVet(@PathVariable("vetId") final Integer vetId, final ModelMap model) {
		Optional<Vet> vet = this.vetService.findVetById(vetId);
		if (vet.isPresent()) {
			Vet v = vet.get();
			this.vetService.delete(v);
		} else {
			model.put("message", "This vet don't exist");
		}

		return "redirect:/vets";
	}

	//Add specialty to a Vet

	@GetMapping(value = "/vet/{vetId}/specialty/new")
	public String initAddSpecialtytForm(final ModelMap modelMap, @PathVariable("vetId") final int vetId) {
		Specialty specialty = new Specialty();
		Vet vet = new Vet();
		Optional<Vet> vets = this.vetService.findVetById(vetId);
		if (vets.isPresent()) {
			vet = this.vetService.findVetById(vetId).get();
		}
		List<Specialty> specialties = vet.getSpecialties();

		modelMap.put("vet", vet);
		modelMap.put("specialty", specialty);
		modelMap.put("specialties", specialties);
		return "vets/updateSpecialties";
	}

	@PostMapping(value = "/vet/{vetId}/specialty/new")
	public String initAddSpecialtytForm(@Valid final Specialty specialty, final BindingResult result, @PathVariable("vetId") final int vetId) {
		if (result.hasErrors()) {
			return "vets/updateSpecialties";
		} else {
			Specialty newSpecialty = this.vetService.saveSpecialty(specialty);
			Vet vet = this.vetService.findVetById(vetId).get();
			vet.addSpecialty(newSpecialty);
			this.vetService.saveVet(vet);
			return "redirect:/vets";
		}
	}

	@GetMapping(value = "/vet/{vetId}/specialty/delete/{specialtyId}")
	public String deleteSpecialty(@PathVariable("vetId") final Integer vetId, @PathVariable("specialtyId") final Integer specialtyId, final ModelMap model) {
		Optional<Vet> vet = this.vetService.findVetById(vetId);
		List<Specialty> specialties = vet.get().getSpecialties();
		Specialty res = new Specialty();
		for (Specialty spe : specialties) {
			if (spe.equals(this.vetService.findSpecialtyById(specialtyId).get())) {
				res = spe;
			}
		}

		if (vet.isPresent()) {
			vet.get().deleteSpecialty(res);
			this.vetService.saveVet(vet.get());

		} else {
			model.put("message", "This specialty don't exist");
		}

		return "redirect:/vets";
	}

	@GetMapping(value = "/vet/{vetId}/clinic")
	public String showVetClinic(@PathVariable("vetId") final Integer vetId, final Map<String, Object> model) {
		Optional<Vet> vets = this.vetService.findVetById(vetId);
		Clinic c = new Clinic();
		if (vets.isPresent()) {
			c = vets.get().getClinic();
		}
		model.put("clinic", c);
		return "clinic/show";
	}

}
