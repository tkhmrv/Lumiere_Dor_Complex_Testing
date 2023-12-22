package org.lumiere_d_or;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс, содержащий все основные повторяющиеся функции
 */
public class MainMethods {
    /**
     * Функция для запроса у тестировщика URL страницы
     *
     * @return URL страницы
     */
    public static String getUrl(Integer i) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите URL товара для " + (i + 2) + " теста: ");
        return scanner.nextLine();
    }

    /**
     * Функция ожидания страницы для указанного количества секунд
     *
     * @param seconds Количество секунд
     */
    public void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L); // Переводим секунды в миллисекунды
        } catch (InterruptedException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "InterruptedException in waitForSeconds", e);
        }
    }

    /**
     * Функция выводит результат отдельно выполненного теста
     *
     * @param allTestsPassed Булевая переменная на успешность выполнения тестов
     */
    public void printResult(boolean allTestsPassed, String testID) {
        if (allTestsPassed) {
            System.out.println(testID + ConsoleColors.GREEN + "passed" + ConsoleColors.RESET);
        } else {
            System.out.println(testID + ConsoleColors.RED + " failed" + ConsoleColors.RESET);
        }
    }

    /**
     * Функция перемещается к определенному элементу на странице
     *
     * @param driver  драйвер браузера
     * @param element элемент, к которому нужно переместиться
     */
    public void scrollToElement(WebDriver driver, WebElement element) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }
}