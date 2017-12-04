package Utils;

import MyMain.BaseRunner;
import MyMain.Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class Utilities {

    static SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yy-HH:mm:ss,SS");

    public static void log(BaseRunner runner, String command) {
        Date currentTime = new Date();
        String line;
        currentTime.getTime();

        line = String.format("%-30s%-30s%-30s%-30s%-20s", ft.format(currentTime), runner.TYPE+"_"+runner.getName(), runner.User, runner.testName, command);

        System.out.println(line);
        runner.overallWriter.println(line);
        runner.pw.println(line);
        runner.pw.flush();
        runner.overallWriter.flush();
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

    public static PrintWriter CreateReportFile(BaseRunner runner, int i) {
        File logs = new File(Main.logsFolder + "/" + runner.TYPE);
        if (!logs.exists())
            logs.mkdir();

        File report = new File(logs, i + "-" + runner.getName() + ".txt");
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

    public static void log(BaseRunner runner, Exception e) {
        Date currentTime = new Date();
        String line;
        currentTime.getTime();
        line = String.format("%-30s%-30s%-30s%-30s%-50s", ft.format(currentTime), runner.TYPE+"_"+runner.getName(), runner.User, runner.testName, e.getMessage().replace("\n", "\t"));
        System.out.println(line);
        e.printStackTrace();
        runner.overallWriter.println(line);
        e.printStackTrace(runner.overallWriter);
        runner.overallWriter.flush();
    }

    public static File CreateLogsFolderForRun() {
        File logs = new File("logs");
        if (!logs.exists())
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
        Iterator it = Main.suites.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if ((boolean) pair.getValue()) {
                File logsForRunner = new File("logs/" + pair.getKey());
                if (!logsForRunner.exists())
                    logsForRunner.mkdir();

            }

        }

        return parentFile;
    }

    public static PrintWriter createReportFile(File logsFolder, String runner, String fileName) {
        File logs;
        if (runner.equals("")) {
            logs = logsFolder;
        } else {
            logs = new File(logsFolder + "/" + runner);
        }

        if (!logs.exists())
            logs.mkdir();

        String reportName = fileName + ".txt";
        File report = new File(logs + "/" + reportName);
        FileWriter fw = null;
        try {
            fw = new FileWriter(report);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        return pw;

    }

    public static void sleep(BaseRunner runner, int time) {
        try {
            log(runner, "starting to wait - " + time + " milliseconds");
            Thread.sleep(time);
        } catch (Exception e) {
            log(runner, e);
        }
    }

    public static void writeToSummary(BaseRunner runner, String chosenDeviceName, String status) {
        Date currentTime = new Date();
        String line;
        currentTime.getTime();
        line = String.format("%-25s%-15s%-30s%-30s%-30s%-20s", ft.format(currentTime), runner.TYPE+"_"+runner.getName(), runner.User, runner.testName, chosenDeviceName, status);
        System.out.println(line);
        runner.overallSummaryWriter.println(line);
        runner.overallSummaryWriter.flush();

        Main.summaryWriter.println(line);
        Main.summaryWriter.flush();
    }
}
