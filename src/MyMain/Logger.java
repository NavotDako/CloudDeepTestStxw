package MyMain;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by khaled.abbas on 11/26/2017.
 */
public class Logger {


    public enum LogLevel{
        DEBUG, TRACE, ERROR
    }

    private static Logger instance;
    private static LogLevel loggingLevel;
    private static PrintWriter writer = null;
    private Logger(){
        try {

            writer = new PrintWriter(new BufferedWriter(new FileWriter("reports\\DebugLog.txt", true)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //static block initialization for exception handling
    static{
        try{
            instance = new Logger();
        }catch(Exception e){
            throw new RuntimeException("Exception occured in creating singleton instance");
        }
    }
    public static void createDir(){
        File logsFolder = new File("reports");
        if (!logsFolder.exists()) logsFolder.mkdir();
        for (File file : logsFolder.listFiles()) DeleteRecursive(file);
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter("reports\\DebugLog.txt", true)));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public static void DeleteRecursive(File fileOrDirectory) {

        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                DeleteRecursive(child);

        fileOrDirectory.delete();

    }

    private static Logger getInstance(){
        return instance;
    }

    public static void setLoggingLevel(LogLevel loggingLevel) {
        Logger.loggingLevel = loggingLevel;
    }

    public static synchronized void debug(String message){
        if(loggingLevel.equals(LogLevel.DEBUG)) {
            getInstance().write(message);
        }
    }
    public static synchronized void trace(String message){
        if(loggingLevel.equals(LogLevel.DEBUG) || loggingLevel.equals(LogLevel.TRACE)) {
            getInstance().write(message);
        }
    }
    private static synchronized void write(String message){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        writer.write(String.format("%-10s%-10s%-70s\n", sdf.format(new Date(System.currentTimeMillis())),loggingLevel.toString(), message));
        writer.flush();
    }

    public static synchronized void Error(String s) {
        getInstance().write(s);
    }
}
