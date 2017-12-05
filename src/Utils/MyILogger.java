package Utils;

import STASuite.STARunner;
import com.experitest.client.log.ILogger;
import com.experitest.client.log.Level;

/**
 * Created by eyal.neumann on 12/3/2017.
 */
public class MyILogger implements ILogger {

    public static final int DEBUG_PRINT_INSTANCE = 50;

    public MyILogger(STARunner runner) {
        this.runner = runner;
    }


    private STARunner runner;

    private int debugCounter = 0;


    @Override
    public void log(Level level, Object o, Throwable throwable) {
        // JTestVMRunner runner = (JTestVMRunner) Thread.runner();
        //JTestVMRunner runner = (JTestVMRunner) Thread.runner();
        if (level.equals(Level.FATAL) || level.equals(Level.ERROR) || level.equals(Level.INFO)) {
            if (o != null || o instanceof String) {
                Utilities.log(runner, "[" + level.toString() + "] " + (String) o);
            }
            if (throwable != null || throwable instanceof Exception) {
                Utilities.log(runner, (Exception) throwable);
            }
        } else if (level.equals(Level.DEBUG)) {
            if (debugCounter % DEBUG_PRINT_INSTANCE == 0) {
                if (o != null || o instanceof String) {
                    Utilities.log(runner, "[" + level.toString() + "] " + (String) o);
                }
                if (throwable != null || throwable instanceof Exception) {
                    Utilities.log(runner, (Exception) throwable);
                }
            }
            debugCounter++;
        }
    }
}


