package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
*Класс страницы яндекс маркета с подкатегорией
 */
public class YandexMarketSubCategory extends YandexMarket{
    /**
     * драйвер ожиданий
     */
    private WebDriverWait wait = new WebDriverWait(driver, 10);

    /**
     * xpath для названия продукта
     */
    private String nameOfElementPath ="//h3[not (@class='-KSHF')]";
    /**
     * альтернативный xpath для названия продукта
     */
    private String aNameOfElementPath ="//h3[@data-zone-name='title']/a";
    /**
     * xpath кнопки "Показывать по 12(48)"
     */
    private String buttonPath="//button[@class='vLDMf']";
    /**
     * xpath для кнопки "Следующая страница"
     */
    private String nextPagePath="//a[@aria-label='Следующая страница']";

    /**
     * конструктор класса
     * @param chromeDriver драйвер
     */
    public YandexMarketSubCategory(WebDriver chromeDriver) {
        super(chromeDriver);
    }

    /**
     * Выбор производителя
     * @param name название производителя
     * @return проверяет наличие производителя в названии продукта после обновления результатов и возвращает true или false соответственно
     */
    public boolean chooseManufacturer (String name){
        WebElement manufacturerBox = driver.findElement(By.xpath("//input[@name='Производитель "+name+"']/.."));
        manufacturerBox.click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(nameOfElementPath),name));
        List<WebElement> table = driver.findElements(By.xpath(nameOfElementPath));
        for(WebElement x: table){
            if(x.getText().contains(name)==false)
                return false;
        }
        return true;
    }

    /**
     * Выбор двух производителей
     * @param name1 название первого производителя
     * @param name2 название второго производителя
     * @return проверяет наличие хотя бы одного производителя в названии продукта после обновления результатов и возвращает true или false соответственно
     */
    public boolean chooseManufacturer (String name1, String name2){
        WebElement manufacturerBox = driver.findElement(By.xpath("//input[@name='Производитель "+name1+"']/.."));
        manufacturerBox.click();
        waitForRefresh();
        manufacturerBox = driver.findElement(By.xpath("//input[@name='Производитель "+name2+"']/.."));
        manufacturerBox.click();
        waitForRefresh();
        List<WebElement> table = driver.findElements(By.xpath(nameOfElementPath));
        for(WebElement x: table){
            if((x.getText().toLowerCase().contains(name1.toLowerCase())==false) & (x.getText().toLowerCase().contains(name2.toLowerCase())==false)) {
                System.out.println(x.getText());
                System.out.println(x);
                return false;
            }
        }
        return true;
    }


    /**
     * Показывать по n элементов на странице
     * @param n количество элементов на странице
     * @return true или false в зависимости от правильного вывода количества элементов
     */
    public boolean showBy(int n){
        String path = "//button[text()='Показывать по "+n+"']";
        WebElement showButton = driver.findElement(By.xpath(buttonPath));
        showButton.click();
        WebElement showButtonBy = driver.findElement(By.xpath(path));
        showButtonBy.click();
        wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath(nameOfElementPath), n));
        List<WebElement> table = driver.findElements(By.xpath(nameOfElementPath));
        if(table.size()==n)
            return true;
        return false;
    }

    /**
     * Проверяет соответствие количества элементов на странице входящему параметру
     * @param n количество проверяемых элементов
     * @return true или false
     * @deprecated Эта проверка включена в метод по изменению количества элементов на странице
     * @see YandexMarketSubCategory#showBy(int)
     */
    public boolean numberOfElementsCheck(int n){
        List<WebElement> table = driver.findElements(By.xpath(nameOfElementPath));
        if(table.size()==n)
            return true;
        return false;
    }

    /**
     * Проверяет, что в названиях всех продуктов присутствует название телефона во входящем параметре
     * @param phoneName название проверяемого телефона
     */
    public void phoneNameCheck(String phoneName){
        Boolean isPresent;
        do{
            isPresent = driver.findElements(By.xpath(nextPagePath)).size() > 0;
            List<WebElement> table = driver.findElements(By.xpath(nameOfElementPath));
            Assertions.assertTrue(table.stream().anyMatch(x->x.getText().contains(phoneName)),"not "+phoneName+" was found");
            if(isPresent==true) {
                WebElement y = driver.findElement(By.xpath(nextPagePath));
                y.click();
                waitForRefresh();
            }
        }while (isPresent==true);
    }

    /**
     * Устанавливает верхнюю и нижнюю границы цены
     * @param from "цена от"
     * @param to "цена до"
     */
    public void setPriceFromTo(Integer from, Integer to){
        WebElement priceFrom = driver.findElement(By.name("Цена от"));
        priceFrom.sendKeys(from.toString());
        waitForRefresh();
        WebElement priceTo = driver.findElement(By.name("Цена до"));
        priceTo.sendKeys(to.toString());
        waitForRefresh();
    }

    /**
     * Запоминает первый элемент, вбивает в поиск его название и сравнивает первый найденный элемент с тем, что записан в память
     */
    public void checkFirstProductName(){

        WebElement first = driver.findElement(By.xpath(aNameOfElementPath));
        String firstProductName = first.getAttribute("title");
        this.findProduct(firstProductName);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        WebElement second = driver.findElement(By.xpath(aNameOfElementPath));
        String secondProductName= second.getAttribute("title");
        Assertions.assertEquals(firstProductName, secondProductName);

    }

}
