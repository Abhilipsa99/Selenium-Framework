package pageObjects;

import AbstractComponents.AbstractComponents;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ConfirmationPage extends AbstractComponents {

    WebDriver driver;

    public ConfirmationPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @FindBy(css=".hero-primary")
    WebElement confirmMessage;

    public String getConfirmMessage(){
        String message = confirmMessage.getText();
        return message;
    }
}
