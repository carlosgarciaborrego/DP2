
package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetHistory;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
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
		if (hotel1.isPresent()) {

		}
		Assertions.assertThat(hotel1.isPresent());
		Assertions.assertThat(hotel1.get().getDate().toString()).isEqualTo("2010-09-07");
		Assertions.assertThat(hotel1.get().getDetails()).isEqualTo("details");
		Assertions.assertThat(hotel1.get().getSummary()).isEqualTo("summary");
		Assertions.assertThat(hotel1.get().getPets().getId()).isEqualTo(1);
	}

	@Test
	void shouldInsertHotel() {
		PetHistory history = new PetHistory();
		history.setDate(LocalDate.now());
		history.setDetails("Esto son unos detalles");
		history.setSummary("Esto es un resumen");
		Pet p = this.petService.findPetById(1);
		history.setPets(p);

		this.petHistoryService.savePetHistory(history);
		Assertions.assertThat(history.getId().longValue()).isEqualTo(2);
	}

	//	@Test
	//	void shouldUpdateHotel() {
	//		Iterable<Hotel> hotels = this.petHistoryService.findAll();
	//		Collection<Hotel> nuevaLista = new ArrayList<Hotel>();
	//
	//		for (Hotel h : hotels) {
	//			nuevaLista.add(h);
	//		}
	//
	//		Hotel hotel1 = EntityUtils.getById(nuevaLista, Hotel.class, 1);
	//		Hotel hotel2 = EntityUtils.getById(nuevaLista, Hotel.class, 1);
	//		String newLocation = "Herrera";
	//		Integer newCapacity = 5;
	//		Integer newCount = 0;
	//		hotel1.setLocation(newLocation);
	//		hotel1.setCapacity(newCapacity);
	//		hotel1.setCount(newCount);
	//
	//		this.petHistoryService.save(hotel1);
	//
	//		Assertions.assertThat(hotel2.getLocation()).isEqualTo(hotel1.getLocation());
	//		Assertions.assertThat(hotel2.getCapacity()).isEqualTo(hotel1.getCapacity());
	//		Assertions.assertThat(hotel2.getCount()).isEqualTo(hotel1.getCount());
	//	}
	//
	//	@Test
	//	void shouldDeleteHotel() {
	//		Iterable<Hotel> hotels = this.petHistoryService.findAll();
	//		Collection<Hotel> nuevaLista = new ArrayList<Hotel>();
	//
	//		for (Hotel h : hotels) {
	//			nuevaLista.add(h);
	//		}
	//
	//		Hotel hotel1 = EntityUtils.getById(nuevaLista, Hotel.class, 1);
	//
	//		this.petHistoryService.delete(hotel1);
	//		Assertions.assertThat(nuevaLista.isEmpty());
	//	}

}
