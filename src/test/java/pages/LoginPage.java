package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class LoginPage {
	ExtentTest test;
	WebDriver driver;

	public LoginPage(WebDriver driver, ExtentTest test) {
		this.driver = driver;
		this.test = test;
	}

	public void setLogin(String login) {
		WebElement loginField = driver.findElement(By.xpath("//div[@id='loginbox']//div[1]/input"));
		loginField.sendKeys(login);
		test.log(LogStatus.INFO, "Enter login");
	}

	public void clearLogin() {
		WebElement loginField = driver.findElement(By.xpath("//div[@id='loginbox']//div[1]/input"));
		loginField.clear();
		test.log(LogStatus.INFO, "Clear login");
	}

	public void setPassword(String password) {
		WebElement passwordField = driver.findElement(By.name("password"));
		passwordField.sendKeys(password);
		test.log(LogStatus.INFO, "Enter password");
	}

	public void clearPassword() {
		WebElement passwordField = driver.findElement(By.name("password"));
		passwordField.clear();
		test.log(LogStatus.INFO, "Clear password");
	}

	public void clickSignInBtn() {
		WebElement SignInButton = driver.findElement(By.tagName("button"));
		SignInButton.click();
		test.log(LogStatus.INFO, "Click Sign In button");
	}
	
	WebElement noAccountFoundAlert;
	public WebElement getNoAccountFoundAlert(){
		noAccountFoundAlert = driver.findElement(By.cssSelector(".alert.alert-info.alert-dismissable"));
		return noAccountFoundAlert;
	
	}

}