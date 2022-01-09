package com.nopcommerce.user;

import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.BaseTest;
import commons.PageGeneratorManager;
import pageObjects.user.UserAddressPageObject;
import pageObjects.user.UserCustomerInforPageObject;
import pageObjects.user.UserHomePageObject;
import pageObjects.user.UserLoginPageObject;
import pageObjects.user.UserOrdersPageObject;
import pageObjects.user.UserRegisterPageObject;
import pageObjects.user.UserRewardPointPageObject;

public class Level_07_Switch_Page extends BaseTest {
	private WebDriver driver;
	
	private UserHomePageObject homePage;
	private UserRegisterPageObject registerPage;
	private UserLoginPageObject loginPage;
	private UserCustomerInforPageObject customerInforPage;
	private UserOrdersPageObject ordersPage;
	private UserAddressPageObject addressPage;
	private UserRewardPointPageObject rewardPointPage;
	
		
	private String firstName, lastName, emailAddress, password;

	@Parameters({"browser","url"})
	@BeforeClass
	public void beforeClass(String browserName, String urlValue) {
		// 1 - Mở url ra --> mở ra trang Home Page (Business Page)
		driver = getBrowserDriver(browserName, urlValue);
		
		homePage = PageGeneratorManager.getUserHomePage(driver);
		
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
		
		loginPage.inputToEmailTextBox(emailAddress);
		loginPage.inputToPasswordTextBox(password);
		
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
	
	@Test
	public void TC_04_Switch_Page() {
		// Customer Infor Page -> Orders Page
		ordersPage = customerInforPage.openOrdersPage(driver);
		
		// Orders -> Reward Page
		rewardPointPage = ordersPage.openRewardPointPage(driver);
		
		// Reward -> Order
		ordersPage = rewardPointPage.openOrdersPage(driver);
		
		// Order -> Customer Infor
		customerInforPage = ordersPage.openCustomerInforPage(driver);
		
		// Customer Infor -> Address Page
		addressPage = customerInforPage.openAddressPage(driver);
		
		// Address Page -> Order page
		ordersPage = addressPage.openOrdersPage(driver);
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
