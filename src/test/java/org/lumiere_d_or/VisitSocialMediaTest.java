package org.lumiere_d_or;

import ch.qos.logback.classic.Level;
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

public class VisitSocialMediaTest {
    // Установка уровня логгирования для WebDriverManager
    static {
        Logger wdmLogger = LoggerFactory.getLogger("io.github.bonigarcia.wdm");
        ch.qos.logback.classic.Logger wdmLoggerImpl = (ch.qos.logback.classic.Logger) wdmLogger;
        wdmLoggerImpl.setLevel(Level.OFF);
    }

    private WebDriver driver;
    private boolean allTestsPassed = true;

    public static void main(String[] args) {
        VisitSocialMediaTest testInstance = new VisitSocialMediaTest();
        testInstance.setup();
        testInstance.VisitSocialMediaLogic();
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

        //Ожидание загрузки страницы
        MainMethods mainMethods = new MainMethods();
        mainMethods.waitForSeconds(2);
    }

    @Test
    public void VisitSocialMediaLogic() {
        try {
            MainMethods mainMethods = new MainMethods();

            // Прокрутка до футера на главной странице
            WebElement elementToScrollMainPage = driver.findElement(By.id("rec624668305"));
            mainMethods.scrollToElement(driver, elementToScrollMainPage);
            mainMethods.waitForSeconds(1);

            // Переход в раздел контакты
            WebElement clickContactsPageLink = driver.findElement(By.xpath(".//div[@id='rec624668305']/div/div/div[4]/div/div[2]/a[2]"));
            clickContactsPageLink.click();
            mainMethods.waitForSeconds(2);

            // Прокрутка до блока с социальными сетями
            WebElement elementToScrollContacts = driver.findElement(By.id("rec624704939"));
            mainMethods.scrollToElement(driver, elementToScrollContacts);
            mainMethods.waitForSeconds(1);

            // Нажатие на ссылку Телеграм
            WebElement clickSocialMediaLink = driver.findElement(By.cssSelector(".t188__wrapper .t-sociallinks__item_telegram path"));
            clickSocialMediaLink.click();
            mainMethods.waitForSeconds(3);

            // Проверка перехода на страницу Телеграма
            driver.findElement(By.xpath(".//div[2]/div[2]/div/div"));

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
        String testID = "unit_test_№5 .. visit_social_media ... ";
        mainMethods.printResult(allTestsPassed, testID);
    }
}