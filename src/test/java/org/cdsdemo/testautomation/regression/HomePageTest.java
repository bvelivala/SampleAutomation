package org.cdsdemo.testautomation.regression;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class HomePageTest {
	
	public RemoteWebDriver rdriver;
	public WebDriver driver;
	public boolean isRemoteDriver = true;
	
	@Parameters({ "remoteDriverUrl", "isRemote" })
	@BeforeClass
	public void setUp(String remoteDriverURL) throws MalformedURLException {
		System.out.println("Remote Driver URL :: "+remoteDriverURL);
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		
		if ("Y".equalsIgnoreCase(isRemoteDriver)) {
			rdriver = new RemoteWebDriver(new URL(remoteDriverURL), capabilities);
			System.out.println("Remote Driver initialized : "+driver);
			rdriver.manage().window().maximize();
		} else {
			driver = new RemoteWebDriver(new URL(remoteDriverURL), capabilities);
			System.out.println("Web Driver initialized : "+driver);
			driver.manage().window().maximize();
		}
	}
	
	@Parameters({ "appUrl" })
        @Test
	public void testDriverCurrentUrl(String appUrl) {
		System.out.println("*** Navigation to Application ***"+appUrl);
		String url = "";
		
		if (isRemoteDriver) {
			rdriver.navigate().to(appUrl);
			url = rdriver.getCurrentUrl();
		} else {
			driver.navigate().to(appUrl);
			url = driver.getCurrentUrl();
		}
		
	  	System.out.println("Driver current url is "+url);
		Assert.assertTrue(url.contains("autoclaim"), "Driver's Current Url doesn't match");
	}

	@Parameters({ "appUrl" })
	@Test
	public void testHomePageTitle(String appUrl) {
		String strPageTitle = "";
		
		if (isRemoteDriver) {
			rdriver.navigate().to(appUrl);
			strPageTitle = rdriver.getTitle();
		} else {
			driver.navigate().to(appUrl);
			strPageTitle = driver.getTitle();
		}
		
		System.out.println("*** Verifying page title ***");
		Assert.assertTrue(strPageTitle.equalsIgnoreCase("Login Page"), "Page title doesn't match");
	}

        @Test
        public void testDriverPageSource() {
		if (isRemoteDriver){ 
                	Assert.assertTrue(rdriver.getPageSource().contains("Register User"), "Driver's page source doesn't match");
		} else {
			Assert.assertTrue(driver.getPageSource().contains("Register User"), "Driver's page source doesn't match");
		}
        }

	
	@AfterClass
	public void closeBrowser() {
		if (driver != null) {
			driver.quit();
		}
		if (rdriver != null) {
			rdriver.quit();
		}
	}
}
