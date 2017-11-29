package play;

import Utils.Utilities;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * Created by navot.dako on 11/29/2017.
 */
public abstract class BaseTempTest {


    @Before
    public void SetUp() throws Exception {}

    @Test
    abstract public void test();

    @After
    public void finish(){};

    public class WatchmanTest {
        private String watchedLog;

        @Rule
        public TestWatcher watchman= new TestWatcher() {
            @Override
            protected void failed(Throwable e, Description description) {
                watchedLog+= description + "\n";
                System.out.println(watchedLog);

            }

            @Override
            protected void succeeded(Description description) {
                watchedLog+= description + " " + "success!\n";
                System.out.println(watchedLog);

            }
        };

    }
}
