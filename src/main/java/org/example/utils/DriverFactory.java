package org.example.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

public class DriverFactory {

    public static WebDriver createDriver(String browser) {

        return switch (browser.toLowerCase()) {

            case "chrome" -> createChromeDriver();

            case "firefox" -> createFirefoxDriver();

            case "edge" -> new EdgeDriver();

            case "safari" -> new SafariDriver();

            default -> throw new IllegalArgumentException(
                    "Unsupported browser: " + browser
            );
        };
    }

    private static WebDriver createChromeDriver() {

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--window-size=1920,1080");

        // GitHub Actions / CI
        if (System.getenv("CI") != null) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
        }

        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver() {

        FirefoxOptions options = new FirefoxOptions();

        if (System.getenv("CI") != null) {
            options.addArguments("--headless");
            options.addArguments("--width=1920");
            options.addArguments("--height=1080");
        }

        return new FirefoxDriver(options);
    }
}
