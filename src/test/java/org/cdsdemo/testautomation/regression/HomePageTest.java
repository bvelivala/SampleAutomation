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
	
	@Parameters({ "remoteDriverUrl" })
	@BeforeClass
	public void setUp(String remoteDriverURL) throws MalformedURLException {
		System.out.println("Remote Driver URL :: "+remoteDriverURL);
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability("proxy", null);
		rdriver = new RemoteWebDriver(new URL(remoteDriverURL), capabilities);
		System.out.println("Remote Driver initialized : "+rdriver);
		rdriver.manage().window().maximize();
	}
	
	@Parameters({ "appUrl" })
        @Test
	public void testDriverCurrentUrl(String appUrl) {
		System.out.println("*** Navigation to Application ***"+appUrl);		
		rdriver.navigate().to(appUrl);
		String url = rdriver.getCurrentUrl();		
	  	System.out.println("Driver current url is "+url);
		Assert.assertTrue(url.contains("autoclaim"), "Driver's Current Url doesn't match");
	}

	@Parameters({ "appUrl" })
	@Test
	public void testHomePageTitle(String appUrl) {		
		rdriver.navigate().to(appUrl);
		String strPageTitle = rdriver.getTitle();				
		System.out.println("*** Verifying page title ***");
		Assert.assertTrue(strPageTitle.equalsIgnoreCase("Login Page"), "Page title doesn't match");
	}

        @Test
        public void testDriverPageSource() {
                Assert.assertTrue(rdriver.getPageSource().contains("Register User"), "Driver's page source doesn't match");		
        }

	
	@AfterClass
	public void closeBrowser() {		
		if (rdriver != null) {
			rdriver.quit();
		}
	}
}
