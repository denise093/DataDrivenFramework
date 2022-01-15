package testcases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;


import base.TestBase;

public class BankManagerLoginTest extends TestBase {
	
	@Test
	public void bankManagerLoginTest() throws InterruptedException, IOException {
		
		
		log.info("Inside Login test");
		click("bmlBtn_CSS");
		Thread.sleep(3000);
		
		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustbtn_CSS"))), "Login not successfull");
		log.info("Login successfully executed");
		Reporter.log("Login successfully executed");
		
		
	}
	
}
