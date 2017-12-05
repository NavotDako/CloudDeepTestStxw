package TestPlanSuite.tests;

import MyMain.BaseRunner;
import MyMain.Enums;
import TestPlanSuite.TestPlanBaseTest;
import TestPlanSuite.cloudEntities.*;
import Utils.Utilities;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by khaled.abbas on 11/30/2017.
 */
public class CreateTestPlans extends TestPlanBaseTest{


    @Test
    public void run() {
        Utilities.log((BaseRunner) Thread.currentThread(), "Create test plans Test Starts");

        System.out.println(this.getClass() + "-- started Running");

        Utilities.log((BaseRunner) Thread.currentThread(), "create test plans");
        List<TestPlan> createdTestPlans = this.seleniumHelper.createTestPlans();

        Utilities.log((BaseRunner) Thread.currentThread(), "verify os test plan was created");
        TestPlan osApp = createdTestPlans.stream().filter(p -> p.getOS().equals(Enums.OS.IOS)).collect(Collectors.toList()).get(0);
        Utilities.log((BaseRunner) Thread.currentThread(), "verify android test plan was created");
        TestPlan androidApp = createdTestPlans.stream().filter(p -> p.getOS().equals(Enums.OS.ANDROID)).collect(Collectors.toList()).get(0);
        Assert.assertTrue("We do not have any android test plan in this project",
                this.seleniumHelper.getProjectTestPlans().stream().filter(p -> p.getTestPlanName().equals(androidApp.getTestPlanName())).count() > 0);
        Assert.assertTrue("We do not have any ios test plan in this project",
                this.seleniumHelper.getProjectTestPlans().stream().filter(p -> p.getTestPlanName().equals(osApp.getTestPlanName())).count() > 0);

        Utilities.log((BaseRunner) Thread.currentThread(), "successfully created test plans for ios + android");
    }
}
