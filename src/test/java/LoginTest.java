import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class LoginTest {

    private WebDriver driver;
    private String browser;

    // Constructor to accept the browser name
    public LoginTest(String browser) {
        this.browser = browser;
    }

    public void setUp() throws InterruptedException {
        System.out.println("Setting up the browser: " + browser);

        if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        driver.get("http://127.0.0.1:5500/index.html");
        Thread.sleep(1000); // 1 saniye bekleme
    }

    @Test
    public void testValidLogin() throws InterruptedException {
        setUp();
        System.out.println("Running test: testValidLogin on browser: " + browser);

        driver.findElement(By.id("username")).sendKeys("admin");
        Thread.sleep(1000); // 1 saniye bekleme
        driver.findElement(By.id("password")).sendKeys("admin123");
        Thread.sleep(1000); // 1 saniye bekleme
        driver.findElement(By.xpath("/html/body/div/button")).click();
        Thread.sleep(1000); // 1 saniye bekleme
        Assert.assertTrue(driver.getPageSource().contains("Giriş Başarılı!"), "Login failed!");
    }

    @Test
    public void testInvalidLogin() throws InterruptedException {
        setUp();
        System.out.println("Running test: testInvalidLogin on browser: " + browser);

        driver.findElement(By.id("username")).sendKeys("wrong_username");
        Thread.sleep(1000); // 1 saniye bekleme
        driver.findElement(By.id("password")).sendKeys("wrong_password");
        Thread.sleep(1000); // 1 saniye bekleme
        driver.findElement(By.xpath("/html/body/div/button")).click();
        Thread.sleep(1000); // 1 saniye bekleme
        Assert.assertTrue(driver.getPageSource().contains("Hatalı Giriş!"), "Expected error message not displayed!");
    }

    @AfterMethod
    public void tearDown() {
        System.out.println("Tearing down the browser: " + browser);
        if (driver != null) {
            driver.quit();
        }
    }
}