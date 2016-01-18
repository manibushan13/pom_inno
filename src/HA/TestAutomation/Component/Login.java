package HA.TestAutomation.Component;

import org.apache.log4j.xml.DOMConfigurator;
import HA.SeleniumLib.Sync;
import HA.TestAutomation.HATF_properties;
import HA.TestAutomation.Driver;


public class Login {
	public static HATF_properties _properties = new HATF_properties();

	public static void signIn(String browser) throws Exception
	{
		DOMConfigurator.configure(System.getProperty("user.dir")+"/src/log4j.xml");
		Driver.logger.info("Logging Started");	
		Driver.logger.info("browserbrowserbrowser"+browser);
		Driver.logger.info("uname"+ _properties.getProperty(HATF_properties.UNAME));
		if(browser!=""){
			Driver.logger.info("ddddddddif");
			Driver.logger.info("url"+_properties.getProperty(HATF_properties.BASEURL));

			Common.driver(browser,_properties.getProperty(HATF_properties.BASEURL));

		}
		else{
			Driver.logger.info("elseelseelseelseelse");
			Common.driver(_properties.getProperty(HATF_properties.BROWSER),_properties.getProperty(HATF_properties.BASEURL));
		}
		//Common.textBoxInput("id","strUserLogin", _properties.getProperty(HATF_properties.UNAME));
		//Common.textBoxInput("id","strUserPwd", _properties.getProperty(HATF_properties.PWD));
		//Common.clickElement("id","btnLogin");
		
		Driver.logger.info("signIn success");
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
			Driver.logger.info(module+" module selected successfully");

		}
		else
		{
			Sync.waitElementPresent(10,"id",module);
			Common.clickElement("id",module);
			Common.callSubModule(submodule);
			Driver.logger.info(submodule+" module in "+module+" selected successfully");
		}
	}

	public static void selectsubModule(String datafile,String dataset) throws Exception{ //** selecting submodules in Consolidation Module.*/

		Sync.waitElementPresent(20,"id",Common.Getxml(datafile,dataset,"selectsubModule"));
		Common.clickElement("id",Common.Getxml(datafile,dataset,"selectsubModule"));
	}



}
