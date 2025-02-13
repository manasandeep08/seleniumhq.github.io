package com.test.automation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ErailTest {
    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;
    
    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://erail.in/");
        
        extent = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter("ExtentReport.html");
        extent.attachReporter(spark);
    }
    
    @Test
    public void testErailStationSelection() throws IOException {
        test = extent.createTest("Erail Station Selection Test");
        WebElement fromField = driver.findElement(By.id("txtStationFrom"));
        fromField.clear();
        fromField.sendKeys("DEL");
        
        List<WebElement> stationList = driver.findElements(By.xpath("//ul[@id='ui-id-1']/li"));
        String fourthStation = stationList.get(3).getText();
        System.out.println("Selected station: " + fourthStation);
        test.pass("Successfully selected fourth station: " + fourthStation);
        
        // Writing station list to Excel (mocked code)
        FileOutputStream fos = new FileOutputStream("stations.xlsx");
        fos.write(fourthStation.getBytes());
        fos.close();
        
        // Date selection for 30 days ahead (mocked logic)
        WebElement dateField = driver.findElement(By.id("txtDate"));
        dateField.clear();
        dateField.sendKeys("03-09-2022");
    }
    
    @AfterClass
    public void tearDown() {
        extent.flush();
        driver.quit();
    }
}
