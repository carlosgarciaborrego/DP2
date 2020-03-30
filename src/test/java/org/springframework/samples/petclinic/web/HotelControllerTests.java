
package org.springframework.samples.petclinic.web;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
import org.springframework.test.web.servlet.MockMvc;

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
		this.hotel.setName("Externo");
		this.hotel.setCapacity(10);
		this.hotel.setCount(3);
		this.hotel.setLocation("Sevilla");

		Hotel res = this.hotelService.findHotelById(HotelControllerTests.TEST_HOTEL_ID).get();
		BDDMockito.given(res).willReturn(this.hotel);

	}
	
	@WithMockUser(value = "spring")
    @Test
    void testInitCreationForm() throws Exception {
	mockMvc.perform(get("/new")).andExpect(status().isOk()).andExpect(model().attributeExists("hotel"))
			.andExpect(view().name("hotels/editHotel"));
	}
	
	
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccess() throws Exception {
	mockMvc.perform(post("/save")
						.param("name", "Clinica")
						.param("capacity", "15")
						.with(csrf())
						.param("count", "1")
						.param("location", "Huelva"))
			.andExpect(status().is3xxRedirection());
	}

	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormHasErrors() throws Exception {
	mockMvc.perform(post("/save")
						.with(csrf())
						.param("name", "Clinica")
						.param("count", "1")
						.param("location", "Huelva"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("hotel"))
			.andExpect(model().attributeHasFieldErrors("hotel", "capacity"))
			.andExpect(view().name("hotels/editHotel"));
	}
	
    @WithMockUser(value = "spring")
    @Test
    void testProcessUpdateHotelFormSuccess() throws Exception {
	mockMvc.perform(post("/{hotelsId}/edit", TEST_HOTEL_ID)
						.with(csrf())
						.param("name", "Clinica")
						.param("capacity", "15")
						.param("count", "1")
						.param("location", "Cadiz"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/hotels/{hotelId}"));
    }

    @WithMockUser(value = "spring")
    @Test
    void testProcessUpdateHotelFormHasErrors() throws Exception {
	mockMvc.perform(post("/owners/{ownerId}/edit", TEST_HOTEL_ID)
						.with(csrf())
						.param("name", "Clinica")
						.param("count", "1")
						.param("location", "Cadiz"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("hotel"))
			.andExpect(model().attributeHasFieldErrors("hotel", "capacity"))
			.andExpect(view().name("hotels/editHotel"));
    }

    @WithMockUser(value = "spring")
    @Test
    void testShowOwner() throws Exception {
	mockMvc.perform(get("/{hotelId}", TEST_HOTEL_ID)).andExpect(status().isOk())
			.andExpect(model().attribute("hotel", hasProperty("name", is("Externo"))))
			.andExpect(model().attribute("hotel", hasProperty("capacity", is(10))))
			.andExpect(model().attribute("hotel", hasProperty("count", is(3))))
			.andExpect(model().attribute("hotel", hasProperty("location", is("Sevilla"))))
			.andExpect(view().name("redirect:/show/{hotelId}"));
    }
}
