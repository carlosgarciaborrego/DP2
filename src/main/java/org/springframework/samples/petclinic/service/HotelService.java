
package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.repository.HotelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HotelService {

	//	@Autowired
	//	private HotelRepository hotelRepository;

	private HotelRepository hotelRepository;


	@Autowired
	public HotelService(final HotelRepository hotelRepository) {
		this.hotelRepository = hotelRepository;
	}

	@Transactional
	public int hotelCount() {
		return (int) this.hotelRepository.count();
	}

	@Transactional
	public Iterable<Hotel> findAll() {
		return this.hotelRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Hotel findHotelById(final int id) {
		return this.hotelRepository.findHotelById(id);
	}

	@Transactional
	public void save(final Hotel hotel) {
		this.hotelRepository.save(hotel);
	}

	@Transactional
	public void delete(final Hotel hotel) {
		this.hotelRepository.delete(hotel);
	}

}
