
package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Owner;

public interface DashboardRepository extends Repository<Owner, Integer> {

	@Query("SELECT Count(p) FROM Clinic p group by p.name order by p.name")
	List<Integer> numVetsByClinics() throws DataAccessException;

	@Query("SELECT p.name FROM Clinic p group by p.name order by p.name")
	List<String> vetsByClinics() throws DataAccessException;

}
