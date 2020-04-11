
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Clinic;

public interface ClinicRepository extends CrudRepository<Clinic, Integer> {

	@Override
	Collection<Clinic> findAll() throws DataAccessException;

}
