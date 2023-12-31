package school.redrover.model.base.baseConfig;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import school.redrover.model.base.BaseProjectPage;
import school.redrover.runner.TestUtils;

import java.util.List;

public abstract class BaseConfigProjectsPage<Self extends BaseConfigPage<?, ?>, ProjectPage extends BaseProjectPage<?>> extends BaseConfigPage<Self, ProjectPage> {

    @FindBy(xpath = "//label[@for='enable-disable-project']")
    private WebElement enableDisableSwitch;

    @FindBy(xpath = "//span[text() = 'Enabled']")
    private WebElement enabledText;

    @FindBy(xpath = "//label[text()='Discard old builds']/../input")
    private WebElement oldBuildCheckBox;

    @FindBy(xpath = "//input[@name='_.daysToKeepStr']")
    private WebElement daysToKeepBuilds;

    @FindBy(xpath = "//div[text()='Days to keep builds']")
    private WebElement nameFieldDaysToKeepBuilds;

    @FindBy(xpath = "//input[@name='_.numToKeepStr']")
    private WebElement maxNumOfBuildsToKeepNumber;

    @FindBy(xpath = "//*[@name='strategy']//div[@class='error']")
    private WebElement errorMessage;

    @FindBy(xpath = "//label[text()='GitHub project']")
    private WebElement checkBoxGitHubProject;

    @FindBy(css = "[name='_.projectUrlStr']")
    private WebElement inputLineProjectUrl;

    @FindBy(xpath = "//label[text()='This project is parameterized']")
    private WebElement projectIsParametrized;

    @FindBy(xpath = "//button[text()='Add Parameter']")
    private WebElement addParameterDropdown;

    @FindBy(xpath = "//button[text()='Add Parameter']/../../..//a")
    private List<WebElement> optionsOfAddParameterDropdown;

    @FindBy(xpath = "//input[@name='parameter.name']")
    private WebElement inputParameterName;

    @FindBy(xpath = "//label[normalize-space(text())='Set by Default']")
    private WebElement checkboxSetByDefault;

    @FindBy(xpath = "//textarea[contains(@name,'parameter.description')]")
    private WebElement textareaBooleanParameterDescription;

    @FindBy(xpath = "//textarea[@name='parameter.choices']")
    private WebElement inputParameterChoices;

    @FindBy(xpath = "//textarea[@name='parameter.description']")
    private WebElement inputParameterDescription;

    @FindBy(xpath = "//label[normalize-space(text())='Throttle builds']")
    private WebElement throttleBuilds;

    @FindBy(xpath = "//select[@name='_.durationName']")
    private WebElement selectTimePeriod;

    @FindBy(xpath = "//input[@name='jenkins-triggers-ReverseBuildTrigger']")
    private WebElement buildAfterOtherProjectsAreBuiltCheckBox;

    @FindBy(xpath = "//input[@name='_.upstreamProjects']")
    private WebElement projectsToWatchField;

    public BaseConfigProjectsPage(ProjectPage projectPage) {
        super(projectPage);
    }

    public Self clickSwitchEnableOrDisable() {
        getWait2().until(ExpectedConditions.elementToBeClickable(enableDisableSwitch)).click();

        return (Self) this;
    }

    public Boolean isEnabledDisplayed() {
        return enabledText.isDisplayed();
    }

    public Self clickOldBuildCheckBox() {
        TestUtils.clickByJavaScript(this, oldBuildCheckBox);

        return (Self) this;
    }

    public boolean checkboxDiscardOldBuildsIsSelected() {
        return oldBuildCheckBox.isSelected();
    }

    public Self enterDaysToKeepBuilds(int number) {
        TestUtils.scrollToElementByJavaScript(this, nameFieldDaysToKeepBuilds);
        TestUtils.sendTextToInput(this, daysToKeepBuilds, String.valueOf(number));

        return (Self) this;
    }

    public Self enterMaxNumOfBuildsToKeep(int number) {
        TestUtils.sendTextToInput(this, maxNumOfBuildsToKeepNumber, String.valueOf(number));

        return (Self) this;
    }

    public String getDaysToKeepBuilds() {
        return daysToKeepBuilds.getAttribute("value");
    }

    public String getMaxNumOfBuildsToKeep() {
        return maxNumOfBuildsToKeepNumber.getAttribute("value");
    }

    public String getErrorMessageStrategyDays() {
        return getWait2().until(ExpectedConditions.elementToBeClickable(errorMessage)).getText();
    }

    public Self clickGitHubProjectCheckbox() {
        checkBoxGitHubProject.click();

        return (Self) this;
    }

    public Self inputTextTheInputAreaProjectUrlInGitHubProject(String text) {
        inputLineProjectUrl.sendKeys(text);

        return (Self) this;
    }

    public Self checkProjectIsParametrized() {
        TestUtils.click(this, projectIsParametrized);

        return (Self) this;
    }

    public Self openAddParameterDropDown() {
        getWait10().until(ExpectedConditions.elementToBeClickable(addParameterDropdown));
        getWait10().until(ExpectedConditions.elementToBeClickable(projectIsParametrized));
        TestUtils.scrollToElementByJavaScript(this, projectIsParametrized);
        addParameterDropdown.click();

        return (Self) this;
    }

    public Self selectParameterInDropDownByType(String typeParameter) {
        getDriver().findElement(By.xpath(String.format("//li/a[text()='%s']", typeParameter))).click();

        return (Self) this;
    }

    public Self inputBooleanParameterName(String name) {
        inputParameterName.sendKeys(name);

        return (Self) this;
    }

    public Self selectCheckboxSetByDefault() {
        TestUtils.click(this, checkboxSetByDefault);

        return (Self) this;
    }

    public Self setBooleanParameterDescription(String description) {
        textareaBooleanParameterDescription.sendKeys(description);

        return (Self) this;
    }

    public Self inputParameterChoices(List<String> parameterChoices) {
        for (String element : parameterChoices) {
            inputParameterChoices.sendKeys(element + "\n");
        }

        return (Self) this;
    }

    public Self inputParameterDesc(String description) {
        inputParameterDescription.sendKeys(description);

        return (Self) this;
    }

    public List<String> getAllOptionsOfAddParameterDropdown() {
        return TestUtils.getTexts(optionsOfAddParameterDropdown);
    }

    public Self scrollToBuildTriggers() {
        TestUtils.scrollToElementByJavaScript(this, throttleBuilds);

        return (Self) this;
    }

    public Self checkThrottleBuilds() {
       throttleBuilds.click();

        return (Self) this;
    }

    public Self selectTimePeriod(String timePeriod) {
        new Select(selectTimePeriod).selectByValue(timePeriod.toLowerCase());

        return (Self) this;
    }

    public String getTimePeriodText() {
        return new Select(selectTimePeriod).getFirstSelectedOption().getText();
    }

    public Self clickBuildAfterOtherProjectsAreBuiltCheckBox() {
        TestUtils.scrollToElementByJavaScript(this, buildAfterOtherProjectsAreBuiltCheckBox);
        TestUtils.clickByJavaScript(this, buildAfterOtherProjectsAreBuiltCheckBox);

        return (Self) this;
    }

    public Self inputProjectsToWatch(String projectName) {
        getWait5().until(ExpectedConditions.visibilityOf(projectsToWatchField)).sendKeys(projectName);

        return (Self) this;
    }
}
