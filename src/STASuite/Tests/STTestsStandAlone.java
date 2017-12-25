package STASuite.Tests;

import STASuite.ExClient;
import STASuite.STARunner;
import Utils.MyILogger;
import Utils.Utilities;
import com.experitest.client.Client;

import static org.junit.Assert.fail;

public class STTestsStandAlone {

    STARunner runner;
    private String setReporter;
    private boolean successFlag=true;
    private Exception exception=null;
    private String testName="";
    private boolean setReporterFlag=true;
    private String deviceLogPath;
    private boolean logDeviceFlag=false;
    private String dumpType="native";

    public STTestsStandAlone(STARunner runner) {
        this.runner = runner;
    }

    private ExClient getClient(String query) {
        successFlag=true;
        Utilities.log(runner,"\n*********************************************************************");
        Utilities.log(runner, "Getting Client for test "+testName);
        ExClient client = null;
        try {
            client = new ExClient(runner.VMAddress, 8889, true);
            client.setRunner(runner);
        } catch (Exception e) {
            Utilities.log(runner,"["+testName+"] Failed to initiate Client ");
            Utilities.log(runner,e);
            throw e;
        }
        client.setLogger(new MyILogger(runner));

        if (isSetReporterFlag()) {
            setReporter = client.setReporter("xml", runner.jarRemoteFolderPath + "/reports", testName);
            deviceLogPath=setReporter+"\\deviceLog.log";
            if (logDeviceFlag) {
                client.startLoggingDevice(deviceLogPath);
            }
        }


        try {
            client.waitForDevice(query, 300000);
        } catch (Exception e) {
            Utilities.log(runner,"["+testName+"] Failed to Lock Device");
            Utilities.log(runner,e);
            throw e;

        }


        try {
            client.openDevice();
        } catch (Exception e) {
            Utilities.log(runner,"["+testName+"] Failed to Open Device");
            Utilities.log(runner,e);
            throw e;
        }
        try {
            client.deviceAction("Unlock");
        } catch (Exception e) {
            Utilities.log(runner,"["+testName+"] Failed to UnLock Device Screen");
            Utilities.log(runner,e);
            throw e;
        }
        return client;
    }

    private void onFailure(Client client, Exception e) {
        successFlag=false;
        exception=e;
        Utilities.log(runner,"Failure on Test : "+testName);
        Utilities.log(runner,e);


        try {
            String visualDump = client.getVisualDump(dumpType);
            String dumpToLog="["+testName+"]"+dumpType+" Dump:/n"+visualDump;
            Utilities.log(runner,dumpToLog);
        } catch (Exception e1) {
            Utilities.log(runner,"Failed To get Visual Dump : "+testName);
            Utilities.log(runner,e1);
        }


        if (isSetReporterFlag()) {
            try{
                String deviceName=client.getDeviceProperty("device.name");
                String sessionID = client.getSessionID();
                String supportDataPath=setReporter+"//SupportDataRun.zip";
                client.collectSupportData(supportDataPath,"",deviceName,"Running Session "+sessionID,"Should Pass","Failed",true,true);
                Utilities.log(runner,"Support Data Path : "+supportDataPath);
            }catch (Exception e2){
                Utilities.log(runner,"Failed To generate Support Data");
                Utilities.log(runner,e2);
            }
        }
    }

    private void finish(ExClient client) {

        Utilities.log(runner,"["+testName+"] : Starting Finish");
        String deviceName="";

        try {
            deviceName = client.getDeviceProperty("device.name");
            if (isSetReporterFlag()) {
                client.generateReport(false);
                String stopLoggingDevice= null;
                if (logDeviceFlag) {
                    stopLoggingDevice = deviceLogPath;

                    try {
                        stopLoggingDevice = client.stopLoggingDevice();
                        Utilities.log(runner,"["+testName+"] Device log saved in "+stopLoggingDevice);

                    } catch (Exception e) {
                        Utilities.log(runner,"["+testName+"] Failed To Write Device Log to "+stopLoggingDevice);
                        Utilities.log(runner,e);
                    }
                }
            }
            try {
                client.releaseDevice(deviceName, false, true, true);
                Utilities.log(runner,"["+testName+"] Released Device "+deviceName);

            } catch (Exception e) {
                Utilities.log(runner,"["+testName+"] Failed To Release Device "+deviceName);
                Utilities.log(runner,e);
                successFlag=false;

                if (exception==null) {
                    exception=e;
                }
            }

            try {
                client.releaseClient();
                Utilities.log(runner, "Client Released");
            } catch (Exception e) {
                Utilities.log(runner,"["+testName+"] Failed To Release Client  ");
                Utilities.log(runner,e);
                successFlag=false;

                if (exception==null) {
                    exception=e;
                }
            }

        } catch (Exception e) {
            Utilities.log(runner,"["+testName+"] Failed To Finish Test ");
            Utilities.log(runner,e);
            successFlag=false;
            if (exception==null) {
                exception=e;
            }
        }
        Utilities.log(runner,"\n*********************************************************************");

        if(!successFlag){
            if(exception!=null) {
                String message = "[" + testName +": "+deviceName+":"+client.getReportFolder()+ " ] " + exception.getMessage();

                fail(message);
            }
            else{
                fail("[" + testName +": "+deviceName+ " ] "+"No Exception Found");
            }
        }

    }


    public void androidEriBankTestInstrumented() {
        testName="Android EriBank Test Instrumented";
        ExClient client = getClient("@os='android'");

        try {
            //client.startStepsGroup("Android EriBank Test Instrumented");
            client.uninstall("com.experitest.ExperiBank");
            client.install("cloud:com.experitest.ExperiBank/.LoginActivity", true, false);
            client.deviceAction("Unlock");
            client.launch("cloud:com.experitest.ExperiBank/.LoginActivity", true,true);
            client.waitForElement("NATIVE", "hint=Username", 0,10000);
            client.elementSendText("NATIVE", "hint=Username", 0, "company");
            client.elementSendText("NATIVE", "hint=Password", 0, "company");
            client.click("NATIVE", "text=Login", 0, 1);
            if (client.waitForElement("WEB", "xpath=//*[@nodeName='H1']", 0, 10000)) {
                // If statement
            }
            client.click("NATIVE", "text=Make Payment", 0, 1);
            client.elementSendText("NATIVE", "id=phoneTextField", 0, "0987654321");
            client.elementSendText("NATIVE", "id=nameTextField", 0, "Name");
            client.elementSendText("NATIVE", "id=amountTextField", 0, "10");
            client.click("NATIVE", "id=countryButton", 0, 1);
            client.elementListSelect("", "text=Argentina", 0, true);
            client.click("NATIVE", "text=Send Payment", 0, 1);
            client.click("NATIVE", "text=Yes", 0, 1);
            client.elementListSelect("", "text=Logout", 0, false);
        } catch (Exception e) {
            onFailure(client, e);
        } finally {
            finish(client);

        }

        //client.stopStepsGroup();

    }




    public void androidEriBankTestNonInstrumented() {
        dumpType="Non-Instrumented";
        testName="Android EriBank Test Non Instrumented";
        ExClient client = getClient("@os='android'");

        //client.startStepsGroup("Android EriBank Test Non Instrumented");

        try {
            if (client.uninstall("com.experitest.ExperiBank")) {
                // If statement
            }
            if (client.install("cloud:com.experitest.ExperiBank/.LoginActivity", false, true)) {
                // If statement
            }
            client.launch("cloud:com.experitest.ExperiBank/.LoginActivity", false, false);
            client.waitForElement("NATIVE", "xpath=//*[@id='usernameTextField']", 0,10000);

            client.elementSendText("NATIVE", "xpath=//*[@id='usernameTextField']", 0, "company");
            client.elementSendText("NATIVE", "xpath=//*[@id='passwordTextField']", 0, "company");
            client.click("NATIVE", "xpath=//*[@text='Login']", 0, 1);
            client.click("NATIVE", "xpath=//*[@text='Make Payment']", 0, 1);
            client.elementSendText("NATIVE", "xpath=//*[@id='phoneTextField']", 0, "0987654321");
            client.elementSendText("NATIVE", "xpath=//*[@id='nameTextField']", 0, "Name");
            client.elementSendText("NATIVE", "xpath=//*[@id='amountTextField']", 0, "10");
            client.click("NATIVE", "xpath=//*[@id='countryButton']", 0, 1);
            client.click("NATIVE", "xpath=//*[@text='Greece']", 0, 1);
            client.click("NATIVE", "xpath=//*[@id='sendPaymentButton']", 0, 1);
            client.click("NATIVE", "xpath=//*[@id='button1']", 0, 1);
            client.click("NATIVE", "xpath=//*[@id='logoutButton']", 0, 1);
            //client.stopStepsGroup();
        } catch (Exception e) {
            onFailure(client,e);
        } finally {
            finish(client);
        }

    }

    public void androidSimulateCaptureTest() {
        testName="Android Simulate Capture Test";
        ExClient client = getClient("@os='android'");


        //client.startStepsGroup("Android Simulate Capture Test");

        try {
            if (client.uninstall("cloud:com.CameraFlash/.MainActivity")) {
                // If statement
            }
            if (client.install("cloud:com.CameraFlash/.MainActivity", true, false)) {
                // If statement
            }
            client.deviceAction("Unlock");

            client.launch("cloud:com.CameraFlash/.MainActivity", true, true);
            client.simulateCapture("https://qacloud.experitest.com/theme/images/logo.png");
            client.sleep(1000);
            String str1 = client.capture("Capture");
        } catch (Exception e) {
            onFailure(client,e);
        } finally {
            finish(client);
        }
        //client.stopStepsGroup();


    }

    public void webAutomationSiteTest(String query) {
        testName= "Web Automation Site Test";
        dumpType="Web";

        ExClient client = getClient(query);

        //client.startStepsGroup("Web Automation Site Test");
        try {
            client.launch("http://192.168.4.85:8060/html-tests/WebPageTests/WebPageTests.html", false, true);
            client.click("WEB", "xpath=//*[@id='peaker_']", 0, 1);
            if (client.waitForElement("WEB", "xpath=//*[@id='countries']", 0, 10000)) {
                // If statement
            }
            client.hybridSelect("", 0, "id", "countries", "Switserland");
            client.launch("http://192.168.4.85:8060/html-tests/WebPageTests/WebPageTests.html", false, false);
            client.click("WEB", "xpath=//*[@id='textinput_']", 0, 1);
            client.elementSendText("WEB", "xpath=//*[@id='text1']", 0, "Text");
            client.launch("http://192.168.4.85:8060/html-tests/WebPageTests/WebPageTests.html", false, false);
            client.click("WEB", "xpath=//*[@text='Radio Buttons']", 0, 1);
            client.click("WEB", "xpath=//*[@id='radio1']", 0, 1);
            client.click("WEB", "xpath=//*[@id='check2']", 0, 1);
            client.launch("http://192.168.4.85:8060/html-tests/WebPageTests/WebPageTests.html", false, false);
            client.click("WEB", "xpath=//*[@id='add_button']", 0, 1);
            client.click("WEB", "xpath=//*[@id='add_button']", 0, 1);
            client.click("WEB", "xpath=//*[@id='add_button']", 0, 1);
            client.click("WEB", "xpath=//*[@id='add_button']", 0, 1);
            client.applicationClose(client.getCurrentApplicationName());
        } catch (Exception e) {
            onFailure(client,e);
        } finally {
            finish(client);
        }

        //client.stopStepsGroup();

    }

    public void iOSEriBankTestInstrumented() {
        testName= "IOS EriBank Test Instrumented";
        ExClient client = getClient("@os='ios'");

        //client.startStepsGroup("IOS EriBank Test Instrumented");

        try {
            client.uninstall("com.experitest.ExperiBank");
            client.install("cloud:com.experitest.ExperiBank", true, false);

            client.launch("com.experitest.ExperiBank", true, true);
            client.waitForElement("NATIVE", "placeholder=Username", 0,10000);

            client.elementSendText("NATIVE", "placeholder=Username", 0, "company");
            client.elementSendText("NATIVE", "placeholder=Password", 0, "company");
            client.click("NATIVE", "accessibilityLabel=loginButton", 0, 1);
            if (client.waitForElement("NATIVE", "accessibilityLabel=makePaymentButton", 0, 30000)) {
                // If statement
            }
            client.click("NATIVE", "accessibilityLabel=makePaymentButton", 0, 1);
            client.elementSendText("NATIVE", "placeholder=Phone", 0, "096783356");
            client.elementSendText("NATIVE", "placeholder=Name", 0, "Name");
            client.elementSendText("NATIVE", "placeholder=Amount", 0, "1");
            client.click("NATIVE", "accessibilityLabel=countryButton", 0, 1);
            client.elementListSelect("", "text=Spain", 0, false);
            client.click("NATIVE", "xpath=//*[@accessibilityLabel='Spain']", 0, 1);
            client.click("NATIVE", "accessibilityLabel=sendPaymentButton", 0, 1);
            client.click("NATIVE", "text=Yes", 0, 1);
            client.click("NATIVE", "text=Logout", 0, 1);
        } catch (Exception e) {
            onFailure(client,e);
        } finally {
            finish(client);
        }
//		client.stopStepsGroup();

    }

    public void iOSEriBankTestNonInstrumented() {
        dumpType="Non-Instrumented";
        testName="IOS EriBank Test Non Instrumented";
        ExClient client = getClient("@os='ios'");

//		client.startStepsGroup("IOS EriBank Test Non Instrumented");
        try {
            client.uninstall("com.experitest.ExperiBank");
            client.install("cloud:com.experitest.ExperiBank", false, false);
            client.launch("com.experitest.ExperiBank", false, true);
            client.waitForElement("NATIVE", "xpath=//*[@text='Username']", 0,10000);

            client.elementSendText("NATIVE", "xpath=//*[@text='Username']", 0, "company");
            client.elementSendText("NATIVE", "xpath=//*[@text='Password']", 0, "company");
            client.click("NATIVE", "xpath=//*[@text='loginButton']", 0, 1);
            client.click("NATIVE", "xpath=//*[@text='makePaymentButton']", 0, 1);
            client.elementSendText("NATIVE", "xpath=//*[@text='Phone']", 0, "0987654321");
            client.elementSendText("NATIVE", "xpath=//*[@text='Name']", 0, "Name");
            client.elementSendText("NATIVE", "xpath=//*[@text='Amount']", 0, "10");
            client.click("NATIVE", "xpath=//*[@text='countryButton']", 0, 1);
            client.click("NATIVE", "xpath=//*[@text='Norway']", 0, 1);
            client.click("NATIVE", "xpath=//*[@value='sendPaymentButton']", 0, 1);
            client.click("NATIVE", "xpath=//*[@text='Yes']", 0, 1);
            client.click("NATIVE", "xpath=//*[@name='logoutButton']", 0, 1);
        } catch (Exception e) {
            onFailure(client,e);
        } finally {
            finish(client);
        }
//		client.stopStepsGroup();

    }

    public void iOSMobileTimerTest() {
        dumpType="Non-Instrumented";
        testName= "IOS Mobile Timer Test";
        ExClient client = getClient("@os='ios'");

//		client.startStepsGroup("IOS Mobile Timer Test ");
        try {
            client.launch("com.apple.mobiletimer", false, true);
            client.click("NATIVE", "xpath=//*[@text='Stopwatch']", 0, 1);

            if (client.isElementFound("Native", "xpath=//*[@value='Start']")) {
                client.click("NATIVE", "xpath=//*[@value='Start']", 0, 1);
            } else if (client.isElementFound("Native", "xpath=//*[@value='Stop']")) {
                client.click("NATIVE", "xpath=//*[@value='Stop']", 0, 1);
                if (client.isElementFound("Native", "xpath=//*[@value='Reset']")) {
                    client.click("NATIVE", "xpath=//*[@value='Reset']", 0, 1);

                }
            } else

                for (int i = 0; i < 10; i++) {
                    client.sleep(5000);
                    String time = client.elementGetText("NATIVE", "xpath=//*[@text and @class='UIAView' and ./parent::*[@class='UIAView' and ./parent::*[@class='UIAScrollView']]]", 0);
                    Utilities.log(runner,"Time :" + time);
                    client.click("NATIVE", "xpath=//*[@text='Lap']", 0, 1);
                }


            client.click("NATIVE", "xpath=//*[@name='Stop']", 0, 1);
            client.click("NATIVE", "xpath=//*[@text='Reset']", 0, 1);
        } catch (Exception e) {
            onFailure(client,e);
        } finally {
            finish(client);
        }
//		client.stopStepsGroup();


    }

    public void webWikipediaTest(String query) {
        dumpType="Web";

        testName= "Wikipedia Test";
        ExClient client = getClient(query);


        try {
            //client.startStepsGroup("Web Wikipedia Test ");

            client.launch("https://en.wikipedia.org/wiki/Main_Page", true, false);

            for (int i = 0; i < 10; i++) {
                client.click("WEB", "xpath=//*[@id='mw-mf-main-menu-button']", 0, 1);
                client.click("WEB", "xpath=//*[@text='Random']", 0, 1);
                client.hybridWaitForPageLoad(10000);
                if(client.waitForElement("Web","xpath=//*[@id='section_0']",0,10000)){
                    String section0 = client.elementGetText("WEB", "xpath=//*[@id='section_0']", 0);
                    Utilities.log(runner, section0);

                }
            }
            client.applicationClose(client.getCurrentApplicationName());
            //client.stopStepsGroup();
        } catch (Exception e) {
            onFailure(client,e);
        } finally {
            finish(client);
        }



    }

    public boolean isSetReporterFlag() {
        return setReporterFlag;
    }

    public void setSetReporterFlag(boolean setReporterFlag) {
        this.setReporterFlag = setReporterFlag;
    }

    public void setLogDeviceFlag(boolean logDeviceFlag) {
        this.logDeviceFlag = logDeviceFlag;
    }
}
