package ActionTests;

import Utils.Utilities;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SwipeAndClick extends BaseTest {

    @Before
    public void SetUp() {
        super.SetUp();
    }

    @Test
    public void test() {
        try {
            Utilities.log(currentThread,"swipeandclickkk");
            Utilities.log(currentThread, "Enter to swipeAndClick testClass");
        } catch (Exception e) {
            writeFailedLineInLog(e.toString());
        }
    }

    @After
    public void finsh() {
        super.finish();
    }

}
