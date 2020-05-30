
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
public class HotelUITest {

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
	public void testHotelNegativeUI() throws Exception {
		this.whenIamLoggedIntheSystem();
		this.thenICanManageTheHotels();
	}

	private void whenIamLoggedIntheSystem() throws Exception {
		this.driver.get("http://localhost:" + this.port + "/petclinic/");
		this.driver.findElement(By.xpath("//ul[2]/li/a")).click();
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("admin1");
		this.driver.findElement(By.id("password")).click();
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("4dm1n");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	private void thenICanManageTheHotels() throws Exception {
		this.insertHotel();
		this.hotelWithNegativeCause();
		this.hotelWithPositiveCause();
		this.showHotel(); //and then update hotel
		this.hotelWithNegativeCause();
		this.hotelWithPositiveCause();
		this.deleteHotel();
	}

	private void insertHotel() {
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[6]/a/span[2]")).click();
		this.driver.findElement(By.xpath("//div/div/div/a")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	private void showHotel() {
		this.driver.findElement(By.xpath("(//a[contains(text(),'Show')])[2]")).click();
	}

	private void deleteHotel() {
		this.driver.findElement(By.xpath("//table[@id='hotelsTable']/tbody/tr[2]/td[5]/a[2]")).click();
	}

	private void hotelWithNegativeCause() {
		this.driver.findElement(By.id("name")).click();
		this.driver.findElement(By.id("name")).clear();
		this.driver.findElement(By.id("name")).sendKeys("");
		this.driver.findElement(By.id("location")).click();
		this.driver.findElement(By.id("location")).clear();
		this.driver.findElement(By.id("location")).sendKeys("");
		this.driver.findElement(By.id("count")).click();
		this.driver.findElement(By.id("count")).clear();
		this.driver.findElement(By.id("count")).sendKeys("-1");
		this.driver.findElement(By.id("capacity")).click();
		this.driver.findElement(By.id("capacity")).clear();
		this.driver.findElement(By.id("capacity")).sendKeys("-1");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	private void hotelWithPositiveCause() {
		this.driver.findElement(By.id("name")).click();
		this.driver.findElement(By.id("name")).clear();
		this.driver.findElement(By.id("name")).sendKeys("Calle Betis");
		this.driver.findElement(By.id("location")).click();
		this.driver.findElement(By.id("location")).clear();
		this.driver.findElement(By.id("location")).sendKeys("Sevilla");
		this.driver.findElement(By.id("count")).click();
		this.driver.findElement(By.id("count")).clear();
		this.driver.findElement(By.id("count")).sendKeys("0");
		this.driver.findElement(By.id("capacity")).click();
		this.driver.findElement(By.id("capacity")).clear();
		this.driver.findElement(By.id("capacity")).sendKeys("6");
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
