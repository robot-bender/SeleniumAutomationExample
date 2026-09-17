package org.example.responsive;

import org.example.utils.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResponsiveTest {

    private WebDriver driver;

    private static final String WEBSITE_URL =
            "https://www.morosystems.cz/";

    @BeforeEach
    void setUp() {
        driver = DriverFactory.createDriver("chrome");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void websiteDisplaysCorrectlyOnDifferentResolutions() {

        testResolution(1920, 1080);
        testResolution(1366, 768);
        testResolution(768, 1024);
        testResolution(375, 667);
    }

    private void testResolution(int width, int height) {

        driver.manage().window().setSize(
                new Dimension(width, height)
        );

        driver.get(WEBSITE_URL);

        WebElement body = driver.findElement(By.tagName("body"));

        assertTrue(
                body.isDisplayed(),
                "Page should be displayed at "
                        + width + "x" + height
        );

        System.out.println(
                "Tested resolution: "
                        + width + "x" + height
        );
    }
}
