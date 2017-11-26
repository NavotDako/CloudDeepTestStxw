package MyMain;

import Utils.Utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

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
            Utilities.log("creating directory: " + devicefile.getName());
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







}
