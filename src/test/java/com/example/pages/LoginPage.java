package com.example.pages;

import com.example.config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage {

    private static final Logger log = LoggerFactory.getLogger(LoginPage.class);
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton   = By.id("login-button");
    private final By errorMessage  = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Config.timeoutSeconds()));
    }

    /** Happy path: returns the page you land on. */
    public InventoryPage loginAs(String username, String password) {
        log.info("Logging in as '{}'", username);
        submit(username, password);
        return new InventoryPage(driver);
    }

    /** Unhappy path: you stay on the login page. */
    public LoginPage loginExpectingFailure(String username, String password) {
        submit(username, password);
        return this;
    }

    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
    }

    private void submit(String username, String password) {
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
    }
}