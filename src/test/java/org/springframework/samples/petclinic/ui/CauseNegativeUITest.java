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
public class CauseNegativeUITest {
	
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
  public void CauseNegativeUI() throws Exception {
	  driver.get("http://localhost:" + this.port + "/petclinic/");
    driver.findElement(By.linkText("Login")).click();
    driver.findElement(By.xpath("//form[@action='/petclinic/login']")).click();
    driver.findElement(By.xpath("//div")).click();
    driver.findElement(By.id("password")).click();
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.linkText("Causes")).click();
    driver.findElement(By.xpath("(//a[contains(text(),'Show')])[2]")).click();
    driver.findElement(By.xpath("//form[@id='cause']/div/div")).click();
    driver.findElement(By.id("name")).clear();
    driver.findElement(By.id("name")).sendKeys("");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("name")).clear();
    driver.findElement(By.id("name")).sendKeys("Pon una mascota en tu vida");
    driver.findElement(By.xpath("//form[@id='cause']/div/div[2]")).click();
    driver.findElement(By.id("description")).clear();
    driver.findElement(By.id("description")).sendKeys("");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.id("description")).click();
    driver.findElement(By.id("description")).clear();
    driver.findElement(By.id("description")).sendKeys("Description Cause");
    driver.findElement(By.id("organisation")).click();
    driver.findElement(By.id("organisation")).clear();
    driver.findElement(By.id("organisation")).sendKeys("");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.id("organisation")).click();
    driver.findElement(By.id("organisation")).clear();
    driver.findElement(By.id("organisation")).sendKeys("Organisation Cause");
    driver.findElement(By.xpath("//form[@id='cause']/div/div[4]")).click();
    driver.findElement(By.id("budgetTarget")).clear();
    driver.findElement(By.id("budgetTarget")).sendKeys("");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.id("budgetTarget")).click();
    driver.findElement(By.id("budgetTarget")).clear();
    driver.findElement(By.id("budgetTarget")).sendKeys("2000.0");
    driver.findElement(By.xpath("//form[@id='cause']/div/div[5]")).click();
    driver.findElement(By.id("budgetArchivied")).clear();
    driver.findElement(By.id("budgetArchivied")).sendKeys("");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    driver.findElement(By.id("budgetArchivied")).click();
    driver.findElement(By.id("budgetArchivied")).clear();
    driver.findElement(By.id("budgetArchivied")).sendKeys("500.0");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
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
