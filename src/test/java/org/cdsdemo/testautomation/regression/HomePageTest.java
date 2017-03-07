package org.cdsdemo.testautomation.regression;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class HomePageTest {
	
	public RemoteWebDriver driver;
	
	@Parameters({ "remoteDriverUrl" })
	@BeforeClass
	public void setUp(String remoteDriverURL) throws MalformedURLException {
		System.out.println("Remote Driver URL :: "+remoteDriverURL);
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		driver = new RemoteWebDriver(new URL(remoteDriverURL), capabilities);
		System.out.println("Driver initialized : "+driver);
		driver.manage().window().maximize();
	}
	
	@Parameters({ "appUrl" })
        @Test
	public void testDriverCurrentUrl(String appUrl) {
		System.out.println("*** Navigation to Application ***"+appUrl);
		driver.navigate().to(appUrl);
		String url = driver.getCurrentUrl();	
	  	System.out.println("Driver current url is "+url);
		Assert.assertTrue(url.contains("InsuranceManagement"), "Driver's Current Url doesn't match");
	}

	@Parameters({ "appUrl" })
	@Test
	public void testHomePageTitle(String appUrl) {
		driver.navigate().to(appUrl);
		String strPageTitle = driver.getTitle();
		System.out.println("*** Verifying page title ***");
		Assert.assertTrue(strPageTitle.equalsIgnoreCase("Insurance Management Portal"), "Page title doesn't match");
	}

        @Test
        public void testDriverPageSource() {
                Assert.assertTrue(driver.getPageSource().contains("INSURANCE MANAGEMENT PORTAL"), "Driver's page source doesn't match");
        }

	
	@AfterClass
	public void closeBrowser() {
		if (driver != null) {
			driver.quit();
		}
	}
}
