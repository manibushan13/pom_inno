package HA.Component;

import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import HA.SeleniumLib.*;
import HA.Utilities.*;

public class GooglePage {
	
	private WebDriver driver;
	Common cf=new Common();
	public static HashMap<String, String> locaterType = new HashMap<String, String>();
	public static HashMap<String, String> locaterText = new HashMap<String, String>();

	public GooglePage(WebDriver driver) {
		this.driver = driver;
	}
	public static void getAllLocaters() throws Exception
	{
		System.out.println("sizeeeeee :"+locaterType.size());
		if(locaterType.size()<=0)
		{
			locaterType=Util.locaterType("googlepage");
			locaterText=Util.locaterText("googlepage");
		}
	}

	public static void appLogin(String datafile, String dataset) throws Exception
	{
		Common.textEnter(locaterType.get("username"), locaterText.get("username"), Util.getXmlData(datafile, dataset,"username"));
		Common.clickButton(locaterType.get("nextButton"), locaterText.get("nextButton"));
		Common.textEnter(locaterType.get("password"), locaterText.get("password"), Util.getXmlData(datafile, dataset,"password"));
		Common.clickButton(locaterType.get("signIn"), locaterText.get("signIn"));
	}

	public LoginPage loginClick() throws Exception {
		Common.clickButton(locaterType.get("loginLink"), locaterText.get("loginLink"));
		return PageFactory.initElements(driver, LoginPage.class);
	}
	
	public HomePage more() throws Exception {
		Common.clickButton(locaterType.get("more"), locaterText.get("more"));
		return PageFactory.initElements(driver, HomePage.class);
	}
	

}
