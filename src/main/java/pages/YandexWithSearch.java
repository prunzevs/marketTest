package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Класс страницы яндекса
 */
public class YandexWithSearch {

    private String selectorSearchItems="//li[@class='serp-item desktop-card']";
    private String selectorURL = ".//a[@href]";
    private String selectorNamePage = ".//h2";
    private String selectorDescription = ".//span[@class='OrganicTextContentSpan']";

    private WebDriver driver;

    private List<WebElement> searchItems;
    private List<Map<String,Object>> collectResults = new ArrayList<>();

    /**
     * конструктор страницы яндкса
     * @param driver веб-драйвер
     */
    public YandexWithSearch(WebDriver driver) {
        this.driver = driver;
        searchItems = driver.findElements(By.xpath(selectorSearchItems));
    }

    /**
     * Конструктор страницы яндекса с поисковым запросом
     * @param driver драйвер
     * @param search поисковый запрос
     */
    public YandexWithSearch(WebDriver driver, String search) {
        this.driver = driver;
        this.driver.get("https://www.yandex.ru/search/?text="+search);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        searchItems = driver.findElements(By.xpath(selectorSearchItems));
    }

    /**
     * Метод по сбору результатов поиска в список мап
     * @return список мап с результатами поиска
     */
    public List<Map<String, Object>> getCollectResults() {
        for(WebElement result : searchItems){

            Map<String, Object> map = new HashMap<String, Object>(){{
                            put("WEB_ELEMENT", result);
                            put("URL", result.findElement(By.xpath(selectorURL)).getAttribute("href"));
                            put("NAME_PAGE", result.findElement(By.xpath(selectorNamePage)).getText());
                            put("DESCRIPTION", result.findElement(By.xpath(selectorDescription)).getText());
                }};
            collectResults.add(map);
        }
        return collectResults;
    }

    /**
     * метод перехода на страницу
     * @param namePage название страницы
     * @return true или false в зависимости от успеха
     */
    public boolean goPage(String namePage){

        //WebElement pageLink = (WebElement) collectResults.stream()
        WebElement pageLink = (WebElement) this.getCollectResults().stream()

                .filter(x->x.get("NAME_PAGE").toString().contains(namePage))
                .findFirst()
                .get().get("WEB_ELEMENT");
        pageLink.findElement(By.xpath(selectorURL)).click();
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        for (String tab : tabs){
            driver.switchTo().window(tab);
            if(driver.getTitle().contains(namePage))
                return true;
        }
        Assertions.fail();
        return false;
    }
}
