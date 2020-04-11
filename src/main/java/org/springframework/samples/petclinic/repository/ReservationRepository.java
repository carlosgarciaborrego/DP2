
package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Reservation;

public interface ReservationRepository extends CrudRepository<Reservation, Integer> {

	Reservation findReservationById(int id) throws DataAccessException;

	Clinic findClinicByClinicId(int id) throws DataAccessException;

	Owner findOwnerByOwnerId(int id) throws DataAccessException;

	@Query("SELECT p FROM Owner p where p.user.username =:username")
	List<Owner> findOwnerByUserId(String username);

}
