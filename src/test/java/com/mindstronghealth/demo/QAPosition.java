package com.mindstronghealth.demo;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import utilities.Constants;
import pages.CareersPage;
import pages.HomePage;

public class QAPosition {
	private WebDriver driver;
	private JavascriptExecutor jse;
	ExtentReports report;
	ExtentTest test;
//	HomePageFactory homePage;
	HomePage hp;
	CareersPage cp;

	@BeforeClass
	public void beforeClass() throws Exception {
		report = new ExtentReports(Constants.File_Path + "qavacancytest.html");
		test = report.startTest("Test QA Engineer position is open");

		System.setProperty("webdriver.gecko.driver", Constants.geckoLocation);
		driver = new FirefoxDriver();
		jse = (JavascriptExecutor) driver;
//		homePage = new HomePageFactory(driver, test);
		hp = new HomePage(driver, test);
		cp = new CareersPage(driver);
		test.log(LogStatus.INFO, "Browser Started");
		driver.manage().window().maximize();
		test.log(LogStatus.INFO, "Browser Maximized");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// Navigate to Home page
		driver.get(Constants.URL);
		test.log(LogStatus.INFO, "Mindstronghealth home page is opened");
	}

	@Test
	// Check if Software Engineer in Test is present on Careers page
	
	public void vacancy() throws Exception {
		// Set careerlink web element
		WebElement careerlink = hp.getCareersLink();
		
		// Scroll down to Careers link element
		jse.executeScript("arguments[0].scrollIntoView(true);", careerlink);
		
		// Click on Careers link element
		hp.clickCareersLink();

		// Switch to job list frame
		driver.switchTo().frame(0);
		
		// Set qaPosition web element
		WebElement qaPosition = cp.getQAEngineer();
		
		// Verify if 'Software Engineer in Test' is displayed
		if (qaPosition.isDisplayed() == true) {
			test.log(LogStatus.PASS, "QA Position is displayed");

		} else {
			test.log(LogStatus.FAIL, "QA Position is not displayed");
		}
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
		report.endTest(test);
		report.flush();
	}

}
