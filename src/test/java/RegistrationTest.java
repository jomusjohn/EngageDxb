import Login.LoginPage;
import Login.LoginScenario;
import Login.MobileVerificationPage;
import Login.RegistrationFirstPage;
import Login.RegistrationSecondPage;
import org.junit.Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import userdashboard.UserDashboardPage;

import java.util.UUID;


public class RegistrationTest {
    protected WebDriver driver;
    @Test
    public void testUserRegistration() throws InterruptedException {
        String firstName = UUID.randomUUID().toString();
        String lastName = UUID.randomUUID().toString();
        driver= new ChromeDriver();
        LoginPage loginPage = LoginScenario.openLoginPage(driver);
        RegistrationFirstPage registrationPage = loginPage.clickRegisterNow();
        registrationPage.setFirstName(firstName);
        registrationPage.setLastName(lastName);
        RegistrationSecondPage registrationSecondPage = registrationPage.clickNext();
        registrationSecondPage.setEmail(firstName+"@gmail.com");
        registrationSecondPage.setPassword("User@123");
        registrationSecondPage.setConfirmPassword("User@123");
        registrationSecondPage.clickTermsOfUses();
        MobileVerificationPage mobileVerificationPage = registrationSecondPage.clickSecondNext();
        UserDashboardPage userDashboardPage = mobileVerificationPage.clickSkip();
        userDashboardPage.screen();
        driver.close();
    }
}
