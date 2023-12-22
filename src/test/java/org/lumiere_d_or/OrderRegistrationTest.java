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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class OrderRegistrationTest {
    // Установка уровня логгирования для WebDriverManager
    static {
        Logger wdmLogger = LoggerFactory.getLogger("io.github.bonigarcia.wdm");
        ch.qos.logback.classic.Logger wdmLoggerImpl = (ch.qos.logback.classic.Logger) wdmLogger;
        wdmLoggerImpl.setLevel(Level.OFF);
    }

    private WebDriver driver;
    private boolean allTestsPassed = true;

    public static void main(String[] args) {
        String url = MainMethods.getUrl(2);
        OrderRegistrationTest testInstance = new OrderRegistrationTest();
        testInstance.setup();
        testInstance.pageSetup(url);
        testInstance.orderRegistrationLogic();
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
    public void orderRegistrationLogic() {
        try {
            MainMethods mainMethods = new MainMethods();
            mainMethods.waitForSeconds(1);

            // Изменение количества товара
            WebElement changeProductQuantity = driver.findElement(By.cssSelector(".t-store__prod__quantity:nth-child(1) .t-store__prod__quantity__plus"));
            changeProductQuantity.click();
            mainMethods.waitForSeconds(1);

            // Добавление продукта в корзину
            WebElement addToCartButton = driver.findElement(By.cssSelector(".js-store-prod-popup-buy-btn-txt"));
            addToCartButton.click();
            mainMethods.waitForSeconds(1);

            // Переход к корзине
            WebElement goToCartButton = driver.findElement(By.cssSelector(".t706__carticon-imgwrap"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", goToCartButton);
            mainMethods.waitForSeconds(1);

            // Переход к оформлению заказа
            WebElement goToCheckoutButton = driver.findElement(By.cssSelector(".t706__sidebar-continue"));
            goToCheckoutButton.click();
            mainMethods.waitForSeconds(1);

            // Заполнение информации для оформления заказа
            WebElement fillFormNameAndSurname = driver.findElement(By.xpath(".//input[@id='input_1496239431201']"));
            fillFormNameAndSurname.sendKeys("Иван Иванов");

            WebElement fillFormEmail = driver.findElement(By.xpath(".//input[@id='input_1496239459190']"));
            fillFormEmail.sendKeys("ivanov@yandex.ru");

            WebElement fillFormPhoneNumber = driver.findElement(By.xpath(".//input[@id='input_1496239478607']"));
            fillFormPhoneNumber.sendKeys("9999999999");

            // Постановка галочки куки
            WebElement checkCookieTick = driver.findElement(By.cssSelector(".t-input-group:nth-child(4) .t-checkbox__indicator"));
            checkCookieTick.click();

            // Окончание оформление заказа
            WebElement endCheckoutButton = driver.findElement(By.cssSelector(".t-form__submit:nth-child(6) > .t-submit"));
            endCheckoutButton.click();

            // Ожидание заполнения captcha, если будет запрошено + переход страницу благодарности
            String expectedUrl = "https://lumiere-d-or.tilda.ws/thanks";
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.urlToBe(expectedUrl));
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
        String testID = "unit_test_№4 .. order_registration_and_checkout ... ";
        mainMethods.printResult(allTestsPassed, testID);
    }
}