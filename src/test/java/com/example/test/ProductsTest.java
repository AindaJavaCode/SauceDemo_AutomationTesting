package com.example.test;

import com.example.base.BaseTest;
import com.example.pages.InventoryPage;
import com.example.pages.LoginPage;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ProductsTest extends BaseTest {

    private static final Dotenv dotenv = Dotenv.load();
    private InventoryPage inventory;

    @BeforeEach
    void Login(){
        String username = dotenv.get("SAUCEDEMO_USERNAME");
        String password = dotenv.get("SAUCEDEMO_PASSWORD");

        inventory = new LoginPage(driver).loginAs(username, password);
    }

    @Test
    void canAddAllProductsToCart(){

        inventory.addAllProductsToCart();

        assertEquals(inventory.getProductCount(), inventory.getCartCount());

    }

    @Test
    void canRemoveAllProductsFromCart(){
        canAddAllProductsToCart();
        inventory.removeAllProductsFromCart();

        assertEquals(inventory.getCartCount(), 0);


    }


}
