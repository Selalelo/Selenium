package base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.TakesScreenshot;
import pages.LoginPage;
import pages.InventoryPage;
import utils.TestConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class BaseTest {
    protected WebDriver driver;

    @RegisterExtension
    protected final TestExecutionExceptionHandler screenshotOnFailure = new TestExecutionExceptionHandler() {
        @Override
        public void handleTestExecutionException(ExtensionContext context, Throwable cause) throws Throwable {
            captureScreenshot(context);
            throw cause;
        }
    };

    @BeforeEach
    public void startUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(TestConfig.getBaseUrl());
    }

    protected InventoryPage loginAsStandardUser() {
        new LoginPage(driver).login("standard_user", "secret_sauce");
        return new InventoryPage(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void captureScreenshot(ExtensionContext context) {
        if (driver == null || !(driver instanceof TakesScreenshot screenshotDriver)) {
            return;
        }

        String testName = context.getRequiredTestClass().getSimpleName() + "-"
                + context.getRequiredTestMethod().getName();
        Path screenshotPath = Path.of("target", "screenshots",
                testName + "-" + System.currentTimeMillis() + ".png");
        try {
            Files.createDirectories(screenshotPath.getParent());
            Files.write(screenshotPath, screenshotDriver.getScreenshotAs(OutputType.BYTES));
            System.err.println("Saved failure screenshot: " + screenshotPath.toAbsolutePath());
        } catch (IOException | RuntimeException screenshotFailure) {
            System.err.println("Could not save failure screenshot: " + screenshotFailure.getMessage());
        }
    }
}
