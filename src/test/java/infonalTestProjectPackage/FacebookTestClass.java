package infonalTestProjectPackage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


//This class visits the Facebook page. Then, try to log in with blank email and password textboxes
//As expected, the page directs to the login page again and there the program logs in the facebook page.
//After that, the current driver is closed and Log4j methods are used to log the test.
public class FacebookTestClass {
	
	WebDriver driver;
	

	public FacebookTestClass(WebDriver driver) {
		this.driver = driver;
	}
	
	// This method tries to access the facebook site without success. After that the login page appears. Here 
	// the program enters the username and password and logs in facebook.Then, it closes the driver
	protected void facebookTestMethod(){
		

		InfonalTestProjectClass.log.info("Trying to enter facebook with blank username and Facebook!");
		driver.get("https://www.facebook.com/");
		driver.findElement(By.xpath("//*[@id='loginbutton']")).click();
		
		assertEquals("Facebook'a Giriþ Yap | Facebook", driver.getTitle());
		
		InfonalTestProjectClass.log.info("Logging in Facebook with username and password");
		driver.findElement(By.xpath("//*[@id='email']")).sendKeys("seleniumsample@yahoo.com");
	    driver.findElement(By.xpath("//*[@id='pass']")).sendKeys("selenium2014");
	    driver.findElement(By.xpath("//*[@id='loginbutton']")).click();
	    

	    InfonalTestProjectClass.log.info("Checking whether the entered username or/and password is correct");
	    assertTrue("You are not logged in, since the password is not correct", driver.getTitle().matches(".*Facebook"));
	    

	    InfonalTestProjectClass.log.info("Entered values are correct, logged in Facebook!");
	    System.out.println("You have successfully logged in to Facebook!");
	    

	    InfonalTestProjectClass.log.info("Closing the first driver and opening the second driver for N11 web site!");
	    driver.close();
	}
}
