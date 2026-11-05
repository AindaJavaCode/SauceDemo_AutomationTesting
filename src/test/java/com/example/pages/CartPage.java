package com.example.pages;

import com.example.config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By yourCart = By.cssSelector(".title");
    private final By product  = By.cssSelector(".inventory_item_name");
    private final By remove = By.xpath("//div[@class='cart_item']/div/div/button[@class='btn btn_secondary btn_small cart_button']");
    private final By cartItem = By.xpath("//div[@class='cart_item']");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Config.timeoutSeconds()));
    }


    public String getTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(yourCart)).getText();
    }


    public String inspectBasket(){

        return driver.findElement(product).getText();
    }

    public void clickRemove(){
        driver.findElement(remove).click();
    }

    public boolean isItemRemoved(){
        return driver.findElements(cartItem).isEmpty();
    }

}
