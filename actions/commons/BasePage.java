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

import pageObjects.AddressPageObject;
import pageObjects.CustomerInforPageObject;
import pageObjects.OrdersPageObject;
import pageObjects.PageGeneratorManager;
import pageObjects.RewardPointPageObject;
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

	public By getByXpath (String xpathExpression) {
		return By.xpath(xpathExpression);
	}
	
	public WebElement getWebElement(WebDriver driver, String xpathExpression) {
		return driver.findElement(getByXpath(xpathExpression));
	}
	
	public List<WebElement> getWebElements(WebDriver driver, String xpathExpression) {
		return driver.findElements(getByXpath(xpathExpression));
	}
	
	public void clickToElement(WebDriver driver, String xpathExpression) {
		getWebElement(driver, xpathExpression).click();
	}

	public void sendkeyToElement(WebDriver driver, String xpathExpression, String value) {
		getWebElement(driver, xpathExpression).clear();
		getWebElement(driver, xpathExpression).sendKeys(value);
	}

	public String getElementText(WebDriver driver, String xpathExpression) {
		return getWebElement(driver, xpathExpression).getText();
	}
	
	public void selectItemInDefaultDropdown (WebDriver driver, String xpathExpression, String itemText) {
		new Select(getWebElement(driver, xpathExpression)).selectByVisibleText(itemText);
	}
	
	public String selectSelectedTextInDefaultDropdown (WebDriver driver, String xpathExpression) {
		return new Select(getWebElement(driver, xpathExpression)).getFirstSelectedOption().getText();
	}
	
	public boolean isMultipleDefaultDropdown(WebDriver driver, String xpathExpression) {
		return new Select(getWebElement(driver, xpathExpression)).isMultiple();
	}
	
	public void selectItemInCustomDropdown (WebDriver driver, String parentxpathExpression, String childxpathExpression, String itemText) {
        driver.findElement(By.xpath(parentxpathExpression)).click();
        sleepInSecond(1);

        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		List<WebElement> allItems = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(childxpathExpression)));

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

	public String getElementAttribute (WebDriver driver, String xpathExpression, String attributeName) {
		return getWebElement(driver, xpathExpression).getAttribute(attributeName);
	}
	
	public String getCssValue (WebDriver driver, String xpathExpression, String propertyName) {
		return getWebElement(driver, xpathExpression).getCssValue(propertyName);
	}
	
	public void getHexaColorByRgbaColor(String rgbaColor) {
		Color.fromString(rgbaColor).asHex();
	}
	
	public int getElementsNumber(WebDriver driver, String xpathExpression) {
		return getWebElements(driver, xpathExpression).size();
	}
	
	public void checkToRadioButtonOrCheckbox (WebDriver driver, String xpathExpression) {
		if (!getWebElement(driver, xpathExpression).isSelected()) {
			getWebElement(driver, xpathExpression).click();
		}
	}
	
	public void uncheckToCheckbox (WebDriver driver, String xpathExpression) {
		if (getWebElement(driver, xpathExpression).isSelected()) {
			getWebElement(driver, xpathExpression).click();
		}
	}
	
	public boolean isElementSelected(WebDriver driver, String xpathExpression) {
		return getWebElement(driver, xpathExpression).isSelected();
	}
	
	public boolean isElementEnable(WebDriver driver, String xpathExpression) {
		return getWebElement(driver, xpathExpression).isEnabled();
	}
	
	public boolean isElementDisplay(WebDriver driver, String xpathExpression) {
		return getWebElement(driver, xpathExpression).isDisplayed();
	}
	
	// frame
	public void switchToFrame(WebDriver driver, String xpathExpression) {
		driver.switchTo().frame(getWebElement(driver, xpathExpression));
	}

	public void switchToDefaultContentPage(WebDriver driver) {
		driver.switchTo().defaultContent();
	}
	
	// action
	public void hoverMouseToElement(WebDriver driver, String xpathExpression) {
		new Actions(driver).moveToElement(getWebElement(driver, xpathExpression)).perform();
	}
	
	public void pressKeyboardToElement(WebDriver driver, String xpathExpression, Keys key) {
		new Actions(driver).sendKeys(getWebElement(driver, xpathExpression), key).perform();
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

    public void highlightElement(WebDriver driver, String xpathExpression) {
        WebElement element = getWebElement(driver, xpathExpression);
        String originalStyle = element.getAttribute("style");
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", "border: 2px solid red; border-style: dashed;");
        sleepInSecond(1);
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
    }

    public void clickToElementByJS(WebDriver driver, String xpathExpression) {
    	((JavascriptExecutor) driver).executeScript("arguments[0].click();", getWebElement(driver, xpathExpression));
    }

    public void scrollToElement(WebDriver driver, String xpathExpression) {
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", getWebElement(driver, xpathExpression));
    }

    public void sendkeyToElementByJS(WebDriver driver, String xpathExpression, String value) {
    	((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('value', '" + value + "')", getWebElement(driver, xpathExpression));
    }

    public void removeAttributeInDOM(WebDriver driver, String xpathExpression, String attributeRemove) {
    	((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getWebElement(driver, xpathExpression));
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

    public String getElementValidationMessage(WebDriver driver, String xpathExpression) {
        return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].validationMessage;", getWebElement(driver, xpathExpression));
    }

    public boolean isImageLoaded(WebDriver driver, String xpathExpression) {
        boolean status = (boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", getWebElement(driver, xpathExpression));
        return status;
    }

    // Wait
    public void waitForElementVisible (WebDriver driver, String xpathExpression) {
    	new WebDriverWait(driver, longTimeout).until(ExpectedConditions.visibilityOfElementLocated(getByXpath(xpathExpression)));
    }
    
    public void waitForElementClickable (WebDriver driver, String xpathExpression) {
    	new WebDriverWait(driver, longTimeout).until(ExpectedConditions.elementToBeClickable(getByXpath(xpathExpression)));
    }
    
    public void waitForElementInvisible (WebDriver driver, String xpathExpression) {
    	new WebDriverWait(driver, longTimeout).until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(xpathExpression)));
    }
    
    public void waitForAllElementVisible (WebDriver driver, String xpathExpression) {
    	new WebDriverWait(driver, longTimeout).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByXpath(xpathExpression)));
    }
    
    public void waitForAllElementInvisible (WebDriver driver, String xpathExpression) {
    	new WebDriverWait(driver, longTimeout).until(ExpectedConditions.invisibilityOfAllElements(getWebElements(driver, xpathExpression)));
    }
   
    public OrdersPageObject openOrdersPage(WebDriver driver) {
    	waitForElementClickable(driver, BasePageUI.ORDERS_PAGE_LINK);
    	clickToElement(driver, BasePageUI.ORDERS_PAGE_LINK);
    	return PageGeneratorManager.getOrdersPage(driver);
    }
    
    public CustomerInforPageObject openCustomerInforPage(WebDriver driver) {
    	waitForElementClickable(driver, BasePageUI.CUSTOMER_INFOR_PAGE_LINK);
    	clickToElement(driver, BasePageUI.CUSTOMER_INFOR_PAGE_LINK);
    	return PageGeneratorManager.getCustomerInforPage(driver);
    }
    
    public AddressPageObject openAddressPage(WebDriver driver) {
    	waitForElementClickable(driver, BasePageUI.ADDRESS_PAGE_LINK);
    	clickToElement(driver, BasePageUI.ADDRESS_PAGE_LINK);
    	return PageGeneratorManager.getAddressPage(driver);
    }
    
    public RewardPointPageObject openRewardPointPage(WebDriver driver) {
    	waitForElementClickable(driver, BasePageUI.REWARD_POINT_PAGE_LINK);
    	clickToElement(driver, BasePageUI.REWARD_POINT_PAGE_LINK);
    	return PageGeneratorManager.getRewardPointPage(driver);
    }
}

