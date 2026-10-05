package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import base.BaseTest;
import pages.CartPage;
import pages.CheckoutCompletePage;
import pages.CheckoutInformationPage;
import pages.CheckoutOverviewPage;
import pages.InventoryPage;

class CheckoutTest extends BaseTest {
    private InventoryPage inventoryPage;

    @BeforeEach
    void logIn() {
        inventoryPage = loginAsStandardUser();
    }

    @Test
    @DisplayName("completes checkout with multiple products")
    void completesCheckout() {
        inventoryPage.addItemToCart("Sauce Labs Backpack");
        inventoryPage.addItemToCart("Sauce Labs Bike Light");
        CartPage cartPage = inventoryPage.openCart();
        assertEquals(2, cartPage.getItemNames().size());

        CheckoutInformationPage informationPage = cartPage.checkout();
        CheckoutOverviewPage overviewPage = informationPage
                .enterCustomerInformation("Ada", "Lovelace", "12345")
                .continueToOverview();
        assertTrue(overviewPage.getItemNames().containsAll(
                List.of("Sauce Labs Backpack", "Sauce Labs Bike Light")));

        CheckoutCompletePage completePage = overviewPage.finishOrder();
        assertEquals("Thank you for your order!", completePage.getConfirmationMessage());
    }
}
