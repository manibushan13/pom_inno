package HA.Component;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import HA.Utilities.*;
import HA.SeleniumLib.*;

public class GmailPage {

	private WebDriver driver;
	public static HashMap<String, String> locaterType = new HashMap<String, String>();
	public static HashMap<String, String> locaterText = new HashMap<String, String>();

	public GmailPage() {
		System.out.println("GmailPaggggggggggggggggggggggggge");
		try{
		locaterType=Util.locaterType("login");
		locaterText=Util.locaterText("login");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public GmailPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public static void getAllLocaters() throws Exception
	{
		System.out.println("sizeeeeee :"+locaterType.size());
		if(locaterType.size()<=0)
		{
			locaterType=Util.locaterType("gmail");
			locaterText=Util.locaterText("gmail");
		}
	}

	public void appLogout() throws Exception
	{
		Common.clickButton(locaterType.get("logoutLink"), locaterText.get("logoutLink"));
		Common.clickButton(locaterType.get("logout"), locaterText.get("logout"));
		Thread.sleep(5000);

	}
	

}
