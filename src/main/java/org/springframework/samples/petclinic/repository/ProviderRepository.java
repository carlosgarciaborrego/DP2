
package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.model.Provider;

public interface ProviderRepository extends CrudRepository<Provider, Integer> {

	Provider findProviderById(int id) throws DataAccessException;

	Clinic findClinicByClinicId(int id) throws DataAccessException;

}
