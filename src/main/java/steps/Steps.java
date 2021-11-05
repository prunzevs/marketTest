package steps;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import pages.YandexMarket;

import pages.YandexMarketSubCategory;
import pages.YandexMarketSomeCategory;
import pages.YandexWithSearch;

/**
 * Класс шагов для тестов
 */
public class Steps {
    /**
     * Перейдём по ссылке с текстом
     * @param yandexWithSearch страница яндекса
     * @param textTitle название страницы, на которую надо перейти
     */
    @Step("Шаг 1. Перейдём по ссылке с текстом {textTitle}")
    public void GoPageText(YandexWithSearch yandexWithSearch, String textTitle){
        Assertions.assertTrue(yandexWithSearch.goPage(textTitle), "Страница "+textTitle+" не найдена");
    }

    /**
     * Перейдём в категорию
     * @param yandexMarket страница яндекс маркета
     * @param category категория, в которую переходить
     */
    @Step("Шаг 2. Перейдём в категорию {category}")
    public void GoCategoryStep(YandexMarket yandexMarket, String category){
        Assertions.assertTrue(yandexMarket.goCategory(category), "Категория "+category+" не найдена");
    }

    /**
     * Перейдём в подкатегорию
     * @param yandexMarketSomeCategory страница яндекс маркета с некоторой категорией
     * @param subCategory подкатегория
     */
    @Step("Шаг 3. Перейдём в подкатегорию {subCategory}")
    public void GoSubCategoryStep(YandexMarketSomeCategory yandexMarketSomeCategory, String subCategory){
        Assertions.assertTrue(yandexMarketSomeCategory.goCategory(subCategory), "Подкатегория "+subCategory+" не найдена");
    }

    /**
     * Выберем производителя
     * @param yandexMarketSubCategory страница яндекс маркета с подкатегорией (в ней уже находятся продукты)
     * @param manufacturer название производителя
     */
    @Step("Шаг 4. Выберем производителя {manufacturer}")
    public void ChooseManufacturerStep(YandexMarketSubCategory yandexMarketSubCategory, String manufacturer){
        Assertions.assertTrue(yandexMarketSubCategory.chooseManufacturer(manufacturer), "Производитель "+manufacturer+" не найден");
    }

    /**
     * Выберем двух производителей
     * @param yandexMarketSubCategory страница яндекс маркета с подкатегорией (в ней уже находятся продукты)
     * @param manufacturer1 название первого производителя
     * @param manufacturer2 название второго производителя
     */
    @Step("Шаг 4. Выберем производителя {manufacturer1} и производителя {manufacturer1}")
    public void ChooseManufacturerStep(YandexMarketSubCategory yandexMarketSubCategory, String manufacturer1, String manufacturer2){
        Assertions.assertTrue(yandexMarketSubCategory.chooseManufacturer(manufacturer1, manufacturer2), "Производитель "+manufacturer1+" или "+manufacturer2+" не найден");
    }

    /**
     * Показывать на странице по заданному количеству элементов
     * @param yandexMarketSubCategory страница яндекс маркета с подкатегорией (в ней уже находятся продукты)
     * @param number количество элементов на странице
     */
    @Step("Шаг 5. Показывать на странице по {number}")
    public void ShowByStep(YandexMarketSubCategory yandexMarketSubCategory, int number){
        Assertions.assertTrue(yandexMarketSubCategory.showBy(number), "Количество элементов на странице не соответствует "+number);
    }

    /**
     * Проверка выборки по наличию названия телефона среди результата поиска
     * @param yandexMarketSubCategory страница яндекс маркета с подкатегорией (в ней уже находятся продукты)
     * @param phoneName название телефона
     */
    @Step("Шаг 6. Проверка выборки по наличию {phoneName}")
    public void phoneNameCheckStep(YandexMarketSubCategory yandexMarketSubCategory, String phoneName){
        yandexMarketSubCategory.phoneNameCheck(phoneName);

    }

    /**
     * Задать границы цен от и до
     * @param yandexMarketSubCategory страница яндекс маркета с подкатегорией (в ней уже находятся продукты)
     * @param from цена от
     * @param to цена до
     */
    @Step("Шаг ?. Задать цену от {from} до {to}")
    public void setPriceFromToStep(YandexMarketSubCategory yandexMarketSubCategory, int from, int to){
        yandexMarketSubCategory.setPriceFromTo(from, to);
    }

    /**
     * Проверить соответствие названия первого элемента
     * @param yandexMarketSubCategory  страница яндекс маркета с подкатегорией (в ней уже находятся продукты)
     */
    @Step("Шаг ??. Проверить соответствие названия первого элемента")
    public void checkFirstProductNameStep(YandexMarketSubCategory yandexMarketSubCategory){
        yandexMarketSubCategory.checkFirstProductName();
    }


}
