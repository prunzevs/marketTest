package ru.yandex;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;


public class BaseTests {

    protected WebDriver chromeDriver;

    @BeforeEach
    public void before(){
        System.setProperty("webdriver.chrome.driver",System.getenv("CHROME_DRIVER"));
        //System.setProperty("webdriver.chrome.driver","C:\\tools\\chromedriver.exe");

        chromeDriver = new ChromeDriver();
        chromeDriver.manage().window().maximize();
        chromeDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        chromeDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        //chromeDriver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);

    }

    @AfterEach
    public void closeGoogleTest(){
        chromeDriver.quit();
    }
}
