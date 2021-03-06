
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Reservation;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.ReservationService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = ReservationController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)

public class ReservationControllerTests {

	private static final int		TEST_RESERVATION_ID	= 1;

	@Autowired
	private ReservationController	reservationcontroller;

	@MockBean
	private ReservationService		reservationService;

	@MockBean
	private ClinicService			clinicService;

	@MockBean
	private OwnerService			ownerService;

	@Autowired
	private MockMvc					mockMvc;

	private Reservation				reservation;

	private Clinic					clinica				= new Clinic();


	@BeforeEach
	void setup() {

		this.reservation = new Reservation();
		this.reservation.setId(ReservationControllerTests.TEST_RESERVATION_ID);
		this.reservation.setTelephone("664455667");
		DateTimeFormatter fecha = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String date = "2020/06/24";
		LocalDate localDate = LocalDate.parse(date, fecha);
		this.reservation.setReservationDate(localDate);
		this.reservation.setStatus("pending");
		this.reservation.setResponseClient("hola");
		Owner owner = new Owner();
		owner.setId(1);
		owner.setFirstName("George");
		owner.setLastName("Franklin");
		owner.setAddress("110 W. Liberty St.");
		owner.setCity("Madison");
		owner.setTelephone("666777888");
		this.reservation.setOwner(owner);
		Clinic cli = new Clinic();
		cli.setId(1);
		cli.setCapacity(20);
		cli.setName("clinica1");
		cli.setEmail("cli1@gmail.com");
		cli.setEmergency("666777666");
		cli.setLocation("sevilla");
		cli.setTelephone("565656561");
		this.reservation.setClinic(cli);
		this.clinica = cli;

		this.clinicService.saveClinic(cli);
		this.ownerService.saveOwner(owner);
		this.reservationService.save(this.reservation);

		List<Owner> ownersList = new ArrayList<>();
		ownersList.add(owner);

		List<Reservation> resList = new ArrayList<Reservation>();
		resList.add(this.reservation);

		BDDMockito.given(this.reservationService.findOwnerByUserId("owner1")).willReturn(ownersList);
		BDDMockito.given(this.reservationService.findOwnerById(1)).willReturn(owner);
		BDDMockito.given(this.reservationService.findClinicById(1)).willReturn(cli);
		BDDMockito.given(this.reservationService.findReservationById(ReservationControllerTests.TEST_RESERVATION_ID)).willReturn(this.reservation);
		BDDMockito.given(this.reservationService.findAll()).willReturn(resList);

	}

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
		this.mockMvc.perform(MockMvcRequestBuilders.post("/reservations/new").param("telephone", "664455667").param("reservationDate", "2020/06/24").with(SecurityMockMvcRequestPostProcessors.csrf()).param("status", "pending")
			.param("responseClient", "hola").param("clinic.id", "1").param("owner.id", "1")).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(username = "owner1", authorities = {
		"owner"
	})
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/reservations/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("reservationDate", "2020/06/24").param("status", "pending").param("responseClient", "hola").param("clinic.id", "1")
				.param("owner.id", "1"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("reservation")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("reservation", "telephone"))
			.andExpect(MockMvcResultMatchers.view().name("reservations/editCita"));
	}

	@WithMockUser(username = "owner1", authorities = {
		"owner"
	})
	@Test
	void testInitUpdateReservationForm() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/reservations/{reservationId}/edit", ReservationControllerTests.TEST_RESERVATION_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("reservation")).andExpect(MockMvcResultMatchers.model().attribute("reservation", Matchers.hasProperty("responseClient", Matchers.is("hola"))))
			.andExpect(MockMvcResultMatchers.view().name("reservations/editCita"));
	}

	@WithMockUser(username = "owner1", authorities = {
		"owner"
	})
	@Test
	void testProcessUpdateReservationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/reservations/{reservationId}/edit", ReservationControllerTests.TEST_RESERVATION_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("status", "accepted").param("responseClient", "OK")
			.param("clinic.id", "1").param("owner.id", "1")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}

	@WithMockUser(username = "owner1", authorities = {
		"owner"
	})
	@Test
	void testShowReservation() throws Exception {
		DateTimeFormatter fecha = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String date = "2020/06/24";
		LocalDate localDate = LocalDate.parse(date, fecha);
		Reservation res = this.reservationService.findReservationById(ReservationControllerTests.TEST_RESERVATION_ID);
		Owner owner = res.getOwner();
		Clinic clinic = res.getClinic();
		this.mockMvc.perform(MockMvcRequestBuilders.get("/reservations/{reservationId}", ReservationControllerTests.TEST_RESERVATION_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("reservation", Matchers.hasProperty("telephone", Matchers.is("664455667"))))
			.andExpect(MockMvcResultMatchers.model().attribute("reservation", Matchers.hasProperty("reservationDate", Matchers.is(localDate))))
			.andExpect(MockMvcResultMatchers.model().attribute("reservation", Matchers.hasProperty("status", Matchers.is("pending"))))
			.andExpect(MockMvcResultMatchers.model().attribute("reservation", Matchers.hasProperty("responseClient", Matchers.is("hola")))).andExpect(MockMvcResultMatchers.model().attribute("reservation", Matchers.hasProperty("owner", Matchers.is(owner))))
			.andExpect(MockMvcResultMatchers.model().attribute("reservation", Matchers.hasProperty("clinic", Matchers.is(clinic)))).andExpect(MockMvcResultMatchers.view().name("reservations/editCita"));
	}

	@WithMockUser(username = "owner1", authorities = {
		"owner"
	})
	@Test
	void testProcessDeleteSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/reservations/delete/{reservationId}", ReservationControllerTests.TEST_RESERVATION_ID)).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.view().name("reservations/listadoCitas"));
	}
}
