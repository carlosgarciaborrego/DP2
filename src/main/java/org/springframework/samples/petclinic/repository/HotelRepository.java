
package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Hotel;

public interface HotelRepository extends CrudRepository<Hotel, Integer> {

	Hotel findHotelById(int id) throws DataAccessException;

}
