package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import base.BaseTest;
import pages.InventoryPage;

class InventoryTest extends BaseTest {
    private InventoryPage inventoryPage;

    @BeforeEach
    void logIn() {
        inventoryPage = loginAsStandardUser();
    }

    @Test
    @DisplayName("sorts inventory names from A to Z")
    void sortsNamesAscending() {
        inventoryPage.sortBy(InventoryPage.SortOrder.NAME_A_TO_Z);
        List<String> actualNames = inventoryPage.getProductNames();
        List<String> sortedNames = actualNames.stream().sorted(String.CASE_INSENSITIVE_ORDER).toList();
        assertEquals(sortedNames, actualNames);
    }

    @Test
    @DisplayName("sorts inventory prices from low to high")
    void sortsPricesAscending() {
        inventoryPage.sortBy(InventoryPage.SortOrder.PRICE_LOW_TO_HIGH);
        List<java.math.BigDecimal> prices = inventoryPage.getProductPrices();
        assertEquals(prices.stream().sorted().toList(), prices);
    }
}
