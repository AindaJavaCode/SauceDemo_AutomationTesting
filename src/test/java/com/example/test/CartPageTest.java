package com.example.test;

import com.example.base.BaseTest;
import com.example.pages.CartPage;
import com.example.pages.InventoryPage;
import com.example.pages.LoginPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Test;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

public class CartPageTest extends BaseTest {

    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
    private CartPage cartPage;
    private InventoryPage inventoryPage;

    @BeforeEach
    void Login(){
        String username = dotenv.get("SAUCEDEMO_USERNAME");
        String password = dotenv.get("SAUCEDEMO_PASSWORD");

        inventoryPage = new LoginPage(driver).loginAs(username, password);
    }

    @Test
    void addToCartAndVerifyItem(){

        String productPageItem = inventoryPage.addOneProductToCart();
        cartPage = inventoryPage.clickCart();
        String cartItem = cartPage.inspectBasket();

        assertEquals(productPageItem, cartItem);

    }

    @Test
    void removeProductFromCart(){

      inventoryPage.addOneProductToCart();
      cartPage = inventoryPage.clickCart();
      cartPage.inspectBasket();
      cartPage.clickRemove();

      assertTrue(cartPage.isItemRemoved());

    }




}
