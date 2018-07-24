package mail;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;



public class HomePage {
	
	WebDriver driver;
	
	public By From=By.id("src");
	public By To=By.id("dest");
	public By OnwardDate=By.id("onward_cal");
	public By Search=By.id("search_btn");
	
	public HomePage(WebDriver driver) {
	this.driver= driver;
	}

	public void enterSearch() throws InterruptedException, FileNotFoundException, IOException{
		Thread.sleep(5000);
		 //String objFromCity = Data.getInputValue("From");
		String objFromCity = "Chennai";
		driver.findElement(From).sendKeys(objFromCity);
		driver.findElement(By.xpath("//ul[@class='autoFill']/li[text()='Chennai']")).click();
		Thread.sleep(1000);
		driver.findElement(To).sendKeys("Madurai");
		driver.findElement(By.xpath("//ul[@class='autoFill']/li[text()='Madurai']")).click();
		//driver.findElement(OnwardDate).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("(//td[text()='30'])[2]")).click();
		Thread.sleep(1000);
		driver.findElement(Search).click();
	}
	
}
