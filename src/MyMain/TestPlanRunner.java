package MyMain;

import Utils.Utilities;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

public class TestPlanRunner extends BaseRunner {

    public TestPlanRunner(int i) {

        super(i);
    }

    @Override
    public void run() {
        pw = Utilities.CreateReportFile(currentThread(),iteration);
        Utilities.log("Starting Thread Num - " + iteration +" - Thread Name is - "+Thread.currentThread().getName());
        while (true) {
            this.testClass = getTestPlanAction(rand.nextInt(enums.TestPlanActions.length));
            Utilities.log(this,testClass.getName());

            testName = testClass.getSimpleName();

            Long currTime = System.currentTimeMillis();
            TestName = testName + " " + currTime;

            Result r = JUnitCore.runClasses(testClass);

            try {
                int sleepTime = rand.nextInt(20);
                Utilities.log(this, Thread.currentThread().getName() + " is Going to sleep for - " + sleepTime + " minutes");
                for (int i = 0; i < sleepTime; i++) {
                    Thread.sleep(60000);
                    Utilities.log(currentThread().getName() + " isSleeping");
                }
            } catch (Exception e) {
                Utilities.log(e);
            }
        }
    }

}


