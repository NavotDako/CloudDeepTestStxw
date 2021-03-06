package TestPlanSuite.cloudEntities;

import MyMain.BaseRunner;
import MyMain.Enums;
import MyMain.Main;
import Utils.Utilities;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by khaled.abbas on 11/29/2017.
 */
public class SeleniumHelper {
    private boolean loggedIn = false;
    private Project project;
    private User user;
    WebDriver driver;
    private BaseRunner runner;

    public SeleniumHelper(User user, Project project, BaseRunner thread) {

        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability("platformName", "chrome");
        dc.setCapability("username", Main.cs.getUser());
        dc.setCapability("password", Main.cs.getPass());
//        dc.setCapability("projectName", Main.cloudServer.getProject()); //only required if your user has several projects assigned to it. Otherwise, exclude this capability.
        dc.setCapability("generateReport", true);
        dc.setCapability("testName", thread.getName());
        dc.setCapability("newSessionWaitTimeout", 90);
        dc.setCapability(CapabilityType.BROWSER_NAME, BrowserType.CHROME);
        dc.setCapability("newCommandTimeout", 120);

        try {
            driver = new RemoteWebDriver(new URL("http://" + Main.cs.getServerHostName() + ":" + Main.cs.getPort() + "/wd/hub/"), dc);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
//        driver = new ChromeDriver();
        this.project = project;
        this.user = user;
        this.runner = thread;
    }

    private boolean login() {
        if (this.loggedIn) {
            return true;
        }
        driver.get("http://" + Main.cs.getServerHostName() + ":" + Main.cs.getPort());

        //needs login
        try {
            Utilities.log(runner, "logging in to Cloud UI");
            driver.findElement(By.xpath("//*[@placeholder='Username']"));
            driver.findElement(By.xpath("//*[@placeholder='Username']")).sendKeys(user.getUserName());
            driver.findElement(By.xpath("//*[@placeholder='Password']")).sendKeys("Experitest2012");
            driver.findElement(By.xpath("//*[@name='login']")).click();
            try {
                Utilities.log(runner, "Selecting testPlanProject");
                (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@name='selectProject']")));
                String choseProject = "//*[@name='name']";
                WebElement projSelect = driver.findElement(By.xpath(choseProject));
                Select selectObj = new Select(projSelect);
                WebElement projToSelect = selectObj.getOptions().stream().filter(p -> p.getAttribute("label").equals(this.project.getProjName())).collect(Collectors.toList()).get(0);
                Thread.sleep(1500);
                projSelect.click();
                Thread.sleep(1500);
                projToSelect.click();
                driver.findElement(By.xpath("//*[@name='selectProject']")).click();
            } catch (Exception e) {
//                    Utilities.log(e);
                Utilities.log(runner, "failed to navigate to correct testPlanProject: " + project.getProjName());
                Utilities.log(runner, e.getMessage());
                this.loggedIn = true;
                return true;
            }

        } catch (Exception e) {
            Utilities.log(e);
            Utilities.log(runner, "failed to login - testPlanUser: " + user.getUserName());
            Utilities.log(runner, e.getMessage());
            return false;
        }
        this.loggedIn = true;
        return true;
    }

    /**
     * Navigate to testPlan page, gets all test plans in project
     *
     * @return List of test plans
     */
    public List<TestPlan> getProjectTestPlans() {
        String testPlanName;
        Enums.OS os;
        login();
        navigateToTestPlansPage();
        ArrayList<TestPlan> testPlans = new ArrayList<TestPlan>();
        Utilities.log(runner, "getting the testPlanProject test plans");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Utilities.log(e);
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> TestPlans = driver.findElements(By.xpath("//*[@st-select-row='plan']"));
        for(WebElement testToPrint: TestPlans){
            Utilities.log(runner,"-----test plan ---- " + testToPrint.getText());
        }
        for (int i = 0; i < TestPlans.size(); i++) {

            try {
                Utilities.log(runner, "parsing the test plan " + TestPlans.get(i).getText());
                String[] currTestPlan = TestPlans.get(i).getText().trim().replaceAll(" +", " ").split(" ");
                testPlanName = currTestPlan[0];
                int currIDX = 1;
                String currString = currTestPlan[currIDX];
                while ((!currString.toLowerCase().equals("ios")) && !currString.toLowerCase().equals("android")) {
                   Utilities.log(runner,currString);
                    testPlanName += " " + currString;
                    currString = currTestPlan[++currIDX];
                }

                os = currString.equalsIgnoreCase("ios") ? Enums.OS.IOS : Enums.OS.ANDROID;
//                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", TestPlans.get(i));

                TestPlans.get(i).click();
            } catch (StaleElementReferenceException e) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                TestPlans = driver.findElements(By.xpath("//*[@st-select-row='plan']"));
                i = i - 1;
                continue;
            }

            Utilities.log(runner, "chose the test plan, and get info");
            driver.findElement(By.xpath("//*[@id=\"content-after-toolbar\"]/div/div[1]/button/md-icon")).click();
            ArrayList<String> deviceQueries = new ArrayList<>();

            List<WebElement> deviceWebElements = driver.findElements(By.xpath("//*[@ng-repeat='row in executionPlansCtrl.selectedRow.deviceQueries']"));
            for (int j = 0; j < deviceWebElements.size(); j++) {
                try {
                    deviceQueries.add(deviceWebElements.get(j).getText());
                } catch (StaleElementReferenceException e) {
                    j = j - 1;
                }
            }
            Utilities.log(runner, "Finished adding the current plan to test plans from testPlanProject - " + this.project.getProjName());
            Utilities.log(runner, "found test plan with name" + testPlanName);
            testPlans.add(new TestPlan(os, deviceQueries, this.project.getProjName(), testPlanName, this.project.getProjId()));
            Utilities.log(runner,"created a new test plan object " + testPlans.get(testPlans.size()-1).toString());
        }
        return testPlans;
    }

    public boolean checkIfProjectHasTestPlans() {

        try {
            if (!login()) {
                return false;
            }
            Utilities.log(runner, "navigate to testPlanProject test plans");
            // navigate to test plans page
            navigateToTestPlansPage();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@st-select-row='plan']")));
            List<TestPlan> testPlanList = getProjectTestPlans();
            boolean hasAndroid = testPlanList.stream().filter(p -> p.getOS().equals(Enums.OS.ANDROID)).collect(Collectors.toList()).size() > 0;
            boolean hasIOS = testPlanList.stream().filter(p -> p.getOS().equals(Enums.OS.IOS)).collect(Collectors.toList()).size() > 0;
            Utilities.log(runner, ("testPlanProject - " + project.getProjId() + ", has android- " + hasAndroid + ", has IOS " + hasIOS));
            return hasAndroid && hasIOS;
        } catch (Exception e) {
//            Utilities.log(e);
            Utilities.log(runner, "looks like this testPlanProject " + this.project.getProjName() + " does not have test plans");
            return false;
        }
    }

    public List<TestPlan> createTestPlans() {
        Utilities.log(runner, "create test plans for android + ios");
        ArrayList<TestPlan> testPlans = new ArrayList<TestPlan>();

        //Login to cloud and navigate to project page
        login();

        testPlans.add(createTestPlanForOS(Enums.OS.ANDROID));
        testPlans.add(createTestPlanForOS(Enums.OS.IOS));

        return testPlans;
    }

    public TestPlan createTestPlanForOS(Enums.OS os) {

        navigateToTestPlansPage();
        Utilities.log(runner, "create test plan for os - " + os);
        ArrayList<String> deviceQueries = new ArrayList<>();
        String testPlanName = "TestPlan" + os + (int) (Math.random() * 1000);
        //Click on create button
        String createButton = "//*[@title='Create']";
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(createButton)));

        driver.findElement(By.xpath(createButton)).click();


        String executionName = "//*[@name='test_name']";
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(executionName)));
        driver.findElement(By.xpath(executionName)).sendKeys(testPlanName);

        Utilities.log(runner, "upload the application");
        uploadApp(os);
        Utilities.log(runner, "choose the devices");
        pickDevicesToAdd(os, deviceQueries);

        String finishCreatingTestPlan = "//*[@class='white-color md-accent md-raised md-button md-ink-ripple md-button']";
        driver.findElements(By.xpath(finishCreatingTestPlan)).stream().filter(p -> p.getText().
                equalsIgnoreCase("finish")).collect(Collectors.toList()).get(0).click();
        try {
            Utilities.log(runner, "Created test plan, " + testPlanName + " waiting for 5 seconds before verifying it exists");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Utilities.log(e);
        }

        Assert.assertTrue("test plan was not created", testPlanExists(testPlanName));
        return new TestPlan(os, deviceQueries, this.project.getProjName(), testPlanName, this.project.getProjId());
    }

    private void navigateToTestPlansPage() {
        Utilities.log(runner, "navigate to testPlanProject test plans page");
//        driver.manage().window().fullscreen();

        try {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.findElement(By.xpath("//*[@st-select-row='plan']"));
            Utilities.log(runner, "already in testPlansPage");
            return;
        } catch (Exception e) {
//            Utilities.log(e);
        }
//        driver.navigate().refresh();
        // navigate to test plans page
        try {
            (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Resources']")));
            driver.findElement(By.xpath("//*[text()='Resources']")).click();
            //Click on execution configuration
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@class='fa fa-list-ul']")).click();

        } catch (Exception e) {
//            Utilities.log(e);
            Utilities.log(runner, "Did not navigate to test plans page");
        }
    }

    private boolean testPlanExists(String testPlan) {
        Utilities.log(runner, "does test plan " + testPlan + " exists?");
        return getProjectTestPlans().stream().filter(p -> p.getTestPlanName().equals(testPlan)).count() > 0;
    }

    private void uploadApp(Enums.OS os) {
        //Use robot to upload app based on path
        String appPath = os.equals(Enums.OS.ANDROID) ? "lib/testApps/app-eribank.apk" : "lib/testApps/Monster Island Game.ipa";
        //Use robot to upload app based on path
        String testAppPath = os.equals(Enums.OS.ANDROID) ? "lib/testApps/app-eribank-androidTest-1Fail.apk" : "lib/testApps/Monster Island GameUITests-Runner (2).zip";

//        List<WebElement> applicationUploadButtons = driver.findElements(By.xpath("//input[@id='select-file-upload']"));
        Utilities.log(runner, "uploading the test application " + testAppPath + "for OS " + os);
        Utilities.log(runner, "uploading the application " + appPath + "for OS " + os);
        driver.findElement(By.xpath("//input[@id='select-file-upload']")).sendKeys(new File(appPath).getAbsolutePath());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Utilities.log(e);
        }
        driver.findElement(By.xpath("//input[@id='select-test-file-upload']")).sendKeys(new File(testAppPath).getAbsolutePath());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Utilities.log(e);
        }
        String submit = "//*[@type='submit']";
        driver.findElement(By.xpath(submit)).click();
    }

    private void pickDevicesToAdd(Enums.OS os, ArrayList<String> deviceQueries) {
        //Pick devices
        String advancedElem = "//*[@class='md-tab md-ink-ripple']";
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(advancedElem)));
        driver.findElement(By.xpath(advancedElem)).click();

        String queryBox = "//*[@name='query']";

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(queryBox)));
        driver.findElement(By.xpath(queryBox)).sendKeys("@os='" + os + "'");

        String numDevices = "//*[@min='1']";
        driver.findElement(By.xpath(numDevices)).clear();
        driver.findElement(By.xpath(numDevices)).sendKeys("2");

        deviceQueries.add("@os='" + os.toString().toLowerCase() + "'");
        deviceQueries.add("@os='" + os.toString().toLowerCase() + "'");
    }

    public void perish() {
        Utilities.log(runner, "killing the selenium driver");
        driver.quit();
    }

    public boolean runTestPlanFromUI(TestPlan testPlan) {
        Utilities.log(runner, "run test plan from UI");
        login();
        navigateToTestPlansPage();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> testPlans = driver.findElements(By.xpath("//*[@st-select-row='plan']"));
        WebElement testPlanElem;
        try {
            testPlanElem = testPlans.stream().filter(p -> p.getText().startsWith(testPlan.getTestPlanName())).collect(Collectors.toList()).get(0);
        } catch (Exception e) {
            Utilities.log(runner, "could not find test plan to run from api, testPlan " + testPlan.getTestPlanName());
            return false;
        }
        try {
            testPlanElem.click();
        }
        catch (Exception e){
            driver.manage().window().fullscreen();
            testPlanElem.click();
        }
        String runTestPlanElem = "//*[@aria-label='Run']";
        driver.findElement(By.xpath(runTestPlanElem)).click();

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String executeRun = "//*[@name='run-plan-ok']";
        driver.findElement(By.xpath(executeRun)).click();
//        navigateToTestPlansPage();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Utilities.log(e);
        }
        //re - click the test plan element
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String runningTest = "//*[@st-select-row='test']";
        WebElement runningTestElem;
        try {
            runningTestElem = driver.findElement(By.xpath(runningTest));
        } catch (Exception e) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            driver.findElements(By.xpath("//*[@st-select-row='plan']")).stream().filter(p -> p.getText().startsWith(testPlan.getTestPlanName())).collect(Collectors.toList()).get(0).click();
            runningTestElem = driver.findElement(By.xpath(runningTest));
        }

        String runningTestAttr = runningTestElem.getText();
        Assert.assertTrue("TestPlan did not start running", runningTestAttr.contains("Running"));
        return verifyTestPlanStillRunning(testPlan);
    }

    private boolean verifyTestPlanStillRunning(TestPlan testPlan) {
        Utilities.log(runner, "verify test plan is still running");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        Utilities.log(runner, "verify test plan is still running");
        String runningTest = "//*[@st-select-row='test']";
        WebElement runningTestElem = driver.findElement(By.xpath(runningTest));
        long currTime = System.currentTimeMillis();
        long startTime = System.currentTimeMillis();
        while (runningTestElem.getText().contains("Running") && (startTime + 4 * 60 * 60 * 1000) > currTime) {
            try {
                Utilities.log(runner, testPlan.getTestPlanName() + " is still running.");
                Thread.sleep(15000);
                runningTestElem = driver.findElement(By.xpath(runningTest));
            } catch (InterruptedException e) {
                Utilities.log(e);
            }

        }
        return runningTestElem.getText().contains("Finished");
    }


    public boolean deleteTestPlan(TestPlan testPlantoDelete) {
        Utilities.log(runner, "delete test plan from UI");
        login();
        navigateToTestPlansPage();
        try {
            Thread.sleep(3000);
            List<WebElement> testPlans = driver.findElements(By.xpath("//*[@st-select-row='plan']"));
            WebElement testPlanElem = null;
            for(int i = 0; i < testPlans.size(); i++){
                try {
                    WebElement testPlan = testPlans.get(i);
                    if(testPlan.getText().contains(testPlantoDelete.getTestPlanName())){
                        testPlanElem = testPlan;
                        break;
                    }
                }catch (StaleElementReferenceException e){
                    testPlans = driver.findElements(By.xpath("//*[@st-select-row='plan']"));
                }
            }
            testPlanElem.click();

            String deleteButton = "//*[@aria-label='Delete']";
            String executeDelete = "//*[@name='delete-plan-ok']";

            Utilities.log(runner, "execute delete");
            Thread.sleep(2000);
            driver.findElement(By.xpath(deleteButton)).click();
            (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(executeDelete)));
            driver.findElement(By.xpath(executeDelete)).click();
        } catch (Exception e) {
            Utilities.log(runner, "could not find test plan to run from api, testPlan " + testPlantoDelete.getTestPlanName());
            return false;
        }

        return true;
    }
    public boolean deleteSomeTestPlan() {
        Utilities.log(runner, "delete test plan from UI");
        login();
        navigateToTestPlansPage();
        try {
            Thread.sleep(3000);
            WebElement testPlanElem = null;
            String runningTest = "//*[@st-select-row='test']";
            List<WebElement> testPlans = driver.findElements(By.xpath("//*[@st-select-row='plan']"));
            for(WebElement currTestPlan: testPlans) {
                currTestPlan.click();
                try {
                    driver.findElement(By.xpath(runningTest));
                    testPlanElem = currTestPlan;
                } catch (Exception e) {
                    testPlanElem = currTestPlan;
                    break;
                }
            }

            testPlanElem.click();

            String deleteButton = "//*[@aria-label='Delete']";
            String executeDelete = "//*[@name='delete-plan-ok']";

            Utilities.log(runner, "execute delete");
            Thread.sleep(2000);
            driver.findElement(By.xpath(deleteButton)).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath(executeDelete)).click();
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return true;
    }

    public WebDriver getDriver() {
        return this.driver;
    }
}
