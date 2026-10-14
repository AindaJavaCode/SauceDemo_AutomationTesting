package com.example.pages;

import com.example.config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class InventoryPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By title     = By.cssSelector(".title");
    private final By products  = By.cssSelector(".inventory_item");
    private final By cartBadge = By.cssSelector(".shopping_cart_badge");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Config.timeoutSeconds()));
    }

    public String getTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(title)).getText();
    }

    public int getProductCount() {
        return driver.findElements(products).size();
    }

    /** e.g. addToCart("sauce-labs-backpack") */
    public InventoryPage addToCart(String productSlug) {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-" + productSlug))).click();
        return this;
    }

    public int getCartCount() {
        List<WebElement> badges = driver.findElements(cartBadge);
        return badges.isEmpty() ? 0 : Integer.parseInt(badges.get(0).getText());
    }
}