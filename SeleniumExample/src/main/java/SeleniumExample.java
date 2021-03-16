import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumExample {

    public static void main(String[] args) {

        WebDriver firefoxDriver = new FirefoxDriver();
        System.setProperty("webfirefoxDriver.gecko.firefoxDriver", "geckofirefoxDriver");

        firefoxDriver.get("https://google.com/ncr");
        firefoxDriver.findElement(By.name("q")).sendKeys("cheese" + Keys.ENTER);
// Initialize and wait till element(link) became clickable - timeout in 10 seconds
        WebElement firstResult = new WebDriverWait(firefoxDriver, 10)
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//a/h3")));
// Print the first result
        System.out.println(firstResult.getText());


       firefoxDriver.get("http://www.google.com");

// Get search box element from webElement 'q' using Find Element
        WebElement searchBox = firefoxDriver.findElement(By.name("q"));

        searchBox.sendKeys("webdriver");

        try {
            // Navigate to Url
            firefoxDriver.get("https://google.com");

            // Enter "webdriver" text and perform "ENTER" keyboard action
            firefoxDriver.findElement(By.name("q")).sendKeys("webdriver" + Keys.ENTER);

            Actions actionProvider = new Actions(firefoxDriver);
            Action keydown = actionProvider.keyDown(Keys.CONTROL).sendKeys("a").build();
            keydown.perform();
        } finally {
            firefoxDriver.quit();
        }


        //firefoxDriver.quit();

    }
}
