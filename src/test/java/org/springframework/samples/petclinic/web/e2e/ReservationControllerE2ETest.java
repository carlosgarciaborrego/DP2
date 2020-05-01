
package org.springframework.samples.petclinic.web.e2e;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Reservation;
import org.springframework.samples.petclinic.service.ReservationService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
/*
 * @TestPropertySource(
 * locations = "classpath:application-mysql.properties")
 */
public class ReservationControllerE2ETest {

	private static final int	TEST_RESERVATION_ID	= 1;

	@Autowired
	private ReservationService	reservationService;

	@Autowired
	private MockMvc				mockMvc;


	@WithMockUser(username = "owner1", authorities = {
		"owner"
	})
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/reservations/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("reservation"))
			.andExpect(MockMvcResultMatchers.view().name("reservations/editCita"));
	}

	@WithMockUser(username = "owner1", authorities = {
		"owner"
	})
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/reservations/new").param("telephone", "664455667").param("reservationDate", "2020-06-24").with(SecurityMockMvcRequestPostProcessors.csrf()).param("status", "pending").param("responseClient", "hola"))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}

	@WithMockUser(username = "owner1", authorities = {
		"owner"
	})
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/reservations/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("reservationDate", "2020-06-24").param("status", "pending").param("responseClient", "hola"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("reservation")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("reservation", "telephone"))
			.andExpect(MockMvcResultMatchers.view().name("reservations/editCita"));
	}

	@WithMockUser(username = "owner1", authorities = {
		"owner"
	})
	@Test
	void testInitUpdateReservationForm() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/reservations/{reservationId}/edit", ReservationControllerE2ETest.TEST_RESERVATION_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("reservation")).andExpect(MockMvcResultMatchers.model().attribute("reservation", Matchers.hasProperty("responseClient", Matchers.is("adios"))))
			.andExpect(MockMvcResultMatchers.view().name("reservations/editCita"));
	}

	@WithMockUser(username = "owner1", authorities = {
		"owner"
	})
	@Test
	void testProcessUpdateReservationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/reservations/{reservationId}/edit", ReservationControllerE2ETest.TEST_RESERVATION_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("status", "accepted").param("responseClient", "OK"))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}

	@WithMockUser(username = "owner1", authorities = {
		"owner"
	})
	@Test
	void testShowReservation() throws Exception {
		DateTimeFormatter fecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String date = "2020-06-24";
		LocalDate localDate = LocalDate.parse(date, fecha);
		Reservation res = this.reservationService.findReservationById(ReservationControllerE2ETest.TEST_RESERVATION_ID);
		Owner owner = res.getOwner();
		Clinic clinic = res.getClinic();
		this.mockMvc.perform(MockMvcRequestBuilders.get("/reservations/{reservationId}", ReservationControllerE2ETest.TEST_RESERVATION_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("reservation", Matchers.hasProperty("telephone", Matchers.is("664455667"))))
			.andExpect(MockMvcResultMatchers.model().attribute("reservation", Matchers.hasProperty("reservationDate", Matchers.is(localDate))))
			.andExpect(MockMvcResultMatchers.model().attribute("reservation", Matchers.hasProperty("status", Matchers.is("pending"))))
			.andExpect(MockMvcResultMatchers.model().attribute("reservation", Matchers.hasProperty("responseClient", Matchers.is("adios"))))
			.andExpect(MockMvcResultMatchers.model().attribute("reservation", Matchers.hasProperty("owner", Matchers.is(owner)))).andExpect(MockMvcResultMatchers.model().attribute("reservation", Matchers.hasProperty("clinic", Matchers.is(clinic))))
			.andExpect(MockMvcResultMatchers.view().name("reservations/editCita"));
	}

}
