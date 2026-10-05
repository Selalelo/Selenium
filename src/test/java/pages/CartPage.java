package pages;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.WaitUtils;

public class CartPage {
    private static final By CART_ITEMS = By.className("cart_item");
    private static final By ITEM_NAMES = By.className("inventory_item_name");
    private static final By CHECKOUT_BUTTON = By.id("checkout");
    private static final By CONTINUE_SHOPPING_BUTTON = By.id("continue-shopping");

    private final WebDriver driver;
    private final WaitUtils wait;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
        wait.waitForVisibility(CHECKOUT_BUTTON);
    }

    public List<String> getItemNames() {
        return driver.findElements(CART_ITEMS).stream()
                .map(item -> item.findElement(ITEM_NAMES).getText())
                .toList();
    }

    public void removeItem(String productName) {
        WebElement item = driver.findElements(CART_ITEMS).stream()
                .filter(row -> row.findElement(ITEM_NAMES).getText().equals(productName))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Cart item not found: " + productName));
        item.findElement(By.tagName("button")).click();
    }

    public InventoryPage continueShopping() {
        wait.waitForClickable(CONTINUE_SHOPPING_BUTTON).click();
        return new InventoryPage(driver);
    }

    public CheckoutInformationPage checkout() {
        wait.waitForClickable(CHECKOUT_BUTTON).click();
        return new CheckoutInformationPage(driver);
    }
}
