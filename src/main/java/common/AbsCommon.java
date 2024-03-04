package common;

import com.github.javafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import tools.WaitTools;

public  abstract class AbsCommon {
    protected WebDriver driver;
    protected WaitTools waitTools;
    protected Actions actions;
    protected Faker faker;
    protected static Logger logger = (Logger) LogManager.getLogger("ProjectWork");


    public AbsCommon(WebDriver driver) {
        this.driver = driver;
        waitTools = new WaitTools(driver);
        this.actions = new Actions(driver);
        this.faker = new Faker();
        PageFactory.initElements(driver, this);

    }
}