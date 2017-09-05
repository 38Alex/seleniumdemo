package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class HomePage {
	ExtentTest test;
	WebDriver driver;
	
	public HomePage(WebDriver driver, ExtentTest test) {
		this.driver = driver;
		this.test = test;
	}
	public void clickLoginLink() {
		WebElement loginLink = driver.findElement(By.xpath("//div[@class='pp-advanced-menu pp-advanced-menu-accordion-collapse pp-menu-default']//span[contains(text(),'Login')]"));
		loginLink.click();
		test.log(LogStatus.INFO, "Click on login link");
	}
	
	WebElement careersLink;
	public WebElement getCareersLink(){
		careersLink = driver.findElement(By.xpath("//li[@id='menu-item-759']/a"));
		return careersLink;
	}
	
	public void clickCareersLink() {
		careersLink.click();
		test.log(LogStatus.INFO, "Click on Careers link");
	}
		
}