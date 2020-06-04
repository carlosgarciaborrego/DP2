
package org.springframework.samples.petclinic.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Dashboard;
import org.springframework.samples.petclinic.repository.DashboardRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dash")
public class DashboardController {

	private DashboardRepository dashboardRepository;


	@Autowired
	public DashboardController(final DashboardRepository dashboardRepository) {
		this.dashboardRepository = dashboardRepository;
	}

	@GetMapping()
	public String obtenerDashboard(final ModelMap modelMap) {
		String vista = "dashboard/dashboard";
		Dashboard dash = new Dashboard();
		List<String> vetByClinic = this.dashboardRepository.vetsByClinics();
		List<Integer> numVetByClinic = this.dashboardRepository.numVetsByClinics();
		dash.setNumVetsByClinics(numVetByClinic);
		dash.setVetsByClinics(vetByClinic);

		List<String> plazasByHotel = this.dashboardRepository.plazasByHotel();
		List<Integer> numPlazasByHotel = this.dashboardRepository.numPlazasByHotel();
		dash.setPlazasByHotel(plazasByHotel);
		dash.setNumPlazasByHotel(numPlazasByHotel);

		List<String> donationAmoundByCause = this.dashboardRepository.donationAmoundByCause();
		List<Double> numDonationAmoundByCause = this.dashboardRepository.numDonationAmoundByCause();
		dash.setDonationAmoundByCause(donationAmoundByCause);
		dash.setNumDonationAmoundByCause(numDonationAmoundByCause);

		List<String> petsByVets = this.dashboardRepository.petsByVets();
		List<Integer> numPetsByVets = this.dashboardRepository.numPetsByVet();
		dash.setPetsByVets(petsByVets);
		dash.setNumPetsByVet(numPetsByVets);

		modelMap.addAttribute("dashboard", dash);
		return vista;
	}

}
