package com.example.base;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.qameta.allure.Allure;
import java.io.ByteArrayInputStream;

import java.nio.file.Files;
import java.nio.file.Path;

public class ScreenshotOnFailure implements TestExecutionExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ScreenshotOnFailure.class);

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable)
            throws Throwable {
        try {
            byte[] png = ((TakesScreenshot) DriverManager.getDriver())
                    .getScreenshotAs(OutputType.BYTES);

            Path dir = Path.of("target", "screenshots");
            Files.createDirectories(dir);

            String fileName = context.getRequiredTestClass().getSimpleName()
                    + "_" + context.getRequiredTestMethod().getName()
                    + "_" + System.currentTimeMillis() + ".png";
            Path file = dir.resolve(fileName);

            Files.write(file, png);
            Allure.attachment("Screenshot on failure", new ByteArrayInputStream(png));
            log.error("Test failed. Screenshot saved to {}", file.toAbsolutePath());
        } catch (Exception e) {
            log.warn("Could not capture screenshot", e);
        }
        throw throwable;   // always rethrow, or the failure would be swallowed
    }
}