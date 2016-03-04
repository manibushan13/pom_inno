package HA.SeleniumLib;

import HA.SeleniumLib.Sync;
import HA.Properties.HATF_properties;
import HA.Properties.logApp;
import HA.SeleniumLib.Common;


public class Login {
	public static HATF_properties _properties = new HATF_properties();

	public static void signIn(String browser) throws Exception
	{
		logApp.logger.info("Logging Started");	
		logApp.logger.info("Browser :"+browser);
		logApp.logger.info("uname : "+ _properties.getProperty(HATF_properties.UNAME));
		if(browser!=""){
			logApp.logger.info("if statementtttttt");
			logApp.logger.info("URL :"+_properties.getProperty(HATF_properties.BASEURL));
			Common.driver(browser,_properties.getProperty(HATF_properties.BASEURL));
		}
		else{
			logApp.logger.info("else statementttttt");
			Common.driver(_properties.getProperty(HATF_properties.BROWSER),_properties.getProperty(HATF_properties.BASEURL));
		}
	}

	public static void selectModule(String datafile,String dataset) throws Exception
	{
		String module=Common.Getxml(datafile,dataset,"module");
		String submodule=Common.Getxml(datafile,dataset,"submodule");
		Common.switchFrames(0);	
		Common.switchFrames(1);		
		Sync.waitElementPresent(60, "id",module);
		if(module.equals("topNav2")||module.equals("topNav3"))
		{		
			Common.clickElement("id",module);	
			Common.navigatePage();
			logApp.logger.info(module+" module selected successfully");

		}
		else
		{
			Sync.waitElementPresent(10,"id",module);
			Common.clickElement("id",module);
			Common.callSubModule(submodule);
			logApp.logger.info(submodule+" module in "+module+" selected successfully");
		}
	}



}
