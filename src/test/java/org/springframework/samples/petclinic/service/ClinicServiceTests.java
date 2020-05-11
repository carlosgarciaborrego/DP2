
package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ClinicServiceTests {

	@Autowired
	protected ClinicService clinicService;


	@Test
	void shouldFindClinics() {
		Collection<Clinic> clinics = this.clinicService.findAll();
		Clinic c = EntityUtils.getById(clinics, Clinic.class, 3);
		Assertions.assertThat(c.getName()).isEqualTo("Snt Paul Clinic");
		Assertions.assertThat(c.getCapacity()).isEqualTo(10);
	}

	@Test
	void shouldFindClinicWithCorrectId() {
		Clinic clinic1 = this.clinicService.findClinicById(1);

		Assertions.assertThat(clinic1 != null);
		Assertions.assertThat(clinic1.getCapacity()).isEqualTo(50);
		Assertions.assertThat(clinic1.getName()).isEqualTo("Holly Clinic");
		Assertions.assertThat(clinic1.getLocation()).isEqualTo("Sevilla");
	}

	@Test
	void shouldFindAndSaveClinicWithCorrectId() {
		Clinic clinic1 = this.clinicService.findClinicById(1);

		Assertions.assertThat(clinic1 != null);
		Assertions.assertThat(clinic1.getCapacity()).isEqualTo(50);
		Assertions.assertThat(clinic1.getName()).isEqualTo("Holly Clinic");
		Assertions.assertThat(clinic1.getLocation()).isEqualTo("Sevilla");
	}

	@Test
	void shouldUpdateClinic() {
		Iterable<Clinic> clinics = this.clinicService.findAll();
		Collection<Clinic> nuevaLista = new ArrayList<Clinic>();

		for (Clinic h : clinics) {
			nuevaLista.add(h);
		}

		Clinic clinic1 = EntityUtils.getById(nuevaLista, Clinic.class, 1);
		Clinic clinic2 = EntityUtils.getById(nuevaLista, Clinic.class, 1);
		clinic1.setCapacity(6);
		clinic1.setEmail("hola@email.com");
		clinic1.setName("hola");
		clinic1.setTelephone("666666666");

		this.clinicService.saveClinic(clinic1);

		Assertions.assertThat(clinic2.getCapacity()).isEqualTo(clinic1.getCapacity());
		Assertions.assertThat(clinic2.getName()).isEqualTo(clinic1.getName());
		Assertions.assertThat(clinic2.getTelephone()).isEqualTo(clinic1.getTelephone());
	}

	@Test
	void shouldDeleteClinic() {
		Iterable<Clinic> clinics = this.clinicService.findAll();
		Collection<Clinic> nuevaLista = new ArrayList<Clinic>();

		for (Clinic h : clinics) {
			nuevaLista.add(h);
		}

		Clinic clinic1 = EntityUtils.getById(nuevaLista, Clinic.class, 1);

		this.clinicService.delete(clinic1.getId());

		Iterable<Clinic> clinics2 = this.clinicService.findAll();
		Collection<Clinic> nuevaLista2 = new ArrayList<Clinic>();

		for (Clinic h : clinics2) {
			nuevaLista2.add(h);
		}

		Assertions.assertThat(nuevaLista.size() > nuevaLista2.size());
	}

}
