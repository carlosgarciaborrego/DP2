package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.repository.CauseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CauseService {

	@Autowired
	private CauseRepository causeRepository;


	@Transactional
	public int causeCount() {
		return (int) this.causeRepository.count();
	}

	@Transactional
	public Iterable<Cause> findAll() {
		return this.causeRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<Cause> findCauseById(final int id) {
		return this.causeRepository.findById(id);
	}

	@Transactional
	public void save(final Cause hotel) {
		this.causeRepository.save(hotel);
	}

	@Transactional
	public void delete(final Cause hotel) {
		this.causeRepository.delete(hotel);
	}
}
