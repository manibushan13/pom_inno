package HA.Utilities;

import java.io.File;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import HA.Properties.logApp;
import HA.SeleniumLib.Common;


public class Listener implements  ISuiteListener,ITestListener {
	int executionstatus;
	String tName="";

	// This belongs to ISuiteListener and will execute before the Suite start
	@Override
	public void onStart(ISuite arg0) {
		try {
//			logApp.logIntilize();
			Common.Clean_Automation_Environment();
			MailTrigger.startmail();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Util.filecreation();
		Util.filereadyforwritingdata();
		logApp.logger.info("About to begin executing Suite: " + arg0.getName());
		Util.filewrite("About to begin executing Suite: " + arg0.getName());
	}

	// This belongs to ISuiteListener and will execute, once the Suite is finished
	@Override
	public void onFinish(ISuite arg0) {
		try {
			//mailGeneration.reportmail();
		} catch (Exception e) {			
			e.printStackTrace();
		}
		logApp.logger.info("About to end executing Suite: " + arg0.getName());
		Util.filewrite("About to end executing Suite: " + arg0.getName());
		Util.fileclose();
	}
	

	@Override
	public void onStart(ITestContext arg0) {
		executionstatus = 0;		
		VideoCapture.startVideoCapture();
		logApp.logger.info("About to begin executing Test: " + arg0.getName());
		Util.filewrite("About to begin executing Test: " + arg0.getName());
		tName=arg0.getName();
	}

	@Override
	public void onFinish(ITestContext arg0) {		

		logApp.logger.info("About to end executing Test: " + arg0.getName());
		Util.filewrite("About to end executing Test: " + arg0.getName());
		String videopath = VideoCapture.stopVideoCapture(arg0.getName());
		System.out.println("executionstatus : " +executionstatus);
		if(executionstatus==16)
		{
			if(videopath!=null)
			{
				File f = new File(videopath);
				f.delete();
			}
		}
	}
	

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {


	}
	
	@Override
	public void onTestSkipped(ITestResult arg0) {

		executionstatus = arg0.getStatus();
		System.out.println("executionstatus : " +executionstatus);
		logApp.logger.info("Test Skipped: " + arg0.getName());
		Util.filewrite("Test Skipped: " + arg0.getName());
	}

	@Override
	public void onTestStart(ITestResult arg0) {

		executionstatus = arg0.getStatus();
		System.out.println("executionstatus : " +executionstatus);
		logApp.logger.info("Test Started: " + arg0.getName());
		Util.filewrite("Test Started: " + arg0.getName());
	}

	@Override
	public void onTestSuccess(ITestResult arg0) {

		logApp.logger.info("Test Passed: " + arg0.getName());
		Util.filewrite("Test Passed: " + arg0.getName());
	}

	@Override
	public void onTestFailure(ITestResult arg0) {
		executionstatus = arg0.getStatus();
		System.out.println("executionstatus : " +executionstatus);
		logApp.logger.info("Test Failed: " + arg0.getName());
		System.out.println("......"+tName);
		try{
		Common.screenShot(tName);
		} catch(Exception e){
			e.printStackTrace();
		}
		Util.filewrite("Test Failed: " + arg0.getName());
		
	}

}