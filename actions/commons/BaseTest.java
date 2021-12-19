package commons;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	private WebDriver driver;

	protected WebDriver getBrowserDriver(String browserName, String urlValue) {

		BrowserList browers = BrowserList.valueOf(browserName.toUpperCase());
		
		if (browers == BrowserList.FIREFOX) {
			WebDriverManager.firefoxdriver().setup();
			// Cach dung version: Chi khi KH yeu cau, con lai ko dung --> dung ban moi nhat
			// Browser Version
//			WebDriverManager.firefoxdriver().browserVersion("95.0").setup();
			// Browser Driver Version
//			WebDriverManager.firefoxdriver().driverVersion("0.30.0").setup();
			driver = new FirefoxDriver();
		} else if (browers == BrowserList.CHROME) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browers == BrowserList.EDGE) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		} else {
			throw new RuntimeException("This browser is not supported!");
		}

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get(urlValue);
		driver.manage().window().maximize();
		
		return driver;
	}
}
