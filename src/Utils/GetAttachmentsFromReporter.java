package Utils;

import MyMain.Main;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import java.io.FileOutputStream;
import java.io.InputStream;

public class GetAttachmentsFromReporter {


//    private static String user = "khaleda";
    private static String password = "Experitest2012";
    public static void main(String[] args) {
//        System.out.println(downloadTestWithoutProject("http://192.168.2.63:8052/#/test/14779"));
        System.out.println(downloadAttachmentsFromReporter("http://192.168.2.63:8052/#/test/107149/project/Default/","khaleda"));
    }

    private static void downloadUtil(String URL, String localPath, String user){
        HttpResponse<InputStream> response = null;
        try {
            response = Unirest.get (URL)
                    .basicAuth (user, password)
                    .header("content-type", "*/*")
                    .asBinary();
            InputStream inputStream = response.getRawBody();
            FileOutputStream fileOutputStream = new FileOutputStream(localPath);
            int bytesRead = -1;
            byte[] buffer = new byte[4096];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param testURL - Example input http://192.168.2.63:8052/#/test/14808/project/Default/
     * @return local path
     */
    private static String downloadTestWithoutProject(String testURL, String username){
//        String test = "http://192.168.2.63:8052/#/test/14779";
        String RestAPIURL = testURL.replace("#/test","api") + "/attachments";
        String localPath = Main.reporterAttachmentsFolder + testURL.substring(testURL.indexOf("/test") + "/test".length()) + ".zip";//adds test id to the local path
        downloadUtil(RestAPIURL, localPath, username);

        return localPath;
    }


    /**
     * @param testURL - Example input http://192.168.2.63:8052/#/test/376/project/ayoubProjectDeepTest1/
     * @return local path
     */
    private static String downloadTestWithProject(String testURL, String username){

        String testID = (testURL.substring(testURL.indexOf("/test") + "/test".length(), testURL.indexOf("/project"))).replace("/", "");
        String projectName = (testURL.substring(testURL.indexOf("/project") + "/project".length())).replace("/", "");
        String localPath = Main.reporterAttachmentsFolder + "/" + projectName + "-" + testID + ".zip";//adds project name + test id to the local path
        String RestAPIURL = testURL.substring(0, testURL.indexOf("#/test")) + "api/" + projectName + "/" + testID + "/attachments-name";
        downloadUtil(RestAPIURL, localPath, username);
        return localPath;
    }

    public static String downloadAttachmentsFromReporter(String url, String username){

        if(url.contains("project")){
            return downloadTestWithProject(url, username);
        }
        return downloadTestWithoutProject(url, username);
    }
}
