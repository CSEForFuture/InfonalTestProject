package infonalTestProjectPackage;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

// This class is the main test class. This class tests the required information with the help
// of the methods of FacebookTestClass and N11TestClass and Log4j methods are used to log the test.
public class InfonalTestProjectClass {
	private WebDriver driver;
	private WebDriver driver2;
	protected static Logger log = Logger.getLogger("Infonal Main Test Log");
	
		@Before
		public  void setUp() throws Exception {
			DOMConfigurator.configure("log4j.xml");
			driver = new FirefoxDriver();
			log.info("New driver instantiated");
		}
		
		@Test
		public void testMethod() throws Exception {
			
		    FacebookTestClass fTCObj = new FacebookTestClass(driver);
		    fTCObj.facebookTestMethod();
		    driver2 = new FirefoxDriver();
		    log.info("Another driver instantiated");
		    N11TestClass nTCObj = new N11TestClass(driver2);
		    nTCObj.fromMainPageToAuthorsMethod();
		    nTCObj.testAuthorPagesMethod();

		}

		@After
		public void tearDown() throws Exception {
			log.info("Test completed...");
			driver2.quit();
			log.info("Terminating the driver");
		}
}
