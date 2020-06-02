
package org.springframework.samples.petclinic.web;

import org.assertj.core.util.Lists;
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
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(value = ClinicController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class ClinicControllerTests {

	@Autowired
	private ClinicController	clinicController;

	@MockBean
	private ClinicService		clinicService;

	@MockBean
	private VetService			vetService;

	@Autowired
	private MockMvc				mockMvc;

	private static final int	TEST_CLINIC_ID	= 1;


	@BeforeEach
	void setup() {

		Clinic c1 = new Clinic();
		c1.setName("Holly Clinic");
		c1.setCapacity(50);
		c1.setEmail("clinic1@gmail.com");
		c1.setId(1);
		c1.setEmergency("955910011");
		c1.setTelephone("665544331");
		Clinic c2 = new Clinic();
		c2.setName("George Clinic");
		c2.setCapacity(30);
		c2.setEmail("clinic1@gmail.com");
		c2.setId(2);

		Vet v = new Vet();
		v.setFirstName("James");
		v.setLastName("Carter");
		v.setAddress("110 W. Liberty St.");
		v.setId(1);
		v.setClinic(c1);
		BDDMockito.given(this.clinicService.findAll()).willReturn(Lists.newArrayList(c1, c2));
		BDDMockito.given(this.clinicService.findClinicById(c2.getId())).willReturn(c2);
		BDDMockito.given(this.clinicService.findClinicById(c1.getId())).willReturn(c1);
		BDDMockito.given(this.vetService.findVetByClinicId(c2.getId())).willReturn(Lists.newArrayList(v));

	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/clinic/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("clinic/show"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/clinic/new").param("name", "Clinica Santo Domingo").param("location", "Burgos").with(SecurityMockMvcRequestPostProcessors.csrf()).param("telephone", "666666666").param("emergency", "999999999")
			.param("capacity", "3").param("email", "hola@hotmail.com")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/clinic/new").param("name", "Clinica Santo Domingo").param("location", "").with(SecurityMockMvcRequestPostProcessors.csrf()).param("telephone", "666666666").param("emergency", "999999999")
			.param("capacity", "3").param("email", "hola@hotmail.com")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("clinic", "location")).andExpect(MockMvcResultMatchers.view().name("clinic/show"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateClinicForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/clinic/{clinicId}", ClinicControllerTests.TEST_CLINIC_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("clinic", Matchers.hasProperty("name", Matchers.is("Holly Clinic")))).andExpect(MockMvcResultMatchers.model().attribute("clinic", Matchers.hasProperty("capacity", Matchers.is(50))))
			.andExpect(MockMvcResultMatchers.model().attribute("clinic", Matchers.hasProperty("email", Matchers.is("clinic1@gmail.com"))))
			.andExpect(MockMvcResultMatchers.model().attribute("clinic", Matchers.hasProperty("emergency", Matchers.is("955910011")))).andExpect(MockMvcResultMatchers.model().attribute("clinic", Matchers.hasProperty("telephone", Matchers.is("665544331"))))
			.andExpect(MockMvcResultMatchers.view().name("clinic/show"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateClinicFormSuccess() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/clinic/{clinicId}", ClinicControllerTests.TEST_CLINIC_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "Clinica Santa Clara").param("location", "Burgos")
				.with(SecurityMockMvcRequestPostProcessors.csrf()).param("telephone", "666666666").param("emergency", "999999999").param("capacity", "3").param("email", "hola@hotmail.com"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/clinic"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateClinicFormError() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/clinic/{clinicId}", ClinicControllerTests.TEST_CLINIC_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "Clinica Santa Clara").param("location", "")
				.with(SecurityMockMvcRequestPostProcessors.csrf()).param("telephone", "666666666").param("emergency", "999999999").param("capacity", "3").param("email", "hola@hotmail.com"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("clinic", "location")).andExpect(MockMvcResultMatchers.view().name("clinic/show"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessShowClinicVetsSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/clinic/{clinicId}/vets", ClinicControllerTests.TEST_CLINIC_ID)).andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("vets/vetList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessDeleteSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/clinic/{clinicId}/delete", 2)).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/clinic"));
	}

}
