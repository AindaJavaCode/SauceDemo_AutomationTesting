package com.example.test;

import com.example.base.BaseTest;
import com.example.pages.InventoryPage;
import com.example.pages.LoginPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest extends BaseTest {

    private static final Dotenv dotenv = Dotenv.load();

    @Test
    void validUserCanLogIn() {
        String username = dotenv.get("SAUCEDEMO_USERNAME");
        String password = dotenv.get("SAUCEDEMO_PASSWORD");

        InventoryPage inventory = new LoginPage(driver).loginAs(username, password);

        assertEquals("Products", inventory.getTitle());
        assertTrue(inventory.getProductCount() > 1);
    }

    @Test
    void lockedOutUserIsRejected() {
        LoginPage login = new LoginPage(driver)
                .loginExpectingFailure("locked_out_user", "secret_sauce");

        assertTrue(login.getErrorMessage().contains("locked out"));
    }

    @ParameterizedTest(name = "[{index}] user=''{0}'' pass=''{1}'' -> {2}")
    @CsvSource({
            "standard_user, wrong_password, do not match",
            "'', secret_sauce, Username is required",
            "standard_user, '', Password is required"
    })
    void invalidCredentialsShowHelpfulError(String user, String pass, String expectedFragment) {
        LoginPage login = new LoginPage(driver).loginExpectingFailure(user, pass);

        assertTrue(login.getErrorMessage().contains(expectedFragment));
    }
}