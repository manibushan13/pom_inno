package HA.Utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;


import java.io.FileOutputStream;
import java.io.IOException;





import java.net.InetAddress;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.CharacterData;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import HA.Properties.HATF_properties;
import HA.Properties.logApp;
import HA.SeleniumLib.Common;







public class generateHTMLReport {
	
	final public static Logger logger = Logger.getLogger(generateHTMLReport.class);	
	
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
								stringList =generateHTMLReport.getTCdetails(k);
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
										mail.totalTCcount =mail.totalTCcount+TCcount.length;
										mail.passTCcount =mail.passTCcount+TCcount.length;
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
									mail.totalTCcount =mail.totalTCcount+TCcount.length;
									mail.failTCcount =mail.failTCcount+TCcount.length;
									
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
	
public static ArrayList<String> getTCdetails(int i) throws Exception{
		

	ArrayList<String> strList = new ArrayList<String>();
		
 
	File file = new File(System.getProperty("user.dir")+"/TestNG.xml");

		if(file.getName().endsWith(".xml")){

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
				
//					System.out.println("testids"+strList.get(0));
//					System.out.println("testdesc"+strList.get(1));
//					System.out.println("priority"+strList.get(2));
										
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
		return "Mankoo123";
	}
	
	public static void testPassPercentage(){
		String passRate =null;
		Float pcount,totcount;
		pcount = (float) mail.passTCcount;
		totcount = (float) mail.totalTCcount;
		try{
			
			passRate = String.format("%.0f", 100*(pcount/totcount));
			
			if(mail.passTCcount==mail.totalTCcount){
				
				mail.testPassPercentage = " - All tests passed";
				mail.testPassRate = passRate+"% ("+mail.passTCcount+"/"+mail.totalTCcount+")";

			}else if(mail.failTCcount==mail.totalTCcount) {
				
				mail.testPassPercentage = " - All tests failed";
				mail.testPassRate = passRate+"% ("+mail.passTCcount+"/"+mail.totalTCcount+")";

			}else{
				
				mail.testPassPercentage = " - "+mail.failTCcount+" out of "+mail.totalTCcount+" tests failed ";
				mail.testPassRate = passRate+"% ("+mail.passTCcount+"/"+mail.totalTCcount+")";

			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
		}



	}
	

public static void consolidatedreport() throws Exception{
		
//		For below code to  work add jcifs jar to Lib
		
//		String smbUrl = "smb://username:password@server/share/file";
//		SmbFileOutputStream fos = new SmbFileOutputStream(new SmbFile(smbURL));
//		String filePathString= "\\\\hyddc1\\hyd_shared$\\QA\\Mchiruvella\\new.xls";
//		String folder= "\\\\hyddc1\\hyd_shared$\\QA\\Mchiruvella";
		
		
		//String strHTML1= null;
		//strHTML1 = strHTML1 + generateHTMLReport.generateHTML();
	String strDate = null;
	SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");

	Date now = new Date();

	strDate = sdfDate.format(now);
	
//	String filePathString= "smb://hyddc1/hyd_shared$/QA/Mchiruvella/"+strDate+"/MacConsolidatedreport"+strDate+".xls";
	
	String user = "automationuser:h@1p@55w0rd";
    System.out.println("User: " + user);

    NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(user);
    String filePathString = System.getProperty("user.dir")+"/test-output/"+strDate+"/Consolidatedreport"+strDate+".xls";
//    String filePathString = "smb://hyddc1/hyd_shared$/QA/Mchiruvella/"+strDate+"/Consolidatedreport"+strDate+".xls";
    System.out.println("Path: " +filePathString);
    SmbFile sFile = new SmbFile(filePathString, auth);
    
    if(sFile.exists())
    {
    	logApp.logger.info("hiiii");
    }
    else
    {
    	logApp.logger.info("byeee");
    }
    
//   SmbFileOutputStream sfos = new SmbFileOutputStream(sFile);
    
    
   
    /**
    final FileInputStream fileInputStream = new FileInputStream(new File(
    	    "/Users/automationuser/Desktop/06032015/hataf/source/smb:/hyddc1/hyd_shared$/QA/Mchiruvella/"+strDate+"/MacConsolidatedreport"+strDate+".xls"));
    
    final byte[] buf = new byte[16 * 1024 * 1024];
    int len;
    while ((len = fileInputStream.read(buf)) > 0) {
    	sfos.write(buf, 0, len);
    }
    fileInputStream.close();
    sfos.close();
    
   **/
	
	
	String folder= "smb://hyddc1/hyd_shared$/QA/Mchiruvella/"+strDate;
	
//	File sharedDir = new File(folder);
	SmbFile sharedDir = new SmbFile(folder, auth);        
	
	File f = new File(filePathString);
	ArrayList<String> stringList = new ArrayList<String>();
	stringList =generateHTMLReport.getTCdetails(1);
	String module = stringList.get(3);
		
	
		String browserName=_properties.getProperty(HATF_properties.BROWSER);
		
		String version = "";
		if(browserName.equals("IE"))
		{
		ArrayList<String> output = new ArrayList<String>();
		Process p = Runtime.getRuntime().exec("reg query \"HKLM\\Software\\Microsoft\\Internet Explorer\" /v Version");
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()),8*1024);
//		BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		String s = null;
		System.out.println("Here is the standard output of the command:\n");
		while ((s = stdInput.readLine()) != null)
		output.add(s);

		String internet_explorer_value = (output.get(2));
		version = internet_explorer_value.trim().split("   ")[2];
	   	version=version.trim().split("\\.")[1];
		System.out.println(version);
		}
		if (!sharedDir.exists()) {
			try
			{
			sharedDir.mkdir();
			System.out.println("sharedDir created successfully");
			}
			catch(Exception e)
			{
				System.out.println(e);
            	System.out.println("sharedDir Not created successfully");
            	sharedDir.mkdir();
            	System.out.println("sharedDir created successfully for 2nd attempt");
			}
//			sharedDir.mkdirs();
		} System.out.println("File exists :"+f.exists()+"File Diectory :"+f.isDirectory());
			if (sFile.exists() && !f.isDirectory()){
				
				 //Read the spreadsheet that needs to be updated
//              FileInputStream input_document = new FileInputStream(new File(filePathString));
				 SmbFileInputStream input_document = new SmbFileInputStream(sFile);
                //Access the workbook
                HSSFWorkbook workbookout = new HSSFWorkbook(input_document); 
                //Access the worksheet, so that we can update / modify it.
                HSSFSheet worksheet = workbookout.getSheet("POI Worksheet"); 
                // declare a Cell object
                System.out.println("Rows: "+worksheet.getLastRowNum()+1);
				Row row1 = worksheet.createRow(worksheet.getLastRowNum()+1);
				Cell cell01 = row1.createCell(0);
				cell01.setCellValue(_properties.getProperty(HATF_properties.BASEURL));
				Cell cell11 = row1.createCell(1);
				cell11.setCellValue(_properties.getProperty(HATF_properties.TENANT));
				Cell cell21 = row1.createCell(2);
				cell21.setCellValue(System.getProperty("os.name"));
				Cell cell31 = row1.createCell(3);
				cell31.setCellValue(InetAddress.getLocalHost().getHostName());
				Cell cell41 = row1.createCell(4);
				cell41.setCellValue(module);
				Cell cell51 = row1.createCell(5);
				cell51.setCellValue(timedate.getlocaltime(generateHTMLReport.starttime));
				Cell cell61 = row1.createCell(6);
				cell61.setCellValue(timedate.getlocaltime(generateHTMLReport.endtime));
				Cell cell71 = row1.createCell(7);
				cell71.setCellValue(timedate.getminsectime(generateHTMLReport.Exetime));
				Cell cell81 = row1.createCell(8);
				cell81.setCellValue(mail.passTCcount);
				Cell cell91 = row1.createCell(9);
				cell91.setCellValue(mail.totalTCcount);
				Cell cell101 = row1.createCell(10);
				cell101.setCellValue(browserName+version);
				  //Close the InputStream
                input_document.close();
                //Open FileOutputStream to write updates
                
               /**
                FileOutputStream output_file =new FileOutputStream(new File(filePathString));
                //write changes
                workbookout.write(output_file);
                //close the stream
                output_file.close();
                **/
                
                
                SmbFileOutputStream sfos = new SmbFileOutputStream(sFile);
                try
                {
                workbookout.write(sfos);
                System.out.println("write done successfully");
                }
                catch(Exception e)
                {
                	System.out.println(e);
                	System.out.println("write not done properly");
                	workbookout.write(sfos);
                    System.out.println("write done successfully for 2nd attempt");
                }
                sfos.close();
			}
			else{
				
				HSSFWorkbook workbook = new HSSFWorkbook();
				HSSFSheet worksheet = workbook.createSheet("POI Worksheet");
				Row row = worksheet.createRow(0);
				Cell cell0 = row.createCell(0);
				cell0.setCellValue("Environment");
				Cell cell1 = row.createCell(1);
				cell1.setCellValue("Tenant");
				Cell cell2 = row.createCell(2);
				cell2.setCellValue("OS");
				Cell cell3 = row.createCell(3);
				cell3.setCellValue("Execution_Machine");
				Cell cell4 = row.createCell(4);
				cell4.setCellValue("Module");
				Cell cell5 = row.createCell(5);
				cell5.setCellValue("Execution_Start_Time");
				Cell cell6 = row.createCell(6);
				cell6.setCellValue("Execution_End_Time");
				Cell cell7 = row.createCell(7);
				cell7.setCellValue("Execution_Time");
				Cell cell8 = row.createCell(8);
				cell8.setCellValue("Passed_TC");
				Cell cell9 = row.createCell(9);
				cell9.setCellValue("Total_TC");
				Cell cell10 = row.createCell(9);
				cell10.setCellValue("Browser");

				//mail.passTCcount +"/"+mail.totalTCcount

				Row row1 = worksheet.createRow(1);
				Cell cell01 = row1.createCell(0);
				cell01.setCellValue(_properties.getProperty(HATF_properties.BASEURL));
				Cell cell11 = row1.createCell(1);
				cell11.setCellValue(_properties.getProperty(HATF_properties.TENANT));
				Cell cell21 = row1.createCell(2);
				cell21.setCellValue(System.getProperty("os.name"));
				Cell cell31 = row1.createCell(3);
				cell31.setCellValue(InetAddress.getLocalHost().getHostName());
				Cell cell41 = row1.createCell(4);
				cell41.setCellValue(module);
				Cell cell51 = row1.createCell(5);
				cell51.setCellValue(timedate.getlocaltime(generateHTMLReport.starttime));
				Cell cell61 = row1.createCell(6);
				cell61.setCellValue(timedate.getlocaltime(generateHTMLReport.endtime));
				Cell cell71 = row1.createCell(7);
				cell71.setCellValue(timedate.getminsectime(generateHTMLReport.Exetime));
				Cell cell81 = row1.createCell(8);
				cell81.setCellValue(mail.passTCcount);
				Cell cell91 = row1.createCell(9);
				cell91.setCellValue(mail.totalTCcount);
				Cell cell101 = row1.createCell(10);
				cell101.setCellValue(browserName+version);
				/**
				FileOutputStream fileOut = new FileOutputStream(filePathString);
				workbook.write(fileOut);
				fileOut.flush();
				fileOut.close();
				**/
				
				
				SmbFileOutputStream sfos = new SmbFileOutputStream(sFile);
				try
                {
                workbook.write(sfos);
                System.out.println("write done successfully");
                }
                catch(Exception e)
                {
                	System.out.println(e);
                	System.out.println("write not done properly");
                	workbook.write(sfos);
                    System.out.println("write done successfully for 2nd attempt");
                }
	                sfos.close();
			}
			
//			File fsource = new File(System.getProperty("user.dir")+"/test-output/Reporting.html");
			
			File fsource = new File(HTML.htmlreportpath);
			
//			File fdest = new File(folder+"/"+module+"Mac.html");
			
//	        SmbFile sFiledest = new SmbFile(folder+"/"+module+"Mac.html");  
			
			 String filePathStringfdest = "smb://hyddc1/hyd_shared$/QA/Mchiruvella/"+strDate+"/"+module+" "+System.getProperty("os.name")+" "+InetAddress.getLocalHost().getHostName()+" "+browserName+version+".html";
		        System.out.println("Path: " +filePathStringfdest);
		        SmbFile sFilefdest = new SmbFile(filePathStringfdest, auth);
		        
		        SmbFileOutputStream sfos = new SmbFileOutputStream(sFilefdest);
			
			 
	        final FileInputStream fileInputStream = new FileInputStream(fsource);
	        
	        final byte[] buf = new byte[16 * 1024 * 1024];
	        int len;
	        while ((len = fileInputStream.read(buf)) > 0) {
	        	try
                {
	        	sfos.write(buf, 0, len);
	        	 System.out.println("write buf done successfully");
                }
                catch(Exception e)
                {
                	System.out.println(e);
                	System.out.println("write buf not done properly");
                	sfos.write(buf, 0, len);
                    System.out.println("write buf done successfully for 2nd attempt");
                }
	        }
	        fileInputStream.close();
	        sfos.close();
	        
	      
//	        
//			
//			copyFiletoDest(fsource,sFiledest);
		}
		
		
		
public static void copyFiletoDest(File source, File dest)	throws IOException {
			FileUtils.copyFile(source, dest);
			logApp.logger.info("Copy success");
		}



	
	public static String consolidatedmoduledetails() throws Exception{
		//Float pcount = (float) 0,totcount = (float) 0;
		int pcount = 0,totcount = 0;
		int DI_totcount = 0,DI_pcount = 0;
		int DI_exetime = 0;
		SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");

		Date now = new Date();

		String strDate = sdfDate.format(now);
		String DI_Tenant = null,DI_OS = null,DI_Machine="",DI_module="";
		
		
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("DI_TotTC", "0");
		hm.put("DI_PassedTC", "0");
		hm.put("Consolidation", "Mank");
		hm.put("Reporting", "Mank");
		String Module = null,Environment = null,Tenant,ExecutionMachine,OS,ExecutionStartTime,ExecutionEndTime,ExecutionTime,PassedTC,TotalTCExecuted="";
		String strHTML="";
		//String strDate = null;
//		SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
//		Date now = new Date();
//
//		strDate = sdfDate.format(now);28-02-2015
		
		FileInputStream file1 = new FileInputStream(new File("\\\\hyddc1\\hyd_shared$\\QA\\Mchiruvella\\"+strDate+"\\Consolidatedreport"+strDate+".xls")); 
		//FileInputStream file1 = new FileInputStream(new File("\\\\hyddc1\\hyd_shared$\\QA\\Mchiruvella\\28-02-2015\\Consolidatedreport28-02-2015.xls")); 
		//FileInputStream file1 = new FileInputStream(new File("\\C:\\QA\\Mchiruvella\\05-03-2015\\Consolidatedreport05-03-2015.xls"));
		HSSFWorkbook workbook = new HSSFWorkbook(file1);  

		HSSFSheet sheet = workbook.getSheet("POI Worksheet");  

		int rowNum = sheet.getLastRowNum()+1;

		for (int i=1; i<rowNum; i++){
			
			HSSFRow row = sheet.getRow(i);
			Cell cell0 = row.getCell(0);				
			Environment = ReadXML.cellToString(cell0);
				Cell cell1 = row.getCell(1);				
				Tenant = ReadXML.cellToString(cell1);
				Cell cell2 = row.getCell(2);				
				OS = ReadXML.cellToString(cell2);
				Cell cell3 = row.getCell(3);				
				ExecutionMachine = ReadXML.cellToString(cell3);
				Cell cell4 = row.getCell(4);				
				Module = ReadXML.cellToString(cell4);
				Cell cell5 = row.getCell(5);				
				ExecutionStartTime = ReadXML.cellToString(cell5);
				Cell cell6 = row.getCell(6);				
				ExecutionEndTime = ReadXML.cellToString(cell6);
				Cell cell7 = row.getCell(7);				
				ExecutionTime = ReadXML.cellToString(cell7);
				Cell cell8 = row.getCell(8);				
				PassedTC = ReadXML.cellToString(cell8);
				Cell cell9 = row.getCell(9);				
				TotalTCExecuted = ReadXML.cellToString(cell9);
				
				String[] Exetime = ExecutionTime.split("\\s");
				System.out.println("Time :"+Exetime[0]);
				
				String[] passedTC = PassedTC.split("\\.");
				System.out.println("passedTC :"+passedTC[0]);
				
				String[] totTC = TotalTCExecuted.split("\\.");
				System.out.println("totTC :"+totTC[0]);
				if(Environment.contains("https://qasandbox.hostanalytics.com")){
					pcount = pcount+Integer.parseInt(passedTC[0]);
					totcount = totcount+Integer.parseInt(totTC[0]);
					totExe_time = totExe_time+Integer.parseInt(Exetime[0]);
				}
				
				if(Module.equals("DI") && Environment.contains("https://qasandbox.hostanalytics.com")){
					DI_module = Module;
					DI_exetime = DI_exetime + Integer.parseInt(Exetime[0]);
					DI_pcount = DI_pcount+Integer.parseInt(passedTC[0]);
					DI_totcount = DI_totcount+Integer.parseInt(totTC[0]);
					DI_Tenant = Tenant;
					DI_OS = OS;
					if(DI_Machine==""){
						DI_Machine = ExecutionMachine;
					}else{
						DI_Machine = DI_Machine+","+ExecutionMachine;
					}
					
					
				}else if (Environment.contains("https://qasandbox.hostanalytics.com")){
					strHTML = strHTML + "<TR>";
					strHTML = strHTML + "<TD bgcolor='#F3F3FF' height=50px ALIGN='Center'><font face=Calibri size=3 #color#>#Module#</font></TD> ";
					strHTML = strHTML + "<TD bgcolor='#F3F3FF' height=50px ALIGN='Center'><font face=Calibri size=3 #color#>#Tenant#</font></TD>";
					strHTML = strHTML + "<TD bgcolor='#F3F3FF' height=50px ALIGN='Center'><font face=Calibri size=3 #color#>#ExecutionMachine#</font></TD>";
					strHTML = strHTML + "<TD bgcolor='#F3F3FF' height=50px ALIGN='Center'><font face=Calibri size=3 #color#>#OS#</font></TD>";
//					strHTML = strHTML + "<TD bgcolor='#F3F3FF' height=50px><font face=Calibri size=3 #color#>#ExecutionStartTime#</font></TD> ";
//					strHTML = strHTML + "<TD bgcolor='#F3F3FF' height=50px><font face=Calibri size=3 #color#>#ExecutionEndTime#</font></TD>";
					strHTML = strHTML + "<TD bgcolor='#F3F3FF' height=50px ALIGN='Center'><font face=Calibri size=3 #color#>#ExecutionTime#</font></TD> ";				
					strHTML = strHTML + "<TD bgcolor='#F3F3FF' height=50px ALIGN='Center'><font face=Calibri size=3 #color#>#TotalTCExecuted#</font></TD>";
					strHTML = strHTML + "<TD bgcolor='#F3F3FF' height=50px ALIGN='Center'><font face=Calibri size=3 #color#>#PassedTC#</font></TD> ";
					strHTML = strHTML + "</TR>";
					strHTML = strHTML.replace("#Module#",Module).replace("#Tenant#", Tenant).replace("#ExecutionMachine#", ExecutionMachine).replace("#OS#", OS).replace("#ExecutionStartTime#",ExecutionStartTime);
					strHTML = strHTML.replace("#ExecutionEndTime#",ExecutionEndTime).replace("#ExecutionTime#",ExecutionTime).replace("#PassedTC#",passedTC[0]).replace("#TotalTCExecuted#",totTC[0]);
					
					if (TotalTCExecuted.equals(PassedTC)){
						strHTML = strHTML.replace("#color#","color=green");
					}else{
						strHTML = strHTML.replace("#color#","color=red");
					}
				}
				


		}
		
		
		if(DI_totcount!=0 && Environment.contains("https://qasandbox.hostanalytics.com")){
			strHTML = strHTML + "<TR>";
			strHTML = strHTML + "<TD bgcolor='#F3F3FF' height=50px ALIGN='Center'><font face=Calibri size=3 #color#>#Module#</font></TD> ";
			strHTML = strHTML + "<TD bgcolor='#F3F3FF' height=50px ALIGN='Center'><font face=Calibri size=3 #color#>#Tenant#</font></TD>";
			strHTML = strHTML + "<TD bgcolor='#F3F3FF' height=50px ALIGN='Center'><font face=Calibri size=3 #color#>#ExecutionMachine#</font></TD>";
			strHTML = strHTML + "<TD bgcolor='#F3F3FF' height=50px ALIGN='Center'><font face=Calibri size=3 #color#>#OS#</font></TD>";
//			strHTML = strHTML + "<TD bgcolor='#F3F3FF' height=50px><font face=Calibri size=3 #color#>#ExecutionStartTime#</font></TD> ";
//			strHTML = strHTML + "<TD bgcolor='#F3F3FF' height=50px><font face=Calibri size=3 #color#>#ExecutionEndTime#</font></TD>";
			strHTML = strHTML + "<TD bgcolor='#F3F3FF' height=50px ALIGN='Center'><font face=Calibri size=3 #color#>#ExecutionTime#</font></TD> ";				
			strHTML = strHTML + "<TD bgcolor='#F3F3FF' height=50px ALIGN='Center'><font face=Calibri size=3 #color#>#TotalTCExecuted#</font></TD>";
			strHTML = strHTML + "<TD bgcolor='#F3F3FF' height=50px ALIGN='Center'><font face=Calibri size=3 #color#>#PassedTC#</font></TD> ";
			strHTML = strHTML + "</TR>";
			strHTML = strHTML.replace("#Module#",DI_module).replace("#Tenant#",DI_Tenant).replace("#ExecutionMachine#", DI_Machine).replace("#OS#", DI_OS);
			strHTML = strHTML.replace("#ExecutionTime#",Integer.toString(DI_exetime)+ " Mins").replace("#PassedTC#",Integer.toString(DI_pcount)).replace("#TotalTCExecuted#",Integer.toString(DI_totcount));
			
			if (DI_totcount==DI_pcount){
				strHTML = strHTML.replace("#color#","color=green");
			}else{
				strHTML = strHTML.replace("#color#","color=red");
			}
		}
		
		//Man

		
		String passRate =null;
		int fcount=totcount-pcount;
		

			passRate = String.format("%.0f", 100*((float)pcount/(float)totcount));

			if(pcount==totcount){
				consol_testPassPercentage = " - All tests passed";				

			}else if(pcount==0) {
				consol_testPassPercentage = " - All tests failed";
			}else{
				consol_testPassPercentage = " - "+fcount+" out of "+totcount+" tests failed ";

			}
			
			consol_testPassRate = passRate+"% ("+pcount+"/"+totcount+")";
			strHTML = strHTML + "</TABLE><BR><BR><BR>";
			strHTML = strHTML + gethtmlfiles("\\\\hyddc1\\hyd_shared$\\QA\\Mchiruvella\\"+strDate);
//			strHTML = strHTML + gethtmlfiles("C:\\hataf\\source\\TestLogs\\05-03-2015");
			
			return strHTML;
		
		
				
	}
	
	
	public static String extracthtml_details(String fileloc) throws Exception{

		String s="Manoj";
		
		   FileReader fr=new FileReader(fileloc);
		   BufferedReader br= new BufferedReader(fr);
		    String content="";
		   while((s=br.readLine())!=null)
		    {

		     content=content+s;

		    } 
		   System.out.println("Mnakoo"+content);
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
	
	public static String gethtmlfiles(String path) throws Exception{
		String strHTML="";
		
		//path = "\\C:\\QA\\Mchiruvella\\05-03-2015"; 
		  
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
