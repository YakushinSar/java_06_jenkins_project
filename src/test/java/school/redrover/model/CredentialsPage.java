package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseSubmenuPage;

public class CredentialsPage extends BaseSubmenuPage<CredentialsPage> {

    @FindBy(xpath = "//h1")
    private WebElement pageHeader;

    public CredentialsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String callByMenuItemName() {
        return "Manage Credentials";
    }

    public String getTitleText() {
        return getWait2().until(ExpectedConditions.visibilityOf(pageHeader)).getText();
    }

}
