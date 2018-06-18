package fluffybunny.malbunny.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class UtilityBunny {
	
	public static final int[] popGuide = {50,100,200,500,1000,2000,3000,4000,5000,6000,7000,8000,9000,10000,11000};
	public static final String[] popGuideLabel = {"1-50","51-100","101-200","201-500","501-1000","1001-2000",
			"2001-3000","3001-4000","4001-5000","5001-6000","6001-7000","7001-8000","8001-9000","9001-10000","10001+"};
	public static final int[] srcGuide = {50,100,200,500,1000,2000,3000,4000,5000,6000,7000,8000,9000,10000,11000};
	//public static final String[] scrGuideStr = {50,100,200,500,1000,2000,3000,4000,5000,6000,7000,8000,9000,10000,11000};
	
	public static ChromeDriver newChrome() {
		try {
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("chromedriver.exe");
			Files.copy(is, new File("/tmp/chromedriver.exe").toPath());
			System.setProperty("webdriver.chrome.driver","/tmp/chromedriver.exe");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ChromeOptions options = new ChromeOptions();
		//options.addArguments("load-extension=C:\\Users\\firco\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Extensions\\cjpalhdlnbpafiamejdnhcphjbkeiagm\\1.16.4_0");
		//DesiredCapabilities capabilities = new DesiredCapabilities();
		//capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		ChromeDriver driver = new ChromeDriver(options);
		driver.get("https://myanimelist.net/login.php");

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		driver.findElement(By.xpath("/html/body/div[8]/div/div[2]/div/button")).click();
		By userinput = By.xpath("//*[@id=\'loginUserName\']");
		By passpass = By.xpath("//*[@id=\'login-password\']");

		driver.findElement(userinput).sendKeys("HophopBunny");
		driver.findElement(passpass).sendKeys("J96B8LVnAH6M");

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		driver.findElement(By.xpath("//*[@id=\"dialog\"]/tbody/tr/td/form/div/p[6]/input")).click();
		String ret = "";
		return driver;
	}
	
	public static String fixString(String str) {
		return str.replace("&#9734;", "☆").replace("&#9837;", "♭").replace("&#9678;","◎").replace("&#9733;","★").replace("&#275;","ē").replace("&#9834", "♪");
	}

	public static int getArrayBucket(int[] popGuide, int pop) {
		for(int i = 0; i < popGuide.length; i++) {
			if(pop < popGuide[i]) {
				return i;
			}
		}
		return popGuide.length-1;
	}
	
	public static String ranked(int i) {
		System.out.println(i);
		if(i > 0) {
			return "true";
		} else {
			return "false";
		}
	}

}
