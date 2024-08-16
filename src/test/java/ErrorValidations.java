import TestComponents.BaseTest;
import TestComponents.Retry;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.CartPage;
import pageObjects.CheckoutPage;
import pageObjects.ConfirmationPage;
import pageObjects.ProductCatalogue;

import java.io.IOException;
import java.util.List;

public class ErrorValidations extends BaseTest {

    @Test(groups={"ErrorHandling"},retryAnalyzer = Retry.class)
    public void submitOrderWithWrongDetails(){
        lp.loginApplication("abhilipsamail@gmail.com","Aradhyaa@4211");
        Assert.assertEquals("Incorrect email password.",lp.getErrorMessage());

    }

    @Test
    public void productErrorValidation() throws IOException {
        String productName = "IPHONE 13 PRO";
        lp.loginApplication("abhilipsamail@gmail.com","Aradhya@421");

        ProductCatalogue pc = new ProductCatalogue(driver);
        List<WebElement> products = pc.getProductList();
        pc.addProductToCart(productName);

        CartPage cp = new CartPage(driver);
        cp.goToCart();
        Boolean match = cp.verifyProductIsDiplayed("IPHONE 13 PRO MAX");
        Assert.assertFalse(match);
    }
}
