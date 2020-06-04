
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "pet_history")
public class PetHistory extends BaseEntity {

	@Column(name = "summary")
	@NotEmpty
	private String		summary;

	@Column(name = "details")
	@NotEmpty
	private String		details;

	@Column(name = "date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate	date;

	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet			pets;


	public Pet getPets() {
		return this.pets;
	}

	public void setPets(final Pet pets) {
		this.pets = pets;
	}

	public LocalDate getDate() {
		return this.date;
	}

	public void setDate(final LocalDate date) {
		this.date = date;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}

	public String getDetails() {
		return this.details;
	}

	public void setDetails(final String details) {
		this.details = details;
	}

}
