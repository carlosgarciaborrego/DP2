
package org.springframework.samples.petclinic.web;

import org.assertj.core.util.Lists;
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
import org.springframework.samples.petclinic.service.ClinicService;
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
		Clinic c2 = new Clinic();
		c2.setName("George Clinic");
		c2.setCapacity(30);
		c2.setEmail("clinic1@gmail.com");
		c2.setId(2);
		BDDMockito.given(this.clinicService.findAll()).willReturn(Lists.newArrayList(c1, c2));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/clinic/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("clinic")).andExpect(MockMvcResultMatchers.view().name("clinic/show"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/clinic/new").param("name", "Clinica Santo Domingo").param("location", "Burgos").with(SecurityMockMvcRequestPostProcessors.csrf()).param("telephone", "666666666").param("emergency", "999999999")
			.param("capacity", "3").param("email", "hola@hotmail.com")).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

}
