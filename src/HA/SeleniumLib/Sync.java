package HA.SeleniumLib;


import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import HA.TestAutomation.Driver;
import HA.TestAutomation.HATF_properties;


public class Sync extends Common {

	final public static Logger logger = Logger.getLogger(Sync.class);
	private static long waitSeconds=Long.parseLong(_properties.getProperty(HATF_properties.waitTime));
	public static WebDriverWait wait = new WebDriverWait(dr, waitSeconds);


	/**
	 * it will wait to load the Page completely for a given time;
	 * @param waitSeconds
	 */	
	public static void waitPageLoad() throws Exception {	
		dr.manage().timeouts().pageLoadTimeout(Long.parseLong(_properties.getProperty(HATF_properties.waitTime)),TimeUnit.SECONDS);			
		Sync.logger.info("Sync waitPageLoad Completed successfully");
	}	

	/**
	 * set ImplicitWait time.
	 * @param waitSeconds
	 */
	public static void waitImplicit(long waitSeconds) throws Exception{
		dr.manage().timeouts().implicitlyWait(waitSeconds, TimeUnit.SECONDS);
		Sync.logger.info("Sync waitImplicit Completed successfully");
	}


	/**
	 * waitAlert: it will Wait for alert
	 * @param waitSeconds
	 */
	public static void waitAlert(long waitSeconds) throws Exception{
		WebDriverWait syncWait = new WebDriverWait(dr, waitSeconds);
		syncWait.withTimeout(waitSeconds, TimeUnit.SECONDS);
		syncWait.pollingEvery(300, TimeUnit.MILLISECONDS);
		syncWait.until(ExpectedConditions.alertIsPresent());
		Sync.logger.info("Sync waitAlert Completed successfully");
	}	

	/**
	 * waitElementinvisible: 
	 * @param waitSeconds -- provide wait in seconds.
	 * @param elemfindBy -- provide Element findType with id/name/className/linkText/xpath.
	 * @param elemfindText -- provide Element findText 
	 */
	public static void waitElementInvisible(String elemfindBY,String elemfindText) throws Exception{
		WebDriverWait syncWait = new WebDriverWait(dr, waitSeconds);
		syncWait.withTimeout(Long.parseLong(_properties.getProperty(HATF_properties.waitTime)), TimeUnit.SECONDS);
		syncWait.pollingEvery(300, TimeUnit.MILLISECONDS);
		switch(elemfindBY){
		case "id":
			syncWait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(elemfindText)));		
			break;
		case "name":
			syncWait.until(ExpectedConditions.invisibilityOfElementLocated(By.name(elemfindText)));					
			break;
		case "className":
			syncWait.until(ExpectedConditions.invisibilityOfElementLocated(By.className(elemfindText)));			
			break;
		case "linkText":				
			syncWait.until(ExpectedConditions.invisibilityOfElementLocated(By.linkText(elemfindText)));	
			break;
		case "xpath":
			syncWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(elemfindText)));
		case "css":
			syncWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(elemfindText)));
			break;
		case "pLinkText":
			syncWait.until(ExpectedConditions.invisibilityOfElementLocated(By.partialLinkText(elemfindText)));
			break;
		case "tagName":
			syncWait.until(ExpectedConditions.invisibilityOfElementLocated(By.tagName(elemfindText)));
			break;
		}			
		Sync.logger.info("Sync waitElementInvisible completed successfully");
	}

	/**
	 * waitElementinvisible: 
	 * @param waitSeconds -- provide wait in seconds.
	 * @param elemfindBy -- provide Element findType with id/name/className/linkText/xpath.
	 * @param elemfindText -- provide Element findText 
	 */

	public static void waitElementVisible(String elemfindBY,String elemfindText) throws Exception{
		WebDriverWait syncWait = new WebDriverWait(dr, waitSeconds);
		syncWait.withTimeout(Long.parseLong(_properties.getProperty(HATF_properties.waitTime)), TimeUnit.SECONDS);
		syncWait.pollingEvery(300, TimeUnit.MILLISECONDS);

		switch(elemfindBY){
		case "id":
			syncWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(elemfindText)));		
			break;

		case "name":
			syncWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(elemfindText)));
			break;
		case "className":
			syncWait.until(ExpectedConditions.visibilityOfElementLocated(By.className(elemfindText)));			
			break;
		case "linkText":				
			syncWait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(elemfindText)));	
			break;
		case "xpath":
			syncWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elemfindText)));
			break;
		case "css":
			syncWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(elemfindText)));
			break;
		case "pLinkText":
			syncWait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(elemfindText)));
			break;
		case "tagName":
			syncWait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(elemfindText)));
			break;
		}
		Driver.logger.info("Sync waitElementVisible completed successfully");
	}


	/**
	 * waitElementClickable: it will wait until element clickable
	 * @param waitSeconds---set waiting Time
	 * @param elemfindBy --- set findBy like..id,name,className,xpath
	 * @param elemfindText--set findText like Consolidation...etc
	 */
	public static void waitElementClickable(long waitSeconds,String elemfindBY,String elemfindText) throws Exception{

		WebDriverWait syncWait = new WebDriverWait(dr, waitSeconds);
		syncWait.withTimeout(waitSeconds, TimeUnit.SECONDS);
		syncWait.pollingEvery(300, TimeUnit.MILLISECONDS);
		switch(elemfindBY){
		case "id":
			syncWait.until(ExpectedConditions.elementToBeClickable(By.id(elemfindText)));		
			break;
		case "name":
			syncWait.until(ExpectedConditions.elementToBeClickable(By.name(elemfindText)));					
			break;
		case "className":
			syncWait.until(ExpectedConditions.elementToBeClickable(By.className(elemfindText)));			
			break;
		case "linkText":				
			syncWait.until(ExpectedConditions.elementToBeClickable(By.linkText(elemfindText)));	
			break;
		case "xpath":
			syncWait.until(ExpectedConditions.elementToBeClickable(By.xpath(elemfindText)));
			break;
		case"css":
			syncWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(elemfindText)));
			break;
		case "pLinkText":
			syncWait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(elemfindText)));
			break;
		case "tagName":
			syncWait.until(ExpectedConditions.elementToBeClickable(By.tagName(elemfindText)));
			break;
		}
		Sync.logger.info("Sync waitElementClickable completed successfully");
	}

	/**
	 * it will wait for frame
	 * @param waitSeconds --- set waitSeconds
	 * @param frame --- set frameName/frameId
	 */	

	public static void waitFrame(long waitSeconds,String frame) throws Exception{

		WebDriverWait syncWait = new WebDriverWait(dr, waitSeconds);
		syncWait.withTimeout(waitSeconds, TimeUnit.SECONDS);
		syncWait.pollingEvery(300, TimeUnit.MILLISECONDS);
		syncWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
		Sync.logger.info("Sync waitFrame completed successfully");	
	}

	/**
	 * it will wait for frame
	 * @param waitSeconds --- set waitSeconds
	 * @param frame --- set frameName/frameId
	 */	
	public static void waitFrame(long waitSeconds,String frame, String findBy) throws Exception {

		WebDriverWait syncWait = new WebDriverWait(dr, waitSeconds);
		syncWait.withTimeout(waitSeconds, TimeUnit.SECONDS);
		syncWait.pollingEvery(300, TimeUnit.MILLISECONDS);
		switch(findBy)
		{
		case "id":
			syncWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id(frame)));
			break;
		case "name":
			syncWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.name(frame)));
			break;
		case "class":
			syncWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.className(frame)));
			break;
		}	Sync.logger.info("Sync waitFrame completed successfully");
	}

	/**
	 * it will wait for frame
	 * @param frame --- set frameName/frameId
	 */	
	public static void waitFrame(String frame) throws Exception {

		WebDriverWait syncWait = new WebDriverWait(dr, waitSeconds);
		syncWait.withTimeout(Long.parseLong(_properties.getProperty(HATF_properties.waitTime)), TimeUnit.SECONDS);
		syncWait.pollingEvery(300, TimeUnit.MILLISECONDS);
		syncWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
		Sync.logger.info("Sync waitFrame completed successfully");
	}	

	/**
	 * it will wait for Text present in a element
	 * @param waitSeconds --- set set waiting Time
	 * @param elemfindBy --- set findBy like..id,name,className,xpath
	 * @param elemfindText---set findText like Consolidation...etc
	 * @param text --- set text
	 */
	public static void waitTextPresent(long waitSeconds,String elemfindBY,String elemfindText,String text) throws Exception{

		WebDriverWait syncWait = new WebDriverWait(dr, waitSeconds);
		syncWait.withTimeout(waitSeconds, TimeUnit.SECONDS);
		syncWait.pollingEvery(300, TimeUnit.MILLISECONDS);
		switch(elemfindBY){
		case "id":
			syncWait.until(ExpectedConditions.textToBePresentInElementLocated(By.id(elemfindText), text));
			break;			
		case "name":
			syncWait.until(ExpectedConditions.textToBePresentInElementLocated(By.name(elemfindText),text));		
			break;
		case "className":
			syncWait.until(ExpectedConditions.textToBePresentInElementLocated(By.className(elemfindText),text));
			break;
		case "linkText":				
			syncWait.until(ExpectedConditions.textToBePresentInElementLocated(By.linkText(elemfindText),text));	
			break;
		case "xpath":
			syncWait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(elemfindText),text));
			break;
		case "css":
			syncWait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(elemfindText),text));
			break;
		case "pLinkText":
			syncWait.until(ExpectedConditions.textToBePresentInElementLocated(By.partialLinkText(elemfindText),text));
			break;

		case "tagName":
			syncWait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName(elemfindText),text));
			break;
		}
		Sync.logger.info("Sync waitTextPresent completed successfully");
	}



	/**
	 * it will wait for Text present in a element	 
	 * @param elemfindBy --- set findBy like..id,name,className,xpath
	 * @param elemfindText---set findText like Consolidation...etc
	 * @param text --- set text
	 */
	public static void waitTextPresent(String elemfindBY,String elemfindText,String text) throws Exception{

		WebDriverWait syncWait = new WebDriverWait(dr, waitSeconds);
		syncWait.withTimeout(Long.parseLong(_properties.getProperty(HATF_properties.waitTime)), TimeUnit.SECONDS);
		syncWait.pollingEvery(300, TimeUnit.MILLISECONDS);
		switch(elemfindBY){
		case "id":
			syncWait.until(ExpectedConditions.textToBePresentInElementLocated(By.id(elemfindText), text));
			break;			
		case "name":
			syncWait.until(ExpectedConditions.textToBePresentInElementLocated(By.name(elemfindText),text));		
			break;
		case "className":
			syncWait.until(ExpectedConditions.textToBePresentInElementLocated(By.className(elemfindText),text));
			break;
		case "linkText":				
			syncWait.until(ExpectedConditions.textToBePresentInElementLocated(By.linkText(elemfindText),text));	
			break;
		case "xpath":
			syncWait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(elemfindText),text));
			break;
		case "css":
			syncWait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(elemfindText),text));
			break;
		case "pLinkText":
			syncWait.until(ExpectedConditions.textToBePresentInElementLocated(By.partialLinkText(elemfindText),text));
			break;

		case "tagName":
			syncWait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName(elemfindText),text));
			break;
		}Sync.logger.info("Sync waitTextPresent completed successfully");
	}


	/**
	 * it will wait for waitElementPresent
	 * @param waitSeconds---set waiting Time
	 * @param elemfindBy --- set findBy like..id,name,className,xpath
	 * @param elemfindText--set findText like Consolidation...etc
	 */
	public static void waitElementPresent(long waitSeconds,String elemfindBY,String elemfindText) throws Exception{

		WebDriverWait syncWait = new WebDriverWait(dr, waitSeconds);
		syncWait.withTimeout(waitSeconds, TimeUnit.SECONDS);
		syncWait.pollingEvery(300, TimeUnit.MILLISECONDS);
		switch(elemfindBY){
		case "id":
			syncWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(elemfindText)));
			break;
		case "name":
			syncWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.name(elemfindText)));
			break;
		case "className":
			syncWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className(elemfindText)));
			break;
		case "linkText":				
			syncWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.linkText(elemfindText)));	
			break;
		case "xpath":
			syncWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(elemfindText)));
			break;
		case "css":
			syncWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(elemfindText)));
			break;
		case "pLinkText":
			syncWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.partialLinkText(elemfindText)));
			break;

		case "tagName":
			syncWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName(elemfindText)));
			break;
		}			
		Sync.logger.info("Sync waitElementPresent completed successfully");
	}


	public static void waitElementPresent(String elemfindBY,String elemfindText) throws Exception{
		WebDriverWait syncWait = new WebDriverWait(dr, waitSeconds);

		syncWait.withTimeout(Long.parseLong(_properties.getProperty(HATF_properties.waitTime)), TimeUnit.SECONDS);
		syncWait.pollingEvery(300, TimeUnit.MILLISECONDS);
		switch(elemfindBY){
		case "id":
			syncWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(elemfindText)));
			break;
		case "name":
			syncWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.name(elemfindText)));	
			break;
		case "className":
			syncWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className(elemfindText)));
			break;
		case "linkText":
			syncWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.linkText(elemfindText)));	
			break;
		case "xpath":
			syncWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(elemfindText)));
			break;
		case "css":
			syncWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(elemfindText)));
			break;
		case "pLinkText":
			syncWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.partialLinkText(elemfindText)));
			break;
		case "tagName":			
			syncWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName(elemfindText)));
			break;
		}
		Sync.logger.info("Sync waitElementPresent completed successfully");		
	}

	/**
	 * it will wait for waitElementPresent
	 * @param elemfindText send id of the elemnet to be present
	 * @throws Exception 
	 */
	public static void waitElementPresent(String elemfindText) throws Exception{

		WebDriverWait syncWait = new WebDriverWait(dr, waitSeconds);

		syncWait.withTimeout(Long.parseLong(_properties.getProperty(HATF_properties.waitTime)), TimeUnit.SECONDS);
		syncWait.pollingEvery(300, TimeUnit.MILLISECONDS);
		syncWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id((elemfindText))));
		Sync.logger.info("Sync waitElementPresent completed successfully");

	}

	/**
	 * it will wait for document ready state	
	 * @throws Exception 
	 */
	public static void waitForLoad() throws Exception {
		ExpectedCondition <Boolean> pageLoadCondition = new ExpectedCondition <Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait syncWait = new WebDriverWait(dr, 30);
		syncWait.until(pageLoadCondition);
	}

	/**
	 * it will wait for waitForNumberOfWindowsToEqual more than one provide by user
	 * @param numberOfWindows pass the no.of windows expecting
	 * @throws Exception 
	 */
	public static void waitForNumberOfWindowsToEqual(final int numberOfWindows) throws Exception {
		new WebDriverWait(dr,30){}.until(new ExpectedCondition<Boolean>(){
			@Override public Boolean apply(WebDriver driver){
				return (driver.getWindowHandles().size() == numberOfWindows);
			}
		});

	}

	/**
	 * it will wait for waitForNumberOfWindowsToEqual more than one provide by user for a specific time
	 * @param delay pass delay time
	 * @param numberOfWindows pass the no.of windows expecting
	 * @throws Exception 
	 */
	public static void waitForNumberOfWindowsToEqual(int delay, final int numberOfWindows) throws Exception {
		//Making a new expected condition
		new WebDriverWait(dr,delay){}.until(new ExpectedCondition<Boolean>(){
			@Override public Boolean apply(WebDriver driver)
			{
				return (driver.getWindowHandles().size() == numberOfWindows);
			}
		});

	}


	public static void waitForGridload()throws Exception{
		System.out.println("waitForGridload started");
		(new WebDriverWait(dr,30)).until(new ExpectedCondition<Boolean>(){
			public Boolean apply(WebDriver d){
				JavascriptExecutor js = (JavascriptExecutor)d;
				return (Boolean) js.executeScript("return $.active==0");
			}
		});
		System.out.println("waitForGridloadwaitForGridloadwaitForGridloadwaitForGridloadwaitForGridload");
	}


	public static void waitload(){
		((JavascriptExecutor)HA.SeleniumLib.Common.dr).executeScript("$(document).ready(function(){return true;})");
	}

	public static void waitPresenceOfElementLocated(String elemfindBY,String elemfindText){

		WebDriverWait syncWait = new WebDriverWait(dr, waitSeconds);
		syncWait.withTimeout(waitSeconds, TimeUnit.SECONDS);
		syncWait.pollingEvery(300, TimeUnit.MILLISECONDS);

		switch(elemfindBY){
		case "id":
			syncWait.until(ExpectedConditions.presenceOfElementLocated(By.id(elemfindText)));	
			//			Sync.logger.info("Sync presenceOfElementLocated completed successfully for the element: "+elemfindBY +"with text:  "+elemfindText);
			break;

		case "name":
			syncWait.until(ExpectedConditions.presenceOfElementLocated(By.name(elemfindText)));
			//			Sync.logger.info("Sync presenceOfElementLocated completed successfully");
			break;
		case "className":
			syncWait.until(ExpectedConditions.presenceOfElementLocated(By.className(elemfindText)));
			//			Sync.logger.info("Sync presenceOfElementLocated completed successfully");
			break;
		case "linkText":				
			syncWait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(elemfindText)));	
			//			Sync.logger.info("Sync presenceOfElementLocated completed successfully");
			break;
		case "xpath":
			syncWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(elemfindText)));
			//			Sync.logger.info("Sync presenceOfElementLocated completed successfully");
			break;
		case "css":
			syncWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(elemfindText)));
			//			Sync.logger.info("Sync presenceOfElementLocated completed successfully");
			break;
		case "pLinkText":
			syncWait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(elemfindText)));
			//			Sync.logger.info("Sync presenceOfElementLocated completed successfully");
			break;
		case "tagName":
			syncWait.until(ExpectedConditions.presenceOfElementLocated(By.tagName(elemfindText)));
			//			Sync.logger.info("Sync presenceOfElementLocated completed successfully");
			break;
		}
		//		Driver.logger.info("Sync presenceOfElementLocated completed successfullyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
		Driver.logger.info("Sync presenceOfElementLocated completed successfully for the element: "+elemfindBY +"with text:  "+elemfindText);

		//		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("whatever")));
	}

	public static void processSync(long time){
		try{
			Thread.sleep(time);
			Driver.logger.info("processSync passed");
		}catch(Exception e){
			Driver.logger.error("processSync failed with below error: "+e);
		}
	}





}