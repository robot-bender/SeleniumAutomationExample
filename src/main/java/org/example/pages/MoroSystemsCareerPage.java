package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MoroSystemsCareerPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By cookieNecessaryButton =
            By.xpath("//button[contains(normalize-space(.), 'Pouze nutné')]");

//    private final By careerLink =
//            By.xpath("//a[contains(normalize-space(.), 'Kariéra')]");

//    private final By careerLink = By.xpath(
//            "//a[contains(normalize-space(.), 'Kariéra') " +
//                    "and not(ancestor-or-self::*[contains(@style, 'display: none')])]"
//    );

    private final By careerLink =
            By.cssSelector("a[href*='/kariera']");

    private final By cityDropdown =
            By.cssSelector(".inp-custom-select__select");

    public MoroSystemsCareerPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

//    public void acceptNecessaryCookies() {
//        try {
//            WebElement button = wait.until(
//                    ExpectedConditions.elementToBeClickable(cookieNecessaryButton)
//            );
//
//            button.click();
//
//            // Wait until the cookie button disappears.
//            wait.until(
//                    ExpectedConditions.invisibilityOfElementLocated(
//                            cookieNecessaryButton
//                    )
//            );
//
//        } catch (Exception e) {
//            // Cookie dialog was not displayed.
//        }
//    }

    public void acceptNecessaryCookies() {
        try {
            WebElement button = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(
                            cookieNecessaryButton
                    ));

            button.click();

            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.invisibilityOfElementLocated(
                            cookieNecessaryButton
                    ));

        } catch (org.openqa.selenium.TimeoutException e) {
            // Cookie dialog was not displayed.
        }
    }

    public void openCareerPage() {

        wait.until(driver ->
                ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );

        WebElement link = wait.until(
                ExpectedConditions.presenceOfElementLocated(careerLink)
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                link
        );

        // Give the page/header a moment to finish any animations.
        wait.until(driver ->
                ((JavascriptExecutor) driver).executeScript(
                        "return arguments[0].offsetParent !== null;",
                        link
                ).equals(true)
        );

        // Click the exact element we already found.
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();",
                link
        );

        wait.until(ExpectedConditions.urlContains("/kariera"));
    }


    public void selectCity(String city) {

        WebElement dropdown = wait.until(
                ExpectedConditions.elementToBeClickable(cityDropdown)
        );

        dropdown.click();

        By cityOptionLocator =
                By.xpath("//label[@data-filter='" + city + "']");

        WebElement cityOption = wait.until(
                ExpectedConditions.elementToBeClickable(cityOptionLocator)
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                cityOption
        );

        cityOption.click();
    }

    public boolean isCitySelected(String city) {

        By cityOptionLocator =
                By.xpath("//label[@data-filter='" + city + "']");

        WebElement cityOption = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        cityOptionLocator
                )
        );

        WebElement radioButton = cityOption.findElement(
                By.cssSelector("input[type='radio']")
        );

        return radioButton.isSelected();
    }
}
