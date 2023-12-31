package school.redrover.model.jobsConfig;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import school.redrover.model.jobs.FolderPage;
import school.redrover.model.base.baseConfig.BaseConfigFoldersPage;

public class FolderConfigPage extends BaseConfigFoldersPage<FolderConfigPage, FolderPage> {

    @FindBy(xpath = "//div[@class='repeated-container with-drag-drop']/span")
    private WebElement addButton;

    @FindBy(xpath = "//input[@checkdependson='name']")
    private WebElement nameField;

    @FindBy(xpath = "//input[@name='_.defaultVersion']")
    private WebElement defaultVersionField;

    @FindBy(xpath = "//div[contains(text(), 'Source Code')]/../div/select")
    private WebElement sourceCodeManagementOptions;

    @FindBy(xpath = "//input[@name='_.repositoryUrl']")
    private WebElement repositoryField;

    @FindBy(xpath = "//div[@class='ok']")
    private WebElement currentDefaultVersion;

    @FindBy(xpath = "//button[@data-section-id='properties']")
    private WebElement propertiesButton;

    @FindBy(tagName = "footer")
    private WebElement footer;

    @FindBy(xpath = "//label[contains(text(), 'Repository Scan')]")
    private WebElement repositoryScanRadio;

    @FindBy(xpath = "//label[@class='attach-previous ']")
    private WebElement cacheFetchedLabel;

    public FolderConfigPage(FolderPage folderPage) {
        super(folderPage);
    }

    public FolderConfigPage inputNameLibrary() {
        new Actions(getDriver())
                .scrollToElement(footer)
                .click(addButton)
                .perform();

        nameField.sendKeys("shared-library");

        return this;
    }

    public FolderConfigPage inputDefaultVersion(String defaultVersion) {
        new Actions(getDriver())
                .scrollToElement(cacheFetchedLabel)
                .perform();

       getWait2().until(ExpectedConditions.elementToBeClickable(defaultVersionField)).sendKeys(defaultVersion);

        return this;
    }

    public FolderConfigPage pushSourceCodeManagementButton() {
        new Actions(getDriver())
                .scrollToElement(sourceCodeManagementOptions)
                .click(sourceCodeManagementOptions)
                .perform();

        return this;
    }

    public FolderConfigPage chooseOptionGitHub() {
        new Select(sourceCodeManagementOptions).selectByVisibleText("GitHub");

        return this;
    }

    public FolderConfigPage inputLibraryRepoUrl(String repoUrl) {
        new Actions(getDriver())
                .scrollToElement(repositoryScanRadio)
                .perform();

        getWait2().until(ExpectedConditions.elementToBeClickable(repositoryField)).sendKeys(repoUrl);

        return this;
    }

    public FolderConfigPage refreshPage() {
        getDriver().navigate().refresh();

        return this;
    }

    public Boolean libraryDefaultVersionValidated() {
        new Actions(getDriver())
                .scrollToElement(cacheFetchedLabel)
                .perform();

        return getWait5().until(ExpectedConditions.visibilityOf(currentDefaultVersion)).getText().contains("Currently maps to revision");
    }
}
