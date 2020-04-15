
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.HotelRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HotelService {

	//	@Autowired
	//	private HotelRepository hotelRepository;

	private HotelRepository	hotelRepository;
	private VisitRepository	visitRepository;


	@Autowired
	public HotelService(final HotelRepository hotelRepository, final VisitRepository visitRepository) {
		this.hotelRepository = hotelRepository;
		this.visitRepository = visitRepository;
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

	public Collection<Visit> findVisitsByHotelId(final int hotelId) {
		return this.visitRepository.findByHotelId(hotelId);
	}

	@Transactional
	public void deleteVisit(final Visit visit) {
		this.visitRepository.delete(visit);
	}

	@Transactional(readOnly = true)
	public Visit findVisitById(final int id) {
		return this.visitRepository.findVisitById(id);
	}

	@Transactional
	public void saveVisit(final Visit visit) {
		this.visitRepository.save(visit);
	}
}
