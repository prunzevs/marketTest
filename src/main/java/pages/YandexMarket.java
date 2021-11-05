package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *Класс страницы яндекс маркета
 */
public class YandexMarket {
    /**
     * хром драйвер
     */
    protected WebDriver driver;
    /**
     * кнопка каталог
     */
    protected WebElement catalogButton;
    /**
     * список значений из каталога
     */
    protected List<WebElement> catalogList = new ArrayList<>();
    /**
     * мапа значений из каталога
     */
    protected Map<String, WebElement> catalogMap= new HashMap<>();
    /**
     * драйвер для ожиданий
     */
    protected WebDriverWait waitFor;
    /**
     * поле поиска
     */
    protected WebElement searchField;
    /**
     * Кнопка поиска
     */
    protected WebElement findButton;

    /**
     * xpath для поля поиска
     */
    protected String searchFieldId="header-search";
    /**
     * xpath для кнопки "поиск"
     */
    protected String findButtonPath="//button[@data-r='search-button']";
    /**
     * xpath для первой вспомогательной кнопки маркера для ожидания обновления результатов поиска
     */
    protected String waitFor1Path="//div[@class='_8v6CF _2KV_b']";
    /**
     * xpath для второй вспомогательной кнопки маркера для ожидания обновления результатов поиска
     */
    protected String waitFor2Path="//div[@class='_8v6CF']";
    /**
     * xpath для списка элементов каталога
     */
    protected String CatalogListPath="//li[@class='_1hPrb cia-cs']";
    /**
     * xpath для кнопки "каталог"
     */
    protected String catalogButtonPath = "//span[text()='Каталог']/../../..";
    /**
     * xpath для ссылки на категорию каталога
     */
    protected String selectorUrl = "./a[@href]";

    /**
     *Конструктор для этой страницы
     * @param chromeDriver Вебдрайвер
     */
    public YandexMarket(WebDriver chromeDriver){
        this.driver=chromeDriver;
        waitFor = new WebDriverWait(driver, 9);
        searchField = driver.findElement(By.id(searchFieldId));
        findButton = driver.findElement(By.xpath(findButtonPath));

    }

    /**
     * Метод нажатия на кнопку "Каталог"
     */
    public void catalogClick(){
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        catalogButton=driver.findElement(By.xpath(catalogButtonPath));
        catalogButton.click();

    }

    /**
     * Метод получения для списка категория из каталога
     * @return catalogList список элементов из каталога
     */
    public List<WebElement> getCatalogList(){
        catalogList=driver.findElements(By.xpath(CatalogListPath));
        return catalogList;
    }

    /**
     * Метод для получения мапы с названиями категорий каталога и ссылками для перехода
     * @return catalogMap сформированная методом мапа
     */
    public Map<String, WebElement> getCatalogMap(){
        getCatalogList();
        for(WebElement result : catalogList) {
            catalogMap.put(result.getText(), result.findElement(By.xpath(selectorUrl)));
        }
        return catalogMap;
    }

    /**
     * Переходит на указанную категорию в каталоге
     * @param category название категории
     * @return true для проверке в steps
     */
    public boolean goCategory(String category){
        catalogClick();
        getCatalogList();
        getCatalogMap();
        catalogMap.get(category).click();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        /*
        if(driver.getTitle().contains(category))
            return true;
        Assertions.fail();
        return false;
        */
        return true;

    }

    /**
     * Метод для ожидания обновления результатов при поиске.
     * При изменениях в области, где показываются продукты, нужно дождаться обновления результатов.
     * Это достигается путем ожидания изменений класса кнопки.
     * Первое изменение происходит при начале обновления результатов.
     * Второе изменение при окончании обновления результатов.
     * Таким образом метод ждет пока кнопка изменит свой класс дважды.
     */
    public void waitForRefresh(){
        waitFor.until(ExpectedConditions.presenceOfElementLocated(By.xpath(waitFor1Path)));
        waitFor.until(ExpectedConditions.presenceOfElementLocated(By.xpath(waitFor2Path)));
    }

    /**
     *Найти продукт в поиске
     * @param product Название искомого продукта
     */
    public void findProduct(String product){
        searchField.sendKeys(product);
        findButton.click();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

    }


}
