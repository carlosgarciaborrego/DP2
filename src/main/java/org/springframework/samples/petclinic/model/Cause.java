package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "cause")
public class Cause extends NamedEntity{

	@NotEmpty
	@Column(name = "name")
	private String name;

	@NotEmpty
	@Column(name = "description")
	private String description;

	@NotEmpty
	@Column(name = "organisation")
	private String organisation;
	
	@NotNull
	@Column(name = "budgetTarget")
	@Range(min = 0)
	private Double budgetTarget;
	
	@NotNull
	@Column(name = "budgetArchivied")
	@Range(min = 0)
	private Double budgetArchivied;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrganisation() {
		return organisation;
	}

	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}

	public Double getBudgetTarget() {
		return budgetTarget;
	}

	public void setBudgetTarget(Double budgetTarget) {
		this.budgetTarget = budgetTarget;
	}

	public Double getBudgetArchivied() {
		return budgetArchivied;
	}

	public void setBudgetArchivied(Double budgetArchivied) {
		this.budgetArchivied = budgetArchivied;
	}
	
	
}
