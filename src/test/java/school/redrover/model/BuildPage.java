package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BaseMainHeaderPage;
import school.redrover.model.base.BasePage;

public class BuildPage extends BaseMainHeaderPage<BuildPage> {

    @FindBy(xpath = "//span[@Class='build-status-icon__outer']/*[@title ='Success']")
    private WebElement greenIconV;

    @FindBy(xpath = "//h1")
    private WebElement buildHeader;

    @FindBy(xpath = "//span[contains(text(),'Started by upstream')] ")
    private WebElement buildInfo;

    @FindBy(xpath = "//span[contains(text(), 'Delete build')]/..")
    private WebElement deleteBuildButton;

    @FindBy(xpath = "//span[contains(text(), 'Console Output')]/..")
    private WebElement consoleOutputButton;

    @FindBy(xpath = "//span[text()='Edit Build Information']/..")
    private WebElement editBuildInformation;

    @FindBy(xpath = "//div[@id='description']/div[1]")
    private WebElement buildDescription;

    @FindBy(xpath = "//a[@href='editDescription']")
    private WebElement editDescription;

    @FindBy(name = "description")
    private WebElement descriptionTextField;

    @FindBy(xpath = "//button[contains(text(),'Save')]")
    private WebElement saveButton;

    @FindBy(xpath = "//a[@class='textarea-show-preview']")
    private WebElement previewButton;

    @FindBy(xpath = "//div[@class='textarea-preview']")
    private WebElement previewTextarea;

    @FindBy(xpath = "//button[@formnovalidate]")
    private WebElement keepBuildForeverButton;

    @FindBy (css = "a[href = 'aggregatedTestReport/']")
    private WebElement aggregatedTestResultLink;

    @FindBy(css = "#main-panel table tr:last-child td:last-child")
    private WebElement aggregatedTestResultNodeValue;

    @FindBy(css = ".task:last-of-type span a")
    private WebElement aggregatedTestResultSideMenuOption;

    public BuildPage(WebDriver driver) {
        super(driver);
    }

    public boolean isDisplayedGreenIconV() {
        return getWait5().until(ExpectedConditions.visibilityOf(greenIconV)).isDisplayed();
    }

    public boolean isDisplayedBuildTitle() {
        return buildHeader.getText().contains("Build #1");
    }

    public String getBuildInfo() {
        return buildInfo.getText().substring(0, buildInfo.getText().length() - 38);
    }

    public <JobTypePage extends BasePage<?, ?>> DeletePage<JobTypePage> clickDeleteBuild(JobTypePage jobTypePage) {
        getWait5().until(ExpectedConditions.elementToBeClickable(deleteBuildButton)).click();
        return new DeletePage<>(jobTypePage);
    }

    public ConsoleOutputPage clickConsoleOutput() {
        consoleOutputButton.click();

        return new ConsoleOutputPage(getDriver());
    }

    public EditBuildInformationPage clickEditBuildInformation() {
        getWait5().until(ExpectedConditions.elementToBeClickable(editBuildInformation)).click();

        return new EditBuildInformationPage(getDriver());
    }

    public String getBuildHeaderText() {
        return getWait5().until(ExpectedConditions.visibilityOf(buildHeader)).getText();
    }

    public String getBuildNameFromTitle() {
        String buildName = getWait5().until(ExpectedConditions.visibilityOf(buildHeader)).getText();

        return buildName.substring(buildName.indexOf(" ") + 1, buildName.indexOf(" ("));
    }

    public String getDescriptionText() {
        return getWait2().until(ExpectedConditions.visibilityOf(buildDescription)).getText();
    }

    public BuildPage clickEditDescription() {
        editDescription.click();
        return this;
    }

    public BuildPage clearDescriptionText() {
        descriptionTextField.clear();
        return this;
    }

    public BuildPage enterDescriptionText(String text) {
        descriptionTextField.sendKeys(text);
        return this;
    }

    public BuildPage clickSaveButton() {
        getWait2().until(ExpectedConditions.elementToBeClickable(saveButton)).click();
        return this;
    }

    public BuildPage clickPreview() {
        previewButton.click();
        return this;
    }

    public String getPreviewText() {
        return getWait2().until(ExpectedConditions.visibilityOf(previewTextarea)).getText();
    }

    public BuildPage clickKeepBuildForever() {
        keepBuildForeverButton.click();

        return this;
    }

    public boolean isDisplayedAggregatedTestResultLink() {
        return aggregatedTestResultLink.isDisplayed();
    }

    public String getTestResultsNodeText() {
        return aggregatedTestResultNodeValue.getText();
    }

    public String getAggregateTestResultSideMenuLinkText() {
        return aggregatedTestResultSideMenuOption.getAttribute("href");
    }
}
