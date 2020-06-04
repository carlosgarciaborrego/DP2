
package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.PetHistory;
import org.springframework.samples.petclinic.repository.PetHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PetHistoryService {

	private PetHistoryRepository petHistoryRepository;


	@Autowired
	public PetHistoryService(PetHistoryRepository petHistoryRepository) {
		this.petHistoryRepository = petHistoryRepository;
	}

	@Transactional(readOnly = true)
	public Collection<PetHistory> findPetHistories() throws DataAccessException {
		return this.petHistoryRepository.findAll();
	}

	@Transactional
	public void savePetHistory(final PetHistory petHistory) throws DataAccessException {
		this.petHistoryRepository.save(petHistory);
	}

	public void delete(final int petHistory) {

		this.petHistoryRepository.deleteById(petHistory);

	}

	public Optional<PetHistory> findVetById(final int vetId) {
		Optional<PetHistory> optional = this.petHistoryRepository.findById(vetId);
		return optional;
	}

	@Transactional(readOnly = true)
	public List<PetHistory> findPetHistoryByPetId(final int id) throws DataAccessException {
		return this.petHistoryRepository.findByPetId(id);
	}

	public Optional<PetHistory> findPetHistoryById(final Integer petHistoryId) {
		// TODO Auto-generated method stub
		return this.petHistoryRepository.findById(petHistoryId);
	}
}
