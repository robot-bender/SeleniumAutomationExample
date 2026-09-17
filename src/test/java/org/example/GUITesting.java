package org.example;

import org.example.pages.MoroSystemsCareerPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GUITesting {

    private WebDriver driver;
    private MoroSystemsCareerPage careerPage;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        careerPage = new MoroSystemsCareerPage(driver);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void searchAndFilterMoroSystemsJobs() {

        // Open MoroSystems
        driver.get("https://www.morosystems.cz/");

        // Handle cookies
        careerPage.acceptNecessaryCookies();

        // Open Kariéra
        careerPage.openCareerPage();

        // Select preferred city
        String preferredCity = "Brno";
        careerPage.selectCity(preferredCity);

        // Verify city is selected
        assertTrue(
                careerPage.isCitySelected(preferredCity),
                preferredCity + " should be selected"
        );
    }
}
