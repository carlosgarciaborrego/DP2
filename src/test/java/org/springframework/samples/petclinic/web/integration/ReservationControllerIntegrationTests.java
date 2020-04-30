
package org.springframework.samples.petclinic.web.integration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Reservation;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.ReservationService;
import org.springframework.samples.petclinic.web.ReservationController;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerIntegrationTests {

	private static final int		TEST_RESERVATION_ID	= 1;
	private static final int		TEST_OWNER_ID		= 1;
	private static final int		TEST_CLINIC_ID		= 1;

	@Autowired
	private ReservationController	reservationController;

	@Autowired
	private ReservationService		reservationService;

	@Autowired
	private OwnerService			ownerService;

	@Autowired
	private ClinicService			clinicService;


	@Test
	void testInitCreationForm() throws Exception {
		ModelMap model = new ModelMap();
		String view = this.reservationController.crearCita(model);

		Assert.assertEquals(view, "reservations/editCita");
		Assert.assertNotNull(model.get("reservation"));
	}

	@Test
	void testProcessCreationFormSuccess() throws Exception {
		ModelMap model = new ModelMap();
		Reservation reservation = new Reservation();
		reservation.setTelephone("667788990");
		reservation.setStatus("pending");
		DateTimeFormatter fecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String date = "2020-06-24";
		LocalDate localDate = LocalDate.parse(date, fecha);
		reservation.setReservationDate(localDate);
		reservation.setResponseClient("Puedo antes de las 17:00");
		Owner owner = this.ownerService.findOwnerById(ReservationControllerIntegrationTests.TEST_OWNER_ID);
		reservation.setOwner(owner);
		Clinic clinic = this.clinicService.findClinicById(ReservationControllerIntegrationTests.TEST_CLINIC_ID).get();
		reservation.setClinic(clinic);
		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
		String view = this.reservationController.salvarCita(reservation, bindingResult, model);
		Assert.assertEquals(view, "redirect:/reservations");
	}

	@Test
	void testProcessCreationFormHasError() throws Exception {
		ModelMap model = new ModelMap();
		Reservation reservation = new Reservation();
		reservation.setTelephone("667788990");
		reservation.setStatus("pending");
		reservation.setResponseClient("Puedo antes de las 17:00");
		Owner owner = this.ownerService.findOwnerById(ReservationControllerIntegrationTests.TEST_OWNER_ID);
		reservation.setOwner(owner);
		Clinic clinic = this.clinicService.findClinicById(ReservationControllerIntegrationTests.TEST_CLINIC_ID).get();
		reservation.setClinic(clinic);
		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
		bindingResult.reject("reservationDate", "must not be null");
		String view = this.reservationController.salvarCita(reservation, bindingResult, model);
		Assert.assertEquals(view, "reservations/editCita");
	}

	@Test
	void testInitUpdateForm() throws Exception {
		ModelMap model = new ModelMap();

		String view = this.reservationController.aceptarCita(model, ReservationControllerIntegrationTests.TEST_RESERVATION_ID);
		Assert.assertEquals(view, "reservations/editCita");
		Assert.assertNotNull(model.get("reservation"));
	}

	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		ModelMap model = new ModelMap();
		Reservation reservation = new Reservation();
		reservation.setTelephone("665544332");
		reservation.setStatus("pending");
		DateTimeFormatter fecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String date = "2020-06-20";
		LocalDate localDate = LocalDate.parse(date, fecha);
		reservation.setReservationDate(localDate);
		reservation.setResponseClient("");
		Owner owner = this.ownerService.findOwnerById(ReservationControllerIntegrationTests.TEST_OWNER_ID);
		reservation.setOwner(owner);
		Clinic clinic = this.clinicService.findClinicById(ReservationControllerIntegrationTests.TEST_CLINIC_ID).get();
		reservation.setClinic(clinic);
		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
		String view = this.reservationController.actualizarCitaPost(reservation, bindingResult, ReservationControllerIntegrationTests.TEST_RESERVATION_ID, model);
		Assert.assertEquals(view, "redirect:/reservations/{reservationId}");
	}

	@Test
	void testProcessUpdateFormHasError() throws Exception {
		ModelMap model = new ModelMap();
		Reservation reservation = new Reservation();
		reservation.setStatus("pending");
		reservation.setResponseClient("");
		Owner owner = this.ownerService.findOwnerById(ReservationControllerIntegrationTests.TEST_OWNER_ID);
		reservation.setOwner(owner);
		Clinic clinic = this.clinicService.findClinicById(ReservationControllerIntegrationTests.TEST_CLINIC_ID).get();
		reservation.setClinic(clinic);
		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
		bindingResult.reject("telephone", "must not be null");
		String view = this.reservationController.actualizarCitaPost(reservation, bindingResult, ReservationControllerIntegrationTests.TEST_RESERVATION_ID, model);
		Assert.assertEquals(view, "reservations/editCita");
	}

	@Test
	void testDelete() throws Exception {
		ModelMap model = new ModelMap();
		String view = this.reservationController.borrarCita(ReservationControllerIntegrationTests.TEST_RESERVATION_ID, model);
		Assert.assertEquals(view, "redirect:/reservations/listadoCitas");
	}

	@Test
	void testShow() throws Exception {
		ModelMap model = new ModelMap();
		String view = this.reservationController.showCita(ReservationControllerIntegrationTests.TEST_RESERVATION_ID, model);
		Assert.assertEquals(view, "reservations/editCita");
	}

}
