
package org.springframework.samples.petclinic.ui;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class VetUITest {

	private WebDriver		driver;
	private String			baseUrl;
	private boolean			acceptNextAlert		= true;
	private StringBuffer	verificationErrors	= new StringBuffer();


	@BeforeEach
	public void setUp() throws Exception {
		//		String path = "D:\\Ingenieria de Software 19-20\\DP2\\";
		//		System.setProperty("webdriver.chrome.driver", path + "chromedriver.exe");
		//		this.driver = new ChromeDriver();
		String pathToGeckoDriver = "D:\\Ingenieria de Software 19-20\\DP2\\";
		System.setProperty("webdriver.gecko.driver", pathToGeckoDriver + "\\geckodriver.exe");

		this.driver = new FirefoxDriver();
		this.baseUrl = "https://www.google.com/";
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testListVet() throws Exception {
		this.driver.get("http://localhost:8080/petclinic/");
		this.driver.findElement(By.xpath("//a[contains(@href, '/petclinic/login')]")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).clear();
		this.driver.findElement(By.xpath("//input[@id='username']")).sendKeys("admin1");
		this.driver.findElement(By.xpath("//input[@id='password']")).click();
		this.driver.findElement(By.xpath("//input[@id='password']")).clear();
		this.driver.findElement(By.xpath("//input[@id='password']")).sendKeys("4dm1n");
		this.driver.findElement(By.xpath("//input[@id='password']")).sendKeys(Keys.ENTER);
		Thread.sleep(100);
		this.driver.findElement(By.xpath("//a[contains(@href, '/petclinic/vets')]")).click();
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li/a/span[2]")).click();
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).click();
		this.driver.findElement(By.linkText("Logout")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}
	@Test
	public void testCreateAndDeleteVetPositive() throws Exception {
		this.driver.get("http://localhost:8080/petclinic/");
		this.driver.findElement(By.xpath("//a[contains(@href, '/petclinic/login')]")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).clear();
		this.driver.findElement(By.xpath("//input[@id='username']")).sendKeys("admin1");
		this.driver.findElement(By.xpath("//input[@id='password']")).click();
		this.driver.findElement(By.xpath("//input[@id='password']")).clear();
		this.driver.findElement(By.xpath("//input[@id='password']")).sendKeys("4dm1n");
		this.driver.findElement(By.xpath("//input[@id='password']")).sendKeys(Keys.ENTER);
		Thread.sleep(100);
		this.driver.findElement(By.xpath("//a[contains(@href, '/petclinic/vets')]")).click();
		this.driver.findElement(By.xpath("//a[contains(text(),'New vet')]")).click();
		this.driver.findElement(By.id("firstName")).click();
		this.driver.findElement(By.id("firstName")).clear();
		this.driver.findElement(By.id("firstName")).sendKeys("Juan");
		this.driver.findElement(By.id("lastName")).click();
		this.driver.findElement(By.id("lastName")).clear();
		this.driver.findElement(By.id("lastName")).sendKeys("Jimenez");
		this.driver.findElement(By.id("address")).click();
		this.driver.findElement(By.id("address")).clear();
		this.driver.findElement(By.id("address")).sendKeys("La Botica 12");
		this.driver.findElement(By.id("city")).click();
		this.driver.findElement(By.id("city")).clear();
		this.driver.findElement(By.id("city")).sendKeys("Sevilla");
		this.driver.findElement(By.id("telephone")).click();
		this.driver.findElement(By.id("telephone")).clear();
		this.driver.findElement(By.id("telephone")).sendKeys("666666666");
		this.driver.findElement(By.id("user.username")).click();
		this.driver.findElement(By.id("user.username")).clear();
		this.driver.findElement(By.id("user.username")).sendKeys("JuanRubiio");
		this.driver.findElement(By.id("user.password")).click();
		this.driver.findElement(By.id("user.password")).clear();
		this.driver.findElement(By.id("user.password")).sendKeys("12ke28600");
		this.driver.findElement(By.xpath("//option[@value='Snt Paul Clinic']")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("(//a[contains(text(),'Delete')])[7]")).click();
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).click();
		this.driver.findElement(By.linkText("Logout")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

	@Test
	public void testCreateVetNegative() throws Exception {
		this.driver.get("http://localhost:8080/petclinic/");
		this.driver.findElement(By.xpath("//a[contains(@href, '/petclinic/login')]")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).clear();
		this.driver.findElement(By.xpath("//input[@id='username']")).sendKeys("admin1");
		this.driver.findElement(By.xpath("//input[@id='password']")).click();
		this.driver.findElement(By.xpath("//input[@id='password']")).clear();
		this.driver.findElement(By.xpath("//input[@id='password']")).sendKeys("4dm1n");
		this.driver.findElement(By.xpath("//input[@id='password']")).sendKeys(Keys.ENTER);
		Thread.sleep(100);
		this.driver.findElement(By.xpath("//a[contains(@href, '/petclinic/vets')]")).click();
		this.driver.findElement(By.xpath("//a[contains(text(),'New vet')]")).click();
		this.driver.findElement(By.id("firstName")).click();
		this.driver.findElement(By.id("firstName")).clear();
		this.driver.findElement(By.id("firstName")).sendKeys("");
		this.driver.findElement(By.id("lastName")).click();
		this.driver.findElement(By.id("lastName")).clear();
		this.driver.findElement(By.id("lastName")).sendKeys("");
		this.driver.findElement(By.id("address")).click();
		this.driver.findElement(By.id("address")).clear();
		this.driver.findElement(By.id("address")).sendKeys("La Botica 12");
		this.driver.findElement(By.id("city")).click();
		this.driver.findElement(By.id("city")).clear();
		this.driver.findElement(By.id("city")).sendKeys("Sevilla");
		this.driver.findElement(By.id("telephone")).click();
		this.driver.findElement(By.id("telephone")).clear();
		this.driver.findElement(By.id("telephone")).sendKeys("666666666");
		this.driver.findElement(By.id("user.username")).click();
		this.driver.findElement(By.id("user.username")).clear();
		this.driver.findElement(By.id("user.username")).sendKeys("JuanRubiio");
		this.driver.findElement(By.id("user.password")).click();
		this.driver.findElement(By.id("user.password")).clear();
		this.driver.findElement(By.id("user.password")).sendKeys("12ke28600");
		this.driver.findElement(By.xpath("//option[@value='Snt Paul Clinic']")).click();
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
