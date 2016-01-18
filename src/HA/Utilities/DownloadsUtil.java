package HA.Utilities;



import java.io.File;
import java.util.List;

import org.openqa.selenium.WebDriver;

import HA.SeleniumLib.Sync;
import HA.TestAutomation.Driver;
import HA.TestAutomation.Component.Common;
import atu.utils.windows.handler.WindowElement;
import atu.utils.windows.handler.WindowHandler;

public class DownloadsUtil {



	public static void ViewDownloads(String dailogName,String filePath) throws Exception{
		
		File file =new File(filePath);
		if(file.exists()){
			file.delete();
		}
		Thread.sleep(5000);
		AutoITUtil.robo_openDownloadsWindow();
		Thread.sleep(3000);
		AutoITUtil.loadJocobDLL();
		WindowHandler handle=AutoIT_ActionsUtil.getHandler();


		WindowElement windowElement=AutoIT_ActionsUtil.getDialog(handle,dailogName);
		WindowElement button_clearList=AutoIT_ActionsUtil.elementByName(handle, windowElement, "Clear list");
		AutoIT_ActionsUtil.clickElement(handle, button_clearList);


		List<WindowElement> elements=AutoIT_ActionsUtil.elementsByLocalizedControlType(handle, windowElement, "split button");
		AutoIT_ActionsUtil.clickElement(handle, elements.get(1));
		AutoITUtil.robo_click_saveAs();


		//*********************** Save As Dialog ***********************************************************
		WindowElement saveAsDailog_element=AutoIT_ActionsUtil.elementByName(handle, windowElement, "Save As");
		Thread.sleep(1000);
		AutoITUtil.robo_click_backspace();
		WindowElement editElement=AutoIT_ActionsUtil.elementByClassName(handle, saveAsDailog_element, "Edit");

		AutoIT_ActionsUtil.clear(handle, editElement);		
		AutoIT_ActionsUtil.type(handle, editElement, filePath);

		WindowElement button_save=AutoIT_ActionsUtil.elementByName(handle, saveAsDailog_element, "Save");
		AutoIT_ActionsUtil.clickElement(handle, button_save);
		WindowElement button_close=AutoIT_ActionsUtil.elementByName(handle, windowElement, "Close");
		AutoIT_ActionsUtil.clickElement(handle, button_close);
		System.out.println("Done....");
	}

public static void ViewDownloads(WebDriver driver,String dailogName,String filePath) throws Exception{
		
		File file =new File(filePath);
		if(file.exists()){
			file.delete();
		}
		Thread.sleep(9000);
		AutoITUtil.openDownloadsWindow(driver);
		Driver.logger.info("gotdownloadswindow");
		AutoITUtil.loadJocobDLL();
		Driver.logger.info("loaddllsuccess");
		Thread.sleep(3000);
		WindowHandler handle=AutoIT_ActionsUtil.getHandler();
		Driver.logger.info("gothandler");


		WindowElement windowElement=AutoIT_ActionsUtil.getDialog(handle,dailogName);
		Driver.logger.info("gotdialog");
		WindowElement button_clearList=AutoIT_ActionsUtil.elementByName(handle, windowElement, "Clear list");
		Driver.logger.info("gotclear list");
		AutoIT_ActionsUtil.clickElement(handle, button_clearList);
		Driver.logger.info("click clearlist success");


		List<WindowElement> elements=AutoIT_ActionsUtil.elementsByLocalizedControlType(handle, windowElement, "split button");
		Driver.logger.info("gotspiltbutton");
		AutoIT_ActionsUtil.clickElement(handle, elements.get(1));
		Driver.logger.info("click element success");
		AutoITUtil.robo_click_saveAs();
		Driver.logger.info("saveas success");


		//*********************** Save As Dialog ***********************************************************
		WindowElement saveAsDailog_element=AutoIT_ActionsUtil.elementByName(handle, windowElement, "Save As");
		Driver.logger.info("gotsaveasr");
		Thread.sleep(1000);
		AutoITUtil.robo_click_backspace();		
		Driver.logger.info("back space success");
		Thread.sleep(7000);
		WindowElement editElement=AutoIT_ActionsUtil.elementByClassName(handle, saveAsDailog_element, "Edit");
		Driver.logger.info("gotedit");

		AutoIT_ActionsUtil.clear(handle, editElement);	
		Driver.logger.info("clear success");
		AutoIT_ActionsUtil.type(handle, editElement, filePath);
		Driver.logger.info("type success");

		WindowElement button_save=AutoIT_ActionsUtil.elementByName(handle, saveAsDailog_element, "Save");
		Driver.logger.info("gotsave");
		AutoIT_ActionsUtil.clickElement(handle, button_save);
		Driver.logger.info("save success");
		WindowElement button_close=AutoIT_ActionsUtil.elementByName(handle, windowElement, "Close");
		Driver.logger.info("gotclose");
		AutoIT_ActionsUtil.clickElement(handle, button_close);
		Driver.logger.info("close success");
		Thread.sleep(5000);
		System.out.println("Done....");
	}
	public static void ExportFile(String dailogName,String filePath) throws Exception{
		
		File file =new File(filePath);
		if(file.exists()){
			file.delete();
		}
		Thread.sleep(3000);		
		AutoITUtil.loadJocobDLL();
		Thread.sleep(3000);
		
		WindowHandler handle=AutoIT_ActionsUtil.getHandler();

		WindowElement windowElement=AutoIT_ActionsUtil.getDialog(handle,dailogName);
		WindowElement editElement=AutoIT_ActionsUtil.elementByClassName(handle, windowElement, "Edit");
		AutoIT_ActionsUtil.clear(handle, editElement);		
		AutoIT_ActionsUtil.type(handle, editElement, filePath);
		
		WindowElement button_save=AutoIT_ActionsUtil.elementByName(handle, windowElement, "Save");
		AutoIT_ActionsUtil.clickElement(handle, button_save);
		Thread.sleep(3000);
		if(Common.isAlertPresent()==true){
			Sync.waitAlert(120);
			Sync.isAlertPresent();
			Common.alerts("OK", "");
		}
		
		
	}
}
