package Cloud_API;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

import MyMain.JTestRunner;
import MyMain.Main;
import Utils.Utilities;

public class GetDevices {
    private String host = "qacloud.experitest.com";
    private String port = "";
    private String Devices_URL = "https://" + host + port + "/api/v1/devices";
    private String authStringEnc;
    String threadName;

    public GetDevices(String threadName) throws IOException {
        this.threadName = threadName;
        Utilities.log(threadName + " - GetDevices setup");
        String name = "ayouba";
        String password = "Experitest2012";

        String authString = name + ":" + password;
        authStringEnc = Base64.getEncoder().encodeToString(authString.getBytes());

    }


    protected void printGet(URL url, HttpURLConnection httpURLConnection, String result) throws IOException {
        int responseCode = httpURLConnection.getResponseCode();
        Utilities.log(threadName+ " - Sending 'GET' request to URL : " + url);
        Utilities.log(threadName+ " - Response Code : " + responseCode);
        //Utilities.log(result);
    }

    public String doGet() throws IOException {
        URL url = new URL(Devices_URL);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
        InputStream is = urlConnection.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        int numCharsRead;
        char[] charArray = new char[1024];
        StringBuffer sb = new StringBuffer();
        while ((numCharsRead = isr.read(charArray)) > 0) {
            sb.append(charArray, 0, numCharsRead);
        }
        String result = sb.toString();
        printGet(url, (HttpURLConnection) urlConnection, result);
        if (((HttpURLConnection) urlConnection).getResponseCode() < 300) {

            return result;
        } else {
            return null;
        }
    }


}