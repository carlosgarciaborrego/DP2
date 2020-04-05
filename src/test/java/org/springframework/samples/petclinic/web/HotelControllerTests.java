
package org.springframework.samples.petclinic.web;

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
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.service.HotelService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = HotelController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class HotelControllerTests {

	private static final int	TEST_HOTEL_ID	= 1;

	@Autowired
	private HotelController		hotelcontroller;

	@MockBean
	private HotelService		hotelService;

	@Autowired
	private MockMvc				mockMvc;

	private Hotel				hotel;


	@BeforeEach
	void setup() {

		this.hotel = new Hotel();
		this.hotel.setId(HotelControllerTests.TEST_HOTEL_ID);
		this.hotel.setName("Externa");
		this.hotel.setCapacity(10);
		this.hotel.setCount(3);
		this.hotel.setLocation("Sevilla");

		BDDMockito.given(this.hotelService.findHotelById(HotelControllerTests.TEST_HOTEL_ID)).willReturn(this.hotel);

	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("hotel")).andExpect(MockMvcResultMatchers.view().name("hotels/editHotel"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/save").param("name", "Clinica").param("capacity", "15").with(SecurityMockMvcRequestPostProcessors.csrf()).param("count", "1").param("location", "Huelva"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/save").with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "Clinica").param("count", "1").param("location", "Huelva")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("hotel")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("hotel", "capacity")).andExpect(MockMvcResultMatchers.view().name("hotels/editHotel"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateHotelFormSuccess() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/{hotelsId}/edit", HotelControllerTests.TEST_HOTEL_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "Clinica").param("capacity", "15").param("count", "1").param("location", "Cadiz"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/hotels/{hotelId}"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateHotelFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/owners/{ownerId}/edit", HotelControllerTests.TEST_HOTEL_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "Clinica").param("count", "1").param("location", "Cadiz"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("hotel")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("hotel", "capacity"))
			.andExpect(MockMvcResultMatchers.view().name("hotels/editHotel"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowOwner() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/{hotelId}", HotelControllerTests.TEST_HOTEL_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("hotel", Matchers.hasProperty("name", Matchers.is("Externo")))).andExpect(MockMvcResultMatchers.model().attribute("hotel", Matchers.hasProperty("capacity", Matchers.is(10))))
			.andExpect(MockMvcResultMatchers.model().attribute("hotel", Matchers.hasProperty("count", Matchers.is(3)))).andExpect(MockMvcResultMatchers.model().attribute("hotel", Matchers.hasProperty("location", Matchers.is("Sevilla"))))
			.andExpect(MockMvcResultMatchers.view().name("redirect:/show/{hotelId}"));
	}
}
