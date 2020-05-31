
package org.springframework.samples.petclinic.ui;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CauseNegativeUITest {

	@LocalServerPort
	private int				port;
	private WebDriver		driver;
	private String			baseUrl;
	private boolean			acceptNextAlert		= true;
	private StringBuffer	verificationErrors	= new StringBuffer();


	@BeforeEach
	public void setUp() throws Exception {

		String pathToGeckoDriver = "C:\\Ingenieria de Software 19-20\\DP2\\";
		System.setProperty("webdriver.gecko.driver", pathToGeckoDriver + "\\geckodriver.exe");

		this.driver = new FirefoxDriver();
		this.baseUrl = "https://www.google.com/";
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void CauseNegativeUI() throws Exception {
		this.driver.get("http://localhost:" + this.port + "/petclinic/");
		this.driver.findElement(By.linkText("Login")).click();
		this.driver.findElement(By.xpath("//form[@action='/petclinic/login']")).click();
		this.driver.findElement(By.xpath("//div")).click();
		this.driver.findElement(By.id("password")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.linkText("Causes")).click();
		this.driver.findElement(By.xpath("(//a[contains(text(),'Show')])[2]")).click();
		this.driver.findElement(By.xpath("//form[@id='cause']/div/div")).click();
		this.driver.findElement(By.id("name")).clear();
		this.driver.findElement(By.id("name")).sendKeys("");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.id("name")).click();
		this.driver.findElement(By.id("name")).clear();
		this.driver.findElement(By.id("name")).sendKeys("Pon una mascota en tu vida");
		this.driver.findElement(By.xpath("//form[@id='cause']/div/div[2]")).click();
		this.driver.findElement(By.id("description")).clear();
		this.driver.findElement(By.id("description")).sendKeys("");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.id("description")).click();
		this.driver.findElement(By.id("description")).clear();
		this.driver.findElement(By.id("description")).sendKeys("Description Cause");
		this.driver.findElement(By.id("organisation")).click();
		this.driver.findElement(By.id("organisation")).clear();
		this.driver.findElement(By.id("organisation")).sendKeys("");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.id("organisation")).click();
		this.driver.findElement(By.id("organisation")).clear();
		this.driver.findElement(By.id("organisation")).sendKeys("Organisation Cause");
		this.driver.findElement(By.xpath("//form[@id='cause']/div/div[4]")).click();
		this.driver.findElement(By.id("budgetTarget")).clear();
		this.driver.findElement(By.id("budgetTarget")).sendKeys("");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.id("budgetTarget")).click();
		this.driver.findElement(By.id("budgetTarget")).clear();
		this.driver.findElement(By.id("budgetTarget")).sendKeys("2000.0");
		this.driver.findElement(By.xpath("//form[@id='cause']/div/div[5]")).click();
		this.driver.findElement(By.id("budgetArchivied")).clear();
		this.driver.findElement(By.id("budgetArchivied")).sendKeys("");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.id("budgetArchivied")).click();
		this.driver.findElement(By.id("budgetArchivied")).clear();
		this.driver.findElement(By.id("budgetArchivied")).sendKeys("500.0");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
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
