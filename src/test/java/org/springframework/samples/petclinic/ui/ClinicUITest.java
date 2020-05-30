
package org.springframework.samples.petclinic.ui;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClinicUITest {

	@LocalServerPort
	private int				port;
	private WebDriver		driver;
	private String			baseUrl;
	private boolean			acceptNextAlert		= true;
	private StringBuffer	verificationErrors	= new StringBuffer();


	@BeforeEach
	public void setUp() throws Exception {
		//Formato para mac
		System.setProperty("webdriver.gecko.driver", "/Users/carlosjesusgarciaborrego/Downloads/Drivers/geckodriver");

		//Formato para windows
		//String pathToGeckoDriver = "D:\\Ingenieria de Software 19-20\\DP2\\";
		//System.setProperty("webdriver.gecko.driver", pathToGeckoDriver + "\\geckodriver.exe");

		//Común
		this.driver = new FirefoxDriver();
		this.baseUrl = "https://www.google.com/";
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	@Test
	public void testCreateDeleteClinic() throws Exception {
		this.driver.get("http://localhost:" + this.port + "/petclinic/");
		this.driver.findElement(By.xpath("//a[contains(@href, '/petclinic/login')]")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).clear();
		this.driver.findElement(By.xpath("//input[@id='username']")).sendKeys("admin1");
		this.driver.findElement(By.xpath("//input[@id='password']")).click();
		this.driver.findElement(By.xpath("//input[@id='password']")).clear();
		this.driver.findElement(By.xpath("//input[@id='password']")).sendKeys("4dm1n");
		this.driver.findElement(By.xpath("//input[@id='password']")).sendKeys(Keys.ENTER);
		Thread.sleep(100);
		this.driver.findElement(By.xpath("//a[contains(@href, '/petclinic/clinic')]")).click();
		this.driver.findElement(By.xpath("//a[contains(text(),'New Clinic')]")).click();
		this.driver.findElement(By.id("name")).click();
		this.driver.findElement(By.id("name")).clear();
		this.driver.findElement(By.id("name")).sendKeys("New Bridge");
		this.driver.findElement(By.id("location")).click();
		this.driver.findElement(By.id("location")).clear();
		this.driver.findElement(By.id("location")).sendKeys("Cordoba");
		this.driver.findElement(By.id("telephone")).clear();
		this.driver.findElement(By.id("telephone")).sendKeys("666999333");
		this.driver.findElement(By.id("emergency")).click();
		this.driver.findElement(By.id("emergency")).clear();
		this.driver.findElement(By.id("emergency")).sendKeys("965478656");
		this.driver.findElement(By.id("capacity")).click();
		this.driver.findElement(By.id("capacity")).clear();
		this.driver.findElement(By.id("capacity")).sendKeys("55");
		this.driver.findElement(By.id("email")).click();
		this.driver.findElement(By.id("email")).clear();
		this.driver.findElement(By.id("email")).sendKeys("bridgenew@gmail.com");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/petclinic/clinic/8/delete')]")).click();
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).click();
		this.driver.findElement(By.linkText("Logout")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	@Test
	public void testCreateNegativClinic() throws Exception {
		this.driver.get("http://localhost:" + this.port + "/petclinic/");
		this.driver.findElement(By.xpath("//a[contains(@href, '/petclinic/login')]")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).clear();
		this.driver.findElement(By.xpath("//input[@id='username']")).sendKeys("admin1");
		this.driver.findElement(By.xpath("//input[@id='password']")).click();
		this.driver.findElement(By.xpath("//input[@id='password']")).clear();
		this.driver.findElement(By.xpath("//input[@id='password']")).sendKeys("4dm1n");
		this.driver.findElement(By.xpath("//input[@id='password']")).sendKeys(Keys.ENTER);
		Thread.sleep(100);
		this.driver.findElement(By.xpath("//a[contains(@href, '/petclinic/clinic')]")).click();
		this.driver.findElement(By.xpath("//a[contains(text(),'New Clinic')]")).click();
		this.driver.findElement(By.id("name")).click();
		this.driver.findElement(By.id("name")).clear();
		this.driver.findElement(By.id("name")).sendKeys("");
		this.driver.findElement(By.id("location")).click();
		this.driver.findElement(By.id("location")).clear();
		this.driver.findElement(By.id("location")).sendKeys("");
		this.driver.findElement(By.id("telephone")).clear();
		this.driver.findElement(By.id("telephone")).sendKeys("666999333");
		this.driver.findElement(By.id("emergency")).click();
		this.driver.findElement(By.id("emergency")).clear();
		this.driver.findElement(By.id("emergency")).sendKeys("965478656");
		this.driver.findElement(By.id("capacity")).click();
		this.driver.findElement(By.id("capacity")).clear();
		this.driver.findElement(By.id("capacity")).sendKeys("55");
		this.driver.findElement(By.id("email")).click();
		this.driver.findElement(By.id("email")).clear();
		this.driver.findElement(By.id("email")).sendKeys("bridgenew@gmail.com");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).click();
		this.driver.findElement(By.linkText("Logout")).click();
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
