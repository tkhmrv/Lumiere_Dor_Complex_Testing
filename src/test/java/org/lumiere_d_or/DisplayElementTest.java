package org.lumiere_d_or;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DisplayElementTest {
    // Установка уровня логгирования для WebDriverManager
    static {
        Logger wdmLogger = LoggerFactory.getLogger("io.github.bonigarcia.wdm");
        ch.qos.logback.classic.Logger wdmLoggerImpl = (ch.qos.logback.classic.Logger) wdmLogger;
        wdmLoggerImpl.setLevel(ch.qos.logback.classic.Level.ERROR);
    }

    private WebDriver driver;
    private boolean allTestsPassed = true;

    public static void main(String[] args) {
        String url = MainMethods.getUrl(0);
        DisplayElementTest testInstance = new DisplayElementTest();
        testInstance.setup();
        testInstance.pageSetup(url);
        testInstance.productPageDisplaysCorrectlyWithPrint();
        testInstance.tearDown();
    }

    @BeforeEach
    public void setup() {
        // Инициализация и настройка WebDriver
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    public void pageSetup(String url) {
        // Переход на страницу продукта
        driver.get(url);

        //Ожидание загрузки страницы
        MainMethods mainMethods = new MainMethods();
        mainMethods.waitForSeconds(2);
    }

    @Test
    public void productPageDisplaysCorrectlyWithPrint() {

        try {
            // Проверка загрузки названия товара
            WebElement productNameElement = driver.findElement(By.cssSelector(".t-store__prod-popup__name"));
            checkElementDisplayAndPrint(productNameElement, "Product name");

            // Проверка загрузки цены товара
            WebElement productPriceElement = driver.findElement(By.cssSelector(".js-store-prod-price > .js-product-price"));
            checkElementDisplayAndPrint(productPriceElement, "Product price");

            // Проверка загрузки кнопки покупки
            WebElement productPurchaseButton = driver.findElement(By.cssSelector(".js-store-prod-popup-buy-btn-txt"));
            checkElementDisplayAndPrint(productPurchaseButton, "Product purchase button");

        } catch (Throwable t) {
            // если возникла ошибка, устанавливаем allTestsPassed в false
            allTestsPassed = false;
            // выводим сообщение об ошибке
            System.err.println("Test failed: " + t.getMessage());
            t.printStackTrace();
        }
    }

    @Test
    public void productPageDisplaysCorrectly() {

        try {
            // Проверка загрузки названия товара
            WebElement productNameElement = driver.findElement(By.cssSelector(".t-store__prod-popup__name"));
            checkElementDisplay(productNameElement);

            // Проверка загрузки цены товара
            WebElement productPriceElement = driver.findElement(By.cssSelector(".js-store-prod-price > .js-product-price"));
            checkElementDisplay(productPriceElement);

            // Проверка загрузки кнопки покупки
            WebElement productPurchaseButton = driver.findElement(By.cssSelector(".js-store-prod-popup-buy-btn-txt"));
            checkElementDisplay(productPurchaseButton);

        } catch (Throwable t) {
            // если возникла ошибка, устанавливаем allTestsPassed в false
            allTestsPassed = false;
            // выводим сообщение об ошибке
            System.err.println("Test failed: " + t.getMessage());
            t.printStackTrace();
        }
    }

    @AfterEach
    public void tearDown() {
        // Завершение работы WebDriver после каждого теста
        if (driver != null) {
            driver.quit();
        }

        // Выводим сообщение о результате всех тестов в конце
        MainMethods mainMethods = new MainMethods();
        String testID = "unit_test_№2 .. elements_on_display ... ";
        mainMethods.printResult(allTestsPassed, testID);
    }

    // Функция проверки загрузки элемента на странице и вывода результата
    public void checkElementDisplayAndPrint(WebElement element, String elementName) {
        if (element.isDisplayed()) {
            System.out.println(elementName + " is displayed successfully.");
        } else {
            assert false : elementName + " is not displayed.";
            allTestsPassed = false;
        }
    }

    // Функция проверки загрузки элемента на странице без вывода
    public void checkElementDisplay(WebElement element) {
        if (!element.isDisplayed()) {
            allTestsPassed = false;
        }
    }
}