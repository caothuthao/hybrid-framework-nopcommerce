package com.nopcommerce.user;

import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.BaseTest;
import commons.GlobalContants;
import commons.PageGeneratorManager;
import pageObjects.admin.AdminDashboardPageObject;
import pageObjects.admin.AdminLoginPageObject;
import pageObjects.user.UserCustomerInforPageObject;
import pageObjects.user.UserHomePageObject;
import pageObjects.user.UserLoginPageObject;

public class Level_08_Switch_Role_User extends BaseTest {
	private WebDriver driver;
	private UserHomePageObject userHomePage;
	private UserLoginPageObject userLoginPage;
	private AdminLoginPageObject adminLoginPage;
	private AdminDashboardPageObject adminDashboardPage;
	private UserCustomerInforPageObject userCustomerInforPage;
	
	String userEmailAddress, userPassword, adminEmailAddress, adminPassword;

	@Parameters({"browser"})
	@BeforeClass
	public void beforeClass(String browserName) {
		// 1 - Quyền User mở url của trang User lên -> qua Home Page
		driver = getBrowserDriver(browserName, GlobalContants.USER_URL);	
		userHomePage = PageGeneratorManager.getUserHomePage(driver);
		
		userEmailAddress = "thaocao06012022@gmail.com";
		userPassword = "123456";
		
		adminEmailAddress = "admin@yourstore.com";
		adminPassword = "admin";
	}

	@Test
	public void Role_01_Switch_User_To_Admin() {
		// Home -> Login
		userLoginPage = userHomePage.clickToLoginLink();
		
		// Login -> ...	
		userHomePage = userLoginPage.loginAsUser(userEmailAddress, userPassword);
		
		// Home -> My Account Page
		userCustomerInforPage = userHomePage.clickToMyAccountLink();
		
		// My Account Page -> Logout link -> Home
		userHomePage = userCustomerInforPage.clickToUserLogoutLink(driver);
		
		// Any page -> Admin Url -> Admin Login Page
		userHomePage.openUrl(driver, GlobalContants.ADMIN_URL);
		adminLoginPage = PageGeneratorManager.getAdminLoginPage(driver);
		
		// Login -> Dashboard
		adminDashboardPage = adminLoginPage.loginAsAdmin(adminEmailAddress, adminPassword);
		
		// Dashboard -> Any Page -> Logout -> Admin Login
		adminLoginPage = adminDashboardPage.clickToAdminLogoutLink(driver);
	}

	@Test
	public void Role_02_Switch_Admin_To_User() {
		// Any Page Admin -> User Url -> Home (User)
		adminLoginPage.openUrl(driver, GlobalContants.USER_URL);
		userHomePage = PageGeneratorManager.getUserHomePage(driver); 
		
		// Home (User) -> Register/ Login/ Checkout/ Shopping/...
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
