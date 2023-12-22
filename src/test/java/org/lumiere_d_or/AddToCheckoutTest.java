package org.lumiere_d_or;

import ch.qos.logback.classic.Level;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddToCheckoutTest {
    // Установка уровня логгирования для WebDriverManager
    static {
        Logger wdmLogger = LoggerFactory.getLogger("io.github.bonigarcia.wdm");
        ch.qos.logback.classic.Logger wdmLoggerImpl = (ch.qos.logback.classic.Logger) wdmLogger;
        wdmLoggerImpl.setLevel(Level.OFF);
    }

    private WebDriver driver;
    private boolean allTestsPassed = true;

    public static void main(String[] args) {
        String url = MainMethods.getUrl(1);
        AddToCheckoutTest testInstance = new AddToCheckoutTest();
        testInstance.setup();
        testInstance.pageSetup(url);
        testInstance.addToCheckoutLogic();
        testInstance.tearDown();
    }

    @BeforeEach
    public void setup() {
        // Инициализация WebDriver
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
    public void addToCheckoutLogic() {
        try {
            MainMethods mainMethods = new MainMethods();

            // Изменение количества товара
            WebElement changeProductQuantity = driver.findElement(By.cssSelector(".t-store__prod__quantity:nth-child(1) .t-store__prod__quantity__plus"));
            changeProductQuantity.click();

            // Добавление товара в корзину
            WebElement addToCartButton = driver.findElement(By.cssSelector(".js-store-prod-popup-buy-btn-txt"));
            addToCartButton.click();
            mainMethods.waitForSeconds(2);

            // Переход к корзине
            WebElement goToCartButton = driver.findElement(By.cssSelector(".t706__carticon-imgwrap"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", goToCartButton);

            // Проверка, что продукт добавлен в корзину
            driver.findElement(By.cssSelector(".t706__product-imgdiv"));
            mainMethods.waitForSeconds(2);

            // Получение цены продукта и количества
            WebElement productPriceElement = driver.findElement(By.cssSelector(".t-store__prod-popup__price-value"));
            WebElement productQuantityElement = driver.findElement(By.cssSelector(".t706__product-quantity"));

            // Расчет ожидаемой итоговой стоимости
            double productPrice = Double.parseDouble(productPriceElement.getText().replace(" ", ""));
            int productQuantity = Integer.parseInt(productQuantityElement.getText());
            double expectedTotalPrice = productPrice * productQuantity;

            // Получение отображаемой итоговой стоимости
            WebElement totalPriceElement = driver.findElement(By.cssSelector(".t706__sidebar-prodamount > .t706__cartwin-prodamount-price"));
            double actualTotalPrice = Double.parseDouble(totalPriceElement.getText().replace(" ", ""));

            // Проверка, что итоговая стоимость рассчитана корректно
            assertEquals(expectedTotalPrice, actualTotalPrice, 0.01, "Incorrect total price.");

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
        String testID = "unit_test_№3 .. add_to_checkout ... ";
        mainMethods.printResult(allTestsPassed, testID);
    }
}