package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "donations")
public class Donation extends NamedEntity{

	@NotNull
	@Column(name = "amount")
	@Range(min = 0)
	private Double amount;
	
	/**
	 * Holds value of property cause.
	 */
	@ManyToOne
	@JoinColumn(name = "cause_id")
	private Cause cause;

	
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Cause getCause() {
		return cause;
	}

	public void setCause(Cause cause) {
		this.cause = cause;
	}
	
	
	
}
