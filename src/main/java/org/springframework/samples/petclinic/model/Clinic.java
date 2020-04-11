
package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "clinic")
public class Clinic extends NamedEntity {

	@Column(name = "name")
	@NotEmpty
	private String	name;

	@Column(name = "location")
	@NotEmpty
	private String	location;

	@Column(name = "telephone")
	@NotEmpty
	@Digits(fraction = 0, integer = 10)
	private String	telephone;

	@Column(name = "emergency")
	@NotEmpty
	@Digits(fraction = 0, integer = 10)
	private String	emergency;

	@Column(name = "capacity")
	@Range(min = 0)
	@NotNull
	private Integer	capacity;

	@Column(name = "email")
	@NotEmpty
	private String	email;


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

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(final String telephone) {
		this.telephone = telephone;
	}

	public String getEmergency() {
		return this.emergency;
	}

	public void setEmergency(final String emergency) {
		this.emergency = emergency;
	}

	public Integer getCapacity() {
		return this.capacity;
	}

	public void setCapacity(final Integer capacity) {
		this.capacity = capacity;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Clinic " + this.name;
	}

}
