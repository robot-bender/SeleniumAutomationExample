package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MoroSystemsCareerPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By cookieNecessaryButton =
            By.xpath("//button[contains(normalize-space(.), 'Pouze nutné')]");

    private final By careerLink =
            By.xpath("//a[contains(normalize-space(.), 'Kariéra')]");

    private final By cityDropdown =
            By.cssSelector(".inp-custom-select__select");

    public MoroSystemsCareerPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void acceptNecessaryCookies() {
        try {
            WebElement button = wait.until(
                    ExpectedConditions.elementToBeClickable(cookieNecessaryButton)
            );

            button.click();

            // Wait until the cookie button disappears.
            wait.until(
                    ExpectedConditions.invisibilityOfElementLocated(
                            cookieNecessaryButton
                    )
            );

        } catch (Exception e) {
            // Cookie dialog was not displayed.
        }
    }

    public void openCareerPage() {

        // Wait until the page has finished loading.
        wait.until(driver ->
                ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );

        // Wait for the career link to exist.
        WebElement link = wait.until(
                ExpectedConditions.presenceOfElementLocated(careerLink)
        );

        // Scroll it into view. This helps when the header is outside
        // the currently visible viewport in headless CI Chrome.
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                link
        );

        // Wait until Selenium considers it clickable.
        wait.until(
                ExpectedConditions.elementToBeClickable(careerLink)
        );

        // Use JavaScript click as a fallback for CI/headless Chrome.
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();",
                link
        );

        wait.until(ExpectedConditions.urlContains("kariera"));
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
