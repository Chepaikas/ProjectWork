package tools;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitTools {
    private int waiterTimeout = Integer.parseInt(System.getProperty("wait.timeout","10"));
    private WebDriver driver;

    public WaitTools(WebDriver driver){
        this.driver = driver;
    }
    public boolean waitForCondition(ExpectedCondition condition) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(waiterTimeout)).until(condition);
            return true;
        }   catch (TimeoutException ignored){
            return false;
        }
    }
    public boolean waitElementVisible(WebElement element) {
        return waitForCondition(ExpectedConditions.visibilityOf(element));
    }

    public void until(ExpectedCondition<Boolean> condition) {
        waitForCondition(condition);
    }
}