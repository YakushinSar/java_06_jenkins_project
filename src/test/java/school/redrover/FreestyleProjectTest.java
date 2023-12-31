package school.redrover;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import school.redrover.model.*;
import school.redrover.model.jobs.FreestyleProjectPage;
import school.redrover.model.jobsConfig.FreestyleProjectConfigPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.ArrayList;
import java.util.List;

public class FreestyleProjectTest extends BaseTest {

    private static final String FREESTYLE_NAME = "FREESTYLE_NAME";
    private static final String NEW_FREESTYLE_NAME = "NEW_FREESTYLE_NAME";
    private static final String DESCRIPTION_TEXT = "DESCRIPTION_TEXT";
    private static final String NEW_DESCRIPTION_TEXT = "NEW_DESCRIPTION_TEXT";
    private static final String GITHUB_URL = "https://github.com/ArtyomDulya/TestRepo";
    private static final String NEW_GITHUB_URL = "https://github.com/nikabenz/repoForJenkinsBuild";
    private static final String DISPLAY_NAME = "FreestyleDisplayName";
    private static final String NEW_DISPLAY_NAME = "NewFreestyleDisplayName";

    @Test
    public void testCreateFromCreateAJob() {
        MainPage mainPage = new MainPage(getDriver())
                .clickCreateAJob()
                .enterItemName(FREESTYLE_NAME)
                .selectJobType(TestUtils.JobType.FreestyleProject)
                .clickOkButton(new FreestyleProjectConfigPage(new FreestyleProjectPage(getDriver())))
                .getHeader()
                .clickLogo();

        Assert.assertTrue(mainPage.projectStatusTableIsDisplayed());
        Assert.assertEquals(mainPage.getJobName(FREESTYLE_NAME), FREESTYLE_NAME);
    }

    @Test
    public void testCreateFromCreateAJobArrow() {
        boolean freestyleProjectNameIsAppeared = new MainPage(getDriver())
                .clickCreateAJobArrow()
                .enterItemName(FREESTYLE_NAME)
                .selectJobType(TestUtils.JobType.FreestyleProject)
                .clickOkButton(new FreestyleProjectConfigPage(new FreestyleProjectPage(getDriver())))
                .getHeader()
                .clickLogo()
                .jobIsDisplayed(FREESTYLE_NAME);

        Assert.assertTrue(freestyleProjectNameIsAppeared, "Error! Job Is Not Displayed");
    }

    @Test
    public void testCreateFromNewItem() {
        MainPage projectName = new MainPage(getDriver())
                .clickNewItem()
                .enterItemName(FREESTYLE_NAME)
                .selectJobType(TestUtils.JobType.FreestyleProject)
                .clickOkButton(new FreestyleProjectConfigPage(new FreestyleProjectPage(getDriver())))
                .clickSaveButton()
                .getHeader()
                .clickLogo();

        Assert.assertTrue(projectName.jobIsDisplayed(FREESTYLE_NAME));
    }

    @Test
    public void testCreateFromPeoplePage() {
        MainPage projectPeoplePage = new MainPage(getDriver())
                .clickPeopleOnLeftSideMenu()
                .clickNewItem()
                .enterItemName(FREESTYLE_NAME)
                .selectJobType(TestUtils.JobType.FreestyleProject)
                .clickOkButton(new FreestyleProjectConfigPage(new FreestyleProjectPage(getDriver())))
                .getHeader()
                .clickLogo();

        Assert.assertTrue(projectPeoplePage.jobIsDisplayed(FREESTYLE_NAME));
    }

    @Test
    public void testCreateFromBuildHistoryPage() {
        MainPage newProjectFromBuildHistoryPage = new BuildHistoryPage(getDriver())
                .clickNewItem()
                .enterItemName(FREESTYLE_NAME)
                .selectJobType(TestUtils.JobType.FreestyleProject)
                .clickOkButton(new FreestyleProjectConfigPage(new FreestyleProjectPage(getDriver())))
                .getHeader()
                .clickLogo();

        Assert.assertTrue(newProjectFromBuildHistoryPage.jobIsDisplayed(FREESTYLE_NAME));
    }

    @Test
    public void testCreateFromManageJenkinsPage() {
        boolean jobIsDisplayed = new MainPage(getDriver())
                .clickManageJenkinsPage()
                .clickNewItem()
                .enterItemName(FREESTYLE_NAME)
                .selectJobType(TestUtils.JobType.FreestyleProject)
                .clickOkButton(new FreestyleProjectConfigPage(new FreestyleProjectPage(getDriver())))
                .getHeader()
                .clickLogo()
                .jobIsDisplayed(FREESTYLE_NAME);

        Assert.assertTrue(jobIsDisplayed, "Error: the Freestyle Project's name is not displayed on Dashboard");
    }

    @Test
    public void testCreateFromMyViewsCreateAJob() {
        MainPage projectName = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickCreateAJob()
                .enterItemName(FREESTYLE_NAME)
                .selectJobType(TestUtils.JobType.FreestyleProject)
                .clickOkButton(new FreestyleProjectConfigPage(new FreestyleProjectPage(getDriver())))
                .getHeader()
                .clickLogo();

        Assert.assertTrue(projectName.jobIsDisplayed(FREESTYLE_NAME), "Error: the Freestyle Project's name is not displayed on Dashboard from Home page");
        Assert.assertTrue(projectName.clickMyViewsSideMenuLink()
                .jobIsDisplayed(FREESTYLE_NAME), "Error: the Freestyle Project's name is not displayed on Dashboard from MyViews page");
    }

    @Test
    public void testCreateFromMyViewsCreateAJobArrow() {
        MainPage mainPage = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickCreateAJobArrow()
                .enterItemName(FREESTYLE_NAME)
                .selectJobType(TestUtils.JobType.FreestyleProject)
                .clickOkButton(new FreestyleProjectConfigPage(new FreestyleProjectPage(getDriver())))
                .getHeader()
                .clickLogo();

        Assert.assertTrue(mainPage.jobIsDisplayed(FREESTYLE_NAME));
        Assert.assertTrue(mainPage.clickMyViewsSideMenuLink().verifyJobIsPresent(FREESTYLE_NAME));
    }

    @Test
    public void testCreateFromMyViewsNewItem() {
        MainPage projectName = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .clickNewItem()
                .enterItemName(FREESTYLE_NAME)
                .selectJobType(TestUtils.JobType.FreestyleProject)
                .clickOkButton(new FreestyleProjectConfigPage(new FreestyleProjectPage(getDriver())))
                .getHeader()
                .clickLogo();

        boolean nameJobFromMyViewsPage = new MainPage(getDriver())
                .clickMyViewsSideMenuLink()
                .jobIsDisplayed(FREESTYLE_NAME);

        Assert.assertTrue(projectName.jobIsDisplayed(FREESTYLE_NAME), "Error: the folder name is not displayed");
        Assert.assertTrue(nameJobFromMyViewsPage, "Created Freestyle project name is displayed on MyViews Page");
    }

    @Test
    public void testCreateWithExistingName() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        CreateItemErrorPage errorPage =
                TestUtils.createJobWithExistingName(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject);

        Assert.assertEquals(errorPage.getHeaderText(), "Error");
        Assert.assertEquals(errorPage.getErrorMessage(),
                String.format("A job already exists with the name ‘%s’", FREESTYLE_NAME));
    }

    @DataProvider(name = "invalid-characters")
    public Object[][] getInvalidCharacters() {
        return new Object[][]{{"!"}, {"@"}, {"#"}, {"$"}, {"%"}, {"^"}, {"&"}, {"*"}, {"?"}, {"|"}, {">"}, {"["}, {"]"}};
    }

    @Test(dataProvider = "invalid-characters")
    public void testCreateUsingInvalidData(String character) {
        NewJobPage newJobPage = new MainPage(getDriver())
                .clickNewItem()
                .enterItemName(character)
                .selectJobType(TestUtils.JobType.FreestyleProject);

        Assert.assertFalse(newJobPage.isOkButtonEnabled(), "The button is enabled");
        Assert.assertEquals(newJobPage.getItemInvalidMessage(), "» ‘" + character + "’ is an unsafe character");
    }

    @Test
    public void testCreateWithEmptyName() {
        final String expectedError = "» This field cannot be empty, please enter a valid name";

        String actualError = new MainPage(getDriver())
                .clickCreateAJobArrow()
                .selectJobType(TestUtils.JobType.FreestyleProject)
                .getItemNameRequiredErrorText();

        Assert.assertEquals(actualError, expectedError);
    }

    @Test
    public void testOKButtonIsDisabledWhenEmptyName() {
        boolean okButton = new MainPage(getDriver())
                .clickCreateAJobArrow()
                .selectJobType(TestUtils.JobType.FreestyleProject)
                .isOkButtonEnabled();

        Assert.assertFalse(okButton);
    }

    @Test
    public void testCreateWithSpaceInsteadOfName() {
        CreateItemErrorPage errorPage =
                TestUtils.createJobWithSpaceInsteadName(this, TestUtils.JobType.FreestyleProject);

        Assert.assertEquals(errorPage.getHeaderText(), "Error");
        Assert.assertEquals(errorPage.getErrorMessage(), "No name is specified");
    }

    @Test
    public void testCreateWithDotInsteadOfName() {
        final String expectedError = "» “.” is not an allowed name";

        String actualError = new MainPage(getDriver())
                .clickNewItem()
                .selectJobType(TestUtils.JobType.FreestyleProject)
                .enterItemName(".")
                .getItemInvalidMessage();

        Assert.assertEquals(actualError, expectedError);
    }

    @Test
    public void testCreateWithLongName() {
        String longName = RandomStringUtils.randomAlphanumeric(256);

        String errorMessage = new MainPage(getDriver())
                .clickNewItem()
                .enterItemName(longName)
                .selectJobAndOkAndGoToBugPage(TestUtils.JobType.FreestyleProject)
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "A problem occurred while processing the request.");
    }

    @Test
    public void testRenameFromDropDownMenu() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String actualFreestyleProjectName = new MainPage(getDriver())
                .dropDownMenuClickRename(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .enterNewName(NEW_FREESTYLE_NAME)
                .clickRenameButton()
                .getJobName();

        Assert.assertEquals(actualFreestyleProjectName, "Project " + NEW_FREESTYLE_NAME);
    }

    @Test
    public void testRenameFromSideMenu() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String projectName = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickRename()
                .enterNewName(FREESTYLE_NAME + " New")
                .clickRenameButton()
                .getJobName();

        Assert.assertEquals(projectName, "Project " + FREESTYLE_NAME + " New");
    }

    @Test
    public void testRenameToTheCurrentNameAndGetError() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String errorMessage = new MainPage(getDriver())
                .dropDownMenuClickRename(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .enterNewName(FREESTYLE_NAME)
                .clickRenameButtonAndGoError()
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "The new name is the same as the current name.");
    }

    @DataProvider(name = "wrong-character")
    public Object[][] provideWrongCharacters() {
        return new Object[][]{{"!", "!"}, {"@", "@"}, {"#", "#"}, {"$", "$"}, {"%", "%"}, {"^", "^"}, {"&", "&amp;"}, {"*", "*"},
                {"?", "?"}, {"|", "|"}, {">", "&gt;"}, {"<", "&lt;"}, {"[", "["}, {"]", "]"}};
    }

    @Test(dataProvider = "wrong-character")
    public void testRenameWithInvalidData(String invalidData, String expectedResult) {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String actualErrorMessage = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickRename()
                .enterNewName(invalidData)
                .clickRenameButtonAndGoError()
                .getErrorMessage();

        Assert.assertEquals(actualErrorMessage, "‘" + expectedResult + "’ is an unsafe character");
    }

    @Test
    public void testRenameWithDotInsteadName() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String actualErrorMessage = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickRename()
                .enterNewName(".")
                .clickRenameButtonAndGoError()
                .getErrorMessage();

        Assert.assertEquals(actualErrorMessage, "“.” is not an allowed name");
    }

    @Test
    public void testCreateBuildNowFromDropDown() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String createBuildNow = new MainPage(getDriver())
                .clickJobDropdownMenuBuildNow(FREESTYLE_NAME)
                .getHeader()
                .clickLogoWithPause()
                .getLastBuildIconStatus();

        Assert.assertEquals(createBuildNow, "Success");
    }

    @Test
    public void testCreateBuildNowFromSideMenu() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        boolean buildHeaderIsDisplayed = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickBuildNowFromSideMenu()
                .clickIconBuildOpenConsoleOutput(1)
                .isDisplayedBuildTitle();

        Assert.assertTrue(buildHeaderIsDisplayed, "build not created");
    }

    @Test
    public void testCreateBuildNowFromArrow() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        boolean buildHeaderIsDisplayed = new MainPage(getDriver())
                .clickBuildByGreenArrow(FREESTYLE_NAME)
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickIconBuildOpenConsoleOutput(1)
                .isDisplayedBuildTitle();

        Assert.assertTrue(buildHeaderIsDisplayed, "Build is not created");
    }

    @Test
    public void testPresenceOfBuildLinksAfterBuild() {
        TestUtils.createJob(this, NEW_FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String statusIcon = new MainPage(getDriver())
                .clickJobName(NEW_FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickBuildNowFromSideMenu()
                .getBreadcrumb()
                .clickDashboardButton()
                .getJobBuildStatusIcon(NEW_FREESTYLE_NAME);

        int sizeOfPermalinksList = new MainPage(getDriver())
                .clickJobName(NEW_FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .getSizeOfPermalinksList();

        Assert.assertEquals(statusIcon, "Success");
        Assert.assertEquals(sizeOfPermalinksList, 4);
    }

    @Test
    public void testAddDisplayNameForBuild() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, false);

        String buildHeaderText = new FreestyleProjectPage(getDriver())
                .clickBuildNowFromSideMenu()
                .clickLastBuildLink()
                .clickEditBuildInformation()
                .enterDisplayName("DisplayName")
                .clickSaveButton()
                .getBuildHeaderText();

        Assert.assertTrue(buildHeaderText.contains("DisplayName"),
                "Error: The Display Name for the Build has not been changed.");
    }

    @Test
    public void testPreviewDescriptionFromBuildPage() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, false);

        String previewText = new FreestyleProjectPage(getDriver())
                .clickBuildNowFromSideMenu()
                .clickLastBuildLink()
                .clickAddOrEditDescription()
                .enterDescription(DESCRIPTION_TEXT)
                .clickPreviewDescription()
                .getPreviewDescriptionText();

        Assert.assertEquals(previewText, DESCRIPTION_TEXT);
    }

    @Test
    public void testEditDescriptionFromBuildPage() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String newBuildDescription = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickBuildNowFromSideMenu()
                .clickBuildFromSideMenu(FREESTYLE_NAME, 1)
                .clickEditBuildInformation()
                .enterDescription(DESCRIPTION_TEXT)
                .clickSaveButton()
                .clickAddOrEditDescription()
                .clearDescriptionField()
                .enterDescription(NEW_DESCRIPTION_TEXT)
                .clickSaveButtonDescription()
                .getDescriptionText();

        Assert.assertEquals(newBuildDescription, NEW_DESCRIPTION_TEXT);
    }

    @Test
    public void testBuildChangesFromDropDown() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String titleChange = new MainPage(getDriver())
                .clickBuildByGreenArrow(FREESTYLE_NAME)
                .getHeader()
                .clickLogo()
                .openBuildDropDownMenu("#1")
                .clickChangesBuildFromDropDown()
                .getTextChanges();

        Assert.assertEquals(titleChange, "Changes");
    }

    @Test
    public void testBuildChangesFromProjectPage() {
        final String heading = "Changes";
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String changesTitle = new MainPage(getDriver())
                .clickBuildByGreenArrow(FREESTYLE_NAME)
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickChangesBuildFromProjectPage()
                .getHeading();

        Assert.assertEquals(changesTitle, heading);
    }

    @Test
    public void testBuildChangesFromLastBuild() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, false);

        String text = new FreestyleProjectPage(getDriver())
                .clickBuildNowFromSideMenu()
                .clickChangesViaLastBuildDropDownMenu()
                .getTextOfPage();

        Assert.assertTrue(text.contains("No changes."));
    }

    @Test
    public void testBuildChangesFromBuildPage(){
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String text = new MainPage(getDriver())
                .clickBuildByGreenArrow(FREESTYLE_NAME)
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickLastBuildLink()
                .clickChangesBuildFromSideMenu()
                .getPageHeaderText();

        Assert.assertEquals(text, "Changes");
    }

    @Test
    public void testConsoleOutputFromDropDown() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        boolean consoleOutputTitle = new MainPage(getDriver())
                .clickBuildByGreenArrow(FREESTYLE_NAME)
                .getHeader()
                .clickLogo()
                .openLastBuildDropDownMenu()
                .clickConsoleOutputLastBuildDropDown()
                .isDisplayedBuildTitle();

        Assert.assertTrue(consoleOutputTitle, "Error: Console Output Title is not displayed!");
    }

    @Test
    public void testConsoleOutputFromProjectPage() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        boolean consoleOutput = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickBuildNowFromSideMenu()
                .openBuildsDropDownMenu()
                .clickConsoleOutputType()
                .isDisplayedBuildTitle();

        Assert.assertTrue(consoleOutput, "Console output page is not displayed");
    }

    @Test
    public void testConsoleOutputFromLastBuild() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        FreestyleProjectPage freestyleJob = new MainPage(getDriver())
                .clickBuildByGreenArrow(FREESTYLE_NAME)
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()));

        String lastBuildNumber = freestyleJob
                .getLastBuildNumber();

        ConsoleOutputPage consoleOutput = freestyleJob
                .clickLastBuildLink()
                .clickConsoleOutput();

        String breadcrumb = consoleOutput
                .getBreadcrumb()
                .getFullBreadcrumbText();

        Assert.assertTrue(consoleOutput.isDisplayedBuildTitle(), "Console output page is not displayed");
        Assert.assertTrue(breadcrumb.contains(lastBuildNumber));
    }

    @Test
    public void testConsoleOutputFromBuildPage() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        boolean consoleOutputTitleDisplayed = new MainPage(getDriver())
                .clickBuildByGreenArrow(FREESTYLE_NAME)
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickLastBuildLink()
                .clickConsoleOutput()
                .isDisplayedBuildTitle();

        Assert.assertTrue(consoleOutputTitleDisplayed, "Error: Console Output Title is not displayed!");
    }

    @Test
    public void testEditBuildInformationFromDropDown() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String getTitle = new MainPage(getDriver())
                .clickBuildByGreenArrow(FREESTYLE_NAME)
                .getHeader()
                .clickLogo()
                .openBuildDropDownMenu("#1")
                .clickEditBuildInformFromDropDown()
                .getHeaderText();

        Assert.assertEquals(getTitle, "Edit Build Information");
    }

    @Test
    public void testEditBuildInformationFromProjectPage() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String titleEditBuildPage = new MainPage(getDriver())
                .clickBuildByGreenArrow(FREESTYLE_NAME)
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickEditBuildInformFromProjectPage()
                .getHeaderText();

        Assert.assertEquals(titleEditBuildPage, "Edit Build Information");
    }

    @Test
    public void testEditBuildInformationFromLastBuild() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String buildName = new MainPage(getDriver())
                .clickBuildByGreenArrow(FREESTYLE_NAME)
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .editBuildInfoPermalinksLastBuildDropDown()
                .enterDisplayName(DISPLAY_NAME)
                .enterDescription(DESCRIPTION_TEXT)
                .clickSaveButton()
                .getHeader()
                .clickLogo()
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .editBuildInfoPermalinksLastBuildDropDown()
                .editDisplayName(NEW_DISPLAY_NAME)
                .enterDescription(NEW_DESCRIPTION_TEXT)
                .clickSaveButton()
                .getBuildNameFromTitle();

        String description = new BuildPage(getDriver())
                .getDescriptionText();

        Assert.assertEquals(buildName, NEW_DISPLAY_NAME);
        Assert.assertEquals(description, NEW_DESCRIPTION_TEXT);
    }

    @Test
    public void testEditBuildInformationFromBuildPage() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String buildName = new MainPage(getDriver())
                .clickBuildByGreenArrow(FREESTYLE_NAME)
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickLastBuildLink()
                .clickEditBuildInformation()
                .enterDisplayName(DISPLAY_NAME)
                .enterDescription(DESCRIPTION_TEXT)
                .clickSaveButton()
                .clickEditBuildInformation()
                .editDisplayName(NEW_DISPLAY_NAME)
                .enterDescription(NEW_DESCRIPTION_TEXT)
                .clickSaveButton()
                .getBuildNameFromTitle();

        String description = new BuildPage(getDriver())
                .getDescriptionText();

        Assert.assertEquals(buildName, NEW_DISPLAY_NAME);
        Assert.assertEquals(description, NEW_DESCRIPTION_TEXT);
    }

    @Test
    public void testPreviewDescriptionFromEditInformationPage() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, false);

        String previewDescriptionText = new FreestyleProjectPage(getDriver())
                .clickBuildNowFromSideMenu()
                .clickLastBuildLink()
                .clickEditBuildInformation()
                .enterDescription(DESCRIPTION_TEXT)
                .clickPreviewButton()
                .getPreviewText();

        Assert.assertEquals(previewDescriptionText, DESCRIPTION_TEXT);
    }

    @Test
    public void testAddDescriptionFromEditInformationPage() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String descriptionText = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickBuildNowFromSideMenu()
                .clickBuildFromSideMenu(FREESTYLE_NAME, 1)
                .clickEditBuildInformation()
                .enterDescription(DESCRIPTION_TEXT)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(descriptionText, DESCRIPTION_TEXT);
    }

    @Test
    public void testDeleteBuildNowFromDropDown() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        boolean noBuildsMessage = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickBuildNowFromSideMenu()
                .getHeader()
                .clickLogo()
                .clickBuildDropdownMenuDeleteBuild("#1")
                .clickDelete(new FreestyleProjectPage(getDriver()))
                .isNoBuildsDisplayed();

        Assert.assertTrue(noBuildsMessage, "Error");
    }

    @Test
    public void testDeleteBuildNowFromSideMenu() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        boolean noBuildsMessage = new MainPage(getDriver())
                .clickBuildByGreenArrow(FREESTYLE_NAME)
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickLastBuildLink()
                .clickDeleteBuild(new FreestyleProjectPage(getDriver()))
                .clickYesButton()
                .isNoBuildsDisplayed();

        Assert.assertTrue(noBuildsMessage, "error! No builds message is not display");
    }

    @Test
    public void testDeleteBuildNowFromLastBuild() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        boolean buildMessage = new MainPage(getDriver())
                .clickBuildByGreenArrow(FREESTYLE_NAME)
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .deleteBuildPermalinksLastBuildDropDown()
                .clickYesButton()
                .isNoBuildsDisplayed();

        Assert.assertTrue(buildMessage, "error! No builds message is not display");
    }

    @Test
    public void testDeleteBuildNowFromBuildPage() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        boolean noBuildsMessage = new MainPage(getDriver())
                .clickBuildByGreenArrow(FREESTYLE_NAME)
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickLastBuildLink()
                .clickDeleteBuild(new FreestyleProjectPage(getDriver()))
                .clickYesButton()
                .isNoBuildsDisplayed();

        Assert.assertTrue(noBuildsMessage, "error! No builds message is not display");
    }

    @Test
    public void testKeepThisBuildForever() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);
        final List<String> buildKeepForeverMenuOptions = new ArrayList<>(List.of(
                "Changes", "Console Output", "Edit Build Information"));

        FreestyleProjectPage freestyleProjectPage = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickBuildNowFromSideMenu()
                .clickBuildDateFromBuildRow()
                .clickKeepBuildForever()
                .getBreadcrumb()
                .clickJobNameFromBreadcrumb(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .openBuildsDropDownMenu();

        Assert.assertEquals(freestyleProjectPage.getTextBuildDropDownMenuOptions(), buildKeepForeverMenuOptions);
        Assert.assertTrue(freestyleProjectPage.isIconLockIsDispalyed(), "The lock icon is not displayed");
    }

    @Test
    public void testVisibleProjectNameOnProjectPage() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String projectNameOnProjectPage = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .getJobName();

        Assert.assertEquals(projectNameOnProjectPage, "Project " + FREESTYLE_NAME);
    }

    @Test
    public void testDisableFromProjectPage() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        FreestyleProjectPage projectName = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickDisable();

        List<String> dropDownMenu = new MainPage(getDriver())
                .getListOfProjectMenuItems(FREESTYLE_NAME);

        SoftAssert soft = new SoftAssert();
        soft.assertFalse(dropDownMenu.contains("Build Now"), "'Build Now' option is present in drop-down menu");
        soft.assertEquals(projectName.getDisabledMessageText(), "This project is currently disabled");
        soft.assertEquals(projectName.getEnableButtonText(), "Enable");
        soft.assertAll();
    }

    @Test
    public void testEnableFromProjectPage() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        FreestyleProjectPage projectName = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickDisable()
                .clickEnable();

        SoftAssert soft = new SoftAssert();
        soft.assertEquals(projectName.getDisableButtonText(), "Disable Project");
        soft.assertTrue(projectName.clickConfigure().isEnabledDisplayed(), "'Enabled' is not displayed");
        soft.assertEquals(projectName.getHeader().clickLogo().getJobBuildStatusIcon(FREESTYLE_NAME), "Not built");
        soft.assertAll();
    }

    @Test
    public void testPreviewDescriptionFromProjectPage() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String previewDescription = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickAddOrEditDescription()
                .enterDescription(DESCRIPTION_TEXT)
                .clickPreviewDescription()
                .getPreviewDescriptionText();

        Assert.assertEquals(previewDescription, "DESCRIPTION_TEXT");
    }

    @Test
    public void testAddDescriptionFromProjectPage() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String actualDescription = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .addDescription("Freestyle project")
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(actualDescription, "Freestyle project");
    }

    @Test
    public void testEditDescription() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String editDescription = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickAddOrEditDescription()
                .clearDescriptionField()
                .enterDescription(NEW_DESCRIPTION_TEXT)
                .clickSaveButtonDescription()
                .getDescriptionText();

        Assert.assertEquals(editDescription, NEW_DESCRIPTION_TEXT);
    }

    @Test
    public void testNavigateToChangePage() {
        TestUtils.createJob(this, "Engineer", TestUtils.JobType.FreestyleProject, true);

        String text = new MainPage(getDriver())
                .clickJobName("Engineer", new FreestyleProjectPage(getDriver()))
                .clickChangeOnLeftSideMenu()
                .getTextOfPage();

        Assert.assertTrue(text.contains("No builds."),
                "In the Freestyle project Changes chapter, not displayed status of the latest build.");
    }

    @Test
    public void testNavigateToWorkspaceFromProjectPage(){
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String workspacePage = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickBuildNowFromSideMenu()
                .clickWorkspaceFromSideMenu()
                .getTextFromWorkspacePage();

        Assert.assertEquals(workspacePage, "Workspace of FREESTYLE_NAME on Built-In Node");
    }

    @Test
    public void testPreviewDescriptionFromConfigurationPage() {
        final String descriptionText = "In publishing and graphic design, Lorem ipsum is a placeholder " +
                "text commonly used to demonstrate the visual form of a document or a typeface without relying .";
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String previewText = new MainPage(getDriver())
                .clickConfigureDropDown(FREESTYLE_NAME, new FreestyleProjectConfigPage(new FreestyleProjectPage(getDriver())))
                .addDescription(descriptionText)
                .clickPreview()
                .getPreviewText();

        String actualDescriptionText = new FreestyleProjectPage(getDriver())
                .clickSaveButtonDescription()
                .getDescriptionText();

        Assert.assertEquals(previewText, descriptionText);
        Assert.assertEquals(actualDescriptionText, descriptionText);
    }

    @Test
    public void testAddDescriptionFromConfigurationPage() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, false);

        String description = new FreestyleProjectPage(getDriver())
                .clickConfigure()
                .addDescription(DESCRIPTION_TEXT)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(description, DESCRIPTION_TEXT);
    }

    @Test
    public void testAccessConfigurationPageFromDashboard() {
        final String breadcrumb = "Dashboard > " + FREESTYLE_NAME + " > Configuration";
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        FreestyleProjectConfigPage freestyleConfigPage = new MainPage(getDriver())
                .clickConfigureDropDown(
                        FREESTYLE_NAME, new FreestyleProjectConfigPage(new FreestyleProjectPage(getDriver()))
                );

        Assert.assertEquals(freestyleConfigPage.getBreadcrumb().getFullBreadcrumbText(), breadcrumb);
        Assert.assertEquals(freestyleConfigPage.getHeaderText(), "Configure");
    }

    @Test
    public void testAccessConfigurationPageFromProjectPage() {
        final String breadcrumbRoute = "Dashboard > " + FREESTYLE_NAME + " > Configuration";
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        FreestyleProjectConfigPage freestyleConfigPage = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure();

        Assert.assertEquals(freestyleConfigPage.getBreadcrumb().getFullBreadcrumbText(), breadcrumbRoute);
        Assert.assertEquals(freestyleConfigPage.getHeaderText(), "Configure");
    }

    @Test
    public void testDisableFromConfigurationPage() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        FreestyleProjectPage freestyleProjectPage = new MainPage(getDriver())
                .clickConfigureDropDown(FREESTYLE_NAME, new FreestyleProjectConfigPage(new FreestyleProjectPage(getDriver())))
                .clickSwitchEnableOrDisable()
                .clickSaveButton();

        String availableMode = freestyleProjectPage
                .getEnableButtonText();

        MainPage mainPage = freestyleProjectPage
                .getHeader()
                .clickLogo();

        Assert.assertEquals(availableMode, "Enable");
        Assert.assertEquals(mainPage.getJobBuildStatusIcon(FREESTYLE_NAME), "Disabled");
        Assert.assertFalse(mainPage.isScheduleBuildOnDashboardAvailable(FREESTYLE_NAME), "Error: disabled project cannot be built");
    }

    @Test
    public void testSetParametersToDiscardOldBuilds() {
        final int daysToKeepBuilds = 3;
        final int maxOfBuildsToKeep = 5;
        TestUtils.createJob(this, NEW_FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        FreestyleProjectConfigPage freestyleProjectConfigPage = new MainPage(getDriver())
                .clickJobName(NEW_FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .clickOldBuildCheckBox()
                .enterDaysToKeepBuilds(daysToKeepBuilds)
                .enterMaxNumOfBuildsToKeep(maxOfBuildsToKeep)
                .clickSaveButton()
                .clickConfigure();

        Assert.assertEquals(Integer
                .parseInt(freestyleProjectConfigPage.getDaysToKeepBuilds()), daysToKeepBuilds);
        Assert.assertEquals(Integer
                .parseInt(freestyleProjectConfigPage.getMaxNumOfBuildsToKeep()), maxOfBuildsToKeep);
    }

    @Test
    public void testAddingAProjectOnGitHubToTheFreestyleProject() {
        final String expectedNameRepo = "Sign in";
        TestUtils.createJob(this, NEW_FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        final String actualNameRepo = new MainPage(getDriver())
                .clickJobName(NEW_FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .clickGitHubProjectCheckbox()
                .inputTextTheInputAreaProjectUrlInGitHubProject(GITHUB_URL)
                .clickSaveButton()
                .getHeader()
                .clickLogo()
                .selectFromJobDropdownMenuTheGitHub(NEW_FREESTYLE_NAME);

        Assert.assertEquals(actualNameRepo, expectedNameRepo);
    }

    @Test
    public void testAddBooleanParameterTheFreestyleProject() {
        final String booleanParameter = "Boolean Parameter";
        final String booleanParameterName = "Boolean";
        TestUtils.createJob(this, NEW_FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        final boolean checkedSetByDefault = new MainPage(getDriver())
                .clickJobName(NEW_FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .checkProjectIsParametrized()
                .openAddParameterDropDown()
                .selectParameterInDropDownByType(booleanParameter)
                .inputBooleanParameterName(booleanParameterName)
                .selectCheckboxSetByDefault()
                .clickSaveButton()
                .clickBuildWithParameters()
                .checkedTrue();

        Assert.assertTrue(checkedSetByDefault);
    }

    @Test
    public void testAddChoiceParameter() {
        final String parameterType = "Choice Parameter";
        final String parameterName = "Choice parameter name test";
        final String parameterDesc = "Choice parameter desc test";
        final List<String> parameterChoicesList = new ArrayList<>() {{
            add("choice one");
            add("choice two");
            add("choice three");
        }};
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, false);

        BuildWithParametersPage buildPage = new FreestyleProjectPage(getDriver())
                .clickConfigure()
                .checkProjectIsParametrized()
                .openAddParameterDropDown()
                .selectParameterInDropDownByType(parameterType)
                .inputBooleanParameterName(parameterName)
                .inputParameterChoices(parameterChoicesList)
                .inputParameterDesc(parameterDesc)
                .clickSaveButton()
                .clickBuildWithParameters();

        Assert.assertTrue(buildPage.isParameterNameDisplayed(parameterName));
        Assert.assertEquals(buildPage.getParameterDescription(), parameterDesc);
        Assert.assertEquals(buildPage.getChoiceParametersValuesList(), parameterChoicesList);
    }

    @Test
    public void testSetRateLimitForBuilds() {
        final String timePeriod = "Week";
        TestUtils.createJob(this, NEW_FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        final String actualTimePeriod = new MainPage(getDriver())
                .clickJobName(NEW_FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .checkThrottleBuilds()
                .selectTimePeriod(timePeriod)
                .clickSaveButton()
                .clickConfigure()
                .getTimePeriodText();

        Assert.assertEquals(actualTimePeriod, timePeriod);
    }

    @Test
    public void testAllowParallelBuilds() {

        TestUtils.createJob(this, NEW_FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        final boolean statusExecuteConcurrentBuilds = new MainPage(getDriver())
                .clickJobName(NEW_FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .clickCheckBoxExecuteConcurrentBuilds()
                .clickSaveButton()
                .clickConfigure()
                .isExecuteConcurrentBuildsSelected();

        Assert.assertTrue(statusExecuteConcurrentBuilds, "ExecuteConcurrentBuilds is not Selected");
    }

    @Test
    public void testSetPeriodForJenkinsToWaitBeforeActuallyStartingTriggeredBuild() {
        final String expectedQuietPeriod = "10";
        TestUtils.createJob(this, NEW_FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        final String actualQuietPeriod = new MainPage(getDriver())
                .clickJobName(NEW_FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .clickAdvancedDropdownMenu()
                .clickQuietPeriod()
                .inputQuietPeriod(expectedQuietPeriod)
                .clickSaveButton()
                .clickConfigure()
                .clickAdvancedDropdownMenu()
                .getQuietPeriod();

        Assert.assertEquals(actualQuietPeriod, expectedQuietPeriod);
    }

    @Test
    public void testSetNumberOfCountForJenkinsToCheckOutFromTheSCMUntilItSucceeds() {
        final String retryCount = "5";
        TestUtils.createJob(this, NEW_FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        final String actualRetryCount = new MainPage(getDriver())
                .clickJobName(NEW_FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .clickAdvancedDropdownMenu()
                .clickRetryCount()
                .inputSCMCheckoutRetryCount(retryCount)
                .clickSaveButton()
                .clickConfigure()
                .clickAdvancedDropdownMenu()
                .getCheckoutRetryCountSCM();

        Assert.assertEquals(actualRetryCount, retryCount);
    }

    @Test
    public void testEnableJenkinsToBlockBuildsWhenUpstreamProjectIsBuilding() {
        TestUtils.createJob(this, NEW_FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        final boolean statusBlockBuildWhenUpstreamProjectIsBuilding = new MainPage(getDriver())
                .clickJobName(NEW_FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .clickAdvancedDropdownMenu()
                .clickBlockBuildWhenUpstreamProjectIsBuilding()
                .clickSaveButton()
                .clickConfigure()
                .clickAdvancedDropdownMenu()
                .getTrueBlockBuildWhenUpstreamProjectIsBuilding();

        Assert.assertTrue(statusBlockBuildWhenUpstreamProjectIsBuilding, "error input is not selected");
    }

    @Test
    public void testAddDisplayName() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String displayName = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .clickAdvancedDropdownMenu()
                .setDisplayName(NEW_FREESTYLE_NAME)
                .clickSaveButton()
                .getJobName();

        Assert.assertEquals(displayName, "Project " + NEW_FREESTYLE_NAME);
    }

    @Test
    public void testAddRepositoryFromSourceCodeManagement() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String repositoryUrl = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .clickSourceCodeManagementLink()
                .clickRadioButtonGit()
                .inputRepositoryUrl(GITHUB_URL)
                .clickSaveButton()
                .getHeader()
                .clickLogo()
                .clickConfigureDropDown(FREESTYLE_NAME, new FreestyleProjectConfigPage(new FreestyleProjectPage(getDriver())))
                .clickSourceCodeManagementLink()
                .getRepositoryUrlText();

        Assert.assertEquals(repositoryUrl, GITHUB_URL);
    }
    @Ignore
    @Test
    public void testAddBranchFromSourceCodeManagement() {
        final String branchName = "for_jenkins_build";
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        GitBuildDataPage gitBuildDataPage = new MainPage(getDriver())
                .clickConfigureDropDown(FREESTYLE_NAME, new FreestyleProjectConfigPage(new FreestyleProjectPage(getDriver())))
                .clickSourceCodeManagementLink()
                .clickRadioButtonGit()
                .inputRepositoryUrl(NEW_GITHUB_URL)
                .correctMainBranchName()
                .clickAddBranchButton()
                .inputAddBranchName(branchName)
                .clickSaveButton()
                .clickBuildNowFromSideMenu()
                .clickIconAdditionalBranchBuild()
                .clickGitBuildDataLink();

        Assert.assertEquals(gitBuildDataPage.getNamesOfBuiltBranches(), "origin/main, origin/for_jenkins_build");
        Assert.assertEquals(gitBuildDataPage.getRepositoryName(), NEW_GITHUB_URL);
    }

    @Test
    public void testConfigureBuildTriggersBuildAfterOtherProjectsAreBuilt() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);
        TestUtils.createJob(this, NEW_FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String lastBuildInfo = new MainPage(getDriver())
                .clickConfigureDropDown(FREESTYLE_NAME, new FreestyleProjectConfigPage(new FreestyleProjectPage(getDriver())))
                .clickBuildAfterOtherProjectsAreBuiltCheckBox()
                .inputProjectsToWatch(NEW_FREESTYLE_NAME)
                .clickSaveButton()
                .getHeader()
                .clickLogo()
                .clickJobDropdownMenuBuildNow(NEW_FREESTYLE_NAME)
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickLastBuildLink()
                .getBuildInfo();

        Assert.assertEquals(lastBuildInfo, "Started by upstream project " + NEW_FREESTYLE_NAME);
    }

    @Test
    public void testBuildStepsOptions() {
        List<String> expectedOptionsInBuildStepsSection = List.of("Execute Windows batch command", "Execute shell",
                "Invoke Ant", "Invoke Gradle script", "Invoke top-level Maven targets", "Run with timeout",
                "Set build status to \"pending\" on GitHub commit");

        List<String> actualOptionsInBuildStepsSection = new MainPage(getDriver())
                .clickNewItem()
                .enterItemName(FREESTYLE_NAME)
                .selectJobType(TestUtils.JobType.FreestyleProject)
                .clickOkButton(new FreestyleProjectConfigPage(new FreestyleProjectPage(getDriver())))
                .openBuildStepOptionsDropdown()
                .getOptionsInBuildStepDropdown();

        Assert.assertEquals(actualOptionsInBuildStepsSection, expectedOptionsInBuildStepsSection);
    }

    @Test
    public void testBuildStepsExecuteWindowsBatchCommand(){
        final String commandFieldText = "echo Hello";
        final String cmdCommand = "$ cmd /c call";

        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String consoleOutput = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .openBuildStepOptionsDropdown()
                .selectExecuteWindowsBatchCommandBuildStep()
                .addExecuteWindowsBatchCommand(commandFieldText)
                .clickSaveButton()
                .clickBuildNowFromSideMenu()
                .clickIconBuildOpenConsoleOutput(1)
                .getConsoleOutputText();

        Assert.assertTrue(consoleOutput.contains(cmdCommand), "Command wasn't run");
    }

    @Test
    public void testBuildStepsExecuteShell() {
        final String commandFieldText = "echo Hello";

        String consoleOutput = new MainPage(getDriver())
                .clickNewItem()
                .enterItemName(FREESTYLE_NAME)
                .selectJobType(TestUtils.JobType.FreestyleProject)
                .clickOkButton(new FreestyleProjectConfigPage(new FreestyleProjectPage(getDriver())))
                .addExecuteShellBuildStep(commandFieldText)
                .clickSaveButton()
                .clickBuildNowFromSideMenu()
                .clickIconBuildOpenConsoleOutput(1)
                .getConsoleOutputText();

        Assert.assertTrue(consoleOutput.contains(commandFieldText), "Command wasn't run OR test was run on the Windows");
        Assert.assertTrue(consoleOutput.contains("Finished: SUCCESS"), "Build wasn't finished successfully");
    }

    @Test
    public void testBuildStepsInvokeMavenGoalsTargets() {
        String goals = "clean";
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String mavenGoals = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .openBuildStepOptionsDropdown()
                .addInvokeMavenGoalsTargets(goals)
                .clickSaveButton()
                .clickConfigure()
                .getMavenGoals();

        Assert.assertEquals(mavenGoals, goals);
    }

    @Test
    public void testConfigurePostBuildActionsAggregateDownStreamTestResults() {
        BuildPage buildPage = new MainPage(getDriver())
                .clickNewItem()
                .enterItemName(FREESTYLE_NAME)
                .selectJobType(TestUtils.JobType.FreestyleProject)
                .clickOkButton(new FreestyleProjectConfigPage(new FreestyleProjectPage(getDriver())))
                .clickPostBuildActionsButton()
                .clickAddPostBuildActionDropDown()
                .clickAggregateDownstreamTestResults()
                .clickSaveButton()
                .clickBuildNowFromSideMenu()
                .clickLastBuildLink();

        Assert.assertTrue(buildPage.isDisplayedAggregatedTestResultLink());
        Assert.assertEquals(buildPage.getTestResultsNodeText(), "Aggregated Test Result (no tests)");
        Assert.assertTrue(buildPage.getAggregateTestResultSideMenuLinkText().contains("/job/FREESTYLE_NAME/lastBuild/aggregatedTestReport"));
    }

    @Test
    public void testConfigurePostBuildActionArchiveArtifacts() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String archiveTheArtifacts = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .clickPostBuildActionsButton()
                .clickAddPostBuildActionDropDown()
                .clickArchiveTheArtifacts()
                .clickSaveButton()
                .clickConfigure()
                .clickPostBuildActionsButton()
                .getTextArchiveArtifacts();

        Assert.assertEquals(archiveTheArtifacts, "Archive the artifacts\n" +
                "?\n" +
                "Files to archive\n" +
                "?\n" +
                "Advanced");
    }

    @Test
    public void testConfigurePostBuildActionBuildOtherProjects() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);
        TestUtils.createJob(this, NEW_FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String lastBuildInfo = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .clickPostBuildActionsButton()
                .clickAddPostBuildActionDropDown()
                .clickBuildOtherProjects()
                .setBuildOtherProjects(NEW_FREESTYLE_NAME)
                .clickSaveButton()
                .clickBuildNowFromSideMenu()
                .getHeader()
                .clickLogo()
                .clickJobName(NEW_FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickLastBuildLink()
                .getBuildInfo();

        Assert.assertEquals(lastBuildInfo, "Started by upstream project " + FREESTYLE_NAME);
    }

    @Test
    public void testAddGitPublisherInPostBuildActions() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String gitPublisherText = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .clickPostBuildActionsButton()
                .clickAddPostBuildActionDropDown()
                .clickGitPublisher()
                .clickSaveButton()
                .clickConfigure()
                .getGitPublisherText();

        Assert.assertEquals(gitPublisherText, "Git Publisher\n?");
    }

    @Test
    public void testAddEmailNotificationToPostBuildActions() {
        final String email = "email@email.com";
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String currentEmail = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .clickPostBuildActionsButton()
                .clickAddPostBuildActionDropDown()
                .clickEmailNotification()
                .setEmailNotification(email)
                .clickSaveButton()
                .clickConfigure()
                .clickPostBuildActionsButton()
                .getEmailNotificationFieldText();

        Assert.assertEquals(currentEmail, email);
    }

    @Test
    public void testConfigurePostBuildActionEditableEmailNotification() {
        String username = "jenkins05test@gmail.com";
        String expectedConsoleOutputText = "Sending email to: jenkins05test@gmail.com";

        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);
        TestUtils.manageJenkinsEmailNotificationSetUp(this);

        String emailSentLog = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .clickPostBuildActionsButton()
                .clickAddPostBuildActionDropDown()
                .selectEditableEmailNotification()
                .inputEmailIntoProjectRecipientListInputField(username)
                .clickSaveButton()
                .clickBuildNowButtonSideMenu()
                .clickBuildIconStatus()
                .getConsoleOutputText();

        Assert.assertTrue(emailSentLog.contains(expectedConsoleOutputText), "Error: Email report wasn't sent");

        TestUtils.manageJenkinsEmailNotificationGoingBackToOriginalSettings(this);
    }

    @Test
    public void testSetGitHubCommitStatusToPostBuildActions() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String commitContextName = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .clickPostBuildActionsButton()
                .clickAddPostBuildActionDropDown()
                .clickSetGitHubCommitStatus()
                .setGitHubCommitStatusContext(FREESTYLE_NAME)
                .clickSaveButton()
                .clickConfigure()
                .clickPostBuildActionsButton()
                .getGitHubCommitStatus();

        Assert.assertEquals(commitContextName, FREESTYLE_NAME);
    }

    @Test
    public void testDeleteWorkspaceWhenBuildDonePostBuildActions() {
        String expectedWorkspaceStatus = "Error: no workspace";
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String actualWorkspaceStatus = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .clickPostBuildActionsButton()
                .clickAddPostBuildActionDropDown()
                .clickDeleteWorkspaceWhenBuildDone()
                .clickSaveButton()
                .clickBuildNowFromSideMenu()
                .clickWorkspaceFromSideMenu()
                .getTextFromWorkspacePage();

        Assert.assertEquals(actualWorkspaceStatus, expectedWorkspaceStatus);
    }

    @Test
    public void testCancelDeletingFromDropDownMenu() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        boolean projectIsPresent = new MainPage(getDriver())
                .dropDownMenuClickDelete(FREESTYLE_NAME)
                .dismissAlert()
                .getHeader()
                .clickLogo()
                .jobIsDisplayed(FREESTYLE_NAME);

        Assert.assertTrue(projectIsPresent, "Error: the name of the Freestyle project is not shown");
    }

    @Test
    public void testCancelDeletingFromSideMenu() {
        TestUtils.createJob(this, NEW_FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        boolean isProjectPresent = new MainPage(getDriver())
                .clickJobName(NEW_FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickDeleteAndCancel()
                .getHeader()
                .clickLogo()
                .verifyJobIsPresent(NEW_FREESTYLE_NAME);

        Assert.assertTrue(isProjectPresent, "error! project is not displayed!");
    }

    @Test
    public void testDeleteItemFromDropDown() {
        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject,true);
        MainPage welcomeIsDisplayed= new MainPage(getDriver())
                .dropDownMenuClickDelete(FREESTYLE_NAME)
                .acceptAlert();

        Assert.assertTrue(welcomeIsDisplayed.isWelcomeDisplayed());
        Assert.assertEquals(welcomeIsDisplayed.clickMyViewsSideMenuLink().getStatusMessageText(), "This folder is empty");
    }

    @Test
    public void testDeleteItemFromSideMenu() {
        TestUtils.createJob(this, NEW_FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        boolean isProjectPresent = new MainPage(getDriver())
                .clickJobName(NEW_FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickDeleteAndAccept()
                .isWelcomeDisplayed();

        Assert.assertTrue(isProjectPresent, "error was not show Welcome to Jenkins!");
    }

    @Test
    public void testUseCustomWorkspaceFromConfigureGeneralAdvanced() {
        String directoryName = "My directory";

        TestUtils.createJob(this, FREESTYLE_NAME, TestUtils.JobType.FreestyleProject, true);

        String actualConsoleOutputText = new MainPage(getDriver())
                .clickJobName(FREESTYLE_NAME, new FreestyleProjectPage(getDriver()))
                .clickConfigure()
                .clickAdvancedGeneral()
                .clickUseCustomWorkspace(directoryName)
                .clickSaveButton()
                .clickBuildNowFromSideMenu()
                .getHeader()
                .clickLogo()
                .openLastBuildDropDownMenu()
                .clickConsoleOutputLastBuildDropDown()
                .getConsoleOutputText();

        Assert.assertTrue(actualConsoleOutputText.contains(directoryName), "Error: Directory is not used!");
    }
}
