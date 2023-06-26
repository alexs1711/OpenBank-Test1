package org.example.pageobjects;

import org.example.AbstractComponent.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import java.util.List;

public class Contactanos extends AbstractComponent {

	WebDriver driver;

	public Contactanos(WebDriver driver) {
		// initialization
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	//div:has([data-id$='Telephone']) p span:nth-child(2)
	/*@FindBy(xpath = "//span[normalize-space()='Desde España:']//following-sibling::span")
	WebElement numerotelefono;*/

	@FindBy(css = "div:has([data-id$='Telephone']) p span:nth-child(2)")
	WebElement numerotelefono;

	public String getNumerotelefono() {
		String ntelefono = numerotelefono.getText();
		return ntelefono;
	}
}
