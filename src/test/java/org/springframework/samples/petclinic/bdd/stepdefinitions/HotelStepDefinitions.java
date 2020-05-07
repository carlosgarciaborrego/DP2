
package org.springframework.samples.petclinic.bdd.stepdefinitions;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.java.Log;

@Log
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class HotelStepDefinitions extends AbstractStep {

	@LocalServerPort
	private int	port;

	private int	hotelesAlInicio	= 0;
	private int	hotelesAlAnadir	= 0;
	private int	hotelesAlFinal	= 0;


	@Given("I am logged in the system as admin")
	public void i_am_logged_in_the_system_as_admin() {
		this.getDriver().get("http://localhost:" + this.port + "/petclinic/");
		this.getDriver().findElement(By.xpath("//ul[2]/li/a")).click();
		this.getDriver().findElement(By.id("username")).click();
		this.getDriver().findElement(By.id("username")).clear();
		this.getDriver().findElement(By.id("username")).sendKeys("admin1");
		this.getDriver().findElement(By.id("password")).click();
		this.getDriver().findElement(By.id("password")).clear();
		this.getDriver().findElement(By.id("password")).sendKeys("4dm1n");
		this.getDriver().findElement(By.xpath("//button[@type='submit']")).click();
	}

	@When("I add, edit and delete a hotel")
	public void i_manage_a_hotel() {
		this.getDriver().findElement(By.xpath("//li[6]/a")).click();
		this.hotelesAlInicio = this.contarHoteles();
		this.getDriver().findElement(By.xpath("//div/div/div/a")).click();
		this.getDriver().findElement(By.id("name")).click();
		this.getDriver().findElement(By.id("name")).clear();
		this.getDriver().findElement(By.id("name")).sendKeys("Calle Betis");
		this.getDriver().findElement(By.id("location")).click();
		this.getDriver().findElement(By.id("location")).clear();
		this.getDriver().findElement(By.id("location")).sendKeys("Sevilla");
		this.getDriver().findElement(By.id("capacity")).click();
		this.getDriver().findElement(By.id("capacity")).clear();
		this.getDriver().findElement(By.id("capacity")).sendKeys("10");
		this.getDriver().findElement(By.xpath("//button[@type='submit']")).click();
		this.hotelesAlAnadir = this.contarHoteles();
		this.getDriver().findElement(By.xpath("//table[@id='hotelsTable']/tbody/tr[2]/td[2]")).click();
		this.getDriver().findElement(By.xpath("(//a[contains(text(),'Show')])[2]")).click();
		this.getDriver().findElement(By.id("location")).click();
		this.getDriver().findElement(By.id("location")).clear();
		this.getDriver().findElement(By.id("location")).sendKeys("Huelva");
		this.getDriver().findElement(By.xpath("//button[@type='submit']")).click();
		this.getDriver().findElement(By.xpath("//table[@id='hotelsTable']/tbody/tr[2]/td[2]")).click();
		this.getDriver().findElement(By.xpath("//table[@id='hotelsTable']/tbody/tr[2]/td[5]/a[2]")).click();
		this.hotelesAlFinal = this.contarHoteles();

	}

	private int contarHoteles() {
		WebElement tablaHotel = this.getDriver().findElement(By.xpath("//table[1]"));
		List<WebElement> filasDeTablaHoteles = tablaHotel.findElements(By.tagName("tr"));
		return filasDeTablaHoteles.size();
	}

	@Then("the view is the same that when I managed the hotel")
	public void the_same_hotels() {
		Assert.assertTrue(this.contarHoteles() == this.hotelesAlInicio);
		this.stopDriver();
	}

}
