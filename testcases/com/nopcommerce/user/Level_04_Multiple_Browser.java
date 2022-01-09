package com.nopcommerce.user;

import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.BasePage;
import commons.BaseTest;
import pageObjects.user.UserHomePageObject;
import pageObjects.user.UserRegisterPageObject;

public class Level_04_Multiple_Browser extends BaseTest {
	private WebDriver driver;
	
	private UserHomePageObject homePage;
	private UserRegisterPageObject registerPage;
		
	String existingEmail;
	String password;
	String notFoundEmail;
	String invalidEmail;
	
	Select select;
	WebDriverWait explicitWait;
	BasePage basePage;

	@Parameters({"browser","url"})
	@BeforeClass
	public void beforeClass(String browserName, String urlValue) {
		driver = getBrowserDriver(browserName, urlValue);
		
		basePage = new BasePage();
		
		existingEmail = generateRandomEmail();
		password = "123456";
		notFoundEmail = generateRandomEmail();
		invalidEmail = "thao@123..";
		
		// Khởi tạo page lên
		homePage = new UserHomePageObject(driver);
	}

	@Test
	public void TC_01_Register_Empty_Data() {
		// 2 - Từ Home Page click vào Register link mở ra trang Register Page (Business Page)
		homePage.clickToRegisterLink();
		registerPage = new UserRegisterPageObject(driver);
		
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
		registerPage = new UserRegisterPageObject(driver);
		
		registerPage.sendKeyToFirstNameTextBox("Cao");
		registerPage.sendKeyToLastNameTextBox("Thu Thao");
		registerPage.sendKeyToEmailTextBox(invalidEmail);
		registerPage.sendKeyToPasswordTextBox("123456");
		registerPage.sendKeyToConfirmPasswordTextBox("123456");
		
		registerPage.clickToRegisterButton();
		
		Assert.assertEquals(registerPage.getEmailErrorMessage(), "Wrong email");
	}

	@Test
	public void TC_03_Register_Success() {
		homePage.clickToRegisterLink();
		registerPage = new UserRegisterPageObject(driver);
		
		registerPage.sendKeyToFirstNameTextBox("Cao");
		registerPage.sendKeyToLastNameTextBox("Thu Thao");
		registerPage.sendKeyToEmailTextBox(existingEmail);
		registerPage.sendKeyToPasswordTextBox("123456");
		registerPage.sendKeyToConfirmPasswordTextBox("123456");
		
		registerPage.clickToRegisterButton();
		
		Assert.assertEquals(registerPage.getRegisterSuccessMessage(), "Your registration completed");

		registerPage.clickToLogoutLink();
		
		// 3 - Từ trang Register --> Home Page
		homePage = new UserHomePageObject(driver);
	}

	@Test
	public void TC_04_Register_Existing_Email() {
		homePage.clickToRegisterLink();
		registerPage = new UserRegisterPageObject(driver);
		
		registerPage.sendKeyToFirstNameTextBox("Automation");
		registerPage.sendKeyToLastNameTextBox("FC");
		registerPage.sendKeyToEmailTextBox(existingEmail);
		registerPage.sendKeyToPasswordTextBox("123456");
		registerPage.sendKeyToConfirmPasswordTextBox("123456");
		
		registerPage.clickToRegisterButton();
		
		Assert.assertEquals(registerPage.getExistedEmailMessage(), "The specified email already exists");
	}

	@Test
	public void TC_05_Register_Password_Less_Than_6_Chars() {
		homePage.clickToRegisterLink();
		registerPage = new UserRegisterPageObject(driver);
		
		registerPage.sendKeyToFirstNameTextBox("Automation");
		registerPage.sendKeyToLastNameTextBox("FC");
		registerPage.sendKeyToEmailTextBox(notFoundEmail);
		registerPage.sendKeyToPasswordTextBox("1234");
		registerPage.sendKeyToConfirmPasswordTextBox("1234");
		
		registerPage.clickToRegisterButton();

		Assert.assertEquals(registerPage.getPasswordErrorMessage(), "Password must meet the following rules:\nmust have at least 6 characters");
	}

	@Test
	public void TC_06_Register_Invalid_Confirm_Password() {
		homePage.clickToRegisterLink();
		registerPage = new UserRegisterPageObject(driver);
		
		registerPage.sendKeyToFirstNameTextBox("Automation");
		registerPage.sendKeyToLastNameTextBox("FC");
		registerPage.sendKeyToEmailTextBox(notFoundEmail);
		registerPage.sendKeyToPasswordTextBox("123456");
		registerPage.sendKeyToConfirmPasswordTextBox("123457");
		
		registerPage.clickToRegisterButton();
		
		Assert.assertEquals(registerPage.getConfirmPasswordErrorMessage(), "The password and confirmation password do not match.");
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
