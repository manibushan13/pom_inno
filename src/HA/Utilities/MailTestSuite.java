package HA.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import HA.TestAutomation.Driver;


public class MailTestSuite {
public static String generateExecutionHTML() throws Exception{	
		
	    
		File file = new File(System.getProperty("user.dir")+"/test-output/testng-results.xml");
		String values;
		String Exetime = "";
		String starttime="";
		String endtime="";
		if(file.getName().endsWith(".xml")){

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			
			String suiteName = "suite";
			String testName = "test";
			String className = "class";
			String testmethod="test-method";
            String teststatus="status";
            StringBuilder contentBuilder = new StringBuilder();
    	    contentBuilder.append("<tr><td align='middle' style='background:#f3f3f; border:1px solid #b6b6b6; font:normal 15px Calibri; color:#color#;'>#TestID#</td>");
    	    contentBuilder.append("<td align='center' valign='middle' style='background:#f3f3f; border:1px solid #b6b6b6; font:normal 15px Calibri; color:#color#;'>#Priority#</td>");
    	    contentBuilder.append("<td align='center' valign='middle' style='background:#f3f3f; border:1px solid #b6b6b6; font:normal 15px Calibri; color:#color#;'>#Module#</td>");
    	    contentBuilder.append("<td valign='middle' style='background:#f3f3f; border:1px solid #b6b6b6; font:normal 15px Calibri; color:#color#;'>#TestScriptName#</td>");
    	    contentBuilder.append("<td align='center' valign='middle' style='background:#f3f3f; border:1px solid #b6b6b6; font:normal 15px Calibri; color:#color#;'> #TestDesc#</td>");
    	    contentBuilder.append("<td align='center' valign='middle' style='background:#f3f3f; border:1px solid #b6b6b6; font:normal 15px Calibri; color:#color#;'>#Status#</td>");
    	    contentBuilder.append("<td align='center' valign='middle' style='background:#f3f3f; border:1px solid #b6b6b6; font:normal 15px Calibri; color:#color#;'>#Remarks#</td>");
    	    contentBuilder.append("<td align='center' valign='middle' style='background:#f3f3f; border:1px solid #b6b6b6; font:normal 15px Calibri; color:#color#;'>#Exetime#</td></tr>");
    		    
			String Row=contentBuilder.toString();
			String exceptionname="";
			String dynamicRow;
			
			NodeList resultNode = doc.getElementsByTagName("testng-results");
			
			for (int i = 0; i < resultNode.getLength(); i++) 
			{
				Node rNode = resultNode.item(i);
			
			Element resultElement = (Element) rNode;
			
			String skipped = resultElement.getAttribute("skipped");
			String failed = resultElement.getAttribute("failed");
			String total = resultElement.getAttribute("total");
			String passed = resultElement.getAttribute("passed");
			
			
			Driver.logger.info("No of test skipped: "+skipped);
			Driver.logger.info("No of test failed: "+failed);
			Driver.logger.info("No of test total: "+total);
			Driver.logger.info("No of test passed: "+passed);
			
			NodeList sList = doc.getElementsByTagName(suiteName);
			for (int temp = 0; temp < sList.getLength(); temp++) 
			{
				Node suiteNode = sList.item(temp);
				
				Element suiteElement = (Element) suiteNode;	
				Exetime = HTMLPreparation.MilliSecondsToMinutes(Long.parseLong(suiteElement.getAttribute("duration-ms")));
				String Suite = suiteElement.getAttribute("name");
				starttime = suiteElement.getAttribute("started-at");
				endtime = suiteElement.getAttribute("finished-at");
				Driver.logger.info("Exetime: "+Exetime);
				Driver.logger.info("Suite Name: "+Suite);
				generateHTMLReport.starttime=starttime;
				generateHTMLReport.endtime=endtime;
				generateHTMLReport.Exetime=suiteElement.getAttribute("duration-ms");
				NodeList nList = suiteElement.getElementsByTagName(testName);


				for (int k = 0; k < nList.getLength(); k++) 
				{
					
					dynamicRow=Row;
					Node testNode = nList.item(k);
					
					Element tableElement = (Element) testNode;	

							    	  
						NodeList testList = tableElement.getElementsByTagName(className);
						for (int j = 0; j < testList.getLength(); j++) 
						{
							
							String testscriptname = tableElement.getAttribute("name");
							String testexetime = HTMLPreparation.MilliSecondsToMinutes(Long.parseLong(tableElement.getAttribute("duration-ms")));
							Driver.logger.info("Test Script name: "+testscriptname);
							Driver.logger.info("Test Script Exetime: "+testexetime);
							
							
							Node classNode = testList.item(j);
							Element classElement = (Element) classNode;				         
							String classname = classElement.getAttribute("name");
							Driver.logger.info("Test class name: "+classname);
							
							
							NodeList testmethodList = tableElement.getElementsByTagName(testmethod);
							loopmethod:
							for (int l = 0; l < testmethodList.getLength(); l++) 
							{
								
															
								Node testmethodNode = testmethodList.item(l);
								Element testmethodElement = (Element) testmethodNode;				         
								String testmethodname1 = testmethodElement.getAttribute("name");
								Driver.logger.info("Test Method name: "+testmethodname1);
								values=testmethodElement.getAttribute(teststatus);
								
								ArrayList<String> stringList = new ArrayList<String>();
								stringList =generateHTMLReport.getTCdetails(k);
								System.out.println("Value of K: "+k);
								String testids = stringList.get(0);
								String testdesc = stringList.get(1);
								String priority = stringList.get(2);
								String module = stringList.get(3);
								String[] TCcount = testids.split(",");
								dynamicRow=dynamicRow.replace("#TestScriptName#", testscriptname).replace("#Exetime#", testexetime).replace("#TestID#", testids).replace("#Priority#", priority).replace("#TestDesc#", testdesc).replace("#Module#", module);
								if(values.equals("PASS")){
									Driver.logger.info("Test :"+testmethodElement.getAttribute("signature")+" is passed");
									if(l==testmethodList.getLength()-1){
										
										dynamicRow=dynamicRow.replaceAll("#color#","green").replace("#Remarks#","").replace("#Status#", "PASS");
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
										
										exceptionname =generateHTMLReport.exception(exceptionname);

										Driver.logger.info("Exception :"+ generateHTMLReport.exception(exceptionname));
									
									}
									
									Driver.logger.info("Test :"+testmethodElement.getAttribute("signature")+" is failed");
									Driver.logger.info("Test :"+classElement.getAttribute("name")+" is failed");
									dynamicRow=dynamicRow.replaceAll("#color#","red").replace("#Remarks#",exceptionname).replace("#Status#","FAIL");
									mail.totalTCcount =mail.totalTCcount+TCcount.length;
									mail.failTCcount =mail.failTCcount+TCcount.length;
									
									break loopmethod;
								}								
																							
							}	
							
						}					
					
						
						HTMLPreparation.htmlContent=HTMLPreparation.htmlContent.replace("#Row#", dynamicRow+"#Row#");		
				}
				
			}
			}

		}
	
		generateHTMLReport.testPassPercentage();
		HTMLPreparation.htmlContent=HTMLPreparation.htmlContent.replace("#Row#","").replace("#TestPassRate#",mail.testPassRate).replace("#ExecutionStartTime#",timedate.getlocaltime(starttime)).replace("#ExecutionEndTime#",timedate.getlocaltime(endtime)).replace("#ExecutionTime#",Exetime);	
		return mail.testPassPercentage;
	}
	
public static  Map<String, Row> generateConsolidatedHTML() throws Exception
{
	String Environment="https://qasandbox.haicpm.com";
	SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");

	Date now = new Date();

	String strDate = sdfDate.format(now);
	FileInputStream file1 = new FileInputStream(new File("\\\\hyddc1\\hyd_shared$\\QA\\Mchiruvella\\"+strDate+"\\Consolidatedreport"+strDate+".xls")); 

	HSSFWorkbook workbook = new HSSFWorkbook(file1);  

	HSSFSheet sheet = workbook.getSheet("POI Worksheet");  

	int rowNum = sheet.getLastRowNum()+1;
	Map<String, Row> map =new LinkedHashMap<String, Row>();
	
	for (int i=1; i<rowNum; i++){
		
		HSSFRow row = sheet.getRow(i);
		Cell cell0 = row.getCell(0);				
		Environment = ReadXML.cellToString(cell0);
		if(Environment.contains("https://qasandbox.haicpm.com"))
		{
			Row reportRow=new Row();
			Cell cell1 = row.getCell(1);				
			reportRow.Tenant = ReadXML.cellToString(cell1);
			Cell cell2 = row.getCell(2);				
			reportRow.OS = ReadXML.cellToString(cell2);
			Cell cell3 = row.getCell(3);				
			reportRow.ExecutionMachine = ReadXML.cellToString(cell3);
			Cell cell4 = row.getCell(4);				
			reportRow.Module = ReadXML.cellToString(cell4);
			Cell cell7 = row.getCell(7);				
			reportRow.ExecutionTime = ReadXML.cellToString(cell7);
			Cell cell8 = row.getCell(8);				
			reportRow.Passed_TC = ReadXML.cellToString(cell8);
			Cell cell9 = row.getCell(9);				
			reportRow.Total_TC = ReadXML.cellToString(cell9);
			Cell cell10 = row.getCell(10);				
			reportRow.Browser = ReadXML.cellToString(cell10);
			 System.out.println("In common"+reportRow.Total_TC);
			String[] Exetime = reportRow.ExecutionTime.split("\\s");
			System.out.println("Time :"+Exetime[0]);
			
			String[] passedTC = reportRow.Passed_TC.split("\\.");
			System.out.println("passedTC :"+passedTC[0]);
			
			String[] totTC = reportRow.Total_TC.split("\\.");
			System.out.println("totTC :"+totTC[0]);
					
	        String key=reportRow.Module+"_"+reportRow.OS+"_"+reportRow.Browser+"_"+reportRow.Tenant;
	        System.out.println("Inside generateConsolidatedHTML while loop"+i);
	        if(!map.containsKey(key))
	        {
	        reportRow.Percentage=(int)((Float.parseFloat(reportRow.Passed_TC)/Float.parseFloat(reportRow.Total_TC))*100)+"";
		    map.put(key,reportRow);
	        }
	        else
	        {
	        	Row checkRow=map.get(key);
	        	checkRow.ExecutionTime=(Integer.parseInt(checkRow.ExecutionTime.split(" ")[0])+Integer.parseInt(reportRow.ExecutionTime.split(" ")[0]))+" Mins";
	        	checkRow.Total_TC=(Integer.parseInt(checkRow.Total_TC)+Integer.parseInt(reportRow.Total_TC))+"";
	        	checkRow.Passed_TC=(Integer.parseInt(checkRow.Passed_TC)+Integer.parseInt(reportRow.Passed_TC))+"";
	        	checkRow.Percentage= (int)((Float.parseFloat(checkRow.Passed_TC)/Float.parseFloat(checkRow.Total_TC))*100)+"";
	        	checkRow.ExecutionMachine=checkRow.ExecutionMachine+","+reportRow.ExecutionMachine;
	        	map.put(key, checkRow);

	        }
		
		}
}
	return map;
}

public static void generateConsolidatedTable(Map<String, Row> map ) throws FileNotFoundException, IOException
{
	 int TOTAL=0,PASSED=0,PASSRATE;
	 Set<String> keySet=map.keySet();
	 Iterator<String> it = keySet.iterator();
	 int i=0;
	 int passedTC,totalTC,ExeTime,testPassRate;
	 Map<String,List<Row>> os_browser_combinations=new LinkedHashMap<String,List<Row>>();
	   List<String> mailFormat =new ArrayList<String>();

		Properties prop = new Properties();
	    prop.load(new FileInputStream(System.getProperty("user.dir")+"/config/mail.properties"));
		
		String[] browser = prop.getProperty("BROWSER").split(",");
        List<String> browserList=Arrays.asList(browser);
        
        String[] OS = prop.getProperty("OS").split(",");
        List<String> OSList=Arrays.asList(OS);

        for (String s1: OSList) {
        	 for (String s2 : browserList) {
        		 mailFormat.add(s1+"_"+s2);	
        	 }
        	}
; 
	 
       StringBuilder contentBuilder1 = new StringBuilder();
	    contentBuilder1.append("<tr><td align='middle' style='background:#f3f3f; border:1px solid #b6b6b6; font:normal 15px Calibri; color:black;'>#OS#</td>");
	    contentBuilder1.append("<td align='center' valign='middle' style='background:#f3f3f; border:1px solid #b6b6b6; font:normal 15px Calibri; color:black;'>#Browser#</td>");
        contentBuilder1.append("<td align='middle' style='background:#f3f3f; border:1px solid #b6b6b6; font:normal 15px Calibri; color:black;'>#Module#</td>");
	    contentBuilder1.append("<td align='center' valign='middle' style='background:#f3f3f; border:1px solid #b6b6b6; font:normal 15px Calibri; color:black;'>#Tenant#</td>");
	    contentBuilder1.append("<td align='center' valign='middle' style='background:#f3f3f; border:1px solid #b6b6b6; font:normal 15px Calibri; color:black;'>#ExecutionMachine#</td>");
	    contentBuilder1.append("<td align='center' valign='middle' style='background:#f3f3f; border:1px solid #b6b6b6; font:normal 15px Calibri; color:black;'> #ExecutionTime#</td>");
	    contentBuilder1.append("<td align='center' valign='middle' style='background:#f3f3f; border:1px solid #b6b6b6; font:normal 15px Calibri; color:black;'>#Total_TC#</td>");
	    contentBuilder1.append("<td  align='center' valign='middle' style='background:#f3f3f; border:1px solid #b6b6b6; font:normal 15px Calibri; color:black;'>#Passed_TC#</td>");
	    contentBuilder1.append("<td align='center' valign='middle' style='background:#f3f3f; border:1px solid #b6b6b6; font:normal 15px Calibri; color:black;'>#Percentage#</td></tr>");	
        String report=contentBuilder1.toString();
        StringBuilder contentBuilder2 = new StringBuilder();
 	    contentBuilder2.append("<tr><td align='middle' style='background:#f3f3f; border:1px solid #b6b6b6; font:normal 15px Calibri;  '>#OS#</td>");
        contentBuilder2.append("<td align='center' valign='middle' style='background:#f3f3f; border:1px solid #b6b6b6; font:normal 15px Calibri;  '>#Browser#</td>");
 	    contentBuilder2.append("<td align='center' valign='middle' style='background:#f3f3f; border:1px solid #b6b6b6; font:normal 15px Calibri;  '>#TestPassRate#</td>");
 	    contentBuilder2.append("<td align='center' valign='middle' style='background:#f3f3f; border:1px solid #b6b6b6; font:normal 15px Calibri;  '>#PassedTC#</td>");
 	    contentBuilder2.append("<td align='center' valign='middle' style='background:#f3f3f; border:1px solid #b6b6b6; font:normal 15px Calibri;  '>#TotalTC#</td>");
 	    contentBuilder2.append("<td align='center' valign='middle' style='background:#f3f3f; border:1px solid #b6b6b6; font:normal 15px Calibri;  '>#ExeTime#</td></tr>");
 	   String consolReport=	contentBuilder2.toString();					
       
	
		String dynamicReport,dynamicConsolRow;
		String key ="default";
        while(i<keySet.size()) {
       
             Row row=map.get(it.next());
             key=row.OS+"_"+row.Browser;
                         
             if(os_browser_combinations.containsKey(key))
             {
            	 os_browser_combinations.get(key).add(row);
             }
             else
             {
            	 List<Row> rowList= new ArrayList<Row>();
                 rowList.add(row);
            	 os_browser_combinations.put(key, rowList);
             }
             i++;
       }    
               
        i=0;  
   
        while(i<mailFormat.size()) {
        	
        	if(os_browser_combinations.containsKey(mailFormat.get(i)))
        	{	  
        	 List<Row> rowList=os_browser_combinations.get(mailFormat.get(i));
        	 passedTC=0;
        	 totalTC=0;
        	 ExeTime=0;
        	 
        	 dynamicConsolRow=consolReport;
        	 Row firstRow=rowList.get(0);
        	 for(int j=0;j<rowList.size();j++)
        	 {
        	 Row row=rowList.get(j);
             dynamicReport=report;
             dynamicReport=dynamicReport.replace("#OS#",row.OS).replace("#Browser#",row.Browser).replace("#Module#",row.Module).replace("#Tenant#",row.Tenant).replace("#ExecutionMachine#",row.ExecutionMachine);   
             dynamicReport=dynamicReport.replace("#ExecutionTime#",calculateTime(Integer.parseInt(row.ExecutionTime.split(" ")[0]))).replace("#Total_TC#", row.Total_TC).replace("#Passed_TC#", row.Passed_TC).replace("#Percentage#", row.Percentage); 
             System.out.println("Inside generateConsolidatedTable while loop"+i);
             HTMLPreparation.htmlContent= HTMLPreparation.htmlContent.replace("#Row#",dynamicReport+"#Row#");
             passedTC+=Integer.parseInt(row.Passed_TC);
             totalTC+=Integer.parseInt(row.Total_TC);
           
             ExeTime+=Integer.parseInt(row.ExecutionTime.split(" ")[0]);
        	 }
        	  TOTAL+=totalTC;
              PASSED+=passedTC;
              String totalExeTime= calculateTime(ExeTime);
        	 dynamicConsolRow= dynamicConsolRow.replace("#OS#",firstRow.OS).replace("#Browser#",firstRow.Browser).replace("#PassedTC#",passedTC+"").replace("#TotalTC#",totalTC+"").replace("#ExeTime#",totalExeTime);
        	 testPassRate=(int)((float)passedTC/(float)totalTC*100);
        	 dynamicConsolRow=dynamicConsolRow.replace("#TestPassRate#", testPassRate+"%");
        	  HTMLPreparation.htmlContent= HTMLPreparation.htmlContent.replace("#ConsolidatedRow#",dynamicConsolRow+"#ConsolidatedRow#");
	 
        }  
        	 i++;
        }
        PASSRATE=(int)((float)PASSED/(float)TOTAL*100);  
        HTMLPreparation.htmlContent= HTMLPreparation.htmlContent.replace("#Row#","").replace("#dynamicConsolRow#","").replace("#TestPassRate#", PASSRATE+"%").replace("#URL#","https://qasandbox.haicpm.com").replace("#ConsolidatedRow#","");
           

		if(PASSED==TOTAL){
			mailGeneration.subject+= "- All tests passed";				

		}else if(PASSED==0) {
			mailGeneration.subject += "- All tests failed";
		}else{
			int FAILED=TOTAL-PASSED;
		
			mailGeneration.subject+= " - "+FAILED+" out of "+TOTAL+" tests failed ";
		}
}

private static String calculateTime(int exeTime) {
	
	String time;
	if(exeTime>60){
		int hours = (int) Math.floor(exeTime / 60); 
		int minutes = exeTime % 60;
		time= hours+":" +minutes + " Hrs";
	
	}
	else{
		time=String.valueOf(exeTime)+" Mins";
	}
	return time;
	
}

public static void generateStartMail() throws Exception {
	File fXmlFile = new File(System.getProperty("user.dir")+"/TestNG.xml");
	
	   StringBuilder contentBuilder1 = new StringBuilder();
			contentBuilder1.append("<tr><td align='middle' style='background:#f3f3f; border:1px solid #b6b6b6; font:normal 15px Calibri;'>#TestCaseID#</td>");
	        contentBuilder1.append("<td align='center' valign='middle' style='background:#f3f3f; border:1px solid #b6b6b6; font:normal 15px Calibri;'>#Priority#</td>");
			contentBuilder1.append("<td align='center' valign='middle' style='background:#f3f3f; border:1px solid #b6b6b6; font:normal 15px Calibri;'>#Module#</td>");
			contentBuilder1.append("<td valign='middle' style='background:#f3f3f; border:1px solid #b6b6b6; font:normal 15px Calibri;'>#TestCaseName#</td>");
			contentBuilder1.append("<td align='center' valign='middle' style='background:#f3f3f; border:1px solid #b6b6b6; font:normal 15px Calibri;'>#Description#</td></tr>");
		    String row=contentBuilder1.toString();
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(fXmlFile);

	doc.getDocumentElement().normalize();
 
 
	NodeList nList = doc.getElementsByTagName("test");
	String module = "";
    String dynamicRow;
    System.out.println("LIST SIZE"+nList.getLength());
	for (int temp =0; temp < nList.getLength(); temp++) 
	{
        dynamicRow=row;
		ArrayList<String> stringList = new ArrayList<String>();
		stringList =generateHTMLReport.getTCdetails(temp);
		System.out.println("Value of temp: "+temp);
		String testids = stringList.get(0);
		String testdesc = stringList.get(1);
		String priority = stringList.get(2);
	    module = stringList.get(3);
		String className = stringList.get(4);
		String[] TCcount = testids.split(",");
		dynamicRow=dynamicRow.replace("#TestCaseID#",testids).replace("#Priority#",priority).replace("#Module#","POC").replace("#Description#", testdesc).replace("#TestCaseName#", className.split("HA.TestExecute.")[1]);
		System.out.println("Value of temp: "+TCcount);
		System.out.println(testids);
		System.out.println(testdesc);
		System.out.println(priority);
		System.out.println(module); //module
		HTMLPreparation.htmlContent= HTMLPreparation.htmlContent.replace("#Row#",dynamicRow+"#Row#");
	}
	 if(module.equals("DI"))
     {
		 HTMLPreparation.htmlContent=HTMLPreparation.htmlContent.replace( "#HorizonStatus#", "    <tr>"
                          +"   <td align='left' valign='middle' style='background:#e9e9e9; border:1px solid #b6b6b6; font:bold 13px 'Segoe UI', Arial, Helvetica, sans-serif;' class='auto-style1'>Horizon Status</td>"
                           +"  <td align='left' valign='middle' style='border:1px solid #b6b6b6; font:normal 13px 'Segoe UI', Arial, Helvetica, sans-serif; '>"+"Sample_Build"+"</td>"
                         +"</tr>");
     }
     else
    	 HTMLPreparation.htmlContent=HTMLPreparation.htmlContent.replace( "#HorizonStatus#","");

    mailGeneration.subject+=": "+module+" Module Start Mail";
	HTMLPreparation.htmlContent= HTMLPreparation.htmlContent.replace("#Row#","");
}
        
}



class Row
{
	String OS,Browser,Module,Tenant,ExecutionMachine,ExecutionTime,Total_TC,Passed_TC,Percentage;
	
}