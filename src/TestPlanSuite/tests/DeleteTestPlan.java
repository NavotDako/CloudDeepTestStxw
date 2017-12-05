package TestPlanSuite.tests;

import MyMain.BaseRunner;
import TestPlanSuite.TestPlanBaseTest;
import Utils.Utilities;
import TestPlanSuite.cloudEntities.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by khaled.abbas on 12/3/2017.
 */
public class DeleteTestPlan extends TestPlanBaseTest{

        @Test
        public void run() {
            Utilities.log(runner, "DeleteTestPlan Test Starts");

            System.out.println(this.getClass() + "-- started Running");
//            if (!this.isThereTestPlanInProject()) {
//                Utilities.log((BaseRunner) Thread.runner(), "Create test request");
//                this.createTestRequest();
//            }
            Utilities.log(runner, "create test plans");
            List<TestPlan> testPlansCreated = this.seleniumHelper.createTestPlans();
//
//            Utilities.log((BaseRunner) Thread.runner(), "get test plans");
//            List<TestPlan> testPlans = this.project.getTestPlanByOS(Math.random() > Math.random() ? Enums.OS.IOS : Enums.OS.ANDROID);
//            Utilities.log((BaseRunner) Thread.runner(), "get a random application");
            this.setTestPlanToRun(testPlansCreated.get((int) Math.random() * testPlansCreated.size()));


            Assert.assertTrue("failed to delete test plan", this.seleniumHelper.deleteTestPlan(testPlanToRun));

            Utilities.log(runner, "verify os test plan was deleted");

            Assert.assertTrue("We have an existing test plan with the test plan name",
                    this.seleniumHelper.getProjectTestPlans().stream().filter(p -> p.getTestPlanName().equalsIgnoreCase(testPlanToRun.getTestPlanName())).count() == 0);

            Utilities.log(runner, "successfully deleted test plan");
        }

}