package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class BasePage {

    private static final Logger LOG = LoggerFactory.getLogger(BasePage.class);

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        LOG.info("Setting up Selenium WebDriver and configuring timeouts");
        this.driver = driver;
        this.driver.manage().window().maximize();
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // implicit wait, applies to every element to be searched for
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // explicit wait, applies to a given element conditionally
    }
}
