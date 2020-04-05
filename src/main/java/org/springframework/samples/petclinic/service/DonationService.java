package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.repository.DonationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DonationService {

	@Autowired
	private DonationRepository donationRepository;
	
	@Transactional(readOnly = true)
	public List<Donation> findByIdCause(Cause id) {
		return this.donationRepository.findByCause(id);
	}
	
	@Transactional
	public int donationCount() {
		return (int) this.donationRepository.count();
	}

	@Transactional
	public Iterable<Donation> findAll() {
		return this.donationRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<Donation> findCauseById(final int id) {
		return this.donationRepository.findById(id);
	}

	@Transactional
	public void save(final Donation donation) {
		this.donationRepository.save(donation);
	}

	@Transactional
	public void delete(final Donation donation) {
		this.donationRepository.delete(donation);
	}
}