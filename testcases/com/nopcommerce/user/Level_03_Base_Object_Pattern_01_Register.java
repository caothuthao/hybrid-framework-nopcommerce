package com.nopcommerce.user;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import commons.BasePage;
import commons.BaseTest;
import pageObjects.HomePageObject;
import pageObjects.RegisterPageObject;

public class Level_03_Base_Object_Pattern_01_Register extends BaseTest {
	private WebDriver driver;
	
	private HomePageObject homePage;
	private RegisterPageObject registerPage;
	
	String projectPath = System.getProperty("user.dir");
	
	Select select;
	WebDriverWait explicitWait;
	BasePage basePage;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		basePage = new BasePage();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		// 1 - Mở url ra --> mở ra trang Home Page (Business Page)
		driver.get("https://demo.nopcommerce.com/");
		
		// Khởi tạo page lên
		homePage = new HomePageObject(driver);
	}

	@Test
	public void TC_01_Register_Empty_Data() {
		// 2 - Từ Home Page click vào Register link mở ra trang Register Page (Business Page)
		homePage.clickToRegisterLink();
		registerPage = new RegisterPageObject(driver);
		
		registerPage.clickToRegisterButton();
		
		Assert.assertEquals(registerPage.getFirstNameErrorMessage(), "First name is required.");
		Assert.assertEquals(registerPage.getLastNameErrorMessage(), "Last name is required.");
		Assert.assertEquals(registerPage.getEmailErrorMessage(), "Email is required.");
		Assert.assertEquals(registerPage.getPasswordErrorMessage(), "Password is required.");
		Assert.assertEquals(registerPage.getConfirmPasswordErrorMessage(), "Password is required.");

	}

	@Test
	public void TC_02_Register_Invalid_Email() {
		homePage.clickToRegisterLink();
		registerPage = new RegisterPageObject(driver);
		
		registerPage.sendKeyToFirstNameTextBox("Cao");
		registerPage.sendKeyToLastNameTextBox("Thu Thao");
		registerPage.sendKeyToEmailTextBox("thao");
		registerPage.sendKeyToPasswordTextBox("123456");
		registerPage.sendKeyToConfirmPasswordTextBox("123456");
		
		registerPage.clickToRegisterButton();
		
		Assert.assertEquals(registerPage.getEmailErrorMessage(), "Wrong email");
	}

	@Test
	public void TC_03_Register_Success() {
		homePage.clickToRegisterLink();
		registerPage = new RegisterPageObject(driver);
		
		registerPage.sendKeyToFirstNameTextBox("Cao");
		registerPage.sendKeyToLastNameTextBox("Thu Thao");
		registerPage.sendKeyToEmailTextBox("thao784573@gmail.com");
		registerPage.sendKeyToPasswordTextBox("123456");
		registerPage.sendKeyToConfirmPasswordTextBox("123456");
		
		registerPage.clickToRegisterButton();
		
		Assert.assertEquals(registerPage.getRegisterSuccessMessage(), "Your registration completed");

		registerPage.clickToLogoutLink();
		
		// 3 - Từ trang Register --> Home Page
		homePage = new HomePageObject(driver);
	}

	@Test
	public void TC_04_Register_Existing_Email() {
		homePage.clickToRegisterLink();
		registerPage = new RegisterPageObject(driver);
		
		registerPage.sendKeyToFirstNameTextBox("Automation");
		registerPage.sendKeyToLastNameTextBox("FC");
		registerPage.sendKeyToEmailTextBox("thao784573@gmail.com");
		registerPage.sendKeyToPasswordTextBox("123456");
		registerPage.sendKeyToConfirmPasswordTextBox("123456");
		
		registerPage.clickToRegisterButton();
		
		Assert.assertEquals(registerPage.getExistedEmailMessage(), "The specified email already exists");
	}

	@Test
	public void TC_05_Register_Password_Less_Than_6_Chars() {
		homePage.clickToRegisterLink();
		registerPage = new RegisterPageObject(driver);
		
		registerPage.sendKeyToFirstNameTextBox("Automation");
		registerPage.sendKeyToLastNameTextBox("FC");
		registerPage.sendKeyToEmailTextBox("thao@gmail.com");
		registerPage.sendKeyToPasswordTextBox("1234");
		registerPage.sendKeyToConfirmPasswordTextBox("1234");
		
		registerPage.clickToRegisterButton();

		Assert.assertEquals(registerPage.getPasswordErrorMessage(), "Password must meet the following rules:\nmust have at least 6 characters");
	}

	@Test
	public void TC_06_Register_Invalid_Confirm_Password() {
		homePage.clickToRegisterLink();
		registerPage = new RegisterPageObject(driver);
		
		registerPage.sendKeyToFirstNameTextBox("Automation");
		registerPage.sendKeyToLastNameTextBox("FC");
		registerPage.sendKeyToEmailTextBox("thao@gmail.com");
		registerPage.sendKeyToPasswordTextBox("123456");
		registerPage.sendKeyToConfirmPasswordTextBox("123457");
		
		registerPage.clickToRegisterButton();
		
		Assert.assertEquals(registerPage.getConfirmPasswordErrorMessage(), "The password and confirmation password do not match.");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
