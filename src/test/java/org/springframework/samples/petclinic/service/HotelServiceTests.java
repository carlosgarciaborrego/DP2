package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class HotelServiceTests {

	@Autowired
	protected HotelService hotelService;


	// Este test falla para h2 pero no para mysql con el mismo data (espera 5)
	@Test
	void testCountWithInitialData() {
		int count = this.hotelService.hotelCount();
		Assertions.assertEquals(count, 6);
	}

	@Test
	void shouldFindAllHotels() {
		Iterable<Hotel> hotels = this.hotelService.findAll();
		Collection<Hotel> nuevaLista = new ArrayList<Hotel>();

		for (Hotel h : hotels) {
			nuevaLista.add(h);
		}

		Hotel hotel1 = EntityUtils.getById(nuevaLista, Hotel.class, 1);
		assertThat(hotel1.getName()).isEqualTo("Calle Cadiz");
		assertThat(hotel1.getCapacity()).isEqualTo(10);
		assertThat(hotel1.getCount()).isEqualTo(0);
		assertThat(hotel1.getLocation()).isEqualTo("Sevilla");
	}
	
	@Test
	void shouldFindHotelWithCorrectId(){
		Hotel hotel1 = this.hotelService.findHotelById(1); 
		assertThat(hotel1.getName()).isEqualTo("Calle Cadiz");
		assertThat(hotel1.getLocation()).isEqualTo("Sevilla");
		assertThat(hotel1.getCount()).isBetween(0, hotel1.getCapacity());
		assertThat(hotel1.getCapacity()).isEqualTo(10);
	}

	@Test
	void shouldInsertHotel() {
		Hotel hotel = new Hotel();
		hotel.setName("Calle Betis");
		hotel.setCapacity(8);
		hotel.setCount(3);
		hotel.setLocation("Estepa");
		
		this.hotelService.save(hotel);
		assertThat(hotel.getId().longValue()).isEqualTo(6);
	}
	
	@Test
	void shouldUpdateHotel() {
		Iterable<Hotel> hotels = this.hotelService.findAll();
		Collection<Hotel> nuevaLista = new ArrayList<Hotel>();

		for (Hotel h : hotels) {
			nuevaLista.add(h);
		}
		
		
		Hotel hotel1 = EntityUtils.getById(nuevaLista, Hotel.class, 1);
		Hotel hotel2 = EntityUtils.getById(nuevaLista, Hotel.class, 1);
		String street = "Calle Roya";
		String newLocation = "Herrera";
		Integer newCapacity = 5;
		hotel1.setName(street);
		hotel1.setLocation(newLocation);
		hotel1.setCapacity(newCapacity);
		
		this.hotelService.save(hotel1);
		
		assertThat(hotel2.getLocation()).isEqualTo(hotel1.getLocation());
		assertThat(hotel2.getCapacity()).isEqualTo(hotel1.getCapacity());
	}
	
	@Test
	void shouldDeleteHotel() {
		Hotel hotel1 = this.hotelService.findHotelById(1); 
		Collection<Hotel> nuevaLista = new ArrayList<Hotel>();
		
		nuevaLista.add(hotel1);
		
		this.hotelService.delete(hotel1);
		assertThat(nuevaLista.isEmpty());

	}
	
}