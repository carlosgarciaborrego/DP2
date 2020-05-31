
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
public class CausePositiveUITest {
	
		@LocalServerPort
		private int				port;
		private WebDriver driver;
		private String baseUrl;
		private boolean acceptNextAlert = true;
		private StringBuffer verificationErrors = new StringBuffer();
	
		@BeforeEach
		public void setUp() throws Exception {
			
			String pathToGeckoDriver = "C:\\Ingenieria de Software 19-20\\DP2\\";
			System.setProperty("webdriver.gecko.driver", pathToGeckoDriver + "\\geckodriver.exe");

			
		   driver = new FirefoxDriver();
		   baseUrl = "https://www.google.com/";
		   driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}
	
	  @Test
	  public void testManageCausesPositive() throws Exception {
	    driver.get("http://localhost:" + this.port + "/petclinic/");
		driver.findElement(By.linkText("Login")).click();
		driver.findElement(By.id("username")).click();
		driver.findElement(By.xpath("//div")).click();
		driver.findElement(By.id("password")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.linkText("Causes")).click();
		driver.findElement(By.linkText("Show")).click();
		driver.findElement(By.id("name")).click();
		driver.findElement(By.id("name")).clear();
		driver.findElement(By.id("name")).sendKeys("Pon una mascota en tu vida, edita");
		driver.findElement(By.id("description")).click();
		driver.findElement(By.id("description")).clear();
		driver.findElement(By.id("description")).sendKeys("Description Cause, edita");
		driver.findElement(By.id("organisation")).click();
		driver.findElement(By.id("organisation")).clear();
		driver.findElement(By.id("organisation")).sendKeys("Organisation Cause, edita");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.linkText("Show")).click();
		try {
		  Assert.assertEquals("Pon una mascota en tu vida, edita", driver.findElement(By.id("name")).getAttribute("value"));
		} catch (Error e) {
		  verificationErrors.append(e.toString());
	}
	try {
		Assert.assertEquals("Description Cause, edita", driver.findElement(By.id("description")).getAttribute("value"));
	} catch (Error e) {
	  verificationErrors.append(e.toString());
	}
	try {
		Assert.assertEquals("Organisation Cause, edita", driver.findElement(By.id("organisation")).getAttribute("value"));
	    } catch (Error e) {
	      verificationErrors.append(e.toString());
	    }
	  }
	
	  @AfterEach
	  public void tearDown() throws Exception {
	    driver.quit();
	    String verificationErrorString = verificationErrors.toString();
	    if (!"".equals(verificationErrorString)) {
	      Assert.fail(verificationErrorString);
	    }
	  }
	
	  private boolean isElementPresent(By by) {
	    try {
	      driver.findElement(by);
	      return true;
	    } catch (NoSuchElementException e) {
	      return false;
	    }
	  }
	
	  private boolean isAlertPresent() {
	    try {
	      driver.switchTo().alert();
	      return true;
	    } catch (NoAlertPresentException e) {
	      return false;
	    }
	  }
	
	  private String closeAlertAndGetItsText() {
	    try {
	      Alert alert = driver.switchTo().alert();
	      String alertText = alert.getText();
	      if (acceptNextAlert) {
	        alert.accept();
	      } else {
	        alert.dismiss();
	      }
	      return alertText;
	    } finally {
	      acceptNextAlert = true;
	    }
	  }
}
