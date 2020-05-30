
package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetHistory;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PetHistoryServiceTests {

	@Autowired
	protected PetHistoryService	petHistoryService;

	@Autowired
	protected PetService		petService;


	@Test
	void shouldFindAllPetHistories() {
		Collection<PetHistory> hotels = this.petHistoryService.findPetHistories();
		Collection<PetHistory> nuevaLista = new ArrayList<PetHistory>();

		for (PetHistory h : hotels) {
			nuevaLista.add(h);
		}
		PetHistory hotel1 = EntityUtils.getById(nuevaLista, PetHistory.class, 1);
		Assertions.assertThat(hotel1.getDate().toString()).isEqualTo("2010-09-07");
		Assertions.assertThat(hotel1.getDetails()).isEqualTo("details");
		Assertions.assertThat(hotel1.getSummary()).isEqualTo("summary");
		Assertions.assertThat(hotel1.getPets().getId()).isEqualTo(1);
	}

	@Test
	void shouldFindPetHistoryWithCorrectId() {
		Optional<PetHistory> hotel1 = this.petHistoryService.findPetHistoryById(1);

		Assertions.assertThat(hotel1.isPresent());
		Assertions.assertThat(hotel1.get().getDate().toString()).isEqualTo("2010-09-07");
		Assertions.assertThat(hotel1.get().getDetails()).isEqualTo("details");
		Assertions.assertThat(hotel1.get().getSummary()).isEqualTo("summary");
		Assertions.assertThat(hotel1.get().getPets().getId()).isEqualTo(1);
	}

	@Test
	void shouldInsertPetHistory() {
		PetHistory history = new PetHistory();
		history.setDate(LocalDate.now());
		history.setDetails("Esto son unos detalles");
		history.setSummary("Esto es un resumen");
		Pet p = this.petService.findPetById(1);
		history.setPets(p);

		this.petHistoryService.savePetHistory(history);
		Assertions.assertThat(history.getId().longValue()).isEqualTo(4);
	}

	@Test
	void shouldUpdatePetHistory() {
		Iterable<PetHistory> hotels = this.petHistoryService.findPetHistories();
		Collection<PetHistory> nuevaLista = new ArrayList<PetHistory>();

		for (PetHistory h : hotels) {
			nuevaLista.add(h);
		}

		PetHistory hotel1 = EntityUtils.getById(nuevaLista, PetHistory.class, 1);
		PetHistory hotel2 = EntityUtils.getById(nuevaLista, PetHistory.class, 1);
		hotel1.setDate(LocalDate.now());
		hotel1.setDetails("Esto son unos detalles");
		hotel1.setSummary("Esto es un resumen");

		this.petHistoryService.savePetHistory(hotel1);

		Assertions.assertThat(hotel2.getDate()).isEqualTo(hotel1.getDate());
		Assertions.assertThat(hotel2.getDetails()).isEqualTo(hotel1.getDetails());
		Assertions.assertThat(hotel2.getSummary()).isEqualTo(hotel1.getSummary());
	}

	@Test
	void shouldDeletePetHistory() {
		Iterable<PetHistory> hotels = this.petHistoryService.findPetHistories();
		Collection<PetHistory> nuevaLista = new ArrayList<PetHistory>();

		for (PetHistory h : hotels) {
			nuevaLista.add(h);
		}

		PetHistory hotel1 = EntityUtils.getById(nuevaLista, PetHistory.class, 3);

		this.petHistoryService.delete(hotel1.getId());

		Iterable<PetHistory> hotels2 = this.petHistoryService.findPetHistories();
		Collection<PetHistory> nuevaLista2 = new ArrayList<PetHistory>();

		for (PetHistory h : hotels2) {
			nuevaLista2.add(h);
		}

		Assertions.assertThat(nuevaLista2.size()).isEqualTo(2);
	}

}
