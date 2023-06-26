package org.example.AbstractComponent;

import org.example.pageobjects.Contactanos;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

public class AbstractComponent {
	
	WebDriver driver;

	public AbstractComponent(WebDriver driver) {
		
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
	}

	@FindBy(css="div[class='ok-public-topbar'] a[title='Contáctanos'] ")
	WebElement contactanos;

	public Contactanos abrirContactanos()
	{
		contactanos.click();
		Contactanos pagContactanos = new Contactanos(driver);
		return pagContactanos;
	}

	public void waitForElementToAppear(By findBy) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));

	}
	public void waitForWebElementToAppear(WebElement findBy) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(findBy));

	}
	public void waitForElementToDisappear(WebElement ele) throws InterruptedException
	{
		Thread.sleep(1000);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
		wait.until(ExpectedConditions.invisibilityOf(ele));
	}
	public void scrollToElement (WebElement myElement) throws InterruptedException{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
		wait.until(ExpectedConditions.visibilityOf(myElement));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", myElement);
		Thread.sleep(500);
	}

	public void waitForWebElementToBeClickable(WebElement findBy) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.elementToBeClickable(findBy));

	}

	public void clickCookies(WebElement ele) throws InterruptedException{
		//queda revisar el como hacerlo con zoom o pantallas mas pequenas
		waitForWebElementToAppear(ele);
		ele.click();
		waitForElementToDisappear(ele);
	}

	public void switchToframe(WebElement frame){
		driver.switchTo().frame(frame);
	}

}
