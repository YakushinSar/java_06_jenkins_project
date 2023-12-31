package school.redrover.model.base;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.*;
import school.redrover.model.jobs.MultiConfigurationProjectPage;
import school.redrover.runner.TestUtils;

import java.util.List;

public abstract class BaseProjectPage<Self extends BaseProjectPage<?>> extends BaseJobPage<Self> {

    @FindBy(xpath = "//a[contains(@href, 'changes')]")
    private WebElement changesButton;

    @FindBy(xpath = "//span[contains(text(),'Delete')]")
    private WebElement deleteButton;

    @FindBy(xpath = "//form[@id='disable-project']/button")
    private WebElement disableButton;

    @FindBy(xpath = "//form[@id='enable-project']/button")
    private WebElement enableButton;

    @FindBy(css = "form#enable-project")
    private WebElement disabledMessage;

    @FindBy(css = "[href*='build?']")
    private WebElement buildNowButton;

    @FindBy(xpath = "//td[@class='build-row-cell']")
    private WebElement buildRowCell;

    @FindBy(xpath = "//h2[text()='Permalinks']")
    private WebElement permalinks;

    @FindBy(xpath = "//ul[@class='permalinks-list']//li")
    private List<WebElement> permalinksList;

    @FindBy(xpath = "//a[@href='lastBuild/']")
    private WebElement lastBuildLink;

    @FindBy(xpath = "//a[@href='lastBuild/']/button")
    private WebElement lastBuildDropDownMenu;

    @FindBy(xpath = "(//a[@update-parent-class='.build-row'])[1]")
    private WebElement lastBuildCompletedLink;

    @FindBy(xpath = "//a[text()='trend']")
    private WebElement trend;

    @FindBy(xpath = "//a[@class='model-link inside build-link display-name']//button[@class='jenkins-menu-dropdown-chevron']")
    private WebElement buildsDropDownMenu;

    @FindBy(xpath = "//span[text() = 'Workspaces']")
    private WebElement workspacesFromBuildDropDownMenu;

    @FindBy(xpath = "//span[contains(text(),'Delete build ‘#1’')]")
    private WebElement deleteBuildButtonDropDownMenu;

    @FindBy(xpath = "(//a[contains(@href, 'changes')])[1]")
    private WebElement changesButtonDropDownMenu;

    @FindBy(xpath = "//div[@id='no-builds']")
    private WebElement noBuildsMessage;

    @FindBy(xpath = "//*[@id='tasks']/div[3]/span/a")
    private WebElement workspaceButton;

    @FindBy(xpath = "//ul[@class='first-of-type']/li[@index='0']")
    private WebElement changesFromLastBuild;

    @FindBy(xpath = "//a[contains(@class, 'task-link task-link')]")
    private WebElement statusButton;

    @FindBy(xpath = "//span[contains(text(),'Edit Build Information')]")
    private WebElement editBuildInformFromDropDownOfBuild;

    @FindBy(xpath = "//span[contains(text(),'Console Output')]")
    private WebElement consoleOutputType;

    @FindBy(xpath = "(//li[@class='permalink-item'])[1]//button")
    private WebElement permalinksLastBuildDropDown;

    @FindBy(xpath = "//a[@update-parent-class='.build-row'][@tooltip]")
    private WebElement buildDate;

    @FindBy(xpath = "//div[@class='bd']/ul/li")
    private List<WebElement> buildDropdownMenuOptions;

    @FindBy(xpath = "//div[@class='middle-align build-badge']/img")
    private WebElement iconLock;

    @FindBy(xpath = "//a[not(contains(tooltip, 'In progress > Console Output'))]/ancestor::div/a[contains(@href,'/2/')]")
    private WebElement iconAddBranchBuild;

    @FindBy(xpath = "//div[@id='breadcrumb-menu-target']//span[text()='Changes']")
    private WebElement changesBuildButton;

    @FindBy(xpath = "//span[contains(text(), 'Replay')]")
    private WebElement replayButton;

    @FindBy(xpath = "//a[contains(@href, 'lastBuild')]/span[text()='Pipeline Steps']")
    private WebElement pipelineStepsDropDown;

    @FindBy(xpath = "//div[@id='breadcrumb-menu-target']//span[text()='Pipeline Steps']")
    private WebElement pipelineStepsDropDownFromSideMenu;


    public BaseProjectPage(WebDriver driver) {
        super(driver);
    }

    public ChangesPage<Self> clickChangeOnLeftSideMenu() {
        getWait10().until(ExpectedConditions.visibilityOf(changesButton)).click();

        return new ChangesPage<>((Self) this);
    }

    public MainPage clickDeleteAndAccept() {
        getWait2().until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
        getDriver().switchTo().alert().accept();

        return new MainPage(getDriver());
    }

    public Self clickDeleteAndCancel() {
        getWait2().until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
        getDriver().switchTo().alert().dismiss();

        return (Self)this;
    }

    public Self clickDisable() {
        disableButton.click();

        return (Self) this;
    }

    public Self clickEnable() {
        getWait5().until(ExpectedConditions.elementToBeClickable(enableButton)).click();

        return (Self) this;
    }

    public String getDisableButtonText() {
        return disableButton.getText();
    }

    public String getEnableButtonText() {
        return enableButton.getText();
    }

    public String getDisabledMessageText() {
        return disabledMessage.getText().trim().substring(0, 34);
    }

    public Self clickBuildNowFromSideMenu() {
        buildNowButton.click();
        getWait10().until(ExpectedConditions.visibilityOf(buildRowCell));

        return (Self) this;
    }

    public BuildWithParametersPage<Self> clickBuildWithParameters() {
        buildNowButton.click();

        return new BuildWithParametersPage<>((Self) this);
    }

    public ConsoleOutputPage clickIconBuildOpenConsoleOutput(int buildNumber) {
        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@href,'/" + buildNumber + "/console')]"))).click();

        return new ConsoleOutputPage(getDriver());
    }

    public int getSizeOfPermalinksList() {
        getWait2().until(ExpectedConditions.visibilityOf(permalinks));

        return permalinksList.size();
    }

    public BuildPage clickLastBuildLink() {
        getWait10().until(ExpectedConditions.visibilityOf(lastBuildCompletedLink));
        getDriver().navigate().refresh();
        getWait10().until(ExpectedConditions.visibilityOf(lastBuildLink)).click();

        return new BuildPage(getDriver());
    }

    public ConsoleOutputPage clickIconAdditionalBranchBuild() {
        getWait15().until(ExpectedConditions.visibilityOf(iconAddBranchBuild)).click();

        return new ConsoleOutputPage(getDriver());
    }

    public TimelinePage clickTrend() {
        trend.click();

        return new TimelinePage(getDriver());
    }

    public Self openBuildsDropDownMenu() {
        getWait10().until(ExpectedConditions.visibilityOf(buildsDropDownMenu)).sendKeys(Keys.RETURN);

        return (Self)this;
    }

    public DeletePage<Self> clickDeleteBuildFromDropDownMenu() {
        openBuildsDropDownMenu();
        deleteBuildButtonDropDownMenu.click();

        return new DeletePage<>((Self)this);
    }

    public boolean isNoBuildsDisplayed() {
        return noBuildsMessage.isDisplayed();
    }

    public ChangesPage<Self> clickChangesFromDropDownMenu() {
        openBuildsDropDownMenu();
        changesButtonDropDownMenu.click();

        return new ChangesPage<>((Self)this);
    }

    private Self openLastBuildDropDownMenu() {
        statusButton.click();
        lastBuildDropDownMenu.sendKeys(Keys.RETURN);

        return (Self) this;
    }

    public ChangesPage<Self> clickChangesViaLastBuildDropDownMenu() {
        openLastBuildDropDownMenu();
        changesFromLastBuild.click();

        return new ChangesPage<>((Self)this);
    }

    public WorkspacePage clickWorkspaceFromSideMenu() {
        workspaceButton.click();

        return new WorkspacePage(getDriver());
    }

    public EditBuildInformationPage clickEditBuildInformFromProjectPage(){
        openBuildsDropDownMenu();
        editBuildInformFromDropDownOfBuild.click();

        return new EditBuildInformationPage(getDriver());
    }

    public ChangesBuildPage clickChangesBuildFromProjectPage() {
        openBuildsDropDownMenu();
        changesBuildButton.click();

        return new ChangesBuildPage(getDriver());
    }

    public ConsoleOutputPage clickConsoleOutputType(){
        consoleOutputType.click();

        return new ConsoleOutputPage(getDriver());
    }

    public BuildPage clickBuildFromSideMenu(String jobName, int buildName) {
       getDriver().findElement(By.xpath("//a[@href='/job/" + jobName + "/"+ buildName + "/']")).click();

        return new BuildPage(getDriver());
    }

    public String getLastBuildNumber(){
        return getWait10().until(ExpectedConditions.elementToBeClickable(lastBuildCompletedLink)).getText();
    }

    public Self openPermalinksLastBuildsDropDownMenu() {
        getWait10().until(ExpectedConditions.visibilityOf(permalinksLastBuildDropDown)).sendKeys(Keys.RETURN);

        return (Self)this;
    }

    public EditBuildInformationPage editBuildInfoPermalinksLastBuildDropDown() {
        openPermalinksLastBuildsDropDownMenu();
        editBuildInformFromDropDownOfBuild.click();

        return new EditBuildInformationPage(getDriver());
    }

    public DeletePage<Self> deleteBuildPermalinksLastBuildDropDown() {
        openPermalinksLastBuildsDropDownMenu();
        deleteBuildButtonDropDownMenu.click();

        return new DeletePage<>((Self)this);
    }

    public BuildPage clickBuildDateFromBuildRow() {
        buildDate.click();

        return new BuildPage(getDriver());
    }

    public List<String> getTextBuildDropDownMenuOptions() {
        return TestUtils.getTexts(buildDropdownMenuOptions);
    }

    public boolean isIconLockIsDispalyed() {
        return iconLock.isDisplayed();
    }

    public ReplayPage<Self> clickReplayFromDropDownMenu() {
        getWait2().until(ExpectedConditions.elementToBeClickable(replayButton)).click();

        return new ReplayPage<>((Self)this);
    }

    public WorkspacesBuildPage clickWorkspaceButtonFromBuildDropDown() {
        getWait2().until(ExpectedConditions.elementToBeClickable(workspacesFromBuildDropDownMenu)).click();

        return new WorkspacesBuildPage(getDriver());
    }

    public PipelineStepsPage clickPipelineStepsViaLastBuildDropDownMenu() {
        openLastBuildDropDownMenu();
        pipelineStepsDropDown.click();

        return new PipelineStepsPage(getDriver());
    }

    public Self refreshPage() {
        getDriver().navigate().refresh();

        return (Self) this;
    }

    public PipelineStepsPage clickPipelineStepsFromBuildDropDownFromSideMenu() {
        openBuildsDropDownMenu();
        Actions actions = new Actions(getDriver());
        actions.moveToElement(pipelineStepsDropDownFromSideMenu);
        actions.click().perform();

        return new PipelineStepsPage(getDriver());
    }
}
