
package org.springframework.samples.petclinic.web.integration;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.PetHistory;
import org.springframework.samples.petclinic.service.PetHistoryService;
import org.springframework.samples.petclinic.web.PetHistoryController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PetHistoryControllerIntegrationTests {

	private static final int		TEST_PETHISTORY_ID	= 1;

	private static final int		TEST_PET_ID			= 1;

	private static final int		TEST_VET_ID			= 1;

	@Autowired
	private PetHistoryController	petHistoryController;

	@Autowired
	private PetHistoryService		petHistoryService;


	@Test
	void testInitCreationForm() throws Exception {
		ModelMap model = new ModelMap();

		String view = this.petHistoryController.crearPetHistory(PetHistoryControllerIntegrationTests.TEST_PET_ID, PetHistoryControllerIntegrationTests.TEST_VET_ID, model);

		Assertions.assertEquals(view, "petHistory/editPetHistory");
		Assertions.assertNotNull(model.get("petHistory"));
	}

	@WithMockUser(username = "vet1", authorities = {
		"veterinarian"
	})
	@Test
	void testInitList() throws Exception {
		ModelMap model = new ModelMap();

		String view = this.petHistoryController.listadoPetHistory(PetHistoryControllerIntegrationTests.TEST_PET_ID, model);

		Assertions.assertEquals(view, "petHistory/petHistoryList");
		Assertions.assertNotNull(model.get("petHistories"));

	}

	@WithMockUser(username = "vet1", authorities = {
		"veterinarian"
	})
	@Test
	void petHistoryShow() throws Exception {
		ModelMap model = new ModelMap();

		String view = this.petHistoryController.petHistoryShow(PetHistoryControllerIntegrationTests.TEST_PET_ID, PetHistoryControllerIntegrationTests.TEST_PETHISTORY_ID, model);

		Assertions.assertEquals(view, "petHistory/editPetHistory");
		Assertions.assertNotNull(model.get("petHistory"));
	}

	@Test
	void testProcessCreationFormSuccess() throws Exception {
		ModelMap model = new ModelMap();
		PetHistory petH = new PetHistory();
		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");

		petH.setSummary("summary");
		petH.setDetails("details");
		LocalDate local = LocalDate.now();
		petH.setDate(local);
		String view = this.petHistoryController.salvarPetHistory(PetHistoryControllerIntegrationTests.TEST_PET_ID, petH, bindingResult, model);

		Assertions.assertEquals(view, "redirect:/vet/{vetId}/pets/{petId}/pethistory");
	}

	@Test
	void testProcessCreationFormError() throws Exception {
		ModelMap model = new ModelMap();
		PetHistory petH = new PetHistory();
		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");

		petH.setSummary("summary");
		petH.setDetails("");
		LocalDate local = LocalDate.now();
		petH.setDate(local);
		bindingResult.reject("details", "must not be null");

		String view = this.petHistoryController.salvarPetHistory(PetHistoryControllerIntegrationTests.TEST_PET_ID, petH, bindingResult, model);

		Assertions.assertEquals(view, "petHistory/editPetHistory");
	}

	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		ModelMap model = new ModelMap();

		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
		Optional<PetHistory> p = this.petHistoryService.findPetHistoryById(PetHistoryControllerIntegrationTests.TEST_PETHISTORY_ID);
		if (p.isPresent()) {
			PetHistory petH = p.get();
			petH.setSummary("summary");
			petH.setDetails("details");
			LocalDate local = LocalDate.now();
			petH.setDate(local);
			String view = this.petHistoryController.petHistoryEdit(petH, bindingResult, model);

			Assertions.assertEquals(view, "redirect:/vet/{vetId}/pets/{petId}/pethistory");

		}
	}

	@Test
	void testProcessUpdateFormError() throws Exception {
		ModelMap model = new ModelMap();

		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
		Optional<PetHistory> p = this.petHistoryService.findPetHistoryById(PetHistoryControllerIntegrationTests.TEST_PETHISTORY_ID);
		if (p.isPresent()) {
			PetHistory petH = p.get();
			petH.setSummary("");
			petH.setDetails("details");
			LocalDate local = LocalDate.now();
			petH.setDate(local);
			bindingResult.reject("summary", "must not be null");

			String view = this.petHistoryController.petHistoryEdit(petH, bindingResult, model);

			Assertions.assertEquals(view, "petHistory/editPetHistory");

		}
	}

	@Test
	void testDeletePetHistory() throws Exception {
		ModelMap model = new ModelMap();

		String view = this.petHistoryController.deletePetHistory(PetHistoryControllerIntegrationTests.TEST_PETHISTORY_ID, model);

		Assertions.assertEquals(view, "redirect:/vet/{vetId}/pets/{petId}/pethistory");

	}

}
