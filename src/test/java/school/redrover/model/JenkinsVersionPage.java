package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseModel;

import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfWindowsToBe;

public class JenkinsVersionPage extends BaseModel {

    @FindBy(xpath = "//h1")
    private WebElement jenkinsPageTitle;

    public JenkinsVersionPage(WebDriver driver) {
        super(driver);
    }

    public JenkinsVersionPage switchJenkinsDocPage() {
        String originalWindow = getDriver().getWindowHandle();

        getWait2().until(numberOfWindowsToBe(2));

        assert getDriver().getWindowHandles().size() == 2;

        for (String winHandle : getDriver().getWindowHandles()) {
            if (!originalWindow.contentEquals(winHandle)) {
                getDriver().switchTo().window(winHandle);
                break;
            }
        }

        return this;
    }

    public String getJenkinsPageTitle() {
        return getWait10().until(ExpectedConditions.visibilityOf(jenkinsPageTitle)).getText();
    }
}
