
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
		this.hotel.setCount(0);
		this.hotel.setLocation("Sevilla");

		List<Hotel> hotels = new ArrayList<Hotel>();
		hotels.add(this.hotel);

		BDDMockito.given(this.hotelService.findHotelById(HotelControllerTests.TEST_HOTEL_ID)).willReturn(this.hotel);
		BDDMockito.given(this.hotelService.findAll()).willReturn(hotels);

	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/hotels/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("hotel")).andExpect(MockMvcResultMatchers.view().name("hotels/editHotel"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/hotels/save").param("name", "Clinica").param("capacity", "15").with(SecurityMockMvcRequestPostProcessors.csrf()).param("count", "1").param("location", "Huelva"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/hotels/save").with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "Clinica").param("count", "").param("location", "")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("hotel")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("hotel", "location")).andExpect(MockMvcResultMatchers.view().name("hotels/editHotel"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateHotelForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/hotels/{hotelId}/edit", HotelControllerTests.TEST_HOTEL_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("hotel"))
			.andExpect(MockMvcResultMatchers.model().attribute("hotel", Matchers.hasProperty("name", Matchers.is("Externa")))).andExpect(MockMvcResultMatchers.model().attribute("hotel", Matchers.hasProperty("capacity", Matchers.is(10))))
			.andExpect(MockMvcResultMatchers.model().attribute("hotel", Matchers.hasProperty("count", Matchers.is(0)))).andExpect(MockMvcResultMatchers.model().attribute("hotel", Matchers.hasProperty("location", Matchers.is("Sevilla"))))
			.andExpect(MockMvcResultMatchers.view().name("hotels/editHotel"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateHotelFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/hotels/{hotelId}/edit", HotelControllerTests.TEST_HOTEL_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "Clinica").param("count", "1").param("location", ""))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.model().attributeHasErrors("hotel")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("hotel", "location"))
			.andExpect(MockMvcResultMatchers.view().name("hotels/editHotel"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowHotel() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/hotels/{hotelId}", HotelControllerTests.TEST_HOTEL_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("hotel", Matchers.hasProperty("name", Matchers.is("Externa")))).andExpect(MockMvcResultMatchers.model().attribute("hotel", Matchers.hasProperty("capacity", Matchers.is(10))))
			.andExpect(MockMvcResultMatchers.model().attribute("hotel", Matchers.hasProperty("count", Matchers.is(0)))).andExpect(MockMvcResultMatchers.model().attribute("hotel", Matchers.hasProperty("location", Matchers.is("Sevilla"))))
			.andExpect(MockMvcResultMatchers.view().name("hotels/editHotel"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessDeleteSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/hotels/delete/{hotelId}", HotelControllerTests.TEST_HOTEL_ID)).andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.view().name("hotels/listadoHoteles"));
	}
}
