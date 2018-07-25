package cloudHealthMonitors;

import MyMain.CloudServer;
import MyMain.Main;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CollectSupportData extends Thread {
    @Override
    public void run() {
        while (true) {
            try {
                SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                Date now = new Date();
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet getRequest = new HttpGet(
                        CloudServer.URL_ADDRESS + "/api/v2/configuration/collect-support-data/true/true/");
                getRequest.addHeader("accept", "application/json");
                getRequest.addHeader("Authorization", "Basic Y2xvdWR1c2VyOkV4cGVyaXRlc3QyMDEy");
                HttpResponse response = httpClient.execute(getRequest);

                if (response.getStatusLine().getStatusCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + response.getStatusLine().getStatusCode());
                }

                File targetFile = new File(Main.supportDataFolder + "//" + sdfFormat.format(now) + ".zip");
                FileUtils.copyInputStreamToFile(response.getEntity().getContent(), targetFile);
                httpClient.getConnectionManager().shutdown();

            } catch (ClientProtocolException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();
            }
            try {
                Thread.sleep(30 * 60 * 1000);//we collect data once in half an hour
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
