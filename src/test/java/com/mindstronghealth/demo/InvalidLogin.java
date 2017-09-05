package com.mindstronghealth.demo;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.AfterClass;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import pages.HomePage;
import pages.LoginPage;
import utilities.Constants;
import utilities.Screenshots;

import java.io.FileInputStream;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;

public class InvalidLogin {
	private WebDriver driver;
	ExtentReports report;
	ExtentTest test;
	HomePage hp;
	LoginPage lp;
	XSSFWorkbook workbook;
	XSSFSheet sheet;
	XSSFCell cell;

	@Parameters("browserType")
	@BeforeClass
	public void beforeClass(String browser) {
		report = new ExtentReports(Constants.File_Path + "invalidlogin.html");
		test = report.startTest("Verify error is present");

		if (browser.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", Constants.geckoLocation);
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			test.log(LogStatus.INFO, "Browser Maximized");

		} else if (browser.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", Constants.chromeLocation);
			driver = new ChromeDriver();
			driver.manage().window().setSize(new org.openqa.selenium.Dimension(1440, 900));
			driver.manage().window().maximize();
			test.log(LogStatus.INFO, "Browser Maximized");

		} else if (browser.equals("opera")) {
			System.setProperty("webdriver.opera.driver", Constants.operaLocation);
			driver = new OperaDriver();
			driver.manage().window().maximize();
			test.log(LogStatus.INFO, "Browser Maximized");

		}

		hp = new HomePage(driver, test);
		lp = new LoginPage(driver, test);
		test.log(LogStatus.INFO, "Browser Started");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// Open Home page
		driver.get(Constants.URL);
		test.log(LogStatus.INFO, "Mindstronghealth home page is opened");
		// Open Login page
		hp.clickLoginLink();
		test.log(LogStatus.INFO, "Mindstronghealth login page is opened");
	}

	@Test
	/**
	 * Login with empty field should cause warning message: 
	 * "Please fill out this field."
	 */
	
	public void emptyField() throws Exception {

		// Import excel sheet
		String filePath = "//Users//alex//Documents//Selenium//demo//ExcelData.xlsx";
		String sheetName = "InvalidLogins";

		// Load the file
		FileInputStream excelFile = new FileInputStream(filePath);

		// Load the workbook
		workbook = new XSSFWorkbook(excelFile);

		// Load the sheet in which data is stored
		sheet = workbook.getSheet(sheetName);

		// Switch to Login frame
		driver.switchTo().frame(0);
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			try {
				// Import data from Excel file into email field
				cell = sheet.getRow(i).getCell(1);
				cell.setCellType(CellType.STRING);

				lp.clearLogin();
				lp.setLogin(cell.getStringCellValue());

				// Import data from Excel file into password field
				cell = sheet.getRow(i).getCell(2);
				cell.setCellType(CellType.STRING);

				lp.clearPassword();
				lp.setPassword(cell.getStringCellValue());

				lp.clickSignInBtn();
				// Thread.sleep(2000);

				// Verify if Warning message is displayed
				if (driver.getPageSource().contains("Please fill out this field.")) {
					test.log(LogStatus.PASS, "Warning message 'Please fill out this field.' is present");

				} else {
					test.log(LogStatus.FAIL, "Warning message 'Please fill out this field.' is not present");
				}
			} catch (NullPointerException NPE) {
				continue;
			}

		}
		// Return to default frame
		driver.switchTo().defaultContent();
		// Thread.sleep(2000);
	}

	@Test
	/**
	 * Login with unregistered email should cause error message: 
	 * "Error: No account found for <email>"
	 */

	public void unregisteredEmail() throws Exception {

		driver.switchTo().frame(0);
		lp.setLogin("test@test.com");
		lp.setPassword("testpassword");
		lp.clickSignInBtn();
		driver.switchTo().defaultContent();
		// Thread.sleep(2000);

		// Set noAccountFoundAlert web element
		WebElement noAccountFoundAlert = lp.getNoAccountFoundAlert();
		// Verify if Error message is displayed
		if (noAccountFoundAlert != null) {
			test.log(LogStatus.PASS, "Alert box 'Error: No account found for <email>' is present");
		} else {
			String path = Screenshots.takeScreenshot(driver, "invalidLogin");
			String imagePath = test.addScreenCapture(path);
			test.log(LogStatus.FAIL, "Alert box 'Error: No account found for <email>' is not present", imagePath);
		}

		// Thread.sleep(2000);
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
		report.endTest(test);
		report.flush();
	}

}
