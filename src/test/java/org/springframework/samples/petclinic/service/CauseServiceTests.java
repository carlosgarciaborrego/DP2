
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
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CauseServiceTests {

	@Autowired
	protected CauseService causeService;


	@Test
	void shouldFindAllCauses() {
		Iterable<Cause> causes = this.causeService.findAll();
		Collection<Cause> nuevaLista = new ArrayList<Cause>();

		for (Cause h : causes) {
			nuevaLista.add(h);
		}
		Cause cause1 = EntityUtils.getById(nuevaLista, Cause.class, 1);
		Assertions.assertThat(cause1.getName().toString()).isEqualTo("Pon una mascota en tu vida");
		Assertions.assertThat(cause1.getBudgetTarget()).isEqualTo(1000.0);
		Assertions.assertThat(cause1.getDescription()).isEqualTo("Description Cause");
		Assertions.assertThat(cause1.getOrganisation()).isEqualTo("Organisation Cause");
	}

	@Test
	void shouldFindCauseWithCorrectId() {
		Cause cause1 = this.causeService.findCauseById(1);
		Assertions.assertThat(cause1.getName().toString()).isEqualTo("Pon una mascota en tu vida");
		Assertions.assertThat(cause1.getBudgetTarget()).isEqualTo(1000.0);
		Assertions.assertThat(cause1.getDescription()).isEqualTo("Description Cause");
		Assertions.assertThat(cause1.getOrganisation()).isEqualTo("Organisation Cause");
	}

	@Test
	void shouldInsertCause() {
		Cause cause = new Cause();
		cause.setName("Calle Betis");
		cause.setBudgetTarget(1000.0);
		cause.setDescription("DescripcionTest");
		cause.setOrganisation("OrganisationTest");
		cause.setBudgetArchivied(0.0);

		this.causeService.save(cause);
		Assertions.assertThat(cause.getId().longValue()).isEqualTo(6);
	}

	@Test
	void shouldUpdateCause() {
		Iterable<Cause> causes = this.causeService.findAll();
		Collection<Cause> nuevaLista = new ArrayList<Cause>();

		for (Cause c : causes) {
			nuevaLista.add(c);
		}

		Cause cause1 = EntityUtils.getById(nuevaLista, Cause.class, 1);
		Cause cause2 = EntityUtils.getById(nuevaLista, Cause.class, 1);
		String name = "nombreTestUPdate";
		Double budgetTarget = 1100.0;
		Double budgetArchivied = 200.0;
		String description = "descr test update";
		cause1.setName(name);
		;
		cause1.setBudgetTarget(budgetTarget);
		cause1.setBudgetArchivied(budgetArchivied);
		cause1.setDescription(description);

		this.causeService.save(cause1);

		Assertions.assertThat(cause2.getName()).isEqualTo(cause1.getName());
		Assertions.assertThat(cause2.getBudgetTarget()).isEqualTo(cause1.getBudgetTarget());
		Assertions.assertThat(cause2.getBudgetArchivied()).isEqualTo(cause1.getBudgetArchivied());
		Assertions.assertThat(cause2.getDescription()).isEqualTo(cause1.getDescription());
		Assertions.assertThat(cause2.getOrganisation()).isEqualTo(cause1.getOrganisation());
	}

	@Test
	void shouldDeleteCause() {
		Iterable<Cause> causes = this.causeService.findAll();
		Collection<Cause> nuevaLista = new ArrayList<Cause>();

		for (Cause c : causes) {
			nuevaLista.add(c);
		}

		Cause cause1 = EntityUtils.getById(nuevaLista, Cause.class, 1);

		this.causeService.delete(cause1);
		Assertions.assertThat(nuevaLista.isEmpty());
	}

}
