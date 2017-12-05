package TestPlanSuite.tests;

import MyMain.BaseRunner;
import MyMain.Enums;
import TestPlanSuite.TestPlanBaseTest;
import Utils.Utilities;
import TestPlanSuite.cloudEntities.*;
import org.apache.http.entity.ContentType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by khaled.abbas on 11/27/2017.
 */
public class RunTestAppFromAPI extends TestPlanBaseTest {

//    public RunTestAppFromAPI(Project proj, User testPlanUser) {
//        super(proj, testPlanUser);
//    }
    @Test
    public void run() {
        System.out.println(this.getClass() + "-- started Running");
        Utilities.log((BaseRunner) Thread.currentThread(), "RunTestAppFromAPI Test Starts");

        if (!this.isThereTestPlanInProject()) {
            Utilities.log((BaseRunner) Thread.currentThread(), "Create test request");
                this.createTestRequest();
            }
        Utilities.log((BaseRunner) Thread.currentThread(), "get test plans");
            List<TestPlan> testPlans = this.project.getTestPlanByOS(Math.random() > Math.random() ? Enums.OS.IOS : Enums.OS.ANDROID);
            this.setTestPlanToRun(testPlans.get((int) Math.random() * testPlans.size()));
            this.changeQueries = Math.random() > Math.random();
        Utilities.log((BaseRunner) Thread.currentThread(),"running test application " + this.testPlanToRun.getTestPlanName() + " for testPlanUser " + user.getUserName() + " in project " + this.project.getProjName());

//        _updateRes.start("RunTestAppFromAPI - Testplan " + testPlanToRun.getTestPlanName() + ", for testPlanUser, " + this.testPlanUser.getUserName());
        Utilities.log((BaseRunner) Thread.currentThread(), "updating the API request");
            updateRequest();
            try {
                Utilities.log((BaseRunner) Thread.currentThread(), "run the Rest api command");
                String response = runAPITest();
                if (parseResponse(response)) {
                    Utilities.log((BaseRunner) Thread.currentThread(), "RunTestAppFromAPI - Testplan " + testPlanToRun.getTestPlanName() + ", for testPlanUser, " + this.user.getUserName() + " - Test has passed");
//                    BaseTest._updateRes.pass("RunTestAppFromAPI - Testplan " + testPlanToRun.getTestPlanName() + ", for testPlanUser, " + this.testPlanUser.getUserName() + " - Test has passed");
                } else {
                    Utilities.log((BaseRunner) Thread.currentThread(),"RunTestAppFromAPI - Test has Failed");
                    Assert.fail("RunTestAppFromAPI - Test has Failed");
                }
            } catch (Exception e) {
                e.printStackTrace();

                Utilities.log((BaseRunner) Thread.currentThread(), "RunTestAppFromAPI - Test has Failed");
                Assert.fail("RunTestAppFromAPI - Test has Failed");
            }
    }

    private boolean parseResponse(String response){
        Utilities.log((BaseRunner) Thread.currentThread(),"parsing response from API Request " + response);
                response.replace(",", ":").replace("\"", "");
        String [] responseArr = response.split(":");
        if(this.testPlanToRun.getOS().equals(Enums.OS.ANDROID)) {
            for (int i = 0; i < responseArr.length; i++) {
                if (responseArr[i].toString().equalsIgnoreCase("Total number of tests")) {
                    Utilities.log((BaseRunner) Thread.currentThread(),"Expecting num of tests to be 3 per device, got: " + responseArr[i + 1].toString());
                    Assert.assertEquals("" + (3 * this.testPlanToRun.getDeviceQueries().size()), responseArr[i].toString());
                }
                if (responseArr[i].toString().equalsIgnoreCase("Number of passed tests")) {
                    Utilities.log((BaseRunner) Thread.currentThread(),"Expecting num of passed tests to be 2 per device, got: " + responseArr[i + 1].toString());
                    Assert.assertEquals("" + (2 * this.testPlanToRun.getDeviceQueries().size()), responseArr[i].toString());
                }
                if (responseArr[i].toString().equalsIgnoreCase("Number of failed tests")) {
                    Utilities.log((BaseRunner) Thread.currentThread(),"Expecting num of failed tests to be 1 per device, got: " + responseArr[i + 1].toString());
                    Assert.assertEquals("" + (1 * this.testPlanToRun.getDeviceQueries().size()), responseArr[i].toString());
                }
            }
        }
        else{
            for (int i = 0; i < responseArr.length; i++) {
                if (responseArr[i].toString().equalsIgnoreCase("Total number of tests")) {
                    Utilities.log((BaseRunner) Thread.currentThread(),"Expecting num of tests to be 6 per device, got: " + responseArr[i + 1].toString());
                    Assert.assertEquals("" + (6 * this.testPlanToRun.getDeviceQueries().size()), responseArr[i].toString());
                }
                if (responseArr[i].toString().equalsIgnoreCase("Number of passed tests")) {
                    Utilities.log((BaseRunner) Thread.currentThread(),"Expecting num of passed tests to be 5 per device, got: " + responseArr[i + 1].toString());
                    Assert.assertEquals("" + (5 * this.testPlanToRun.getDeviceQueries().size()), responseArr[i].toString());
                }
                if (responseArr[i].toString().equalsIgnoreCase("Number of failed tests")) {
                    Utilities.log((BaseRunner) Thread.currentThread(),"Expecting num of failed tests to be 5 per device, got: " + responseArr[i + 1].toString());
                    Assert.assertEquals("" + (5 * this.testPlanToRun.getDeviceQueries().size()), responseArr[i].toString());
                }
            }
        }
        return true;
    }
    protected void updateRequest(){
    /*
    *  This attaches the file to the POST:
    */
        Utilities.log((BaseRunner) Thread.currentThread(),"add body to rest multipart request");
        File f = this.testPlanToRun.getOS().equals(Enums.OS.ANDROID) ? new File("testApps//app-eribank.apk"): new File("testApps//Monster Island Game.ipa");
        File testApp = this.testPlanToRun.getOS().equals(Enums.OS.ANDROID) ? new File("testApps//app-eribank-androidTest-1Fail.apk"): new File("testApps//Monster Island GameUITests-Runner (2).zip");
        builder.addTextBody("testPlan", testPlanToRun.getProject() + ":" + testPlanToRun.getTestPlanName(), ContentType.TEXT_PLAIN);
        if(changeQueries) {
            Utilities.log((BaseRunner) Thread.currentThread(),"Changing device queries for testPlan: " + this.testPlanToRun.toString());
            this.testPlanToRun.setDeviceQueries((int)(Math.random() * 5));
        }
        if(changeQueries) {
            for (String deviceQuery: this.testPlanToRun.getDeviceQueries()) {
                builder.addTextBody("deviceQueries", deviceQuery, ContentType.TEXT_PLAIN);
            }
        }
        builder.addTextBody(f.getName(), "yes", ContentType.TEXT_PLAIN);
        builder.addTextBody(testApp.getName(), "yes", ContentType.TEXT_PLAIN);
        try {
            builder.addBinaryBody(
                    "app",
                    new FileInputStream(f),
                    ContentType.APPLICATION_OCTET_STREAM,
                    f.getName()
            );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Utilities.log((BaseRunner) Thread.currentThread(),"RunTestAppFromAPI - could not find file");
            Assert.fail("RunTestAppFromAPI - could not find file");
        }
        builder.addTextBody(testApp.getName(), "yes", ContentType.TEXT_PLAIN);
        try {
            builder.addBinaryBody(
                    "testApp",
                    new FileInputStream(testApp),
                    ContentType.APPLICATION_OCTET_STREAM,
                    testApp.getName()
            );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Utilities.log((BaseRunner) Thread.currentThread(),"RunTestAppFromAPI - could not find file");
            Assert.fail("RunTestAppFromAPI - could not find file");
        }
    }
}
