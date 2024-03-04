package pages;

import data.sorted.EventTypeData;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalendarEventsPage extends AbsBasePage{

    public CalendarEventsPage(WebDriver driver) {
        super(driver, "/events/near/");
    }
    @FindBy(css = ".dod_new-event")
    private List<WebElement> eventTiles;
    @FindBy(css = ".dod_new-event__calendar-icon ~span")
    private List<WebElement>dateEvents;
    @FindBy(css = ".dod_new-event .dod_new-type__text")
    private List<WebElement> eventsTypes;
    private String dropduwnSortngEventsListSelector = ".dod_new-events-dropdown";
    private String dropduwnEventlistSelector = dropduwnSortngEventsListSelector + " .dod_new-events-dropdown__list";
    private String dropduwnSortingEventTemplate = dropduwnEventlistSelector + " [title='%s']";

    public CalendarEventsPage checkEventTilesShouldBeVisible() {
        Assertions.assertTrue(waitTools.waitForCondition((ExpectedConditions.visibilityOfAllElements(eventTiles))));

        logger.info("Event tiles are visible");

        return this;

    }
    //  проверка, что даты не позднее настоящей
    public CalendarEventsPage  checkStartEventDate() {
        for(WebElement dateEvent: dateEvents) {
            LocalDate currentDate = LocalDate.now();
            Pattern pattern = Pattern.compile("\\d+\\s+[а-яА-Я]+\\s+\\d{4}");
            String dateEventStr = dateEvent.getText();
            Matcher matcher = pattern.matcher(dateEventStr);
            if(!matcher.find()) {
                dateEventStr += String.format(" %d", currentDate.getYear());
            }
            LocalDate eventDate = LocalDate.parse(dateEventStr,
                    DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.of("ru")));

            Assertions.assertTrue(eventDate.isAfter(currentDate) || eventDate.isEqual(currentDate),
                    "The event date is not as expected or has already passed");
        }
        logger.info("Event dates are not older than the present time");
        return this;
    }

    // проверка, что выпадающий список не открыт
    private CalendarEventsPage dropdownSortingEventsShouldNotBeOpened() {
        Assertions.assertTrue(
                waitTools.waitForCondition(
                        ExpectedConditions.not(ExpectedConditions.attributeContains(
                                $(dropduwnSortngEventsListSelector), "class", ".dod_new-events-dropdown_opened")
                        )
                )
        );
        logger.info("Dropdown sorting events should not be opened");
        return this;
    }
    // проверка, что выпадающий список  открыт
    private CalendarEventsPage dropdownSortingEventsShouldBeOpened(){
        waitTools.until(ExpectedConditions.attributeContains($(dropduwnEventlistSelector),
                "class", ".dod_new-events-dropdown_opened"));

        logger.info("Dropdown sorting events should be opened");

        return this;

    }

    //  метод клик
    private CalendarEventsPage openSortingEventsDropdown() {
        $(dropduwnSortngEventsListSelector).click();

        logger.info("Click on the dropdown");
        return this;
    }

    // список видимый
    private CalendarEventsPage sortingItemsShouldBeVisible() {
        Assertions.assertTrue(waitTools.waitElementVisible($(dropduwnEventlistSelector)));
        logger.info("Dropdown list visible");
        return this;
    }
    // метод сортировки по заданному значению
    private CalendarEventsPage clickSortingItem(EventTypeData eventsSortedData) {
        $(String.format(dropduwnSortingEventTemplate, eventsSortedData.getName())).click();

        logger.info("Set value selected");
        return this;
    }

    public CalendarEventsPage selectSortedEventsType(EventTypeData eventsSortedData){
        this.dropdownSortingEventsShouldNotBeOpened()
                .openSortingEventsDropdown()
                .dropdownSortingEventsShouldBeOpened()
                .sortingItemsShouldBeVisible()
                .clickSortingItem(eventsSortedData);

        return this;

    }
    //  проверка соответствия типа события ожидаемому "Открытый вебинар" в карточках
    public CalendarEventsPage checkEventsType(EventTypeData eventTypeData){
        for (WebElement element : eventsTypes) {
            Assertions.assertEquals(eventTypeData.getName(),element.getText(),
                    "Element text does not match expected value");
        }
        logger.info("The event type is as expected");
        return this;
    }

}