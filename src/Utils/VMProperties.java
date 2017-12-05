package Utils;

import java.util.*;
import java.io.*;

/**
 * Created by eyal.neumann on 11/29/2017.
 */
public class VMProperties {
    public static void main(String[] args)throws Exception{

        System.out.println(getVMClientsNumber(1));
    }

    public static String getJarName() throws IOException {
        return getVMProperty("jar.name");
    }
    public static String getJarSource() throws IOException {
        return getVMProperty("jar.source");
    }
    public static String getJarDest() throws IOException {
        return getVMProperty("jar.dest");
    }
    public static String getVMNumber() throws IOException {
        return getVMProperty("vm.number");
    }
    public static String getVMUser(int number) throws IOException {
        return getVMProperty("vm."+number+".username");
    }
    public static String getVMAddress(int number) throws IOException {
        return getVMProperty("vm."+number+".address");
    }
    public static String getVMPassword(int number) throws IOException {
        return getVMProperty("vm."+number+".password");
    }
    public static String getVMSTAVersion(int number) throws IOException {
        return getVMProperty("vm."+number+".sta.version");
    }
    public static int getVMClientsNumber(int number) throws IOException {
        return Integer.parseInt(getVMProperty("vm."+number+".clients.number"));
    }
    public static String getVMClientQuery(int vmNumber,int clientNumber) throws IOException {
        return getVMProperty("vm."+vmNumber+".client."+clientNumber+".query");
    }

    private static String getVMProperty(String propertyName) throws IOException {
        FileReader reader=new FileReader("Utils/vmTest.properties");
        Properties p=new Properties();
        p.load(reader);
        return p.getProperty(propertyName);
    }

}
