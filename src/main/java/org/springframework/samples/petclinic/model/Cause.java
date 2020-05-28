
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
public class Cause extends NamedEntity {

	@NotEmpty
	@Column(name = "name")
	private String			name;

	@NotEmpty
	@Column(name = "description")
	private String			description;

	@NotEmpty
	@Column(name = "organisation")
	private String			organisation;

	@NotNull
	@Column(name = "budget_target")
	@Range(min = 0)
	private Double			budgetTarget;

	@NotNull
	@Column(name = "budget_archivied")
	@Range(min = 0)
	private Double			budgetArchivied;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cause", fetch = FetchType.EAGER)
	private Set<Donation>	donations;


	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getOrganisation() {
		return this.organisation;
	}

	public void setOrganisation(final String organisation) {
		this.organisation = organisation;
	}

	public Double getBudgetTarget() {
		return this.budgetTarget;
	}

	public void setBudgetTarget(final Double budgetTarget) {
		this.budgetTarget = budgetTarget;
	}

	public Double getBudgetArchivied() {
		return this.budgetArchivied;
	}

	public void setBudgetArchivied(final Double budgetArchivied) {
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

	public void setDonations(final Set<Donation> donations) {
		this.donations = donations;
	}

}
