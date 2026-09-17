package org.example.pages;

import org.openqa.selenium.By;
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
            By.xpath("//button[contains(., 'Pouze nutné')]");

    private final By careerLink =
            By.xpath("//a[contains(., 'Kariéra')]");

    private final By cityDropdown =
            By.cssSelector(".inp-custom-select__select");

    public MoroSystemsCareerPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void acceptNecessaryCookies() {
        try {
            WebElement button = wait.until(
                    ExpectedConditions.elementToBeClickable(cookieNecessaryButton)
            );

            button.click();

        } catch (Exception e) {
            // Cookie dialog was not displayed.
        }
    }

    public void openCareerPage() {
        WebElement link = wait.until(
                ExpectedConditions.elementToBeClickable(careerLink)
        );

        link.click();

        wait.until(ExpectedConditions.urlContains("kariera"));
    }

    public void selectCity(String city) {

        // Open city dropdown
        WebElement dropdown = wait.until(
                ExpectedConditions.elementToBeClickable(cityDropdown)
        );

        dropdown.click();

        // Find city option
        WebElement cityOption = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//label[@data-filter='" + city + "']")
                )
        );

        // Select city
        cityOption.click();
    }

    public boolean isCitySelected(String city) {

        WebElement cityOption = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//label[@data-filter='" + city + "']")
                )
        );

        WebElement radioButton = cityOption.findElement(
                By.cssSelector("input[type='radio']")
        );

        return radioButton.isSelected();
    }
}
