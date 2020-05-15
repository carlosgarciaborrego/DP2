
package org.springframework.samples.petclinic.web.integration;

import java.util.Collections;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.samples.petclinic.web.VetController;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class VetControllerIntegrationTests {

	private static final int	TEST_VET_ID	= 1;

	@Autowired
	private VetController		vetController;

	@Autowired
	private VetService			vetService;


	@Test
	void testInitCreationForm() throws Exception {
		ModelMap model = new ModelMap();

		String view = this.vetController.initCreationForm(model);

		Assertions.assertEquals(view, "vet/createOrUpdateVetForm");
		Assertions.assertNotNull(model.get("vet"));
	}

	@Test
	void testProcessCreationFormSuccess() throws Exception {
		ModelMap model = new ModelMap();
		Vet v = new Vet();
		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
		v.setCity("city");
		v.setFirstName("first name");
		v.setAddress("address");
		v.setLastName("last name");
		v.setTelephone("666666666");
		User user = new User();
		user.setUsername("prueba1");
		user.setPassword("prueba2");
		v.setUser(user);
		String view = this.vetController.processCreationForm(v, bindingResult, model);

		Assertions.assertEquals(view, "vets/vetList");
		Assertions.assertNotNull(model.get("vets"));
	}

	@Test
	void testProcessCreationFormErrors() throws Exception {
		ModelMap model = new ModelMap();
		Vet v = new Vet();
		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
		v.setFirstName("first name");
		v.setAddress("address");
		v.setLastName("last name");
		v.setTelephone("666666666");
		User user = new User();
		user.setUsername("prueba1");
		user.setPassword("prueba2");
		v.setUser(user);
		bindingResult.reject("city", "Requied!");

		String view = this.vetController.processCreationForm(v, bindingResult, model);

		Assertions.assertEquals(view, "vets/vetList");
		Assertions.assertNotNull(model.get("vets"));
	}

	@Test
	void testInitUpdateVetForm() throws Exception {
		ModelMap model = new ModelMap();

		String view = this.vetController.showVet(VetControllerIntegrationTests.TEST_VET_ID, model);
		Assertions.assertEquals(view, "vet/createOrUpdateVetForm");
		Assertions.assertNotNull(model.get("vet"));
	}

	@Test
	void testProcessUpdateVetFormSuccess() throws Exception {
		BindingResult result = new MapBindingResult(Collections.emptyMap(), "");
		ModelMap model = new ModelMap();

		Vet v = this.vetService.findVetById(VetControllerIntegrationTests.TEST_VET_ID).get();
		v.setAddress("La botica 12");

		String view = this.vetController.showVet(v, result, model);
		Assertions.assertEquals(view, "vets/vetList");
		Assertions.assertNotNull(model.get("vets"));
	}

	@Test
	void testProcessUpdateVetFormHasErrors() throws Exception {
		BindingResult result = new MapBindingResult(Collections.emptyMap(), "");
		ModelMap model = new ModelMap();

		Vet v = this.vetService.findVetById(VetControllerIntegrationTests.TEST_VET_ID).get();
		v.setAddress("");
		result.reject("address", "Requied!");

		String view = this.vetController.showVet(v, result, model);
		Assertions.assertEquals(view, "vet/createOrUpdateVetForm");
		Assertions.assertNotNull(model.get("vet"));
	}

	// Test de Specialty
	@Test
	void testInitCreationSpecialtyForm() throws Exception {
		ModelMap model = new ModelMap();
		String view = this.vetController.initAddSpecialtytForm(model, VetControllerIntegrationTests.TEST_VET_ID);

		Assert.assertEquals(view, "vets/updateSpecialties");
		Assert.assertNotNull(model.get("vet"));
	}

	@Test
	void testProcessCreationSpecialtyFormSuccess() throws Exception {
		Specialty newSpecialty = new Specialty();
		newSpecialty.setName("Ophthalmology");

		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
		String view = this.vetController.initAddSpecialtytForm(newSpecialty, bindingResult, VetControllerIntegrationTests.TEST_VET_ID);
		Assert.assertEquals(view, "redirect:/vets");
	}

	@Test
	void testDelete() throws Exception {
		ModelMap model = new ModelMap();
		Integer TEST_VET3_ID = 3;
		Integer TEST_SPECIALTY_ID = 3;
		String view = this.vetController.deleteSpecialty(TEST_VET3_ID, TEST_SPECIALTY_ID, model);
		Assert.assertEquals(view, "redirect:/vets");
	}

}
