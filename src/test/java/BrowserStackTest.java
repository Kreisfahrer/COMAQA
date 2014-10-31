import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class BrowserStackTest {

    public static final String USERNAME = "kreisfahrer1";
    public static final String AUTOMATE_KEY = "fszZysBLtMxU8zuqZs7z";
    public static final String URL = "http://" + USERNAME + ":" + AUTOMATE_KEY + "@hub.browserstack.com/wd/hub";
    private WebDriver driver;

    @BeforeMethod
    public void setUp() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browser", "IE");
        caps.setCapability("browser_version", "7.0");
        caps.setCapability("os", "Windows");
        caps.setCapability("os_version", "XP");
        caps.setCapability("browserstack.debug", "true");

        driver = new RemoteWebDriver(new URL(URL), caps);
    }

    @Test
    public void exampleTest() throws InterruptedException {
        driver.get("http://comaqa.by/");
        WebElement element = driver.findElement(By.id("menu-comaqamenu")).findElement(By.cssSelector("li"));
        element.click();
        Thread.sleep(3000);
        Assert.assertTrue(driver.getCurrentUrl().contains("comaqa.by/category/news"));
        System.out.println(driver.getTitle());
        driver.quit();
    }
}
