package search;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import page.AbstractPage;

/**
 * Search page (/Search)
 *
 * <br><img src="SearchPage.png"/>
 *
 * @author Jomy John
 */

public class SearchPage extends AbstractPage {

    @FindBy(xpath = "(//td[@class='user']/span)[1]")
    private WebElement searchList;

    @Step
    public String getSearchIem() {
        String name = searchList.getText();
        System.out.println("check name "+ name);
        Allure.step("return " + name);
        return name;
    }

    //TODO

    public SearchPage(WebDriver driver) {
        super(driver);
    }
}
