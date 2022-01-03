package com.nopcommerce.user;

import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.BaseTest;
import pageObjects.CustomerInforPageObject;
import pageObjects.HomePageObject;
import pageObjects.LoginPageObject;
import pageObjects.PageGeneratorManager;
import pageObjects.RegisterPageObject;

public class Level_06_Page_Generator_Manager_Part_III extends BaseTest {
	private WebDriver driver;
	
	private HomePageObject homePage;
	private RegisterPageObject registerPage;
	private LoginPageObject loginPage;
	private CustomerInforPageObject customerInforPage;
		
	private String firstName, lastName, emailAddress, password;

	@Parameters({"browser","url"})
	@BeforeClass
	public void beforeClass(String browserName, String urlValue) {
		// 1 - Mở url ra --> mở ra trang Home Page (Business Page)
		driver = getBrowserDriver(browserName, urlValue);
		
		// Chưa xử lý đoạn này được
		homePage = PageGeneratorManager.getHomePage(driver);
		
		firstName = "Cao";
		lastName = "Thu Thao";
		emailAddress = "thao" + generateRandomEmail();
		password = "123456";
	}

	@Test
	public void TC_01_User_Register_To_System() {
		// 2 - Từ Home Page click vào Register link mở ra trang Register Page (Business Page)
		registerPage = homePage.clickToRegisterLink();
		
		registerPage.sendKeyToFirstNameTextBox(firstName);
		registerPage.sendKeyToLastNameTextBox(lastName);
		registerPage.sendKeyToEmailTextBox(emailAddress);
		registerPage.sendKeyToPasswordTextBox(password);
		registerPage.sendKeyToConfirmPasswordTextBox(password);
		
		registerPage.clickToRegisterButton();
		
		Assert.assertEquals(registerPage.getRegisterSuccessMessage(), "Your registration completed");
		
		// 3 - Từ trang Register chuyển qua Home Page
		homePage = registerPage.clickToLogoutLink();
	}

	@Test
	public void TC_02_User_Login_To_System() {
		// 4 - Từ trang HomePage chuyển qua Login Page
		loginPage = homePage.clickToLoginLink();
		
		loginPage.sendKeyToEmailTextBox(emailAddress);
		loginPage.sendKeyToPasswordTextBox(password);
		
		// 5 - Từ trang Login Page chuyển qua Home Page
		homePage = loginPage.clickToLoginButton();
	}

	@Test
	public void TC_03_My_Account_Infor() {
		// 6 - Từ trang Home Page chuyển qua Customer Infor Page
		customerInforPage = homePage.clickToMyAccountLink();
		
		Assert.assertEquals(customerInforPage.getFirstNameTextboxValue(), firstName);
		Assert.assertEquals(customerInforPage.getLastNameTextboxValue(), lastName);
		Assert.assertEquals(customerInforPage.getEmailTextboxValue(), emailAddress);
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
