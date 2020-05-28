
package org.springframework.samples.petclinic.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

@Entity
@Table(name = "hotels")
public class Hotel extends NamedEntity {

	@Column(name = "name")
	@NotEmpty
	private String		name;

	@Column(name = "location")
	@NotEmpty
	private String		location;

	@Column(name = "count")
	@Range(min = 0)
	private Integer		count;

	@NotNull
	@Column(name = "capacity")
	@Range(min = 0)
	private Integer		capacity;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "hotel", fetch = FetchType.EAGER)
	private Set<Visit>	visits;


	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(final String name) {
		this.name = name;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(final String location) {
		this.location = location;
	}

	public Integer getCount() {
		return this.getVisits().size();
	}

	public Integer getCapacity() {
		return this.capacity;
	}

	public void setCapacity(final Integer capacity) {
		this.capacity = capacity;
	}

	protected Set<Visit> getVisitsInternal() {
		if (this.visits == null) {
			this.visits = new HashSet<>();
		}
		return this.visits;
	}

	protected void setVisitsInternal(final Set<Visit> visits) {
		this.visits = visits;
	}

	public List<Visit> getVisits() {
		List<Visit> sortedVisits = new ArrayList<>(this.getVisitsInternal());
		PropertyComparator.sort(sortedVisits, new MutableSortDefinition("date", false, false));
		return Collections.unmodifiableList(sortedVisits);
	}

	public void addVisit(final Visit visit) {
		this.getVisitsInternal().add(visit);
		visit.setHotel(this);
	}

	public void removeVisit(final Visit visit) {
		this.getVisitsInternal().remove(visit);
		visit.setHotel(this);
	}

	public void setVisits(final Set<Visit> visits) {
		this.visits = visits;
	}

}
