
package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.repository.ClinicRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClinicService {

	private ClinicRepository clinicRepository;


	@Autowired
	public ClinicService(final ClinicRepository clinicRepository) {
		this.clinicRepository = clinicRepository;
	}

	@Transactional(readOnly = true)
	public Collection<Clinic> findAll() throws DataAccessException {
		return this.clinicRepository.findAll();
	}

	@Transactional
	public void saveClinic(final Clinic petHistory) throws DataAccessException {
		this.clinicRepository.save(petHistory);
	}

	public void delete(final int petHistory) {

		this.clinicRepository.deleteById(petHistory);

	}

	public Optional<Clinic> findClinicById(final Integer id) {
		return this.clinicRepository.findById(id);
	}

}
