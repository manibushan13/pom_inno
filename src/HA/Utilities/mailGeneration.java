package HA.Utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import org.testng.ITestResult;

import HA.Properties.HATF_properties;
import HA.Properties.logApp;

public class MailGeneration {

	public static HATF_properties _properties = new HATF_properties();
	public static String mailTemplatePath=System.getProperty("user.dir")+"/MailTemplates/";
	public static String htmlContent, htmlreportpath;
	public static String module;
	public static ITestResult test;

	public static String parseHTMLfile(String filePath)
	{
		StringBuilder contentBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new FileReader(filePath));
			String str;
			while ((str = in.readLine()) != null) {
				contentBuilder.append(str);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		logApp.logger.info("File Parsed");
		return contentBuilder.toString();
	}

	public static String prepmail(String fileName) throws Exception{

		FileWriter fWriter = null;
		BufferedWriter writer = null;
		String strDate = timedate.GetDateTimeforHTML();
		String sFilename = fileName+ strDate+ ".html";
		String htmlreportpath =System.getProperty("user.dir") + "/test-output/" + File.separator + sFilename;
		try {

			fWriter = new FileWriter(htmlreportpath);
			writer = new BufferedWriter(fWriter);
			writer.write(htmlContent);;
			writer.close();
			System.out.println("MAil Body prepared");
			logApp.logger.info("MAil Body prepared");
		} catch (Exception e) {
			e.printStackTrace();
			logApp.logger.error(e);
		}
		return htmlreportpath;
	}


	public static String generateMail(String mailType) throws Exception {

		String filePath;
		switch(mailType)
		{
		case "Start" :
		{
			filePath=mailTemplatePath+"ExecutionStartMail.html";
			MailTrigger.to= _properties.getProperty("Exestartmail");
			htmlContent= parseHTMLfile(filePath).replace("#Browser#", _properties.getProperty("BROWSER") );

			fillCommonDetails();
			MailBody.generateStartHTML();
			return prepmail("Execution_StartMail");
		}
		case "ExecutionReport":
		{
			filePath=mailTemplatePath+"ExecutionMail.html";
			MailTrigger.to= _properties.getProperty("Exemail");
			htmlContent= parseHTMLfile(filePath).replace("#Browser#", _properties.getProperty("BROWSER") );
			fillCommonDetails();
			//			System.out.println("Module Name:"+HTMLPreparation.module);
			String module=GenerateHTML.getTCdetails(0).get(4).split("HA.TestExecute.")[0]+" "+ _properties.getProperty("BROWSER");
			//           String ModuleName=generateHTMLReport.getTCdetails(0).get(4).split("HA.TestExecute.")[1].split("\\.")[0];

			htmlContent=htmlContent.replace( "#HorizonStatus#","");
			MailTrigger.subject+=": "+module+" Module Results"+MailBody.generateExecutionHTML();;
			htmlreportpath=prepmail("Execution_report");
			return htmlreportpath;
		}
		case "failure":
		{
			filePath=mailTemplatePath+"ExecutionFail.html";
			MailTrigger.subject="Test case Failed";
			MailTrigger.to= _properties.getProperty("FailureMail");
			htmlContent= parseHTMLfile(filePath);
			fillCommonDetails();
			String testDetails = test.getTestClass().toString();
			String testName=testDetails.split("HA.TestExecute.")[1];
			String Module= testName.split("\\.")[0];
			String testScriptName =(testName.split("\\.")[1]).split("]")[0];
			String testDuration = MilliSecondsToMinutes(test.getStartMillis()-test.getEndMillis());
			String Remarks=test.getThrowable().toString();
			Remarks=Remarks.split("\\:")[0];
			htmlContent=htmlContent.replace("#Module#",Module).replace("#Test Script Name#",testScriptName).replace("#Test Duration#",testDuration).replace("#Remarks#",GenerateHTML.exception(Remarks));
			return prepmail("TC_Failed");
		}
		}
		return null;
	}



	public static void fillCommonDetails() throws UnknownHostException
	{
		htmlContent=htmlContent.replace("#URL#",_properties.getProperty("BASEURL")).replace("#OS#",System.getProperty("os.name")).replace("#ExecutionMachine#",InetAddress.getLocalHost().getHostName()).replace("#BuildVersion#","Sample_Build");
	}




	public static void setFailedTestcaseDetails(ITestResult testCase) {

		test=testCase;

	}


	public static String MilliSecondsToMinutes(long testexetime)
	{
		String time;
		long minutes = TimeUnit.MILLISECONDS.toMinutes(testexetime);
		if(minutes<1){
			minutes = TimeUnit.MILLISECONDS.toSeconds(testexetime);
			time="1 Mins";
			logApp.logger.info("Test Script Exetime in Secs: "+minutes);
		}
		else{
			time=String.valueOf(minutes)+" Mins";
		}
		return time;
	}
}
