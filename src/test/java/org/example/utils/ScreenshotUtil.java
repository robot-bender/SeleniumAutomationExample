package org.example.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ScreenshotUtil {

    public static void takeScreenshot(
            WebDriver driver,
            String fileName
    ) {

        File screenshot =
                ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.FILE);

        Path destination = Path.of(
                "target",
                "screenshots",
                fileName + ".png"
        );

        try {

            Files.createDirectories(destination.getParent());

            Files.copy(
                    screenshot.toPath(),
                    destination
            );

            System.out.println(
                    "Screenshot saved: "
                            + destination.toAbsolutePath()
            );

        } catch (IOException e) {

            throw new RuntimeException(
                    "Could not save screenshot",
                    e
            );
        }
    }
}
