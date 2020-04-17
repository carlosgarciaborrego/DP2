package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Reservation;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ReservationServiceTests {

	@Autowired
	protected ReservationService reservationService;
	


	@Test
	void testCountWithInitialData() {
		int count = this.reservationService.reservationCount();
		Assertions.assertEquals(count, 1);
	}

		@Test
		void shouldFindAllReservations() {
			Iterable<Reservation> reservations = this.reservationService.findAll();
			Collection<Reservation> nuevaLista = new ArrayList<Reservation>();
	
			for (Reservation r : reservations) {
				nuevaLista.add(r);
			}
	
			Reservation reservation1 = EntityUtils.getById(nuevaLista, Reservation.class, 1);
			assertThat(reservation1.getTelephone()).isEqualTo("664455667");
			assertThat(reservation1.getReservationDate()).isEqualTo("2020-06-24");
			assertThat(reservation1.getStatus()).isEqualTo("pending");
			assertThat(reservation1.getResponseClient()).isEqualTo("adios");
			assertThat(reservation1.getResponseClinic()).isEqualTo("hola");	
		}

	
	@Test
	void shouldFindReservationWithCorrectId(){
		Reservation reservation1 = this.reservationService.findReservationById(1); 
		assertThat(reservation1.getTelephone()).isEqualTo("664455667");
		assertThat(reservation1.getReservationDate()).isEqualTo("2020-06-24");
		assertThat(reservation1.getStatus()).isEqualTo("pending");
		assertThat(reservation1.getResponseClient()).isEqualTo("adios");
		assertThat(reservation1.getResponseClinic()).isEqualTo("hola");
	}
	
	@Test
	void shouldInsertHotel() {
		Reservation reservation  = new Reservation();
		reservation.setTelephone("111222333");
		LocalDate actual = LocalDate.now();
		reservation.setReservationDate(actual);
		reservation.setStatus("accepted");
		reservation.setResponseClient("");
		reservation.setResponseClinic("");
		Owner owner = new Owner();
		owner.setId(1);;
		owner.setFirstName("George");
		owner.setLastName("Franklin");
		owner.setAddress("110 W. Liberty St.");
		owner.setCity("Madison");
		owner.setTelephone("666777888");
		reservation.setOwner(owner);
		Clinic cli = new Clinic();
		cli.setId(1);
		cli.setName("clinica1");
		cli.setEmail("cli1@gmail.com");
		cli.setEmergency("666777666");
		cli.setLocation("sevilla");
		cli.setTelephone("565656561");
		reservation.setClinic(cli);


		this.reservationService.save(reservation);
		assertThat(reservation.getId().longValue()).isEqualTo(2);
	}
	
	@Test
	void shouldUpdateReservation() {
		Iterable<Reservation> reservations = this.reservationService.findAll();
		Collection<Reservation> nuevaLista = new ArrayList<Reservation>();

		for (Reservation res : reservations) {
			nuevaLista.add(res);
		}
		
		Reservation res1 = EntityUtils.getById(nuevaLista, Reservation.class, 1);
		Reservation res2 = EntityUtils.getById(nuevaLista, Reservation.class, 1);

		String status = "accepted";
		String responseClient = "Podría solo esta semana";
		
		res1.setStatus(status);
		res1.setResponseClient(responseClient);
		

		this.reservationService.save(res1);
		
		
		assertThat(res2.getStatus()).isEqualTo(res1.getStatus());
		assertThat(res2.getResponseClient()).isEqualTo(res1.getResponseClient());
	}
	
	@Test
	void shouldDeleteReservation() {
		Iterable<Reservation> reservations = this.reservationService.findAll();
		Collection<Reservation> nuevaLista = new ArrayList<Reservation>();

		for (Reservation res : reservations) {
			nuevaLista.add(res);
		}
		
		Reservation res1 = EntityUtils.getById(nuevaLista, Reservation.class, 1);
		
		this.reservationService.delete(res1);
		assertThat(nuevaLista.isEmpty());
	}
	
}