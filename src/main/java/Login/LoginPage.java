package Login;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import page.AbstractPage;

/**
 * Login Page (/account/login)
 *
 * <br><img src="LoginPage.png"/>
 *
 * @author Jomy John
 */
public class LoginPage extends AbstractPage {

    @FindBy(id = "username")
    private WebElement userNameInput;

    @Step
    public void setEmail(String email) {
        userNameInput.sendKeys(email);
    }

    @FindBy(id = "password")
    private WebElement passwordInput;

    @Step
    public void setPassword(String password) {
        passwordInput.sendKeys(password);
    }

    @FindBy(id = "btnLogin")
    private WebElement signInButton;

    @Step
    public void clickSignIn() {
        signInButton.click();
    }

    @FindBy(xpath = "//a[text()='Register Now']")
    private WebElement registerNowButton;

    @Step
    public RegistrationFirstPage clickRegisterNow() {
        registerNowButton.click();
        return new RegistrationFirstPage(driver);
    }

    //TODO

    public LoginPage(WebDriver driver) {
        super(driver);
    }
}
