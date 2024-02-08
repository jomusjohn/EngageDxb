package Login;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import page.AbstractPage;

/**
 * RegistrationSecondPage
 *
 * <br><img src="RegistrationFirstPage.png"/>
 *
 * @author Jomy John
 */

public class RegistrationFirstPage extends AbstractPage {

    @FindBy(id = "firstName")
    private WebElement firstNameInput;

    @Step
    public void setFirstName(String firstName) {
        firstNameInput.sendKeys(firstName);
    }

    @FindBy(id = "lastName")
    private WebElement lastNameInput;

    @Step
    public void setLastName(String lastName) {
        lastNameInput.sendKeys(lastName);
    }

    @FindBy(id = "firstNext")
    private WebElement nextButton;

    @Step
    public RegistrationSecondPage clickNext() {
        nextButton.click();
        return new RegistrationSecondPage(driver);
    }


    public RegistrationFirstPage(WebDriver driver) {
        super(driver);
    }
}
