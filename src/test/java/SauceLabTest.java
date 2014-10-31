import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.testng.SauceOnDemandAuthenticationProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.URL;

public class SauceLabTest implements SauceOnDemandSessionIdProvider, SauceOnDemandAuthenticationProvider {
    private static final String DEFAULT_URL = "http://www.landsend.com/";
    private WebDriver driver;
    public SauceOnDemandAuthentication authentication;
    private ThreadLocal<String> sessionId;

    @Override
    public SauceOnDemandAuthentication getAuthentication() {
        return authentication;
    }

    @Override
    public String getSessionId() {
        return sessionId.get();
    }

    @Parameters({"username", "key", "os", "browser", "browserVersion"})
    @BeforeMethod
    public void setup(@Optional("kreisfahrer") String username,
                      @Optional("c1310746-39bf-49dd-be15-203c85850b98") String key,
                      @Optional("Windows 7") String os,
                      @Optional("firefox") String browser,
                      @Optional("29") String browserVersion, Method method) throws Exception {
        authentication = new SauceOnDemandAuthentication(username, key);
        sessionId = new ThreadLocal<>();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PLATFORM, os);
        capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
        capabilities.setCapability(CapabilityType.VERSION, browserVersion);
        capabilities.setCapability("name", method.getName());
        driver = new RemoteWebDriver(
                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand" +
                        ".saucelabs.com:80/wd/hub"), capabilities);
        sessionId.set(((RemoteWebDriver) driver).getSessionId().toString());
    }

    @Test
    public void exampleTest() throws InterruptedException {
        driver.get("http://comaqa.by/");
        WebElement element = driver.findElement(By.id("menu-comaqamenu")).findElement(By.cssSelector("li"));
        element.click();
        System.out.println(driver.getTitle());
        driver.quit();
    }
}
