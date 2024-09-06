package Frameworkpractice.tests;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.sun.net.httpserver.Authenticator.Retry;

import Frameworkpractice.pageobjects.CartPage;
import Frameworkpractice.pageobjects.CheckoutPage;
import Frameworkpractice.pageobjects.ConfirmationPage;
import Frameworkpractice.pageobjects.ProductCatalogue;
import Frameworkpractice.pageobjects.landingPage;
import SeleniumComponents.TestComponents.BaseTest;
import io.github.bonigarcia.wdm.WebDriverManager;

public class ErrorValidations extends BaseTest{

		@Test(groups= {"ErrorHandling"},retryAnalyzer = SeleniumComponents.TestComponents.Retry.class)
		public void LoginErrorValidation() throws InterruptedException, IOException {
		// String ProductName = "ZARA COAT 3";
		ProductCatalogue ProductCatalogue =	landingPage.loginApplication("1ms18is41@gmail.com", "teju_RP@411");
		Assert.assertEquals("Incorrect email password.", landingPage.getErrorMessage());
			
		}
		
		
		@Test 
		public void ProductErrorValidation() throws InterruptedException, IOException {
			 String ProductName = "ZARA COAT 3";
				ProductCatalogue ProductCatalogue =	landingPage.loginApplication("tejuArya@gmail.com", "teju_RP@411");
				
				List<WebElement> products = ProductCatalogue.getProductList();
				ProductCatalogue.addProductToCart(ProductName);
				CartPage cartPage = ProductCatalogue.goToCartPage();
				
				Boolean match = cartPage.VerifyProductDisplay("ZARA COAT 33");
				Assert.assertFalse(match);
				//	driver.quit();
			
		}

       
	}

