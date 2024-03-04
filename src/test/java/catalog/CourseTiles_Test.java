package catalog;

import data.catalog.LessonsCategoryData;
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
import pages.CatalogPage;
import pages.LessonPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CourseTiles_Test {

    private WebDriver driver;
    private CatalogPage catalogPage =null;
    private Logger logger = (Logger) LogManager.getLogger("ProjectWork");

    @BeforeEach
    public void init() throws BrowserNotSupportedExeption{
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

        List<String> queryParams = new ArrayList<>();
        queryParams.add(String.format("categories=%s", LessonsCategoryData.TESTING.name().toLowerCase(Locale.ROOT)));

        this.catalogPage = new CatalogPage(driver);
        catalogPage.open(queryParams);
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
    public void catalogTilesNumbers() {
        catalogPage.lessonTilesNumberShouldBeSameAs(10);
    }

    @Test
    public void checkDataOnLessonPage()throws IOException {
        for(int i=1; i<catalogPage.getTilesNumbers(); i++){
            String expectedHeader = catalogPage.getLessonNameByIndex(i);
            LessonPage lessonPage=new LessonPage(driver,"/qa-lead/");
            String expectedLessonDuration = catalogPage.getLessonDuration(i);

            lessonPage.checkHeaderLessonByIndex(i,expectedHeader);
            lessonPage.checkDescriptionLessonByIndex(i);
            lessonPage.checkLessonDuration(i,expectedLessonDuration);
            lessonPage.checkLessonFormat(i,"Онлайн");
        }

    }

}