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
    private final By productsName  = By.cssSelector(".inventory_item_name");
    private final By cartBadge = By.cssSelector(".shopping_cart_badge");
    private final By addToCartButtons = By.xpath("//button[@class='btn btn_primary btn_small btn_inventory ']");
    private final By removeFromCartButtons = By.xpath("//button[@class='btn btn_secondary btn_small btn_inventory ']");
    private final By cartLabel = By.xpath("//a[@class='shopping_cart_link']");
    private final By removeLabel = By.xpath("//button[@id='remove-sauce-labs-backpack']");
    private final By addToCartPostRemove = By.xpath("//div[@class='inventory_item'][1]/div/div/button[@class='btn btn_primary btn_small btn_inventory ']");

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

    public CartPage clickCart(){

        driver.findElement(cartLabel).click();
        return new CartPage(driver);

    }


    public boolean removeButtonDisplayed(){

        driver.findElements(addToCartButtons).get(0).click();

        try {
            driver.findElement(removeLabel);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public boolean AddToCartAppearsAfterClickingRemove(){

        driver.findElements(addToCartButtons).get(0).click();
        driver.findElement(removeLabel).click();

        try{
            driver.findElement(addToCartPostRemove);
            return true;
        } catch(org.openqa.selenium.NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String addOneProductToCart(){

        String item = driver.findElements(productsName).get(0).getText();
        driver.findElements(addToCartButtons).get(0).click();
        return item;
    }



    public void addAllProductsToCart() {
        int total = driver.findElements(addToCartButtons).size();

        for (int i = 1; i <= total; i++) {
            // re-find every time, so we never hold a stale reference
            driver.findElements(addToCartButtons).get(0).click();
            wait.until(ExpectedConditions.textToBe(cartBadge, String.valueOf(i)));
        }
    }

    public void removeAllProductsFromCart(){
        int total = driver.findElements(removeFromCartButtons).size();

        for(int i = 1; i <= total; i++){
            driver.findElements(removeFromCartButtons).get(0).click();
            if (!driver.findElements(removeFromCartButtons).isEmpty()){
                wait.until(ExpectedConditions.textToBe(cartBadge, String.valueOf(total - i)));
            }
            else{
                wait.until(ExpectedConditions.attributeToBe(cartLabel,  "aria-label", "Cart, empty"));
            }


        }

    }
}