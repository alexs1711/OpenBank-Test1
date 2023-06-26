package org.example.pageobjects;

import org.example.AbstractComponent.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class RoboAdvisor extends AbstractComponent {

	WebDriver driver;

	public RoboAdvisor(WebDriver driver) {
		// initialization
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//span[normalize-space()='Simula tu inversión']")
	WebElement buttonSimInv;
	@FindBy(css = "div[class='rc-slider-mark'] span")
	List<WebElement> spansRiesgo;

	@FindBy(xpath = "(//div[@class='pie-section__title'])")
	List<WebElement> divConjuntoActivosValue;

	@FindBy(xpath = "(//div[@class='pie-section__subtitle'])")
	List<WebElement> divConjuntoActivosTitle;


	@FindBy(css = "div[class='ok-robo-landing-simulation-sliders__link'] span[class='hover-underline']")
	WebElement spanPortfolioDetails;

	public boolean checkSimInvTotal100(){
		boolean checkTotalPercentage = false;
		buttonSimInv.click();
		spanPortfolioDetails.click();
		for (int i = 0; i < spansRiesgo.size(); i++) {
			String textoSpan= spansRiesgo.get(i).getText();
			System.out.println("-----------------------------------");
			System.out.println("Riesgo "+textoSpan);
			spansRiesgo.get(i).click();
			double total = 0.00;
			double[] percentagesFromOneRisk = getPercentagesPortfolioCompFromOneRisk();
			for(double value :percentagesFromOneRisk){
				total = total+value;
			}
			if(total==100.0){
			System.out.println("The total risk for risk "+textoSpan+" is: "+total+"%");
			checkTotalPercentage = true;
			}
			else{
				System.out.println("Total risk in "+textoSpan+"is not 100%, is: "+total);
			}
		}
		return  checkTotalPercentage;
	}
	public double[] getPercentagesPortfolioCompFromOneRisk(){
		double[] percentagesCompOneRisk = new double[divConjuntoActivosValue.size()];

		for (int j = 0; j < percentagesCompOneRisk.length; j++) {
			System.out.print(divConjuntoActivosTitle.get(j).getText()+": "+divConjuntoActivosValue.get(j).getText()+" ");
			String input = divConjuntoActivosValue.get(j).getText().replace(",", ".");
			double value = Double.parseDouble(input.substring(0, input.length() - 1));
			percentagesCompOneRisk[j] = value;
		}
		return percentagesCompOneRisk;
	}




}
