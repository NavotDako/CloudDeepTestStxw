package TestPlanSuite.tests;

import MyMain.BaseRunner;
import TestPlanSuite.TestPlanBaseTest;
import Utils.Utilities;
import TestPlanSuite.cloudEntities.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by khaled.abbas on 11/30/2017.
 */
public class RunTestPlanFromUI extends TestPlanBaseTest {

    @Test
    public void run() {
        try {
            Utilities.log(runner,"RunTestPlanFromUI has started");

            if (!this.isThereTestPlanInProject()) {
                Utilities.log(runner,"creating test request");
                this.createTestRequest();
            }
            Utilities.log(runner,"get testPlanProject test plans");
            List<TestPlan> testPlans = this.seleniumHelper.getProjectTestPlans();
            this.setTestPlanToRun(testPlans.get((int) Math.random() * testPlans.size()));
            Utilities.log(runner,"running test application " + this.testPlanToRun.getTestPlanName() + " for testPlanUser " + user.getUserName() + " in testPlanProject " + this.testPlanProject.getProjName());
            Utilities.log(runner,"login and run test from UI");
            Assert.assertTrue("Run test plan from UI", this.seleniumHelper.runTestPlanFromUI(this.testPlanToRun));
            Utilities.log(runner, "RunTestAppFromUI - Testplan " + testPlanToRun.getTestPlanName() + ", for testPlanUser, " + this.user.getUserName() + " - Test has passed");


        }catch (Exception e){
            Utilities.log(runner,e.getMessage());
//            BaseTest._updateRes.fail("RunTestAppFromUI - Testplan " + testPlanToRun.getTestPlanName() + ", for testPlanUser, " + this.testPlanUser.getUserName() + " - Test has failed");
            Assert.fail("RunTestAppFromUI - Testplan " + testPlanToRun.getTestPlanName() + ", for testPlanUser, " + this.user.getUserName() + " - Test has failed");
        }

    }
}
