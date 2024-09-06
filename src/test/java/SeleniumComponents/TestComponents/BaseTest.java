package SeleniumComponents.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import Frameworkpractice.pageobjects.landingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	
	public WebDriver driver;
	public landingPage landingPage;
	
	public WebDriver initializerDriver() throws IOException
	{
		//Properties class
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//main//java//SeleniumFrameWork//resources//Golbaldata.properties");
		prop.load(fis);
		String browserName = System.getProperty("browser")!=null ? System.getProperty("browser") : prop.getProperty("browser");
		//String browserName = prop.getProperty("browser");
		
		
		if(browserName.contains("chrome")) {
		
		ChromeOptions options = new ChromeOptions();
			
		WebDriverManager.chromedriver().setup();
		if(browserName.contains("headless")) {
			options.addArguments("headless");
		}
		
		driver = new ChromeDriver(options);
		driver.manage().window().setSize(new Dimension(1440,900)); // full screen 
		
		}
		else if(browserName.equals("FireFox"))
		{
			driver = new FirefoxDriver();
		}
		else if(browserName.equals("edge"))
		{
			//Edge
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));			
		driver.manage().window().maximize();
		
		return driver;

		
	}
	
	
	public List<HashMap<Object, Object>> getJsonDataToMap(String filePath) throws IOException {
		//read json to string
		String jsonContent = 	FileUtils.readFileToString(new File(filePath), 
				StandardCharsets.UTF_8);
		
		//String to HashMap- Jackson Datbind
		
		ObjectMapper mapper = new ObjectMapper();
		  List<HashMap<Object, Object>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<Object, Object>>>() {
	      });
		  return data;
		
		
	}
	
	
	public String getScreenshot(String testCaseName,WebDriver Driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File filepath = new File(System.getProperty("user.dir") +"//reports//"+testCaseName+".png");
		FileUtils.copyFile(source, filepath);
		return System.getProperty("user.dir") +"//reports//"+testCaseName+".png";
	}
	
	
	@BeforeMethod(alwaysRun=true)
	public landingPage launchApplication() throws IOException
	{
		driver = initializerDriver();
		landingPage = new landingPage(driver);
		landingPage.goTo();
		return landingPage;
	}

	@AfterMethod(alwaysRun=true)
	public void tearDown() {
		driver.close();
	}
	
	
	
	
}
