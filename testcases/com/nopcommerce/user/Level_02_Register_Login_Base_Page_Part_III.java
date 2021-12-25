package com.nopcommerce.user;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import commons.BasePage;

public class Level_02_Register_Login_Base_Page_Part_III extends BasePage {
	WebDriver driver;
	String emailAddress;
	String projectPath = System.getProperty("user.dir");
	
	Select select;
	WebDriverWait explicitWait;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		
		emailAddress = "afc" + generateFakeNumber() + "@mail.vn";
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@Test
	public void TC_01_Register_Empty_Data() {
		openUrl(driver, "https://demo.nopcommerce.com/");

		clickToElement(driver, "//a[@class='ico-register']");
		clickToElement(driver, "//button[@id='register-button']");
		
		Assert.assertEquals(getElementText(driver, "//span[@id='FirstName-error']"), "First name is required.");
		Assert.assertEquals(getElementText(driver, "//span[@id='LastName-error']"), "First name is required.");
		Assert.assertEquals(getElementText(driver, "//span[@id='Email-error']"), "First name is required.");
		Assert.assertEquals(getElementText(driver, "//span[@id='Password-error']"), "First name is required.");
		Assert.assertEquals(getElementText(driver, "//span[@id='ConfirmPassword-error']"), "First name is required.");
	}

	@Test
	public void TC_02_Register_Invalid_Email() {
		driver.findElement(By.cssSelector("a.ico-register")).click();

		driver.findElement(By.cssSelector("input#FirstName")).sendKeys("Automation");
		driver.findElement(By.cssSelector("input#LastName")).sendKeys("FC");
		driver.findElement(By.cssSelector("input#Email")).sendKeys("123@456#%*");
		driver.findElement(By.cssSelector("input#Password")).sendKeys("123456");
		driver.findElement(By.cssSelector("input#ConfirmPassword")).sendKeys("123456");

		driver.findElement(By.cssSelector("button#register-button")).click();

		Assert.assertEquals(driver.findElement(By.cssSelector("span#Email-error")).getText(), "Wrong email");

	}

	@Test
	public void TC_03_Register_Success() {
		driver.findElement(By.cssSelector("a.ico-register")).click();

		driver.findElement(By.cssSelector("input#FirstName")).sendKeys("Automation");
		driver.findElement(By.cssSelector("input#LastName")).sendKeys("FC");
		driver.findElement(By.cssSelector("input#Email")).sendKeys(emailAddress);
		driver.findElement(By.cssSelector("input#Password")).sendKeys("123456");
		driver.findElement(By.cssSelector("input#ConfirmPassword")).sendKeys("123456");

		driver.findElement(By.cssSelector("button#register-button")).click();

		Assert.assertEquals(driver.findElement(By.cssSelector("div.result")).getText(), "Your registration completed");

		driver.findElement(By.cssSelector("a.ico-logout")).click();
	}

	@Test
	public void TC_04_Register_Existing_Email() {
		driver.findElement(By.cssSelector("a.ico-register")).click();

		driver.findElement(By.cssSelector("input#FirstName")).sendKeys("Automation");
		driver.findElement(By.cssSelector("input#LastName")).sendKeys("FC");
		driver.findElement(By.cssSelector("input#Email")).sendKeys(emailAddress);
		driver.findElement(By.cssSelector("input#Password")).sendKeys("123456");
		driver.findElement(By.cssSelector("input#ConfirmPassword")).sendKeys("123456");

		driver.findElement(By.cssSelector("button#register-button")).click();

		Assert.assertEquals(driver.findElement(By.cssSelector("div.message-error li")).getText(), "The specified email already exists");
	}

	@Test
	public void TC_05_Register_Password_Less_Than_6_Chars() {
		driver.findElement(By.cssSelector("a.ico-register")).click();

		driver.findElement(By.cssSelector("input#FirstName")).sendKeys("Automation");
		driver.findElement(By.cssSelector("input#LastName")).sendKeys("FC");
		driver.findElement(By.cssSelector("input#Email")).sendKeys(emailAddress);
		driver.findElement(By.cssSelector("input#Password")).sendKeys("123");
		driver.findElement(By.cssSelector("input#ConfirmPassword")).sendKeys("123");

		driver.findElement(By.cssSelector("button#register-button")).click();

		Assert.assertEquals(driver.findElement(By.cssSelector("#Password-error")).getText(), "Password must meet the following rules:\nmust have at least 6 characters");
	}

	@Test
	public void TC_06_Register_Invalid_Confirm_Password() {
		driver.findElement(By.cssSelector("a.ico-register")).click();

		driver.findElement(By.cssSelector("input#FirstName")).sendKeys("Automation");
		driver.findElement(By.cssSelector("input#LastName")).sendKeys("FC");
		driver.findElement(By.cssSelector("input#Email")).sendKeys(emailAddress);
		driver.findElement(By.cssSelector("input#Password")).sendKeys("123456");
		driver.findElement(By.cssSelector("input#ConfirmPassword")).sendKeys("654321");

		driver.findElement(By.cssSelector("button#register-button")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector("#ConfirmPassword-error")).getText(), "The password and confirmation password do not match.");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public int generateFakeNumber() {
		Random rand = new Random();
		return rand.nextInt(9999);
	}
}
