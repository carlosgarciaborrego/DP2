
package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.service.HotelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hotels")
public class HotelController {

	@Autowired
	private HotelService hotelService;


	@GetMapping()
	public String listadoHoteles(final ModelMap modelMap) {
		String vista = "hotels/listadoHoteles";
		Iterable<Hotel> hotels = this.hotelService.findAll();
		modelMap.addAttribute("hotel", hotels);
		return vista;
	}

	@GetMapping(path = "/new")
	public String crearHotel(final ModelMap modelMap) {
		String view = "hotels/editHotel";
		modelMap.addAttribute("hotel", new Hotel());
		return view;
	}

	@PostMapping(path = "/save")
	public String salvarHotel(@Valid final Hotel hotel, final BindingResult result, final ModelMap modelMap) {
		String view = "hotels/listadoHoteles";
		if (result.hasErrors()) {
			modelMap.addAttribute("hotel", hotel);
			return "hotels/editHotel";
		} else {
			this.hotelService.save(hotel);
			modelMap.addAttribute("message", "Hotel successfully saved!");
			view = this.listadoHoteles(modelMap);
		}
		return view;
	}

	@GetMapping(path = "/delete/{hotelId}")
	public String borrarHotel(@PathVariable("hotelId") final int hotelId, final ModelMap modelMap) {
		String view = "hotels/listadoHoteles";
		Optional<Hotel> hotel = this.hotelService.findHotelById(hotelId);
		if (hotel.isPresent()) {
			this.hotelService.delete(hotel.get());
			modelMap.addAttribute("message", "Hotel successfully deleted!");
			view = this.listadoHoteles(modelMap);
		} else {
			modelMap.addAttribute("message", "Hotel not found!");
			view = this.listadoHoteles(modelMap);
		}
		return view;
	}

	@GetMapping(path = "/hotels/{hotelsId}/edit")
	public String actualizarHotel(@PathVariable("hotelId") final int hotelId, final ModelMap modelMap) {
		String view = "hotels/editHotel";
		Optional<Hotel> hotel = this.hotelService.findHotelById(hotelId);
		modelMap.addAttribute(hotel);
		return view;
	}

	@PostMapping(value = "/hotels/{hotelId}/edit")
	public String actualizarHotelPost(@Valid final Hotel hotel, final BindingResult result, @PathVariable("hoteId") final int hotelId) {
		String view = "hotels/editHotel";
		if (result.hasErrors()) {
			return view;
		} else {
			hotel.setId(hotelId);
			this.hotelService.save(hotel);
			return "redirect:/hotels/{hotelId}";
		}
	}

}
