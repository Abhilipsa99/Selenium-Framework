package pageObjects;

import AbstractComponents.AbstractComponents;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage extends AbstractComponents {

    WebDriver driver;

    public CartPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(css=".cartSection h3")
    List<WebElement> cartProducts;

    @FindBy(xpath="//button[text()='Checkout']")
    WebElement checkoutButton;

    By paymentShipping = By.className("payment__shipping");

    public Boolean verifyProductIsDiplayed(String productName){
        Boolean match = cartProducts.stream().anyMatch(cartProduct->cartProduct.getText().equals(productName));
        return match;
    }

    public CheckoutPage clickOnCheckout(){
        checkoutButton.click();
        waitForElementToAppear(paymentShipping);
        return new CheckoutPage(driver);
    }
}
