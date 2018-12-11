package mail;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

@Functionality(module= {"login1"},story= {"12341"})
public class Driver {
	
	 protected static WebDriver driver;
	
	public void loadBrowser(String browser)
	{
		if(browser.equals("Firefox"))
		{
			System.setProperty("webdriver.gecko.driver", "./Drivers/geckodriver.exe");
			
			driver=new FirefoxDriver();
		}
		if(browser.equals("Chrome")
				){
			System.setProperty("webdriver.chrome.driver", "./Drivers/chromedriver.exe");
			driver=new ChromeDriver();
		}
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
	}

	@BeforeClass
	public void loadData(){
		
	}
	
	@BeforeMethod
	public void loadBrowser(){
		loadBrowser("Chrome");
		driver.get("https://www.redbus.in");
	}
	
	@AfterMethod
	public void quit(){
		driver.quit();
		
	}
	public static void getscreenshot() throws Exception 
    {
            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
         //The below method will save the screen shot in d drive with name "screenshot.png"
            FileUtils.copyFile(scrFile, new File("screenshot.png"));
            
            Date d = new Date();
            Timestamp t = new Timestamp(d.getTime());
            String timeStamp = t.toString();
            timeStamp = timeStamp.replace(' ', '_');
            timeStamp = timeStamp.replace(':', '_');

            FileUtils.copyFile(scrFile, new File("383184"+ "_" + timeStamp + ".jpg"));
           
    }
}
