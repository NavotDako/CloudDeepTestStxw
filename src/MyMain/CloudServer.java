package MyMain;

import Utils.Utilities;
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class CloudServer {
    public static String webPage;
    public static String authStringEnc;
    public static String DEVICES_URL = "/devices";
    public static String URL_ADDRESS = null;
    public static String HOST;
    public static int PORT;
    public static String USER;
    public static String PASSWORD;
    public static boolean SECURED = false;
    public static String GRID_URL;
    public static CloudServerName cloudName;
    public static String authString;
    public static String result;
    public static String PROJECTNAME;

    public CloudServer(CloudServerName cloudName) {
        this.cloudName = cloudName;
        updateCloudDetails();
        if (SECURED) {
            URL_ADDRESS = "https://" + HOST + ":" + PORT;
        } else {
            URL_ADDRESS = "http://" + HOST + ":" + PORT;
        }
        GRID_URL = URL_ADDRESS + "/wd/hub/";

        authString = this.USER + ":" + this.PASSWORD;
        if (SECURED) {
            webPage = "https://" + this.HOST + ":" + this.PORT + "/api/v1";
        } else {
            webPage = "http://" + this.HOST + ":" + this.PORT + "/api/v1";
        }

        byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
        authStringEnc = new String(authEncBytes);
    }

    public int getPORT() {
        return PORT;
    }

    public String getUSER() {
        return USER;
    }

    public String getPASS() {
        return PASSWORD;
    }

    public boolean getIsSECURED() {
        return SECURED;
    }

    public String getServerHostName() {
        return HOST;
    }

    public enum CloudServerName {
        MY, QA, MIRRON, KHALED, MASTER, KHALED_SECURED, RELEASE, ATT
    }

    public void updateCloudDetails() {
        switch (cloudName) {
            case MY:
                HOST = "192.168.2.13";
                PORT = 80;
                USER = "admin";
                PASSWORD = "Experitest2012";
                break;
            case KHALED_SECURED:
                HOST = "khaleds-mac-mini.local";
                PORT = 8090;
                USER = "khaleda";
                PASSWORD = "Experitest2012";
                SECURED = true;
                break;
            case QA:
                HOST = "192.168.2.135";
                PORT = 80;
                USER = "khaleda";
                PASSWORD = "Experitest2012";
                break;
            case MIRRON:
                HOST = "192.168.2.71";
                PORT = 8080;
                USER = "user1";
                PASSWORD = "Welc0me!";
                break;
            case KHALED:
                HOST = "192.168.2.156";
                PORT = 80;
                USER = "khaleda";
                PASSWORD = "Experitest2012";
                break;
            case MASTER:
                HOST = "mastercloud";
                PORT = 80;
                USER = "khaleda";
                PASSWORD = "Experitest2012";
                break;
            case RELEASE:
                HOST = "releasecloud";
                PORT = 80;
                USER = "khaleda";
                PASSWORD = "Experitest2012";
                break;
            default:
                HOST = "192.168.2.13";
                PORT = 80;
                USER = "admin";
                PASSWORD = "Experitest2012";
                break;
        }
    }

    public String doGet(String objectsToGet) throws IOException {

        String authString = USER + ":" + PASSWORD;
        authStringEnc = java.util.Base64.getEncoder().encodeToString(authString.getBytes());
        URL url = new URL(DEVICES_URL);
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
