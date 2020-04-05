
package org.springframework.samples.petclinic.web;

import java.util.Map;

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
		Hotel hotel = this.hotelService.findHotelById(hotelId);
		this.hotelService.delete(hotel);
		modelMap.addAttribute("message", "Hotel successfully deleted!");
		view = this.listadoHoteles(modelMap);
		return view;
	}

	@GetMapping(path = "/{hotelId}/edit")
	public String actualizarHotel(@PathVariable("hotelId") final int hotelId, final ModelMap modelMap) {
		String view = "hotels/editHotel";
		Hotel hotel = this.hotelService.findHotelById(hotelId);
		modelMap.addAttribute(hotel);
		return view;
	}

	@PostMapping(value = "/{hotelId}/edit")
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

	@GetMapping(value = "/{hotelId}")
	public String showHotel(@PathVariable("hotelId") final Integer hotelId, final Map<String, Object> model) {
		Hotel hotel = this.hotelService.findHotelById(hotelId);
		model.put("hotel", hotel);
		return "hotels/editHotel";
	}

	@PostMapping(value = "/{hotelId}")
	public String showHotel(@Valid final Hotel hotel, final BindingResult result, final Map<String, Object> model) {
		if (result.hasErrors()) {
			return "hotels/editHotel";
		} else {
			this.hotelService.save(hotel);
			;
			return "redirect:/show/{hotelId}";
		}
	}

}
