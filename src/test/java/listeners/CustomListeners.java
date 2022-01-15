package listeners;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import base.TestBase;
import utilities.MonitoringMail;
import utilities.TestConfig;
import utilities.TestUtil;

public class CustomListeners extends TestBase implements ITestListener,ISuiteListener {
	
	public String messageBody; 
	 	
	    public void onFinish(ITestContext arg0) {	
	    	
	    	MonitoringMail mail = new MonitoringMail();
			
			try {
				messageBody = ("http://"+InetAddress.getLocalHost().getHostAddress()+":8080/job/DataDrivenProject/Extent_20Reports");
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(messageBody);
			
			try {
				mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, TestConfig.messageBody, TestConfig.attachmentPath, TestConfig.attachmentName);
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
	        		
	    }		

	    public void onStart(ITestContext arg0) {					
	    					
	        		
	    }		

	    		
	    public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {					
	        // TODO Auto-generated method stub				
	        		
	    }		

	  	
	    public void onTestFailure(ITestResult arg0) {					
	      
	    	System.setProperty("org.uncommons.reportng.escape-output", "false");
	    	try {
				TestUtil.captureScreenshot();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
	    	Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+">Screenshot</a>");	
	    	Reporter.log("<br>");
	    
	        test.log(Status.FAIL, arg0.getName().toUpperCase()+" FAIL with exception: "+ arg0.getThrowable());	 
	        test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(TestUtil.screenshotName).build());
	        rep.flush();	
	    }		

	   	
	    public void onTestSkipped(ITestResult arg0) {					
	        test.log(Status.SKIP, arg0.getName()+" Skipped the test as the Run mode is No.");			
	        rep.flush();		
	    }		

	  		
	    public void onTestStart(ITestResult arg0) {					
	    	test = rep.createTest(arg0.getName());	
	    	//runmodes - Y
	    	if(!TestUtil.isTestRunnable(arg0.getName(), excel)) {
	    		throw new SkipException("Skipping the test: "+arg0.getName().toUpperCase()+"  as the Run mode is No");
	    	}
	        		
	    }		

	    		
	    public void onTestSuccess(ITestResult arg0) {					
	        test.log(Status.PASS, arg0.getName().toUpperCase()+" PASS");		
	        rep.flush();	
	    }		

}
