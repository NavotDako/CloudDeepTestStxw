//package Framework;
//
//import io.appium.java_client.AppiumDriver;
//import org.apache.commons.io.FileUtils;
//import org.openqa.selenium.OutputType;
//
//import java.io.*;
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//
///**
// * Created by navot.dako on 6/5/2017.
// */
//public class utils {
//    public static void writeToDeviceLog(String deviceID, String stringToWrite) {
//        PrintWriter writer = null;
//        try {
//            writer = new PrintWriter(new BufferedWriter(new FileWriter(Runner.reportDir.getName() + "\\" + deviceID + ".txt", true)));
//            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//            writer.write(String.format("%-30s %-100s\n",  LocalDateTime.now().toString(), stringToWrite));
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void DeleteRecursive(File fileOrDirectory) {
//
//        if (fileOrDirectory.isDirectory())
//            for (File child : fileOrDirectory.listFiles())
//                DeleteRecursive(child);
//
//        fileOrDirectory.delete();
//
//    }
//
//    public static void writeToOverall(boolean status, String deviceID, String testName, Exception e, long time, String pathInReporter) {
//        PrintWriter writer = null;
//        PrintWriter exceptionWriter = null;
//        try {
//
//            exceptionWriter = new PrintWriter(new BufferedWriter(new FileWriter(Runner.reportDir.getName() + "\\Exceptions.txt", true)));
//            writer = new PrintWriter(new BufferedWriter(new FileWriter(Runner.reportDir.getName() + "\\overallReport.txt", true)));
//            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//            if (status) {
//                writer.write(String.format("%-30s%-50s%-10s%-20s%-20s%-50s\n", LocalDateTime.now().toString(), deviceID, "PASS", testName, (time / 1000) + "s", pathInReporter));
//            } else {
//                writer.write(String.format("%-30s%-50s%-10s%-20s%-20s%-50s\n ", LocalDateTime.now().toString(), deviceID, "FAIL", testName, (time / 1000) + "s",pathInReporter));
//                exceptionWriter.write(String.format("%-30s%-50s%-10s%-20s\n ", LocalDateTime.now().toString(), deviceID, "FAIL", testName));
//                e.printStackTrace(exceptionWriter);
//
//
//            }
//            writer.close();
//            exceptionWriter.close();
//        } catch (IOException ex) {
//            e.printStackTrace();
//        }
//    }
//
//    public void screenshot(AppiumDriver driver, String path) throws IOException {
//        File srcFile = driver.getScreenshotAs(OutputType.FILE);
//        String filename = "dfjklsdhfjk" + "#" + System.currentTimeMillis();
//        File targetFile = new File(path + "\\" + filename + ".jpg");
//        FileUtils.copyFile(srcFile, targetFile);
//    }
//}
