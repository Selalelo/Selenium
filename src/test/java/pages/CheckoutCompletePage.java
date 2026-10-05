package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.WaitUtils;

public class CheckoutCompletePage {
    private static final By COMPLETE_HEADER = By.className("complete-header");
    private final WaitUtils wait;

    public CheckoutCompletePage(WebDriver driver) {
        this.wait = new WaitUtils(driver);
        wait.waitForVisibility(COMPLETE_HEADER);
    }

    public String getConfirmationMessage() {
        return wait.waitForVisibility(COMPLETE_HEADER).getText();
    }
}
