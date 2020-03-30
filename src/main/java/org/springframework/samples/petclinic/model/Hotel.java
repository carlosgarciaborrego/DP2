
package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "hotels")
public class Hotel extends NamedEntity {

	@Column(name = "name")
	private String	name;

	@Column(name = "location")
	@NotEmpty
	private String	location;

	@Column(name = "count")
	@Range(min = 0)
	private Integer	count;

	@Column(name = "capacity")
	@Range(min = 0)
	private Integer	capacity;

	//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "hotel")
	//	private Set<Pet>	pets;


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
		return this.count;
	}

	public void setCount(final Integer count) {
		this.count = count;
	}

	public Integer getCapacity() {
		return this.capacity;
	}

	public void setCapacity(final Integer capacity) {
		this.capacity = capacity;
	}

	//	public Set<Pet> getPets() {
	//		return this.pets;
	//	}
	//
	//	public void setPets(final Set<Pet> pets) {
	//		this.pets = pets;
	//	}

}
