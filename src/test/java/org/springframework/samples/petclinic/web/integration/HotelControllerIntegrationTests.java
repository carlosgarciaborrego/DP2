
package org.springframework.samples.petclinic.web.integration;

import java.util.Collections;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.service.HotelService;
import org.springframework.samples.petclinic.web.HotelController;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HotelControllerIntegrationTests {

	private static final int	TEST_HOTEL_ID	= 1;

	@Autowired
	private HotelController		hotelcontroller;

	@Autowired
	private HotelService		hotelService;


	@Test
	void testInitCreationForm() throws Exception {
		ModelMap model = new ModelMap();
		String view = this.hotelcontroller.crearHotel(model);

		Assert.assertEquals(view, "hotels/editHotel");
		Assert.assertNotNull(model.get("hotel"));
	}

	@Test
	void testProcessCreationFormSuccess() throws Exception {
		ModelMap model = new ModelMap();
		Hotel newHotel = new Hotel();
		newHotel.setName("Calle Andalucia");
		newHotel.setLocation("Estepa");
		newHotel.setCapacity(8);
		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
		String view = this.hotelcontroller.salvarHotel(newHotel, bindingResult, model);
		Assert.assertEquals(view, "redirect:/hotels/");
	}

	@Test
	void testProcessCreationFormHasError() throws Exception {
		ModelMap model = new ModelMap();
		Hotel newHotel = new Hotel();
		newHotel.setName("Calle Andalucia");
		newHotel.setCapacity(8);
		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
		bindingResult.reject("location", "must not be null");
		String view = this.hotelcontroller.salvarHotel(newHotel, bindingResult, model);
		Assert.assertEquals(view, "hotels/editHotel");
	}

	@Test
	void testInitUpdateForm() throws Exception {
		ModelMap model = new ModelMap();

		String view = this.hotelcontroller.actualizarHotel(HotelControllerIntegrationTests.TEST_HOTEL_ID, model);
		Assert.assertEquals(view, "hotels/editHotel");
		Assert.assertNotNull(model.get("hotel"));
	}

	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		ModelMap model = new ModelMap();
		Hotel newHotel = new Hotel();
		newHotel.setName("Calle Granada");
		newHotel.setLocation("Jaén");
		newHotel.setCapacity(7);
		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
		String view = this.hotelcontroller.actualizarHotelPost(newHotel, bindingResult, HotelControllerIntegrationTests.TEST_HOTEL_ID, model);
		Assert.assertEquals(view, "redirect:/hotels/{hotelId}");
	}

	@Test
	void testProcessUpdateFormHasError() throws Exception {
		ModelMap model = new ModelMap();
		Hotel newHotel = new Hotel();
		newHotel.setName("Calle Granada");
		newHotel.setLocation("Jaén");
		newHotel.setCapacity(-1);
		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
		bindingResult.reject("capacity", "must be between 0 and 9223372036854775807");
		String view = this.hotelcontroller.salvarHotel(newHotel, bindingResult, model);
		Assert.assertEquals(view, "hotels/editHotel");
	}

	@Test
	void testDelete() throws Exception {
		ModelMap model = new ModelMap();
		String view = this.hotelcontroller.borrarHotel(HotelControllerIntegrationTests.TEST_HOTEL_ID, model);
		Assert.assertEquals(view, "hotels/listadoHoteles");
	}

	@Test
	void testShow() throws Exception {
		ModelMap model = new ModelMap();
		String view = this.hotelcontroller.showHotel(HotelControllerIntegrationTests.TEST_HOTEL_ID, model);
		Assert.assertEquals(view, "hotels/editHotel");
	}

}
