package org.example.pageobjects;

import org.example.AbstractComponent.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HipotecaFija extends AbstractComponent {

	WebDriver driver;

	public HipotecaFija(WebDriver driver) {
		// initialization
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	/*public HipotecaFija(WebDriver driver,WebElement language) {
		// initialization
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}*/
	@FindBy(css = "tbody tr:nth-child(2) td:not([class])")
	List<WebElement> subjectDiscounts;

	@FindBy(css = "tbody tr:nth-child(3) td:not([class])")
	List<WebElement> nonSubjectDiscounts;

	@FindBy(css = "tbody tr:nth-child(1) td:not([class])")
	List<WebElement> yearsInterestRates;

	@FindBy(css = ".lang-options.lang-options--es > li > a")
	List<WebElement> langOptions;

	@FindBy(css = ".lang-selector__icon.icon-accordion-abrir")
	WebElement buttonCountryLangOptions;

	@FindBy(xpath = "//div[@class='language-select']//button[@id='dropdownMenu2']")
	WebElement buttonLangOptions;

	@FindBy(css = "a[class='ok-topbar__superior__cta button button--prussian-blue'] span")
	WebElement buttonAcceptChangeCountryLang;

	@FindBy(css = "label[for='r-first']")
	WebElement labelButtonPrimaryHome;

	@FindBy(css = "label[for='r-old']")
	WebElement labelButtonResaleHome;

	@FindBy(css = "input[placeholder='Seleccionar']")
	WebElement inputSelectState;

	@FindBy(css = ".amount.mobile-no-border.mortgage-mobile-number")
	WebElement inputSelectHousePrice;

	@FindBy(css = ".mortgage.mobile-no-border.mortgage-mobile-number")
	WebElement inputMoneyFinanced;
	@FindBy(css = ".years.mortgage-mobile-number")
	WebElement inputYearsOfMortgage;

	@FindBy(css = "#calculate-button.button.button--primary:not([class*='button--disabled'])") //button calculate mortgage is not disabled
	WebElement buttonCalculateMortgage;


	By table = By.cssSelector(".table");

	public List<WebElement> getLangOptions() {
		waitForElementToAppear(table);
		return langOptions;
	}

	public List<WebElement> getSubjectDiscountList() {
		waitForElementToAppear(table);
		return subjectDiscounts;
	}

	public List<WebElement> getNonSubjectDiscountList() {
		waitForElementToAppear(table);
		return nonSubjectDiscounts;
	}

	public void print(List<WebElement> testElements) {
		for (int i = 0; i < testElements.size(); i++) {
			System.out.println(testElements.get(i).getText());
		}
	}

	public String getYearsInterestRates(int i) {
		String patron = "((\\d{2}-\\{2})|(\\d{2}))";
		Pattern pattern = Pattern.compile(patron);
		Matcher matcher = pattern.matcher(yearsInterestRates.get(i).getText());
		String title = null;
		while (matcher.find()) {
			title = matcher.group();
		}
		return title;
	}

	public HashMap<String, HashMap<Integer, Double>> getAllValuesOneLanguage() {
		HashMap<String, HashMap<Integer, Double>> comb = new HashMap<String, HashMap<Integer, Double>>();
		HashMap<Integer, Double> subjectDiscountsHash = new HashMap<Integer, Double>();
		HashMap<Integer, Double> nonSubjectDiscountsHash = new HashMap<Integer, Double>();
		for (int i = 0; i < subjectDiscounts.size(); i++) {
			Pattern pattern = Pattern.compile("\\d+[.,]\\d+%\\s+\\w+\\d?\\s*\\((\\d+[.,]\\d+)%.*\\)");
			Matcher matcher = pattern.matcher(subjectDiscounts.get(i).getText());
			String result = null;
			if (matcher.find()) {
				result = matcher.group(1).replace(",", ".");
			}
			int title = Integer.parseInt(getYearsInterestRates(i));
			subjectDiscountsHash.put(title, Double.valueOf(result));
			comb.put("SubjectDiscounts", subjectDiscountsHash);
		}
		for (int i = 0; i < nonSubjectDiscounts.size(); i++) {
			//String[] name=nonSubjectDiscounts.get(i).getText().replace(",",".").split("%");
			//String formattedName=name[0].trim();
			Pattern pattern = Pattern.compile("\\d+[.,]\\d+%\\s+\\w+\\d?\\s*\\((\\d+[.,]\\d+)%.*\\)");
			Matcher matcher = pattern.matcher(nonSubjectDiscounts.get(i).getText());
			String result = null;
			if (matcher.find()) {
				result = matcher.group(1).replace(",", ".");
			}
			int title = Integer.parseInt(getYearsInterestRates(i));
			nonSubjectDiscountsHash.put(title, Double.valueOf(result));
			comb.put("nonSubjectDiscounts", nonSubjectDiscountsHash);
		}
		return comb;
	}

	public ArrayList<HashMap<String, HashMap<Integer, Double>>> getAllLanguagesValues() {
		ArrayList<HashMap<String, HashMap<Integer, Double>>> allLanguages = new ArrayList<>();
		for (int i = 0; i < getLangOptions().size(); i++) {
			buttonCountryLangOptions.click();
			buttonLangOptions.click();
			langOptions.get(i).click();
			buttonAcceptChangeCountryLang.click();
			waitForElementToAppear(table);
			allLanguages.add(getAllValuesOneLanguage());
		}
		return allLanguages;
	}

	public SimuladorHipoteca inputMortgage(String state, String housePrice, String financedMoney, int yearsOfMortgage) throws InterruptedException {//implementar data set en la pagina de tests, por ahora lo meto directamente
		//scrollToElement(radioButtonPrimaryHome);
		waitForWebElementToBeClickable(labelButtonPrimaryHome);
		labelButtonPrimaryHome.click();
		labelButtonResaleHome.click();
		inputSelectState.sendKeys(state);
		inputSelectState.sendKeys(Keys.ENTER);
		inputSelectHousePrice.sendKeys(housePrice);
		inputMoneyFinanced.click();
		inputMoneyFinanced.clear();
		inputMoneyFinanced.sendKeys(financedMoney);
		inputYearsOfMortgage.sendKeys(Integer.toString(yearsOfMortgage));
		inputYearsOfMortgage.sendKeys(Keys.TAB);
		waitForWebElementToAppear(buttonCalculateMortgage);
		buttonCalculateMortgage.click();
		SimuladorHipoteca simHip = new SimuladorHipoteca(driver);
		return simHip;
	}

	public boolean checkCorrectInterest(Double checkTAE, int yearsOfMortgage,HashMap<Integer, Double> TAEHashmap, boolean check) {

		int target = yearsOfMortgage;
		double closestValue = Double.MAX_VALUE;;
		int closestKey = -1;
		for (Map.Entry<Integer, Double> entry : TAEHashmap.entrySet()) {
			int key = entry.getKey();
			double value = entry.getValue();
			if (key >= target && key < closestValue) {
				closestValue = key;
				closestKey = key;
			}
		}

		if (closestValue == -1) {
			System.out.println("Value not found");
		} else {
			System.out.println("Closest key "+closestKey);
			System.out.println("Offered interest after form: "+checkTAE+" <= Offered interest website: "+TAEHashmap.get(closestKey)) ;
			if(checkTAE<=TAEHashmap.get(closestKey)){
				check = true;
			}
		}
		return check;
	}
}