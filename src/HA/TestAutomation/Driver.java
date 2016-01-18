package HA.TestAutomation;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;


public class Driver {
	public static HATF_properties _properties = new HATF_properties();
	final public static Logger logger = Logger.getLogger(Driver.class);	

//	public static void main(String[] args) throws Exception {
//		DOMConfigurator.configure(System.getProperty("user.dir")+"/src/log4j.xml");
//		System.out.println("log4j Intialised Successfully.");
//		logger.info("log4j Intialised Successfully.");
//
//
//	}
	public static void logIntilize() throws Exception {
		DOMConfigurator.configure(System.getProperty("user.dir")+"/src/log4j.xml");
		System.out.println("log4j Intialised Successfully.");
		logger.info("log4j Intialised Successfully.");


	}

}
