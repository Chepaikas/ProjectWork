package pages;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import java.io.IOException;

public class LessonPage extends AbsBasePage {

    private CatalogPage catalogPage;
    public LessonPage(WebDriver driver, String lessonsPath) {
        super(driver, String.format("/lessons/%s", lessonsPath));
        this.catalogPage = new CatalogPage(driver);
    }

    // проверка заголовка по индексу LessonPage
    public void checkHeaderLessonByIndex(int index, String expectedHeader) throws IOException {
        Document dom =catalogPage.getDomPage(index);
        Element headerPageElement = dom.selectFirst("h1");
        logger.info("Header present");
        Assertions.assertEquals(expectedHeader, headerPageElement.text(),
                "No header");
    }

    //  проверка оприсания лекций по индексу LessonPage
    public void checkDescriptionLessonByIndex(int index) throws IOException{
        Element headerPageElement = catalogPage.getDomPage(index).selectXpath("//div[contains(@class,'dZDxRw')]").get(0);

        logger.info("Description present");

        Assertions.assertFalse(headerPageElement.text().isEmpty(),"No description");
    }

    //  проверка продолжительности курса из LessonPage
    public void checkLessonDuration(int index, String expectedDuration)throws IOException{
        Element headerPageElement = catalogPage.getDomPage(index)
                .selectXpath("//div/following-sibling::p[contains(text(),'месяц')]").get(0);

        logger.info("Course duration is determined");

        Assertions.assertEquals(expectedDuration.replaceAll("^.*?·\\s*",""),
                headerPageElement.text(),"The duration of the course is not determined");

    }

    // проверка формата обучения LessonPage
    public void checkLessonFormat(int index, String format) throws IOException {
        Element formatLessonElement = catalogPage.getDomPage(index)
                .selectXpath(String.format("//p[contains(text(),'%s')]",format)).get(0);

        logger.info("Expected course format");

        Assertions.assertFalse(formatLessonElement.text().isEmpty(),"The course format is not as expected");
    }

}