package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.repository.CauseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CauseService {

	private CauseRepository causeRepository;

	@Autowired
	public CauseService(final CauseRepository causeRepository) {
		this.causeRepository = causeRepository;
	}

	@Transactional
	public int causeCount() {	
		return (int) this.causeRepository.count();
	}

	@Transactional
	public Iterable<Cause> findAll() {
		return this.causeRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Cause findCauseById(final int id) {
		return this.causeRepository.findCauseById(id);
	}

	@Transactional
	public void save(final Cause cause) {
		this.causeRepository.save(cause);
	}

	@Transactional
	public void delete(final Cause cause) {
		this.causeRepository.delete(cause);
	}
}
