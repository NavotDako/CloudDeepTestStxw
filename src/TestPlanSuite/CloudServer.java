package TestPlanSuite;

import org.apache.commons.codec.binary.Base64;

public class CloudServer {
    private static String webPage;
    private static String authStringEnc;
    private static String DEVICES_URL = "/devices";
    public String HOST;
    public int PORT;
    public String USER;
    public String PASS;
    public boolean SECURED = false;
    public String gridURL;
    public CloudServerName cloudName;
    private String authString;
    String result;
    public String PROJECT;

    public CloudServer(CloudServerName cloudName) {
        this.cloudName = cloudName;
        updateCloudDetails();
        if (SECURED) {
            gridURL = "https://" + HOST + ":" + PORT + "/wd/hub/";
        } else {
            gridURL = "http://" + HOST + ":" + PORT + "/wd/hub/";
        }
        authString = this.USER + ":" + this.PASS;
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
        return PASS;
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
                PASS = "Experitest2012";
                break;
            case KHALED_SECURED:
                HOST = "khaleds-mac-mini.local";
                PORT = 8090;
                USER = "khaleda";
                PASS = "Experitest2012";
                SECURED = true;
                break;
            case QA:
                HOST = "192.168.2.135";
                PORT = 80;
                USER = "khaleda";
                PASS = "Experitest2012";
                break;
            case MIRRON:
                HOST = "192.168.2.71";
                PORT = 8080;
                USER = "user1";
                PASS = "Welc0me!";
                break;
            case KHALED:
                HOST = "192.168.2.156";
                PORT = 80;
                USER = "khaleda";
                PASS = "Experitest2012";
                break;
            case MASTER:
                HOST = "mastercloud";
                PORT = 80;
                USER = "khaleda";
                PASS = "Experitest2012";
                break;
            case RELEASE:
                HOST = "releasecloud";
                PORT = 80;
                USER = "khaleda";
                PASS = "Experitest2012";
                break;
            default:
                HOST = "192.168.2.13";
                PORT = 80;
                USER = "admin";
                PASS = "Experitest2012";
                break;
        }
    }
}
