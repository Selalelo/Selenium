package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.WaitUtils;

public class CheckoutOverviewPage {
    private static final By ITEM_NAMES = By.className("inventory_item_name");
    private static final By FINISH_BUTTON = By.id("finish");

    private final WebDriver driver;
    private final WaitUtils wait;

    public CheckoutOverviewPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
        wait.waitForVisibility(FINISH_BUTTON);
    }

    public List<String> getItemNames() {
        return driver.findElements(ITEM_NAMES).stream().map(element -> element.getText()).toList();
    }

    public CheckoutCompletePage finishOrder() {
        wait.waitForClickable(FINISH_BUTTON).click();
        return new CheckoutCompletePage(driver);
    }
}
