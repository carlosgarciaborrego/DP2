
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "reservations")
public class Reservation extends BaseEntity {

	@Column(name = "telephone")
	@NotEmpty
	@Digits(fraction = 0, integer = 10)
	private String		telephone;

	@Column(name = "reservation_date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate	reservationDate;

	@NotNull
	private String		status;

	private String		responseClient;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner		owner;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "clinic_id")
	private Clinic		clinic;


	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(final String telephone) {
		this.telephone = telephone;
	}

	public LocalDate getReservationDate() {
		return this.reservationDate;
	}

	public void setReservationDate(final LocalDate reservationDate) {
		this.reservationDate = reservationDate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getResponseClient() {
		return this.responseClient;
	}

	public void setResponseClient(final String responseClient) {
		this.responseClient = responseClient;
	}

	public Owner getOwner() {
		return this.owner;
	}

	public void setOwner(final Owner owner) {
		this.owner = owner;
	}

	public Clinic getClinic() {
		return this.clinic;
	}

	public void setClinic(final Clinic clinic) {
		this.clinic = clinic;
	}

}
