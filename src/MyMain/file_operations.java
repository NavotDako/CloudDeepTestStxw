package MyMain;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class file_operations {
    file_operations() {
    }

    public File create_file(File currentTimeFolder, String deviceName) {
        String puredevicenmae = "";

        puredevicenmae = deviceName;
        while (puredevicenmae.contains(":")) {
            puredevicenmae = puredevicenmae.split(":")[1];
        }

        File devicefile = new File(currentTimeFolder, puredevicenmae);
        // if the directory does not exist, create it
        if (!devicefile.exists()) {
            System.out.println("creating directory: " + devicefile.getName());
            boolean result = false;

            try {
                devicefile.mkdir();

                result = true;
            } catch (SecurityException se) {
                //handle it
            }
            if (result) {
                System.out.println(deviceName + " created");
                return devicefile;
            }


        }
        return devicefile;
    }

    public PrintWriter CreateReportFile(String TestName) {

        File report = new File(Main.LogsFile, TestName + ".txt");
        FileWriter fw = null;
        try {
            fw = new FileWriter(report);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        return pw;
    }

    public File createParentFile() {
        Long currTime = System.currentTimeMillis();
        File parentFile = new File("Logs " + Long.toString(currTime));
        // if the directory does not exist, create it
        if (!parentFile.exists()) {
            System.out.println("creating directory: " + parentFile.getName());
            boolean result = false;

            try {
                parentFile.mkdir();
                result = true;
            } catch (SecurityException se) {
                //handle it
            }
            if (result) {
                System.out.println(Long.toString(currTime) + " created");
                return parentFile;
            }
        }
        return parentFile;
    }

    public PrintWriter createSummaryFile() {
        Long currTime = System.currentTimeMillis();
        String reportname = "Overall Summary View " + Long.toString(currTime) + ".txt";
        File report = new File(reportname);
        FileWriter fw = null;
        try {
            fw = new FileWriter(report);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        return pw;

    }


}
