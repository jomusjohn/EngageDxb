package Login;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import userdashboard.UserDashboardPage;

public class MobileVerificationPage extends RegistrationSecondPage {

    @FindBy(xpath = "//a[text()='Skip']")
    private WebElement skipButton;

    @Step
    public UserDashboardPage clickSkip() {
        waitElement(By.id("third-screen"));
        skipButton.click();
        return new UserDashboardPage(driver);
    }

    //TODO

    public MobileVerificationPage(WebDriver driver) {
        super(driver);
    }
}
