
package org.springframework.samples.petclinic.model;

import java.util.List;

public class Dashboard {

	private List<Integer>	numVetsByClinics;
	private List<String>	vetsByClinics;


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

}
