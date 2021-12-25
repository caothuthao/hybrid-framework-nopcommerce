package pageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePageObject extends BasePage {
	private WebDriver driver;
	
	public HomePageObject(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//a[@class='ico-register']")
	private WebElement registerLink;
	
	@FindBy(xpath="//a[@class='ico-login']")
	private WebElement logoutLink;
	
	public void clickToRegisterLink() {
		waitForElementClickable(driver, registerLink);
		clickToElement(registerLink);
	}

	public void clickToLoginLink() {
		waitForElementClickable(driver, logoutLink);
		clickToElement(logoutLink);
	}
}
