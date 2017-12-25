package MyMain;

import STXWSuite.tests.*;
import TestPlanSuite.tests.CreateTestPlans;
import TestPlanSuite.tests.DeleteTestPlan;
import TestPlanSuite.tests.RunTestAppFromAPI;
import TestPlanSuite.tests.RunTestPlanFromUI;


public class Enums {


    public String[] USERS = {"ayoubAdminDeepTest1", "ayoubAdminDeepTest2", "ayoubAdminDeepTest3", "ayoubAdminDeepTest4",
            "ayoubAdminDeepTest5", "ayoubUserDeepTest1", "ayoubUserDeepTest2", "ayoubUserDeepTest3", "ayoubProjectAdminDeepTest1", "ayoubProjectAdminDeepTest2"};
    public String[] PROJECTS = {"ayoubProjectDeepTest1", "ayoubProjectDeepTest2"};
    public Class[] Actions = {Install.class, Reboot.class, ExtendSession.class, OpenLogs.class, StartVideo.class, Monitors.class};
    public String STXWPassword = "Experitest2012";
    public Class[] TestPlanActions = {CreateTestPlans.class, RunTestPlanFromUI.class, RunTestAppFromAPI.class, DeleteTestPlan.class};
    public enum OS{
        ANDROID, IOS
    }
    public enum ROLES{
        ADMIN, USER, PROJECT_ADMIN
    }
    public String applicationPath = "UICatalog";
}
