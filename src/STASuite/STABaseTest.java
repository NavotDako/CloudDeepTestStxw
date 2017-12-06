package STASuite;

import MyMain.BaseBaseTest;
import MyMain.Main;
import STASuite.Tests.SeeTestTestsStandAlone;
import Utils.Utilities;
import org.junit.After;
import org.junit.Before;

import java.io.File;
import java.util.Date;


public class STABaseTest extends BaseBaseTest{

    protected SeeTestTestsStandAlone STA = new SeeTestTestsStandAlone((STARunner) runner);

    @Before
    public void SetUp() throws Exception {
        runner = (STARunner) Thread.currentThread();

        String cloudUser = "testPlanUser"+ runner.iteration;

    }
    @After
    public void finish() {
        Utilities.log(runner, "finish");
        Date CurrentTime = new Date();

        String thisPath =(new File("")).getAbsolutePath()+"\\";

        String line = String.format("%-30s%-30s%-30s%-30s%-5s", CurrentTime, runner.user, runner.testClass.getName(), (((double) (CurrentTime.getTime() - startTime.getTime())) / 60000), thisPath + Main.logsFolder.getName() + "\\" + runner.testName);
        Main.overallWriter.println(line);
        Main.overallWriter.flush();
    }

}
