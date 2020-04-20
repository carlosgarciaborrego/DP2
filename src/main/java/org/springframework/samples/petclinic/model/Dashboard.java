
package org.springframework.samples.petclinic.model;

import java.util.List;

public class Dashboard {

	private List<Integer>	numVetsByClinics;
	private List<String>	vetsByClinics;
	private List<Integer>	numPlazasByHotel;
	private List<String>	plazasByHotel;
	private List<Integer>	numPetsByVet;
	private List<String>	petsByVets;
	private List<Double>	numDonationAmoundByCause;
	private List<String>	donationAmoundByCause;


	public List<Integer> getNumVetsByClinics() {
		return this.numVetsByClinics;
	}

	public void setNumVetsByClinics(final List<Integer> numVetsByClinics) {
		this.numVetsByClinics = numVetsByClinics;
	}

	public List<String> getVetsByClinics() {
		return this.vetsByClinics;
	}

	public void setVetsByClinics(final List<String> vetsByClinics) {
		this.vetsByClinics = vetsByClinics;
	}

	public List<Integer> getNumPlazasByHotel() {
		return this.numPlazasByHotel;
	}

	public void setNumPlazasByHotel(final List<Integer> numPlazasByHotel) {
		this.numPlazasByHotel = numPlazasByHotel;
	}

	public List<String> getPlazasByHotel() {
		return this.plazasByHotel;
	}

	public void setPlazasByHotel(final List<String> plazasByHotel) {
		this.plazasByHotel = plazasByHotel;
	}

	public List<Integer> getNumPetsByVet() {
		return this.numPetsByVet;
	}

	public void setNumPetsByVet(final List<Integer> numPetsByVet) {
		this.numPetsByVet = numPetsByVet;
	}

	public List<String> getPetsByVets() {
		return this.petsByVets;
	}

	public void setPetsByVets(final List<String> petsByVets) {
		this.petsByVets = petsByVets;
	}

	public List<Double> getNumDonationAmoundByCause() {
		return this.numDonationAmoundByCause;
	}

	public void setNumDonationAmoundByCause(final List<Double> numDonationAmoundByCause) {
		this.numDonationAmoundByCause = numDonationAmoundByCause;
	}

	public List<String> getDonationAmoundByCause() {
		return this.donationAmoundByCause;
	}

	public void setDonationAmoundByCause(final List<String> donationAmoundByCause) {
		this.donationAmoundByCause = donationAmoundByCause;
	}

}
