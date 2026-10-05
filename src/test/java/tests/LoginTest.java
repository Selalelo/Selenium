package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;

import base.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import utils.TestConfig;

public class LoginTest extends BaseTest {
    private static final String USERNAME = "standard_user";
    private static final String INVALID_USERNAME = "locked_out_user";
    private static final String PASSWORD = "secret_sauce";
    private LoginPage loginPage;

    @BeforeEach
    public void initPage() {
        loginPage = new LoginPage(driver);
    }

    @Nested
    @DisplayName("Successful login")
    class SuccessfulLogin {
        @Test
        @DisplayName("valid login loads inventory page")
        public void testValidLogin() {
            loginPage.login(USERNAME, PASSWORD);
            String expectedInventoryUrl = URI.create(TestConfig.getBaseUrl()).resolve("inventory.html").toString();
            assertEquals(expectedInventoryUrl, driver.getCurrentUrl());
        }
    }

    @Nested
    @DisplayName("Unsuccessful login")
    class UnsuccessfulLogin {
        @Test
        @DisplayName("Locked out user sees error message")
        public void testInvalidLogin() {
            loginPage.login(INVALID_USERNAME, PASSWORD);
            assertTrue(loginPage.getErrorMessage().contains("Sorry, this user has been locked out"));
        }
    }
}


