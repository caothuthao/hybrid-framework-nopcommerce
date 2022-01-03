package com.nopcommerce.user;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import commons.BasePage;
import commons.BaseTest;
import pageObjects.HomePageObject;
import pageObjects.LoginPageObject;
import pageObjects.RegisterPageObject;

public class Level_03_Base_Object_Pattern_02_Login extends BaseTest {
	private WebDriver driver;
	
	private HomePageObject homePage;
	private RegisterPageObject registerPage;
	private LoginPageObject loginPage;
	
	String projectPath = System.getProperty("user.dir");
	String existingEmail;
	String password;
	String notFoundEmail;
	String invalidEmail;
	
	WebDriverWait explicitWait;
	BasePage basePage;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		basePage = new BasePage();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		existingEmail = generateRandomEmail();
		password = "123456";
		notFoundEmail = generateRandomEmail();
		invalidEmail = "thao@123..";
		
		// 1 - Mở url ra --> mở ra trang Home Page (Business Page)
		driver.get("https://demo.nopcommerce.com/");
		
		// Khởi tạo page lên
		homePage = new HomePageObject(driver);
		homePage.clickToRegisterLink();
		registerPage = new RegisterPageObject(driver);
		
		registerPage.sendKeyToFirstNameTextBox("Cao");
		registerPage.sendKeyToLastNameTextBox("Thu Thao");
		registerPage.sendKeyToEmailTextBox(existingEmail);
		registerPage.sendKeyToPasswordTextBox(password);
		registerPage.sendKeyToConfirmPasswordTextBox(password);
		
		registerPage.clickToRegisterButton();
		Assert.assertEquals(registerPage.getRegisterSuccessMessage(), "Your registration completed");
		registerPage.clickToLogoutLink();
		
		// 3 - Từ trang Register --> Home Page
		homePage = new HomePageObject(driver);
	}

//	@Test
	public void TC_01_Login_Empty_Data() {
		// 2 - Từ Home Page click vào Login link mở ra trang Login Page (Business Page)
		homePage.clickToLoginLink();
		loginPage = new LoginPageObject(driver);	
		loginPage.clickToLoginButton();
		
		Assert.assertEquals(loginPage.getEmailErrorMessage(), "Please enter your email");
	}

//	@Test
	public void TC_02_Login_Invalid_Email() {
		homePage.clickToLoginLink();
		loginPage = new LoginPageObject(driver);
		
		loginPage.sendKeyToEmailTextBox(invalidEmail);
		loginPage.clickToLoginButton();
		
		Assert.assertEquals(loginPage.getEmailErrorMessage(), "Wrong email");
	}

//	@Test
	public void TC_03_Login_Email_Not_Found() {
		homePage.clickToLoginLink();
		loginPage = new LoginPageObject(driver);
		
		loginPage.sendKeyToEmailTextBox(notFoundEmail);
		loginPage.sendKeyToPasswordTextBox(password);
		
		loginPage.clickToLoginButton();
		
		Assert.assertEquals(loginPage.getLoginErrorMessage(), "Login was unsuccessful. Please correct the errors and try again.\nNo customer account found");
	}

//	@Test
	public void TC_04_Login_Existing_Email_Empty_Password() {
		homePage.clickToLoginLink();
		loginPage = new LoginPageObject(driver);
		
		loginPage.sendKeyToEmailTextBox(existingEmail);
		
		loginPage.clickToLoginButton();
		
		Assert.assertEquals(loginPage.getLoginErrorMessage(), "Login was unsuccessful. Please correct the errors and try again.\nThe credentials provided are incorrect");
	}

//	@Test
	public void TC_05_Login_Existing_Email_Incorrect_Password() {
		homePage.clickToLoginLink();
		loginPage = new LoginPageObject(driver);
		
		loginPage.sendKeyToEmailTextBox(existingEmail);
		loginPage.sendKeyToPasswordTextBox(password);
		
		loginPage.clickToLoginButton();

		Assert.assertEquals(loginPage.getLoginErrorMessage(), "Login was unsuccessful. Please correct the errors and try again.\nNo customer account found");
	}

	@Test
	public void TC_06_Login_Successful() {
		homePage.clickToLoginLink();
		loginPage = new LoginPageObject(driver);
		
		loginPage.sendKeyToEmailTextBox(existingEmail);
		loginPage.sendKeyToPasswordTextBox(password);
		
		loginPage.clickToLoginButton();
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public String generateRandomEmail() {
		Random random = new Random();
		return "thao" + random.nextInt(1000) + "@gmail.com";
	}

}
