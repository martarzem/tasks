package com.kodilla.testing2.crudapp;

import com.kodilla.testing2.config.WebDriverConfig;
import org.openqa.selenium.support.ui.Select;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CrudAppTestSuite {
    private final static String BASE_URL = "http://localhost:8888/tasks_frontend/";
    private final static String XPATH_TASK_NAME = "//form[contains(@action, \"createTask\")]/fieldset[1]/input";
    private final static String XPATH_TASK_CONTENT = "//form[contains(@action, \"createTask\")]/fieldset[2]/textarea";
    private final static String XPATH_ADD_BUTTON = "//form[contains(@action, \"createTask\")]/fieldset[3]/button";
    private WebDriver driver;
    private Random generator;

    @Before
    public void initTests() {
        driver = WebDriverConfig.getDriver(WebDriverConfig.FIREFOX);
        driver.get(BASE_URL);

        generator = new Random();
    }

    private String createCrudAppTestTask() throws InterruptedException {
        String taskName = "Task number " + generator.nextInt(100000);
        String taskContent = taskName + " content";

        WebElement name = driver.findElement(By.xpath(XPATH_TASK_NAME));
        name.sendKeys(taskName);

        WebElement content = driver.findElement(By.xpath(XPATH_TASK_CONTENT));
        content.sendKeys(taskContent);

        WebElement pushButton = driver.findElement(By.xpath(XPATH_ADD_BUTTON));
        pushButton.click();
        Thread.sleep(5000);

        return taskName;
    }

    private void sendTestTaskToTrello(String taskName) throws InterruptedException {
        driver.navigate().refresh();

        while(!driver.findElement(By.xpath("//select[1]")).isDisplayed());

        driver.findElements(By.xpath("//form[@class=\"datatable__row\"]")).stream()
                .filter(anyForm ->
                        anyForm.findElement(By.xpath(".//p[@class=\"datatable__field-value\"]"))
                                .getText().equals(taskName))
                .forEach(theForm -> {
                    WebElement selectBoard = theForm.findElement(By.xpath(".//select[1]"));
                    Select selectB = new Select(selectBoard);
                    selectB.selectByIndex(1);

                    WebElement selectList = theForm.findElement(By.xpath(".//select[2]"));
                    Select selectL = new Select(selectList);
                    selectL.selectByIndex(3);

                    WebElement buttonCreateCard =
                            theForm.findElement(By.xpath(".//button[contains(@class, \"card-creation\")]"));
                    buttonCreateCard.click();
                });
        Thread.sleep(5000);

        driver.switchTo().alert().accept();
    }

    private void deleteCrudAppTestTask(String taskName) throws InterruptedException {
        driver.findElements(By.xpath("//form[@class=\"datatable__row\"]")).stream()
                .filter(anyForm ->
                        anyForm.findElement(By.xpath(".//p[@class=\"datatable__field-value\"]"))
                                .getText().equals(taskName))
                .forEach(theForm -> {
                    WebElement deleteButton =
                            theForm.findElement(By.xpath(".//button[@data-task-delete-button=\"\"]"));
                    deleteButton.click();
                });
        Thread.sleep(5000);

        driver.close();
    }

    private boolean checkTaskExistsInTrello(String taskName) throws InterruptedException {
        final String TRELLO_URL = "https://trello.com/login";
        boolean result = false;

        driver = WebDriverConfig.getDriver(WebDriverConfig.FIREFOX);
        driver.get(TRELLO_URL);

        driver.findElement(By.id("user")).sendKeys("marta.rzempowska@gmail.com");
        driver.findElement(By.id("password")).sendKeys("Voldemort");
        WebElement el = driver.findElement(By.id("login"));
        el.submit();

        Thread.sleep(5000);

        driver.findElements(By.xpath("//a[@class=\"board-tile\"]")).stream()
                .filter(aHref -> aHref.findElements(By.xpath(".//div[@title=\"Kodilla Application\"]")).size() > 0)
                .forEach(WebElement::click);

        Thread.sleep(5000);

        result = driver.findElements(By.xpath("//span")).stream()
                .anyMatch(theSpan -> theSpan.getText().equals(taskName));

        driver.close();

        return result;
    }

    @Test
    public void shouldCreateTrelloCard() throws InterruptedException {
        String taskName = createCrudAppTestTask();
        sendTestTaskToTrello(taskName);
        deleteCrudAppTestTask(taskName);
        assertTrue(checkTaskExistsInTrello(taskName));
    }
}
