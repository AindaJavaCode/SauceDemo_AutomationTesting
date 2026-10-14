package com.example.base;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.HashMap;
import java.util.Map;

public final class DriverFactory {

    private DriverFactory() { }

    public static WebDriver create(String browser, boolean headless) {
        WebDriver driver = switch (browser.toLowerCase()) {
            case "chrome"  -> createChrome(headless);
            case "firefox" -> createFirefox(headless);
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        };
        driver.manage().window().setSize(new Dimension(1280, 900));
        return driver;
    }

    private static WebDriver createChrome(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new");
        }
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        options.setExperimentalOption("prefs", prefs);
        return new ChromeDriver(options);
    }

    private static WebDriver createFirefox(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        if (headless) {
            options.addArguments("-headless");
        }
        options.addPreference("signon.rememberSignons", false);
        options.addPreference("signon.autofillForms", false);
        options.addPreference("signon.management.page.breach-alerts.enabled", false);
        return new FirefoxDriver(options);
    }
}