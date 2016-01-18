package HA.Utilities;
 
import java.io.File;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import HA.Properties.HATF_properties;
import HA.Properties.logApp;
import HA.SeleniumLib.Common;

 
public class Listener implements  ISuiteListener,ITestListener {
	int executionstatus;
 
    // This belongs to ISuiteListener and will execute before the Suite start
	@Override
    public void onStart(ISuite arg0) {
    	try {
    		logApp.logIntilize();
    		Common.Clean_Automation_Environment();
    		mail.exemail();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	Txtfilecreation.filecreation();
    	Txtfilecreation.filereadyforwritingdata();
    	 logApp.logger.info("About to begin executing Suite: " + arg0.getName());
    	 Txtfilecreation.filewrite("About to begin executing Suite: " + arg0.getName());
    	 
        
    }
    
    // This belongs to ISuiteListener and will execute, once the Suite is finished
	@Override
    public void onFinish(ISuite arg0) {
    	try {
    		//mail.sendmail();
    	} catch (Exception e) {			
    		e.printStackTrace();
    	}
    	 logApp.logger.info("About to end executing Suite: " + arg0.getName());
    	 Txtfilecreation.filewrite("About to end executing Suite: " + arg0.getName());
    	 Txtfilecreation.fileclose();
    	 Common.Regression_Run_Backup();
        
    }

	@Override
	public void onStart(ITestContext arg0) {
		executionstatus = 0;		
		VideoCapture.startVideoCapture();
		logApp.logger.info("About to begin executing Test: " + arg0.getName());
		Txtfilecreation.filewrite("About to begin executing Test: " + arg0.getName());
	}
	
	@Override
	public void onFinish(ITestContext arg0) {		
		
		logApp.logger.info("About to end executing Test: " + arg0.getName());
		Txtfilecreation.filewrite("About to end executing Test: " + arg0.getName());
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
	public void onTestFailure(ITestResult arg0) {
		executionstatus = arg0.getStatus();
		System.out.println("executionstatus : " +executionstatus);
		logApp.logger.info("Test Failed: " + arg0.getName());
		HATF_properties _properties = new HATF_properties();
		if(_properties.getProperty("FailureMail")!=null)
		{
			HTMLPreparation.setFailedTestcaseDetails(arg0);
			try {
				mailGeneration.sendMail("failure");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Txtfilecreation.filewrite("Test Failed: " + arg0.getName());
		
	}
	@Override
	public void onTestSkipped(ITestResult arg0) {
		
		executionstatus = arg0.getStatus();
		System.out.println("executionstatus : " +executionstatus);
		logApp.logger.info("Test Skipped: " + arg0.getName());
		Txtfilecreation.filewrite("Test Skipped: " + arg0.getName());
	}

	@Override
	public void onTestStart(ITestResult arg0) {
			
		executionstatus = arg0.getStatus();
		System.out.println("executionstatus : " +executionstatus);
		logApp.logger.info("Test Started: " + arg0.getName());
		Txtfilecreation.filewrite("Test Started: " + arg0.getName());
	}

	@Override
	public void onTestSuccess(ITestResult arg0) {
		
		logApp.logger.info("Test Passed: " + arg0.getName());
		Txtfilecreation.filewrite("Test Passed: " + arg0.getName());
	}
    
}