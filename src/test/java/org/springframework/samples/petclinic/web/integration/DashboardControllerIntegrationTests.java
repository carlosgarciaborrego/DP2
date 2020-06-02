
package org.springframework.samples.petclinic.web.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.web.DashboardController;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ModelMap;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DashboardControllerIntegrationTests {

	@Autowired
	private DashboardController dashboardController;


	@Test
	void testShowDashboard() throws Exception {
		ModelMap model = new ModelMap();

		String view = this.dashboardController.obtenerDashboard(model);

		Assertions.assertEquals(view, "dashboard/dashboard");
		Assertions.assertNotNull(model.get("dashboard"));
	}

}
