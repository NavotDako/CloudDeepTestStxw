package STASuite;

import Utils.Utilities;
import com.experitest.client.Client;
import com.experitest.client.log.ILogger;
import com.experitest.client.log.Level;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by eyal.neumann on 12/11/2017.
 */
public class ExClient extends Client{
    private final String host;
    private final int port;
    private final boolean useSessionID;
    private STARunner runner=null;


    private static AtomicInteger installationsAttempts =new AtomicInteger();
    private static AtomicInteger installationsFailures =new AtomicInteger();

    private static AtomicInteger launchAttempts =new AtomicInteger();
    private static AtomicInteger launchFailures =new AtomicInteger();

    private static AtomicInteger clickAttempts =new AtomicInteger();
    private static AtomicInteger clickFailures =new AtomicInteger();

    private String reportFolder;
    private ILogger logger;

    public ExClient(String host, int port, boolean useSessionID){
        super(host,port,useSessionID);
        this.host=host;
        this.port=port;
        this.useSessionID=useSessionID;
    }

    public void setRunner(STARunner runner){
        this.runner=runner;
    }

    @Override
    public void setLogger(ILogger logger) {
        this.logger=logger;
        super.setLogger(logger);
    }

    private void log(String message){
        if(this.logger!=null){
            this.logger.log(Level.INFO,message,null);
        }
        else{
            Utilities.log(this.runner,message);
        }
    }
    private void log(Exception e){
        if(this.logger!=null){
            this.logger.log(Level.INFO,e.getMessage(),e);
        }
        else{
            Utilities.log(this.runner,e);
        }
    }





    @Override
    public String setReporter(String reporterName, String directory, String testName) {
        String setReporter = super.setReporter(reporterName, directory, testName);
        String[] split = setReporter.split("\\\\");
        this.reportFolder = split[split.length-1];
        return setReporter;
    }

    @Override
    public void click(String zone, String element, int index, int clickCount) {

        try {
            int attemptNumber = clickAttempts.incrementAndGet();
            Utilities.log(this.runner,"Attempting to Click ["+zone+":"+element+" ("+index+")");
            Utilities.log(this.runner,"This Method has been attempted "+attemptNumber+ " Times");
            super.click(zone, element, index, clickCount);
            Utilities.log(this.runner,"Succeeded  to Click ["+zone+":"+element+" ("+index+")");
            Utilities.log(this.runner,"Total Click Attempts :"+clickAttempts.get()+" Total Installtion Failures :"+clickFailures.get());

        } catch (Exception e) {
            Utilities.log(this.runner,"Failed to Click ["+zone+":"+element+" ("+index+")");
            int incrementAndGet = clickFailures.incrementAndGet();
            Utilities.log(this.runner,"So far Click have  failed "+incrementAndGet+" times");
            Utilities.log(this.runner,e);
            Utilities.log(this.runner,"Total Click Attempts :"+clickAttempts.get()+" Total Click Failures :"+clickFailures.get());
            throw e;
        }
    }

    @Override
    public boolean install(String path, boolean instrument, boolean keepData) {
        boolean install = false;
        try {
            int attemptNumber = installationsAttempts.incrementAndGet();
            Utilities.log(this.runner,"Attempting to Install "+path);
            Utilities.log(this.runner,"This Method has been attempted "+attemptNumber+ " Times");
            install = super.install(path, instrument, keepData);
            Utilities.log(this.runner,"Install Succesfull :"+path);
            Utilities.log(this.runner,"Total Install Attempts :"+installationsAttempts.get()+" Total Installtion Failures :"+installationsFailures.get());

        } catch (Exception e) {
            Utilities.log(this.runner,"Failed To Install "+path);
            int incrementAndGet = installationsFailures.incrementAndGet();
            Utilities.log(this.runner,"So far Install a failed "+incrementAndGet+" times");
            Utilities.log(this.runner,e);
            Utilities.log(this.runner,"Total Install Attempts :"+installationsAttempts.get()+" Total Installtion Failures :"+installationsFailures.get());

            throw e;
        }

        return install;
    }

    @Override
    public void launch(String activityURL, boolean instrument, boolean stopIfRunning)  {
        try {
            int attemptNumber = launchAttempts.incrementAndGet();
            Utilities.log(this.runner,"Attempting to Launch  "+activityURL);
            Utilities.log(this.runner,"This Method has been attempted "+attemptNumber+ " Times");

            super.launch(activityURL, instrument, stopIfRunning);
            if(activityURL.startsWith("http")){
                // A Web Test
                this.hybridWaitForPageLoad(10000);
                String currentURL = getCurrentURL();
                if (!currentURL.replace(".m.",".").equals(activityURL)){
                    RuntimeException e =new RuntimeException("Failure To launch The site :"+activityURL+" Current URL :"+currentURL);
                    throw e;
                }
            }else
            {
                String currentApplicationName = this.getCurrentApplicationName();
                if(!activityURL.contains(currentApplicationName)){
                    //RuntimeException e =new RuntimeException("Failure To launch The App :"+activityURL+" Current Application :"+currentApplicationName);
                    Utilities.log(runner,"Possible Failure To launch The App :["+activityURL+"] Current Application is :["+currentApplicationName+"]");

                    //throw e;
                }
            }
            Utilities.log(this.runner," Launch :"+activityURL);
            Utilities.log(this.runner,"Total Launch Attempts :"+launchAttempts.get()+" Total Launch Failures :"+launchFailures.get());

        } catch (Exception e) {
            Utilities.log(this.runner,"Failed To Launch "+activityURL);
            int incrementAndGet = launchFailures.incrementAndGet();
            Utilities.log(this.runner,"So far Launch has failed "+incrementAndGet+" times");
            Utilities.log(this.runner,e);
            Utilities.log(this.runner,"Total Launch Attempts :"+launchAttempts.get()+" Total Launch Failures :"+launchFailures.get());
            throw e;
        }
    }

    public String getCurrentURL() {
        return this.hybridRunJavascript("", 0, "result=window.location.href");
    }

    public String getReportFolder() {
        return reportFolder;
    }
}
