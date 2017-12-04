package Utils;

import MyMain.BaseRunner;
import MyMain.Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilities {

    static SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yy-HH:mm:ss,SS");

    public static void log(BaseRunner currentThread, String command) {
        Date currentTime = new Date();
        String line;
        currentTime.getTime();

        line = String.format("%-30s%-30s%-30s%-30s%-20s", ft.format(currentTime), currentThread.getName(), currentThread.User, currentThread.testName, command);

        System.out.println(line);
        Main.overallWriter.println(line);
        currentThread.pw.println(line);
        currentThread.pw.flush();
        Main.overallWriter.flush();
    }

    public static void log(String message) {
        Date currentTime = new Date();
        String line;
        currentTime.getTime();
        line = String.format("%-30s%-50s", ft.format(currentTime), message);
        System.out.println(line);
        Main.overallWriter.println(line);
        Main.overallWriter.flush();
    }

    public static PrintWriter CreateReportFile(Thread thread, int i) {

        File report = new File(Main.logsFolder, i + "-" + thread.getName() + ".txt");
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
        line = String.format("%-30s%-50s", ft.format(currentTime), e.getMessage().replace("\n", "\t"));
        System.out.println(line);
        e.printStackTrace();
        Main.overallWriter.println(line);
        e.printStackTrace(Main.overallWriter);
        Main.overallWriter.flush();
    }

    public static void log(BaseRunner currentThread, Exception e) {
        Date currentTime = new Date();
        String line;
        currentTime.getTime();
        line = String.format("%-30s%-30s%-30s%-30s%-50s", ft.format(currentTime), currentThread.getName(), currentThread.User, currentThread.testName, e.getMessage().replace("\n", "\t"));
        System.out.println(line);
        e.printStackTrace();
        Main.overallWriter.println(line);
        e.printStackTrace(Main.overallWriter);
        Main.overallWriter.flush();
    }

    public static File CreateLogsFolderForRun() {
        File logs = new File("logs");
        if(!logs.exists())
            logs.mkdir();


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

    public static PrintWriter createOverallReportFile(File logsFolder, String fileName) {

        String reportName = fileName + ".txt";
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

    public static void sleep(BaseRunner currentThread, int time) {
        try {
            log(currentThread, "starting to wait - " + time + " milliseconds");
            Thread.sleep(time);
        } catch (Exception e) {
            log(currentThread, e);
        }
    }

    public static void writeToSummary(BaseRunner currentThread,String chosenDeviceName, String status) {
        Date currentTime = new Date();
        String line;
        currentTime.getTime();
        line = String.format("%-25s%-15s%-30s%-30s%-30s%-20s", ft.format(currentTime), currentThread.getName(), currentThread.User, currentThread.testName, chosenDeviceName, status);
        System.out.println(line);
        Main.overallSummaryWriter.println(line);
        Main.overallSummaryWriter.flush();
    }
}
