
package org.springframework.samples.petclinic.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

@Entity
@Table(name = "clinic")
public class Clinic extends NamedEntity {

	@Column(name = "name")
	@NotEmpty
	private String				name;

	@Column(name = "location")
	@NotEmpty
	private String				location;

	@Column(name = "telephone")
	@NotEmpty
	@Digits(fraction = 0, integer = 10)
	private String				telephone;

	@Column(name = "emergency")
	@NotEmpty
	@Digits(fraction = 0, integer = 10)
	private String				emergency;

	@Column(name = "capacity")
	@Range(min = 0)
	@NotNull
	private Integer				capacity;

	@Column(name = "email")
	@NotEmpty
	private String				email;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "clinic")
	private Set<Reservation>	reservations;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "clinic")
	private Set<Provider>		providers;


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

	protected Set<Reservation> getReservationsInternal() {
		if (this.reservations == null) {
			this.reservations = new HashSet<>();
		}
		return this.reservations;
	}

	protected void setReservationsInternal(final Set<Reservation> reservations) {
		this.reservations = reservations;
	}

	public List<Reservation> getReservations() {
		List<Reservation> sortedReservations = new ArrayList<>(this.getReservationsInternal());
		PropertyComparator.sort(sortedReservations, new MutableSortDefinition("date", false, false));
		return Collections.unmodifiableList(sortedReservations);
	}

	public void addReservation(final Reservation reservation) {
		this.getReservationsInternal().add(reservation);
		reservation.setClinic(this);
	}

	public void removeReservation(final Reservation reservation) {
		this.getReservationsInternal().remove(reservation);
		reservation.setClinic(this);
	}

	public void setReservations(final Set<Reservation> reservations) {
		this.reservations = reservations;
	}

	protected Set<Provider> getProvidersInternal() {
		if (this.providers == null) {
			this.providers = new HashSet<>();
		}
		return this.providers;
	}

	protected void setProvidersInternal(final Set<Provider> providers) {
		this.providers = providers;
	}

	public List<Provider> getProviders() {
		List<Provider> sortedProviders = new ArrayList<>(this.getProvidersInternal());
		PropertyComparator.sort(sortedProviders, new MutableSortDefinition("date", false, false));
		return Collections.unmodifiableList(sortedProviders);
	}

	public void addProvider(final Provider provider) {
		this.getProvidersInternal().add(provider);
		provider.setClinic(this);
	}

	public void removeProvider(final Provider provider) {
		this.getProvidersInternal().remove(provider);
		provider.setClinic(this);
	}

	public void setProviders(final Set<Provider> providers) {
		this.providers = providers;
	}

	@Override
	public String toString() {
		return "Clinic " + this.name;
	}

}
