
package org.springframework.samples.petclinic.web.e2e;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
public class HotelControllerE2ETest {

	private static final int	TEST_HOTEL_ID	= 1;

	@Autowired
	private MockMvc				mockMvc;


	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/hotels/new", HotelControllerE2ETest.TEST_HOTEL_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("hotel"))
			.andExpect(MockMvcResultMatchers.view().name("hotels/editHotel"));
	}

	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/hotels/save").param("name", "Clinica").param("capacity", "15").with(SecurityMockMvcRequestPostProcessors.csrf()).param("count", "1").param("location", "Huelva"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/hotels/save").with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "Clinica").param("count", "").param("location", "")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("hotel")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("hotel", "location")).andExpect(MockMvcResultMatchers.view().name("hotels/editHotel"));
	}

	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testInitUpdateHotelForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/hotels/{hotelId}/edit", HotelControllerE2ETest.TEST_HOTEL_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("hotel"))
			.andExpect(MockMvcResultMatchers.model().attribute("hotel", Matchers.hasProperty("name", Matchers.is("Calle Cadiz")))).andExpect(MockMvcResultMatchers.model().attribute("hotel", Matchers.hasProperty("capacity", Matchers.is(10))))
			.andExpect(MockMvcResultMatchers.model().attribute("hotel", Matchers.hasProperty("count", Matchers.is(0)))).andExpect(MockMvcResultMatchers.model().attribute("hotel", Matchers.hasProperty("location", Matchers.is("Sevilla"))))
			.andExpect(MockMvcResultMatchers.view().name("hotels/editHotel"));
	}

	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testProcessUpdateHotelFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/hotels/{hotelId}/edit", HotelControllerE2ETest.TEST_HOTEL_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "Clinica").param("count", "1").param("location", ""))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("hotel")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("hotel", "location"))
			.andExpect(MockMvcResultMatchers.view().name("hotels/editHotel"));
	}

	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testShowOwner() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/hotels/{hotelId}", HotelControllerE2ETest.TEST_HOTEL_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("hotel", Matchers.hasProperty("name", Matchers.is("Calle Cadiz")))).andExpect(MockMvcResultMatchers.model().attribute("hotel", Matchers.hasProperty("capacity", Matchers.is(10))))
			.andExpect(MockMvcResultMatchers.model().attribute("hotel", Matchers.hasProperty("count", Matchers.is(0)))).andExpect(MockMvcResultMatchers.model().attribute("hotel", Matchers.hasProperty("location", Matchers.is("Sevilla"))))
			.andExpect(MockMvcResultMatchers.view().name("hotels/editHotel"));
	}

}
