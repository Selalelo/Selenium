package utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtils {
    private final WebDriverWait wait;

    public WaitUtils(WebDriver driver){
        this(driver, TestConfig.getWaitTimeoutSeconds());
    }

    public WaitUtils(WebDriver driver, int customTimeoutSeconds){
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(customTimeoutSeconds));
    }

    public WebElement waitForVisibility(By locator){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public boolean waitForInvisibility(By locator){
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public WebElement waitForClickable(By locator){
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public boolean waitForTextToBePresent(By locator, String text){
        return wait.until(ExpectedConditions.textToBePresentInElementValue(locator, text));
    }

    public WebElement waitForPresence(By locator){
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
}
