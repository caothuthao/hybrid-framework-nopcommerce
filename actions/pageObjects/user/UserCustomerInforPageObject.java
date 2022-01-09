package pageObjects.user;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import pageUIs.UserCustomerInforPageUI;

public class UserCustomerInforPageObject extends BasePage {
	private WebDriver driver;
	
	public UserCustomerInforPageObject(WebDriver driver) {
		this.driver = driver;
	}

	public String getFirstNameTextboxValue() {
		waitForElementVisible(driver, UserCustomerInforPageUI.FIRST_NAME_TEXTBOX);
		return getElementAttribute(driver, UserCustomerInforPageUI.FIRST_NAME_TEXTBOX, "value");
	}

	public String getLastNameTextboxValue() {
		waitForElementVisible(driver, UserCustomerInforPageUI.LAST_NAME_TEXTBOX);
		return getElementAttribute(driver, UserCustomerInforPageUI.LAST_NAME_TEXTBOX, "value");
	}

	public String getEmailTextboxValue() {
		waitForElementVisible(driver, UserCustomerInforPageUI.EMAIL_TEXTBOX);
		return getElementAttribute(driver, UserCustomerInforPageUI.EMAIL_TEXTBOX, "value");
	}
	
	
}
