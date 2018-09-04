package mail;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import org.testng.annotations.AfterGroups;
import org.testng.annotations.Test;

import mail.Driver;
import mail.HomePage;
import mail.SearchPage;;

public class Test1 extends Driver {
	@Functionality(module = "login", story = "1234")
	@Test(groups = { "reg" })
	void test() throws Exception {
		HomePage home = new HomePage(driver);

		home.enterSearch();

		// SearchPage search=new SearchPage(driver);
		//
		// search.search();

		getscreenshot();
	}

	
}
