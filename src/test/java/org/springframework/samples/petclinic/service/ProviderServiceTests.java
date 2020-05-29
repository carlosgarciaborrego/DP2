
package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.model.Hotel;
import org.springframework.samples.petclinic.model.Provider;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProviderServiceTests {

	@Autowired
	protected ProviderService providerService;


	@Test
	void testCountWithInitialData() {
		int count = this.providerService.providerCount();
		Assertions.assertEquals(count, 4);
	}

	@Test
	void shouldFindAllProviders() {
		Iterable<Provider> providers = this.providerService.findAll();
		Collection<Provider> nuevaLista = new ArrayList<Provider>();

		for (Provider p : providers) {
			nuevaLista.add(p);
		}

		Provider provider1 = EntityUtils.getById(nuevaLista, Provider.class, 1);
		assertThat(provider1.getName()).isEqualTo("mercadona");
		assertThat(provider1.getCity()).isEqualTo("Sevilla");
		assertThat(provider1.getTelephone()).isEqualTo("664455669");
		assertThat(provider1.getDescription()).isEqualTo("comida para los animales");
	}

	@Test
	void shouldFindProviderWithCorrectId(){
		Provider provider1 = this.providerService.findProviderById(1); 
		assertThat(provider1.getName()).isEqualTo("mercadona");
		assertThat(provider1.getCity()).isEqualTo("Sevilla");
		assertThat(provider1.getTelephone()).isEqualTo("664455669");
		assertThat(provider1.getDescription()).isEqualTo("comida para los animales");
	}
	
	@Test
	void shouldInsertProvider() {
		Provider provider = new Provider();
		provider.setName("Nuevo Proveedor");
		provider.setCity("Sevilla");
		provider.setTelephone("667788990");
		provider.setDescription("cosas necesarias");
		Clinic cli = new Clinic();
		cli.setId(1);
		cli.setName("clinica1");
		cli.setEmail("cli1@gmail.com");
		cli.setEmergency("666777666");
		cli.setLocation("sevilla");
		cli.setTelephone("565656561");
		provider.setClinic(cli);
		
		this.providerService.save(provider);
		assertThat(provider.getId().longValue()).isEqualTo(4);
	}
	
	@Test
	void shouldUpdateProvider() {
		Iterable<Provider> providers = this.providerService.findAll();
		Collection<Provider> nuevaLista = new ArrayList<Provider>();

		for (Provider p : providers) {
			nuevaLista.add(p);
		}
		
		
		Provider provider1 = EntityUtils.getById(nuevaLista, Provider.class, 1);
		Provider provider2 = EntityUtils.getById(nuevaLista, Provider.class, 1);
		String name = "John Company's";
		String city = "Huelva";
	
		provider1.setName(name);
		provider1.setCity(city);
		
		this.providerService.save(provider1);
		
		assertThat(provider2.getName()).isEqualTo(provider1.getName());
		assertThat(provider2.getCity()).isEqualTo(provider1.getCity());
	}
	
	@Test
	void shouldDeleteProvider() {
		Iterable<Provider> providers = this.providerService.findAll();
		Collection<Provider> nuevaLista = new ArrayList<Provider>();

		for (Provider p : providers) {
			nuevaLista.add(p);
		}
		
		Provider provider1 = EntityUtils.getById(nuevaLista, Provider.class, 1);
		
		this.providerService.delete(provider1);
		assertThat(nuevaLista.isEmpty());
	}
	
}
