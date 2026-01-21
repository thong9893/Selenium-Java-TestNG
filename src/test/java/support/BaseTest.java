package support;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.DashboardPage;
import pages.LoginPage;
import pages.PageGenerator;

import java.io.File;
import java.io.IOException;

public class BaseTest {
    protected WebDriver driver;
    protected LoginPage loginPage;
    protected DashboardPage dashboardPage;
    protected BasePage basePage;

    @Parameters({"browser"})
    @BeforeClass
    public void setup(String browser){
        driver = DriverFactory.createDriver(browser);
        driver.get(GlobalConstance.SITE_URL);
        basePage = new BasePage(driver);
        loginPage = PageGenerator.getLoginPage(driver);
        dashboardPage = loginPage.login(GlobalConstance.ADMIN_USERNAME, GlobalConstance.ADMIN_PASSWORD);
    }
    @AfterClass
    public void tearDown(){
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
    @BeforeSuite
    public void deleteReportFolder() {
        deleteAllFileInFolder("allure-results");
    }
    @AfterMethod
    public void takeScreenshotOnFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            screenshot(result.getName());
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
    private void deleteAllFileInFolder(String folderName) {
        try {
            String pathFolderDownload = GlobalConstance.PROJECT_PATH + File.separator + folderName;
            File file = new File(pathFolderDownload);
            File[] listOfFiles = file.listFiles();
            if (listOfFiles != null && listOfFiles.length > 0) {
                for (File f : listOfFiles) {
                    if (f.isFile() && !"environment.properties".equals(f.getName())) {
                        f.delete();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void screenshot(String fileName) {
        File directory = new File("screenshots");
        if (!directory.exists()) {
            directory.mkdir();
        }
        File scrFile = ((TakesScreenshot)  driver).getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(scrFile, new File("screenshots/" + fileName + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
