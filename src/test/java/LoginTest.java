import TestComponents.BaseTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class LoginTest extends BaseTest {

    String productName = "IPHONE 13 PRO";

    @Test (dataProvider = "getData",groups = {"Purchase"})
    public void submitOrder(HashMap<String,String> input) throws IOException {

        lp.loginApplication(input.get("email"),input.get("password"));

        ProductCatalogue pc = new ProductCatalogue(driver);
        List<WebElement> products = pc.getProductList();
        pc.addProductToCart(input.get("productName"));

        CartPage cp = new CartPage(driver);
        cp.goToCart();
        Boolean match = cp.verifyProductIsDiplayed(input.get("productName"));
        Assert.assertTrue(match);
        CheckoutPage checkout = cp.clickOnCheckout();
        ConfirmationPage confirmPage;
        checkout.selectCountry("India");
        try{
            confirmPage = checkout.submitOrder();
        }catch (ElementClickInterceptedException e){
            System.out.println(e.getMessage());
            confirmPage = checkout.submitOrder();
        }
        String confirmMessage = confirmPage.getConfirmMessage();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
    }

    @Test(dependsOnMethods = {"submitOrder"})
    public void orderHistoryTest(){
        lp.loginApplication("abhilipsamail@gmail.com","Aradhya@421");
        ProductCatalogue pc1 = new ProductCatalogue(driver);
        OrderPage op = pc1.goToOrders();
        Assert.assertTrue(op.verifyOrderIsDisplayed(productName));
    }


    @DataProvider
    public Object[][] getData() throws IOException {
       // return new Object[][] {{"abhilipsamail@gmail.com","Aradhya@421","IPHONE 13 PRO"}};
       // HashMap<String,String> map = new HashMap<>();
       // map.put("email","abhilipsamail@gmail.com");
       // map.put("password","Aradhya@421");
       // map.put("productName","IPHONE 13 PRO");

        List<HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\data\\PurchaseOrder.json");
        return new Object[][] {{data.get(0)}};
    }
}
