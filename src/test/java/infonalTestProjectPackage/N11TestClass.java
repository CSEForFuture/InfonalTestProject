package infonalTestProjectPackage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;


//This class visits www.n11.com and from the main page tries to visits the author page as followings:
//main page -> kitap, müzik, film, oyun -> kitap -> yazarlar. Also here an advertisement
//that pops up on 'kitap' page is closed before accessing the 'yazarlar' page. (fromMainPagetoAuthorMethod)
//After the program visits the required page, the program checks whether the author links exceeds 80 or not!
//Also it checks (if there is a second page of authors) the last element of the first page and the first
//element of the second page are the same or not. If there is a problem, test fails.(testAuthorPagesMethod)
//And Log4j methods are used to log the test.
public class N11TestClass {
	
	WebDriver driver;
	
	public N11TestClass(WebDriver driver){
		this.driver = driver;
	}
	
	
	//This method accesses www.n11.com website. From here, tries to reach to 'Yazarlar' page via Kitap, Müzik,
	//Film, Oyun dropdown menu and from there clicks the 'kitap' link. Then, clicks the 'Yazarlar' page.
	// Added some wait time, since sometimes the pages load a bit slowly.
	protected void fromMainPageToAuthorsMethod() throws Exception{
		
		
		InfonalTestProjectClass.log.info("Visiting the www.n11.com website");
		driver.get("http://www.n11.com/");
		
		InfonalTestProjectClass.log.info("Trying to access 'Kitap' link through dropdown menu");
	    WebElement element = driver.findElement(By.linkText("Kitap, Müzik, Film, Oyun"));
        Actions action = new Actions(driver);
        action.moveToElement(element).build().perform();
        driver.findElement(By.linkText("Kitap")).click();
        InfonalTestProjectClass.log.info("Accessed the link 'Kitap' through 'Kitap, Müzik, Film, Oyun' dropdown menu");
        
        InfonalTestProjectClass.log.info("Waiting imlicitly since it can be slower to reach the site in some systems.");
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        
        InfonalTestProjectClass.log.info("Closing the pop-up advertisement!");
	    driver.findElement(By.xpath("//*[@title='Close']")).click();
	    
	    InfonalTestProjectClass.log.info("Waiting for the page to load the 'book' page");
        Thread.sleep(10000);
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        
        InfonalTestProjectClass.log.info("Accessing the 'Yazarlar' page through relevant link"); 
        driver.findElement(By.linkText("Yazarlar")).click();
        
        InfonalTestProjectClass.log.info("Waiting implicitly for the page to load the 'book' page");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	
	// This method checks the authors name in the relevant page whether there is an author name which is not started
	// with appropriate letter. And also, this method controls if the last element of the first page and the first element
	// of the second page are the same or not, If so, test fails...
	protected void testAuthorPagesMethod(){
		
		char[] alphabetString = {'A','B','C','Ç','D','E','F','G','H','I','Ý','J','K','L','M','N','O','Ö','P','Q','R','S','Þ','T','U','Ü','V','W','X','Y','Z'};
		
        for(char alpStr : alphabetString)
        {
        	InfonalTestProjectClass.log.info("Accessing the authors names with the first letter '"+alpStr+"'");
        	String alpString = Character.toString(alpStr);
        	
        	String lowerCaseAlpStr = alpString.toLowerCase();
        	char lowerCaseAlpChar = lowerCaseAlpStr.charAt(0);
        	
        	driver.findElement(By.xpath("//span[contains(@class, 'alphabetSearch') and normalize-space()='" +alpString + "']")).click();
	        WebElement allDivElements = driver.findElement(By.id("authorsList"));
	        List<WebElement> authorLinks = allDivElements.findElements(By.tagName("a"));
	        
	        InfonalTestProjectClass.log.info("Checking if the page has 80 or less author name in the list");
	        assertTrue("There are more than 80 elements in the current page!!",authorLinks.size()<=80);
	        InfonalTestProjectClass.log.info("There are 80 or less author name in the list!");
	        
	        List<String> nameOfAuthorsList = new LinkedList<String>();
	        
	        InfonalTestProjectClass.log.info("Checking if there is an author name without first letter '"+alpStr+"'");
	        for(WebElement e : authorLinks){
	        	nameOfAuthorsList.add(e.getText());
	        	assertTrue("There is an item in the list whose first letter is not " + alpStr + "!!", e.getText().charAt(0) == alpStr || e.getText().charAt(0) == lowerCaseAlpChar);
	        }
	        
	        System.out.println("There is no unrelated item in the author link list!");
	        InfonalTestProjectClass.log.info("There is no author name in the list without first letter '"+alpStr+"'");
	        
	        InfonalTestProjectClass.log.info("Checking if there is another authors page with the first letter '"+alpStr+"'");
	        if(driver.findElements(By.linkText("2")).size()>0){
	        	
	        	InfonalTestProjectClass.log.info("There is another page for the authors page with the first letter '"+alpStr+"'");
	        	
		        driver.findElement(By.linkText("2")).click();
		        WebElement allDivElements2 = driver.findElement(By.id("authorsList"));
		        List<WebElement> authorLinks2 = allDivElements2.findElements(By.tagName("a"));
		        
		        InfonalTestProjectClass.log.info("Checking if there is an author name without first letter '"+alpStr+"'");
		        List<String> nameOfAuthorsList2 = new LinkedList<String>();
		        for(WebElement e : authorLinks2){
		        	nameOfAuthorsList2.add(e.getText());
		        	assertTrue("There is an item in the list whose first letter is not" + alpStr + "!!", e.getText().charAt(0) == alpStr || e.getText().charAt(0) == lowerCaseAlpChar);
		        }
		        InfonalTestProjectClass.log.info("There is no author name in the list without the first letter '"+alpStr+"'");
		        
		        InfonalTestProjectClass.log.info("Checking if the last author name of the previous page and the first author name of the current page is the same");
		        assertFalse("The name of the last author of the previous page and the first author of the current page is same."
		        			,nameOfAuthorsList2.get(0).equals(nameOfAuthorsList.get(nameOfAuthorsList.size()-1)) );
		        InfonalTestProjectClass.log.info("The last author name of the previous page and the first one of the current page are not the same!");
	        }
        }
	}
}
