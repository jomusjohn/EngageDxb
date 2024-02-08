package userdashboard;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import page.AbstractPage;
import search.SearchPage;

/**
 * UserDashboardPage
 *
 * <br><img src="UserDashboardPage.png"/>
 *
 * @author Jomy John
 */
public class UserDashboardPage extends AbstractPage {



    @FindBy(xpath = "//div[@class='post-popup active']//i[@class='la la-times']")
    private WebElement welcomeModalCloseButton;

    @Step
    public void clickWelcomeCloseButton() {
        try {
            WebElement welcomeModal = driver.findElement(By.xpath("//h3[text()='Welcome to EngageDXB.']"));
            if (welcomeModal.isDisplayed()) {
                waitElement(By.xpath("//h3[text()='Welcome to EngageDXB.']"));
                welcomeModalCloseButton.click();
            } else {
                System.out.println("Welcome modal not found. Proceeding with the next step.");
            }
        } catch (NoSuchElementException e) {
            System.out.println("Welcome modal not found. Proceeding with the next step.");
        }
    }

    @FindBy(id = "searchType")
    private WebElement searchFilter;

    @Step
    public void setSearchFilter(String name) {
        Select select = new Select(searchFilter);
        select.selectByValue(name);
    }

    @FindBy(id = "searchbox")
    private WebElement searchInput;

    @Step
    public void setSearch(String searchValue) {
        searchInput.sendKeys(searchValue);
        waitInvisibilityElement(By.xpath("//input[contains(@class,'loading')]"));
        waitElement(By.xpath("//ul[@id='ui-id-1']"),3000);
    }

    @FindBy(xpath = "(//ul[@id='ui-id-1']/li)[1]")
    private WebElement firstItemFromSearch;

    @Step
    public void clickFirstItemFromSearch() {
        firstItemFromSearch.click();
    }

    @FindBy(xpath = "//button[@type='submit']/img")
    private WebElement searchButton;

    @Step
    public SearchPage clickSearchButton() {
        searchButton.click();
        return new SearchPage(driver);
    }

    @FindBy(xpath = "//i[@class='la la-angle-down']")
    private WebElement angleDownButton;

    @Step
    public void clickAngleDown() {
        angleDownButton.click();
    }

    @FindBy(id = "lnkLogout")
    private WebElement logoutButton;

    @Step
    public void clickLogout() {
        waitElement(By.xpath("//div[contains(@class,'user-account-settingss')]"));
        logoutButton.click();
    }

    //TODO

    public UserDashboardPage(WebDriver driver) {
        super(driver);
    }
}
