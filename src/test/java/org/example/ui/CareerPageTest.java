package org.example.ui;

import org.example.pages.MoroSystemsCareerPage;
import org.example.utils.DriverFactory;
import org.example.utils.ScreenshotExtension;
import org.example.utils.VisualComparisonUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ScreenshotExtension.class)
public class CareerPageTest {

    private WebDriver driver;
    private MoroSystemsCareerPage careerPage;

    private static final String CAREER_URL =
            "https://www.morosystems.cz/";

    @BeforeEach
    void setUp() {

        driver = DriverFactory.createDriver("chrome");

        driver.manage().window().maximize();

        ScreenshotExtension.setDriver(driver);

        careerPage = new MoroSystemsCareerPage(driver);
    }

    @AfterEach
    void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void careerPageLoads() {

        driver.get(CAREER_URL);

        String title = driver.getTitle();

        System.out.println("Page title: " + title);

        assertTrue(
                title != null && !title.isBlank(),
                "Career page should have a title"
        );
    }

    @Test
    void searchAndFilterMoroSystemsJobs() {

        // Open MoroSystems
        driver.get(CAREER_URL);

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

//    @Test
//    void careerPageLoadsNegativeTest() {
//
//        driver.get(CAREER_URL);
//
//        assertTrue(false);
//    }

    @Disabled("Visual regression is intentionally disabled in CI")
    @Test
    void careerPageVisualTest() throws IOException {

        driver.get(CAREER_URL);

        careerPage.acceptNecessaryCookies();

        File screenshot =
                ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.FILE);

        Path actualPath =
                Paths.get("target/screenshots/career-page.png");

        Files.createDirectories(actualPath.getParent());

        Files.copy(
                screenshot.toPath(),
                actualPath,
                StandardCopyOption.REPLACE_EXISTING
        );

        boolean matches =
                VisualComparisonUtil.compareImages(
                        "src/test/resources/baselines/career-page.png",
                        actualPath.toString(),
                        0.01
                );

        assertTrue(
                matches,
                "Current page differs from the visual baseline"
        );
    }
}
