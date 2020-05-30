
package org.springframework.samples.petclinic.ui;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CausePositiveUITest {

	private WebDriver		driver;
	private String			baseUrl;
	private boolean			acceptNextAlert		= true;
	private StringBuffer	verificationErrors	= new StringBuffer();


	@Before
	public void setUp() throws Exception {
		//    driver = new FirefoxDriver();
		//    baseUrl = "https://www.google.com/";
		//    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		//Formato para mac
		System.setProperty("webdriver.gecko.driver", "/Users/carlosjesusgarciaborrego/Downloads/Drivers/geckodriver");
		this.driver = new FirefoxDriver();
		this.baseUrl = "https://www.google.com/";
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testManageCausesPositive() throws Exception {
		this.driver.get("http://localhost:8080/petclinic/");
		this.driver.findElement(By.linkText("Login")).click();
		this.driver.findElement(By.id("username")).click();
		this.driver.findElement(By.xpath("//div")).click();
		this.driver.findElement(By.id("password")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.linkText("Causes")).click();
		this.driver.findElement(By.linkText("Show")).click();
		this.driver.findElement(By.id("name")).click();
		this.driver.findElement(By.id("name")).clear();
		this.driver.findElement(By.id("name")).sendKeys("Pon una mascota en tu vida, edita");
		this.driver.findElement(By.id("description")).click();
		this.driver.findElement(By.id("description")).clear();
		this.driver.findElement(By.id("description")).sendKeys("Description Cause, edita");
		this.driver.findElement(By.id("organisation")).click();
		this.driver.findElement(By.id("organisation")).clear();
		this.driver.findElement(By.id("organisation")).sendKeys("Organisation Cause, edita");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.linkText("Show")).click();
		try {
			Assert.assertEquals("Pon una mascota en tu vida, edita", this.driver.findElement(By.id("name")).getAttribute("value"));
		} catch (Error e) {
			this.verificationErrors.append(e.toString());
		}
		try {
			Assert.assertEquals("Description Cause, edita", this.driver.findElement(By.id("description")).getAttribute("value"));
		} catch (Error e) {
			this.verificationErrors.append(e.toString());
		}
		try {
			Assert.assertEquals("Organisation Cause, edita", this.driver.findElement(By.id("organisation")).getAttribute("value"));
		} catch (Error e) {
			this.verificationErrors.append(e.toString());
		}
	}

	@After
	public void tearDown() throws Exception {
		this.driver.quit();
		String verificationErrorString = this.verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			Assert.fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(final By by) {
		try {
			this.driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			this.driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = this.driver.switchTo().alert();
			String alertText = alert.getText();
			if (this.acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			this.acceptNextAlert = true;
		}
	}
}
