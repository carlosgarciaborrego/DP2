
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;

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
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetHistory;
import org.springframework.samples.petclinic.service.PetHistoryService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = PetHistoryController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class PetHistoryControllerTests {

	@MockBean
	private PetHistoryService	petHistoryService;

	@MockBean
	private PetService			petService;

	@Autowired
	private MockMvc				mockMvc;

	private static final int	TEST_PET_ID	= 1;

	private static final int	TEST_VET_ID	= 1;


	@BeforeEach
	void setup() {

		PetHistory history = new PetHistory();
		history.setDate(LocalDate.parse("2010-09-07"));
		history.setDetails("details");
		history.setSummary("summary 1");
		Pet p = this.petService.findPetById(1);
		history.setPets(p);

		PetHistory history2 = new PetHistory();
		history2.setDate(LocalDate.parse("2011-09-07"));
		history2.setDetails("details");
		history2.setSummary("summary 2");
		history2.setPets(p);
		BDDMockito.given(this.petHistoryService.findPetHistories()).willReturn(Lists.newArrayList(history, history2));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitList() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/vet/{vetId}/pets/{petId}/pethistory", PetHistoryControllerTests.TEST_PET_ID, PetHistoryControllerTests.TEST_VET_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("petHistories")).andExpect(MockMvcResultMatchers.view().name("petHistory/petHistoryList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/vet/{vetId}/pets/{petId}/pethistory/new", PetHistoryControllerTests.TEST_PET_ID, PetHistoryControllerTests.TEST_VET_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("petHistory")).andExpect(MockMvcResultMatchers.view().name("petHistory/editPetHistory"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/vet/{vetId}/pets/{petId}/pethistory/new", PetHistoryControllerTests.TEST_PET_ID, PetHistoryControllerTests.TEST_VET_ID).param("summary", "summary 1").param("date", "2010-09-07")
			.param("details", "details").with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/vet/{vetId}/pets/{petId}/pethistory/new", PetHistoryControllerTests.TEST_PET_ID, PetHistoryControllerTests.TEST_VET_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("summary", "")
				.param("date", "2010-09-07").param("details", ""))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("petHistory")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("petHistory", "summary"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("petHistory", "details")).andExpect(MockMvcResultMatchers.view().name("petHistory/editPetHistory"));
	}

}
