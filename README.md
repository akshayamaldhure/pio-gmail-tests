# Gmail tests

## About

This project contains UI test(s) for Gmail. It is based on Java and Selenium WebDriver.

## Tech stack
- Java
- Selenium WebDriver
- TestNG
- Gradle
- Lombok
- Logback

## System requirements
- Linux (Ubuntu) or macOS
- Java 17
- Gradle 7.x
- Chrome browser (latest version as of September 2024 should work without any issues)

## Running the tests
In order to run the tests locally, valid Gmail credentials must be supplied with the environment variables `GMAIL_ADDRESS`, `GMAIL_PASSWORD` and `GMAIL_SECRET`.

```
GMAIL_ADDRESS=<gmail_address_here> GMAIL_PASSWORD=<gmail_password_here> GMAIL_SECRET=<gmail_authenticator_secret_here> make run-tests
```

## Technical details
- `SendEmailTests.java` is the entrypoint to the test.
- The core implementation uses the popular [Page Object Model](https://www.selenium.dev/documentation/test_practices/encouraged/page_object_models/) design pattern with [PageFactory](https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/support/PageFactory.html) to make it easy to maintain various web pages and elements.
- It has been developed with easy extensibility and maintainability into consideration.
- With separate page classes for each webpage in the application, it is easy to maintain and reuse the code across different tests and flows.
- Separate base classes for initialising the tests and page objects make it easy to extend the solution to add more tests in the future.
- Utility classes make it easy to reuse the common flows like login, waiting for an email to be received, etc. across multiple tests.
- The use of Lombok library makes it easy to eliminate the need to define the methods for common operations like clicking, sending text, etc. done to the UI elements.

## Assumptions
Since the tests have been authored against a third-party email service (Gmail), they have been written with below assumptions/pre-requisites.
1. The login flow consists of only the email address, password and six-digit authenticator code entry screens. No other screens (like login from an unknown device/location, suspicious login attempt etc.) are expected to be encountered while running the tests. While running the tests locally, if you encounter any unexpected verification screen(s), you may try by logging into the Gmail account manually in a separate Chrome session and then retry the tests.
2. UI tests usually rely on the element locators (like XPath, CSS selector, accessibility ID etc.) for interacting with various UI elements in the test flows. Most if not all elements defined in this test suite use XPath. So the assumption is that none of attributes pertaining to the XPaths we use in this project are changed by Gmail developers.