
package org.springframework.samples.petclinic.web;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.assertj.core.util.Lists;
import org.hamcrest.xml.HasXPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Test class for the {@link VetController}
 */
@WebMvcTest(controllers = VetController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class VetControllerTests {

	@Autowired
	private VetController	vetController;

	@MockBean
	private VetService		clinicService;

	@Autowired
	private MockMvc			mockMvc;
	
	private static final int TEST_VET_ID = 1;


	@BeforeEach
	void setup() {

		Vet james = new Vet();
		james.setFirstName("James");
		james.setLastName("Carter");
		james.setId(1);
		Vet helen = new Vet();
		helen.setFirstName("Helen");
		helen.setLastName("Leary");
		helen.setId(2);
		Specialty radiology = new Specialty();
		radiology.setId(1);
		radiology.setName("radiology");
		helen.addSpecialty(radiology);
		BDDMockito.given(this.clinicService.findVets()).willReturn(Lists.newArrayList(james, helen));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowVetListHtml() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/vets")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("vets")).andExpect(MockMvcResultMatchers.view().name("vets/vetList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowVetListXml() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/vets.xml").accept(MediaType.APPLICATION_XML)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_XML_VALUE))
			.andExpect(MockMvcResultMatchers.content().node(HasXPath.hasXPath("/vets/vetList[id=1]/id")));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/vet/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("vet")).andExpect(MockMvcResultMatchers.view().name("vet/createOrUpdateVetForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(
			MockMvcRequestBuilders.post("/vet/new").param("firstName", "Joe").param("lastName", "Bloggs").with(SecurityMockMvcRequestPostProcessors.csrf()).param("address", "123 Caramel Street").param("city", "London").param("telephone", "01316761638"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/vet/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("firstName", "Joe").param("lastName", "Bloggs").param("city", "London")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("vet")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("vet", "address")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("vet", "telephone"))
			.andExpect(MockMvcResultMatchers.view().name("vet/createOrUpdateVetForm"));
	}


	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateVetForm() throws Exception {
		mockMvc.perform(post("/vet/show/{vetId}", TEST_VET_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("vet"))
				.andExpect(model().attribute("vet", hasProperty("lastName", is("Carter"))))
				.andExpect(model().attribute("vet", hasProperty("firstName", is("James"))))
				.andExpect(model().attribute("vet", hasProperty("address", is("110 W. Liberty St."))))
				.andExpect(model().attribute("vet", hasProperty("city", is("Madison"))))
				.andExpect(model().attribute("vet", hasProperty("telephone", is("6085551023"))))
				.andExpect(view().name("vet/createOrUpdateVetForm"));
	}

        @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateVetFormSuccess() throws Exception {
		mockMvc.perform(post("/vet/show/{vetId}", TEST_VET_ID)
							.with(csrf())
							.param("firstName", "Joe")
							.param("lastName", "Bloggs")
							.param("address", "123 Caramel Street")
							.param("city", "London")
							.param("telephone", "01616291589"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/vet/show/{vetId}"));
	}

        @WithMockUser(value = "spring")
	@Test
	void testProcessUpdateVetFormHasErrors() throws Exception {
		mockMvc.perform(post("/vet/show/{vetId}", TEST_VET_ID)
							.with(csrf())
							.param("firstName", "Joe")
							.param("lastName", "Bloggs")
							.param("city", "London"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("vet"))
				.andExpect(model().attributeHasFieldErrors("vet", "address"))
				.andExpect(model().attributeHasFieldErrors("vet", "telephone"))
				.andExpect(view().name("vet/createOrUpdateVetForm"));
	}

        @WithMockUser(value = "spring")
	@Test
	void testShowVet() throws Exception {
    		mockMvc.perform(get("/vet/show/{vetId}", TEST_VET_ID)).andExpect(status().isOk())
			.andExpect(model().attributeExists("vet"))
			.andExpect(model().attribute("vet", hasProperty("lastName", is("Carter"))))
			.andExpect(model().attribute("vet", hasProperty("firstName", is("James"))))
			.andExpect(model().attribute("vet", hasProperty("address", is("110 W. Liberty St."))))
			.andExpect(model().attribute("vet", hasProperty("city", is("Madison"))))
			.andExpect(model().attribute("vet", hasProperty("telephone", is("6085551023"))))
			.andExpect(view().name("vet/createOrUpdateVetForm"));
	}
}
