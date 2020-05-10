
package org.springframework.samples.petclinic.web.integration;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.web.PetController;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

/**
 * Test class for the {@link PetController}
 *
 * @author Colin But
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PetControllerIntegrationTests {

	private static final int	TEST_OWNER_ID	= 1;

	private static final int	TEST_PET_ID		= 1;

	@Autowired
	private PetController		petController;

	@Autowired
	private PetService			petService;

	@Autowired
	private OwnerService		ownerService;


	@Test
	void testInitCreationForm() throws Exception {
		Owner owner = this.ownerService.findOwnerById(PetControllerIntegrationTests.TEST_OWNER_ID);
		ModelMap model = new ModelMap();

		String view = this.petController.initCreationForm(owner, model);

		Assertions.assertEquals(view, "pets/createOrUpdatePetForm");
		Assertions.assertNotNull(model.get("pet"));
	}

	@Test
	void testProcessCreationFormSuccess() throws Exception {
		ModelMap model = new ModelMap();
		Owner owner = this.ownerService.findOwnerById(PetControllerIntegrationTests.TEST_OWNER_ID);
		Pet newPet = new Pet();
		PetType petType = this.petService.findPetTypes().iterator().next();
		newPet.setName("Betty");
		newPet.setType(petType);
		newPet.setBirthDate(LocalDate.now());
		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");

		String view = this.petController.processCreationForm(owner, newPet, bindingResult, model);

		Assertions.assertEquals(view, "redirect:/owners/{ownerId}");
	}

	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		ModelMap model = new ModelMap();
		Owner owner = this.ownerService.findOwnerById(PetControllerIntegrationTests.TEST_OWNER_ID);
		Pet newPet = new Pet();
		PetType petType = this.petService.findPetTypes().iterator().next();
		newPet.setName("Betty");
		newPet.setBirthDate(LocalDate.now());
		BindingResult bindingResult = new MapBindingResult(new HashMap(), "");
		bindingResult.reject("petType", "Requied!");

		String view = this.petController.processCreationForm(owner, newPet, bindingResult, model);

		Assertions.assertEquals(view, "pets/createOrUpdatePetForm");
	}

	@Test
	void testInitUpdateForm() throws Exception {
		ModelMap model = new ModelMap();

		String view = this.petController.initUpdateForm(PetControllerIntegrationTests.TEST_PET_ID, model);

		Assertions.assertEquals(view, "pets/createOrUpdatePetForm");
		Assertions.assertNotNull(model.get("pet"));
	}

	@Test
	void testProcessUpdateFormHasErrors() throws Exception {
		ModelMap model = new ModelMap();
		Owner owner = this.ownerService.findOwnerById(PetControllerIntegrationTests.TEST_OWNER_ID);
		Pet newPet = new Pet();
		PetType petType = this.petService.findPetTypes().iterator().next();
		newPet.setName("Betty");
		newPet.setBirthDate(LocalDate.now());
		BindingResult bindingResult = new MapBindingResult(new HashMap(), "");
		bindingResult.reject("petType", "Requied!");

		String view = this.petController.processUpdateForm(newPet, bindingResult, owner, PetControllerIntegrationTests.TEST_PET_ID, model);

		Assertions.assertEquals(view, "pets/createOrUpdatePetForm");
	}

}
