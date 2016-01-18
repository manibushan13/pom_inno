package HA.SeleniumLib;

import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.os.WindowsUtils;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.*;
import org.testng.Assert;

import HA.TestAutomation.Driver;
import HA.TestAutomation.HATF_properties;
import HA.Utilities.Fetch_PageLocaters;


public class Common {

	public static WebDriver dr;
	public static String browservar;
	public static WebElement element;
	public static HATF_properties _properties = new HATF_properties();
	public static String parenWindow;
	public static String os;
	
	public Common(){
		Fetch_PageLocaters fp=new Fetch_PageLocaters();
	}

	public static String browserName(){
		return _properties.getProperty(HATF_properties.BROWSER);
	}

	/**
	 * driver: will instance a browser and open your url	 *  
	 * @param browser -- pass bowser name as 	 	
	 ** "IE" for Internet Explorer.
	 ** "GC" for Google Chrome.
	 ** 	by default it will take FireFox
	 * @param url
	 */	

	public static void driver(String browser,String url) throws Exception{		


		browservar=browser;
		switch (browser){	

		case "IE":

			System.setProperty("webdriver.ie.driver",System.getProperty("user.dir") +"/Lib/IEDriverServer.exe");
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			cap.setCapability("enablePersistentHover", false);
			cap.setCapability("ensureCleanSession", true);
			cap.setCapability("unexpectedAlertBehaviour", "accept");
			DesiredCapabilities.internetExplorer().setCapability("ignoreProtectedModeSettings", true);			
			dr = new InternetExplorerDriver(cap);	
			Driver.logger.info("Invoked IE Driver Success");
			break;

		case "GC":

			//Get Chrome Driver
			if(os.contains("mac"))
			{
				System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") +"/Lib/chromedriver");
			}
			else
			{	
				System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") +"/Lib/chromedriver.exe");
			}
			String downloadFilepath = System.getProperty("user.dir")+"/src/HA/TestData/Reporting/Downloads";

			//Save Chrome Preferences in Hash Map
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			HashMap<String, Object> contentsetting = new HashMap<String, Object>();
			//chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("download.default_directory", downloadFilepath);

			chromePrefs.put("download.default_directory", downloadFilepath);
			contentsetting.put("multiple-automatic-downloads",1);
			chromePrefs.put("download.prompt_for_download", "false");
			chromePrefs.put("profile.default_content_settings", contentsetting);

			//Save Chrome Opions
			ChromeOptions options = new ChromeOptions();
			options.addExtensions(new File(System.getProperty("user.dir")+"/Lib/okata.crx"));
			HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();			
			options.setExperimentalOption("prefs", chromePrefs);

			options.addArguments("--test-type");
//			options.addArguments("--disable-extensions");
			options.addArguments("--disable-popup-blocking");

			//Set desired capability
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability("chrome.switches", Arrays.asList("--disable-local-storage"));
			capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
			//cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);

			//Start Chrome Driver
			dr = new ChromeDriver(capabilities);

			Driver.logger.info("Invoked Chrome Driver Success");
			break;


		case "FF":

			dr = new FirefoxDriver();
			Driver.logger.info("Invoked Firefox Driver Success");
		}
		dr.get(url);	
		dr.manage().window().maximize();
		dr.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	public static List<WebElement> findElements(String elemfindBY,String elemfindText)throws Exception{

		List<WebElement> elements = null;	

		if(elemfindBY.equals("id")){
			elements = dr.findElements(By.id(elemfindText));			
		}
		else if(elemfindBY.equals("name")){
			elements = dr.findElements(By.name(elemfindText));				
		}
		else if(elemfindBY.equals("className")){
			elements = dr.findElements(By.className(elemfindText));				
		}			
		else if(elemfindBY.equals("linkText")){				
			elements = dr.findElements(By.linkText(elemfindText));
		}		
		else if(elemfindBY.equals("xpath")){
			elements = dr.findElements(By.xpath(elemfindText));
		}
		else if(elemfindBY.equals("css")){
			elements = dr.findElements(By.cssSelector(elemfindText));
		} 
		else if(elemfindBY.equals("pLinkText")){
			elements = dr.findElements(By.partialLinkText(elemfindText));
		}
		else if(elemfindBY.equals("tagName")){
			elements = dr.findElements(By.tagName(elemfindText));
		}

		return elements;
	}


	public static Object executeJS(String js, Object arg1, Object arg2){

		Driver.logger.info("Executing js: " + js);
		if(arg1 != null && arg2 == null)
			return ((JavascriptExecutor)dr).executeScript(js, arg1);
		if(arg1 != null && arg2 != null)
			return ((JavascriptExecutor)dr).executeScript(js, arg1, arg2);
		return ((JavascriptExecutor)dr).executeScript(js);
	}	

	/**
	 * find: will find the WebElement
	 * @param elemfindBY -- pass how to found the WebElemnt as  
	 * 		** id
	 * 		** name
	 * 		** className
	 * 		** linkText
	 * 		** xpath
	 * @param elemfindText -- pass value for found Text 
	 * 		** elemfindText
	 * @return
	 */
	public static WebElement findElement(String elemfindBY,String elemfindText) throws Exception { 
		java.util.List<WebElement> elements = null;				
		elements = findElements(elemfindBY, elemfindText);

		if(elements == null || elements.size() == 0)	
			Driver.logger.info("Element with "+ elemfindBY +"  "+ elemfindText +" found");	
		for(WebElement element: elements)
		{
			if(element.isDisplayed())
				return element;
		}
		return elements.get(0);
	}


	/**
	 * find: will find the WebElement
	 * @param elemfindBY -- pass how to found the WebElemnt as  
	 * 		** id
	 * 		** name
	 * 		** className
	 * 		** linkText
	 * 		** xpath
	 * @param elemfindText -- pass value for found Text 
	 * 		** elemfindText
	 * @return
	 */
	public static List<WebElement> IsElementPresent(String elemfindBY,String elemfindText)throws Exception{
		List<WebElement> element = null;
		if(elemfindBY=="id"){
			element = dr.findElements(By.id(elemfindText));			
		}
		else if(elemfindBY=="name"){
			element = dr.findElements(By.name(elemfindText));				
		}
		else if(elemfindBY=="className"){
			element = dr.findElements(By.className(elemfindText));				
		}			
		else if(elemfindBY=="linkText"){				
			element = dr.findElements(By.linkText(elemfindText));
		}		
		else if(elemfindBY=="xpath"){
			element = dr.findElements(By.xpath(elemfindText));
		}
		else if(elemfindBY=="css"){
			element = dr.findElements(By.cssSelector(elemfindText));
		}
		else if(elemfindBY=="pLinkText"){
			element = dr.findElements(By.partialLinkText(elemfindText));
		}
		else if(elemfindBY=="tagName"){
			element = dr.findElements(By.tagName(elemfindText));
		}

		Driver.logger.info("Element with "+ elemfindBY +"  "+ elemfindText +" found");
		return element;

	}

	public static String Getxml(String datafile, String dataset, String key) throws Exception{
		String values = null;		
		values = HA.Utilities.ReadXML.dataset(datafile,dataset,key);
		System.out.println(key+" valuesssssssssssss "+values);
		return values;
	}


	/**
	 * maximizeImplicitWait: will maximize the currency window and implicitWait() will helps to delay problems.
	 *	 * implicitWait: will wait for the respond.
	 * @waitTime: provide waiting time in Seconds in HATF_propertices page.

	 */	
	//**Maximizing Window */
	public static void maximizeImplicitWait() throws Exception{
		java.awt.Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
		int width = ss.width;
		int height = ss.height;
		System.out.println("ddddddddddddddddddddddddeeeeeeeeeeeeeeeeeeee: "+width+"\teeeeeeeeeeee: "+height);
		//		ss.setSize(width, height);
		Dimension sse = new Dimension(width,height);
		//		dr.manage().window().setSize(width,height);
		dr.manage().window().setSize(sse);		
		dr.manage().window().maximize();
		Driver.logger.info("Login Window maximized");
		implicitWait();	

	}

	public static void resizeWindow() throws Exception{
		dr.manage().window().setSize(new Dimension(5120,3500));
	}

	/**
	 * implicitWait: will wait for the respond.
	 * @waitTime: provide waiting time in Seconds in HATF_propertices page.
	 */
	//** ImplicitWait */
	public static void implicitWait() throws Exception{
		Sync.waitImplicit(Long.parseLong(_properties.getProperty(HATF_properties.waitTime)));
		System.out.println("Implicit Wait:  "+Long.parseLong(_properties.getProperty(HATF_properties.waitTime)));
	}

	/**
	 * switchWindows(): will switch to windows by driver generated id.
	 * @throws Exception 
	 * @Noparameter
	 * 
	 */
	//**Switching Windows*/	
	public static void switchWindows() throws Exception{
		switchWindows(true);		
	}	

	public static void switchWindows(Boolean maximizeWindow) throws Exception{
		Set <String> ids=dr.getWindowHandles();
		String currentWindowId = null;		
		for(String id:ids)
		{
			if(currentWindowId == null)
			{
				try{
					currentWindowId = dr.getWindowHandle();
				}
				catch(NoSuchWindowException ex){
					Driver.logger.info("Web Driver is not pointing to any window currently.");
				}
			}

			if(id.equals(currentWindowId))
				parenWindow = id;
			else
			{
				if(currentWindowId == null)
					parenWindow = id;
				dr.switchTo().window(id);					
			}
			System.out.println("Window Details:  "+id);
		}
		if(maximizeWindow)
			maximizeImplicitWait();	
	}

	public static void switchmainWindows() throws Exception{

		Set <String> ids=dr.getWindowHandles();	
		System.out.println("id size:\t"+ids.size());
		for(String id:ids)
		{
			dr.switchTo().window(id);				

			System.out.println("Window Details:  "+id);
		}
	}

	public static String parenWindows()throws Exception{		
		return dr.getWindowHandle();
	}

	public static void switchmainWindowsCons()throws Exception{
		String parentWin = parenWindows();
		System.out.println("parentWinparentWinparentWin:\t"+parentWin);
		Set <String> ids=dr.getWindowHandles();	
		System.out.println("id size:\t"+ids.size());
		for(String id:ids)
		{
			if(((ids).size())>1){
				System.out.println("if condition: idsidsidsids");
				if((id).equals(parentWin)){
					System.out.println("if condition: dfdfdfdfd");
				}
				else{
					System.out.println("else condition: dfdfdfdfd");
					dr.switchTo().window(id);					
				}
			}

			if(((ids).size())==1){
				dr.switchTo().window(id);
			}		
			System.out.println("Window Details:  "+id);
		}
	}	

	//**Switching Windows*/	
	/**
	 * switchWindows:will switch to window by windowName or popupName
	 * @param popupName -- provide the windowName or popupName
	 */
	public static void switchWindows(String popupName)throws Exception{

		dr.switchTo().window(popupName);
		Driver.logger.info(popupName+"window/popup selected");

	}

	//**Switching Frame with FrameId */
	/**
	 * switchFrames(id): switch to frame by id
	 * @param frameid -- provide frameid
	 */
	public static void switchFrames(int frameid)throws Exception{
		if(frameid>0){
			dr.switchTo().frame(frameid);
			Driver.logger.info("frameidframeidframeid: "+frameid);			
		}
		else{				
			dr.switchTo().defaultContent();
			Driver.logger.info("frameidframeidframeid: "+frameid);
		}	
	}

	//**Switching Frame with FrameName */	
	/**
	 * switchFrames(frameName): switch to frame by frameName or frameC
	 * @param frameFindBY -- provide frameFindBY 
	 *  @param frameFindText -- provide frameFindText
	 */
	public static void switchFrames(String frameFindBY, String frameFindText)throws Exception{

		dr.switchTo().frame(Common.findElement(frameFindBY, frameFindText));	
		Driver.logger.info("Frame:  "+ frameFindText + " Found successfully");	

	}
	//**Switching Frame with FrameName */	
	/**
	 * switchFrames(frameName): switch to frame by frameName or frameC
	 * @param frameName -- provide frameName or frameClassName
	 */
	//	public static void switchFrames(String frameName){
	//		try{			
	//			dr.switchTo().frame(dr.findElement(By.id(frameName)));
	//			Driver.logger.info("Frame:  "+ frameName + " Found successfully");
	//		}catch(Exception e){			
	//			try{				
	//				dr.switchTo().frame(dr.findElement(By.name(frameName)));			
	//				Driver.logger.info("FrameName:  "+ frameName + " Found successfully");
	//			}catch(Exception ce){				
	//				dr.switchTo().frame(dr.findElement(By.className(frameName)));
	//				Driver.logger.error("switch frame failed with following exception:\n"+e);
	//			}
	//		}
	//
	//	}

	/**
	 * txtVerify: will verify the text and return the result in true or false
	 * @param text: pass expected text.
	 */
	public static void txtVerify(String text) throws Exception{		
		if(dr.findElement(By.cssSelector("BODY")).getText().contains(text));
		Driver.logger.info(text+" :Text found in frame");	
	}

	/**
	 * getText: will get the text value for an element
	 * @param elemfindBY
	 * @param elemfindText
	 */

	public static String getText(String elemfindBY,String elemfindText)throws Exception{
		String getText = null;
		getText = findElement(elemfindBY,elemfindText).getText();
		Driver.logger.info("the Text for the given element is : "+getText);
		return getText;
	}

	/** Controls related functions*/	

	/**
	 * textBoxinput -- will provide the text value into the textBox
	 * @param elemfindBY -- pass how to found the WebElemnt as  
	 * 		** id
	 * 		** name
	 * 		** className
	 * 		** linkText
	 * 		** xpath
	 * @param elemfindText -- pass value for found Text 
	 * 		** elemfindText
	 * @param value -- pass the text value to input.
	 */
	public static void textBoxInput(String elemfindBY,String elemfindText,String value) throws Exception{	
		Sync.waitElementClickable(60, elemfindBY, elemfindText);
		findElement(elemfindBY,elemfindText).clear();
		findElement(elemfindBY,elemfindText).sendKeys(value);
		String entered=findElement(elemfindBY,elemfindText).getAttribute("value");
		if(!entered.equals(value)){
			findElement(elemfindBY,elemfindText).clear();
			findElement(elemfindBY,elemfindText).sendKeys(value);
		}
		Driver.logger.info(value+" Text filled in text box as expected");
	}


	/**
	 * textBoxVerify -- will verify the text value into the textBox
	 * @param elemfindBY -- pass how to found the WebElemnt as  
	 * 		** id
	 * 		** name
	 * 		** className
	 * 		** linkText
	 * 		** xpath
	 * @param elemfindText -- pass value for found Text 
	 * 		** elemfindText
	 * @param value -- pass the expected  text to verify.
	 * @throws Exception 
	 */
	//textBoxVerification
	public static String textBoxVerify(String elemfindBY,String elemfindText,String value) throws Exception{	
		String actual = findElement(elemfindBY,elemfindText).getAttribute("value");
		System.out.println("actualText:  "+  actual  +"  userText:  "+value);
		if(value.equals(actual)){
			Driver.logger.info(value+" Text found in text box");
			return "Pass";
		}
		else{
			Driver.logger.info(value+" Text not found in text box");
			return "Fail";
		}				
	}

	public static void verifyTextBox(String elemfindBY,String elemfindText,String value) throws Exception{	
		String actual = findElement(elemfindBY,elemfindText).getAttribute("value");
		System.out.println("actualText:  "+  actual  +"  userText:  "+value);
		if(value.equals(actual)){
			Driver.logger.info(value+" Text found in text box");
		}
		else{
			Driver.logger.info(value+" Text not found in text box");
			Assert.fail();
		}				
	}
	
	/**
	 * textVerify -- will verify the text contained in an element
	 * @param elemfindBY -- pass how to found the WebElemnt as  
	 * 		** id
	 * 		** name
	 * 		** className
	 * 		** linkText
	 * 		** xpath
	 * @param elemfindText -- pass value for found Text 
	 * 		** elemfindText
	 * @param value -- pass the expected  text to verify.
	 */
	//textBoxVerification
	public static String textVerify(String elemfindBY,String elemfindText,String value) throws Exception{	
		String actual = findElement(elemfindBY,elemfindText).getText();
		System.out.println("actualText:  "+  actual  +"  userText:  "+value);
		if(value.equals(actual)){
			Driver.logger.info(value+" Text found in element");
			return "Pass";
		}
		else{
			Driver.logger.info(value+" Text not found in element");
			return "Fail";
		}				
	}


	//clickElement
	/**
	 * clickElement: will find the element and click on that element.	 * 
	 * @param elemfindBY -- pass how to found the WebElemnt as  
	 * 		** id
	 * 		** name
	 * 		** className
	 * 		** linkText
	 * 		** xpath
	 * @param elemfindText -- pass value for found Text 
	 * @throws Exception 
	 */
	//	public static void clickElement(String elemfindBY,String elemfindText) throws Exception{		
	//		WebElement element = findElement(elemfindBY,elemfindText);
	//		if(element.isDisplayed()){
	//			((JavascriptExecutor) dr).executeScript("arguments[0].scrollIntoView(true);", element);
	//			element.click();		
	//		}
	//
	//		else
	//			ClickOnHiddenElement(element);
	//		Driver.logger.info(elemfindText+ " element click success");	
	//	}

	public static void clickElement(String elemfindBY,String elemfindText) throws Exception{	
		WebElement element = findElement(elemfindBY,elemfindText);
		if(element.isDisplayed()){
			//			((JavascriptExecutor) dr).executeScript("arguments[0].scrollIntoView(true);", element);
			((JavascriptExecutor) dr).executeScript("var el=arguments[0]; setTimeout(function() { el.scrollIntoView(true); }, 10);",element);	
			element.click();	
		}

		else
			ClickOnHiddenElement(element);
		Driver.logger.info(elemfindText+ " element click success");	
	}



	public static void ClickOnHiddenElement(String elemfindBY,String elemfindText)throws Exception{					
		WebElement element = findElement(elemfindBY,elemfindText);
		ClickOnHiddenElement(element);
		Driver.logger.info(elemfindText+ " element click success");
	}

	public static void KeyPress(String elemfindBY,String elemfindText, Keys keyCode)throws Exception{
		findElement(elemfindBY, elemfindText).sendKeys(keyCode);		
	}


	//clicks on hidden element
	//	public static void ClickOnHiddenElement(WebElement element){
	//		Driver.logger.info("Element is not visible.");		
	//		((JavascriptExecutor) dr).executeScript("if(document.createEvent){"
	//				+ "var evObj = document.createEvent('MouseEvents');"
	//				+ "evObj.initEvent('click', true, false); arguments[0].dispatchEvent(evObj);} "
	//				+ "else if(document.createEventObject) { arguments[0].fireEvent('onclick');}", element);
	//	}

	public static void ClickOnHiddenElement(WebElement element){
		Driver.logger.info("Element is not visible.");	
		((JavascriptExecutor) dr).executeScript("var el=arguments[0];  if(document.createEvent){"
				+ "var evObj = document.createEvent('MouseEvents');"
				+ "evObj.initEvent('click', true, false); setTimeout(function() { el.dispatchEvent(evObj); }, 10);} "
				+ "else if(document.createEventObject) { setTimeout(function() {el.fireEvent('onclick');}, 10);}", element);
	}




	public static void clickCheckBox(String elemfindBY,String elemfindText,boolean isChecked) throws Exception{			
		WebElement checkBox = findElement(elemfindBY,elemfindText);
		if(checkBox.isSelected() != isChecked) {
			checkBox.click();
		}
		Driver.logger.info(elemfindText+ "Button click success");

	}

	public static Boolean verifyCheckBox(String elemfindBY,String elemfindText,boolean isChecked) throws Exception{
		WebElement checkBox = findElement(elemfindBY,elemfindText);
		if(checkBox.isSelected() != isChecked) {			
			Driver.logger.info(elemfindText+ "Checkbox verification failed");
			return false;
		}

		Driver.logger.info(elemfindText+ "Checkbox verification success");
		return true;
	}


	//drop down
	/**
	 * dropdown: will select the value from  a dropdown list.
	 * * clickElement: will find the element and click on that element.	 * 
	 * @param elemfindBY -- pass how to found the WebElemnt as  
	 * 		** id
	 * 		** name
	 * 		** className
	 * 		** linkText
	 * 		** xpath
	 * @param elemfindText -- pass value for found Text 
	 * @param selectvalue -- pass value to select
	 */
	public static void dropdown(String elemfindBY,String elemfindText,String selectvalue)throws Exception{		
		Select s = new Select(findElement(elemfindBY,elemfindText));
		//		List<WebElement> m = s.getOptions();
		//		System.out.println("Manoj"+m.size());
		s.selectByVisibleText(selectvalue);	
	}

	/**
	 * dropdown: will select the value from  a dropdown list.
	 * clickElement: will find the element and click on that element.
	 * @param elemfindText -- pass value for found Text 
	 * @param selectvalue -- pass value to select
	 */
	public static void dropdown(String elemfindText,String selectvalue)throws Exception{		
		Sync.waitElementClickable(120,"id",elemfindText);
		Select s = new Select(findElement("id",elemfindText));
		s.selectByVisibleText(selectvalue);
	}

	/**
	 * dropdown: will select the value from  a dropdown list.
	 * * clickElement: will find the element and click on that element.	 * 
	 * @param elemfindBY -- pass how to found the WebElemnt as  
	 * 		** id
	 * 		** name
	 * 		** className
	 * 		** linkText
	 * 		** xpath
	 * @param elemfindText -- pass value for found Text 
	 * @param selectvalue -- pass value to select
	 */
	public static void dropdown(String elemfindBY,String elemfindText,String selectvalue, SelectBy selectBy)throws Exception{		
		Select s = new Select(findElement(elemfindBY,elemfindText));

		if(selectBy == SelectBy.TEXT)
			s.selectByVisibleText(selectvalue);
		else if(selectBy == SelectBy.VALUE)
			s.selectByValue(selectvalue);
		else if(selectBy == SelectBy.INDEX)
			s.selectByIndex(Integer.parseInt(selectvalue));	

	}

	public static boolean verifyDropdown(String elemfindBY,String elemfindText, SelectBy selectBy, String expected) throws Exception{
		Select s = new Select(findElement(elemfindBY,elemfindText));
		boolean res = false;
		if(selectBy == SelectBy.TEXT)
		{
			String actual = s.getFirstSelectedOption().getText();
			if(expected.equals(actual))
			{
				Driver.logger.info("Verify dropdown successful.");
				res = true;
			}else{

				res = false;
			}
		}

		return res;
	}

	/**
	 * DLRdrop down control 
	 * @param index
	 * @param colValue
	 */
	public static void DLRdropdown(String index, String colValue)throws Exception{

		if ((index).equals("1")){
			Sync.waitElementClickable(60, "xpath", "//tr/td[2]/select[@id='strHeader']");
			Select s1 = new Select(dr.findElement(By.xpath("//tr/td[2]/select[@id='strHeader']")));
			s1.selectByVisibleText(colValue);
		}
		else{
			Sync.waitElementClickable(60, "xpath", "//tr[" +index+ "]/td[2]/select[@id='strHeader']");
			Select s1 = new Select(dr.findElement(By.xpath("//tr[" +index+ "]/td[2]/select[@id='strHeader']")));
			s1.selectByVisibleText(colValue);
		}
	}

	public static void DLRdiv()throws Exception{		
		dr.findElement(By.id("intRowContHeaderInfo")).click();
	}

	public static void subMenuClick(String popup, String id)throws Exception{	
		String func = popup+".document.getElementById('"+id+"')";
		((JavascriptExecutor) dr).executeScript("eval("+func+").click();");
	}


	public static void Massloadsave()throws Exception{		
		((JavascriptExecutor)dr).executeScript("submitFrm('Load')");
		Driver.logger.info("Successfully loaded");		
	}


	public static void JSExecute(String Funv)throws Exception{
		((JavascriptExecutor)dr).executeScript(Funv);
		Driver.logger.info("Successfully excuted JS function");
	}


	//**dhtmlGrid*/	
	/**
	 * dthmlxEditableGrid: will provide the value to Editable grid text and will click on link or image on in Editable Grid. 
	 * @param controlType: provide control type like as: "AutoComplete", "Link", "Text", "Select".
	 * @param gridName: provide gridName
	 * @param rowIndex: provide rowIndex
	 * @param colIndex: provide colIndex
	 * @param inputValue: provide inputValue
	 */

	public static void dhtmlxEditableGrid(String controlType,String gridName, int rowIndex,int colIndex, String inputValue)throws Exception{	

		if (controlType.equals("AutoComplete")){				
			((JavascriptExecutor) dr).executeScript("var gridObj=" + gridName+";"
					+ "var editColIndex="+colIndex+";"
					+ "var columnIndex = "+rowIndex+";"
					+ "gridObj.grid.selectRow(columnIndex);"
					+ "var newValue=\'"+inputValue+"\';"
					+ "var data = gridObj.config.autocomplete.source[editColIndex];"
					+ "var record = $.grep(data, function (elem, ind) { return $.trim(elem.label) == newValue });"
					+ "gridObj.grid.cellById(gridObj.getRowID(columnIndex),editColIndex).updateCell(newValue, record[0].value);"
					+ "gridObj.grid.editStop()");
		}
		if (controlType.equals("Link")){
			((JavascriptExecutor) dr).executeScript("var gridObj=" + gridName+";"
					+ "var rowIndex = "+rowIndex+";"
					+ "gridObj.grid.selectRow(rowIndex)");							
			((JavascriptExecutor) dr).executeScript("$('." + inputValue + "').eq("+rowIndex+").click()");	
		}
		if (controlType.equals("Text")){
			((JavascriptExecutor) dr).executeScript("var gridObj ="+ gridName +";"
					+ "var rowIndex = "+rowIndex+";"
					+ "gridObj.grid.selectRow(rowIndex);"
					+ "gridObj.grid.selectCell(gridObj.getRowIndex(gridObj.getSelectedRowID())," +  colIndex  + ", false, false, false);" 
					+ "gridObj.grid.cellById(gridObj.getSelectedRowID(), " + colIndex + ").setValue('"+ inputValue +"');"
					+ "gridObj.grid.editStop()");
		}
		if(controlType.equals("Select")){
			((JavascriptExecutor) dr).executeScript("var gridObj=" + gridName+";"
					+ "var rowIndex = "+rowIndex+";"
					+ "gridObj.grid.selectRow(rowIndex)");
		}

	}
	public static String dhtmlxGridGetText(String searchTexts,String searchTextColumnIndex,String gridName,String checkBoxColumnIndex,String getTextColumnIndex,String action){			

		action.toLowerCase();

		String ss = null;
		int i = 1;
		while(ss==null){


			try{
				if(i>25){
					break;
				}

				if((action.toLowerCase()).equals("select")){		
					String cellText= (String)((JavascriptExecutor) dr).executeScript("var searchedRows = [];"
							+ "var searchText = \'" + searchTexts +"\';"
							+ "if(" + gridName+".grid){"
							+ "var gridObj=" + gridName+".grid;}else{"
							+ "var gridObj=" + gridName+";};"
							+ "var getTextColumnIndex = "+getTextColumnIndex+";"
							+ "var searchTextColumnIndex = "+searchTextColumnIndex+";"
							+ "var searchTextArray = searchText.split('||@@||');"
							+ "for(var i=0; i<searchTextArray.length; i++){"
							+ "searchedRows = gridObj.findCell(searchTextArray[i], searchTextColumnIndex, true);"
							+ "gridObj.selectRowById(searchedRows[0][0], true, true, true);}"
							+ "var text = gridObj.cellById(searchedRows[0][0],getTextColumnIndex).getValue();"
							+ "return text;"
							);		
					System.out.println("cellTextcellTextcellTextcellText:  "+cellText);
					ss = cellText;
				}

				if((action.toLowerCase()).equals("check")){

					String cellText= (String)((JavascriptExecutor) dr).executeScript("var searchedRows = [];"
							+ "var searchText = \'" + searchTexts +"\';"
							+ "if(" + gridName+".grid){"
							+ "var gridObj=" + gridName+".grid;}else{"
							+ "var gridObj=" + gridName+";};"
							+ "var getTextColumnIndex = "+getTextColumnIndex+";"
							+ "var searchTextColumnIndex = "+searchTextColumnIndex+";"
							+ "var checkBoxColumnIndex = "+checkBoxColumnIndex+";"
							+ "multiSelection();"
							+ "function multiSelection(){"
							+ "var searchTextArray = searchText.split('||@@||');"
							+ "for(var i=0; i<searchTextArray.length; i++){"
							+ "searchedRows = gridObj.findCell(searchTextArray[i], searchTextColumnIndex);"
							+ "var domNode = gridObj.cellById(searchedRows[0][0],checkBoxColumnIndex).cell.childNodes[0];"
							//					+ "domNode.click();"
							+ "}"
							+ "}"
							+ "var text = gridObj.cellById(searchedRows[0][0],getTextColumnIndex).getValue();"
							+ "return text;"
							);		
					ss = cellText;
					System.out.println("cellTextcellTextcellTextcellText:  "+cellText);
					break;
				}
			}catch(Exception e){
				ss = null;
			}
			i =i+1;
		}
		return ss;


	}	

	public static void dhtmlxGridTotalCellValues(String datafile,String dataset,String value,String gridName,boolean fail)throws Exception{
		Object columnCount = HA.SeleniumLib.Common.dhtmlxGridColumnCount(gridName);		
		int columnCountValue = Integer.valueOf(String.valueOf(columnCount));
		Driver.logger.info("columnCount: "+columnCountValue);
		Object rowCount = HA.SeleniumLib.Common.dhtmlxGridRowcount(gridName);		
		int rowCountValue = Integer.valueOf(String.valueOf(rowCount));
		Driver.logger.info("rowCountValue: "+rowCountValue);
		for(int i=0;i<((rowCountValue));i++){
			for(int j=0;j<((columnCountValue));j++){
				String cellValue = HA.SeleniumLib.Common.dhtmlxGridGetTextNew(gridName, i, j, "select");
				String xmlprovidedValue ="";
				System.out.println("["+i+"]["+j+"]"+cellValue);
				try{				
					xmlprovidedValue = Common.Getxml(datafile, dataset, value+"["+i+"]["+j+"]");
				}catch(Exception e){
					xmlprovidedValue = null;
				}

				if(xmlprovidedValue==null){
					Driver.logger.warn("\nActual value: "+cellValue+"\nExpected Value: "+xmlprovidedValue+"Result: Comparion Not Happened");
				}else if(cellValue.contains(xmlprovidedValue)){
					Driver.logger.info("\nActual value: "+cellValue+"\nExpected Value: "+xmlprovidedValue+"Result: Comparion Passed");
				}
				else{
					Driver.logger.error("\nActual value: "+cellValue+"\nExpected Value: "+xmlprovidedValue+"Result: Comparion failed");
					//System.out.println("\t<Add key=\"dseValidationsStatus["+i+"]["+j+"]\" value=\""+cellValue+"\"/>");
					if(fail==true){
						Assert.fail();
					}
				}					
			}			
		}
	}


	public static String dhtmlxGridGetTextNew(String gridName,int rowIndex,int columnIndex,String action){			

		action.toLowerCase();
		String textInColumn = null;
		if((action.toLowerCase()).equals("select")){		
			String cellText= (String)((JavascriptExecutor) dr).executeScript("if(" + gridName+".grid){"
					+ "var gridObj=" + gridName+".grid;}else{"
					+ "var gridObj=" + gridName+";};"
					+ "gridObj.selectRow('"+rowIndex+"',true,false,true);"
					+"var text = gridObj.cells2('"+rowIndex+"','"+columnIndex+"').getTitle();"
					+ "return text;"
					);		
			Driver.logger.info("dhtmlxGridGetTextNew value returned:  "+cellText);
			textInColumn = cellText;
		}
		return textInColumn;


	}

	public static String dhtmlxGridGetTooltip(String gridName,int rowIndex,int columnIndex,String action){			

		action.toLowerCase();
		String textInColumn = null;				
		String cellText= (String)((JavascriptExecutor) dr).executeScript("if(" + gridName+".grid){"
				+ "var gridObj=" + gridName+".grid;}else{"
				+ "var gridObj=" + gridName+";};"
				+ "if(gridObj.cellByIndex('"+rowIndex+"','"+columnIndex+"').cell.childNodes[0]."+action+"){"
				+"var text = gridObj.cellByIndex('"+rowIndex+"','"+columnIndex+"').cell.childNodes[0]."+action+";}"
				+ "else{"
				+"var text = gridObj.cells2('"+rowIndex+"','"+columnIndex+"').getTitle();}"
				+ "return text;"
				);
		textInColumn = cellText;		
		return textInColumn;
	}

	public static void dhtmlxGrid(String searchTexts,String columnIndex,String gridName, String newVal,String Action)throws Exception{

		Action.toLowerCase();

		if((Action.toLowerCase()).equals("check")){

			System.out.println("Before newValnewValnewValnewValnewVal"+newVal);
			if(!newVal.equals("1")){			
				newVal="0";
				System.out.println("newValnewValnewValnewValnewVal"+newVal);
			}
			System.out.println("After newValnewValnewValnewValnewVal"+newVal);

			int i=0;
			String gridText = null;
			while(i<20){
				gridText = dhtmlxGridGetText(searchTexts, columnIndex, gridName, newVal, columnIndex, "check");
				if(gridText.equalsIgnoreCase(searchTexts)){
					break;
				}else{
					i=i+1;
				}
			}
			System.out.println("checkcheck");


			((JavascriptExecutor) dr).executeScript("var searchedRows = [];"
					+ "var searchText = \'" + searchTexts +"\';"
					+ "if(" + gridName+".grid){"
					+ "var gridObj=" + gridName+".grid;}else{"
					+ "var gridObj=" + gridName+";};"
					+ "multiSelection();"
					+ "function multiSelection(){"
					+ "var searchTextArray = searchText.split('||@@||');"
					+ "for(var i=0; i<searchTextArray.length; i++){"
					+ "searchedRows = gridObj.findCell(searchTextArray[i], " + columnIndex + ");"
					+ "var domNode = gridObj.cellById(searchedRows[0][0]," + newVal + ").cell.childNodes[0];"
					+ "domNode.click();}"
					+ "}"
					);	

			System.out.println("checkcheckcheck");			

		}		
		if((Action.toLowerCase()).equals("select")){	
			System.out.println("selectselectselectselect");
			int i=0;
			String gridText = null;
			while(i<20){
				gridText = dhtmlxGridGetText(searchTexts, columnIndex, gridName, columnIndex, columnIndex, "select");
				if(gridText.equalsIgnoreCase(searchTexts)){
					break;
				}else{
					i=i+1;
				}
			}

			if(gridText.equalsIgnoreCase(searchTexts)){

				((JavascriptExecutor) dr).executeScript("var searchedRows = [];"
						+ "var searchText = \'" + searchTexts +"\';"
						+ "if(" + gridName+".grid){"
						+ "var gridObj=" + gridName+".grid;}else{"
						+ "var gridObj=" + gridName+";};"
						+ "var searchTextArray = searchText.split('||@@||');"
						+ "for(var i=0; i<searchTextArray.length; i++){"
						+ "searchedRows = gridObj.findCell(searchTextArray[i], " + columnIndex + ", true);"
						+ "gridObj.selectRowById(searchedRows[0][0], false, true, true);}");		
			}else{
				i=i+1;
			}
		}



		if((Action.toLowerCase()).equals("dropdown")){	

			int i=0;
			String gridText = null;
			while(i<20){
				gridText = dhtmlxGridGetText(searchTexts, columnIndex, gridName, columnIndex, columnIndex, "select");
				if(gridText.equalsIgnoreCase(searchTexts)){
					break;
				}else{
					i=i+1;
				}
			}

			((JavascriptExecutor) dr).executeScript("var searchedRows = [];"
					+ "var searchText = \'" + searchTexts +"\';"
					+ "var gridObj=" + gridName+";"
					+ "var newVal= \'" + newVal +"\';"
					+ "var searchTextArray = searchText.split('||@@||');"
					+ "for(var i=0; i<searchTextArray.length; i++){"
					+ "searchedRows = gridObj.grid.findCell(searchTextArray[i], " + columnIndex + ");"
					+ "var ddl = gridObj.grid.cellById(searchedRows[0][0],3).cell.childNodes[0];"
					+ "for (var j = 0; j < ddl.options.length; j++) {"
					+ "if (ddl.options[j].text == newVal) {"
					+ "ddl.options[j].selected = true;"
					+ "}}}");
		}
		Driver.logger.info("dhtmlxGrid "+Action+" successfully done");	

	}


	/**
	 * dhtmlxTree: will do all the actions for tree
	 * @param treeName: provide treeName
	 * @param treeNodeValue:pass node value like (Account Main||@@||AccountRollup||@@||AccountChild2)
	 * @param treeAction:pass action as "SELECT", "CHECKED", "UNCHECKED"
	 */

	public static Boolean isRowPresentInDhtmlxGrid(String searchTexts,String columnIndex,String gridName)throws Exception{

		Boolean strRowPresent = (Boolean)((JavascriptExecutor) dr).executeScript("var searchedRows = [];"
				+ "var searchText = \'" + searchTexts +"\';"
				+ "var gridObj=" + gridName+";"
				+ "var searchTextArray = searchText.split('||@@||');"
				+ "for(var i=0; i<searchTextArray.length; i++){"
				+ "searchedRows = gridObj.grid.findCell(searchTextArray[i], " + columnIndex + ");"
				+ "for(var j=0; j<searchedRows.length; j++){ if(gridObj.grid.cellById(searchedRows[j][0], 1).getTitle() == searchText){"
				+ "return true;alert(2);} } return false;}");
		return strRowPresent;
	}

	public static Boolean isRowPresentInDhtmlxGridIndex(String searchTexts,String columnIndex,String gridName)throws Exception{

		Boolean strRowPresent = (Boolean)((JavascriptExecutor) dr).executeScript("var searchedRows = [];"
				+ "var searchText = \'" + searchTexts +"\';"
				+ "var gridObj=" + gridName+";"
				+ "var searchTextArray = searchText.split('||@@||');"
				+ "for(var i=0; i<searchTextArray.length; i++){"
				+ "searchedRows = gridObj.grid.findCell(searchTextArray[i], " + columnIndex + ");"
				+ "for(var j=0; j<searchedRows.length; j++){ if(gridObj.grid.cellById(searchedRows[j][0], 0).getTitle() == searchText){"
				+ "return true;alert(2);} } return false;}");
		return strRowPresent;
	}

	public static void dhtmlxGridUnmapp(String searchTexts,String columnIndex,String gridName, String newVal,String Action)throws Exception{
		Action.toLowerCase();			

		if((Action.toLowerCase()).equals("select")){		
			((JavascriptExecutor) dr).executeScript("var searchedRows = [];"			
					+ "var searchText = \'" + searchTexts +"\';"
					+ "var gridObj=" + gridName+";"
					//		+ "alert(gridObj.lenght);"
					+ "var searchTextArray = searchText.split('||@@||');"
					+ "for(var i=0; i<searchTextArray.length; i++){"
					+ "searchedRows=gridObj.grid.filterBy(" + columnIndex + ",searchTextArray[i], true); gridObj.grid.selectRow(0, true,false, true);"
					+ "}");

		}
		Driver.logger.info("dhtmlxGrid "+Action+" successfully done");	
	}

	/**
	 * dhtmlxTree: will do all the actions for tree
	 * @param treeName: provide treeName
	 * @param treeNodeValue:pass node value like (Account Main||@@||AccountRollup||@@||AccountChild2)
	 * @param treeAction:pass action as "SELECT", "CHECKED", "UNCHECKED"
	 */

	public static void dhtmlxTree(String treeName,String treeNodeValue,String treeAction )throws Exception{

		int dhtmlxTreeRetry=0;
		dhtmlxTreeRetryWhile:
			while(dhtmlxTreeRetry<10){
				try{

					((JavascriptExecutor)dr).executeScript("var _treenames="+treeName+";"            
							+ "function performNodeAction(action, childNode)"
							+ "{ "
							+"switch (action.toUpperCase()) {"
							+"case 'SELECT':"
							+"myTree.selectItem(childNode); "
							+"myTree.callEvent('onSelect', [childNode, true]);"
							+"myTree.callEvent('onClick', [childNode, true]);"	                               
							+"break;"
							+"case 'CHECKED':"
							+"var checkOption = true;"
							+"var isChecked = myTree.isItemChecked(childNode);"
							+"myTree.setCheck(childNode, true);"                               
							+"if ((checkOption && isChecked == 0) || (!checkOption && isChecked == 1)) {"
							+"myTree.callEvent('onCheck', [childNode, true])"
							+"}"
							+"break;"
							+"case 'UNCHECKED':"
							+"var checkOption = true;"
							+ "var isChecked = myTree.isItemChecked(childNode);"
							+ "myTree.setCheck(childNode, false);"
							+ "if ((checkOption && isChecked == 0) || (!checkOption && isChecked == 1))"
							+ " {"
							+ "myTree.callEvent('onCheck', [childNode, true])"
							+ "}"
							+ "break;"

	                     + "} "
	                     + "} "
	                     + "function drillUpDownToggle(treeNode, action, rootNode)"
	                     + "{ "                    
	                     + "var rootNode = (rootNode) ? rootNode : 0; "
	                     + "var subItem = myTree.getSubItems(rootNode).split(','); "
	                     + "for (var i = 0; i < subItem.length; i++){ "
	                     + "var itemtext = (myTree.getItemText(subItem[i])) ? myTree.getItemText(subItem[i]).toUpperCase() : ''; "

	                     + "treeNode[0] = (treeNode[0]) ? treeNode[0] : ''; "
	                     + "itemtext = itemtext.replace(/&AMP;/g, '&');" 
	                     //+ "itemtext = itemtext.replace(/&#39;/g, '\'');"
	                     + "if(itemtext != '' && (itemtext === treeNode[0].toUpperCase()) ){ "   
	                     + "treeNode.shift(); "
	                     + "myTree.openItem(subItem[i]); var childNode = subItem[i]; "
	                     + "if(treeNode.length == 0) {performNodeAction(action, childNode); "
	                     + "} "
	                     + "else { drillUpDownToggle(treeNode, action, childNode); "
	                     + "} } } } "
	                     + "var __treeNode=\'" + treeNodeValue +"\';"
	                     + "var __action=\'" + treeAction +"\';"
	                     +"var __tree = _treenames;"                  
	                     + "var __searchText = ''; "
	                     + "var treeNode = (__treeNode) ? __treeNode.split('||@@||') : []; "
	                     + "var myTree; "
	                     + "if (__tree.dimensionMembersData){myTree = __tree.getTree().tree;}else if (__tree.tree){myTree = __tree.tree;}else{myTree = __tree;"
	                     + "} "
	                     + "if(__searchText && __searchText != '' && __searchText.length != 0){ var node = myTree.findItem(__searchText,false, true); "
	                     + "} if(__tree && treeNode.length > 0){ drillUpDownToggle(treeNode, __action); }"
							);
					break dhtmlxTreeRetryWhile;
				}catch(Exception e){
					dhtmlxTreeRetry=dhtmlxTreeRetry+1;
					if(dhtmlxTreeRetry>10){
						Assert.fail();
						Common.quitProcess();
					}
				}
			}
		Driver.logger.info("Tree "+treeAction+" passed successfully");		
	}




	public static WebElement dhtmlxTreeReturn(String treeName,String treeNodeValue,String treeAction )throws Exception{

		WebElement dd = (WebElement)((JavascriptExecutor)dr).executeScript("var _treenames="+treeName+";"            
				+ "function performNodeAction(action, childNode)"
				+ "{ "
				+"switch (action.toUpperCase()) {"
				+"case 'SELECT':"
				+"myTree.selectItem(childNode); "
				+"myTree.callEvent('onSelect', [childNode, true]);"
				+"myTree.callEvent('onClick', [childNode, true]);"	                               
				+"break;"
				+"case 'CHECKED':"
				+"var checkOption = true;"
				+"var isChecked = myTree.isItemChecked(childNode);"
				+"myTree.setCheck(childNode, true);"                               
				+"if ((checkOption && isChecked == 0) || (!checkOption && isChecked == 1)) {"
				+"myTree.callEvent('onCheck', [childNode, true])"
				+"}"
				+"break;"
				+"case 'UNCHECKED':"
				+"var checkOption = true;"
				+ "var isChecked = myTree.isItemChecked(childNode);"
				+ "myTree.setCheck(childNode, false);"
				+ "if ((checkOption && isChecked == 0) || (!checkOption && isChecked == 1))"
				+ " {"
				+ "myTree.callEvent('onCheck', [childNode, true])"
				+ "}"
				+ "break;"

	                     + "} "
	                     + "} "
	                     + "function drillUpDownToggle(treeNode, action, rootNode)"
	                     + "{ "                    
	                     + "var rootNode = (rootNode) ? rootNode : 0; "
	                     + "var subItem = myTree.getSubItems(rootNode).split(','); "
	                     + "for (var i = 0; i < subItem.length; i++){ "
	                     + "var itemtext = (myTree.getItemText(subItem[i])) ? myTree.getItemText(subItem[i]).toUpperCase() : ''; "

	                     + "treeNode[0] = (treeNode[0]) ? treeNode[0] : ''; "
	                     + "itemtext = itemtext.replace(/&AMP;/g, '&');" 
	                     //+ "itemtext = itemtext.replace(/&#39;/g, '\'');"
	                     + "if(itemtext != '' && (itemtext === treeNode[0].toUpperCase()) ){ "   
	                     + "treeNode.shift(); "
	                     + "myTree.openItem(subItem[i]); var childNode = subItem[i]; "
	                     + "if(treeNode.length == 0) {performNodeAction(action, childNode); "
	                     + "} "
	                     + "else { drillUpDownToggle(treeNode, action, childNode); "
	                     + "} } } } "
	                     + "var __treeNode=\'" + treeNodeValue +"\';"
	                     + "var __action=\'" + treeAction +"\';"
	                     +"var __tree = _treenames;"                  
	                     + "var __searchText = ''; "
	                     + "var treeNode = (__treeNode) ? __treeNode.split('||@@||') : []; "
	                     + "var myTree; "
	                     + "if (__tree.dimensionMembersData){myTree = __tree.getTree().tree;}else if (__tree.tree){myTree = __tree.tree;}else{myTree = __tree;"
	                     + "} "
	                     + "if(__searchText && __searchText != '' && __searchText.length != 0){ var node = myTree.findItem(__searchText,false, true); "
	                     + "} if(__tree && treeNode.length > 0){ drillUpDownToggle(treeNode, __action); }"
				);
		Driver.logger.info("Tree "+treeAction+" passed successfully");	
		return dd;
	}


	public static void owcSelectCell(String owcname,String row, String column, String sheetid){		

		if(browservar.equals("IE")){
			((JavascriptExecutor) dr).executeScript("document.all."+owcname+".sheets('"+sheetid+"').cells("+row+","+column+").select()");
		}
		else
		{
			String Col = HA.Utilities.ReadXML.getEquivColumn(column);
			System.out.println(Col);
			((JavascriptExecutor) dr).executeScript("ss.protect(false);ss.spreadsheet.api.setSelectedRange('"+Col+row+":"+Col+row+
					"');ss.protect(false);");
		}

	}

	/**
	 * owcInput: will input the values into OWC sheet.
	 * @param row: row index to input  
	 * @param column: column index to input
	 * @param value: value 
	 * @param owcname: ownName
	 */
	public static void owcInput(String row,String column,String value, String owcname)throws Exception{	

		String jsfunc=null;

		if(browservar.equals("IE")){

			jsfunc="document.all."+owcname+".cells("+row+","+column+").value = '"+value+"';";
			System.out.println(jsfunc);
			((JavascriptExecutor) dr).executeScript("document.all."+owcname+".cells("+row+","+column+").value = '"+value+"';");

		}else{

			jsfunc="ss.spreadsheet.api.disableScreenUpdate();ss.spreadsheet.api.setValue('C6:N15','500');ss.spreadsheet.api.enableScreenUpdate();";
			System.out.println(jsfunc);
			((JavascriptExecutor) dr).executeScript(jsfunc);			

		}

	}

	public static void spreadsheetswitchsheets(String owcname,String sheetName){
		((JavascriptExecutor)dr).executeScript("var ssobj;"            
				+ "ssobj = eval('document.all.'+'"+owcname+"');"
				+ "for (var i = 1; i <= ssobj.sheets.count; i++){"
				+ "if (ssobj.sheets(i).name == '"+sheetName+"') {"
				+ "ssobj.sheets(i).activate();}}");		
	}

	public static void spreadsheetswitchsheets(String owcname,int sheetid){

		((JavascriptExecutor)dr).executeScript("var ssobj;"        
				+ "if (!ssobj) {ssobj = eval('document.all.'+'"+owcname+"');}"
				+ "ssobj.sheets("+sheetid+").activate();");		
	}


	/**
	 * getBrowser(): will return the browserVersion
	 * 
	 */

	public static String getBrowser()throws Exception{
		String s = (String) ((JavascriptExecutor) dr).executeScript("return navigator.appName;");
		System.out.println(s);
		Capabilities cap=((RemoteWebDriver) dr).getCapabilities();
		String browsername=cap.getBrowserName();
		String browserversion=cap.getVersion();
		System.out.println("Browser details: "+browsername+ " " +browserversion);
		return s;
	}


	/**
	 * owcVerify: will verify owcSheetName
	 * @param spreadsheet
	 * @param controltype
	 */
	public static void owcVerify(String spreadsheet, int controltype, String expecteddata)throws Exception{		
		String script = null;
		if(browservar.equals("IE")){
			script = "var spreadsheetname =\'"+spreadsheet+"\';var controltype =\'"+controltype+"\';var tmpxmlsheet;"
					+ "if(controltype == 0){"
					+ "var manksheet = eval('document.'+spreadsheetname);"
					+ "return ExportToExcel(document.CommonDialog1, manksheet, false, false);"
					+ "} else if (controltype == 1){"
					+ "var spread; "
					+ "tmpxmlsheet = CreateSpreadsheet();"
					+ "spread = eval('document.all.'+spreadsheetname);"
					+ "tmpxmlsheet.xmldata = spread.xmldata;"
					+ "return tmpxmlsheet.xmldata"
					+ "}"	
					+ " else if (controltype == 2){"
					+ "var spread; "
					+ "tmpxmlsheet = CreateSpreadsheet();"
					+ "spread = eval('document.'+spreadsheetname);"
					+ "tmpxmlsheet.xmldata = spread.XMLData;"
					+ "return tmpxmlsheet.xmldata"
					+ "}"		
					+ "function ExportToExcel(exporter, spreadsheet, appendDate, applyFormat, clearRange, columnsToHide, rowsToHide){ "
					+ "var tmpSpreadsheet; "
					+ "try{ "
					+ "tmpSpreadsheet = CreateSpreadsheet(); "
					+ "tmpSpreadsheet.xmldata = spreadsheet.xmldata; "
					+ "for (var iCnt = 1; iCnt <= tmpSpreadsheet.worksheets.count; iCnt++){ "
					+ "tmpSpreadsheet.Sheets(iCnt).Protection.Enabled = false; "
					+ "tmpSpreadsheet.Sheets(iCnt).UsedRange.Hyperlink.Delete(); "
					+ "if(applyFormat && iCnt == 1) "
					+ "{ "
					+ "FormatExportData(tmpSpreadsheet.Sheets(iCnt), clearRange, columnsToHide, rowsToHide); "
					+ "} "
					+ "tmpSpreadsheet.Sheets(iCnt).Protection.AllowFormattingColumns = true; "
					+ "tmpSpreadsheet.Sheets(iCnt).Protection.Enabled = true; "
					+ "} "
					+ "} "				
					+ "catch( e ) "
					+ "{ "
					+ "} "
					+"return tmpSpreadsheet.xmldata"
					+"}";
		}else{
			script = "return getXMLForAutomationHelp();";
		}
		String xmldata = (String) ((JavascriptExecutor) dr).executeScript(script);

		if(Common.browserName().equalsIgnoreCase("GC"))
		{
			if(HA.Utilities.Xmlcomparison.compareResult(xmldata, expecteddata))
			{
				Driver.logger.info("true");
			}
			else
			{
				Driver.logger.info("false");
				Assert.fail();
			}
		}
		else
			HA.Utilities.File.fcompare(xmldata,expecteddata);

		Driver.logger.info("owcVerification done successfully");		

	}
	
	public static void owcVerifyRange(String spreadsheet, int controltype, String expecteddata,int row,int column)throws Exception{		
		String script = null;
		if(browservar.equals("IE")){
			script = "var spreadsheetname =\'"+spreadsheet+"\';var controltype =\'"+controltype+"\';var tmpxmlsheet;"
					+ "if(controltype == 0){"
					+ "var manksheet = eval('document.'+spreadsheetname);"
					+ "return ExportToExcel(document.CommonDialog1, manksheet, false, false);"
					+ "} else if (controltype == 1){"
					+ "var spread; "
					+ "tmpxmlsheet = CreateSpreadsheet();"
					+ "spread = eval('document.all.'+spreadsheetname);"
					+ "tmpxmlsheet.xmldata = spread.xmldata;"
					+ "return tmpxmlsheet.xmldata"
					+ "}"	
					+ " else if (controltype == 2){"
					+ "var spread; "
					+ "tmpxmlsheet = CreateSpreadsheet();"
					+ "spread = eval('document.'+spreadsheetname);"
					+ "tmpxmlsheet.xmldata = spread.XMLData;"
					+ "return tmpxmlsheet.xmldata"
					+ "}"		
					+ "function ExportToExcel(exporter, spreadsheet, appendDate, applyFormat, clearRange, columnsToHide, rowsToHide){ "
					+ "var tmpSpreadsheet; "
					+ "try{ "
					+ "tmpSpreadsheet = CreateSpreadsheet(); "
					+ "tmpSpreadsheet.xmldata = spreadsheet.xmldata; "
					+ "for (var iCnt = 1; iCnt <= tmpSpreadsheet.worksheets.count; iCnt++){ "
					+ "tmpSpreadsheet.Sheets(iCnt).Protection.Enabled = false; "
					+ "tmpSpreadsheet.Sheets(iCnt).UsedRange.Hyperlink.Delete(); "
					+ "if(applyFormat && iCnt == 1) "
					+ "{ "
					+ "FormatExportData(tmpSpreadsheet.Sheets(iCnt), clearRange, columnsToHide, rowsToHide); "
					+ "} "
					+ "tmpSpreadsheet.Sheets(iCnt).Protection.AllowFormattingColumns = true; "
					+ "tmpSpreadsheet.Sheets(iCnt).Protection.Enabled = true; "
					+ "} "
					+ "} "				
					+ "catch( e ) "
					+ "{ "
					+ "} "
					+"return tmpSpreadsheet.xmldata"
					+"}";
		}else{
			script = "return getXMLForAutomationHelp();";
		}
		String xmldata = (String) ((JavascriptExecutor) dr).executeScript(script);

//		HA.Utilities.File.fcompare(xmldata,expecteddata);		
//		if(HA.Utilities.Xmlcomparison.compareResult(xmldata, expecteddata,3,16380))
		if(HA.Utilities.Xmlcomparison.compareResult(xmldata, expecteddata,row,column))
		{
			Driver.logger.info("true");
		}
		else
		{
			Driver.logger.info("false");
			Assert.fail();
		}

		Driver.logger.info("owcVerification done successfully");		

	}

	public static void htmlVerify(String htmlData, String expecteddata)throws Exception{			
		HA.Utilities.File.htmlFileCompare(htmlData,expecteddata);
		Driver.logger.info("HTML verification done successfully");
	}

	/**
	 * wexInput: will input on wex
	 */

	public static void wexInput()throws Exception{
		String jsfunc=null;
		jsfunc="ss.spreadsheet.api.disableScreenUpdate();ss.spreadsheet.api.setValue('C12:C12','500');ss.spreadsheet.api.enableScreenUpdate();";
		System.out.println(jsfunc);
		((JavascriptExecutor) dr).executeScript(jsfunc);
		Driver.logger.info("WEX Input done successfully");
	}

	/**
	 * dhtmlxGridRowcount: will get the no.of rows in a Grid.
	 * @param gridName: provide grideName
	 * @return
	 */
	public static Object dhtmlxGridRowcount(String gridName)throws Exception{
		Object count = ((JavascriptExecutor) dr).executeScript(""
				+ "if(" + gridName+".grid){"
				+ "var gridObj=" + gridName+".grid;}else{"
				+ "var gridObj=" + gridName+";};"+"return gridObj.getRowsNum()");
		Driver.logger.info("get dhtmlxGridCount successfully  "+count);
		return count;
	}	
	public static Object dhtmlxGridRowExists(String gridName)throws Exception{
		Object count = ((JavascriptExecutor) dr).executeScript(""
				+ "if(" + gridName+".grid){"
				+ "var gridObj=" + gridName+".grid;}else{"
				+ "var gridObj=" + gridName+";};"+"return gridObj.doesRowExist()");
		Driver.logger.info("get dhtmlxGridCount successfully  "+count);
		return count;
	}	
	public static Object dhtmlxGridColumnCount(String gridName)throws Exception{		
		Object count = ((JavascriptExecutor) dr).executeScript(""
				+ "if(" + gridName+".grid){"
				+ "var gridObj=" + gridName+".grid;}else{"
				+ "var gridObj=" + gridName+";};"+"return gridObj.getColumnsNum()");
		Driver.logger.info("get dhtmlxGridCount successfully  "+count);
		return count;	
	}

	public static boolean dhtmlxGridExpectedCount(String gridName,Object expectedRowcount) throws Exception{
		int i=0;
		boolean result = false;
		while(result==false){		
			Object gridCount = dhtmlxGridRowcount(gridName);			
			if(gridCount==expectedRowcount){
				result = true;
				break;
			}else{				
				i=i+1;
				if(i==50){					
					break;
				}
				result = false;
			}
		}
		return result;

	}

	public static Object owcSheetName(String owcName)throws Exception{		
		Object owcSheetName = ((JavascriptExecutor) dr).executeScript("return document.all."+owcName+".activesheet.name;");
		Driver.logger.info("get OWCsheet name successfully");
		return owcSheetName;

	}

	public static void  Calendar(String textBoxId,String value)throws Exception {
		if(value.length()==4){

			((JavascriptExecutor) dr).executeScript("document.getElementById('"+textBoxId+"').value='"+value+"';"
					+ "fnOnReloadYr()");
		}else if(value.length()>4&&value.length()<8){

			((JavascriptExecutor) dr).executeScript("document.getElementById('"+textBoxId+"').value='"+value+"';"
					+ "fnOnReloadMonth()");
		}else{
			((JavascriptExecutor) dr).executeScript("document.getElementById('"+textBoxId+"').value='"+value+"';"
					+ "fnOnReloadDay()");
		}
	}

	public static void  Calendar(String textBoxId,String imageId,String month)throws Exception {

		((JavascriptExecutor) dr).executeScript("document.getElementById('"+textBoxId+"').value='"+month+"';"
				+ "fnOnReloadYr()");
		System.out.println("Calendar text pass done");
		//		Common.clickElement("id", imageId);
		System.out.println("Calendar click on Image pass done");
		Thread.sleep(2000);
		Common.navigatePage();	
	}


	//**Alerts*/
	/**
	 * alerts: will get the text on alert  compare with expected text and click on OK or Cancel.
	 * @param alertActions: pass "OK" for OK and Yes. provide cancel or No to dismiss the alert.
	 * @param alerttext: Expected text on alert.
	 */
	public static void alerts(String alertActions,String alerttext)throws Exception{		
		Alert alert = dr.switchTo().alert();		
		String alertTextget = (alert.getText().trim());
		String alertText=alertTextget;		
		Driver.logger.info("Text Comparison:\nExpected Text:\n"+alerttext+"\nActualtext:\n"+alertText+"\nResult:    "+alerttext.equals(
				alertText));		
		if(alertActions.equals("OK")){
			alert.accept();	
		}else{
			alert.dismiss();
		}	
		
		Driver.logger.info("alerts completed successfully");
	}



	//**Calendar*/
	/**WIP*/
	public static void calender()throws Exception{
		clickElement("id","imgStrMP");
		clickElement("id","td0");	
	}	

	//**drag and drop**//	
	/**
	 * dragdrop: will drag the element from source and drag it on destination on same page.
	 * @param ddSourceeElemfindBy: source element find by, like by "id", "name", "className", "xpath".
	 * @param ddSourcename: source element name
	 * @param ddTargetElemFindBy: target element find by, like by "id", "name", "className", "xpath".
	 * @param ddTargetsname:  target element name
	 */
	public static void dragdrop(String ddSourceeElemfindBy, String ddSourcename,String ddTargetElemFindBy, String ddTargetsname)throws Exception{
		new Actions(dr).dragAndDrop(findElement(ddSourceeElemfindBy,ddSourcename),findElement(ddTargetElemFindBy,ddTargetsname)).build().
		perform();
	}	


	public static void dragdrop(WebElement ddSourcename,String ddTargetElemFindBy, String ddTargetsname)throws Exception{
		new Actions(dr).dragAndDrop(ddSourcename,findElement(ddTargetElemFindBy,ddTargetsname)).build().perform();
	}

	/**
	 * dragdropClickHold: will drag the element from source and drag it on destination on same page.
	 * @param ddSourceeElemfindBy: source element find by, like by "id", "name", "className", "xpath".
	 * @param ddSourcename: source element name
	 * @param ddTargetElemFindBy: target element find by, like by "id", "name", "className", "xpath".
	 * @param ddTargetsname:  target element name
	 * @throws Exception 
	 */
	public static void dragdropClickHold(String ddSourceeElemfindBy, String ddSourcename,String ddTargetElemFindBy, String ddTargetsname)throws Exception{
		Actions ac = new Actions(dr);
		ac.clickAndHold(findElement(ddSourceeElemfindBy, ddSourcename)).moveToElement(findElement(ddTargetElemFindBy, ddTargetsname)).
		release().build().perform();
	}

	//**mouseOver**//	
	/**
	 * mouseOver: will get the mouse over action on a element.
	 * @param elemfindBY: find element by "id", "name", "className", "xpath".
	 * @param elemfindText find element text.
	 * @throws Exception 
	 */
	public static void mouseOver(String elemfindBY, String elemfindText) throws Exception{
		Actions ac = new Actions(dr);			
		ac.moveToElement(findElement(elemfindBY,elemfindText));
		ac.perform();
	}

	/**
	 * mouseOverClick: will get the mouse over action on a element and click on that element.
	 * @param elemfindBY: element by "id", "name", "className", "xpath".
	 * @param elemfindText: element text
	 */
	public static void mouseOverClick(String elemfindBY, String elemfindText)throws Exception{
		Actions ac = new Actions(dr);
		WebElement tr =findElement(elemfindBY,elemfindText);
		ac.moveToElement(tr).click(tr);
		ac.build();
		ac.perform();
	}

	//**Capturing Screenshots**//
	/**
	 * screenShot: will capture the screen shot and save the screen shot a jpg file.
	 * @param saveAs: save the file on the specified location

	 */

	public static void screenShot(String saveAs)throws Exception{
		File scrFile = ((TakesScreenshot)dr).getScreenshotAs(OutputType.FILE);			
		System.out.println("Sy6"+System.getProperty("user.dir")+"/TestLogs/screenShots/"+(saveAs+HA.Utilities.File.GetDateTime())+".jpg");
		FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"/TestLogs/screenShots/"+(saveAs+HA.Utilities.File.GetDateTime
				())+".jpg"));
		Driver.logger.info("screenshot saved successfully");		
	}	


	//**to find the web element**//	
	/**
	 * tagNametoFind: internal user only: used for get the no.of elements based on tagName.
	 * @param tagNameToFind
	 */
	public static int tagNameToFind(String tagNameToFind)throws Exception{			
		System.out.println("TofoundFrames");
		List<WebElement> frames1 = dr.findElements(By.tagName(tagNameToFind));
		System.out.println("FRAMES!Size "+frames1.size());
		return frames1.size();
	}

	public static void framesWait()throws Exception{
		int ss = tagNameToFind("frame");
		while(ss<1){
			ss = tagNameToFind("frame");
		}
	}




	/**
	 * callSubModule:will click on  submodules on Maintenance.
	 * @param callsubmodule: pass id of the submodule on maintenance.	 
	 */
	public static void callSubModule(String callsubmodule)throws Exception {
		((JavascriptExecutor) dr).executeScript("$(oPopup.document.getElementById('"+callsubmodule+"')).click();");
		Driver.logger.info(callsubmodule +" clicked successfully");		
	}

	/**
	 * fileUpload: will upload the file
	 * @param filepath: filepath
	 * @throws Exception 
	 */
	public static void fileUpLoad(String elemfindBY, String elemfindText, String filepath) throws Exception{
		WebElement fileInput = findElement(elemfindBY, elemfindText);
		fileInput.sendKeys(filepath);
	}

	/**
	 * fileUpload: will upload the file
	 * @param filepath: filepath
	 */
	//	public static void fileUpLoad(String filepath)throws Exception{
	//		WebElement fileInput = dr.findElement(By.id("strImageName"));
	//		fileInput.sendKeys(filepath);
	//	}

	public static void fileUpLoad(String filepath)throws Exception{

		int filepathProvided = 0;
		while(filepathProvided<3){
			String ss = dr.findElement(By.id("strImageName")).getText();
			Driver.logger.info("provided file path is:  "+ss);
			if(!ss.equalsIgnoreCase(filepath)||ss.equals(null)){	
				WebElement fileInput = dr.findElement(By.id("strImageName"));
				Sync.processSync(4000);
				fileInput.sendKeys(filepath);
				filepathProvided++;
				System.out.println("filepathProvided: "+filepathProvided);
			}else{
				filepathProvided = 20;
			}
		}
	}

	/**
	 * boxFileUpload: will upload the file
	 * @param linktext: link text name
	 * @param filepath: filepath
	 */
	public static void boxFileUpload(String linktext, String filepath)throws Exception{
		dr.findElement(By.linkText(linktext)).click();
		Thread.sleep(2000);
		Alert alert = dr.switchTo().alert();
		alert.sendKeys(filepath);
		alert.accept();
		Driver.logger.info("file uploaded successfully");
	}

	/**
	 * navigatePage: will focus the access on the currency page
	 */
	public static void navigatePage()throws Exception{
		int navigatePage = 0;
		navigateWhile:
			while(navigatePage<10){
				try{
					switchFrames(0);
					switchFrames(2);
					switchFrames(2);
					break navigateWhile;
				}catch(Exception e){
					navigatePage=navigatePage+1;
					if(navigatePage>=10){
						Assert.fail();
						Common.quitProcess();
					}
				}
			}
		Driver.logger.info("navigatePage successfully");
	}

	/**
	 * closeCurrentWindow: will close the currency window.
	 */

	//CloseCurent Window
	public static void closeCurrentWindow()throws Exception{		
		dr.close();
		Driver.logger.info("Window Closed Successfully");
	}

	//CloseAllWindows
	/**
	 * closeAll: will closed all the windows and quit the selenium driver.
	 */
	public static void closeAll()throws Exception{
		try
		{
		if(browservar.equals("IE"))
		{
			try{
				WindowsUtils.killByName("WerFault.exe");	
				Driver.logger.info("WerFault.exe closed successfully");
			}catch(Exception e){
				Driver.logger.info("No WerFault.exe processes to kill");
			}
		}
		dr.quit();
		Driver.logger.info("Driver quit successfully");
		if(browservar.equals("IE")){
			Common.quitProcess();
		}
		//		driverThread.interrupt();
		Driver.logger.info("Driver closed successfully");
		
		}
		catch(Exception e)
		{
			Driver.logger.info("Exception in close all is "+e);
		}
	}

	/**
	 * quitProcess: will kill the selenium driver based on driver. 
	 * @throws IOException
	 * @throws InterruptedException
	 */


	public static void quitProcess()throws Exception {
		dr.close();
		dr.quit();
	}	



	public static String getSysTime()throws Exception{
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("HH_mm_ss");
		String ss = sdf.format(cal.getTime());
		return ss;
	}

	//**Capturing Screenshots**/
	/**
	 * screenShot: will capture the screen shot and save the screen shot a jpg file.
	 * @param saveAs: save the file on the specified location

	 */

	public static void sourceScreenShot(String saveAs)throws Exception{
		File scrFile = ((TakesScreenshot)dr).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(saveAs));
		Driver.logger.info("screenshot saved successfully");
	}

	public static void notificationTextVerification(String textToVerify) throws Exception{

		int i=0;
		while(i<=2000){			
			Common.switchFrames(0);
			Common.switchFrames(1);	
			String actuaText=Common.findElement("xpath","//div/table/tbody/tr/td").getText();
			//			Sync.waitPresenceOfElementLocated("id", "mfMessage");
			//			Sync.waitElementVisible("id", "mfMessage");
			//			String actuaText = (String)((JavascriptExecutor)dr).executeScript("var string=top.frames[1].document.getElementById('mfMessage').innerText;return string;").toString();
			//			Driver.logger.info("Notification read successully");
			System.out.println(actuaText);
			if(actuaText.contains("Error in submission of process. Please try your action again or contact your Administrator.")){

				Driver.logger.info("Actual Text:"+actuaText);
				Driver.logger.info("textToVerify"+textToVerify);
				Driver.logger.error("Error while submission of ADC tasks");
				clickElement("id", "mfClose");
				Common.screenShot("consolidation submission");			
				Assert.fail();
				Common.closeAll();
				break;				
			}else if(actuaText.contains(textToVerify)){			
				Driver.logger.info("Actual Text:"+actuaText);
				Driver.logger.info("textToVerify"+textToVerify);
				Driver.logger.info("Verifycation passed");
				clickElement("id", "mfClose");

				break;
			}else if(((((actuaText).length())>=1)&&(!(actuaText).equals(textToVerify)))||i==2000){				
				Driver.logger.info("Actual Text:"+actuaText);
				Driver.logger.info("textToVerify"+textToVerify);
				Driver.logger.error("not an expected text");
				clickElement("id", "mfClose");				
				Driver.logger.error("notificationMessage not correct");
				Assert.fail();
				Common.closeAll();
				break;
			}else{

				System.out.println("iiiiiiii: "+(i));
				i=i+1;
			}
		}
	}


	public static void notificationTextVerification(String textToVerify,boolean fileDownLoad) throws Exception{

		Boolean ss=false;
		int i=0;
		while(ss==false){			
			Common.switchFrames(0);
			Common.switchFrames(1);		
			String actuaText=Common.findElement("xpath","//div/table/tbody/tr/td").getText();
			System.out.println(actuaText);
			if(actuaText.contains("Error in submission of process. Please try your action again or contact your Administrator.")){

				Driver.logger.info("Actual Text:"+actuaText);
				Driver.logger.info("textToVerify"+textToVerify);
				Driver.logger.error("Error while submission of ADC tasks");
				//		clickElement("id", "mfClose");
				Common.screenShot("consolidation submission");			
				Assert.fail();
				Common.closeAll();
				break;				
			}else if(actuaText.contains(textToVerify)){			
				Driver.logger.info("Actual Text:"+actuaText);
				Driver.logger.info("textToVerify"+textToVerify);
				Driver.logger.info("Verifycation passed");

				//		clickElement("id", "mfClose");
				ss=true;
				break;
			}else if(((((actuaText).length())>=1)&&(!(actuaText).equals(textToVerify)))||i==2000){				
				Driver.logger.info("Actual Text:"+actuaText);
				Driver.logger.info("textToVerify"+textToVerify);
				Driver.logger.error("not an expected text");
				clickElement("id", "mfClose");
				ss=true;
				Driver.logger.error("notificationMessage not correct");
				Assert.fail();
				//		Common.closeAll();
				break;
			}else{

				System.out.println("iiiiiiii: "+(i));
				i=i+1;
			}
		}
	}

	public static String getTagName(String elemfindBY,String elemfindText,String tagName)throws Exception{
		return findElement(elemfindBY, elemfindText).getAttribute(tagName);

	}

	public static int getElementCount(String elemfindBY,String elemfindText)throws Exception{
		int elementCount=0;
		List<WebElement> element = null;		
		if(elemfindBY=="id"){
			element = dr.findElements(By.id(elemfindText));			
		}
		else if(elemfindBY=="name"){
			element = dr.findElements(By.name(elemfindText));				
		}
		else if(elemfindBY=="className"){
			element = dr.findElements(By.className(elemfindText));				
		}			
		else if(elemfindBY=="linkText"){				
			element = dr.findElements(By.linkText(elemfindText));
		}		
		else if(elemfindBY=="xpath"){
			element = dr.findElements(By.xpath(elemfindText));
		}
		else if(elemfindBY=="css"){
			element = dr.findElements(By.cssSelector(elemfindText));
		}
		else if(elemfindBY=="pLinkText"){
			element = dr.findElements(By.partialLinkText(elemfindText));
		}
		else if(elemfindBY=="tagName"){
			element = dr.findElements(By.tagName(elemfindText));
		}
		Driver.logger.info("Element with "+ elemfindBY +"  "+ elemfindText +" found");	
		if(element == null){
			elementCount = 0;
		}else{
			elementCount = element.size();

		}
		return elementCount;
	}

	public enum SelectBy {
		TEXT,
		VALUE,
		INDEX
	}

	public static void catchExceptions(Exception e){ 	
		String functionName=Thread.currentThread().getStackTrace()[2].getMethodName();					
		Driver.logger.error(functionName +" Method failed with below exception:\n"+e.getStackTrace());
	}

	public static void jsClick(String id){
		((JavascriptExecutor) dr).executeScript("SubmitIfrm('Next',1)");
	}

	public static void jsClickElement(String id){
		((JavascriptExecutor) dr).executeScript("document.getElementById('"+id+"').click();");
	}

	public static void saveModelPopup(){
		((JavascriptExecutor)dr).executeScript("submitFrm('Load')");
		Driver.logger.info("Successfully loaded");
	}
	public static void closeNotification() throws Exception {
		Common.navigatePage();
		Common.switchFrames(0);
		Common.switchFrames(1);
		if(dr.findElement(By.xpath("//div/table/tbody/tr/td")).isDisplayed()){
			String notificationText=Common.findElement("xpath","//div/table/tbody/tr/td").getText();
			System.out.println("Notification Text: "+notificationText);
			Driver.logger.info("::::::::::::::::::::::"+notificationText);
			dr.findElement(By.id("mfClose")).click();
			Driver.logger.info("mfClose Button click success");
		}else {
			System.out.println("No Notification to Close");
			Driver.logger.error("No Notification to Close");
		}
	}

	public static void popupHandling(String elemfindBY,String elemfindText,String cellIndex, String selectOption) throws Exception {
		Common.switchFrames(elemfindBY, elemfindText);
		Common.clickElement("id", "idToolFilter");
		Common.textBoxInput("className", "excelText", selectOption);
		Thread.sleep(5000);
		Common.dhtmlxGrid(selectOption,cellIndex,"objgrid","dummy","Select");
		Common.clickElement("id", "btnSave");
		Common.navigatePage();
		Common.switchFrames("id","Listctrl");
	}

	public static void pressTabKey(String elemfindBY, String elemfindText) throws Exception {
		findElement(elemfindBY, elemfindText).sendKeys(Keys.TAB);
	}

	public static void DLRnotification(String textToVerify) throws Exception{
		Boolean ss=false;
		int i=0;
		while(ss==false){
			Common.switchFrames(0);
			Common.switchFrames(1);
			Sync.wait.pollingEvery(1, TimeUnit.SECONDS);
			String actuaText=Common.findElement("xpath","//div/table/tbody/tr/td").getText();
			System.out.println("Expected Text: "+textToVerify);
			System.out.println("Actual Text: "+actuaText);
			if(((((actuaText).length())>=1)&&(!(actuaText).equals(textToVerify)))||i==2000){	
				Driver.logger.info("Actual Text:::::: "+actuaText);
				Driver.logger.info("Expected Tex::::: "+textToVerify);
				clickElement("id", "mfClose");
				ss=true;
				Driver.logger.error("Notification Message not correct");
				break;
			}
			else{
				System.out.println("iiiiiiii: "+(i++));
				i=i+1;
			}
		}
	}


	public static void boxFileNotification(String textToVerify) throws Exception {
		Boolean ss=false;
		while(ss==false){
			String[] file=textToVerify.split("T_");
			String expectedText;
			if(file[0].equals("Input\\S"))
				expectedText="ST_"+file[1];
			else
				expectedText="RT_"+file[1];
			Sync.wait.pollingEvery(300, TimeUnit.MILLISECONDS);
			Sync.wait.until(ExpectedConditions.textToBePresentInElement(Common.findElement("className","notification_title"), expectedText
					));
			String actuaText=Common.findElement("className","notification_title").getText();
			Driver.logger.info("Expected Text: "+expectedText);
			Driver.logger.info("Actual Text::: "+actuaText);
			if(Common.findElement("className","notification_title").isDisplayed())
				if(actuaText!=null){
					Driver.logger.info("Notification button click success");
					if(actuaText.equals(expectedText))
						ss=true;
					break;
				}
		}
	}

	public static void DefaultsDLRDropDown(String index, String colValue) throws Exception {
		if ((index).equals("1")){
			Sync.waitElementClickable(60, "xpath", "//tr/td[5]/select[@name='strSourceAccount']");
			Select s1 = new Select(dr.findElement(By.xpath("//tr/td[5]/select[@name='strSourceAccount']")));
			s1.selectByVisibleText(colValue);
		}else{
			Sync.waitElementClickable(60, "xpath", "//tr["+index+"]/td[5]/select[starts-with(@name,'strSource')]");
			Select s1 = new Select(dr.findElement(By.xpath("//tr["+index+"]/td[5]/select[starts-with(@name,'strSource')]")));
			s1.selectByVisibleText(colValue);
		}

	}

	public static int windowCounts(){
		Set <String> ids=dr.getWindowHandles();	
		return ids.size();
	}

	public static String GetBaseURL(){
		return _properties.getProperty(HATF_properties.BASEURL);
	}

	public static void deletehtmlXGridRow(String controlType, String gridName, int rowIndex) throws Exception{
		((JavascriptExecutor) dr).executeScript(" var gridObj=" + gridName +";"
				+ " gridObj.grid.deleteRow("+ rowIndex + ");" );
	}

	public static void writeFile(String source){	
		try{				
			String fileName = "C:\\hataf\\source\\TestLogs\\runningStatus\\"+((HA.Utilities.File.GetDateTime()).split("\\_"))[0]+".txt";
			PrintWriter writeData = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
			writeData.println(source);
			writeData.close();

		}catch(Exception e){			
			File dir = new File("C:\\hataf\\source\\TestLogs\\runningStatus");
			dir.mkdir();
			writeFile(source);
		}

	}

	//Checks if alert is present
	public static Boolean isAlertPresent(){
		try{ 
			dr.switchTo().alert(); 
			return true; 
		}catch (Exception e){ 
			return false; 
		}
	}

	//Method to get all the options available in a dropdown
	public static List<WebElement> getAllOption(String elemfindBY,String elemfindText) throws Exception{
		Select select = new Select(findElement(elemfindBY,elemfindText));
		return select.getOptions();
	}


	public static boolean isElementdisplayed(String elemfindBY,String elemfindText)throws Exception{	
		if(findElements(elemfindBY, elemfindText).size()>0){
			return	findElement(elemfindBY, elemfindText).isDisplayed();
		}else {
			return false;
		}

	}

	//	public static void javascriptclickElement(String elemfindBY,String elemfindText) throws Exception{		
	//		WebElement element = findElement(elemfindBY,elemfindText);
	//		if(element.isDisplayed()){
	//			((JavascriptExecutor) dr).executeScript("arguments[0].scrollIntoView(true);", element);
	//			((JavascriptExecutor) dr).executeScript("arguments[0].click();", element);
	//		}
	//
	//		else
	//			ClickOnHiddenElement(element);
	//		Driver.logger.info(elemfindText+ " element click success");	
	//	}

	public static void javascriptclickElement(String elemfindBY,String elemfindText) throws Exception{	
		WebElement element = findElement(elemfindBY,elemfindText);
		if(element.isDisplayed()){
			((JavascriptExecutor) dr).executeScript("var el=arguments[0]; setTimeout(function() { el.scrollIntoView(true); }, 10);",element);	
			((JavascriptExecutor) dr).executeScript("var el=arguments[0]; setTimeout(function() { el.click(); }, 10);",element); 
		}

		else
			ClickOnHiddenElement(element);
		Driver.logger.info(elemfindText+ " element click success");	
	}

	public static void actionsKeyPress(Keys keyCode)throws Exception{
		new Actions(dr).sendKeys(keyCode).build().perform();		
	}


	public static void Clean_Automation_Environment(){
		String logs_FOLDER = System.getProperty("user.dir")+"/TestLogs/logs/";
		String compareresults_FOLDER = System.getProperty("user.dir")+"/TestLogs/Compare Results/";
		String screenshots_FOLDER = System.getProperty("user.dir")+"/TestLogs/screenShots/";
		String testoutput_FOLDER = System.getProperty("user.dir")+"/test-output/";
		String videos_FOLDER = System.getProperty("user.dir")+"/TestLogs/videos/";
		String buildlogs_FOLDER = System.getProperty("user.dir")+"/TestLogs/buildlogs/";
		String listener_FOLDER = System.getProperty("user.dir")+"/TestLogs/Listener txt files/";

		DeleteDirectoryOrFiles(logs_FOLDER,"FILES");
		DeleteDirectoryOrFiles(compareresults_FOLDER,"FILES");
		DeleteDirectoryOrFiles(screenshots_FOLDER,"FILES");
		DeleteDirectoryOrFiles(testoutput_FOLDER,"FILES");
		DeleteDirectoryOrFiles(videos_FOLDER,"FILES");
		DeleteDirectoryOrFiles(buildlogs_FOLDER,"FILES");
		DeleteDirectoryOrFiles(listener_FOLDER,"FILES");

	}

	public static void DeleteDirectoryOrFiles(String sDirToDelete,String sFilesOrDiretory){
		File directory = new File(sDirToDelete);
		if(!directory.exists())
		{
			System.out.println(directory+ " - Directory does not exist.");
			Driver.logger.info(directory+ " - Directory does not exist.");
		}
		else
		{
			try
			{ delete(directory,sFilesOrDiretory);}
			catch(IOException e)
			{e.printStackTrace();
			}
		}
	}
	public static void delete(File file ,String sOption) throws IOException{
		if(file.isDirectory())
		{
			if(file.list().length==0)
			{
				if(!sOption.equalsIgnoreCase("FILES"))
				{
					file.delete();
					System.out.println("Directory is deleted : "+ file.getAbsolutePath());
					Driver.logger.info("Directory is deleted : "+ file.getAbsolutePath());
				}
			}
			else
			{
				String files[] = file.list();
				for (String temp : files) 
				{
					File fileDelete = new File(file, temp);       	     
					delete(fileDelete,sOption);
				}
				if(file.list().length==0)
				{
					if(!sOption.equalsIgnoreCase("FILES"))
					{
						file.delete();
						System.out.println("Directory is deleted : "+ file.getAbsolutePath());
						Driver.logger.info("Directory is deleted : "+ file.getAbsolutePath());
					}
				}
			}
		}
		else
		{
			file.delete();
			System.out.println("File is deleted : " + file.getAbsolutePath());
			Driver.logger.info("File is deleted : " + file.getAbsolutePath());
		}
	}	


	public static void Regression_Run_Backup(){
		final String RegressionHistoryDir = System.getProperty("user.dir")+"/Regression Results/";
		final String LogfilesPath = System.getProperty("user.dir")+"/TestLogs/logs/";
		final String CompareResults = System.getProperty("user.dir")+"/TestLogs/Compare Results/";
		final String ScreenshotsFilesPath = System.getProperty("user.dir")+"/TestLogs/screenShots/";
		final String testoutputxmlfilespath= System.getProperty("user.dir")+"/test-output/";
		final String videosfilepath = System.getProperty("user.dir")+"/TestLogs/videos/";
		final String buildlogsfilepath = System.getProperty("user.dir")+"/TestLogs/buildlogs/";
		final String listenerfilepath = System.getProperty("user.dir")+"/TestLogs/Listener txt files/";

		String sFolderName=new SimpleDateFormat("MM-dd-yyyy").format(new Date());
		String sTime=new SimpleDateFormat("HH_mm_ss").format(new Date());
		File sRegHisDir = new File(RegressionHistoryDir+"/"+sFolderName);
		File sRegHisDir1 = new File(RegressionHistoryDir+"/"+sFolderName+"/"+sTime);
		sRegHisDir.mkdir();
		sRegHisDir1.mkdir();

		CopyFoldersWithFiles(LogfilesPath,RegressionHistoryDir+"/"+sFolderName+"/"+sTime+"/"+"logs"+"/");
		CopyFoldersWithFiles(CompareResults,RegressionHistoryDir+"/"+sFolderName+"/"+sTime+"/"+"Compare Results"+"/");
		CopyFoldersWithFiles(ScreenshotsFilesPath,RegressionHistoryDir+"/"+sFolderName+"/"+sTime+"/"+"screenShots"+"/");
		CopyFoldersWithFiles(videosfilepath,RegressionHistoryDir+"/"+sFolderName+"/"+sTime+"/"+"videos"+"/");
		CopyFoldersWithFiles(buildlogsfilepath,RegressionHistoryDir+"/"+sFolderName+"/"+sTime+"/"+"buildlogs"+"/");
		CopyFoldersWithFiles(listenerfilepath,RegressionHistoryDir+"/"+sFolderName+"/"+sTime+"/"+"Listener txt files"+"/");
		CopyFoldersWithFiles(testoutputxmlfilespath,RegressionHistoryDir+"/"+sFolderName+"/"+sTime+"/"+"test-output"+"/");
	}

	public static void CopyFoldersWithFiles(String  sSourceFolder,String sDescFolder){	
		File srcFolder = new File(sSourceFolder);
		File destFolder = new File(sDescFolder);
		if(!srcFolder.exists())
		{
			System.out.println(srcFolder+ "-Directory does not exist.");
			Driver.logger.info(srcFolder+ "-Directory does not exist.");
			//                System.exit(0);                        
		}else
		{
			try{
				copyFolder(srcFolder,destFolder);
			}catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	public static void copyFolder(File src, File dest) throws IOException{
		if(src.isDirectory())
		{
			if(!dest.exists())
			{
				dest.mkdir();
				System.out.println("Directory copied from " + src + "  to " + dest);
				Driver.logger.info("Directory copied from " + src + "  to " + dest);
			}
			String files[] = src.list();
			for (String file : files) 
			{
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				copyFolder(srcFile,destFile);
			}
		}else
		{
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest); 
			byte[] buffer = new byte[1024];
			int length; 
			while ((length = in.read(buffer)) > 0)
			{ out.write(buffer, 0, length);}
			in.close();
			out.close();
			System.out.println("File copied from " + src + " to " + dest);
			Driver.logger.info("File copied from " + src + " to " + dest);
		}
	}

	public static void waitForOverlaydisable(String elemfindBY,String elemfindText) throws Exception{


		System.out.println("waitForOverlaydisable");
		if(Common.browserName().equalsIgnoreCase("IE")){ 
			elemfindText = "loadingMsg";
			boolean visble = (boolean) ((JavascriptExecutor) dr).executeScript("var elem = document.getElementById('"+elemfindText+"');"
					+ "var result=false;"
					+ "if($(elem).is(':visible')){"
					+ "result = true;};else{"
					+ "result = false;};"
					+ "return result;"
					);
			System.out.println("visblevisblevisblevisblevisblevisblevisble"+visble);
			if(visble){
				System.out.println("waitForOverlaydisableifififif");
				boolean inVisible = false;
				while(inVisible==false){
					System.out.println("whilewhilewhilewhilewhilewhilewhile");
					inVisible = (boolean) ((JavascriptExecutor) dr).executeScript("var elem = document.getElementById('"+elemfindText+"');"
							+ "var result=false;"
							+ "if($(elem).is(':visible')==false){"
							+ "result = true;}else{"
							+ "result = false;}"
							+ "return result;");
				}
			}
		}
		else{
			System.out.println("Else");
			Sync.waitElementInvisible(elemfindBY,elemfindText);
			System.out.println("Else Ended");

		}


	}	

	public static void loadUrlinChrome(String url)throws Exception {
		Thread.sleep(3000);
		switchFrames(-1);
		switchFrames(2);
		WebElement frame = findElement("name", "myframe");
		String completeurl= _properties.getProperty(HATF_properties.BASEURL)+url;
		//			((JavascriptExecutor)dr).executeScript("arguments[0].document.location.href =  arguments[1];", frame, completeurl);
		((JavascriptExecutor)dr).executeScript("top.frames[2].frames[2].document.location.href=arguments[1];",frame,  completeurl);

		Thread.sleep(3000);
		Driver.logger.info("Frame URL loaded successfully");

	}

	public static void loadUrl(String url, String ID)throws Exception {
		Sync.processSync(3000);
		switchFrames(-1);
		switchFrames(2);
		WebElement frame = findElement("name", "myframe");
		String completeurl= _properties.getProperty(HATF_properties.BASEURL)+url;
		((JavascriptExecutor)dr).executeScript("top.frames[2].frames[2].document.location.href=arguments[1];",frame,  completeurl);
		Sync.processSync(3000);
		Driver.logger.info("Frame URL loaded successfully");
		navigatePage();
		Sync.waitPresenceOfElementLocated("id", ID);
		Sync.waitElementVisible("id", ID);
		Driver.logger.info("Page navigated successfully");
	}





	public static void doubleClick(String elemfindBY, String elemfindText) throws Exception{
		Actions doubleClick = new Actions(dr);
		doubleClick.moveToElement(findElement(elemfindBY,elemfindText)).doubleClick().build().perform();
	}

	public static void switchWindowsAfterClick(String elemfindBY, String elemfindText)throws Exception{
		String mainWindow = HA.SeleniumLib.Common.dr.getWindowHandle();		
		Driver.logger.info("mainWindow: "+mainWindow);
		Set <String> windows = new LinkedHashSet<String>();
		windows.add(mainWindow);
		Common.clickElement(elemfindBY, elemfindText);
		Sync.waitPageLoad();		
		windows=HA.SeleniumLib.Common.dr.getWindowHandles();
		while(windows.size()<=1){
			windows=HA.SeleniumLib.Common.dr.getWindowHandles();
		}
		HA.SeleniumLib.Common.dr.switchTo().window(mainWindow);	
		windows.remove(mainWindow);		
		for(String id:windows){
			Driver.logger.info("subWindows: "+id);
			HA.SeleniumLib.Common.dr.switchTo().window(id);
			Driver.logger.info("switching window to subwindow: "+id);
		}

	}

	public static void switchWindowsSingleClick(String elemfindBY, String elemfindText)throws Exception{

		String mainWindow = HA.SeleniumLib.Common.dr.getWindowHandle();		
		Set <String> windows = new LinkedHashSet<String>();
		windows.add(mainWindow);
		Common.clickElement(elemfindBY, elemfindText);
		Thread.sleep(8000);
		Sync.waitPageLoad();		
		windows=HA.SeleniumLib.Common.dr.getWindowHandles();
		HA.SeleniumLib.Common.dr.switchTo().window(mainWindow);	
		dr.close();
		windows.remove(mainWindow);		
		for(String id:windows){
			Driver.logger.info("Windows details are:"+id);
			HA.SeleniumLib.Common.dr.switchTo().window(id);
		}


	}

	public static void CheckNotificationMessage(String expectedText) throws Exception{
		Common.switchFrames(0);
		Common.switchFrames(1);	
		//		Sync.waitElementPresent("id", "mfClose");
		Sync.waitElementClickable(10, "id", "mfClose");
		//		Sync.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mfMessage")));
		String messagetext=Common.findElement("xpath","//div/table/tbody/tr/td").getText();
		if(messagetext.equals(expectedText)){
			Driver.logger.info("Actual Text:"+messagetext);
			Driver.logger.info("textToVerify"+expectedText);
			Driver.logger.info("Verification Passed");
			Common.clickElement("id", "mfClose");	
			Driver.logger.info("Notification message closed successfully");
		}else{
			Driver.logger.info("Failed to close Notification message");
			Driver.logger.info("Actual Text:"+messagetext);
			Driver.logger.info("textToVerify"+expectedText);
			Driver.logger.error("Not an expected text");
			Assert.fail();
			Common.closeAll();
		}

	}



	public static String JavaScriptExecute(String Funv) throws Exception{
		String val=(String)((JavascriptExecutor)dr).executeScript("var str="+Funv+";return str;").toString();
		Driver.logger.info("Successfully excuted JS function");
		return val;
	}


	public static void clickSignoutMenu(String id){
		try {
			Thread.sleep(3000);
			if(browservar.equals("IE")){
				((JavascriptExecutor) dr).executeScript("window.userMenuPopup.document.getElementById('"+id+"').click();");
			}
			else{
				switchFrames(-1);
				switchFrames(2);
				String completeurl= null;
				if(id.equals("menuHome"))
					completeurl = _properties.getProperty(HATF_properties.BASEURL)+"/Common/Common_UserList.aspx";
				else if(id.equals("menuSettings"))
					completeurl = _properties.getProperty(HATF_properties.BASEURL)+"/Common/Common_Mysettings_General.aspx";
				((JavascriptExecutor)dr).executeScript("top.frames[2].frames[2].document.location.href=arguments[0];",completeurl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getFlag(){
		System.out.println("TimeStamp:"+HA.Utilities.HTML.GetDateTime());
		String s ="999";
		Boolean ss=false;
		int i=0;
		while(ss==false){	
			try{
				s = (String) ((JavascriptExecutor) dr).executeScript("return flag;").toString();
			}catch(Exception e){
				s = "999";
			}

			if(!s.equals("999")){
				ss=true;
				Driver.logger.info(" Page loaded correct");

				break;
			}else if (i<1000){

				System.out.println("iiiiiiii: "+(i++));
				i=i+1;
			}else if(i==500){
				System.out.println(HA.Utilities.HTML.GetDateTime());
				break;
			}
		}
		return s;

	}

	public static String getSheetCount() {

		String count=((JavascriptExecutor) dr).executeScript("var count=document.all.ssClient.sheets.count; return count;").toString();
		return count;
	}	

	public static String getCurrentDate(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy"); 
		Date date = new Date(); 
		String CurrentDate = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
		Driver.logger.info("DATEEEEEEEEEEEEEEEEEEE:"+dateFormat.format(date)); 
		Driver.logger.info("CUrrent Date is:"+CurrentDate);
		return CurrentDate;
	}


	public static String getnotificationText() throws Exception{

		String actualText ="";
		Boolean ss=false;
		int i=0;
		while(ss==false){			
			Common.switchFrames(0);
			Common.switchFrames(1);		
			actualText=Common.findElement("xpath","//div/table/tbody/tr/td").getText();			
			if(((((actualText).length())>=1))||i==20){				
				Driver.logger.info("Actual Text:"+actualText);
				clickElement("id", "mfClose");
				Driver.logger.info("Notification message closed sucessfully");
				ss=true;
				break;
			}else{

				Driver.logger.error("iiiiiiii: "+(i++));
				i=i+1;
			}
		}
		return actualText;
	}

	public static void CheckNotificationMessageContains(String expectedText) throws Exception{
		Common.switchFrames(0);
		Common.switchFrames(1);	
		//		Sync.waitElementPresent("id", "mfClose");
		//	Sync.waitElementClickable(20, "id", "mfClose");
		//		Sync.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mfMessage")));
		String messagetext=Common.findElement("xpath","//div/table/tbody/tr/td").getText();
		//	if(messagetext.equals(expectedText)){
		if(messagetext.endsWith(expectedText)){
			Driver.logger.info("Actual Text:"+messagetext);
			Driver.logger.info("textToVerify"+expectedText);
			Driver.logger.info("Verification Passed");
			//		Common.clickElement("id", "mfClose");	
			Driver.logger.info("Notification message closed successfully");


		}
		else{
			Driver.logger.info("Failed to close Notification message");
			Driver.logger.info("Actual Text:"+messagetext);
			Driver.logger.info("textToVerify"+expectedText);
			Driver.logger.error("Not an expected text");
			Assert.fail();
			Common.closeAll();

		}

	}

	public static String getSelectedOption(String elemfindBY, String elemfindText) throws Exception
	{
		Select select=new Select(Common.findElement(elemfindBY,elemfindText));
		WebElement option = select.getFirstSelectedOption();
		return option.getText();
	}
	
	public static void textEnter(Object elementType, Object elementText, String entryData) throws Exception {


		if(elementType.equals("id")){
			dr.findElement(By.id((String) elementText)).clear();
			dr.findElement(By.id((String) elementText)).sendKeys(entryData);
		}
		else if(elementType.equals("name")){
			dr.findElement(By.name((String) elementText)).clear();
			dr.findElement(By.name((String) elementText)).sendKeys(entryData);
		}
		else if(elementType.equals("xpath")){
			dr.findElement(By.xpath((String) elementText)).clear();
			dr.findElement(By.xpath((String) elementText)).sendKeys(entryData);
		}
		System.out.println(entryData+" filled in expected textbox.");

	}

	public static void clickButton(Object elementType, Object elementText) throws Exception {

		if(elementType.equals("id"))
			dr.findElement(By.id((String) elementText)).click();
		else if(elementType.equals("name"))
			dr.findElement(By.name((String) elementText)).click();
		else if(elementType.equals("linkText"))
			dr.findElement(By.linkText((String) elementText)).click();
		else if(elementType.equals("xpath"))
			dr.findElement(By.xpath((String) elementText)).click();
		
		System.out.println(elementText+" button click success.");
	}

}

