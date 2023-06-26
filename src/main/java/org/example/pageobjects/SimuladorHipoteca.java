package org.example.pageobjects;

import org.example.AbstractComponent.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimuladorHipoteca extends AbstractComponent {

    WebDriver driver;

    public SimuladorHipoteca(WebDriver driver) {
        // initialization
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "iFrameResizer0") //button calculate mortgage is not disabled
    WebElement iframe;

    @FindBy(xpath = "//div[@id='rptMortgagesCard_Panel1_0']//div[@class='TINTAEBlock ']//span[@class='markbold ']//b")   //(//span[contains(@class,'markbold')])[1]
    WebElement TAEsubjectDiscounts;

    @FindBy(xpath = "//div[@id='rptMortgagesCard_Panel2_0']//div[@class='TINTAEBlock ']//span[@class='markbold ']//b")    //(//span[contains(@class,'markbold')])[7]
    WebElement TAEnonSubjectDiscounts;

    public String filterTAEValues(String TaeValue) {
        Pattern pattern = Pattern.compile("\\d+[.,]\\d+");
        Matcher matcher = pattern.matcher(TaeValue);
        String result = null;
        if (matcher.find()) {
            result = matcher.group();
            result = result.replace(",", ".");
            //System.out.println(result);
        }
        return result;
    }

    public void switchIframe(){
        switchToframe(iframe);
    }

    public Double getSubjectDiscountTAEValues(){
        return Double.valueOf(filterTAEValues(TAEsubjectDiscounts.getText()));
    }

    public Double getNonSubjectDiscountTAEValues(){
        return Double.valueOf(filterTAEValues(TAEnonSubjectDiscounts.getText()));
    }
}
