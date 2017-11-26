package Utils;

import MyMain.JTestRunner;
import MyMain.Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by navot.dako on 11/26/2017.
 */
public class Utilities {

    static SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yy-HH:mm:ss,SS");

    public static void log(JTestRunner currentThread, String command) {
        Date currentTime = new Date();
        String line;
        currentTime.getTime();

        line = String.format("%-30s%-30s%-30s%-25s%-20s", ft.format(currentTime), currentThread.getName(),currentThread.User, currentThread.testName, command);

        System.out.println(line);
        Main.overallWriter.println(line);
        currentThread.pw.println(line);
        currentThread.pw.flush();
    }

    public static void log(String message) {
        Date currentTime = new Date();
        String line;
        currentTime.getTime();
        line = String.format("%-30s%-50s", ft.format(currentTime), message);
        System.out.println(line);
        Main.overallWriter.println(line);


    }

    public static PrintWriter CreateReportFile(int i) {

        File report = new File(Main.logsFolder, "Thread-" + i + ".txt");
        FileWriter fw = null;
        try {
            fw = new FileWriter(report);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        return pw;
    }

    public static void log(Exception e) {
        Date currentTime = new Date();
        String line;
        currentTime.getTime();
        line = String.format("%-30s%-50s", ft.format(currentTime), e.getMessage());
        System.out.println(line);
        e.printStackTrace();
        Main.overallWriter.println(line);
        e.printStackTrace(Main.overallWriter);


    }

    public static File CreateLogsFolderForRun() {
        Date currentTime = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("HH-mm-ss");
        File parentFile = new File("logs/" + ft.format(currentTime));
        // if the directory does not exist, create it
        if (!parentFile.exists()) {
            boolean result = false;

            try {
                parentFile.mkdir();
                result = true;
            } catch (SecurityException se) {
                Utilities.log(se);
            }
            if (result) {
                System.out.println(ft.format(currentTime) + " created");
                return parentFile;
            }
        }
        return parentFile;
    }

    public static PrintWriter createOverallReportFile(File logsFolder) {

        String reportName = "OverallReport.txt";
        File report = new File(logsFolder + "/" + reportName);
        FileWriter fw = null;
        try {
            fw = new FileWriter(report);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        return pw;

    }

    public static void sleep(int time){
        try {
                Thread.sleep(time);
                log("wait "+time+" milliseconds");
            } catch (Exception e) {
                Utilities.log(e);
            }
    }

}
