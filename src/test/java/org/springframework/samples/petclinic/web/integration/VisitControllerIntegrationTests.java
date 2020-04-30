
package org.springframework.samples.petclinic.web.integration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.service.HotelService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.web.VisitController;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VisitControllerIntegrationTests {

	private static final int	TEST_VISIT_ID	= 1;
	private static final int	TEST_HOTEL_ID	= 1;
	private static final int	TEST_PET_ID		= 1;

	@Autowired
	private VisitController		visitController;

	@Autowired
	private HotelService		hotelService;

	@Autowired
	private PetService			petService;


	@Test
	void testInitCreationForm() throws Exception {
		ModelMap model = new ModelMap();
		String view = this.visitController.initNewVisitForm(VisitControllerIntegrationTests.TEST_VISIT_ID, model);

		Assert.assertEquals(view, "pets/createOrUpdateVisitForm");
		Assert.assertNotNull(model.get("visit"));
	}

	@Test
	void testProcessCreationFormSuccess() throws Exception {
		ModelMap model = new ModelMap();
		Visit newVisit = new Visit();
		DateTimeFormatter fecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String date = "2020-06-24";
		LocalDate localDate = LocalDate.parse(date, fecha);
		newVisit.setDate(localDate);
		newVisit.setDescription("Dolor de barriga");
		Hotel hotel = this.hotelService.findHotelById(VisitControllerIntegrationTests.TEST_HOTEL_ID);
		newVisit.setHotel(hotel);
		Pet pet = this.petService.findPetById(VisitControllerIntegrationTests.TEST_PET_ID);
		newVisit.setPet(pet);
		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
		String view = this.visitController.processNewVisitForm(newVisit, bindingResult, model);
		Assert.assertEquals(view, "redirect:/owners/{ownerId}");
	}

	@Test
	void testProcessCreationFormHasError() throws Exception {
		ModelMap model = new ModelMap();
		Visit newVisit = new Visit();
		newVisit.setDescription("Dolor de barriga");
		Hotel hotel = this.hotelService.findHotelById(VisitControllerIntegrationTests.TEST_HOTEL_ID);
		newVisit.setHotel(hotel);
		Pet pet = this.petService.findPetById(VisitControllerIntegrationTests.TEST_PET_ID);
		newVisit.setPet(pet);
		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
		bindingResult.reject("reservationDate", "must not be null");
		String view = this.visitController.processNewVisitForm(newVisit, bindingResult, model);
		Assert.assertEquals(view, "pets/createOrUpdateVisitForm");
	}

	@Test
	void testDelete() throws Exception {
		ModelMap model = new ModelMap();
		String view = this.visitController.deleteVisit(VisitControllerIntegrationTests.TEST_HOTEL_ID, VisitControllerIntegrationTests.TEST_VISIT_ID, VisitControllerIntegrationTests.TEST_PET_ID, model);
		Assert.assertEquals(view, "redirect:/hotels");
	}

	@Test
	void testShow() throws Exception {
		ModelMap model = new ModelMap();
		String view = this.visitController.showVisits(VisitControllerIntegrationTests.TEST_PET_ID, model);
		Assert.assertEquals(view, "visitList");
	}

}
