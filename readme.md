# SauceDemo Selenium Test Automation

A Java test automation study project for SauceDemo, using Selenium WebDriver, JUnit 5, Maven, and the Page Object Model.

## Current test coverage

The automated suite currently contains these scenarios:

- Log in with `standard_user` and check that the inventory page opens.
- Try `locked_out_user` and check that the locked account error appears.
- Sort inventory products alphabetically from A to Z and by price low to high.
- Add products to the cart and check the cart contents; remove a product and check that the cart is empty.
- Complete checkout with two products and check the order confirmation.
- Save a screenshot when a test method fails. Screenshots go to `target/screenshots/`.

These are the scenarios implemented in the test code. Run the suite with the command below to check them in your environment.

## Project structure

```text
src/test/java/
  base/
    BaseTest.java                     Browser setup, teardown, failure screenshots
  pages/
    LoginPage.java                    Login actions and error message
    InventoryPage.java                Product listing, sorting, cart actions
    CartPage.java                     Cart contents, removal, checkout
    CheckoutInformationPage.java      Customer details form
    CheckoutOverviewPage.java         Order review and finish action
    CheckoutCompletePage.java         Order confirmation
  tests/
    LoginTest.java
    InventoryTest.java
    CartTest.java
    CheckoutTest.java
  utils/
    TestConfig.java                   Loads test configuration
    WaitUtils.java                    Shared explicit wait helpers
src/test/resources/
  config.properties                   Base URL and wait timeout
```

Page objects keep selectors and page actions out of the test scenarios. Shared browser setup and teardown live in `BaseTest`; `WaitUtils` provides explicit waits without fixed sleeps.

## Configuration

The defaults are in `src/test/resources/config.properties`:

```properties
base.url=https://www.saucedemo.com/
wait.timeout.seconds=10
```

Override them for a Maven run with system properties:

```shell
mvn test -Dbase.url=https://www.saucedemo.com/ -Dwait.timeout.seconds=15
```

## Requirements and running

- JDK 21
- Maven
- Chrome

Run the automated suite from this directory:

```shell
mvn test
```

Selenium Manager resolves ChromeDriver when needed. Maven build output, including failure screenshots, is under `target/` and ignored by Git.
