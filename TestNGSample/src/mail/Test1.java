package mail;

import org.testng.annotations.Test;

import mail.Driver;
import mail.HomePage;
import mail.SearchPage;;

public class Test1 extends Driver{
	
	@Test 
	public void test() throws Exception
	{
		HomePage home=new HomePage(driver);
		
		home.enterSearch();
		
		SearchPage search=new SearchPage(driver);
		
		search.search();
		
		getscreenshot();
	}

}
