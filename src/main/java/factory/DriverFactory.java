package factory;

import exeption.BrowserNotSupportedExeption;
import factory.settings.IDriverSettings;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;

public class DriverFactory implements IDriverSettings{
    private String browserName = System.getProperty("browser.name");
    private IDriverSettings driverSettings;

    public DriverFactory(IDriverSettings driverSettings) {
        this.driverSettings = driverSettings;
    }

    public WebDriver create() {
        browserName = browserName.toLowerCase();

        switch (browserName){
            case "chrome": {
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                return new ChromeDriver((ChromeOptions) driverSettings.settings());
            }
            case"firefox":{
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--start-maximized");
                return new FirefoxDriver((FirefoxOptions) driverSettings.settings());
            }
        }
        throw new BrowserNotSupportedExeption(browserName);
    }

    @Override
    public AbstractDriverOptions settings() {
        return null;
    }
}
