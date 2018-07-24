package mail;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class SearchPage {
	
	WebDriver driver;
	

	public SearchPage(WebDriver driver) {
		this.driver=driver;
	}


	public void search() throws InterruptedException{
		Thread.sleep(5000);
		String str= driver.findElement(By.xpath("(//div[@class='clearfix main-body bus-tupple'])[2]")).getText();
		System.out.println(str);
		driver.findElement(By.xpath("(//button[text()='View Seats'])[2]")).click();
		
		Thread.sleep(5000);
		/*Actions action=new Actions(driver);
		action.moveToElement(driver.findElement(By.xpath("//canvas[@height='380']")));
		action.build().perform();*/
		driver.findElement(By.xpath(".//*[@id='buses_viewonward']/div/ul/li[1]/div[1]/div/div[1]/div[7]/div[2]/button")).click();
        

        WebElement canvas=driver.findElement(By.xpath(".//*[@id='buses_viewonward']/div/ul/li[1]/div[2]/div[1]/div[1]/div/div/div/div[3]/div/div[2]/canvas"));
        new Actions(driver).moveToElement(canvas, 47, 56).click().perform();
  String seat_and_fare_details=driver.findElement(By.id("abs")).getText();
  
  System.out.println(seat_and_fare_details);

		Thread.sleep(5000);
	}
}
