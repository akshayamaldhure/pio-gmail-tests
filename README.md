# Gmail tests

## About

This project contains UI test(s) for Gmail. The project is based on Java and Selenium WebDriver.

## Tech stack
Java
TestNG
Gradle
Selenium WebDriver

## Requirements
Java 17
Gradle 7.x
Chrome browser

## Running the tests
In order to run the tests, valid Gmail credentials must be supplied with the environment variables `GMAIL_ADDRESS` and `GMAIL_PASSWORD`.

```
GMAIL_ADDRESS=<gmail_address_here> GMAIL_PASSWORD=<gmail_password_here> make run-tests
```

## Assumptions
Since the tests have been authored against a third-party email service (Gmail), they have been written with below assumptions/pre-requisites.
1. The login flow consists of only the email address and password entry screens. No other screens (like login from an unknown device/location, set up 2FA etc) are encountered while running the tests.
2. UI tests usually rely on the element locators (like XPath, CSS selector, accessibility ID etc) for interacting with various UI elements encountered during the flow. Most if not all elements defined in this test suite use XPath. So the assumption is that none of the XPaths we use are changed by Gmail developers.