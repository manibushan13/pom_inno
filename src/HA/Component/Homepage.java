package HA.Component;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import HA.Utilities.*;
import HA.SeleniumLib.*;

public class HomePage {

	private WebDriver driver;
	public static HashMap<String, String> locaterType = new HashMap<String, String>();
	public static HashMap<String, String> locaterText = new HashMap<String, String>();

	public HomePage(WebDriver driver) {
		this.driver = driver;
	}
	
	public static void getAllLocaters() throws Exception
	{
		System.out.println("sizeeeeee :"+locaterType.size());
		if(locaterType.size()<=0)
		{
			locaterType=Util.locaterType("homepage");
			locaterText=Util.locaterText("homepage");
		}
	}

	public LoginPage appLogout() throws Exception
	{
		Common.clickButton(locaterType.get("logoutLink"), locaterText.get("logoutLink"));
		Common.clickButton(locaterType.get("logout"), locaterText.get("logout"));
		Thread.sleep(5000);
		return PageFactory.initElements(driver, LoginPage.class);
		
	}
	

}
