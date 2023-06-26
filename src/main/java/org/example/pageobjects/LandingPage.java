package org.example.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.example.AbstractComponent.AbstractComponent;

public class LandingPage extends AbstractComponent{

	WebDriver driver;
	
	public LandingPage(WebDriver driver)
	{
		super(driver); //acceder al driver del parent (abstract component)
		//initialization
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
		
	//WebElement userEmails = driver.findElement(By.id("userEmail"));
	//PageFactory


	@FindBy(xpath = "//span[normalize-space()='Productos']")
	WebElement productosBt;

	@FindBy(xpath = "//span[normalize-space()='Openbank Wealth']")
	WebElement wealthBt;

	@FindBy(xpath = "//span[normalize-space()='Inversión Automatizada: Roboadvisor']")
	WebElement roboadvBt;

	@FindBy(xpath = "//span[normalize-space()='Financiación']")
	WebElement financiacionBt;

	@FindBy(xpath = "//span[normalize-space()='Hipoteca Fija']")
	WebElement hipotecaFijaBt;

	@FindBy(css=".container-cookie-modal-footer-accept-button")
	WebElement acceptCookiesbt;

	public HipotecaFija abrirHipotecaFija()
	{
		productosBt.click();
		financiacionBt.click();
		hipotecaFijaBt.click();
		HipotecaFija hipFij = new HipotecaFija(driver);
		return hipFij;
	}

	public RoboAdvisor abrirRoboAdvisor()
	{
		productosBt.click();
		wealthBt.click();
		roboadvBt.click();
		RoboAdvisor robAdv = new RoboAdvisor(driver);
		return robAdv;
	}
	public void clickCookies() throws InterruptedException {
		clickCookies(acceptCookiesbt);
	}
	public void goTo()
	{
		driver.get("https://www.openbank.es/");
	}
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
}
