
package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "providers")
public class Provider extends BaseEntity {

	@Column(name = "name")
	@NotEmpty
	private String	name;

	@Column(name = "city")
	@NotEmpty
	private String	city;

	@Column(name = "telephone")
	@NotEmpty
	@Digits(fraction = 0, integer = 10)
	private String	telephone;

	@Column(name = "description")
	@NotEmpty
	private String	description;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "clinic_id")
	private Clinic	clinic;


	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(final String telephone) {
		this.telephone = telephone;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Clinic getClinic() {
		return this.clinic;
	}

	public void setClinic(final Clinic clinic) {
		this.clinic = clinic;
	}
}
