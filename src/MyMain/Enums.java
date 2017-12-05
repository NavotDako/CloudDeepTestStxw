package MyMain;

import STXWSuite.tests.ExtendSession;
import STXWSuite.tests.Install;
import STXWSuite.tests.Monitors;
import STXWSuite.tests.OpenLogs;
import STXWSuite.tests.Reboot;
import STXWSuite.tests.StartVideo;
import TestPlanSuite.tests.*;


public class Enums {


    public String[] Users = {"ayoubAdminDeepTest1", "ayoubAdminDeepTest2", "ayoubAdminDeepTest3", "ayoubAdminDeepTest4",
            "ayoubAdminDeepTest5", "ayoubUserDeepTest1", "ayoubUserDeepTest2", "ayoubUserDeepTest3", "ayoubProjectAdminDeepTest1", "ayoubProjectAdminDeepTest2"};
    public String[] Project = {"ayoubProjectDeepTest1", "ayoubProjectDeepTest2"};
    public Class[] Actions = {Install.class, Reboot.class, ExtendSession.class, OpenLogs.class, StartVideo.class, Monitors.class};
    public String STXWPassword = "Experitest2012";
    public String hostName = "http://releasecloud/index.html#";
    public Class[] TestPlanActions = {CreateTestPlans.class, RunTestPlanFromUI.class, RunTestAppFromAPI.class, DeleteTestPlan.class};
    public enum OS{
        ANDROID, IOS
    }
    public enum ROLES{
        ADMIN, USER, PROJECT_ADMIN
    }
    public String applicationPath = "UICatalog";
}
