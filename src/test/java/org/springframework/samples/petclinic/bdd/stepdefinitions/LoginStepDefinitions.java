
package org.springframework.samples.petclinic.bdd.stepdefinitions;

import java.util.UUID;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.java.Log;

@Log
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginStepDefinitions extends AbstractStep {

	@LocalServerPort
	private int port;


	@Given("I am not logged in the system")
	public void IamNotLogged() throws Exception {
		getDriver().get("http://localhost:" + this.port);
		WebElement element = getDriver().findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a"));
		if (element == null || !element.getText().equalsIgnoreCase("login")) {
			getDriver().findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/span[2]")).click();
			getDriver().findElement(By.linkText("Logout")).click();
			getDriver().findElement(By.xpath("//button[@type='submit']")).click();
		}
	}

	@When("I do login as user {string}")
	public void IdoLoginAs(final String username) throws Exception {
		LoginStepDefinitions.loginAs(username, LoginStepDefinitions.passwordOf(username), this.port, getDriver());
	}

	public static void loginAs(final String username, final int port, final WebDriver driver) {
		LoginStepDefinitions.loginAs(username, LoginStepDefinitions.passwordOf(username), port, driver);
	}

	public static void loginAs(final String username, final String password, final int port, final WebDriver driver) {
		driver.get("http://localhost:" + port);
		driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).click();
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
	}
	private static String passwordOf(final String username) {
		String result = "4dm1n";
		if ("owner1".equals(username)) {
			result = "0wn3r";
		}
		return result;
	}

	@Then("{string} appears as the current user")
	public void asCurretUserAppears(final String username) throws Exception {
		Assert.assertEquals(username.toUpperCase(), getDriver().findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).getText());
		stopDriver();
	}

	@When("I try to do login as user {string} with an invalid password")
	public void ItryToDoLoginWithAnInvalidPasswordAs(final String username) throws Exception {
		LoginStepDefinitions.loginAs(username, UUID.randomUUID().toString(), this.port, getDriver());
	}

	@Then("the login form is shown again")
	public void theLoginFormIsShownAgain() throws Exception {
		Assert.assertEquals(getDriver().getCurrentUrl(), "http://localhost:" + this.port + "/login-error");
		stopDriver();
	}

}
