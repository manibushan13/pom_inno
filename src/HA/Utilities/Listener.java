package HA.Utilities;
 
import java.io.File;
//import org.testng.ITestContext;
//import org.testng.ITestListener;
//import org.testng.ITestNGMethod;
//import org.testng.ITestResult;
//import org.testng.Reporter;

import org.apache.log4j.Logger;
//import org.testng.IInvokedMethod;
//import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import HA.SeleniumLib.Common;
import HA.TestAutomation.Driver;
import HA.TestAutomation.HATF_properties;

 
public class Listener implements  ISuiteListener,ITestListener {
	//public class Listener implements ITestListener, ISuiteListener, IInvokedMethodListener {
	final public static Logger logger = Logger.getLogger(Listener.class);
	int executionstatus;
 
    // This belongs to ISuiteListener and will execute before the Suite start
	@Override
    public void onStart(ISuite arg0) {
    	try {
    		Common.Clean_Automation_Environment();
    		Driver.logIntilize();
    		mail.exemail();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
        //Reporter.log("About to begin executing Suite " + arg0.getName(), true);
//        Listener.logger.info("About to begin executing Suite " + arg0.getName());
    	Txtfilecreation.filecreation();
    	Txtfilecreation.filereadyforwritingdata();
    	 Driver.logger.info("About to begin executing Suite: " + arg0.getName());
    	 Txtfilecreation.filewrite("About to begin executing Suite: " + arg0.getName());
    	 
        
    }
    
    // This belongs to ISuiteListener and will execute, once the Suite is finished
	@Override
    public void onFinish(ISuite arg0) {
        //Reporter.log("About to end executing Suite " + arg0.getName(), true);
    	try {
    		mail.sendmail();
    	} catch (Exception e) {			
    		e.printStackTrace();
    	}
//        Listener.logger.info("About to end executing Suite " + arg0.getName());
    	 Driver.logger.info("About to end executing Suite: " + arg0.getName());
    	 Txtfilecreation.filewrite("About to end executing Suite: " + arg0.getName());
    	 Txtfilecreation.fileclose();
    	 Common.Regression_Run_Backup();
        
    }

	@Override
	public void onStart(ITestContext arg0) {
		executionstatus = 0;		
		VideoCapture.startVideoCapture();
		Driver.logger.info("About to begin executing Test: " + arg0.getName());
		Txtfilecreation.filewrite("About to begin executing Test: " + arg0.getName());
	}
	
	@Override
	public void onFinish(ITestContext arg0) {		
		
		Driver.logger.info("About to end executing Test: " + arg0.getName());
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
		Driver.logger.info("Test Failed: " + arg0.getName());
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
		Driver.logger.info("Test Skipped: " + arg0.getName());
		Txtfilecreation.filewrite("Test Skipped: " + arg0.getName());
	}

	@Override
	public void onTestStart(ITestResult arg0) {
			
		executionstatus = arg0.getStatus();
		System.out.println("executionstatus : " +executionstatus);
		Driver.logger.info("Test Started: " + arg0.getName());
		Txtfilecreation.filewrite("Test Started: " + arg0.getName());
	}

	@Override
	public void onTestSuccess(ITestResult arg0) {
		
		Driver.logger.info("Test Passed: " + arg0.getName());
		Txtfilecreation.filewrite("Test Passed: " + arg0.getName());
	}
    
//    // This belongs to ITestListener and will execute before starting of Test set/batch 
//    public void onStart(ITestContext arg0) {
//        //Reporter.log("About to begin executing Test " + arg0.getName(), true);
//        Listener.logger.info("About to begin executing Test " + arg0.getName());
//    }
//    
//    // This belongs to ITestListener and will execute, once the Test set/batch is finished
//    public void onFinish(ITestContext arg0) {
//        //Reporter.log("Completed executing test " + arg0.getName(), true);
//        Listener.logger.info("Completed executing test " + arg0.getName());
//    }
//    
//    // This belongs to ITestListener and will execute only when the test is pass
//    public void onTestSuccess(ITestResult arg0) {
//        // This is calling the printTestResults method
//        printTestResults(arg0);
//    }
//    
//    // This belongs to ITestListener and will execute only on the event of fail test
//    public void onTestFailure(ITestResult arg0) {
//        // This is calling the printTestResults method
//        printTestResults(arg0);
//    }
//    
//    // This belongs to ITestListener and will execute before the main test start (@Test)
//    public void onTestStart(ITestResult arg0) {
//        System.out.println("The execution of the main test starts now");
//    }
//    
//    // This belongs to ITestListener and will execute only if any of the main test(@Test) get skipped
//    public void onTestSkipped(ITestResult arg0) {
//        printTestResults(arg0);
//    }
//    
//    // This is just a piece of shit, ignore this
//    public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
//    }
// 
//    
//    // This is the method which will be executed in case of test pass or fail
//    // This will provide the information on the test
//    private void printTestResults(ITestResult result) {
//        Reporter.log("Test Method resides in " + result.getTestClass().getName(), true);
//        if (result.getParameters().length != 0) {
//            String params = null;
//            for (Object parameter : result.getParameters()) {
//                params += parameter.toString() + ",";
//            }
//            Reporter.log("Test Method had the following parameters : " + params, true);
//        }
//        String status = null;
//        switch (result.getStatus()) {
//        case ITestResult.SUCCESS:
//            status = "Pass";
//            break;
//        case ITestResult.FAILURE:
//            status = "Failed";
//            break;
//        case ITestResult.SKIP:
//            status = "Skipped";
//        }
//        Reporter.log("Test Status: " + status, true);
//    }
// 
// 
//    // This belongs to IInvokedMethodListener and will execute before every method including @Before @After @Test
//    public void beforeInvocation(IInvokedMethod arg0, ITestResult arg1) {
//        String textMsg = "About to begin executing following method : " + returnMethodName(arg0.getTestMethod());
//        Reporter.log(textMsg, true);
//    }
//    // This belongs to IInvokedMethodListener and will execute after every method including @Before @After @Test
//    public void afterInvocation(IInvokedMethod arg0, ITestResult arg1) {
//        String textMsg = "Completed executing following method : " + returnMethodName(arg0.getTestMethod());
//        Reporter.log(textMsg, true);
// 
//    }
//    
//    // This will return method names to the calling function
//    private String returnMethodName(ITestNGMethod method) {
//        return method.getRealClass().getSimpleName() + "." + method.getMethodName();
//        
//    }
 
}