package pages;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import utils.WaitUtils;

public class InventoryPage {
    private static final By PRODUCT_NAMES = By.className("inventory_item_name");
    private static final By PRODUCT_PRICES = By.className("inventory_item_price");
    private static final By PRODUCT_CARDS = By.className("inventory_item");
    private static final By SORT_SELECT = By.className("product_sort_container");
    private static final By CART_BADGE = By.className("shopping_cart_badge");
    private static final By CART_LINK = By.className("shopping_cart_link");

    private final WebDriver driver;
    private final WaitUtils wait;

    public enum SortOrder {
        NAME_A_TO_Z("az"),
        NAME_Z_TO_A("za"),
        PRICE_LOW_TO_HIGH("lohi"),
        PRICE_HIGH_TO_LOW("hilo");

        private final String value;

        SortOrder(String value) {
            this.value = value;
        }
    }

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
        wait.waitForPresence(PRODUCT_NAMES);
    }

    public void sortBy(SortOrder order) {
        new Select(wait.waitForVisibility(SORT_SELECT)).selectByValue(order.value);
    }

    public List<String> getProductNames() {
        wait.waitForPresence(PRODUCT_NAMES);
        return driver.findElements(PRODUCT_NAMES).stream().map(WebElement::getText).toList();
    }

    public List<BigDecimal> getProductPrices() {
        return driver.findElements(PRODUCT_PRICES).stream()
                .map(WebElement::getText)
                .map(price -> new BigDecimal(price.replace("$", "").trim()))
                .toList();
    }

    public void addItemToCart(String productName) {
        WebElement card = findProductCard(productName);
        card.findElement(By.className("btn_inventory")).click();
    }

    public int getCartItemCount() {
        List<WebElement> badges = driver.findElements(CART_BADGE);
        return badges.isEmpty() ? 0 : Integer.parseInt(badges.getFirst().getText());
    }

    public CartPage openCart() {
        wait.waitForClickable(CART_LINK).click();
        return new CartPage(driver);
    }

    private WebElement findProductCard(String productName) {
        return driver.findElements(PRODUCT_CARDS).stream()
                .filter(card -> card.findElement(PRODUCT_NAMES).getText().equals(productName))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Product not found: " + productName));
    }
}
