package Frameworkpractice.tests;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Frameworkpractice.pageobjects.CartPage;
import Frameworkpractice.pageobjects.CheckoutPage;
import Frameworkpractice.pageobjects.ConfirmationPage;
import Frameworkpractice.pageobjects.OrderPage;
import Frameworkpractice.pageobjects.ProductCatalogue;
import Frameworkpractice.pageobjects.landingPage;
import SeleniumComponents.TestComponents.BaseTest;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SubmitOrderTest extends BaseTest{
	 String ProductName = "ZARA COAT 3";

		@Test(dataProvider = "getData",groups= {"purchase"})
				public void submitOrder(HashMap<String,String> input) throws InterruptedException, IOException {
					
						ProductCatalogue ProductCatalogue =	landingPage.loginApplication(input.get("email"), input.get("password"));
						
						List<WebElement> products = ProductCatalogue.getProductList();
						ProductCatalogue.addProductToCart(input.get("productName"));
						CartPage cartPage = ProductCatalogue.goToCartPage();
						
						Boolean match = cartPage.VerifyProductDisplay(input.get("productName"));
						Assert.assertTrue(match);
						
						CheckoutPage CheckoutPage = cartPage.goToCheckout();
						CheckoutPage.selectCountry("india");
						ConfirmationPage ConfirmationPage =  CheckoutPage.submitOrder();
					
						String confirmMessage = ConfirmationPage.getConfirmationMessage();
						Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
						
						
						
						//	driver.quit();
					
				}
		       
		//verify Zara coat 3 is displaying in orders page
		@Test(dependsOnMethods= {"submitOrder"})
		public void OrderHistoryTest()
		{
			//"ZARA COAT 3";
			ProductCatalogue productCatalogue = landingPage.loginApplication("tejuArya@gmail.com", "teju_RP@411");
			OrderPage ordersPage = productCatalogue.goToOrdersPage();
			Assert.assertTrue(ordersPage.VerifyOrderDisplay(ProductName));
			
		}
		
		
		@DataProvider
		public Object[][] getData() throws IOException
		{
			 
			
			//using object to pass the data
			//return new Object[][]  {{"tejuArya@gmail.com","teju_RP@411","ZARA COAT 3"}, {"1ms18is415@gmail.com","teju_RP@411","ADIDAS ORIGINAL"} };
			
			//using hashmap to pass the data
//			HashMap<Object,Object> map = new HashMap<Object,Object>();
//			map.put("email", "tejuArya@gmail.com");
//			map.put("password", "teju_RP@411");
//			map.put("productName", "ZARA COAT 3");
//			
//			HashMap<Object,Object> map1 = new HashMap<Object,Object>();
//			map1.put("email", "1ms18is415@gmail.com");
//			map1.put("password", "teju_RP@411");
//			map1.put("productName", "ADIDAS ORIGINAL");
//			return new Object[][] {{map} , {map1} };
			
			//using json to pass the data 
			List<HashMap<Object, Object>> data = getJsonDataToMap(System.getProperty("user.dir")+"//src//test//java//SeleniumFrameWork//data//PurchaseOrder.json");
			return new Object[][]  {{data.get(0)}, {data.get(1) } };
			
			
			
		
		
		}
	}

