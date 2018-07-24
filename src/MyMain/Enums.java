package MyMain;

import AdministrationSuite.tests.*;
import STGridSuite.tests.AppiumTests.AppiumAndroidEriBankNonInst;
import STGridSuite.tests.AppiumTests.AppiumAndroidUICatalogInst;
import STGridSuite.tests.AppiumTests.FindByUIAutomator;
import STGridSuite.tests.SeeTestTests.GridAndroidEribankNonInst;
import STGridSuite.tests.SeeTestTests.GridAndroidEribankTest;
import STGridSuite.tests.SeeTestTests.IOSFingerPrint;
import STGridSuite.tests.SeeTestTests.IOSFingerPrintFailures;
import STXWSuite.tests.*;
import TestPlanSuite.tests.CreateTestPlans;
import TestPlanSuite.tests.DeleteTestPlan;
import TestPlanSuite.tests.RunTestAppFromAPI;
import TestPlanSuite.tests.RunTestPlanFromUI;

import java.util.HashMap;
import java.util.Map;


public class Enums {


    public String[] USERS = {"ayoubAdminDeepTest1", "ayoubAdminDeepTest2", "ayoubAdminDeepTest3", "ayoubAdminDeepTest4",
            "ayoubAdminDeepTest5", "ayoubUserDeepTest1", "ayoubUserDeepTest2", "ayoubUserDeepTest3", "ayoubProjectAdminDeepTest1", "ayoubProjectAdminDeepTest2", "khaleda"};
//public String[] USERS = {"ayoubUserDeepTest1"};
    public String[] PROJECTS = {"ayoubProjectDeepTest1", "ayoubProjectDeepTest2"};
    public Class[] Actions = {Install.class, Reboot.class, ExtendSession.class, OpenLogs.class, StartVideo.class, Monitors.class};
    public String STXWPassword = "Experitest2012";
    public Class[] TestPlanActions = {CreateTestPlans.class, RunTestPlanFromUI.class, RunTestAppFromAPI.class, DeleteTestPlan.class};
    public Class[] AdminRunnerActions = {ApplicationAction.class, DeviceGroupAction.class, DHMAction.class, ProjectAction.class, ScreenShotAction.class, UserAction.class};
    public Class[] GridAndroidTests = {AppiumAndroidEriBankNonInst.class, GridAndroidEribankNonInst.class, GridAndroidEribankTest.class, FindByUIAutomator.class, AppiumAndroidUICatalogInst.class};//, GridAndroidWebTest.class
//    public Class[] GridAndroidTests = {GridAndroidEribankNonInst.class, GridAndroidEribankTest.class};//, GridAndroidWebTest.class

//    public Class[] GridIOSTests = {AppiumIOSEriBankNonInst.class, GridiOSWebTest.class, GridiOSEribankTest.class, GridIOSEriBankNonInst.class, IOSFingerPrint.class, IOSFingerPrintFailures.class};
public Class[] GridIOSTests = {IOSFingerPrint.class, IOSFingerPrintFailures.class};
    public enum OS{
        ANDROID, IOS
    }
    public enum ROLES{
        ADMIN, USER, PROJECT_ADMIN
    }
    public String applicationPath = "\\UICatalog.apk";


    private static final Map<String, String> userToAccessKeyMap;
    static
    {
        userToAccessKeyMap = new HashMap<String, String>();
        userToAccessKeyMap.put("ayoubAdminDeepTest1", "eyJ4cC51IjozLCJ4cC5wIjoyLCJ4cC5tIjoiTVRVeE5UWTRNVEV3TVRJM01nIiwiYWxnIjoiSFMyNTYifQ.eyJleHAiOjE4MzEwNDExMDEsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.Lv-6Dt24Z3aQT6LkaQy6dg5Yp4N4vifTsoTUrnAtQUc");
        userToAccessKeyMap.put("ayoubAdminDeepTest2", "eyJ4cC51Ijo0LCJ4cC5wIjoyLCJ4cC5tIjoiTVRVeE5UWTRNVEUyTXpJd053IiwiYWxnIjoiSFMyNTYifQ.eyJleHAiOjE4MzEwNDExNjMsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.zRmdo9giv_Lpj06rfmbAj78nDq-amAbOFQnTyPY5wVI");
        userToAccessKeyMap.put("ayoubAdminDeepTest3", "eyJ4cC51Ijo1LCJ4cC5wIjoyLCJ4cC5tIjoiTVRVeE5UWTRNVEkwTURVek1nIiwiYWxnIjoiSFMyNTYifQ.eyJleHAiOjE4MzEwNDEyNDAsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.AHP3L7q2jXgzkYDkP2MvJ3NY2-hIWzCv1qlnFlcjn2A");
        userToAccessKeyMap.put("ayoubAdminDeepTest4", "eyJ4cC51Ijo4LCJ4cC5wIjoyLCJ4cC5tIjoiTVRVeE5UWTRNVFF5TVRjMk1BIiwiYWxnIjoiSFMyNTYifQ.eyJleHAiOjE4MzEwNDE0MjEsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.07Hq6IwJdXF09wXI7NblrVxlZLfu9diTxhiPwoJxub4");
        userToAccessKeyMap.put("ayoubAdminDeepTest5", "eyJ4cC51Ijo5LCJ4cC5wIjoyLCJ4cC5tIjoiTVRVeE5UWTRNVFEzTmpZM05RIiwiYWxnIjoiSFMyNTYifQ.eyJleHAiOjE4MzEwNDE0NzYsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.kD2-hla4mQ0mskcHuiJHxCuaWNPUTAqap0IK8GIptTY");
        userToAccessKeyMap.put("ayoubUserDeepTest1", "eyJ4cC51IjoxMiwieHAucCI6MiwieHAubSI6Ik1UVXhOVFk0TWpVeU9UQTVPUSIsImFsZyI6IkhTMjU2In0.eyJleHAiOjE4MzEwNDI1MjksImlzcyI6ImNvbS5leHBlcml0ZXN0In0.EY9Ot_0K2upkN_v6sxO6zte29-5dRUGdiBgOMIfC9cQ");
        userToAccessKeyMap.put("ayoubUserDeepTest2", "eyJ4cC51IjoxMywieHAucCI6MywieHAubSI6Ik1UVXhOVFk0TWpVNE5qVXdNdyIsImFsZyI6IkhTMjU2In0.eyJleHAiOjE4NDExNDk4NDgsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.pxAp9f6hAlB2weRwdu5DB-WUqD_Qn-boi__qkvWVQWU");
        userToAccessKeyMap.put("ayoubUserDeepTest3", "eyJ4cC51IjoxNCwieHAucCI6MiwieHAubSI6Ik1UVXhOVFk0TWpZME56STBPUSIsImFsZyI6IkhTMjU2In0.eyJleHAiOjE4MzEwNDI2NDcsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.V8GGicPnQQy6tK0f119V1YOhyvXLVRYtKr3bfQ62vPg");
        userToAccessKeyMap.put("ayoubProjectAdminDeepTest1", "eyJ4cC51IjoxMCwieHAucCI6MywieHAubSI6Ik1UVXhOVFk0TVRnd01qY3pNdyIsImFsZyI6IkhTMjU2In0.eyJleHAiOjE4MzEwNDE4MDIsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.0G_DvsbzwiFmF1cp4SRq7nsfks8mUq7EK8Om4AOAMc4");
        userToAccessKeyMap.put("ayoubProjectAdminDeepTest2", "eyJ4cC51IjoxMSwieHAucCI6NCwieHAubSI6Ik1UVXhOVFk0TWpRMk1UVTBPQSIsImFsZyI6IkhTMjU2In0.eyJleHAiOjE4MzEwNDI0NjEsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.QFEsWDlwWuWGHTrdtnwOJ-zY37Hg_xDkyYHrZ7RjeTc");
        userToAccessKeyMap.put("khaleda", "eyJ4cC51Ijo3LCJ4cC5wIjoyLCJ4cC5tIjoiTVRVeE5UWTRNVEk1TXpjd01nIiwiYWxnIjoiSFMyNTYifQ.eyJleHAiOjE4NDQ3NjA0MzUsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.PY7iv06QeCcEdm8Hh4vuyRO22lW9Gw5QExRs-vrSYpk");
    }
    public static String getAccessKey(String user){
        return userToAccessKeyMap.get(user);
    }
}
