package TestPlanSuite.cloudEntities;

import TestPlanSuite.CloudServer;
import Utils.Utilities;
import org.junit.Assert;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Created by khaled.abbas on 11/15/2017.
 */
public final class RestAPIBuilder {

    private static String authStringEnc;
    private static String username;
    private static String password;
    private static String cloudIP;
    private static int cloudPort;
    private static boolean isSecured;
    private static String prefix;

    public static CloudServer getCloudServerProperties() {
        return cloudServerProperties;
    }

    private static CloudServer cloudServerProperties;

    public static String getAuthStringEnc() {
        return authStringEnc;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static String getCloudIP() {
        return cloudIP;
    }

    public static int getCloudPort() {
        return cloudPort;
    }

    public static boolean isSecured() {
        return isSecured;
    }

    public static String getPrefix() {
        return prefix;
    }

    public RestAPIBuilder(CloudServer cloudServer){
        if(cloudServerProperties != null){
            return;
        }
        cloudServerProperties = cloudServer;

        cloudIP = cloudServerProperties.getServerHostName();
        cloudPort = cloudServerProperties.getPORT();
        username = cloudServerProperties.getUSER();
        password = cloudServerProperties.getPASS();
        isSecured = cloudServerProperties.getIsSECURED();
        prefix = isSecured ? "https://" : "http://";
        String authString = username + ":" + password;
        authStringEnc = Base64.getEncoder().encodeToString(authString.getBytes());
    }

    public synchronized static void printGet(URL url, HttpURLConnection httpURLConnection, String result) throws IOException {
        int responseCode = httpURLConnection.getResponseCode();
        Utilities.log("\nSending 'GET' request to URL : " + url);
        Utilities.log("Response Code : " + responseCode);
    }

    /**
     * @param entity can be "/users" / "/projects" / "/devices" etc
     */
    public synchronized static String doGet(String entity, String webPage, String authStringEnc) throws IOException {
        URL url = new URL(webPage+entity);
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
        boolean isResponseValid = ((HttpURLConnection)urlConnection).getResponseCode() < 300;
        Assert.assertTrue("Did not get a valid response", isResponseValid);
        return result;
    }

    public synchronized static String doPost(String entity , String query, String webPage, String authStringEnc) throws IOException {
        URL url = new URL(webPage+entity);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setDoOutput(true);
        urlConnection.setRequestProperty("Accept", "application/json");
        urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + StandardCharsets.UTF_8.name());
        urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);

        OutputStream output = urlConnection.getOutputStream();
        output.write(query.getBytes(StandardCharsets.UTF_8.name()));

        HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;

        printPost(url, httpURLConnection, query);

        InputStream stream = null;

        if (httpURLConnection.getResponseCode() >= 400) {
            stream = httpURLConnection.getErrorStream();

        } else {
            stream = httpURLConnection.getInputStream();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        String inputLine;
        StringBuffer responseBuffer = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            responseBuffer.append(inputLine);
        }
        in.close();

        //print result
        Utilities.log(responseBuffer.toString());
        boolean isResponseValid = httpURLConnection.getResponseCode() < 300;
        Assert.assertTrue("Did not get a valid response", isResponseValid);
        return responseBuffer.toString();
    }

    public synchronized static void printPost(URL url, HttpURLConnection httpURLConnection, String query) throws IOException {
        int responseCode = httpURLConnection.getResponseCode();
        Utilities.log("Sending 'POST' request to URL : " + url);
        Utilities.log("Sending Query : " + query);
        Utilities.log("Response Code : " + responseCode);
    }
}
