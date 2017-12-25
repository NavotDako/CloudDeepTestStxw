package STASuite.Tests;

import STASuite.STABaseTest;
import STASuite.STARunner;
import Utils.MyILogger;
import Utils.VMProperties;
import com.experitest.client.Client;
import org.junit.Test;

/**
 * Created by navot.dako on 12/4/2017.
 */
public class STAndroidTests extends STABaseTest {
    private String vmClientQuery;

    @Test
    public void test() {

        if(this.runner==null){
            System.out.println("Runner Is Null!, Find out why.");
        }



        STA.androidEriBankTestInstrumented();
        STA.androidEriBankTestNonInstrumented();
        STA.androidSimulateCaptureTest();
        STA.webAutomationSiteTest("@os='android'");
        STA.webWikipediaTest("@os='android'");

    }
}
