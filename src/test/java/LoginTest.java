import Login.LoginScenario;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import userdashboard.UserDashboardPage;

public class LoginTest {
    protected WebDriver driver;
    @Test
    public void testUserLogin() {
        driver= new ChromeDriver();
        UserDashboardPage userDashboardPage = LoginScenario.loginUser(driver);
        userDashboardPage.screen();
        driver.close();
    }
}
