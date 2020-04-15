
package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Reservation;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.service.ReservationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

	@Autowired
	private ReservationService	reservationService;

	private ClinicService		clinicService;


	public ReservationController(final ReservationService reservationService, final ClinicService clinicService) {
		this.reservationService = reservationService;
		this.clinicService = clinicService;
	}

	@ModelAttribute("clinics")
	public Collection<Clinic> populatePetTypes() {
		return this.clinicService.findAll();
	}

	@GetMapping()
	public String listadoCitas(final ModelMap modelMap) {
		String vista = "reservations/listadoCitas";
		Iterable<Reservation> reservations = this.reservationService.findAll();
		modelMap.addAttribute("reservation", reservations);
		return vista;
	}

	@GetMapping(path = "/accepted/{reservationId}")
	public String aceptarCita(final ModelMap model, @PathVariable("reservationId") final int reservationId) {
		Reservation reservation = this.reservationService.findReservationById(reservationId);

		reservation.setStatus("accepted");
		this.reservationService.save(reservation);
		return "redirect:/reservations";
	}

	@GetMapping(path = "/rejected/{reservationId}")
	public String rechazarCita(final ModelMap model, @PathVariable("reservationId") final int reservationId) {
		Reservation reservation = this.reservationService.findReservationById(reservationId);

		reservation.setStatus("rejected");
		this.reservationService.save(reservation);
		return "redirect:/reservations";
	}

	@GetMapping(path = "/new")
	public String crearCita(final ModelMap modelMap) {
		String view = "reservations/editCita";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentPrincipalName = (User) authentication.getPrincipal();
		Owner owner = this.reservationService.findOwnerByUserId(currentPrincipalName.getUsername()).get(0);
		Reservation res = new Reservation();
		res.setOwner(owner);
		res.setStatus("pending");
		modelMap.addAttribute("reservation", res);

		return view;
	}

	@PostMapping(path = "/save")
	public String salvarCita(@Valid final Reservation reservation, final BindingResult result, final ModelMap modelMap) {
		String view = "reservations/listadoCitas";

		if (result.hasErrors()) {
			modelMap.addAttribute("reservation", reservation);
			return "reservations/editCita";
		} else {
			this.reservationService.save(reservation);
			modelMap.addAttribute("message", "Reservation successfully saved!");
			view = "redirect:/reservations";
		}
		return view;
	}

	@GetMapping(path = "/delete/{reservationId}")
	public String borrarCita(@PathVariable("reservationId") final int reservationId, final ModelMap modelMap) {
		String view = "reservations/listadoCitas";
		Reservation reservation = this.reservationService.findReservationById(reservationId);
		this.reservationService.delete(reservation);
		modelMap.addAttribute("message", "Reservation successfully deleted!");
		view = this.listadoCitas(modelMap);
		return view;
	}

	@GetMapping(path = "/{reservationId}/edit")
	public String actualizarCita(@PathVariable("reservationId") final int reservationId, final ModelMap modelMap) {
		String view = "reservations/editCita";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentPrincipalName = (User) authentication.getPrincipal();
		Owner owner = this.reservationService.findOwnerByUserId(currentPrincipalName.getUsername()).get(0);
		Reservation reservation = this.reservationService.findReservationById(reservationId);
		reservation.setOwner(owner);
		modelMap.addAttribute(reservation);
		return view;
	}

	@PostMapping(value = "/{reservationId}/edit")
	public String actualizarCitaPost(@Valid final Reservation reservation, final BindingResult result, @PathVariable("reservationId") final int reservationId) {
		String view = "reservations/editCita";
		if (result.hasErrors()) {
			return view;
		} else {
			reservation.setId(reservationId);
			this.reservationService.save(reservation);
			return "redirect:/reservations/{reservationId}";
		}
	}

	@GetMapping(value = "/{reservationId}")
	public String showCita(@PathVariable("reservationId") final Integer reservationId, final Map<String, Object> model) {
		Reservation reservation = this.reservationService.findReservationById(reservationId);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentPrincipalName = (User) authentication.getPrincipal();
		Owner owner = this.reservationService.findOwnerByUserId(currentPrincipalName.getUsername()).get(0);
		reservation.setOwner(owner);
		model.put("reservation", reservation);
		return "reservations/editCita";
	}

	@PostMapping(value = "/{reservationId}")
	public String showCita(@Valid final Reservation reservation, final BindingResult result, final Map<String, Object> model) {
		if (result.hasErrors()) {
			return "reservations/editCita";
		} else {
			this.reservationService.save(reservation);
			;
			return "redirect:/show/{reservationId}";
		}
	}

}
