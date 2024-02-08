import Login.LoginScenario;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import search.SearchPage;
import userdashboard.UserDashboardPage;

public class SearchTest {
    protected WebDriver driver;
    @Test
    public void testSearchByPeople() {
        driver= new ChromeDriver();
        String searchFilter = "PEOPLE";
        String searchValue = "Muhammad";
        UserDashboardPage userDashboardPage = LoginScenario.loginUser(driver);
        userDashboardPage.clickWelcomeCloseButton();
        userDashboardPage.setSearchFilter(searchFilter);
        userDashboardPage.setSearch(searchValue);
        userDashboardPage.clickFirstItemFromSearch();
        SearchPage searchPage = userDashboardPage.clickSearchButton();
        String result = searchPage.getSearchIem();
        Assertions.assertTrue(result.contains(searchValue));
        driver.close();
    }

    @Test
    public void testSearchByCompany() {
        String filterValue = "COMPANY";
        String searchName = "JAMAL JUMA TRADING CO. (L.L.C.)";
        driver= new ChromeDriver();
        UserDashboardPage userDashboardPage = LoginScenario.loginUser(driver);
        userDashboardPage.clickWelcomeCloseButton();
        userDashboardPage.setSearchFilter(filterValue);
        userDashboardPage.setSearch(searchName);
        userDashboardPage.clickFirstItemFromSearch();
        SearchPage searchPage = userDashboardPage.clickSearchButton();
        String result = searchPage.getSearchIem();
        Assertions.assertEquals(searchName, result);
        driver.close();
    }
    @Test
    public void testSearchByGroup() {
        String filterValue = "GROUP";
        String searchName = "Emirates Strategic Planning Association";
        driver= new ChromeDriver();
        UserDashboardPage userDashboardPage = LoginScenario.loginUser(driver);
        userDashboardPage.clickWelcomeCloseButton();
        userDashboardPage.setSearchFilter(filterValue);
        userDashboardPage.setSearch(searchName);
        userDashboardPage.clickFirstItemFromSearch();
        SearchPage searchPage = userDashboardPage.clickSearchButton();
        String result = searchPage.getSearchIem();
        Assertions.assertEquals(searchName, result);
        driver.close();
    }
}
