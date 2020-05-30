/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;

/**
 * Integration test of the Service and the Repository layer.
 * <p>
 * ClinicServiceSpringDataJpaTests subclasses benefit from the following services provided
 * by the Spring TestContext Framework:
 * </p>
 * <ul>
 * <li><strong>Spring IoC container caching</strong> which spares us unnecessary set up
 * time between test execution.</li>
 * <li><strong>Dependency Injection</strong> of test fixture instances, meaning that we
 * don't need to perform application context lookups. See the use of
 * {@link Autowired @Autowired} on the <code>{@link
 * ClinicServiceTests#clinicService clinicService}</code> instance variable, which uses
 * autowiring <em>by type</em>.
 * <li><strong>Transaction management</strong>, meaning each test method is executed in
 * its own transaction, which is automatically rolled back by default. Thus, even if tests
 * insert or otherwise change database state, there is no need for a teardown or cleanup
 * script.
 * <li>An {@link org.springframework.context.ApplicationContext ApplicationContext} is
 * also inherited and can be used for explicit bean lookup if necessary.</li>
 * </ul>
 *
 * @author Ken Krebs
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Dave Syer
 */

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
class VetServiceTests {

	@Autowired
	protected VetService vetService;


	@Test
	void shouldFindVets() {
		Collection<Vet> vets = this.vetService.findVets();

		Vet vet = EntityUtils.getById(vets, Vet.class, 3);
		Assertions.assertThat(vet.getLastName()).isEqualTo("Douglas");
		Assertions.assertThat(vet.getNrOfSpecialties()).isEqualTo(2);
		Assertions.assertThat(vet.getSpecialties().get(0).getName()).isEqualTo("dentistry");
		Assertions.assertThat(vet.getSpecialties().get(1).getName()).isEqualTo("surgery");
	}

	@Test
	void shouldFindVetWithCorrectId() {
		Vet hotel1 = this.vetService.findVetById(1).get();

		Assertions.assertThat(hotel1.getAddress()).isEqualTo("110 W. Liberty St.");
		Assertions.assertThat(hotel1.getCity()).isEqualTo("Madison");
		Assertions.assertThat(hotel1.getFirstName()).isEqualTo("James");
		Assertions.assertThat(hotel1.getTelephone()).isEqualTo("6085551023");
		Assertions.assertThat(hotel1.getLastName()).isEqualTo("Carter");
		Assertions.assertThat(hotel1.getSpecialties().get(0)).isEqualTo("none");
		Assertions.assertThat(hotel1.getClinic().getId()).isEqualTo(1);
	}

	@Test
	void shouldInsertVet() {
		Vet history = new Vet();
		history.setAddress("Adress");
		history.setCity("City");
		history.setFirstName("First name");
		history.setTelephone("666666666");
		history.setLastName("last name");
		User user = new User();
		user.setPassword("333333");
		user.setUsername("vet7");
		history.setUser(user);

		this.vetService.saveVet(history);
		Assertions.assertThat(history.getId().longValue()).isEqualTo(7);
	}

	@Test
	void shouldUpdateVet() {
		Iterable<Vet> vets = this.vetService.findVets();
		Collection<Vet> nuevaLista = new ArrayList<Vet>();

		for (Vet h : vets) {
			nuevaLista.add(h);
		}

		Vet vet1 = EntityUtils.getById(nuevaLista, Vet.class, 1);
		Vet vet2 = EntityUtils.getById(nuevaLista, Vet.class, 1);
		vet1.setAddress("adress");
		vet1.setCity("city");
		vet1.setTelephone("6085551023");
		vet1.setFirstName("Esto es un nuevo nombre");
		vet1.setTelephone("666666666");

		this.vetService.saveVet(vet1);

		Assertions.assertThat(vet2.getAddress()).isEqualTo(vet1.getAddress());
		Assertions.assertThat(vet2.getCity()).isEqualTo(vet1.getCity());
		Assertions.assertThat(vet2.getFirstName()).isEqualTo(vet1.getFirstName());
	}

	@Test
	void shouldDeleteVet() {
		Iterable<Vet> vets = this.vetService.findVets();
		Collection<Vet> nuevaLista = new ArrayList<Vet>();

		for (Vet h : vets) {
			nuevaLista.add(h);
		}

		Vet vet1 = EntityUtils.getById(nuevaLista, Vet.class, 1);

		this.vetService.delete(vet1.getId());

		Iterable<Vet> vets2 = this.vetService.findVets();
		Collection<Vet> nuevaLista2 = new ArrayList<Vet>();

		for (Vet h : vets2) {
			nuevaLista2.add(h);
		}

		Assertions.assertThat(nuevaLista.size() > nuevaLista2.size());
	}

	@Test
	void shouldAddSpecialty() {
		Collection<Vet> vets = this.vetService.findVets();
		Specialty res = new Specialty();
		res.setId(3);
		res.setName("Prueba");

		Vet vet = EntityUtils.getById(vets, Vet.class, 3);
		Assertions.assertThat(vet.getLastName()).isEqualTo("Douglas");
		vet.addSpecialty(res);
		Assertions.assertThat(vet.getNrOfSpecialties()).isEqualTo(3);
	}

	@Test
	void shouldDeleteSpecialty() {
		Collection<Vet> vets = this.vetService.findVets();

		Vet vet = EntityUtils.getById(vets, Vet.class, 3);
		Assertions.assertThat(vet.getLastName()).isEqualTo("Douglas");
		vet.deleteSpecialty(vet.getSpecialties().get(0));
		Assertions.assertThat(vet.getNrOfSpecialties()).isEqualTo(1);
	}

}
