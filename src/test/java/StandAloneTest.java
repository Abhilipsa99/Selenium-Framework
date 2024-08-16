import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pageObjects.LandingPage;

import java.time.Duration;
import java.util.List;

public class StandAloneTest {
    public static void main(String[] args){

        String productName = "IPHONE 13 PRO";
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://rahulshettyacademy.com/client");
        driver.findElement(By.id("userEmail")).sendKeys("abhilipsamail@gmail.com");
        driver.findElement(By.cssSelector("input#userPassword")).sendKeys("Aradhya@421");
        driver.findElement(By.id("login")).click();
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'col-lg-4')]")));
        List<WebElement> products = driver.findElements(By.xpath("//div[contains(@class,'col-lg-4')]"));
        WebElement prod = products.stream().filter(product->product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
        prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));

        driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']")).click();

        List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
        Boolean match = cartProducts.stream().anyMatch(cartProduct->cartProduct.getText().equals(productName));
        System.out.println(match);
        Assert.assertTrue(match);
        driver.findElement(By.xpath("//button[text()='Checkout']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("payment__shipping")));
        Actions a = new Actions(driver);
        a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")),"India").build().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
        driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".action__submit")));
        try{
            driver.findElement(By.cssSelector(".action__submit")).click();
        }catch (ElementClickInterceptedException e){
            System.out.println(e.getMessage());
            driver.findElement(By.cssSelector(".action__submit")).click();
        }

        String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
        driver.close();
    }
}
