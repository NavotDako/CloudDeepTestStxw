package TestPlanSuite.tests;

import MyMain.Enums;
import TestPlanSuite.TestPlanBaseTest;
import TestPlanSuite.cloudEntities.TestPlan;
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
        Utilities.log(runner, "Create test plans Test Starts");

        Utilities.log(runner, "create test plans");
        List<TestPlan> createdTestPlans = this.seleniumHelper.createTestPlans();

        Utilities.log(runner, "verify os test plan was created");
        TestPlan osApp = createdTestPlans.stream().filter(p -> p.getOS().equals(Enums.OS.IOS)).collect(Collectors.toList()).get(0);
        Utilities.log(runner, "verify android test plan was created");
        TestPlan androidApp = createdTestPlans.stream().filter(p -> p.getOS().equals(Enums.OS.ANDROID)).collect(Collectors.toList()).get(0);
        List<TestPlan> testPlanList = this.seleniumHelper.getProjectTestPlans();
        for(TestPlan currTP: testPlanList) {
            Utilities.log(runner, "test plans in project " + currTP.getTestPlanName());
        }
        Utilities.log(runner, "Android Test plan " + androidApp.getTestPlanName());
        Utilities.log(runner, "ios Test plan " + osApp.getTestPlanName());
        Assert.assertTrue("We do not have any android test plan in this testPlanProject",
                testPlanList.stream().filter(p -> p.getTestPlanName().equals(androidApp.getTestPlanName())).count() > 0);
        Assert.assertTrue("We do not have any ios test plan in this testPlanProject",
                testPlanList.stream().filter(p -> p.getTestPlanName().equals(osApp.getTestPlanName())).count() > 0);

        this.seleniumHelper.deleteTestPlan(osApp);
        this.seleniumHelper.deleteTestPlan(androidApp);
        Utilities.log(runner, "successfully created test plans for ios + android");
    }
}
