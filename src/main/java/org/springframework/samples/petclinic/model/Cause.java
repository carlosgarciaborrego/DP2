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

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cause", fetch = FetchType.EAGER)
	private Set<Donation>	donations;
	
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

	protected Set<Donation> getDonationsInternal() {
		if (this.donations == null) {
			this.donations = new HashSet<>();
		}
		return this.donations;
	}
	
	public List<Donation> getDonations() {
		List<Donation> sortedDonations = new ArrayList<>(this.getDonationsInternal());
		PropertyComparator.sort(sortedDonations, new MutableSortDefinition("date", false, false));
		return Collections.unmodifiableList(sortedDonations);
	}

	public void addVisit(final Donation donation) {
		this.getDonationsInternal().add(donation);
		donation.setCause(this);
	}
	
	public void setDonations(Set<Donation> donations) {
		this.donations = donations;
	}
	
	
	
}
