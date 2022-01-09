package commons;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageObjects.admin.AdminLoginPageObject;
import pageObjects.user.UserAddressPageObject;
import pageObjects.user.UserCustomerInforPageObject;
import pageObjects.user.UserHomePageObject;
import pageObjects.user.UserOrdersPageObject;
import pageObjects.user.UserRewardPointPageObject;
import pageUIs.BasePageUI;

public class BasePage {
	
	private long longTimeout = 50;
	
	public static BasePage getBasePage() {
		return new BasePage();
	}
	
	/* Web Browser */
	public void openUrl(WebDriver driver, String url) {
		driver.get(url);
	}

	public String getPageTitle(WebDriver driver) {
		return driver.getTitle();
	}

	public String getPageUrl(WebDriver driver) {
		return driver.getCurrentUrl();
	}

	public String getPageSourceCode(WebDriver driver) {
		return driver.getPageSource();
	}

	public void backToPage(WebDriver driver) {
		driver.navigate().back();
	}

	public void refreshPage(WebDriver driver) {
		driver.navigate().refresh();
	}

	public void forward(WebDriver driver) {
		driver.navigate().forward();
	}

	public Alert waitForAlertPresence(WebDriver driver) {
		return new WebDriverWait(driver, longTimeout).until(ExpectedConditions.alertIsPresent());
	}

	public void acceptAlert(WebDriver driver) {
		waitForAlertPresence(driver).accept();
	}

	public void cancelAlert(WebDriver driver) {
		waitForAlertPresence(driver).dismiss();
	}

	public String getAlertText(WebDriver driver) {
		return waitForAlertPresence(driver).getText();
	}

	public void sendKeyToAlert(WebDriver driver, String value) {
		waitForAlertPresence(driver).sendKeys(value);
	}

	public void switchToWindowByID(String parentID, WebDriver driver) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String id : allWindows) {
			if (!id.equals(parentID)) {
				driver.switchTo().window(id);
			}
		}
	}

	public void switchToWindowByTitle(String expectedTitle, WebDriver driver) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String id : allWindows) {
			driver.switchTo().window(id);
			String windowTitle = driver.getTitle();
			if (windowTitle.equals(expectedTitle)) {
				break;
			}
		}
	}

	public void closeAllWindowsWithoutParent(String parentID, WebDriver driver) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String id : allWindows) {
			if (!id.equals(parentID)) {
				driver.switchTo().window(id);
				driver.close();
			}
		}
		driver.switchTo().window(parentID);
	}

	public void sleepInSecond(long timeoutInSecond) {
		try {
			Thread.sleep(timeoutInSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void sleepInMilisecond(long timeoutInMilisecond) {
		try {
			Thread.sleep(timeoutInMilisecond);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public String getLocatorValue(String valueUI) {
		String[] lstText = valueUI.split("=");
		String finalString = "";
		
		for (int i = 1; i<lstText.length-1; i++) {
			finalString = finalString.concat(lstText[i].trim()).concat("=");			
		}
		
		finalString = finalString + lstText[lstText.length-1];
		
		return finalString;
	}
	
	public String getLocatorType(String valueUI) {
		String[] lstText = valueUI.split("=");
		return lstText[0].trim().toLowerCase();
	}
	
	public By getByLocator (String locatorValueUI) {
		String locatorType = getLocatorType(locatorValueUI);
		String locatorExpression = getLocatorValue(locatorValueUI);
		
		if (locatorType.equals("id")) {
			return By.id(locatorExpression);
		} else if (locatorType.equals("name")){
			return By.name(locatorExpression);
		} else if (locatorType.equals("class")){
			return By.className(locatorExpression);
		} else if (locatorType.equals("css")){
			return By.cssSelector(locatorExpression);
		} else if (locatorType.equals("xpath")) {
			return By.xpath(locatorExpression);
		} else {
			throw new RuntimeException("Locator type is not supported!");
		}
	}
	
	public WebElement getWebElement(WebDriver driver, String locatorExpression) {
		return driver.findElement(getByLocator(locatorExpression));
	}
	
	public List<WebElement> getWebElements(WebDriver driver, String locatorExpression) {
		return driver.findElements(getByLocator(locatorExpression));
	}
	
	public void clickToElement(WebDriver driver, String locatorExpression) {
		getWebElement(driver, locatorExpression).click();
	}

	public void sendkeyToElement(WebDriver driver, String locatorExpression, String value) {
		getWebElement(driver, locatorExpression).clear();
		getWebElement(driver, locatorExpression).sendKeys(value);
	}

	public String getElementText(WebDriver driver, String locatorExpression) {
		return getWebElement(driver, locatorExpression).getText();
	}
	
	public void selectItemInDefaultDropdown (WebDriver driver, String locatorExpression, String itemText) {
		new Select(getWebElement(driver, locatorExpression)).selectByVisibleText(itemText);
	}
	
	public String selectSelectedTextInDefaultDropdown (WebDriver driver, String locatorExpression) {
		return new Select(getWebElement(driver, locatorExpression)).getFirstSelectedOption().getText();
	}
	
	public boolean isMultipleDefaultDropdown(WebDriver driver, String locatorExpression) {
		return new Select(getWebElement(driver, locatorExpression)).isMultiple();
	}
	
	public void selectItemInCustomDropdown (WebDriver driver, String parentlocatorExpression, String childlocatorExpression, String itemText) {
        driver.findElement(By.xpath(parentlocatorExpression)).click();
        sleepInSecond(1);

        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		List<WebElement> allItems = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(childlocatorExpression)));

        for (WebElement item : allItems) {
            if (item.getText().trim().equals(itemText)) {
                if (!item.isDisplayed()) {
                    JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
					jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
                    sleepInSecond(1);
                }
                item.click();
                break;
            }
        }
	}

	public String getElementAttribute (WebDriver driver, String locatorExpression, String attributeName) {
		return getWebElement(driver, locatorExpression).getAttribute(attributeName);
	}
	
	public String getCssValue (WebDriver driver, String locatorExpression, String propertyName) {
		return getWebElement(driver, locatorExpression).getCssValue(propertyName);
	}
	
	public void getHexaColorByRgbaColor(String rgbaColor) {
		Color.fromString(rgbaColor).asHex();
	}
	
	public int getElementsNumber(WebDriver driver, String locatorExpression) {
		return getWebElements(driver, locatorExpression).size();
	}
	
	public void checkToRadioButtonOrCheckbox (WebDriver driver, String locatorExpression) {
		if (!getWebElement(driver, locatorExpression).isSelected()) {
			getWebElement(driver, locatorExpression).click();
		}
	}
	
	public void uncheckToCheckbox (WebDriver driver, String locatorExpression) {
		if (getWebElement(driver, locatorExpression).isSelected()) {
			getWebElement(driver, locatorExpression).click();
		}
	}
	
	public boolean isElementSelected(WebDriver driver, String locatorExpression) {
		return getWebElement(driver, locatorExpression).isSelected();
	}
	
	public boolean isElementEnable(WebDriver driver, String locatorExpression) {
		return getWebElement(driver, locatorExpression).isEnabled();
	}
	
	public boolean isElementDisplay(WebDriver driver, String locatorExpression) {
		return getWebElement(driver, locatorExpression).isDisplayed();
	}
	
	// frame
	public void switchToFrame(WebDriver driver, String locatorExpression) {
		driver.switchTo().frame(getWebElement(driver, locatorExpression));
	}

	public void switchToDefaultContentPage(WebDriver driver) {
		driver.switchTo().defaultContent();
	}
	
	// action
	public void hoverMouseToElement(WebDriver driver, String locatorExpression) {
		new Actions(driver).moveToElement(getWebElement(driver, locatorExpression)).perform();
	}
	
	public void pressKeyboardToElement(WebDriver driver, String locatorExpression, Keys key) {
		new Actions(driver).sendKeys(getWebElement(driver, locatorExpression), key).perform();
	}

	// Javascript Excuter
	public Object executeForBrowser(WebDriver driver, String javaScript) {
		return ((JavascriptExecutor) driver).executeScript(javaScript);
    }

    public String getInnerText(WebDriver driver) {
        return (String) ((JavascriptExecutor) driver).executeScript("return document.documentElement.innerText;");
    }

    public boolean isExpectedTextInInnerText(WebDriver driver, String textExpected) {
        String textActual = (String) ((JavascriptExecutor) driver).executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0];");
        return textActual.equals(textExpected);
    }

    public void scrollToBottomPage(WebDriver driver) {
    	((JavascriptExecutor) driver).executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }

    public void navigateToUrlByJS(WebDriver driver, String url) {
    	((JavascriptExecutor) driver).executeScript("window.location = '" + url + "'");
    }

    public void highlightElement(WebDriver driver, String locatorExpression) {
        WebElement element = getWebElement(driver, locatorExpression);
        String originalStyle = element.getAttribute("style");
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", "border: 2px solid red; border-style: dashed;");
        sleepInSecond(1);
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
    }

    public void clickToElementByJS(WebDriver driver, String locatorExpression) {
    	((JavascriptExecutor) driver).executeScript("arguments[0].click();", getWebElement(driver, locatorExpression));
    }

    public void scrollToElement(WebDriver driver, String locatorExpression) {
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", getWebElement(driver, locatorExpression));
    }

    public void sendkeyToElementByJS(WebDriver driver, String locatorExpression, String value) {
    	((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('value', '" + value + "')", getWebElement(driver, locatorExpression));
    }

    public void removeAttributeInDOM(WebDriver driver, String locatorExpression, String attributeRemove) {
    	((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getWebElement(driver, locatorExpression));
    }
    
    public boolean areJQueryAndJSLoadedSuccess (WebDriver driver) {
    	WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
    	
    	ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					return true;
				}
			} 	
		};
		
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
			} 	
		};
		
		return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
    }

    public String getElementValidationMessage(WebDriver driver, String locatorExpression) {
        return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].validationMessage;", getWebElement(driver, locatorExpression));
    }

    public boolean isImageLoaded(WebDriver driver, String locatorExpression) {
        boolean status = (boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", getWebElement(driver, locatorExpression));
        return status;
    }

    // Wait
    public void waitForElementVisible (WebDriver driver, String locatorExpression) {
    	new WebDriverWait(driver, longTimeout).until(ExpectedConditions.visibilityOfElementLocated(getByLocator(locatorExpression)));
    }
    
    public void waitForElementClickable (WebDriver driver, String locatorExpression) {
    	new WebDriverWait(driver, longTimeout).until(ExpectedConditions.elementToBeClickable(getByLocator(locatorExpression)));
    }
    
    public void waitForElementInvisible (WebDriver driver, String locatorExpression) {
    	new WebDriverWait(driver, longTimeout).until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(locatorExpression)));
    }
    
    public void waitForAllElementVisible (WebDriver driver, String locatorExpression) {
    	new WebDriverWait(driver, longTimeout).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByLocator(locatorExpression)));
    }
    
    public void waitForAllElementInvisible (WebDriver driver, String locatorExpression) {
    	new WebDriverWait(driver, longTimeout).until(ExpectedConditions.invisibilityOfAllElements(getWebElements(driver, locatorExpression)));
    }
   
    /* User Site */
    public UserOrdersPageObject openOrdersPage(WebDriver driver) {
    	waitForElementClickable(driver, BasePageUI.ORDERS_PAGE_LINK);
    	clickToElement(driver, BasePageUI.ORDERS_PAGE_LINK);
    	return PageGeneratorManager.getUserOrdersPage(driver);
    }
    
    public UserCustomerInforPageObject openCustomerInforPage(WebDriver driver) {
    	waitForElementClickable(driver, BasePageUI.CUSTOMER_INFOR_PAGE_LINK);
    	clickToElement(driver, BasePageUI.CUSTOMER_INFOR_PAGE_LINK);
    	return PageGeneratorManager.getUserCustomerInforPage(driver);
    }
    
    public UserAddressPageObject openAddressPage(WebDriver driver) {
    	waitForElementClickable(driver, BasePageUI.ADDRESS_PAGE_LINK);
    	clickToElement(driver, BasePageUI.ADDRESS_PAGE_LINK);
    	return PageGeneratorManager.getUserAddressPage(driver);
    }
    
    public UserRewardPointPageObject openRewardPointPage(WebDriver driver) {
    	waitForElementClickable(driver, BasePageUI.REWARD_POINT_PAGE_LINK);
    	clickToElement(driver, BasePageUI.REWARD_POINT_PAGE_LINK);
    	return PageGeneratorManager.getUserRewardPointPage(driver);
    }
    
    public UserHomePageObject clickToUserLogoutLink(WebDriver driver) {
    	waitForElementClickable(driver, BasePageUI.USER_LOGOUT_LINK);
    	clickToElement(driver, BasePageUI.USER_LOGOUT_LINK);
    	return PageGeneratorManager.getUserHomePage(driver);
    }
    
    /* Admin Site */
    public AdminLoginPageObject clickToAdminLogoutLink(WebDriver driver) {
    	waitForElementClickable(driver, BasePageUI.ADMIN_LOGOUT_LINK);
    	clickToElement(driver, BasePageUI.ADMIN_LOGOUT_LINK);
    	return PageGeneratorManager.getAdminLoginPage(driver);
    }
}

