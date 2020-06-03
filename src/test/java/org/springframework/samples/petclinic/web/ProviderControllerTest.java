
package org.springframework.samples.petclinic.web;

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
import org.springframework.samples.petclinic.model.Provider;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.service.ProviderService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = ProviderController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class ProviderControllerTest {

	private static final int	TEST_PROVIDER_ID	= 1;

	private static final int	TEST_CLINIC_ID		= 1;

	@Autowired
	private ProviderController	providerController;

	@MockBean
	private ProviderService		providerService;

	@MockBean
	private ClinicService		clinicService;

	@Autowired
	private MockMvc				mockMvc;

	private Provider			provider;

	private Clinic				clinic;


	@BeforeEach
	void setup() {

		this.provider = new Provider();
		this.provider.setId(ProviderControllerTest.TEST_PROVIDER_ID);
		this.provider.setName("mercadona");
		this.provider.setCity("Sevilla");
		this.provider.setTelephone("664455669");
		this.provider.setDescription("comida para los animales");

		this.clinic = new Clinic();
		this.clinic.setId(1);
		this.clinic.setCapacity(20);
		this.clinic.setName("Holly Clinic");
		this.clinic.setEmail("clinic1@gmail.com");
		this.clinic.setEmergency("665544331");
		this.clinic.setLocation("Sevilla");
		this.clinic.setTelephone("955910011");
		//		Set<Provider> conjProv = new HashSet<Provider>();
		//		this.clinic.setProviders(conjProv);
		//		Set<Reservation> conjRes = new HashSet<Reservation>();
		//		this.clinic.setReservations(conjRes);
		this.clinicService.saveClinic(this.clinic);
		this.provider.setClinic(this.clinic);

		List<Provider> providers = new ArrayList<Provider>();
		providers.add(this.provider);
		List<Clinic> clinics = new ArrayList<Clinic>();
		clinics.add(this.clinic);

		BDDMockito.given(this.clinicService.findClinicById(ProviderControllerTest.TEST_CLINIC_ID)).willReturn(this.clinic);
		BDDMockito.given(this.providerService.findProviderById(ProviderControllerTest.TEST_PROVIDER_ID)).willReturn(this.provider);
		BDDMockito.given(this.clinicService.findAll()).willReturn(clinics);
		BDDMockito.given(this.providerService.findAll()).willReturn(providers);

	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/providers/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("provider"))
			.andExpect(MockMvcResultMatchers.view().name("providers/editProvider"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/providers/save").param("name", "mercadona").param("city", "Sevilla").with(SecurityMockMvcRequestPostProcessors.csrf()).param("telephone", "664455669")
			.param("description", "comida para los animales").param("clinic.id", "1")).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/providers/save").with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "mercadona").param("city", "Sevilla").param("description", "comida para los animales").param("clinic.id", "1"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("provider")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("provider", "telephone"))
			.andExpect(MockMvcResultMatchers.view().name("providers/editProvider"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateProviderForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/providers/{providerId}/edit", ProviderControllerTest.TEST_PROVIDER_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("provider"))
			.andExpect(MockMvcResultMatchers.model().attribute("provider", Matchers.hasProperty("name", Matchers.is("mercadona")))).andExpect(MockMvcResultMatchers.model().attribute("provider", Matchers.hasProperty("city", Matchers.is("Sevilla"))))
			.andExpect(MockMvcResultMatchers.model().attribute("provider", Matchers.hasProperty("telephone", Matchers.is("664455669"))))
			.andExpect(MockMvcResultMatchers.model().attribute("provider", Matchers.hasProperty("description", Matchers.is("comida para los animales")))).andExpect(MockMvcResultMatchers.view().name("providers/editProvider"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateProviderFormHasErrors() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/providers/{providerId}/edit", ProviderControllerTest.TEST_PROVIDER_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "mercadona").param("city", "Sevilla")
				.param("description", "comida para los animales").param("clinic.id", "1"))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("provider")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("provider", "telephone"))
			.andExpect(MockMvcResultMatchers.view().name("providers/editProvider"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowOwner() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/providers/{providerId}", ProviderControllerTest.TEST_PROVIDER_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("provider", Matchers.hasProperty("name", Matchers.is("mercadona")))).andExpect(MockMvcResultMatchers.model().attribute("provider", Matchers.hasProperty("city", Matchers.is("Sevilla"))))
			.andExpect(MockMvcResultMatchers.model().attribute("provider", Matchers.hasProperty("telephone", Matchers.is("664455669"))))
			.andExpect(MockMvcResultMatchers.model().attribute("provider", Matchers.hasProperty("description", Matchers.is("comida para los animales")))).andExpect(MockMvcResultMatchers.view().name("providers/editProvider"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessDeleteSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/providers/delete/{providerId}", ProviderControllerTest.TEST_PROVIDER_ID)).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.view().name("providers/listadoProviders"));
	}

}
