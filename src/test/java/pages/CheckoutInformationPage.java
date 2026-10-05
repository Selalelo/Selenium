package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.WaitUtils;

public class CheckoutInformationPage {
    private static final By FIRST_NAME = By.id("first-name");
    private static final By LAST_NAME = By.id("last-name");
    private static final By POSTAL_CODE = By.id("postal-code");
    private static final By CONTINUE_BUTTON = By.id("continue");

    private final WebDriver driver;
    private final WaitUtils wait;

    public CheckoutInformationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
        wait.waitForVisibility(FIRST_NAME);
    }

    public CheckoutInformationPage enterCustomerInformation(String firstName, String lastName, String postalCode) {
        wait.waitForVisibility(FIRST_NAME).sendKeys(firstName);
        driver.findElement(LAST_NAME).sendKeys(lastName);
        driver.findElement(POSTAL_CODE).sendKeys(postalCode);
        return this;
    }

    public CheckoutOverviewPage continueToOverview() {
        wait.waitForClickable(CONTINUE_BUTTON).click();
        return new CheckoutOverviewPage(driver);
    }
}
