package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;

public class AddressPageObject extends BasePage {
	private WebDriver driver;
	
	public AddressPageObject (WebDriver driver) {
		this.driver = driver;
	}
}
