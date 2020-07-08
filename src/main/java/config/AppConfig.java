package config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.*;

public class AppConfig {

    public static String adBlockPath = "C:/Users/mmich/AppData/Local/Google/Chrome/User Data/Default/Extensions/cfhdojbkjhnklbpkdaibdccddilifddb/3.9_0";
    public static String chromeDriverPath = "src/main/java/utils/chromedriver.exe";
    public static WebDriver driver;

    public static WebDriver getDriver() {
        return driver;
    }

    public static void startApplication() {
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("load-extension=" + adBlockPath);
        options.addArguments("--disable-gpu", "--headless", "window-size=1200,1100", "-incognito", "--disable-popup-blocking", "no-sandbox");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new ChromeDriver(options);
        //closeAdBlockTab();
        ResourceBundle bundle = ResourceBundle.getBundle("project");
        driver.get(bundle.getString("base.url"));
        driver.manage().window().maximize();
    }

    public static void closeAdBlockTab() {
        Set<String> handlesSet = driver.getWindowHandles();
        List<String> handlesList = new ArrayList<>(handlesSet);
        driver.switchTo().window(handlesList.get(1));
        driver.close();
        driver.switchTo().window(handlesList.get(0));
    }

}
