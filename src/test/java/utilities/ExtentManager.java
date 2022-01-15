package utilities;

import java.io.File;
import java.io.IOException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager   {

	public static ExtentSparkReporter htmlReporter;
	public static ExtentReports extent;

	
	public static ExtentReports getInstance(){
			
		if(extent==null) {
			
			extent = new ExtentReports();
			htmlReporter = new ExtentSparkReporter(System.getProperty("user.dir")+"\\target\\surefire-reports\\html\\extent.html");
			try {
				htmlReporter.loadXMLConfig(new File (System.getProperty("user.dir")+"\\src\\test\\resources\\extentconfig\\ReportsConfig.xml"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			extent.attachReporter(htmlReporter);

		}
		return extent;
	}
	
}
