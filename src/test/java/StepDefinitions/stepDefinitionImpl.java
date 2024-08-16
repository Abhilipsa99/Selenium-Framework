package StepDefinitions;

import TestComponents.BaseTest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pageObjects.*;

import java.io.IOException;
import java.util.List;

public class stepDefinitionImpl extends BaseTest{

    public LandingPage lp;
    public ProductCatalogue pc;
    public CartPage cp;
    public CheckoutPage checkout;
    public ConfirmationPage confirmPage;
    public String confirmMessage;

    @Given("I landed on Ecommerce page")
    public void iLandedOnEcommercePage() throws IOException {
        lp = launchApplication();
    }

    @Given("^Logged in with userName (.+) and password (.+)$")
    public void loggedInWithUserNameAndPassword(String userName, String password){
        lp.loginApplication(userName,password);
    }

    @When("^I add the product (.+) to cart$")
    public void iAddProductToCart(String productName) {
        pc = new ProductCatalogue(driver);
        List<WebElement> products = pc.getProductList();
        pc.addProductToCart(productName);
    }

    @And("^checkout (.+) and submit the order$")
    public void checkoutAndSubmitTheOrder(String productName) throws IOException, InterruptedException {
        cp = new CartPage(driver);
        cp.goToCart();
        Boolean match = cp.verifyProductIsDiplayed(productName);
        Assert.assertTrue(match);
        checkout = cp.clickOnCheckout();
        checkout.selectCountry("India");
        try{
            confirmPage = checkout.submitOrder();
        }catch (ElementClickInterceptedException e){
            System.out.println(e.getMessage());
            checkout.scrollToBottom();
            confirmPage = checkout.submitOrder();
        }
    }

    @Then("verify {string} message is displayed on confirmation page")
    public void verifyMessageIsDisplayedOnConfirmationPage(String msg){
        confirmMessage = confirmPage.getConfirmMessage();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase(msg));
    }

    @Then("verify {string} is displayed")
    public void verifyIsDisplayed(String errorMsg){
        Assert.assertEquals(errorMsg,lp.getErrorMessage());
        driver.close();
    }

}
