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

public class MainPageToCatalogTest {
    // Установка уровня логгирования для WebDriverManager
    static {
        Logger wdmLogger = LoggerFactory.getLogger("io.github.bonigarcia.wdm");
        ch.qos.logback.classic.Logger wdmLoggerImpl = (ch.qos.logback.classic.Logger) wdmLogger;
        wdmLoggerImpl.setLevel(ch.qos.logback.classic.Level.ERROR);
    }

    private WebDriver driver;
    private boolean allTestsPassed = true;

    public static void main(String[] args) {
        MainPageToCatalogTest testInstance = new MainPageToCatalogTest();
        testInstance.setup();
        testInstance.MainPageToCatalogLogic();
        testInstance.tearDown();
    }

    @BeforeEach
    public void setup() {
        // Инициализация WebDriver
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Переход на главную страницу
        driver.get("https://lumiere-d-or.tilda.ws/");

        MainMethods mainMethods = new MainMethods();
        mainMethods.waitForSeconds(2);
    }

    @Test
    public void MainPageToCatalogLogic() {
        try {
            MainMethods mainMethods = new MainMethods();

            // Прокрутка до блока со ссылкой на страницу каталога
            WebElement elementToScroll = driver.findElement(By.id("recorddiv624619580"));
            mainMethods.scrollToElement(driver, elementToScroll);
            mainMethods.waitForSeconds(1);

            // Переход на страницу каталога
            WebElement catalogLink = driver.findElement(By.xpath(".//div[@id='recorddiv624619580']/div[3]/div/div/div/div/div/div/div/a/table/tbody/tr/td"));
            catalogLink.click();
            mainMethods.waitForSeconds(1);

            // Прокрутка до блока с товарами
            WebElement elementToScroll2 = driver.findElement(By.id("rec624663382"));
            mainMethods.scrollToElement(driver, elementToScroll2);
            mainMethods.waitForSeconds(2);

            // Ввод поискового запроса
            WebElement searchInput = driver.findElement(By.cssSelector(".js-store-filter-search"));
            searchInput.sendKeys("светильник");
            mainMethods.waitForSeconds(1);

            // Нажатие на кнопку поиска
            WebElement searchButton = driver.findElement(By.cssSelector(".t-store__search-icon"));
            searchButton.click();
            mainMethods.waitForSeconds(1);

            // Переход на страницу товара
            driver.findElement(By.cssSelector(".t951__grid-cont > .js-product:nth-child(3) .js-store-prod-btn > .t-store__card__btn-text"));
            driver.get("https://lumiere-d-or.tilda.ws/catalog/tproduct/624663382-659677677671-stilnovo-style-5-lamp");
            mainMethods.waitForSeconds(2);

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
        String testID = "unit_test_№1 .. main_page_to_catalog ... ";
        mainMethods.printResult(allTestsPassed, testID);
    }
}