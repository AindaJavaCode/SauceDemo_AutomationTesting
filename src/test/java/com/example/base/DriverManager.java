package com.example.base;

import com.example.config.Config;
import org.openqa.selenium.WebDriver;

public final class DriverManager {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverManager() { }

    public static void start() {
        DRIVER.set(DriverFactory.create(Config.browser(), Config.headless()));
    }

    public static WebDriver getDriver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("No WebDriver for this thread. Was start() called?");
        }
        return driver;
    }

    public static void quit() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }
}