package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.ExcelReader;
import utilities.ExtentManager;
import utilities.TestUtil;

public class TestBase  {
	
	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger(TestBase.class.getName());
	public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\testdata.xlsx");
	public static WebDriverWait wait;
	public ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;
	public static String browser;
	
	@BeforeSuite
		public void setUp() throws IOException{
		
		PropertyConfigurator.configure(".\\src\\test\\resources\\properties\\log4j.properties");
		
		if(driver == null) {
			
			try {
				fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			config.load(fis);
			log.info("Config file loaded!");
			
			try {
				fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			OR.load(fis);
			log.info("Config file loaded!");
		
	}
		//Jenkins Browser filter configuration
		if(System.getenv("browser")!=null && !System.getenv("browser").isEmpty()) {
			browser = System.getenv("browser");
		} else {
			browser = config.getProperty("browser");
		}
		
		config.setProperty("browser", browser);
			
			
			
			
		if(config.getProperty("browser").equals("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} 
		else if (config.getProperty("browser").equals("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			log.info("Chrome launched");
	}
		else if (config.getProperty("browser").equals("ie")) {
		WebDriverManager.iedriver().setup();
		driver = new InternetExplorerDriver();
	}
		
		driver.get(config.getProperty("testsiteurl"));
		log.info("Navigated to " + config.getProperty("testsiteurl"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(config.getProperty("implicit.wait"))));
		wait = new WebDriverWait(driver,Duration.ofSeconds(5));
	
	}
		public void click(String locator) {
			
			if(locator.endsWith("_CSS")) {
				driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
			} else if (locator.endsWith("_XPATH")) {
				driver.findElement(By.xpath(OR.getProperty(locator))).click();
			} else if (locator.endsWith("_ID")) {
				driver.findElement(By.id(OR.getProperty(locator))).click();
			}
			
			log.info("Clicking on an Element : "+locator);
			test.log(Status.INFO, "Clicking on: " + locator);
		}
		
		public void type(String locator, String value) {
			
			if(locator.endsWith("_CSS")) {
				driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
			} else if (locator.endsWith("_XPATH")) {
				driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
			} else if (locator.endsWith("_ID")) {
				driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
			}
			
			log.info("Typing in an Element : "+locator+" entered value as : "+value);
			test.log(Status.INFO, "Typing in: " + locator+" entered value: "+value);
		}
	
		
		static WebElement dropdown;
		
		public void select(String locator, String value) {
			
			if(locator.endsWith("_CSS")) {
				dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
			} else if (locator.endsWith("_XPATH")) {
				dropdown =driver.findElement(By.xpath(OR.getProperty(locator)));
			} else if (locator.endsWith("_ID")) {
				dropdown =	driver.findElement(By.id(OR.getProperty(locator)));
			}
			
			Select select = new Select(dropdown);
			select.selectByVisibleText(value);
			log.info("Selecting from an element : "+locator+" value as : "+value);
			test.log(Status.INFO, "Selecting the: " + value + " from locator: " + locator);
		}
		
	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
			
		} catch(NoSuchElementException e) {
			return false;
		}
	}
	
	public static void verifyEquals(String expected, String actual) throws IOException {
		
		try {
			Assert.assertEquals(actual, expected);
		}catch(Throwable t) {
			TestUtil.captureScreenshot();
			//ReportNG
			Reporter.log("<br>"+"Verification failure: "+t.getMessage()+"<br>");
	    	Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+">Screenshot</a>");	
	    	Reporter.log("<br>");
	    	
	    	//ExtentReport
	        test.log(Status.FAIL, "Verification failed with Exception: "+t.getMessage());	 
	        test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(TestUtil.screenshotName).build());
	        
		}
		
	}
	

	@AfterSuite   
	public void tearDown(){
		if(driver!=null) {
		driver.quit();
		
	}
		log.info("Execution completed");
	}
	
	
	}


