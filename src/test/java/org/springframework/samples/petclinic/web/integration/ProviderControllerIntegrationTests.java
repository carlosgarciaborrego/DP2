
package org.springframework.samples.petclinic.web.integration;

import java.util.Collections;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Clinic;
import org.springframework.samples.petclinic.model.Provider;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.web.ProviderController;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProviderControllerIntegrationTests {

	private static final int	TEST_PROVIDER_ID	= 1;

	private static final int	TEST_CLINIC_ID		= 1;

	@Autowired
	private ProviderController	providercontroller;

	@Autowired
	private ClinicService		clinicService;


	@Test
	void testInitCreationForm() throws Exception {
		ModelMap model = new ModelMap();
		String view = this.providercontroller.crearProvider(model);

		Assert.assertEquals(view, "providers/editProvider");
		Assert.assertNotNull(model.get("provider"));
	}

	@Test
	void testProcessCreationFormSuccess() throws Exception {
		ModelMap model = new ModelMap();
		Provider newProvider = new Provider();
		newProvider.setName("mercadona");
		newProvider.setCity("Estepa");
		newProvider.setTelephone("665577889");
		newProvider.setDescription("Comida canina");
		Clinic clinic = this.clinicService.findClinicById(ProviderControllerIntegrationTests.TEST_CLINIC_ID);
		newProvider.setClinic(clinic);
		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
		String view = this.providercontroller.salvarProvider(newProvider, bindingResult, model);
		Assert.assertEquals(view, "redirect:/providers/");
	}

	@Test
	void testProcessCreationFormHasError() throws Exception {
		ModelMap model = new ModelMap();
		Provider newProvider = new Provider();
		newProvider.setName("Mercadona");
		newProvider.setCity("Estepa");
		newProvider.setDescription("Comida canina");
		Clinic clinic = this.clinicService.findClinicById(ProviderControllerIntegrationTests.TEST_CLINIC_ID);
		newProvider.setClinic(clinic);
		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
		bindingResult.reject("telephone", "numeric value out of bounds (<10 digits>.<0 digits> expected)");
		String view = this.providercontroller.salvarProvider(newProvider, bindingResult, model);
		Assert.assertEquals(view, "providers/editProvider");
	}

	@Test
	void testInitUpdateForm() throws Exception {
		ModelMap model = new ModelMap();

		String view = this.providercontroller.actualizarProvider(ProviderControllerIntegrationTests.TEST_PROVIDER_ID, model);
		Assert.assertEquals(view, "providers/editProvider");
		Assert.assertNotNull(model.get("provider"));
	}

	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		ModelMap model = new ModelMap();
		Provider newProvider = new Provider();
		newProvider.setName("mercadona");
		newProvider.setCity("Jaén");
		newProvider.setTelephone("67678123");
		newProvider.setDescription("La comida de los animales");
		Clinic clinic = this.clinicService.findClinicById(ProviderControllerIntegrationTests.TEST_CLINIC_ID);
		newProvider.setClinic(clinic);
		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
		String view = this.providercontroller.actualizarProviderPost(newProvider, bindingResult, ProviderControllerIntegrationTests.TEST_PROVIDER_ID, model);
		Assert.assertEquals(view, "redirect:/providers/{providerId}");
	}

	@Test
	void testProcessUpdateFormHasError() throws Exception {
		ModelMap model = new ModelMap();
		Provider newProvider = new Provider();
		newProvider.setName("Calle Granada");
		newProvider.setCity("Jaén");
		newProvider.setDescription("Comida felina");
		Clinic clinic = this.clinicService.findClinicById(ProviderControllerIntegrationTests.TEST_CLINIC_ID);
		newProvider.setClinic(clinic);
		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
		bindingResult.reject("telephone", "numeric value out of bounds (<10 digits>.<0 digits> expected)");
		String view = this.providercontroller.salvarProvider(newProvider, bindingResult, model);
		Assert.assertEquals(view, "providers/editProvider");
	}

	@Test
	void testDelete() throws Exception {
		ModelMap model = new ModelMap();
		String view = this.providercontroller.borrarProvider(ProviderControllerIntegrationTests.TEST_PROVIDER_ID, model);
		Assert.assertEquals(view, "providers/listadoProviders");
	}

	@Test
	void testShow() throws Exception {
		ModelMap model = new ModelMap();
		String view = this.providercontroller.showProvider(ProviderControllerIntegrationTests.TEST_PROVIDER_ID, model);
		Assert.assertEquals(view, "providers/editProvider");
	}

}
