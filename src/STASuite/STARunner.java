package STASuite;

import java.io.IOException;
import java.io.PrintWriter;

import MyMain.*;
import STASuite.Tests.*;
import Utils.Utilities;
import Utils.VMProperties;

import org.junit.Assert;
import org.junit.runner.JUnitCore;

import org.junit.runner.Result;

public class STARunner extends BaseRunner {

    String srcPath;
    String dstPath;
    public String jarRemoteFolderPath;
    String jarName;
    public String VMAddress = "";
    public int VMClientNumber = 1;
    public String VMPassword;
    public int UserIndex;
    public String VMUser;
    public String VMSTAVersion;


    public STARunner(int i, PrintWriter overallSummaryWriter, PrintWriter overallWriter) {
        super("STARunner", i, overallSummaryWriter, overallWriter);

    }

    @Override
    public void run() {
        pw = Utilities.CreateReportFile(this, iteration);
        Utilities.log(this, "Starting Thread Num - " + iteration + " - Thread Name is - " + Thread.currentThread().getName());

        try {
            VMUser = VMProperties.getVMUser(this.iteration);
            VMAddress = VMProperties.getVMAddress(this.iteration);
            VMPassword = VMProperties.getVMPassword(this.iteration);
            VMSTAVersion = VMProperties.getVMSTAVersion(this.iteration);
            VMClientNumber = VMProperties.getVMClientsNumber(this.iteration);
            srcPath = VMProperties.getJarSource();
            dstPath = VMProperties.getJarDest();
            jarRemoteFolderPath = "C:\\" + VMProperties.getJarDest();
            jarName = VMProperties.getJarName();
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }

        UserIndex = iteration;
        user = "testPlanUser" + iteration;
        userType = "Admin";

        Utilities.log(this, "On VM at " + VMAddress + "Installing And Launching STA version " + VMSTAVersion + " Login to Cloud as User " + UserIndex);
        try {
            Utilities.RemoteCopy(this.VMAddress, this.VMUser, this.VMPassword, srcPath, dstPath);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }

        String result = Utilities.RemoteJarLaunchServer(this, VMAddress, jarRemoteFolderPath, jarName, "" + UserIndex, VMSTAVersion);

        if (result.equals("")) {
            Assert.fail("No result Returned ");
        }

        Utilities.log(this, "Finished Launching SeeTest On " + VMAddress);

        while (true) {

            this.testClass = SelectTestsToRun(rand.nextInt(3));
            //       this.testClass = getAction(rand.nextInt(enums.Actions.length));

            Long currTime = System.currentTimeMillis();
            testName = testClass.getSimpleName() + "[" + this.VMAddress + "]" + " " + currTime;

            Result r = JUnitCore.runClasses(testClass);

            GoToSleep(10);
        }
    }

    private Class SelectTestsToRun(int i) {
        switch (i){
            case  0: return STAndroidTests.class;
            case  1: return STiOSTests.class;
            case  2: return STBothOSTests.class;

        }
        return null;
    }


}


