package org.springframework.samples.petclinic.web.integration;

import java.util.Collections;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.service.CauseService;
import org.springframework.samples.petclinic.service.DonationService;
import org.springframework.samples.petclinic.web.CauseController;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CauseControllerIntegrationTests {

	private static final int	TEST_CAUSE_ID	= 1;

	@Autowired
	private CauseController		causeController;

	@Autowired
	private CauseService		causeService;
	
	@Autowired
	private DonationService		donationService;

	@Test
	void testInitCreationForm() throws Exception {
		ModelMap model = new ModelMap();
		String view = this.causeController.crearCause(model);

		Assert.assertEquals(view, "causes/editCause");
		Assert.assertNotNull(model.get("cause"));
	}
	
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		ModelMap model = new ModelMap();
		Cause newCause = new Cause();
		
		newCause.setName("Cause prueba integracion");
		
		newCause.setDescription("Descripcion");
		newCause.setOrganisation("Organisation");
		newCause.setBudgetArchivied(100.0);
		newCause.setBudgetTarget(1000.0);
		
		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
		String view = this.causeController.salvarCause(newCause, bindingResult, model);
		Assert.assertEquals(view, "causes/listadoCauses");
	}
	
	@Test
	void testProcessCreationFormHasError() throws Exception {
		ModelMap model = new ModelMap();
		Cause newCause = new Cause();
		
		newCause.setName("Cause prueba integracion");
		
		newCause.setDescription("");
		newCause.setOrganisation("Organisation");
		newCause.setBudgetArchivied(100.0);
		newCause.setBudgetTarget(1000.0);
		
		
		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
		bindingResult.reject("description", "must not be null");
		String view = this.causeController.salvarCause(newCause, bindingResult, model);
		Assert.assertEquals(view, "causes/editCause");
	}
	
	@Test
	void testInitUpdateForm() throws Exception {
		ModelMap model = new ModelMap();

		String view = this.causeController.actualizarCause(CauseControllerIntegrationTests.TEST_CAUSE_ID, model);
		Assert.assertEquals(view, "causes/editCause");
		Assert.assertNotNull(model.get("cause"));
	}
	
	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		ModelMap model = new ModelMap();
		Cause newCause = new Cause();
		
		newCause.setName("Cause prueba integracion");
		
		newCause.setDescription("Description");
		newCause.setOrganisation("Organisation");
		newCause.setBudgetArchivied(100.0);
		newCause.setBudgetTarget(1000.0);
		
		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
		String view = this.causeController.actualizarCausePost(newCause, bindingResult, CauseControllerIntegrationTests.TEST_CAUSE_ID, model);
		Assert.assertEquals(view, "redirect:/causes/{causeId}");
	}

	@Test
	void testProcessUpdateFormHasError() throws Exception {
		ModelMap model = new ModelMap();
		Cause newCause = new Cause();
		
		newCause.setName("Cause prueba integracion");
		
		newCause.setDescription("Description");
		newCause.setOrganisation("Organisation");
		newCause.setBudgetArchivied(100.0);
		newCause.setBudgetTarget(-1000.0);
		
		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
		bindingResult.reject("capacity", "must be between 0 and 9223372036854775807");
		String view = this.causeController.salvarCause(newCause, bindingResult, model);
		Assert.assertEquals(view, "causes/editCause");
	}

	@Test
	void testDelete() throws Exception {
		ModelMap model = new ModelMap();
		String view = this.causeController.borrarCause(CauseControllerIntegrationTests.TEST_CAUSE_ID, model);
		Assert.assertEquals(view, "causes/listadoCauses");
	}

	@Test
	void testShow() throws Exception {
		ModelMap model = new ModelMap();
		String view = this.causeController.showCause(CauseControllerIntegrationTests.TEST_CAUSE_ID, model);
		Assert.assertEquals(view, "causes/editCause");
	}
	
	// DONATION INTEGRATION TESTS
	@Test
	void testInitCreationFormDonation() throws Exception {
		ModelMap model = new ModelMap();
		String view = this.causeController.crearDonarCause(CauseControllerIntegrationTests.TEST_CAUSE_ID, model);

		Assert.assertEquals(view, "causes/donateCause");
		Assert.assertNotNull(model.get("donation"));
	}
	
	@Test
	void testProcessCreationFormSuccessDonation() throws Exception {
		ModelMap model = new ModelMap();
		Donation newDonation= new Donation();
		Cause cause = new Cause ();
		cause.setId(1);
		
		newDonation.setAmount(100.0);
		newDonation.setName("Donation Integration Tests");
		newDonation.setCause(cause);
		
		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
		String view = this.causeController.salvarDonation(newDonation, CauseControllerIntegrationTests.TEST_CAUSE_ID, bindingResult, model);
		Assert.assertEquals(view, "causes/listadoCauses");
	}

	@Test
	void testProcessCreationFormHasErrorDonation() throws Exception {
		ModelMap model = new ModelMap();
		Donation newDonation= new Donation();
		Cause cause = new Cause ();
		cause.setId(1);
		
		newDonation.setAmount(100.0);
		newDonation.setName("Donation Integration Tests");
		newDonation.setCause(cause);
		
		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
		bindingResult.reject("location", "must not be null");
		String view = this.causeController.salvarDonation(newDonation, CauseControllerIntegrationTests.TEST_CAUSE_ID, bindingResult, model);
		Assert.assertEquals(view, "causes/donateCause");
	}
	
	
}
