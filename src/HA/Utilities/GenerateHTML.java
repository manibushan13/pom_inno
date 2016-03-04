package HA.Utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.CharacterData;
import HA.Properties.HATF_properties;
import HA.Properties.logApp;


public class GenerateHTML {

	final public static Logger logger = Logger.getLogger(GenerateHTML.class);	

	public static String starttime;
	public static String endtime;
	public static String Exetime;	

	public static int totExe_time=0;

	public static String consol_testPassRate,consol_testPassPercentage;
	public static HATF_properties _properties = new HATF_properties();

	public static String exception(String exceptionname)
	{
		String exceptionString=null;
		switch(exceptionname)
		{
		case "org.openqa.selenium.remote.UnreachableBrowserException":
			exceptionString="Browser became unresponsive and not responding";
			break;
		case "java.lang.AssertionError":
			exceptionString="Data validation failure";
			break;
		case "java.io.IOException":
			exceptionString="Unable to perform read/write on test data file";
			break;
		case "org.openqa.selenium.UnhandledAlertException":
			exceptionString="Unhandled exception causing script to fail";
			break;
		case "java.no.file.NoSuchFileException":
			exceptionString="Trying to access a file that does not exist";
			break;
		case "org.openqa.selenium.WebDriverException":   
			exceptionString="Unable to invoke Web driver component/action";
			break;
		case "org.openqa.selenium.InvalidSelectorException":
			exceptionString="InvalidSelectorException";
			break;
		case "org.openqa.selenium.TimeoutException":  
			exceptionString="TimeoutException";
			break;
		case "org.openqa.selenium.NoSuchWindowException":
			exceptionString="Window cannot be found Exception";
			break;
		case "org.openqa.selenium.ElementNotVisibleException":
			exceptionString="Null pointer Exception";
			break;
		case "org.openqa.selenium.NullPointerException":
			exceptionString="Null pointer Exception";
			break;
		case "java.lang.NullPointerException":
			exceptionString="Java Null pointer Exception";
			break;
		default :
			exceptionString="Unknown exception";

		}		
		return exceptionString;

	}

	public static String generateHTML() throws Exception{	


		File file = new File(System.getProperty("user.dir")+"/test-output/testng-results.xml");
		String values,strtestHTML = "";

		if(file.getName().endsWith(".xml")){

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);

			String suiteName = "suite";
			String testName = "test";
			String className = "class";
			String testmethod="test-method";

			String teststatus="status";

			String browser,strstepHTML,exceptionname="";


			NodeList resultNode = doc.getElementsByTagName("testng-results");

			for (int i = 0; i < resultNode.getLength(); i++) 
			{
				Node rNode = resultNode.item(i);

				Element resultElement = (Element) rNode;

				String skipped = resultElement.getAttribute("skipped");
				String failed = resultElement.getAttribute("failed");
				String total = resultElement.getAttribute("total");
				String passed = resultElement.getAttribute("passed");


				logApp.logger.info("No of test skipped: "+skipped);
				logApp.logger.info("No of test failed: "+failed);
				logApp.logger.info("No of test total: "+total);
				logApp.logger.info("No of test passed: "+passed);

				NodeList sList = doc.getElementsByTagName(suiteName);
				for (int temp = 0; temp < sList.getLength(); temp++) 
				{
					Node suiteNode = sList.item(temp);

					Element suiteElement = (Element) suiteNode;	
					Exetime = suiteElement.getAttribute("duration-ms");
					String Suite = suiteElement.getAttribute("name");
					starttime = suiteElement.getAttribute("started-at");
					endtime = suiteElement.getAttribute("finished-at");
					logApp.logger.info("Exetime: "+Exetime);
					logApp.logger.info("Suite Name: "+Suite);

					NodeList nList = suiteElement.getElementsByTagName(testName);


					for (int k = 0; k < nList.getLength(); k++) 
					{


						Node testNode = nList.item(k);

						Element tableElement = (Element) testNode;	


						NodeList testList = tableElement.getElementsByTagName(className);
						for (int j = 0; j < testList.getLength(); j++) 
						{

							String testscriptname = tableElement.getAttribute("name");
							String testexetime = tableElement.getAttribute("duration-ms");
							long minutes = TimeUnit.MILLISECONDS.toMinutes(Long.valueOf(testexetime).longValue());
							if(minutes<1){
								minutes = TimeUnit.MILLISECONDS.toSeconds(Long.valueOf(testexetime).longValue());
								//testexetime=String.valueOf(minutes)+" Secs";
								testexetime="1 Mins";
								logApp.logger.info("Test Script Exetime in Secs: "+minutes);
							}
							else{
								testexetime=String.valueOf(minutes)+" Mins";
								logApp.logger.info("Test Script Exetime in Mins: "+minutes);
							}
							logApp.logger.info("Test Script name: "+testscriptname);
							logApp.logger.info("Test Script Exetime: "+testexetime);


							Node classNode = testList.item(j);
							Element classElement = (Element) classNode;				         
							String classname = classElement.getAttribute("name");
							logApp.logger.info("Test class name: "+classname);


							NodeList testmethodList = tableElement.getElementsByTagName(testmethod);
							loopmethod:
								for (int l = 0; l < testmethodList.getLength(); l++) 
								{
									Node testmethodNode = testmethodList.item(l);
									Element testmethodElement = (Element) testmethodNode;				         
									String testmethodname1 = testmethodElement.getAttribute("name");
									logApp.logger.info("Test Method name: "+testmethodname1);
									browser=getCDataFromElement(testmethodElement);
									System.out.println(browser);
									values=testmethodElement.getAttribute(teststatus);

									ArrayList<String> stringList = new ArrayList<String>();
									stringList =GenerateHTML.getTCdetails(k);
									System.out.println("Value of K: "+k);
									String testids = stringList.get(0);
									String testdesc = stringList.get(1);
									String priority = stringList.get(2);
									String module = stringList.get(3);
									String[] TCcount = testids.split(",");

									if(values.equals("PASS")){
										logApp.logger.info("Test :"+testmethodElement.getAttribute("signature")+" is passed");
										if(l==testmethodList.getLength()-1){
											strstepHTML = reporterpass();				
											strstepHTML=strstepHTML.replace("#ScriptName#", testscriptname).replace("#Exetime#", testexetime).replace("#TestID#", testids).replace("#Priority#", priority).replace("#TestDesc#", testdesc).replace("#Module#", module);
											strtestHTML=strtestHTML+strstepHTML;
											MailTrigger.totalTCcount =MailTrigger.totalTCcount+TCcount.length;
											MailTrigger.passTCcount =MailTrigger.passTCcount+TCcount.length;
										}

									}else{

										NodeList exceptionList = testmethodElement.getElementsByTagName("exception");
										for (int m = 0; m < exceptionList.getLength(); m++) 
										{
											Node exceptionNode = exceptionList.item(m);
											Element exceptionElement = (Element) exceptionNode;				         
											exceptionname = exceptionElement.getAttribute("class");

											exceptionname =exception(exceptionname);

											logApp.logger.info("Exception :"+ exception(exceptionname));

										}

										logApp.logger.info("Test :"+testmethodElement.getAttribute("signature")+" is failed");
										logApp.logger.info("Test :"+classElement.getAttribute("name")+" is failed");
										strstepHTML = reporterfail();
										strstepHTML=strstepHTML.replace("#Exception#", exceptionname).replace("#ScriptName#", testscriptname).replace("#Exetime#", testexetime).replace("#TestID#", testids).replace("#Priority#", priority).replace("#TestDesc#", testdesc).replace("#Module#", module);
										strtestHTML=strtestHTML+strstepHTML;
										MailTrigger.totalTCcount =MailTrigger.totalTCcount+TCcount.length;
										MailTrigger.failTCcount =MailTrigger.failTCcount+TCcount.length;

										break loopmethod;
									}								

								}	

						}					

					}

				}
			}

		}
		testPassPercentage();
		return strtestHTML;
	}

	public static String reporterfail(){
		String strHTML = "";
		strHTML = strHTML + "<TR>";
		strHTML = strHTML + "<TD bgcolor='#F3F3FF' height=50px ALIGN='Center'><font face=Calibri size=3 color=red>#TestID#</font></TD> ";
		strHTML = strHTML + "<TD bgcolor='#F3F3FF' height=50px ALIGN='Center'><font face=Calibri size=3 color=red>#Priority#</font></TD>";
		strHTML = strHTML + "<TD bgcolor='#F3F3FF' height=50px ALIGN='Center'><font face=Calibri size=3 color=red>#Module#</font></TD>";
		strHTML = strHTML + "<TD bgcolor='#F3F3FF' height=50px><font face=Calibri size=3 color=red>#ScriptName#</font></TD> ";
		strHTML = strHTML + "<TD bgcolor='#F3F3FF' height=50px><font face=Calibri size=3 color=red>#TestDesc#</font></TD>";
		strHTML = strHTML + "<TD bgcolor='#F3F3FF' height=50px ALIGN='Center'><font face=Calibri size=3 color=red>Fail</font></TD> ";
		strHTML = strHTML + "<TD bgcolor='#F3F3FF' height=50px ALIGN='Center'><font face=Calibri size=3 color=red>#Exception#</font></TD> ";
		strHTML = strHTML + "<TD bgcolor='#F3F3FF' height=50px ALIGN='Center'><font face=Calibri size=3 color=red>#Exetime#</font></TD>";
		strHTML = strHTML + "</TR>";		
		return strHTML;

	}

	public static String reporterpass(){
		String strHTML = "";
		strHTML = strHTML + "<TR>";
		strHTML = strHTML + "<TD bgcolor='#F3F3FF' height=50px ALIGN='Center'><font face=Calibri size=3 color=green>#TestID#</font></TD> ";
		strHTML = strHTML + "<TD bgcolor='#F3F3FF' height=50px ALIGN='Center'><font face=Calibri size=3 color=green>#Priority#</font></TD>";
		strHTML = strHTML + "<TD bgcolor='#F3F3FF' height=50px ALIGN='Center'><font face=Calibri size=3 color=green>#Module#</font></TD>";
		strHTML = strHTML + "<TD bgcolor='#F3F3FF' height=50px><font face=Calibri size=3 color=green>#ScriptName#</font></TD> ";
		strHTML = strHTML + "<TD bgcolor='#F3F3FF' height=50px><font face=Calibri size=3 color=green>#TestDesc#</font></TD>";
		strHTML = strHTML + "<TD bgcolor='#F3F3FF' height=50px ALIGN='Center'><font face=Calibri size=3 color=green>Pass</font></TD> ";
		strHTML = strHTML + "<TD bgcolor='#F3F3FF' height=50px ALIGN='Center'><font face=Calibri size=3 color=red></font></TD> ";
		strHTML = strHTML + "<TD bgcolor='#F3F3FF' height=50px ALIGN='Center'><font face=Calibri size=3 color=green>#Exetime#</font></TD>";
		strHTML = strHTML + "</TR>";		
		return strHTML;

	}

	public static ArrayList<String> getTCdetails(int i) throws Exception
	{

		ArrayList<String> strList = new ArrayList<String>();

		File file = new File(System.getProperty("user.dir")+"/TestNG.xml");

		if(file.getName().endsWith(".xml"))
		{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);

			String suiteName = "suite";
			String testName = "test";


			NodeList sList = doc.getElementsByTagName(suiteName);
			for (int temp = 0; temp < sList.getLength(); temp++) 
			{
				Node suiteNode = sList.item(temp);

				Element suiteElement = (Element) suiteNode;	

				NodeList nList = suiteElement.getElementsByTagName(testName);

				Node testNode = nList.item(i);

				Element tableElement = (Element) testNode;	
				String testids = tableElement.getAttribute("testid");
				String testdesc = tableElement.getAttribute("description");
				String priority = tableElement.getAttribute("priority");
				strList.add(testids);
				strList.add(testdesc);
				strList.add(priority);

				//System.out.println("testids"+strList.get(0));
				//System.out.println("testdesc"+strList.get(1));
				//System.out.println("priority"+strList.get(2));

				NodeList testList = tableElement.getElementsByTagName("classes");
				for (int j = 0; j < testList.getLength(); j++) 
				{
					Node classNode = testList.item(j);
					Element classElement = (Element) classNode;			
					NodeList testmethodList = classElement.getElementsByTagName("class");

					for (int l = 0; l < testmethodList.getLength(); l++) 
					{


						Node testmethodNode = testmethodList.item(l);
						Element testmethodElement = (Element) testmethodNode;				         
						String classname = testmethodElement.getAttribute("name");
						System.out.println(classname);							
						String[] module = classname.split("\\.");
						strList.add(module[2]);
						strList.add(classname);
					}

				}


			}


		}
		return strList;

	}

	public static String getCDataFromElement(Element e) {
		Node child = e.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			System.out.println("CData is : "+cd.getData());
			return cd.getData();
		}
		return "Mani123";
	}

	public static void testPassPercentage(){
		String passRate =null;
		Float pcount,totcount;
		pcount = (float) MailTrigger.passTCcount;
		totcount = (float) MailTrigger.totalTCcount;
		try{

			passRate = String.format("%.0f", 100*(pcount/totcount));

			if(MailTrigger.passTCcount==MailTrigger.totalTCcount){

				MailTrigger.testPassPercentage = " - All tests passed";
				MailTrigger.testPassRate = passRate+"% ("+MailTrigger.passTCcount+"/"+MailTrigger.totalTCcount+")";

			}else if(MailTrigger.failTCcount==MailTrigger.totalTCcount) {

				MailTrigger.testPassPercentage = " - All tests failed";
				MailTrigger.testPassRate = passRate+"% ("+MailTrigger.passTCcount+"/"+MailTrigger.totalTCcount+")";

			}else{

				MailTrigger.testPassPercentage = " - "+MailTrigger.failTCcount+" out of "+MailTrigger.totalTCcount+" tests failed ";
				MailTrigger.testPassRate = passRate+"% ("+MailTrigger.passTCcount+"/"+MailTrigger.totalTCcount+")";

			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
		}

	}


	public static String extracthtml_details(String fileloc) throws Exception
	{
		String s="Mani";

		FileReader fr=new FileReader(fileloc);
		BufferedReader br= new BufferedReader(fr);
		String content="";
		while((s=br.readLine())!=null)
		{
			content=content+s;

		} 
		System.out.println("Mankoo"+content);
		br.close();

		String Str = new String(content);
		String arr[]=Str.split("<body>", 2);
		System.out.println("Mank text: "+arr[1]);

		String rawbody = arr[1];
		String arrbody[]=rawbody.split("</body>", 2);
		String body=arrbody[0];

		System.out.println("Mank text: "+body);
		return body;	
	}

	public static String gethtmlfiles(String path) throws Exception
	{
		String strHTML="";
		String files;
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles(); 

		for (int i = 0; i < listOfFiles.length; i++) 
		{

			if (listOfFiles[i].isFile()) 
			{
				files = listOfFiles[i].getName();
				if(files.endsWith(".html")){
					System.out.println(files);
					strHTML=strHTML+extracthtml_details(path+"\\"+files);

				}
			}
		}

		return strHTML;

	}



}
