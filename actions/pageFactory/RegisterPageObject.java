package pageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterPageObject extends BasePage {
	private WebDriver driver;
	
	public RegisterPageObject(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@CacheLookup
	@FindBy(id="register-button")
	private WebElement registerButton;
	
	@FindBy(xpath="//span[@id='FirstName-error']")
	private WebElement firstNameErrorMsg;
	
	@FindBy(xpath="//span[@id='LastName-error']")
	private WebElement lastNameErrorMsg;
	
	@FindBy(xpath="//span[@id='Email-error']")
	private WebElement emailErrorMsg;
	
	@FindBy(xpath="//span[@id='Password-error']")
	private WebElement passwordErrorMsg;
	
	@FindBy(xpath="//span[@id='ConfirmPassword-error']")
	private WebElement confirmPasswordErrorMsg;
	
	@FindBy(xpath="//div[contains(@class,'validation-summary-errors')]//li")
	private WebElement existedEmailErrorMsg;
	
	@FindBy(xpath="//div[@class='result']")
	private WebElement registerSuccessMsg;
	
	@FindBy(xpath="//input[@id='FirstName']")
	private WebElement firstNameTextbox;
	
	@FindBy(xpath="//input[@id='LastName']")
	private WebElement lastNameTextbox;
	
	@FindBy(xpath="//input[@id='Email']")
	private WebElement emailTextbox;
	
	@FindBy(xpath="//input[@id='Password']")
	private WebElement passwordTextbox;
	
	@FindBy(xpath="//input[@id='ConfirmPassword']")
	private WebElement confirmPasswordTextbox;
	
	@FindBy(xpath="//a[@class='ico-logout']")
	private WebElement logoutLink;
	
	@FindBy(xpath="//a[@class='ico-register']")
	private WebElement registerLink;
	
	public void clickToRegisterButton() {
		waitForElementClickable(driver, registerButton);
		clickToElement(registerButton);
	}

	public String getFirstNameErrorMessage() {
		waitForElementVisible(driver, firstNameErrorMsg);
		return getElementText(firstNameErrorMsg);
	}

	public String getLastNameErrorMessage() {
		waitForElementVisible(driver, lastNameErrorMsg);
		return getElementText(lastNameErrorMsg);
	}

	public String getEmailErrorMessage() {
		waitForElementVisible(driver, emailErrorMsg);
		return getElementText(emailErrorMsg);
	}
	
	public String getExistedEmailMessage() {
		waitForElementVisible(driver, existedEmailErrorMsg);
		return getElementText(existedEmailErrorMsg);
	}

	public String getPasswordErrorMessage() {
		waitForElementVisible(driver, passwordErrorMsg);
		return getElementText(passwordErrorMsg);
	}

	public String getConfirmPasswordErrorMessage() {
		waitForElementVisible(driver, confirmPasswordErrorMsg);
		return getElementText(confirmPasswordErrorMsg);
	}

	public void sendKeyToFirstNameTextBox(String firstName) {
		waitForElementVisible(driver, firstNameTextbox);
		sendkeyToElementByJS(driver, firstNameTextbox, firstName);
	}

	public void sendKeyToLastNameTextBox(String lastName) {
		waitForElementVisible(driver, lastNameTextbox);
		sendkeyToElementByJS(driver, lastNameTextbox, lastName);
	}

	public void sendKeyToEmailTextBox(String emailAdress) {
		waitForElementVisible(driver, emailTextbox);
		sendkeyToElementByJS(driver, emailTextbox, emailAdress);
	}

	public void sendKeyToPasswordTextBox(String password) {
		waitForElementVisible(driver, passwordTextbox);
		sendkeyToElementByJS(driver, passwordTextbox, password);
	}

	public void sendKeyToConfirmPasswordTextBox(String confirmPassword) {
		waitForElementVisible(driver, confirmPasswordTextbox);
		sendkeyToElementByJS(driver, confirmPasswordTextbox, confirmPassword);
	}

	public String getEmailSuccessMessage() {
		waitForElementVisible(driver, registerSuccessMsg);
		return getElementText(registerSuccessMsg);
	}

	public void clickToLogoutLink() {
		waitForElementVisible(driver, logoutLink);
		clickToElementByJS(driver, logoutLink);
	}
}
