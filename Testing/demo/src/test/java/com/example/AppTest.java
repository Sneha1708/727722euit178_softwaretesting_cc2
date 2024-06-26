package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import java.io.FileInputStream;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AppTest 
{
    public static Logger log = LogManager.getLogger(AppTest.class);
    WebDriver driver;
    ExtentReports report;
    ExtentTest test;

    @BeforeMethod
    public void beforestartoftest() throws Exception
    {
        driver=new ChromeDriver();
        WebDriverManager.chromedriver().setup();
        ExtentSparkReporter cc2report = new ExtentSparkReporter("D:\\CC2report.html");
        report = new ExtentReports();
        report.attachReporter(cc2report);
        driver.get("https://www.barnesandnoble.com/");
        log.info("The webpage has been Successfully opened!");
         PropertyConfigurator.configure("C:\\Users\\Sneha D\\Downloads\\Testing\\demo\\src\\resources\\log4j.properties");

    }
    
    @Test(priority = 0)
    public void TestCase1OfWebsite() throws Exception
    {
        FileInputStream authorname = new FileInputStream("D:\\Login.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(authorname);
        XSSFSheet sheet = workbook.getSheet("Login");
        XSSFRow row = sheet.getRow(1);
        String name = row.getCell(0).getStringCellValue(); 
        driver.findElement(By.xpath("//*[@id=\'rhf_header_element\']/nav/div/div[3]/form/div/div[1]/a")).click();
        driver.findElement(By.xpath("//*[@id='rhf_header_element']/nav/div/div[3]/form/div/div[1]/div/a[2]")).click();
        driver.findElement(By.xpath("//*[@id='rhf_header_element']/nav/div/div[3]/form/div/div[2]/div/input[1]")).sendKeys(name);
        // test.log(Status.PASS,"passed");
        driver.findElement(By.xpath("//*[@id='rhf_header_element']/nav/div/div[3]/form/div/span/button")).click();
        WebElement verify_testcase=driver.findElement(By.xpath("//*[@id='searchGrid']/div/section[1]/section[1]/div/div[1]/div[1]/h1"));
        log.info("TestCase 1 has been passed !");
    }


    @Test(priority = 1)
    public void TestCase2OfWebsite() throws InterruptedException {
        WebElement click_audiobooks = driver.findElement(By.linkText("Audiobooks"));
        Thread.sleep(2000);
        Actions action = new Actions(driver);
        action.moveToElement(click_audiobooks).perform();
        driver.findElement(By.xpath("//*[@id='navbarSupportedContent']/div/ul/li[5]/div/div/div[1]/div/div[2]/div[1]/dd/a[1]")).click();
        Thread.sleep(2000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,300)");
        driver.findElement(By.linkText("Funny Story")).click();
        Thread.sleep(2000);
        js.executeScript("window.scrollBy(0,500)");
        driver.findElement(By.xpath("//*[@id='otherAvailFormats']/div/div")).click();
        Thread.sleep(2000);
        js.executeScript("window.scrollBy(0,700)");
         driver.findElement(By.xpath("//*[@id='prodInfoContainer']/div[3]/form[1]/input[11]")).click();
        Thread.sleep(2000);
        WebElement cart = driver.findElement(By.xpath("//*[@id='add-to-bag-main']/div[1]"));
        String msg = cart.getText();
        if (msg.equals("Item Successfully Added To Your cart")) log.info("Item has been added to cart");
        else
            log.error("An Error Has Occured in adding the item to the cart!");
        Thread.sleep(2000);
        log.info("Testcase2 has been passed");
    }


    @Test(priority = 2)
    public void TestCase3OfWebsite() throws Exception{
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1500)");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id='footer']/div/dd/div/div/div[1]/div/a[5]/span")).click();
        Thread.sleep(2000);
        js.executeScript("windows.scrollBy(0,400)");
        driver.findElement(By.xpath("//*[@id='rewards-modal-link']")).click();
        WebElement verify_testcase3=driver.findElement(By.xpath("//*[@id=\"dialog-title\"]"));
        Assert.assertTrue(verify_testcase3.getText().contains("Sign in or Create an Account"), "Sign in first!!");
        log.info("TestCase 3  has been passed ");
    }


    @AfterMethod
    public void end() throws Exception{
        log.info("ExtentReport has been generated!");
        log.info("The given website has been tested with the given requirements");
        report.flush();
 
        driver.quit();
    }
}
