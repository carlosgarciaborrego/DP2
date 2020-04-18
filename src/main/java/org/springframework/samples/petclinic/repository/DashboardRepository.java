
package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Owner;

public interface DashboardRepository extends Repository<Owner, Integer> {

	@Query("SELECT (Select Count(p) FROM Vet p where p.clinic.id = g.id) from Clinic g group by g.name order by g.name")
	List<Integer> numVetsByClinics() throws DataAccessException;

	@Query("SELECT p.name FROM Clinic p group by p.name order by p.name")
	List<String> vetsByClinics() throws DataAccessException;

	@Query("SELECT p.capacity - p.count FROM Hotel p group by p.name order by p.name")
	List<Integer> numPlazasByHotel() throws DataAccessException;

	@Query("SELECT p.name FROM Hotel p group by p.name order by p.name")
	List<String> plazasByHotel() throws DataAccessException;

	@Query("SELECT (Select Count(p) FROM Pet p where p.vet.id = g.id) from Vet g group by g.firstName order by g.firstName")
	List<Integer> numPetsByVet() throws DataAccessException;

	@Query("SELECT g.firstName from Vet g group by g.firstName order by g.firstName")
	List<String> petsByVets() throws DataAccessException;

	@Query("SELECT SUM(p.amount) FROM Cause g left join Donation p ON g.id = p.cause.id group by g.name order by g.name")
	List<Double> numDonationAmoundByCause() throws DataAccessException;

	@Query("SELECT p.name FROM Cause p group by p.name order by p.name")
	List<String> donationAmoundByCause() throws DataAccessException;

}
