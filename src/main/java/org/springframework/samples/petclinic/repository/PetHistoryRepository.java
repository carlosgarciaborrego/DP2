
package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.model.PetHistory;

public interface PetHistoryRepository extends CrudRepository<PetHistory, Integer> {

	/**
	 * Retrieve all <code>Vet</code>s from the data store.
	 *
	 * @return a <code>Collection</code> of <code>Vet</code>s
	 */
	@Override
	Collection<PetHistory> findAll() throws DataAccessException;

	/**
	 * Save an <code>Owner</code> to the data store, either inserting or updating it.
	 *
	 * @param vet
	 *            the <code>Vet</code> to save
	 * @return
	 * @see BaseEntity#isNew
	 */
	@SuppressWarnings("unchecked")
	@Override
	PetHistory save(PetHistory petHistory) throws DataAccessException;

	@Query("SELECT p FROM PetHistory p where p.pets.id =:id")
	List<PetHistory> findByPetId(@Param("id") int id) throws DataAccessException;

}
