package HA.TestExecute;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterTest;
import HA.Component.*;
import HA.SeleniumLib.*;
import HA.SeleniumLib.Login;

public class NewTest {

	protected LoginPage loginPage;
	protected HomePage homepage;
	protected GooglePage googlepage;
	String datafile="TestData";
	Common cf=new Common();

	@Test
	public void f() throws Exception {

		googlepage = PageFactory.initElements(HA.SeleniumLib.Common.dr, GooglePage.class);
		System.out.println("@Test.......");
		loginPage=googlepage.loginClick();
		loginPage.appLogin(datafile, "InputData");
		googlepage=loginPage.inbox();
		homepage=googlepage.more();
		homepage.appLogout();
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


