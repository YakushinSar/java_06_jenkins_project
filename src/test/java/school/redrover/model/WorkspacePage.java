package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseMainHeaderPage;


public class WorkspacePage extends BaseMainHeaderPage<WorkspacePage> {

    @FindBy(xpath = "//h1")
    private WebElement headerText;

    public WorkspacePage(WebDriver driver) {
        super(driver);
    }

    public String getTextFromWorkspacePage() {
        return getWait5().until(ExpectedConditions.visibilityOf(headerText)).getText();
    }
}
