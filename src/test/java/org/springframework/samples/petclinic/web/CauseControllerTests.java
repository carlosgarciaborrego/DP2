package org.springframework.samples.petclinic.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.service.CauseService;
import org.springframework.samples.petclinic.service.DonationService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = CauseController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class CauseControllerTests {

	private static final int	TEST_CAUSE_ID	= 1;
	
	private static final int	TEST_DONATION_ID	= 1;
	
	@Autowired
	private CauseController		causeController;

	@MockBean
	private CauseService			causeService;
	
	@MockBean
	private DonationService			donationService;
	
	@Autowired
	private MockMvc				mockMvc;
	
	private Cause				cause;
	
	private Donation				donation;
	
	@BeforeEach
	void setup() {

		this.cause = new Cause();
		this.cause.setId(CauseControllerTests.TEST_CAUSE_ID);
		this.cause.setName("NombreCause");
		this.cause.setDescription("Description Cause");
		this.cause.setOrganisation("Organisation Cause");
		this.cause.setBudgetTarget(1000.0);
		
		this.donation = new Donation();
		this.donation.setId(CauseControllerTests.TEST_DONATION_ID);
		this.donation.setName("Donacion Juan");
		this.donation.setAmount(100.0);
		this.donation.setCause(cause);
		
		BDDMockito.given(this.causeService.findCauseById(CauseControllerTests.TEST_CAUSE_ID)).willReturn(this.cause);
		BDDMockito.given(this.donationService.findDonationById(CauseControllerTests.TEST_DONATION_ID)).willReturn(this.donation);

	}
	

	@WithMockUser(value = "spring")
    @Test
    void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/causes/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("cause")).andExpect(MockMvcResultMatchers.view().name("causes/editCause"));
	}
    
	@WithMockUser(value = "spring")
    @Test
    void testInitCreationFormDonation() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("causes/donate/{causeId}", CauseControllerTests.TEST_CAUSE_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("cause")).andExpect(MockMvcResultMatchers.view().name("causes/donateCause"));
	}
	
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/causes/save").param("name", "CauseTest").param("description", "Descripcion Test Cause").with(SecurityMockMvcRequestPostProcessors.csrf()).param("organisation", "Organisation Test Cause").param("budgetTarget", "1000.0"))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	

	
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccessDonation() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("causes/donation/save/{causeId}", CauseControllerTests.TEST_CAUSE_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "DonationTest").param("amount", "150.0"))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("redirect:/causes/listadoCauses"));;
	
//		this.mockMvc.perform(MockMvcRequestBuilders.post("/owners/{ownerId}/pets/new", PetControllerTests.TEST_OWNER_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "Betty").param("type", "hamster").param("birthDate", "2015/02/12"))
//		.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/owners/{ownerId}"));

	
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrorsDonation() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("causes/donation/save/{causeId}", CauseControllerTests.TEST_CAUSE_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "Donation Prueba Controller").param("ammount", "vhjlhj")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("donation")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("donation", "amount")).andExpect(MockMvcResultMatchers.view().name("causes/donateCause"));
	}
	
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/causes/save").with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "CauseTest").param("description", "").param("organisation", "")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("cause")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("cause", "organisation")).andExpect(MockMvcResultMatchers.view().name("causes/editCause"));
	}
	
	
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateHotelFormSuccess() throws Exception {
		this.mockMvc
			.perform(
				MockMvcRequestBuilders.post("/causes/{causeId}/edit", CauseControllerTests.TEST_CAUSE_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "CauseTest").param("description", "Descripcion Test Cause").param("organisation", "Organisation Test Cause").param("budgetTarget", "1000.0").param("budgetArchivied", "0.0"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/causes/{causeId}"));
	}

	
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateHotelFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/causes/{causeId}/edit", CauseControllerTests.TEST_CAUSE_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "CauseTest").param("description", "Descripcion Test Cause").param("organisation", "").param("budgetTarget", "1000.0").param("budgetArchivied", "0.0"))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("cause")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("cause", "organisation"))
			.andExpect(MockMvcResultMatchers.view().name("causes/editCause"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowOwner() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/causes/{causeId}", CauseControllerTests.TEST_CAUSE_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("cause", Matchers.hasProperty("name", Matchers.is("NombreCause")))).andExpect(MockMvcResultMatchers.model().attribute("cause", Matchers.hasProperty("description", Matchers.is("Description Cause"))))
			.andExpect(MockMvcResultMatchers.model().attribute("cause", Matchers.hasProperty("organisation", Matchers.is("Organisation Cause")))).andExpect(MockMvcResultMatchers.model().attribute("cause", Matchers.hasProperty("budgetTarget", Matchers.is(1000.0)))).andExpect(MockMvcResultMatchers.model().attribute("cause", Matchers.hasProperty("budgetArchivied", Matchers.is(0.0))))
			.andExpect(MockMvcResultMatchers.view().name("causes/editCause"));
	}
	
	
}
