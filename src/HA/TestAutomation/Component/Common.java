package HA.TestAutomation.Component;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.xml.sax.SAXException;

import HA.SeleniumLib.Sync;
import HA.SeleniumLib.Common.SelectBy;
import HA.TestAutomation.Driver;
import HA.TestAutomation.HATF_properties;


/**
 * 
 * @author MChiruvella
 *
 */
public class Common {
	
	public static HATF_properties _properties = new HATF_properties();
	
	public static String browserName(){
		return HA.SeleniumLib.Common.browserName();
	}


	/**
	 * <b><i>Getxml</b></i>: will get the value from a XML file
	 * @param datafile -- Provide the file name with extension. Ex: "ConsolidationSmokeData.xml"
	 * @param dataset -- Provide the dataset name in the above file. Ex: "DynamicJournal_Centralization_dataset"
	 * @param key -- Create a Key for your input (Which was similar to '${journalCode}' in Watin)
	 * @Example:Common.<b>Getxml</b>("ConsolidationSmokeData.xml","DynamicJournal_Centralization_dataset","journalCode");
	 * */
	public static String Getxml(String datafile, String dataset, String key) throws Exception{
		String values = null;
		try {

			values = HA.Utilities.ReadXML.dataset(datafile,dataset,key);
			System.out.println(key+" valuesssssssssssss "+values);

		} catch (ParserConfigurationException | SAXException | IOException e) {
//			e.printStackTrace();
			Driver.logger.error(e);
		}
		return values;
	}
	/**
	 * <b><i>driver:</i></b> will instance a browser and open your url	 *  
	 * @param browser -- Provide bowser type
	 * {@value: IE for Internet Explorer.}
	 * {@value: GC for Google Chrome.}
	 * (@value: by default it will take FireFox}
	 * @param url -- Provide the url
	 * {@value: url of HostEnvironment}
	 * @throws Exception 
	 * @Example:
	 * Common.<b>driver</b>("IE","https://qa.hostanalytics.com");
	 */	
	public static void driver(String browser,String url) throws Exception{	
		HA.SeleniumLib.Common.driver(browser, url);					
	}
	
	public static List<WebElement> IsElementPresent(String elemfindBY,String elemfindText)throws Exception{
		return HA.SeleniumLib.Common.IsElementPresent(elemfindBY, elemfindText);
	}

	/**
	 * <b><i>maximizeImplicitWait:</b></i> will maximize the current window and implicitWait() will helps on delay problems.
	 *	 * implicitWait: will wait for the respond.
	 * @throws Exception 
	 * @waitTime: provide waiting time in Seconds in HATF_propertices page.	
	 * @Example:
	 * Common.<b>maximizeImplicitWait()</b>;	 
	 */	
	public static void maximizeImplicitWait() throws Exception{
		HA.SeleniumLib.Common.maximizeImplicitWait();
	}

	public static void resizeWindow() throws Exception{
		HA.SeleniumLib.Common.resizeWindow();
	}

	/**
	 * <b><i>switchWindows():</b></i> Focus will switch to recently opened window.
	 * @param -- NoParameter to pass.
	 * @throws Exception 
	 * @Note: driver will generate id for every newly opened window, the focus will go to recently opened window.
	 * @Example:
	 * Common.<b>switchWindows()</b>;
	 */
	public static void switchWindows() throws Exception{

		HA.SeleniumLib.Common.switchWindows();
	}

	/**
	 * <b><i>switchWindows():</b></i> Focus will switch to recently opened window.
	 * @param -- maximizeWindow decides if the switched window needs to be maximized
	 * @throws Exception 
	 * @Note: driver will generate id for every newly opened window, the focus will go to recently opened window.
	 * @Example:
	 * Common.<b>switchWindows()</b>;
	 */
	public static void switchWindows(Boolean maximizeWindow) throws Exception{

		HA.SeleniumLib.Common.switchWindows(maximizeWindow);

	}

	/**
	 * <b><i>switchWindows:</i></b> will switch to window/Popup by windowName or popupName
	 * @param popupName -- provide the windowName or popupName
	 * @throws Exception 
	 * @Example:
	 * Common.<b>switchWindows</b>("myNewPopup");
	 */
	public static void switchWindows(String popupName) throws Exception{
		HA.SeleniumLib.Common.switchWindows(popupName);
	}

	/**
	 * <b><i>switchFrames:</i></b>  focus will switch to provided frame in current page. 
	 * 
	 * @param frameid -- provide frame number as frameid like 1,2,3.	 
	 * @throws Exception *
	 * @Note: Common.switchFrames(0): focus will come out from all the frames.
	 * @see: switchFrames(String frameName)
	 * @Example
	 * Common.<b>switchFrames(1)</b>;
	 * 
	 */

	public static void switchFrames(int frameid) throws Exception{
		HA.SeleniumLib.Common.switchFrames(frameid);		
	}
	/**
	 * <b><i>switchFrames:</i></b> Focus will switch to provided frame in current page.
	 * 
	 * @param frameName -- provide frame name or className as frameName. 
	 * @throws Exception 
	 * @see: switchFrames(int frameid)
	 * @Example:
	 * Common.<b>switchFrames</b>("myFrame")
	 * 
	 * 
	 */	public static void switchFrames(String frameFindBY, String frameFindText) throws Exception{
		 HA.SeleniumLib.Common.switchFrames(frameFindBY,frameFindText);
	 }

	 /**
	  * <b><i>switchFrames:</i></b> Focus will switch to provided frame in current page.
	  * 
	  * @param frameName -- provide frame name or className as frameName. 
	  * @see: switchFrames(int frameid)
	  * @Example:
	  * Common.<b>switchFrames</b>("myFrame")
	  * 
	  * 
	  */
//	 public static void switchFrames(String frameName){
//		  HA.SeleniumLib.Common.switchFrames(frameName);
//	  }

	  /**
	   * <b><i>txtVerify:</b></i> will verify the text and return the result in true or false
	   * @param text -- pass text to verify
	   * @return: true/false
	   * @throws Exception 
	   * @Example:Common.<b>txtVerify</b>("Consolidation");
	   */
	  public static void txtVerify(String text) throws Exception{
		  HA.SeleniumLib.Common.txtVerify(text);		 
	  }

	  /**
	   * <b><i>textBoxinput:</i></b> will provide the text value into the textBox
	   * @param elemfindBY -- Provide how to found the WebElement
	   * 		<p>{@value: id}
	   * 		<p>{@value: name}
	   * 		<p>{@value: className}
	   * 		<p>{@value: linkText}
	   * 		<p>{@value: xpath}
	   * 		<p>{@value: css}
	   * 		<p>{@value: pLinkText}
	   * 		<p>{@value: tagName}</p>
	   * @param elemfindText -- Provide text of your WebElement. 		
	   * @param value -- Provide text for textbox input.
	   * @throws Exception 
	   * @Example: Common.<b>textBoxInput</b>("id","journalsCodeid","DynamicJournalAdd");
	   */
	  public static void textBoxInput(String elemfindBY,String elemfindText,String value) throws Exception{	
		  HA.SeleniumLib.Common.textBoxInput(elemfindBY,elemfindText,value);
	  }		


	  //textBoxVerification
	  /**
	   * <b><i>textBoxVerify:</b></i> will verify text in textBox
	   * @param elemfindBY -- Provide how to found the WebElement
	   * 		<p>{@value: id}
	   * 		<p>{@value: name}
	   * 		<p>{@value: className}
	   * 		<p>{@value: linkText}
	   * 		<p>{@value: xpath}
	   * 		<p>{@value: css}
	   * 		<p>{@value: pLinkText}
	   * 		<p>{@value: tagName}</p>
	   * @param elemfindText -- Provide text of your WebElement.
	   * @param value -- Provide text to verify with textBox.
	   * @throws Exception 
	   * @Example: Common.<b>textBoxVerify</b>("id","journalsCodeid","DynamicJournalAdd");
	   */
	  public static String textBoxVerify(String elemfindBY,String elemfindText,String value) throws Exception{	
		  return HA.SeleniumLib.Common.textBoxVerify(elemfindBY,elemfindText,value);
	  }
	  
	  public static void verifyTextBox(String elemfindBY,String elemfindText,String value) throws Exception{	
		   HA.SeleniumLib.Common.verifyTextBox(elemfindBY,elemfindText,value);
	  }
	 
	 /**
	  * <b><i>textVerify:</b></i> will verify text in textBox
	  * @param elemfindBY -- Provide how to found the WebElement
	  * 		<p>{@value: id}
	  * 		<p>{@value: name}
	  * 		<p>{@value: className}
	  * 		<p>{@value: linkText}
	  * 		<p>{@value: xpath}
	  * 		<p>{@value: css}
	  * 		<p>{@value: pLinkText}
	  * 		<p>{@value: tagName}</p>
	  * @param elemfindText -- Provide text of your WebElement.
	  * @param value -- Provide text to verify with the element.
	 * @throws Exception 
	  * @Example: Common.<b>textVerify</b>("id","journalsCodeid","DynamicJournalAdd");
	  */
	 public static String textVerify(String elemfindBY,String elemfindText,String value) throws Exception{	
		 return HA.SeleniumLib.Common.textVerify(elemfindBY,elemfindText,value);
	 }


	  //clickElement
	  /**
	   * <b><i>clickElement:</b></i> will find the element and click on that element.	 * 
	   *@param elemfindBY -- Provide how to found the WebElement
	   * 		<p>{@value: id}
	   * 		<p>{@value: name}
	   * 		<p>{@value: className}
	   * 		<p>{@value: linkText}
	   * 		<p>{@value: xpath}
	   * 		<p>{@value: css}
	   * 		<p>{@value: pLinkText}
	   * 		<p>{@value: tagName}</p>
	   * @param elemfindText -- Provide text of your WebElement.
	   * @throws Exception 
	   * @Example: Common.<b>clickElement</b>("id","Save");
	   */
	  public static void clickElement(String elemfindBY,String elemfindText) throws Exception{	
		  Sync.waitPresenceOfElementLocated(elemfindBY,elemfindText);
//		  Sync.waitElementVisible(elemfindBY,elemfindText);
		  HA.SeleniumLib.Common.clickElement(elemfindBY,elemfindText);		
	  }
	  
	//ClickOnHiddenElement
	  /**
	   * <b><i>clickElement:</b></i> will find the element and click on that element.	 * 
	   *@param elemfindBY -- Provide how to found the WebElement
	   * 		<p>{@value: id}
	   * 		<p>{@value: name}
	   * 		<p>{@value: className}
	   * 		<p>{@value: linkText}
	   * 		<p>{@value: xpath}
	   * 		<p>{@value: css}
	   * 		<p>{@value: pLinkText}
	   * 		<p>{@value: tagName}</p>
	   * @param elemfindText -- Provide text of your WebElement.
	   * @throws Exception 
	   * @Example: Common.<b>clickElement</b>("id","Save");
	   */
	  public static void ClickOnHiddenElement(String elemfindBY,String elemfindText) throws Exception{
		  HA.SeleniumLib.Common.ClickOnHiddenElement(elemfindBY,elemfindText);		
	  }
	  
	  public static void KeyPress(String elemfindBY,String elemfindText, Keys keyCode)throws Exception{
		  HA.SeleniumLib.Common.KeyPress(elemfindBY,elemfindText, keyCode);
	  }

	  /**
	   * <b><i>dropdown:</b></i> will select the value from  a dropdown list.
	   * * clickElement: will find the element and click on that element.	 * 
	   *@param elemfindBY -- Provide how to found the WebElement
	   *			<p>{@value: id}
	   * 		<p>{@value: name}
	   * 		<p>{@value: className}
	   * 		<p>{@value: linkText}
	   * 		<p>{@value: xpath}
	   * 		<p>{@value: css}
	   * 		<p>{@value: pLinkText}
	   * 		<p>{@value: tagName}</p>
	   * @param elemfindText -- Provide text of your WebElement. 
	   * @param selectvalue -- pass value to select
	   * @throws Exception 
	   * @Example:
	   * 	Common.<b>dropdown</b>("name","drpScenario","Actual");
	   */
	  public static void dropdown(String elemfindBY,String elemfindText,String selectvalue) throws Exception{
		  HA.SeleniumLib.Common.dropdown(elemfindBY,elemfindText,selectvalue);
	  }

	  /** @throws Exception 
	   * @deprecated
	   * use dropdown(String elemfindBY,String elemfindText,String selectvalue)
	   */
	  public static void dropdown(String findText,String selectvalue) throws Exception{
		  HA.SeleniumLib.Common.dropdown(findText,selectvalue);
	  }

	  /**@deprecated
	   * <b><i>DLR drop down:</b></i> This element was specifically made for Column Mappings screen drop downs.
	   * @param index -- pass value for found Text 
	   * @param colValue -- pass value to select
	   * @throws Exception 
	   */
	  public static void DLRdropdown(String index, String colValue) throws Exception{
		  HA.SeleniumLib.Common.DLRdropdown(index, colValue);
	  }

	  public static void DLRdiv() throws Exception{
		  HA.SeleniumLib.Common.DLRdiv();
	  }

	  /**
	   * <b><i>subMenuClick:</b></i> will click on elements under sub menu.
	   * @param popup -- Provide sub menu pop up name
	   * @param id -- provide id of an element to click.
	   * @throws Exception 
	   * @Example: Common.<b>subMenuClick</b>("opopup","save");
	   */ 

	  public static void subMenuClick(String popup, String id) throws Exception{
		  HA.SeleniumLib.Common.subMenuClick(popup,id);
	  }

	  /**
	   * <b><i>saveModelPopup:</b></i> will click Save button on Model popup.
	   * @throws Exception 
	   * @Example: Common.<b>saveModelPopup()</b>;
	   */

	  public static void Massloadsave() throws Exception{
		  HA.SeleniumLib.Common.Massloadsave();
	  }

	  public static WebElement findElement(String elemfindBY,String elemfindText) throws Exception{
		  return HA.SeleniumLib.Common.findElement(elemfindBY, elemfindText);
	  }

	 public static Object executeJS(String js, Object arg1, Object arg2) throws InterruptedException
	  {
		 Object jsObj = HA.SeleniumLib.Common.executeJS(js, arg1, arg2);
		 Thread.sleep(3000);
		 return jsObj;
	  }

	  //	 public static boolean findlink(String findTextValue){
	  //		 return HA.SeleniumLib.Common.findlink(findTextValue);
	  //	 }

	  //	 public static void link(String findTextValue){
	  //		 HA.SeleniumLib.Common.link(findTextValue);		
	  //	 }

	  /**
	   * <b><i>dthmlxEditableGrid</i></b>: will give value to Auto complete fields
	   * <p>will give values to text fields</p>
	   *<p> will click on link or on image </p>
	   *<p> and will Select a row in Editable Grid.</p>
	   * @param controlType -- provide control type like as: "AutoComplete", "Link", "Text", "Select".
	   * @param gridName -- provide gridName
	   * @param rowIndex -- provide rowIndex
	   * @param colIndex -- provide colIndex
	   * @param inputValue -- provide inputValue
	 * @throws Exception 
	   * @Example AutoComplete: <p>Common.<b>dhtmlxEditableGrid</b>("AutomComplete","objGrid",1,1,"Account1");
	   * @Example Text: <p>Common.<b>dhtmlxEditableGrid</b>("Text","objGrid",1,1,"Account1");
	   * @Example Link/Image click: <p>Common.<b>dhtmlxEditableGrid</b>("Link","objGrid",1,1,"dummy"); <p><b><i>Note:</i></b>provide dummy input value, not considered while execution. use same for ImageClick also.
	   * @Example Select: <p>Common.<b>dhtmlxEditableGrid</b>("Select","objGrid",1,1,"dummy");<p><b><i>Note:</i></b> provide any dummy input value, dummy colIndex,  not considered while execution. 
	   *  
	   */	
	  public static void dhtmlxEditableGrid(String controlType,String gridName, int rowIndex,int colIndex, String inputValue) throws Exception{
//		  try{
			  HA.SeleniumLib.Common.dhtmlxEditableGrid(controlType,gridName,rowIndex,colIndex,inputValue);
//		  }catch(Exception e){
//			  Driver.logger.error("Failed with the below exception:\n"+e);
//		  }
	  }

	  /**
	   * <b><i>dhtmlxGrid:</b></i> will check, selected row in Grid and will select the value from a dropdown
	   * @param searchTexts -- provide Text value for row
	   * @param columnIndex -- for column Index
	   * @param gridName -- gridName
	   * @param newVal -- provide a value to select from dropdown. 
	   * @param Action -- provide what Action to do on dhtmlxGrid.
	   * @throws Exception 
	   * @Example
	   * <p>check:</p>
	   * <p>Common.<b>dhtmlGrid</b>("DynamicJouralCode","1","JournalGrid","dummy",<b>"check"</b>);
	   * <p>select:</p>
	   * <p>Common.<b>dhtmlGrid</b>("DynamicJouralCode","1","JournalGrid","dummy",<b>"select"</b>);
	   * <p>dropdown:</p>
	   * <p>Common.<b>dhtmlGrid</b>("Reportingtype","1","ReportingGrid","Adjustment (CC)",<b>"dropdown"</b>);	 
	   */	

	  public static void dhtmlxGrid(String searchTexts,String columnIndex,String gridName, String newVal,String Action) throws Exception{
		  HA.SeleniumLib.Common.dhtmlxGrid(searchTexts,columnIndex,gridName,newVal,Action);
	  }
	  
	  /**
	   * <b><i>dhtmlxGrid:</b></i> will check, selected row in Grid and will select the value from a dropdown
	   * @param searchTexts -- provide Text value for row
	   * @param searchTextColumnIndex -- for column Index
	   * @param gridName -- gridName
	   * @param checkBoxColumnIndex,String getTextColumnIndex -- provide checkBoxColumnIndex
	   * @param getTextColumnIndex -- provide the column index for getting text.
	   * @param Action -- provide what Action to do on dhtmlxGrid.
	   * @throws Exception 
	   * @Example
	   * <p>check:</p>
	   * <p>Common.<b>dhtmlGrid</b>("DynamicJouralCode","1","JournalGrid","3",<b>"check"</b>);
	   * <p>select:</p>
	   * <p>Common.<b>dhtmlGrid</b>("DynamicJouralCode","1","JournalGrid","dummy",<b>"select"</b>);
	   * <p>dropdown:</p>
	   * <p>Common.<b>dhtmlGrid</b>("Reportingtype","1","ReportingGrid","Adjustment (CC)",<b>"dropdown"</b>);	 
	   */	
	  
	  public static String dhtmlxGridGetText(String searchTexts,String searchTextColumnIndex,String gridName,String checkBoxColumnIndex,String getTextColumnIndex,String action){	
		  return HA.SeleniumLib.Common.dhtmlxGridGetText(searchTexts, searchTextColumnIndex, gridName, checkBoxColumnIndex, getTextColumnIndex, action);
	  }


	  /*	 public static void dhtmlxGridSelect(String searchTexts,String columnIndex,String gridName){
		 HA.SeleniumLib.Common.dhtmlxGridSelect(searchTexts,columnIndex,gridName);
	 }
	   */	 

	  /**
	   * owcInput: will input the values into OWC sheet.
	   * @param row -- row index to input  
	   * @param column -- column index to input
	   * @param value -- value 
	   * @param owcname -- ownName
	   * @throws Exception 
	   */
	  public static void owcInput(String row,String column,String value,String owcname) throws Exception{
		  HA.SeleniumLib.Common.owcInput(row,column,value,owcname);
	  }

	  
	  public static String JavaScriptExecute(String Funv) throws Exception{
			return HA.SeleniumLib.Common.JavaScriptExecute(Funv);
		}
	  
	  /**
	   * wexInput: will input on wex
	   * @throws Exception 
	   */
	  public static void wexInput() throws Exception{
		  HA.SeleniumLib.Common.wexInput();
	  }

	  public static Object owcSheetName(String owcName) throws Exception{
		  return HA.SeleniumLib.Common.owcSheetName(owcName);
	  }

	  //**Alerts*/
	  /**
	   * <b><i>alerts:</b></i> will get the text on alert, compare with expected text and will click on OK or Cancel in alert.
	   * @param alertActions -- pass "OK" for OK and Yes. provide cancel or No to dismiss the alert.
	   * @param alerttext -- Expected text on alert.
	   * @throws Exception 
	   * @Example:
	   * Common.<b>alerts</b>("OK","Do you want to Delete this journal?");
	   */
	  public static void alerts(String alertActions,String alerttext) throws Exception{
		  HA.SeleniumLib.Common.alerts(alertActions,alerttext);
	  }

	  //**drag and drop**//	
	  /**
	   * <b><i>dragdrop:</b></i> will drag the element from source and drag it on destination on same page.
	   * @param ddSourceeElemfindBy -- source element find by, like by "id", "name", "className", "xpath".
	   * @param ddSourcename -- source element name
	   * @param ddTargetElemFindBy -- target element find by, like by "id", "name", "className", "xpath".
	   * @param ddTargetsname -- target element name.
	   * @throws Exception 
	   * @Example -- 
	   * Common.<b>dragdrop</b>("id","seg1","id","seg2");
	   */
	  public static void dragdrop(String ddSourceeElemfindBy, String ddSourcename,String ddTargetElemFindBy, String ddTargetsname) throws Exception{
		  HA.SeleniumLib.Common.dragdrop(ddSourceeElemfindBy,ddSourcename,ddTargetElemFindBy,ddTargetsname);
	  }



	  /**
	   * <b><i>dragdropClickHold:</b></i> will drag the element from source and drag it on destination on same page.
	   * @param ddSourceeElemfindBy -- source element find by, like by "id", "name", "className", "xpath".
	   * @param ddSourcename -- source element name
	   * @param ddTargetElemFindBy -- target element find by, like by "id", "name", "className", "xpath".
	   * @param ddTargetsname -- target element name.
	   * @throws Exception 
	   * @Example -- 
	   * Common.<b>dragdrop</b>("id","seg1","id","seg2");
	   */
	  public static void dragdropClickHold(String ddSourceeElemfindBy, String ddSourcename,String ddTargetElemFindBy, String ddTargetsname) throws Exception{
		  HA.SeleniumLib.Common.dragdropClickHold(ddSourceeElemfindBy,ddSourcename,ddTargetElemFindBy,ddTargetsname);
	  }



	  /**
	   * <b><i>getText:</b></i> will get the text value for an element
	   *@param elemfindBY -- Provide how to found the WebElement
	   *			<p>{@value: id}
	   * 		<p>{@value: name}
	   * 		<p>{@value: className}
	   * 		<p>{@value: linkText}
	   * 		<p>{@value: xpath}
	   * 		<p>{@value: css}
	   * 		<p>{@value: pLinkText}
	   * 		<p>{@value: tagName}</p>
	   * @param elemfindText -- Provide text of your WebElement.
	   * @throws Exception 
	   * @Example--
	   * Common.<b>getText</b>("id","Actual");
	   */

	  public static String getText(String elemfindBY,String elemfindText) throws Exception{
		  return HA.SeleniumLib.Common.getText(elemfindBY,elemfindText);

	  }

	  /**
	   * <b><i>navigatePage:</b></i> focus will move to middle pane on the current page.
	   * @throws Exception 
	   * @Example: 
	   * Common.<b>navigatePage()</b>;
	   */
	  public static void navigatePage() throws Exception{
		  HA.SeleniumLib.Common.navigatePage();
	  }
	  
	  public static void spreadsheetswitchsheets(String owcname,String sheetName){
			HA.SeleniumLib.Common.spreadsheetswitchsheets(owcname, sheetName);
		}
	  
	  public static void spreadsheetswitchsheets(String owcname,int sheetid){
			HA.SeleniumLib.Common.spreadsheetswitchsheets(owcname, sheetid);
		}

	  //**mouseOver**//	
	  /**
	   * <b><i>mouseOver:</b></i> will do the mouse over action on selected element.
	   *@param elemfindBY -- Provide how to found the WebElement
	   *			<p>{@value: id}
	   * 		<p>{@value: name}
	   * 		<p>{@value: className}
	   * 		<p>{@value: linkText}
	   * 		<p>{@value: xpath}
	   * 		<p>{@value: css}
	   * 		<p>{@value: pLinkText}
	   * 		<p>{@value: tagName}</p>
	   * @param elemfindText -- Provide text of your WebElement.
	   * @throws Exception 
	   * @Exapmle:
	   * Common.<b>mouseOver</b>("id","ReportSave");
	   */
	  public static void mouseOver(String elemfindBY, String elemfindText) throws Exception{
		  HA.SeleniumLib.Common.mouseOver(elemfindText,elemfindText);
	  }

	  public static void catchExceptions(Exception e){
		  HA.SeleniumLib.Common.catchExceptions(e);
		  //String e;
		  //functionName=Thread.currentThread().getStackTrace()[1].getMethodName();

		  //Driver.logger.error(functionName +"failed with below exceptions:\n"+e);
	  }


	  /**
	   * <b><i>mouseOverClick:</b></i> will do the mouse over action on a element and click on that element.
	   *@param elemfindBY -- Provide how to found the WebElement
	   *			<p>{@value: id}
	   * 		<p>{@value: name}
	   * 		<p>{@value: className}
	   * 		<p>{@value: linkText}
	   * 		<p>{@value: xpath}
	   * 		<p>{@value: css}
	   * 		<p>{@value: pLinkText}
	   * 		<p>{@value: tagName}</p>
	   * @param elemfindText -- Provide text of your WebElement.
	   * @throws Exception 
	   * @Example:
	   * Common.<b>mouseOverClick</b>("id","ReportSave");
	   */
	  public static void mouseOverClick(String elemfindBY, String elemfindText) throws Exception{
		  HA.SeleniumLib.Common.mouseOverClick(elemfindBY,elemfindText);
	  }

	  //**Capturing Screenshots**//
	  /**
	   * <b><i>screenShot:</b></i> will capture the screen shot and save the screen shot a jpg file.
	   * @param saveAs -- save the file on the specified location.
	   * @throws Exception 
	   * @Example:
	   * Common.<b>screenShot</b>("C:/hataf/Images");
	   * 

	   */
	  public static void screenShot(String saveAs) throws Exception {
		  HA.SeleniumLib.Common.screenShot(saveAs);
	  }

	  public String GetCurrentTimeStamp() 
	  {
		  java.util.Date date= new java.util.Date();
		  Timestamp currentTimestamp= new Timestamp(date.getTime());
		  return currentTimestamp.toString();
	  }

	  //**to find the web element**//	
	  /**
	   * tagNametoFind: internal user only: used for get the no.of elements based on tagName.
	   * @param tagNameToFind
	   * @throws Exception 
	   */
	  public static void tagNameToFind(String tagNameToFind) throws Exception{
		  HA.SeleniumLib.Common.tagNameToFind(tagNameToFind);
	  }

	  /**
	   * <b><i>callSubModule:</b></i>will click on  submodules.
	   * <p> use <b><i>Login.selectModule:</b></i> method for better use.</p>
	   * <p><b>Login.selectModule:</b> will click on Module(Ex: Maintenace) after that will click on SubModule(Ex: Curreny Exchangerates);
	   * <p>Example: Login.selectModule("topNav5","left523");-- this will navigate to Maintenance > Currency Exchangerates screen.
	   * @param callsubmodule -- pass id of the submodule.	 
	   * @throws Exception 
	   * @Example:
	   * callSubModule("left523");
	   * @Note:
	   * before use this method we can click on main module (Ex: topNav1 or topNav4 or topNav5)
	   */
	  public static void callSubModule(String callsubmodule) throws Exception {	
		  HA.SeleniumLib.Common.callSubModule(callsubmodule);
	  }

	  /**
	   * fileUpload: will upload the file
	   * @param filepath: filepath
	   * @throws Exception 
	   */
	  public static void fileUpLoad(String filepath) throws Exception{
		  HA.SeleniumLib.Common.fileUpLoad(filepath);
	  }

	  /**
	   * boxFileUpload: will upload the file
	   * @param linktext: link text name
	   * @param filepath: filepath
	   * @throws Exception 
	   */
	  public static void boxFileUpload(String linktext, String filepath) throws Exception {		
		  HA.SeleniumLib.Common.boxFileUpload(linktext,filepath);
	  }

	  public static void sendkeys(String keys) {		
		  //HA.SeleniumLib.Common.sendkeys(keys);
	  }

	  //CloseCurent Window
	  /**
	   * <b><i>closeCurrentWindow:</b></i> will close the current window.
	   * @throws Exception 
	   * @Example: 
	   * common.<b>closeCurrentWindow()</b>;
	   */
	  public static void closeCurrentWindow() throws Exception{
		  HA.SeleniumLib.Common.closeCurrentWindow();
	  }

	  //CloseAllWindows
	  /**
	   * <b><i>closeAll:</b></i> will close all windows and quit the selenium driver.
	   * @throws Exception 
	   * @Example:
	   * Common.<b>closeAll()</b>;
	   */
	  public static void closeAll() throws Exception{
		  HA.SeleniumLib.Common.closeAll();
	  }

	  /**
	   * <b><i>quitProcess:</b></i> will kill the selenium driver based on web driver (IE, Firefox,Chrome).
	   * @throws Exception 
	   * @Example:Common.<b>quitProcess()</b>;
	   * 
	   */
	  public static void quitProcess() throws Exception {
		  HA.SeleniumLib.Common.quitProcess();
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
	  //	 public static WebElement findElement(String elemfindBY,String elemfindText){
	  //		 return HA.SeleniumLib.Common.findElement(elemfindBY,elemfindText);		
	  //	 }



	  /**
	   * implicitWait: will wait for the respond.
	   * @waitTime: provide waiting time in Seconds in HATF_propertices page.
	   */
	  //	 public static void implicitWait() {
	  //
	  //		 HA.SeleniumLib.Common.implicitWait();
	  //	 }

	  /**
	   * <b><i>dhtmlxTree:</b></i> will do all the actions for tree
	   * @param treeName -- provide treeName
	   * @param treeNodeValue -- pass node value like (Account Main||@@||AccountRollup||@@||AccountChild2)
	   * @param treeAction -- pass action as "SELECT", "CHECK", "UNCHECK"
	   * @throws Exception 
	   * @Example
	   * Common.<b>dhtmlxTree</b>("treeSeg1","Account Main",<b>"SELECT"</b>);
	   * <p>Common.<b>dhtmlxTree</b>("treeSeg1","Account Main||@@||IncomeAccounts||@@||Income1",<b>"CHECK"</b>);
	   * <p>Common.<b>dhtmlxTree</b>("treeSeg1","Account Main||@@||IncomeAccounts||@@||Income1",<b>"UNCHECK"</b>);
	   */

	  public static void dhtmlxTree(String treeName,String treeNodeValue,String treeAction) throws Exception {		
		  HA.SeleniumLib.Common.dhtmlxTree(treeName,treeNodeValue, treeAction);
	  }

	  /**
	   * dhtmlxGridCheck: will check the check box in Grid.
	   * @param searchText: provide Text value for checkbox row
	   * @param columnIndex: for column Index
	   * @param gridName: gridName
	   * @throws Exception 
	   */
	  /*	 public static void dhtmlxGridCheck(String searchTexts,String columnIndex,String gridName) {
		 HA.SeleniumLib.Common.dhtmlxGridCheck(searchTexts,columnIndex,gridName);

	 }

	 /**
	   * dhtmlxGridDropdown: will select value from a dropdown in Grid.
	   * @param searchText: provide Text value for row
	   * @param columnIndex: for column Index
	   * @param gridName: gridName
	   * @param newVal: valuetext to select
	   */
	  /*	 public static void dhtmlxGridDropdown(String string,String string2,String string3,String string4) {
		 HA.SeleniumLib.Common.dhtmlxGridDropdown(string,string2,string3,string4);

	 }
	   */
	  public static void calender(String textBoxId,String month) throws Exception {

		  HA.SeleniumLib.Common.Calendar(textBoxId,month);

	  }

	  public static void calender(String textBoxId,String imageId,String month) throws Exception {

		  HA.SeleniumLib.Common.Calendar(textBoxId,imageId,month);

	  }

	  public static List<WebElement> IsElementPresents(String elemfindBY,String elemfindText) throws Exception{
		  return HA.SeleniumLib.Common.IsElementPresent(elemfindBY, elemfindText);
	  }

	  public static String getSysTime() throws Exception{
		  return HA.SeleniumLib.Common.getSysTime();
	  }

	  public static boolean compareImage(String sourceImage, String targetImage) { 
		  return HA.Utilities.File.compareImage(sourceImage, targetImage);
	  }

	  public static void sourceScreenShot(String saveAs) throws Exception{
		  HA.SeleniumLib.Common.sourceScreenShot(saveAs);
	  }

	  /**
	   * notificationTextVerification: will verify the text in the notification and proceed to next steps
	   * textToVerify: provide expected text in the notification.
	   * @throws Exception 
	   */
	  public static void notificationTextVerification(String textToVerify) throws Exception{
		  HA.SeleniumLib.Common.notificationTextVerification(textToVerify);
	  }
	  
	  public static void notificationTextVerification(String textToVerify,boolean fileDownload) throws Exception{
		  HA.SeleniumLib.Common.notificationTextVerification(textToVerify,fileDownload);
	  }

	  public static String getTagName(String elemfindBY,String elemfindText,String tagName) throws Exception{
		  return HA.SeleniumLib.Common.getTagName(elemfindBY, elemfindText,tagName);
	  }

	  public static int getElementCount(String elemfindBY,String elemfindText) throws Exception{
		  return HA.SeleniumLib.Common.getElementCount(elemfindBY, elemfindText);
	  }

	  public static void fileUpLoad(String elemfindBY, String elemfindText, String filepath) throws Exception{
		  HA.SeleniumLib.Common.fileUpLoad(elemfindBY, elemfindText, filepath);
	  }
	  
	  public static Boolean isRowPresentInDhtmlxGrid(String searchTexts,String columnIndex,String gridName)throws Exception{		
		  return HA.SeleniumLib.Common.isRowPresentInDhtmlxGrid(searchTexts, columnIndex, gridName);
	  }
	  
	  public static void dropdown(String elemfindBY,String elemfindText,String selectvalue, SelectBy selectBy)throws Exception{
		  HA.SeleniumLib.Common.dropdown(elemfindBY, elemfindText, selectvalue, selectBy);
	  }
	  
	  public static boolean verifyDropdown(String elemfindBY,String elemfindText, SelectBy selectBy, String expected) throws Exception{
		return HA.SeleniumLib.Common.verifyDropdown(elemfindBY, elemfindText, selectBy, expected);
	  }
	  
	  public static void clickCheckBox(String elemfindBY,String elemfindText,boolean isChecked) throws Exception{	
		HA.SeleniumLib.Common.clickCheckBox(elemfindBY, elemfindText, isChecked);	
	  }
	  
	  public static String GetBaseURL()
	  {
		  return HA.SeleniumLib.Common.GetBaseURL();
	  }	 
	  
	  public static void owcVerify(String spreadsheet, int controltype, String expecteddata)throws Exception{	
		  HA.SeleniumLib.Common.owcVerify(spreadsheet, controltype, expecteddata);
	  }
		  
	  public static void htmlVerify(String htmlData, String expecteddata)throws Exception{		
		  HA.SeleniumLib.Common.htmlVerify(htmlData, expecteddata);
	  }
	  
	  public static void deletehtmlXGridRow(String controlType, String gridName, int rowIndex) throws Exception{
		  HA.SeleniumLib.Common.deletehtmlXGridRow(controlType, gridName, rowIndex);
	  }
	  
	  public static String BrowserName()
	  {
		  return HA.SeleniumLib.Common.browservar;
	  }	  
	  
	  public static void jsClick(String id)throws Exception{
		  HA.SeleniumLib.Common.jsClick(id);
	  }
	  
	  public static void saveModelPopup(){
		  HA.SeleniumLib.Common.saveModelPopup();
	  }
	  
	  public static void  owcSelectCell(String owcname,String row, String column, String sheetid){
		  HA.SeleniumLib.Common. owcSelectCell(owcname, row, column, sheetid);
	  }
	public static void closeNotification() throws Exception {
		HA.SeleniumLib.Common.closeNotification();
		
	}
	public static Object dhtmlxGridRowcount(String gridName) throws Exception {		
		return  HA.SeleniumLib.Common.dhtmlxGridRowcount(gridName);
		
	}
	public static void popupHandling(String elemfindBY,String elemfindText,String cellIndex, String selectOption) throws Exception {
		HA.SeleniumLib.Common.popupHandling(elemfindBY,elemfindText,cellIndex,selectOption);		
	}
	
	public static void pressTabKey(String elemfindBY, String elemfindText) throws Exception {
		HA.SeleniumLib.Common.pressTabKey(elemfindBY, elemfindText);
	}
	
	public static void DLRnotification(String textToVerify) throws Exception{
		HA.SeleniumLib.Common.DLRnotification(textToVerify);
	}

	public static void DefaultsDLRDropDown(String index, String colValue) throws Exception {
		HA.SeleniumLib.Common.DefaultsDLRDropDown(index, colValue);
	}
	
	public static void boxFileNotification(String textToVerify) throws Exception {
		HA.SeleniumLib.Common.boxFileNotification(textToVerify);
	}
	
	public static void WaitUntilOverlay() throws Exception{                 	
    	Driver.logger.info("Waiting for overlay to disappear");
		if(Common.isElementdisplayed("xpath", String.format("//*[text()='%s']", "Loading....")))
			Sync.waitElementInvisible("xpath", String.format("//*[text()='%s']", "Loading...."));
        Driver.logger.info("Overlay disappeared");                    
    }	
	
	public static void waitForOverlaydisable(String elemfindBY, String elemfindText) throws Exception{                 	
//    	Driver.logger.info("Waiting for overlay to disappear");
//		if(Common.isElementdisplayed("xpath", "//*[@id='loadingMsg']"));
//			Sync.waitElementInvisible("xpath", "//*[@id='loadingMsg']");
//        Driver.logger.info("Overlay disappeared");  
		HA.SeleniumLib.Common.waitForOverlaydisable(elemfindBY, elemfindText);
		
    }	


	public static int windowCounts(){
		return HA.SeleniumLib.Common.windowCounts();
	}
	
	public static Boolean isAlertPresent(){
		return HA.SeleniumLib.Common.isAlertPresent();
	}
	public static  List<WebElement> getAllOption(String elemfindBY,String elemfindText) throws Exception{
	 return HA.SeleniumLib.Common.getAllOption(elemfindBY,elemfindText);
	}
	public static boolean isElementdisplayed(String elemfindBY,String elemfindText)throws Exception{
		 return HA.SeleniumLib.Common.isElementdisplayed(elemfindBY,elemfindText);
	}
	
	public static void loadUrlinChrome(String url) throws Exception{
		HA.SeleniumLib.Common.loadUrlinChrome(url);
	}
	public static void loadUrl(String url, String ID) throws Exception{
		HA.SeleniumLib.Common.loadUrl(url, ID);
	}
	public static void switchmainWindowsCons() throws Exception {
		HA.SeleniumLib.Common.switchmainWindowsCons();
		
	}
	
	 public static void CheckNotificationMessage(String expectedText) throws Exception{
		  HA.SeleniumLib.Common.CheckNotificationMessage(expectedText);
	  }
	 
	 
	 public static void switchWindowsSingleClick(String elemfindBY, String elemfindText)throws Exception{

		 HA.SeleniumLib.Common.switchWindowsSingleClick(elemfindBY, elemfindText);
			}
	 
	 
	 public static void clickSignoutMenu(String id){
		 HA.SeleniumLib.Common.clickSignoutMenu(id);
			
		}
	 public static String getSheetCount() throws Exception
	 {
		 return  HA.SeleniumLib.Common.getSheetCount();
	 }
      

	 public static String getCurrentDate(){
			return HA.SeleniumLib.Common.getCurrentDate();
			
		}
	 
	 public static String getnotificationText() throws Exception{
			String actualText =  HA.SeleniumLib.Common.getnotificationText();
			return actualText;
		  }
	 public static void javascriptclickElement(String elemfindBY,String elemfindText) throws Exception{		
	  HA.SeleniumLib.Common.javascriptclickElement(elemfindBY, elemfindText);
		}
	 public static void jsClickElement(String id){
		 HA.SeleniumLib.Common.jsClickElement(id);
		}
	 public static void JSExecute(String Funv)throws Exception{
			HA.SeleniumLib.Common.JSExecute(Funv);
		}

		public static String getSelectedOption(String elemfindBY, String elemfindText) throws Exception
		 {
		 	
		 	return HA.SeleniumLib.Common.getSelectedOption(elemfindBY, elemfindText);
		 }
		
}
