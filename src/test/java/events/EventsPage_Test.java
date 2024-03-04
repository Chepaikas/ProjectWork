package events;

import data.sorted.EventTypeData;
import exeption.BrowserNotSupportedExeption;
import factory.DriverFactory;
import factory.settings.ChromeDriverSettings;
import factory.settings.FirefoxDriverSettings;
import factory.settings.IDriverSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import pages.CalendarEventsPage;


public class EventsPage_Test {
    private WebDriver driver;
    private CalendarEventsPage calendarEventsPage;
    private Logger logger = (Logger) LogManager.getLogger("ProjectWork");

    @BeforeEach
    public void init() throws BrowserNotSupportedExeption {
        IDriverSettings driverSettings = null;
        String browserName = System.getProperty("browser.name");

        switch (browserName.toLowerCase()) {
            case "chrome":
                driverSettings = new ChromeDriverSettings();
                break;
            case "firefox":
                driverSettings = new FirefoxDriverSettings();
                break;
            default:
                throw new BrowserNotSupportedExeption(browserName);
        }

        this.driver = new DriverFactory(driverSettings).create();

        this.calendarEventsPage = new CalendarEventsPage(driver);
        calendarEventsPage.open();
        logger.info("Open browser");
    }

    @AfterEach
    public void driverStop() {
        if (driver != null) {
            driver.quit();
            logger.info("Close browser");
        }
    }
    @Test
    public void eventsTiles() {
        calendarEventsPage
                .checkEventTilesShouldBeVisible()
                .checkStartEventDate();
    }
    @Test
    public void selectSortedEventsType() {
        calendarEventsPage
                .selectSortedEventsType(EventTypeData.OPEN)
                .checkEventTilesShouldBeVisible()
                .checkEventsType(EventTypeData.OPEN);
    }

}
