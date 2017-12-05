package Utils;

import MyMain.Enums;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

/**
 * Created by navot.dako on 12/5/2017.
 */
public class CloudApiShit {

    public static String doGet(String objectsToGet) throws IOException {
        String host = (new Enums()).hostName.replace("/index.html#","");
        String port = "";
        String Devices_URL = host + port + "/api/v1/"+objectsToGet;
        String authStringEnc;
        String name = "ayouba";
        String password = "Experitest2012";

        String authString = name + ":" + password;
        authStringEnc = Base64.getEncoder().encodeToString(authString.getBytes());
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
        int responseCode = ((HttpURLConnection) urlConnection).getResponseCode();
        Utilities.log("Sending 'GET' request to URL : " + url);
        Utilities.log("Response Code : " + responseCode);
        if (((HttpURLConnection) urlConnection).getResponseCode() < 300) {
            return result;
        } else {
            return null;
        }
    }
}
