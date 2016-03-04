package HA.Utilities;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import HA.Properties.logApp;


public class MailBody {

	public static String generateExecutionHTML() throws Exception{	

		File file = new File(System.getProperty("user.dir")+"/test-output/testng-results.xml");
		String values;
		String Exetime = "";
		String starttime="";
		String endtime="";
		if(file.getName().endsWith(".xml"))
		{
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


				logApp.logger.info("No of test skipped: "+skipped);
				logApp.logger.info("No of test failed: "+failed);
				logApp.logger.info("No of test total: "+total);
				logApp.logger.info("No of test passed: "+passed);

				NodeList sList = doc.getElementsByTagName(suiteName);
				for (int temp = 0; temp < sList.getLength(); temp++) 
				{
					Node suiteNode = sList.item(temp);

					Element suiteElement = (Element) suiteNode;	
					Exetime = MailGeneration.MilliSecondsToMinutes(Long.parseLong(suiteElement.getAttribute("duration-ms")));
					String Suite = suiteElement.getAttribute("name");
					starttime = suiteElement.getAttribute("started-at");
					endtime = suiteElement.getAttribute("finished-at");
					logApp.logger.info("Exetime: "+Exetime);
					logApp.logger.info("Suite Name: "+Suite);
					GenerateHTML.starttime=starttime;
					GenerateHTML.endtime=endtime;
					GenerateHTML.Exetime=suiteElement.getAttribute("duration-ms");
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
							String testexetime = MailGeneration.MilliSecondsToMinutes(Long.parseLong(tableElement.getAttribute("duration-ms")));
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
									values=testmethodElement.getAttribute(teststatus);

									ArrayList<String> stringList = new ArrayList<String>();
									stringList =GenerateHTML.getTCdetails(k);
									System.out.println("Value of K: "+k);
									String testids = stringList.get(0);
									String testdesc = stringList.get(1);
									String priority = stringList.get(2);
									String module = stringList.get(3);
									String[] TCcount = testids.split(",");
									dynamicRow=dynamicRow.replace("#TestScriptName#", testscriptname).replace("#Exetime#", testexetime).replace("#TestID#", testids).replace("#Priority#", priority).replace("#TestDesc#", testdesc).replace("#Module#", module);
									if(values.equals("PASS")){
										logApp.logger.info("Test :"+testmethodElement.getAttribute("signature")+" is passed");
										if(l==testmethodList.getLength()-1){

											dynamicRow=dynamicRow.replaceAll("#color#","green").replace("#Remarks#","").replace("#Status#", "PASS");
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

											exceptionname =GenerateHTML.exception(exceptionname);

											logApp.logger.info("Exception :"+ GenerateHTML.exception(exceptionname));

										}

										logApp.logger.info("Test :"+testmethodElement.getAttribute("signature")+" is failed");
										logApp.logger.info("Test :"+classElement.getAttribute("name")+" is failed");
										dynamicRow=dynamicRow.replaceAll("#color#","red").replace("#Remarks#",exceptionname).replace("#Status#","FAIL");
										MailTrigger.totalTCcount =MailTrigger.totalTCcount+TCcount.length;
										MailTrigger.failTCcount =MailTrigger.failTCcount+TCcount.length;

										break loopmethod;
									}								

								}	

						}					


						MailGeneration.htmlContent=MailGeneration.htmlContent.replace("#Row#", dynamicRow+"#Row#");		
					}

				}
			}

		}

		GenerateHTML.testPassPercentage();
		MailGeneration.htmlContent=MailGeneration.htmlContent.replace("#Row#","").replace("#TestPassRate#",MailTrigger.testPassRate).replace("#ExecutionStartTime#",timedate.getlocaltime(starttime)).replace("#ExecutionEndTime#",timedate.getlocaltime(endtime)).replace("#ExecutionTime#",Exetime);	
		return MailTrigger.testPassPercentage;
	}

	public static void generateStartHTML() throws Exception {
		File XmlFile = new File(System.getProperty("user.dir")+"/TestNG.xml");

		StringBuilder contentBuilder1 = new StringBuilder();
		contentBuilder1.append("<tr><td align='middle' style='background:#f3f3f; border:1px solid #b6b6b6; font:normal 15px Calibri;'>#TestCaseID#</td>");
		contentBuilder1.append("<td align='center' valign='middle' style='background:#f3f3f; border:1px solid #b6b6b6; font:normal 15px Calibri;'>#Priority#</td>");
		contentBuilder1.append("<td align='center' valign='middle' style='background:#f3f3f; border:1px solid #b6b6b6; font:normal 15px Calibri;'>#Module#</td>");
		contentBuilder1.append("<td valign='middle' style='background:#f3f3f; border:1px solid #b6b6b6; font:normal 15px Calibri;'>#TestCaseName#</td>");
		contentBuilder1.append("<td align='center' valign='middle' style='background:#f3f3f; border:1px solid #b6b6b6; font:normal 15px Calibri;'>#Description#</td></tr>");
		String row=contentBuilder1.toString();
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(XmlFile);

		doc.getDocumentElement().normalize();


		NodeList nList = doc.getElementsByTagName("test");
		String module = "";
		String dynamicRow;
		System.out.println("LIST SIZE"+nList.getLength());
		for (int temp =0; temp < nList.getLength(); temp++) 
		{
			dynamicRow=row;
			ArrayList<String> stringList = new ArrayList<String>();
			stringList =GenerateHTML.getTCdetails(temp);
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
			MailGeneration.htmlContent= MailGeneration.htmlContent.replace("#Row#",dynamicRow+"#Row#");
		}
			MailGeneration.htmlContent=MailGeneration.htmlContent.replace( "#HorizonStatus#","");

		MailTrigger.subject+=": "+module+" Module Start Mail";
		MailGeneration.htmlContent= MailGeneration.htmlContent.replace("#Row#","");
	}

}



class Row
{
	String OS,Browser,Module,Tenant,ExecutionMachine,ExecutionTime,Total_TC,Passed_TC,Percentage;

}