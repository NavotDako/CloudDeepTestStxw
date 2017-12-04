package MyMain;

import STXWActionTests.*;
import testPlanTests.CreateTestPlans;
import testPlanTests.DeleteTestPlan;
import testPlanTests.RunTestAppFromAPI;
import testPlanTests.RunTestPlanFromUI;

public class Enums {


    public String[] Users = {"ayoubAdminDeepTest1", "ayoubAdminDeepTest2", "ayoubAdminDeepTest3", "ayoubAdminDeepTest4", "ayoubAdminDeepTest5", "ayoubUserDeepTest1", "ayoubUserDeepTest2", "ayoubUserDeepTest3", "ayoubProjectAdminDeepTest1", "ayoubProjectAdminDeepTest2"};

    public String[] Project = {"ayoubProjectDeepTest1", "ayoubProjectDeepTest2"};
    //	public String [] Devices = {"hadar.zarihan�s iPhone","Sony F3116","iPad(2)","LGE Nexus 5","samsung SM-G920F","samsung SM-G920F","iPhone","LGE LG-F180L","Green iPhone","LGE Nexus 4","samsung SM-G900H-mac","samsung SM-T560 -mac"};

    public Class[] Actions = {Install.class, Reboot.class, ExtendSession.class, OpenLogs.class, StartVideo.class, Monitors.class};

    public Class[] TestPlanActions = {CreateTestPlans.class, RunTestPlanFromUI.class, RunTestAppFromAPI.class, DeleteTestPlan.class};

    public String Password = "Experitest2012";

    public String hostName = "http://releasecloud/index.html#/login";

    public enum ROLES{
        ADMIN, USER, PROJECT_ADMIN
    }
    public enum OS{
        ANDROID, IOS
    }

}
