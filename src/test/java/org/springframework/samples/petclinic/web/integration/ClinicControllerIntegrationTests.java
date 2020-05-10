
package org.springframework.samples.petclinic.web.integration;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.web.ClinicController;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class ClinicControllerIntegrationTests {

	private static final int	TEST_CLINIC_ID	= 1;

	@Autowired
	private ClinicController	clinicController;

	@Autowired
	private ClinicService		clinicService;

}
