package HA.SeleniumLib;

import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
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

import HA.Properties.HATF_properties;
import HA.Properties.logApp;
import HA.Utilities.Fetch_PageLocaters;


public class Common {

	public static WebDriver dr;
	public static String browservar;
	public static WebElement element;
	public static HATF_properties _properties = new HATF_properties();
	public static String parenWindow;
	public static String os;
	
	@SuppressWarnings("unused")
	public Common() {
		try{
		logApp.logIntilize();
		Fetch_PageLocaters fp=new Fetch_PageLocaters();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}

	public static String browserName(){
		return _properties.getProperty(HATF_properties.BROWSER);
	}

	/**
	 * driver: will instance a browser and open your url	 *  
	 * @param browser -- pass bowser name as 	 	
	 ** "IE" for Internet Explorer.
	 ** "GC" for Google Chrome.
	 ** by default it will take FireFox
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
			logApp.logger.info("Invoked IE Driver Success");
			break;

		case "GC":

			//Get Chrome Driver
			if(os.contains("mac"))
				System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") +"/Lib/chromedriver");
			else
				System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") +"/Lib/chromedriver.exe");
			
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

			logApp.logger.info("Invoked Chrome Driver Success");
			break;


		case "FF":

			dr = new FirefoxDriver();
			logApp.logger.info("Invoked Firefox Driver Success");
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

		logApp.logger.info("Executing js: " + js);
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
			logApp.logger.info("Element with "+ elemfindBY +"  "+ elemfindText +" found");	
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

		logApp.logger.info("Element with "+ elemfindBY +"  "+ elemfindText +" found");
		return element;

	}

	public static String Getxml(String datafile, String dataset, String key) throws Exception{
		String values = null;		
		values = HA.Utilities.Util.getXmlData(datafile,dataset,key);
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
		logApp.logger.info("Login Window maximized");
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
					logApp.logger.info("Web Driver is not pointing to any window currently.");
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
		logApp.logger.info(popupName+"window/popup selected");

	}

	//**Switching Frame with FrameId */
	/**
	 * switchFrames(id): switch to frame by id
	 * @param frameid -- provide frameid
	 */
	public static void switchFrames(int frameid)throws Exception{
		if(frameid>0){
			dr.switchTo().frame(frameid);
			logApp.logger.info("frameidframeidframeid: "+frameid);			
		}
		else{				
			dr.switchTo().defaultContent();
			logApp.logger.info("frameidframeidframeid: "+frameid);
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
		logApp.logger.info("Frame:  "+ frameFindText + " Found successfully");	

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
		logApp.logger.info(text+" :Text found in frame");	
	}

	/**
	 * getText: will get the text value for an element
	 * @param elemfindBY
	 * @param elemfindText
	 */

	public static String getText(String elemfindBY,String elemfindText)throws Exception{
		String getText = null;
		getText = findElement(elemfindBY,elemfindText).getText();
		logApp.logger.info("the Text for the given element is : "+getText);
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
		logApp.logger.info(value+" Text filled in text box as expected");
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
			logApp.logger.info(value+" Text found in text box");
			return "Pass";
		}
		else{
			logApp.logger.info(value+" Text not found in text box");
			return "Fail";
		}				
	}

	public static void verifyTextBox(String elemfindBY,String elemfindText,String value) throws Exception{	
		String actual = findElement(elemfindBY,elemfindText).getAttribute("value");
		System.out.println("actualText:  "+  actual  +"  userText:  "+value);
		if(value.equals(actual)){
			logApp.logger.info(value+" Text found in text box");
		}
		else{
			logApp.logger.info(value+" Text not found in text box");
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
			logApp.logger.info(value+" Text found in element");
			return "Pass";
		}
		else{
			logApp.logger.info(value+" Text not found in element");
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
		logApp.logger.info(elemfindText+ " element click success");	
	}



	public static void ClickOnHiddenElement(String elemfindBY,String elemfindText)throws Exception{					
		WebElement element = findElement(elemfindBY,elemfindText);
		ClickOnHiddenElement(element);
		logApp.logger.info(elemfindText+ " element click success");
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
		logApp.logger.info("Element is not visible.");	
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
		logApp.logger.info(elemfindText+ "Button click success");

	}

	public static Boolean verifyCheckBox(String elemfindBY,String elemfindText,boolean isChecked) throws Exception{
		WebElement checkBox = findElement(elemfindBY,elemfindText);
		if(checkBox.isSelected() != isChecked) {			
			logApp.logger.info(elemfindText+ "Checkbox verification failed");
			return false;
		}

		logApp.logger.info(elemfindText+ "Checkbox verification success");
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
				logApp.logger.info("Verify dropdown successful.");
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
		logApp.logger.info("Successfully loaded");		
	}


	public static void JSExecute(String Funv)throws Exception{
		((JavascriptExecutor)dr).executeScript(Funv);
		logApp.logger.info("Successfully excuted JS function");
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
	 * wexInput: will input on wex
	 */

	public static void wexInput()throws Exception{
		String jsfunc=null;
		jsfunc="ss.spreadsheet.api.disableScreenUpdate();ss.spreadsheet.api.setValue('C12:C12','500');ss.spreadsheet.api.enableScreenUpdate();";
		System.out.println(jsfunc);
		((JavascriptExecutor) dr).executeScript(jsfunc);
		logApp.logger.info("WEX Input done successfully");
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
		logApp.logger.info("Text Comparison:\nExpected Text:\n"+alerttext+"\nActualtext:\n"+alertText+"\nResult:    "+alerttext.equals(
				alertText));		
		if(alertActions.equals("OK")){
			alert.accept();	
		}else{
			alert.dismiss();
		}	
		
		logApp.logger.info("alerts completed successfully");
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
		System.out.println(System.getProperty("user.dir")+"/TestLogs/screenShots/"+saveAs+".jpg");
		FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"/TestLogs/screenShots/"+saveAs+".jpg"));
		logApp.logger.info("Screenshot Saved Successfully");		
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
		logApp.logger.info(callsubmodule +" clicked successfully");		
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
			logApp.logger.info("provided file path is:  "+ss);
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
		logApp.logger.info("file uploaded successfully");
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
		logApp.logger.info("navigatePage successfully");
	}

	/**
	 * closeCurrentWindow: will close the currency window.
	 */

	//CloseCurent Window
	public static void closeCurrentWindow()throws Exception{		
		dr.close();
		logApp.logger.info("Window Closed Successfully");
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
				logApp.logger.info("WerFault.exe closed successfully");
			}catch(Exception e){
				logApp.logger.info("No WerFault.exe processes to kill");
			}
		}
		dr.quit();
		logApp.logger.info("Driver quit successfully");
		if(browservar.equals("IE")){
			Common.quitProcess();
		}
		//		driverThread.interrupt();
		logApp.logger.info("Driver closed successfully");
		
		}
		catch(Exception e)
		{
			logApp.logger.info("Exception in close all is "+e);
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
		logApp.logger.info("screenshot saved successfully");
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

				logApp.logger.info("Actual Text:"+actuaText);
				logApp.logger.info("textToVerify"+textToVerify);
				logApp.logger.error("Error while submission of ADC tasks");
				clickElement("id", "mfClose");
				Common.screenShot("consolidation submission");			
				Assert.fail();
				Common.closeAll();
				break;				
			}else if(actuaText.contains(textToVerify)){			
				logApp.logger.info("Actual Text:"+actuaText);
				logApp.logger.info("textToVerify"+textToVerify);
				logApp.logger.info("Verifycation passed");
				clickElement("id", "mfClose");

				break;
			}else if(((((actuaText).length())>=1)&&(!(actuaText).equals(textToVerify)))||i==2000){				
				logApp.logger.info("Actual Text:"+actuaText);
				logApp.logger.info("textToVerify"+textToVerify);
				logApp.logger.error("not an expected text");
				clickElement("id", "mfClose");				
				logApp.logger.error("notificationMessage not correct");
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

				logApp.logger.info("Actual Text:"+actuaText);
				logApp.logger.info("textToVerify"+textToVerify);
				logApp.logger.error("Error while submission of ADC tasks");
				//		clickElement("id", "mfClose");
				Common.screenShot("consolidation submission");			
				Assert.fail();
				Common.closeAll();
				break;				
			}else if(actuaText.contains(textToVerify)){			
				logApp.logger.info("Actual Text:"+actuaText);
				logApp.logger.info("textToVerify"+textToVerify);
				logApp.logger.info("Verifycation passed");

				//		clickElement("id", "mfClose");
				ss=true;
				break;
			}else if(((((actuaText).length())>=1)&&(!(actuaText).equals(textToVerify)))||i==2000){				
				logApp.logger.info("Actual Text:"+actuaText);
				logApp.logger.info("textToVerify"+textToVerify);
				logApp.logger.error("not an expected text");
				clickElement("id", "mfClose");
				ss=true;
				logApp.logger.error("notificationMessage not correct");
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
		logApp.logger.info("Element with "+ elemfindBY +"  "+ elemfindText +" found");	
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
		logApp.logger.error(functionName +" Method failed with below exception:\n"+e.getStackTrace());
	}

	public static void jsClick(String id){
		((JavascriptExecutor) dr).executeScript("SubmitIfrm('Next',1)");
	}

	public static void jsClickElement(String id){
		((JavascriptExecutor) dr).executeScript("document.getElementById('"+id+"').click();");
	}

	public static void saveModelPopup(){
		((JavascriptExecutor)dr).executeScript("submitFrm('Load')");
		logApp.logger.info("Successfully loaded");
	}
	public static void closeNotification() throws Exception {
		Common.navigatePage();
		Common.switchFrames(0);
		Common.switchFrames(1);
		if(dr.findElement(By.xpath("//div/table/tbody/tr/td")).isDisplayed()){
			String notificationText=Common.findElement("xpath","//div/table/tbody/tr/td").getText();
			System.out.println("Notification Text: "+notificationText);
			logApp.logger.info("::::::::::::::::::::::"+notificationText);
			dr.findElement(By.id("mfClose")).click();
			logApp.logger.info("mfClose Button click success");
		}else {
			System.out.println("No Notification to Close");
			logApp.logger.error("No Notification to Close");
		}
	}

	public static void popupHandling(String elemfindBY,String elemfindText,String cellIndex, String selectOption) throws Exception {
		Common.switchFrames(elemfindBY, elemfindText);
		Common.clickElement("id", "idToolFilter");
		Common.textBoxInput("className", "excelText", selectOption);
		Thread.sleep(5000);
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
				logApp.logger.info("Actual Text:::::: "+actuaText);
				logApp.logger.info("Expected Tex::::: "+textToVerify);
				clickElement("id", "mfClose");
				ss=true;
				logApp.logger.error("Notification Message not correct");
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
			logApp.logger.info("Expected Text: "+expectedText);
			logApp.logger.info("Actual Text::: "+actuaText);
			if(Common.findElement("className","notification_title").isDisplayed())
				if(actuaText!=null){
					logApp.logger.info("Notification button click success");
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
			String fileName = "C:\\hataf\\source\\TestLogs\\runningStatus\\"+((HA.Utilities.timedate.getCurrentTimeStamp()).split("\\_"))[0]+".txt";
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
		logApp.logger.info(elemfindText+ " element click success");	
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
			logApp.logger.info(directory+ " - Directory does not exist.");
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
					logApp.logger.info("Directory is deleted : "+ file.getAbsolutePath());
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
						logApp.logger.info("Directory is deleted : "+ file.getAbsolutePath());
					}
				}
			}
		}
		else
		{
			file.delete();
			System.out.println("File is deleted : " + file.getAbsolutePath());
			logApp.logger.info("File is deleted : " + file.getAbsolutePath());
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
			logApp.logger.info(srcFolder+ "-Directory does not exist.");
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
				logApp.logger.info("Directory copied from " + src + "  to " + dest);
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
			logApp.logger.info("File copied from " + src + " to " + dest);
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
		logApp.logger.info("Frame URL loaded successfully");

	}

	public static void loadUrl(String url, String ID)throws Exception {
		Sync.processSync(3000);
		switchFrames(-1);
		switchFrames(2);
		WebElement frame = findElement("name", "myframe");
		String completeurl= _properties.getProperty(HATF_properties.BASEURL)+url;
		((JavascriptExecutor)dr).executeScript("top.frames[2].frames[2].document.location.href=arguments[1];",frame,  completeurl);
		Sync.processSync(3000);
		logApp.logger.info("Frame URL loaded successfully");
		navigatePage();
		Sync.waitPresenceOfElementLocated("id", ID);
		Sync.waitElementVisible("id", ID);
		logApp.logger.info("Page navigated successfully");
	}





	public static void doubleClick(String elemfindBY, String elemfindText) throws Exception{
		Actions doubleClick = new Actions(dr);
		doubleClick.moveToElement(findElement(elemfindBY,elemfindText)).doubleClick().build().perform();
	}

	public static void switchWindowsAfterClick(String elemfindBY, String elemfindText)throws Exception{
		String mainWindow = HA.SeleniumLib.Common.dr.getWindowHandle();		
		logApp.logger.info("mainWindow: "+mainWindow);
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
			logApp.logger.info("subWindows: "+id);
			HA.SeleniumLib.Common.dr.switchTo().window(id);
			logApp.logger.info("switching window to subwindow: "+id);
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
			logApp.logger.info("Windows details are:"+id);
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
			logApp.logger.info("Actual Text:"+messagetext);
			logApp.logger.info("textToVerify"+expectedText);
			logApp.logger.info("Verification Passed");
			Common.clickElement("id", "mfClose");	
			logApp.logger.info("Notification message closed successfully");
		}else{
			logApp.logger.info("Failed to close Notification message");
			logApp.logger.info("Actual Text:"+messagetext);
			logApp.logger.info("textToVerify"+expectedText);
			logApp.logger.error("Not an expected text");
			Assert.fail();
			Common.closeAll();
		}

	}



	public static String JavaScriptExecute(String Funv) throws Exception{
		String val=(String)((JavascriptExecutor)dr).executeScript("var str="+Funv+";return str;").toString();
		logApp.logger.info("Successfully excuted JS function");
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
		System.out.println("TimeStamp:"+HA.Utilities.timedate.GetDateTimeforHTML());
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
				logApp.logger.info(" Page loaded correct");

				break;
			}else if (i<1000){

				System.out.println("iiiiiiii: "+(i++));
				i=i+1;
			}else if(i==500){
				System.out.println(HA.Utilities.timedate.GetDateTimeforHTML());
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
		logApp.logger.info("DATEEEEEEEEEEEEEEEEEEE:"+dateFormat.format(date)); 
		logApp.logger.info("CUrrent Date is:"+CurrentDate);
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
				logApp.logger.info("Actual Text:"+actualText);
				clickElement("id", "mfClose");
				logApp.logger.info("Notification message closed sucessfully");
				ss=true;
				break;
			}else{

				logApp.logger.error("iiiiiiii: "+(i++));
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
			logApp.logger.info("Actual Text:"+messagetext);
			logApp.logger.info("textToVerify"+expectedText);
			logApp.logger.info("Verification Passed");
			//		Common.clickElement("id", "mfClose");	
			logApp.logger.info("Notification message closed successfully");


		}
		else{
			logApp.logger.info("Failed to close Notification message");
			logApp.logger.info("Actual Text:"+messagetext);
			logApp.logger.info("textToVerify"+expectedText);
			logApp.logger.error("Not an expected text");
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
		logApp.logger.info(entryData+" filled in expected textbox.");

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
		
		logApp.logger.info(elementText+" button click success.");
	}

}

