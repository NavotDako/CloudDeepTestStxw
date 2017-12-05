package STASuite;

import MyMain.BaseBaseTest;
import MyMain.Main;
import Utils.Utilities;
import org.junit.After;
import org.junit.Before;

import java.io.File;
import java.util.Date;

/**
 * Created by navot.dako on 12/4/2017.
 */
public class STABaseTest extends BaseBaseTest{

    @Before
    public void SetUp() throws Exception {
        runner = (STARunner) Thread.currentThread();

        String cloudUser = "user"+ runner.iteration;

    }
    @After
    public void finish() {
        Utilities.log(runner, "finish");
        Date CurrentTime = new Date();

        String thisPath =(new File("")).getAbsolutePath()+"\\";

        String line = String.format("%-30s%-30s%-30s%-30s%-5s", CurrentTime, runner.User, runner.testClass.getName(), (((double) (CurrentTime.getTime() - startTime.getTime())) / 60000), thisPath + Main.logsFolder.getName() + "\\" + runner.TestName);
        Main.overallWriter.println(line);
        Main.overallWriter.flush();
    }

}
