package testcases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.SkipException;
import org.testng.annotations.Test;
import base.TestBase;
import utilities.TestUtil;

public class AddCustomerTest extends TestBase {
	
	@Test(dataProviderClass=TestUtil.class,dataProvider="dp")
	public void addCustomerTest(Hashtable<String, String> data) throws InterruptedException {
		
		if(!data.get("Runmode").equals("Y")) {
			throw new SkipException("Skipping the test case as the Run mode is No");
		}
		
		
		
		click("addCustbtn_CSS");
		type("firstName_CSS", data.get("firstName"));
		type("lastName_CSS", data.get("lastName"));
		type("postCode_CSS", data.get("postCode"));
		click("addbtn_CSS");
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		alert.getText().contains(data.get("alerttext"));
		alert.accept();
		
	}

	
}
