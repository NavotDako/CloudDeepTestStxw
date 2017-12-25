package Utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ProcessReader implements Runnable {
    private final Process process;
    private String name;
    public ProcessReader(Process process, String name){
        this.process =process;
        this.name= name;
    }
    @Override
    public void run() {
        Thread.currentThread().setName(name);
        try {
            process.getOutputStream().close();
        } catch (IOException e) {
            //Utilities.log(e);
            Utilities.log(e);
        }

        String line;
        try(BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));) {
            while (process.isAlive()) {
                String output = "[" + name + "] " + stdout.readLine();
                Utilities.log(output);
                //System.out.println(output);
            }
        }
        catch (Exception e){
            //Utilities.log(e);
            Utilities.log(e);

        }

    }
}