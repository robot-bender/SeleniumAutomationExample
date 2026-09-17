package org.example.utils;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;

public class ScreenshotExtension implements AfterTestExecutionCallback {

    private static WebDriver driver;

    public static void setDriver(WebDriver webDriver) {
        driver = webDriver;
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {

        if (context.getExecutionException().isPresent()
                && driver != null) {

            String testName = context.getDisplayName()
                    .replaceAll("[^a-zA-Z0-9.-]", "_");

            String fileName = testName + "_"
                    + System.currentTimeMillis();

            System.out.println(
                    "Test failed - taking screenshot..."
            );

            ScreenshotUtil.takeScreenshot(
                    driver,
                    fileName
            );
        }
    }
}