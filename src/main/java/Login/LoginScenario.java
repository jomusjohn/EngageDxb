package Login;

import dashboard.DashboardPage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import userdashboard.UserDashboardPage;

import java.util.ResourceBundle;

public class LoginScenario {

    public static ResourceBundle getUrl() {
        ResourceBundle routes = ResourceBundle.getBundle("environment");
        return routes;
    }

    @Step
    public static LoginPage openLoginPage(WebDriver driver) {
        String url = getUrl().getString("url");
            driver.get(url);
        driver.manage().window().maximize();
        DashboardPage dashboardPage = new DashboardPage(driver);
        LoginPage loginPage = dashboardPage.clickLogin();
        return loginPage;
    }

    @Step
    public static UserDashboardPage loginUser(WebDriver driver) {
        LoginPage loginPage = openLoginPage(driver);
        String email = getUrl().getString("email");
        String password = getUrl().getString("user_password");
        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.clickSignIn();
        return new UserDashboardPage(driver);
    }

    public LoginScenario(WebDriver driver) {
        super();
    }

}
