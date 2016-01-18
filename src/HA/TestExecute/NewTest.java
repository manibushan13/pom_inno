package HA.TestExecute;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterTest;
import HA.Component.*;
import HA.SeleniumLib.*;
import HA.TestAutomation.Component.Login;

public class NewTest {

	protected LoginPage loginPage;
	protected GmailPage gmailPage;
	protected Homepage homepage;
	String datafile="D:\\poc\\sample\\src\\files\\TestData.xml";
	Common cf=new Common();

	@Test
	public void f() throws Exception {


		homepage = PageFactory.initElements(HA.SeleniumLib.Common.dr, Homepage.class);
		System.out.println("@Test.......");
		loginPage=homepage.loginClick();
		loginPage.appLogin(datafile, "InputData");
		homepage=loginPage.inbox();
		gmailPage=homepage.more();
		gmailPage.appLogout();
	}
	@BeforeTest
	public void beforeTest() throws Exception {
		Login.signIn("FF");
	}

	@AfterTest
	public void afterTest() throws Exception {
		Common.quitProcess();
	}

}


