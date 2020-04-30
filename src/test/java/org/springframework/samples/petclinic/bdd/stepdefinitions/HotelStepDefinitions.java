
package org.springframework.samples.petclinic.bdd.stepdefinitions;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import io.cucumber.java.en.Given;
import lombok.extern.java.Log;

@Log
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HotelStepDefinitions extends AbstractStep {

	@LocalServerPort
	private int port;


	@Given("I am logged in the system as admin")
	public void i_am_logged_in_the_system_as_admin() {

	}

}
