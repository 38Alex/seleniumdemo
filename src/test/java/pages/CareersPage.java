package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CareersPage {
	// Since I'm going to use one web element only on this page, I'm not using logging 
	WebDriver driver;
	
	public CareersPage(WebDriver driver) {
		this.driver = driver;
	}
	
	WebElement qaEngineer;
	public WebElement getQAEngineer(){
		qaEngineer = driver.findElement(By.xpath("//ul[@id='jobs-list']//a[contains(text(),'Software Engineer in Test')]"));
		return qaEngineer;
	
	}
		
}