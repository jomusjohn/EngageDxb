package dashboard;

import Login.LoginPage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Dashboard page
 *
 * <br><img src="Dashboard.png"/>
 *
 * @author Jomy John
 */
public class DashboardPage {

    private final WebDriver driver;
    @FindBy(xpath = "//ul[@class='nav navbar-nav navbar-left']//a[text()='Login']")
    private WebElement loginButton;

    @Step
    public LoginPage clickLogin() {
        loginButton.click();
       return new LoginPage(driver);
    }



    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
        
    }
}
