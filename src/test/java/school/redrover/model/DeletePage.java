package school.redrover.model;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseJobPage;
import school.redrover.model.base.BaseMainHeaderPage;
import school.redrover.model.base.BasePage;

public class DeletePage<ParentPage extends BasePage<?,?>> extends BaseMainHeaderPage<DeletePage<ParentPage>> {

    @FindBy(xpath = "//form[@action='doDelete']")
    private WebElement confirmDeletionForm;

    @FindBy(name = "Submit")
    private WebElement deleteYesButton;

    private final ParentPage parentPage;

    public DeletePage(ParentPage parentPage) {
        super(parentPage.getDriver());
        this.parentPage = parentPage;
    }

    public ParentPage clickYesButton() {
        deleteYesButton.click();

        return parentPage;
    }

    public <JobPage extends BaseJobPage<?>> JobPage clickDelete(JobPage jobPage) {
        deleteYesButton.click();

        return jobPage;
    }

    public String getTextFromConfirmDeletionForm() {
        return getWait5().until(ExpectedConditions.visibilityOf(confirmDeletionForm)).getText();
    }

    public boolean isDeleteButtonDisplayed() {
        return getWait5().until(ExpectedConditions.visibilityOf(deleteYesButton)).isDisplayed();
    }
}
