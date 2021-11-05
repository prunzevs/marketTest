package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Класс страницы яндекс маркета с некоторой категорией каталога
 */
public class YandexMarketSomeCategory extends YandexMarket{
    /**
     * подкатегория категории каталога
     */
    protected WebElement subCategory;

    /**
     * конструктор
     * @param chromeDriver драйвер
     */
    public YandexMarketSomeCategory(WebDriver chromeDriver) {
        super(chromeDriver);
    }

    /**
     * Метод перехода в подкатегорию категории каталога
     * @param category название категории
     * @return true или false в зависимости от того, правильно ли метод перешел в заданную категорию category
     */
    @Override
    public boolean goCategory(String category){
        String subCategoryPath="//a[text()='"+category+"']";
        subCategory=driver.findElement(By.xpath(subCategoryPath));
        subCategory.click();
        if(driver.getTitle().contains(category))
            return true;
        Assertions.fail();
        return false;
    }
}
