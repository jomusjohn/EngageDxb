package Login;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import page.AbstractPage;

/**
 * RegistrationSecondPage
 *
 * <br><img src="RegistrationSecondPage.png"/>
 *
 * @author Jomy John
 */

public class RegistrationSecondPage extends AbstractPage {

    @FindBy(id = "EmailID")
    private WebElement emailInput;

    @Step
    public void setEmail(String email) {
        waitElement(By.xpath("//p[@class='form-instruction2']"));
        emailInput.sendKeys(email);
    }

    @FindBy(id = "Password")
    private WebElement password;

    @Step
    public void setPassword(String passwordValue) {
        password.sendKeys(passwordValue);
    }

    @FindBy(id = "confirmPassword")
    private WebElement confirmPasswordInput;

    @Step
    public void setConfirmPassword(String confirmPassword) {
        confirmPasswordInput.sendKeys(confirmPassword);
    }

    @FindBy(id = "termsOfUses")
    private WebElement termsOfUses;

    @Step
    public void clickTermsOfUses() {
        termsOfUses.click();
    }

    @FindBy(id = "seondNext")
    private WebElement secondNextButton;

    @Step
    public MobileVerificationPage clickSecondNext() {
        secondNextButton.click();
        return new MobileVerificationPage(driver);
    }
    public RegistrationSecondPage(WebDriver driver) {
        super(driver);
    }
}
