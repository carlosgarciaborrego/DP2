
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetHistory;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.service.PetHistoryService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = PetHistoryController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class PetHistoryControllerTests {

	@Autowired
	private PetHistoryController	petHistoryController;

	@MockBean
	private PetHistoryService		petHistoryService;

	@MockBean
	private PetService				petService;

	@MockBean
	private VetService				vetService;

	@Autowired
	private MockMvc					mockMvc;

	private static final int		TEST_PET_ID			= 1;

	private static final int		TEST_VET_ID			= 1;

	private static final int		TEST_PET_HISTORY_ID	= 1;


	@BeforeEach
	void setup() {

		PetHistory history = new PetHistory();
		history.setDate(LocalDate.parse("2010-09-07"));
		history.setDetails("details");
		history.setSummary("summary");
		history.setId(1);
		Pet p = new Pet();
		p.setName("Leo");
		p.setId(1);
		history.setPets(p);

		PetHistory history2 = new PetHistory();
		history2.setDate(LocalDate.parse("2011-09-07"));
		history2.setDetails("details");
		history2.setSummary("summary 2");
		history2.setPets(p);
		history2.setId(2);
		Vet james = new Vet();
		james.setFirstName("James");
		james.setLastName("Carter");
		james.setId(1);
		james.setAddress("110 W. Liberty St.");
		james.setTelephone("6085551023");
		james.setCity("Madison");
		Optional<Vet> vetsOpcional = Optional.of(james);
		List<Vet> vetsList = new ArrayList<>();
		vetsList.add(james);
		Optional<PetHistory> pethistoryOpcional = Optional.of(history);
		BDDMockito.given(this.petHistoryService.findPetHistories()).willReturn(Lists.newArrayList(history, history2));
		BDDMockito.given(this.petHistoryService.findPetHistoryByPetId(p.getId())).willReturn(Lists.newArrayList(history, history2));
		BDDMockito.given(this.vetService.findVetByUserId("vet1")).willReturn(vetsList);
		BDDMockito.given(this.petHistoryService.findPetHistoryById(history.getId())).willReturn(pethistoryOpcional);
		BDDMockito.given(this.petService.findPetById(p.getId())).willReturn(p);
		BDDMockito.given(this.vetService.findVetById(james.getId())).willReturn(vetsOpcional);

	}

	@WithMockUser(username = "vet1", authorities = {
		"veterinarian"
	})
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
		this.mockMvc.perform(MockMvcRequestBuilders.post("/vet/{vetId}/pets/{petId}/pethistory/new", PetHistoryControllerTests.TEST_PET_ID, PetHistoryControllerTests.TEST_VET_ID).param("summary", "summary 1").param("date", "2010/09/07")
			.param("details", "details").with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/vet/{vetId}/pets/{petId}/pethistory/new", PetHistoryControllerTests.TEST_PET_ID, PetHistoryControllerTests.TEST_VET_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("summary", "")
				.param("date", "2010/09/07").param("details", ""))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("petHistory")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("petHistory", "summary"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("petHistory", "details")).andExpect(MockMvcResultMatchers.view().name("petHistory/editPetHistory"));
	}

	@WithMockUser(username = "vet1", authorities = {
		"veterinarian"
	})
	@Test
	void testInitUpdatePetHistoryForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/vet/{vetId}/pets/{petId}/pethistory/{petHistoryId}", PetHistoryControllerTests.TEST_PET_ID, PetHistoryControllerTests.TEST_VET_ID, PetHistoryControllerTests.TEST_PET_HISTORY_ID))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attribute("petHistory", Matchers.hasProperty("summary", Matchers.is("summary"))))
			.andExpect(MockMvcResultMatchers.model().attribute("petHistory", Matchers.hasProperty("details", Matchers.is("details")))).andExpect(MockMvcResultMatchers.view().name("petHistory/editPetHistory"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdatePetHistoryFormSuccess() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/vet/{vetId}/pets/{petId}/pethistory/{petHistoryId}", PetHistoryControllerTests.TEST_PET_ID, PetHistoryControllerTests.TEST_VET_ID, PetHistoryControllerTests.TEST_PET_HISTORY_ID)
				.with(SecurityMockMvcRequestPostProcessors.csrf()).param("summary", "Esta muy malito").param("details", "details").param("id", "1").param("date", "2010/09/07"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/vet/{vetId}/pets/{petId}/pethistory"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdatePetHistoryFormError() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/vet/{vetId}/pets/{petId}/pethistory/{petHistoryId}", PetHistoryControllerTests.TEST_PET_ID, PetHistoryControllerTests.TEST_VET_ID, PetHistoryControllerTests.TEST_PET_HISTORY_ID)
				.with(SecurityMockMvcRequestPostProcessors.csrf()).param("summary", "Esta muy malito").param("details", ""))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("petHistory", "details")).andExpect(MockMvcResultMatchers.view().name("petHistory/editPetHistory"));
	}

}
