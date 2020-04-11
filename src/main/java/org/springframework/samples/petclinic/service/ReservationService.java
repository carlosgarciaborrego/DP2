
package org.springframework.samples.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Reservation;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationService {

	private ReservationRepository	reservationRepository;
	private OwnerRepository			ownerRepository;


	@Autowired
	public ReservationService(final ReservationRepository reservationRepository, final OwnerRepository ownerRepository) {
		this.reservationRepository = reservationRepository;
		this.ownerRepository = ownerRepository;
	}

	@Transactional
	public int reservationCount() {
		return (int) this.reservationRepository.count();
	}

	@Transactional
	public Iterable<Reservation> findAll() {
		return this.reservationRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Reservation findReservationById(final int id) {
		return this.reservationRepository.findReservationById(id);
	}

	@Transactional
	public void save(final Reservation reservation) {
		this.reservationRepository.save(reservation);
	}

	@Transactional
	public void delete(final Reservation reservation) {
		this.reservationRepository.delete(reservation);
	}

	@Transactional
	public Clinic findClinicById(final int id) {
		return this.reservationRepository.findClinicByClinicId(id);
	}
	@Transactional
	public Owner findOwnerById(final int id) {
		return this.reservationRepository.findOwnerByOwnerId(id);
	}

	public List<Owner> findOwnerByUserId(final String username) {
		List<Owner> optional = this.reservationRepository.findOwnerByUserId(username);
		return optional;
	}
}
