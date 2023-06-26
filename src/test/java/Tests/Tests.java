package Tests;

import TestsComponents.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.example.pageobjects.*;

import java.util.*;


public class Tests extends BaseTest {

	@Test()
	public void VerifyPhoneNumber()
	{
	Contactanos contactanos = landingPage.abrirContactanos();
	Assert.assertTrue(contactanos.getNumerotelefono().equalsIgnoreCase("+34 900 200 128"));
	}

	@Test()
	public void checkFixedInterestRates()
	{
		HipotecaFija hipFij = landingPage.abrirHipotecaFija();

		ArrayList<HashMap<String,HashMap<Integer, Double>>> arrayHipValues = hipFij.getAllLanguagesValues();
		HashMap<String,HashMap<Integer, Double>> refArrayHipValues = arrayHipValues.get(0);//reference values to compare with the rest of values from other languages
		//
		System.out.println(Arrays.asList(refArrayHipValues));//just to view better test (useless code delete after)
		//
		boolean equalValues = true;
		for (int i = 1; i < arrayHipValues.size(); i++) { //for each mejor comprension para cliente
			//
			System.out.println(Arrays.asList(arrayHipValues.get(i)));//just to view better test (delete after)
			//
			if(!refArrayHipValues.equals(arrayHipValues.get(i))){
				equalValues = false;
				break;
			}
		}
		Assert.assertTrue(equalValues);
	}
	//@Test (dependsOnMethods = { "checkFixedInterestRates" }) //Interest rates must be consistent in both languages, for the sake of this exercise and for simplicity im commenting this line
	@Test
	public void checkFixedInterestRatesMortgage() throws InterruptedException {
		int yearsOfMortgage = 25;
		String state = "Madrid, Comunidad";
		String housePrice = "320000";
		String financedMoney = "230000";

		HipotecaFija hipFij = landingPage.abrirHipotecaFija();  //mejorar getAllValuesOneLanguage, dar mas descripcion
		HashMap<String, HashMap<Integer, Double>> refArrayHipValues = hipFij.getAllValuesOneLanguage();//reference values to compare with the rest of values from other languages
		//hacer algo que sea get Hashmap String = nonSubjectDiscounts y otro que sea SubjectDiscounts y hacer las cuentas

		System.out.println(Arrays.asList(refArrayHipValues));//just to view better test (useless code delete after)

		SimuladorHipoteca simHip = hipFij.inputMortgage(state,housePrice,financedMoney,yearsOfMortgage);
		simHip.switchIframe();
		System.out.println("getSubjectDiscountTAEValues: "+simHip.getSubjectDiscountTAEValues());
		System.out.println("getNonSubjectDiscountTAEValues: "+simHip.getNonSubjectDiscountTAEValues());

		HashMap<Integer, Double> nonSubjectDiscounts= refArrayHipValues.get("nonSubjectDiscounts");
		HashMap<Integer, Double> SubjectDiscounts= refArrayHipValues.get("SubjectDiscounts");

		boolean nonSubjectDiscountCheck = false;
		boolean subjectDiscountCheck = false;

		Double checkNonDiscountTAE = simHip.getNonSubjectDiscountTAEValues();
		Double checkDiscountTAE = simHip.getSubjectDiscountTAEValues();

		nonSubjectDiscountCheck = hipFij.checkCorrectInterest(checkNonDiscountTAE,yearsOfMortgage,nonSubjectDiscounts,nonSubjectDiscountCheck);
		subjectDiscountCheck = hipFij.checkCorrectInterest(checkDiscountTAE,yearsOfMortgage,SubjectDiscounts,subjectDiscountCheck);

		Assert.assertTrue(nonSubjectDiscountCheck);
		Assert.assertTrue(subjectDiscountCheck);
	}

	@Test()
	public void checkWealth()
	{
		RoboAdvisor rbAdv = landingPage.abrirRoboAdvisor();
		Assert.assertTrue(rbAdv.checkSimInvTotal100());
	}



	@DataProvider
	public Object[][] getData(){

		HashMap<String,String> map = new HashMap<String,String>();
	map.put("email", "anshika@gmail.com");
	map.put("password", "Iamking@000");
	map.put("product", "ZARA COAT 3");

		return new Object[][] {{map}};
	}
	
	
	
	


}
