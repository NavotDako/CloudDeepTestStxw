package Utils;

import java.io.Closeable;
import java.io.IOException;


public class RemoteRoboCopy implements Closeable {
    private String address;
    private String user;
    private String password;
    private boolean isOpen = false;




//    public static void remoteReverseCopy(String remoteAddress, String remoteUser, String remotePassword, String srcPath,
//                                         String dstPath) throws InterruptedException {
//        try (RemoteRobocopy remoteMachine = new RemoteRobocopy(remoteAddress, remoteUser, remotePassword)) {
//            remoteMachine.reverserobocopy(srcPath, dstPath, remoteAddress);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            //e.printStackTrace();
//            Utilities.log(e);
//
//        }
//    }


    public RemoteRoboCopy(String address, String user, String password)  {
        this.address = address;
        this.user = user;
        this.password = password;

    }

    public void Build() throws IOException {
        String command = "net use \\\\" + address + " /user:" + user + " " + password;
        Utilities.runCMD(command);
        this.isOpen = true;
    }


    public void RoboCopy(String srcPath, String dstPath, String remoteAddress) throws IOException, InterruptedException {
        //String command = "RoboCopy "+"c:\\myjars\\"+" \\\\"+address+"\\"+dstPath;
        String command = "RoboCopy " + srcPath + " \\\\" + address + "\\" + dstPath;

        String name = "Robo Copy from " + srcPath + " to " + dstPath + " on " + remoteAddress;
        Utilities.runCMD(name, command);

    }

    private void reverserobocopy(String srcPath, String dstPath, String remoteAddress) throws IOException, InterruptedException {
        String command = "RoboCopy  \\\\" + address + "\\" + dstPath + " " + srcPath;
        String name = "Robo Copy from " + srcPath + " to " + dstPath + " on " + remoteAddress;
        Utilities.runCMD(name, command);

    }

    @Override
    protected void finalize() throws Throwable {
        // TODO Auto-generated method stub
        if (isOpen) {
            this.close();
        }
        super.finalize();
    }


    @Override
    public void close() throws IOException {
        String command = "net use /D \\\\" + address;
        Utilities.runCMD(command);
        this.isOpen = false;

    }

}
