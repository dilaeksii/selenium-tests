
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.testng.Assert.assertTrue;


public class FirstTest {
    WebDriver driver; //Tarayıcıyı kontrol eden Selenium arayüzüdür. Chrome'u açar, siteye gider..
    WebDriverWait wait; //Bir koşul gerçekleşene kadar beklemek için kullanılır, Thread.sleep yerine kullanılan akıllı bekleme

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup(); //Chrome Driver nerede? Hangi sürüm? Mac mi Windows mu? Chrome için uygun driver’ı otomatik bulur/ayarlar
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20)); //İstenilen koşulun gerçekleşmesi için en fazla 20 saniye bekleyebilirsin

        driver.get("https://www.amazon.com");
    }

    @Test
    void searchLaptopTest() {
        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.id("twotabsearchtextbox")));

        searchBox.click();
        searchBox.sendKeys("laptop");
        searchBox.sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.urlContains("k=laptop")); //Sayfa laptop araması yaptı mı? URL üzerinden kontrol. URLnin k=laptop içermesi için maksimum 20 saniye bekle yoksa timeout hatası ver

        assertTrue(
                driver.getCurrentUrl().contains("k=laptop"),
                "URL laptop aramasını içermiyor!"
        );

        //System.out.println(driver.getCurrentUrl()); -> o anki urlyi konsola yazdırır.
        //System.out.println(driver.getTitle()); -> o anki sayfada bulunan başlığı getirir
        //System.out.println(driver.getWindowHandles()); -> tarayıcıda bulunduğumuz andaki unique değeri verir her çalıştığında farklı değer gelir
        //driver.getPageSource(); -> sayfanın html kodunu getirir
        //System.out.println(driver.getClass()); -> class org.openqa.selenium.chrome.ChromeDriver
    }


    @Test
    void titleTest() {
        wait.until(ExpectedConditions.titleContains("Amazon"));

        String title = driver.getTitle();
        String word = driver.getPageSource();
        assertTrue(
                title.contains("Amazon"),
                "Title Amazon içermiyor"
        );

        assertTrue(
                word.contains("shopping"),
                "HTML kodu shopping kelimesini içermiyor"
        );
    }


    @Test
    void urlTest() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-logo-sprites")));

        String URL = driver.getCurrentUrl();
        assertTrue(URL.contains("amazon.com"), "URL, Amazon.com içermiyor");
    }

    @AfterEach
    void close() {
        driver.quit();
    }
}
