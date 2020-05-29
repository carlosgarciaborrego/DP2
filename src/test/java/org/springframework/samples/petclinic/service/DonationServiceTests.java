
package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class DonationServiceTests {

	@Autowired
	protected DonationService donationService;


	@Test
	void shouldFindAllDonations() {
		Iterable<Donation> donations = this.donationService.findAll();
		Collection<Donation> nuevaLista = new ArrayList<Donation>();

		for (Donation h : donations) {
			nuevaLista.add(h);
		}
		Donation donation1 = EntityUtils.getById(nuevaLista, Donation.class, 1);

		Assertions.assertThat(donation1.getName().toString()).isEqualTo("Donacion Juan");
		Assertions.assertThat(donation1.getCause().getId()).isEqualTo(1);
		Assertions.assertThat(donation1.getAmount()).isEqualTo(100.0);
	}

	@Test
	void shouldFindDonationWithCorrectId() {
		Donation donation1 = this.donationService.findDonationById(1);
		Assertions.assertThat(donation1.getName().toString()).isEqualTo("Donacion Juan");
		Assertions.assertThat(donation1.getCause().getId()).isEqualTo(1);
		Assertions.assertThat(donation1.getAmount()).isEqualTo(100.0);
	}

	@Test
	void shouldInsertDonation() {
		Donation donation = new Donation();
		Cause cause = new Cause();

		cause.setId(1);

		donation.setName("Donation Test Service");
		donation.setAmount(150.0);
		donation.setCause(cause);

		this.donationService.save(donation);
		Assertions.assertThat(donation.getId().longValue()).isEqualTo(8);
	}
}
