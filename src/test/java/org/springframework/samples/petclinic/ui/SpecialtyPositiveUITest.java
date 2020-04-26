
package org.springframework.samples.petclinic.ui;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SpecialtyPositiveUITest {

	private WebDriver		driver;
	private String			baseUrl;
	private boolean			acceptNextAlert		= true;
	private StringBuffer	verificationErrors	= new StringBuffer();


	@BeforeEach
	public void setUp() throws Exception {
		System.setProperty("webdriver.gecko.driver", "/Users/carlosjesusgarciaborrego/Downloads/Drivers/geckodriver");
		this.driver = new FirefoxDriver();
		this.baseUrl = "https://www.google.com/";
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testSpecialtyPositiveUI() throws Exception {
		this.driver.get("http://localhost:8080/petclinic/");
		this.driver.findElement(By.xpath("//ul[2]/li/a")).click();
		this.driver.findElement(By.id("username")).click();
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("admin1");
		this.driver.findElement(By.id("password")).click();
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("4dm1n");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[3]/a/span[2]")).click();
		this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr/td[2]")).click();
		this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr/td[2]")).click();
		this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr/td[2]")).click();
		Assert.assertEquals("none", this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr/td[2]")).getText());
		this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr/td[3]/a[2]")).click();
		this.driver.findElement(By.id("name")).click();
		this.driver.findElement(By.id("name")).clear();
		this.driver.findElement(By.id("name")).sendKeys("Ophthalmology");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr/td[2]")).click();
		this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr/td[2]")).click();
		Assert.assertEquals("Ophthalmology", this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr/td[2]")).getText());
		this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr/td[3]/a[2]")).click();
		this.driver.findElement(By.xpath("//table[@id='specialitiesTable']/tbody/tr/td[2]/a")).click();
		this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr/td[2]")).click();
		this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr/td[2]")).click();
		Assert.assertEquals("none", this.driver.findElement(By.xpath("//table[@id='vetsTable']/tbody/tr/td[2]")).getText());
	}

	@AfterEach
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
