package pageObjects.user;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.PageGeneratorManager;
import pageUIs.UserLoginPageUI;

public class UserLoginPageObject extends BasePage {
	private WebDriver driver;
	
	public UserLoginPageObject (WebDriver driver) {
		this.driver = driver;
	}

	public UserHomePageObject clickToLoginButton() {
		waitForElementClickable(driver, UserLoginPageUI.LOGIN_BUTTON);
		clickToElement(driver, UserLoginPageUI.LOGIN_BUTTON);
		return PageGeneratorManager.getUserHomePage(driver);
	}

	public String getEmailErrorMessage() {
		waitForAllElementVisible(driver, UserLoginPageUI.EMAIL_ERROR_MESSAGE);
		return getElementText(driver, UserLoginPageUI.EMAIL_ERROR_MESSAGE);
	}

	public void inputToEmailTextBox(String emailAddress) {
		waitForAllElementVisible(driver, UserLoginPageUI.EMAIL_TEXTBOX);
		sendkeyToElement(driver, UserLoginPageUI.EMAIL_TEXTBOX, emailAddress);
	}

	public void inputToPasswordTextBox(String password) {
		waitForAllElementVisible(driver, UserLoginPageUI.PASSWORD_TEXTBOX);
		sendkeyToElement(driver, UserLoginPageUI.PASSWORD_TEXTBOX, password);
	}

	public String getLoginErrorMessage() {
		waitForAllElementVisible(driver, UserLoginPageUI.LOGIN_ERROR_MESSAGE);
		return getElementText(driver, UserLoginPageUI.LOGIN_ERROR_MESSAGE);
	}
	
	public UserHomePageObject loginAsUser(String emailAddress, String password) {
		inputToEmailTextBox(emailAddress);
		inputToPasswordTextBox(password);
		return clickToLoginButton();
	}
}
