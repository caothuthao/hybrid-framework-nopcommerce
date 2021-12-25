package pageFactory;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
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

public class BasePage {
	
	private long longTimeout = 50;
	
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
	
	public void clickToElement(WebElement element) {
		element.click();
	}

	public void sendkeyToElement(WebElement element, String value) {
		element.clear();
		element.sendKeys(value);
	}

	public String getElementText(WebElement element) {
		return element.getText();
	}
	
	public void selectItemInDefaultDropdown (WebElement element, String itemText) {
		new Select(element).selectByVisibleText(itemText);
	}
	
	public String selectSelectedTextInDefaultDropdown (WebElement element) {
		return new Select(element).getFirstSelectedOption().getText();
	}
	
	public boolean isMultipleDefaultDropdown(WebElement element) {
		return new Select(element).isMultiple();
	}
	
//	public void selectItemInCustomDropdown (WebDriver driver, WebElement parentElement, WebElement childElement, String itemText) {
//		parentElement.click();
//        sleepInSecond(1);
//
//        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
//		List<WebElement> allItems = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(childxpathExpression)));
//
//        for (WebElement item : allItems) {
//            if (item.getText().trim().equals(itemText)) {
//                if (!item.isDisplayed()) {
//                    JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
//					jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
//                    sleepInSecond(1);
//                }
//                item.click();
//                break;
//            }
//        }
//	}

	public String getElementAttribute (WebElement element, String attributeName) {
		return element.getAttribute(attributeName);
	}
	
	public String getCssValue (WebElement element, String propertyName) {
		return element.getCssValue(propertyName);
	}
	
	public void getHexaColorByRgbaColor(String rgbaColor) {
		Color.fromString(rgbaColor).asHex();
	}
	
	public int getElementsNumber(List<WebElement> elements) {
		return elements.size();
	}
	
	public void checkToRadioButtonOrCheckbox (WebElement element) {
		if (!element.isSelected()) {
			element.click();
		}
	}
	
	public void uncheckToCheckbox (WebElement element) {
		if (element.isSelected()) {
			element.click();
		}
	}
	
	public boolean isElementSelected(WebElement element) {
		return element.isSelected();
	}
	
	public boolean isElementEnable(WebElement element) {
		return element.isEnabled();
	}
	
	public boolean isElementDisplay(WebElement element) {
		return element.isDisplayed();
	}
	
	// frame
	public void switchToFrame(WebDriver driver, WebElement element) {
		driver.switchTo().frame(element);
	}

	public void switchToDefaultContentPage(WebDriver driver) {
		driver.switchTo().defaultContent();
	}
	
	// action
	public void hoverMouseToElement(WebDriver driver, WebElement element) {
		new Actions(driver).moveToElement(element).perform();
	}
	
	public void pressKeyboardToElement(WebDriver driver, WebElement element, Keys key) {
		new Actions(driver).sendKeys(element, key).perform();
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

    public void highlightElement(WebDriver driver, WebElement element) {
        String originalStyle = element.getAttribute("style");
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", "border: 2px solid red; border-style: dashed;");
        sleepInSecond(1);
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
    }

    public void clickToElementByJS(WebDriver driver, WebElement element) {
    	((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void scrollToElement(WebDriver driver, WebElement element) {
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void sendkeyToElementByJS(WebDriver driver, WebElement element, String value) {
    	((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('value', '" + value + "')", element);
    }

    public void removeAttributeInDOM(WebDriver driver, WebElement element, String attributeRemove) {
    	((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", element);
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

    public String getElementValidationMessage(WebDriver driver, WebElement element) {
        return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].validationMessage;", element);
    }

    public boolean isImageLoaded(WebDriver driver, WebElement element) {
        boolean status = (boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", element);
        return status;
    }

    // Wait  
    public void waitForElementVisible (WebDriver driver, WebElement element) {
    	new WebDriverWait(driver, longTimeout).until(ExpectedConditions.visibilityOf(element));
    }
    
    public void waitForElementClickable (WebDriver driver, WebElement element) {
    	new WebDriverWait(driver, longTimeout).until(ExpectedConditions.elementToBeClickable(element));
    }
    
    public void waitForElementInvisible (WebDriver driver, WebElement element) {
    	new WebDriverWait(driver, longTimeout).until(ExpectedConditions.invisibilityOf(element));
    }
    
    public void waitForAllElementVisible (WebDriver driver, WebElement element) {
    	new WebDriverWait(driver, longTimeout).until(ExpectedConditions.visibilityOf(element));
    }
    
    public void waitForAllElementInvisible (WebDriver driver, List<WebElement> elements) {
    	new WebDriverWait(driver, longTimeout).until(ExpectedConditions.invisibilityOfAllElements(elements));
    }
}