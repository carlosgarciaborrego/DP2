
package org.springframework.samples.petclinic.web.integration;

import java.util.Collections;
import java.util.HashMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.web.ClinicController;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClinicControllerIntegrationTests {

	private static final int	TEST_CLINIC_ID	= 1;

	@Autowired
	private ClinicController	clinicController;

	@Autowired
	private ClinicService		clinicService;


	@Test
	void testInitCreationForm() throws Exception {
		ModelMap model = new ModelMap();

		String view = this.clinicController.crearClinic(model);

		Assertions.assertEquals(view, "clinic/show");
		Assertions.assertNotNull(model.get("clinic"));
	}

	@Test
	void testProcessCreationFormSuccess() throws Exception {
		ModelMap model = new ModelMap();
		Clinic c = new Clinic();
		c.setCapacity(3);
		c.setEmail("kfjds@email.com");
		c.setEmergency("66666666");
		c.setLocation("Sevilla");
		c.setName("San Bernardo Clinic");
		c.setTelephone("999999999");

		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");

		String view = this.clinicController.guardarClinic(c, bindingResult, model);
		Assertions.assertEquals(view, "clinic/list");
	}

	@Test
	void testProcessCreationFormError() throws Exception {
		ModelMap model = new ModelMap();
		Clinic c = new Clinic();
		c.setCapacity(3);
		c.setEmail("kfjds@email.com");
		c.setEmergency("66666666");
		c.setLocation("");
		c.setName("San Bernardo Clinic");
		c.setTelephone("999999999");

		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
		bindingResult.reject("location", "Requied!");

		String view = this.clinicController.guardarClinic(c, bindingResult, model);
		Assertions.assertNotNull(model.get("clinic"));
		Assertions.assertEquals(view, "clinic/show");
	}

	@Test
	void testShowClinics() throws Exception {
		ModelMap model = new ModelMap();

		String view = this.clinicController.listClinic(model);

		Assertions.assertEquals(view, "clinic/list");
		Assertions.assertNotNull(model.get("clinics"));
	}

	@Test
	void testInitUpdateForm() throws Exception {
		ModelMap model = new ModelMap();

		String view = this.clinicController.showClinic(1, model);

		Assertions.assertEquals(view, "clinic/show");
		Assertions.assertNotNull(model.get("clinic"));
	}

	@Test
	void testProcessUpdateFormHasErrors() throws Exception {
		ModelMap model = new ModelMap();
		Clinic c = this.clinicService.findClinicById(1);
		c.setLocation("");
		BindingResult bindingResult = new MapBindingResult(new HashMap(), "");
		bindingResult.reject("location", "Requied!");

		String view = this.clinicController.showClinic(c, bindingResult, model);

		Assertions.assertEquals(view, "clinic/show");
	}

	@Test
	void testProcessUpdateFormHasSuccess() throws Exception {
		ModelMap model = new ModelMap();
		Clinic c = this.clinicService.findClinicById(1);
		c.setLocation("Las Vegas");
		BindingResult bindingResult = new MapBindingResult(new HashMap(), "");
		String view = this.clinicController.guardarClinic(c, bindingResult, model);

		Assertions.assertEquals(view, "clinic/list");
	}

	@Test
	void testShowClinicVets() throws Exception {
		ModelMap model = new ModelMap();

		String view = this.clinicController.showClinicVets(1, model);

		Assertions.assertEquals(view, "vets/vetList");
		Assertions.assertNotNull(model.get("vets"));
	}

}
