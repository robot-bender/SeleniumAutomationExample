package org.example.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VisualComparisonUtil {

    public static boolean compareImages(
            String baselinePath,
            String actualPath,
            double tolerance
    ) throws IOException {

        BufferedImage baseline =
                ImageIO.read(new File(baselinePath));

        BufferedImage actual =
                ImageIO.read(new File(actualPath));

        if (baseline.getWidth() != actual.getWidth()
                || baseline.getHeight() != actual.getHeight()) {
            return false;
        }

        long differentPixels = 0;
        long totalPixels =
                (long) baseline.getWidth() * baseline.getHeight();

        for (int y = 0; y < baseline.getHeight(); y++) {

            for (int x = 0; x < baseline.getWidth(); x++) {

                int baselinePixel =
                        baseline.getRGB(x, y);

                int actualPixel =
                        actual.getRGB(x, y);

                if (baselinePixel != actualPixel) {
                    differentPixels++;
                }
            }
        }

        double difference =
                (double) differentPixels / totalPixels;

        return difference <= tolerance;
    }
}
