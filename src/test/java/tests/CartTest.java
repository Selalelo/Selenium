package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import base.BaseTest;
import pages.CartPage;
import pages.InventoryPage;

class CartTest extends BaseTest {
    private InventoryPage inventoryPage;

    @BeforeEach
    void logIn() {
        inventoryPage = loginAsStandardUser();
    }

    @Test
    @DisplayName("adds selected products to the cart")
    void addsProductsToCart() {
        inventoryPage.addItemToCart("Sauce Labs Backpack");
        inventoryPage.addItemToCart("Sauce Labs Bike Light");

        assertEquals(2, inventoryPage.getCartItemCount());
        CartPage cartPage = inventoryPage.openCart();
        assertTrue(cartPage.getItemNames().containsAll(
                List.of("Sauce Labs Backpack", "Sauce Labs Bike Light")));
    }

    @Test
    @DisplayName("removes a product from the cart")
    void removesProductFromCart() {
        inventoryPage.addItemToCart("Sauce Labs Backpack");
        CartPage cartPage = inventoryPage.openCart();
        cartPage.removeItem("Sauce Labs Backpack");

        assertTrue(cartPage.getItemNames().isEmpty());
    }
}
