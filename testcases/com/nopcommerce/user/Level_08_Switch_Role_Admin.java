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
import pageObjects.admin.AdminLoginPageObject;
import pageObjects.user.UserHomePageObject;
import pageObjects.user.UserLoginPageObject;
import pageObjects.user.UserRegisterPageObject;

public class Level_08_Switch_Role_Admin extends BaseTest {
	private WebDriver driver;
	private UserHomePageObject userHomePage;
	private AdminLoginPageObject adminLoginPage;

	@Parameters({"browser"})
	@BeforeClass
	public void beforeClass(String browserName) {
		// 2 - Quyền Admin mở url của trang Admin lên -> qua Login Page
		driver = getBrowserDriver(browserName, GlobalContants.ADMIN_URL);	
		userHomePage = PageGeneratorManager.getUserHomePage(driver);
	}

	@Test
	public void Role_01_Switch_User_To_Admin() {

	}

	@Test
	public void Role_02_Switch_Admin_To_User() {

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
