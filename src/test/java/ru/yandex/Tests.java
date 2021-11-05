package ru.yandex;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.YandexMarket;
import pages.YandexMarketSubCategory;
import pages.YandexMarketSomeCategory;
import pages.YandexWithSearch;
import steps.Steps;


import java.util.List;

public class Tests extends BaseTests {



    @DisplayName("Проверка работоспособности ХРОМ драйвера")
    @Test
    public void firstTest() {
        chromeDriver.get("https://www.google.com/");
        WebElement searchField = chromeDriver.findElement(By.xpath("//input[@aria-label='Найти']"));
        WebElement searchButton = chromeDriver.findElement(By.xpath("//input[@aria-label='Поиск в Google']"));

        searchField.click();
        searchField.sendKeys("Гладиолус");
        searchButton.click();

        List<WebElement> resultSearch = chromeDriver.findElements(By.xpath("//*[@class='g']//*[@class='yuRUbf']//cite"));

        Assertions.assertTrue(resultSearch.stream().anyMatch(x -> x.getText().contains("https://ru.wikipedia.org")), "Ссылка на вики не найдена");
    }

    @Feature("Проверка Яндекс маркета")
    @DisplayName("Проверка поиска производителя телефонов")
    @ParameterizedTest
    @CsvSource({"Электроника,Смартфоны,Apple,iPhone"})
    public void YandexSecondTest(String category, String subCategory, String manufacturer, String phoneName){


        /**
         * количество элементов на странице
         */
        int numberOfElements=12;
        //правильно ли ее объявлять здесь?

        Steps step = new Steps();
        YandexWithSearch yandexWithSearch = new YandexWithSearch(chromeDriver, "яндекс маркет");
        step.GoPageText(yandexWithSearch,"Яндекс.Маркет");

        YandexMarket yandexMarket = new YandexMarket(chromeDriver);
        step.GoCategoryStep(yandexMarket,category);

        YandexMarketSomeCategory yandexMarketSomeCategory= new YandexMarketSomeCategory(chromeDriver);
        step.GoSubCategoryStep(yandexMarketSomeCategory,subCategory);


        YandexMarketSubCategory yandexMarketSubCategory = new YandexMarketSubCategory(chromeDriver);
        step.ChooseManufacturerStep(yandexMarketSubCategory,manufacturer);
        step.ShowByStep(yandexMarketSubCategory,numberOfElements);
        step.phoneNameCheckStep(yandexMarketSubCategory,phoneName);


    }

    @Feature("Проверка Яндекс маркета")
    @DisplayName("Проверка нахождения нужного компьютера")
    @ParameterizedTest
    @CsvSource({"Компьютеры,Ноутбуки,HP,Lenovo,10000,30000"})
    public void YandexFirstTest(String category,String subCategory,String manufacturer1,String manufacturer2, int from, int to){

        int n=12;

        Steps step = new Steps();
        YandexWithSearch yandexWithSearch = new YandexWithSearch(chromeDriver, "яндекс маркет");
        step.GoPageText(yandexWithSearch,"Яндекс.Маркет");

        YandexMarket yandexMarket = new YandexMarket(chromeDriver);
        step.GoCategoryStep(yandexMarket,category);

        YandexMarketSomeCategory yandexMarketSomeCategory= new YandexMarketSomeCategory(chromeDriver);
        step.GoSubCategoryStep(yandexMarketSomeCategory,subCategory);

        YandexMarketSubCategory yandexMarketSubCategory = new YandexMarketSubCategory(chromeDriver);
        step.setPriceFromToStep(yandexMarketSubCategory,from, to);
        step.ChooseManufacturerStep(yandexMarketSubCategory, manufacturer1, manufacturer2);
        step.ShowByStep(yandexMarketSubCategory,n);
        step.checkFirstProductNameStep(yandexMarketSubCategory);

    }
}