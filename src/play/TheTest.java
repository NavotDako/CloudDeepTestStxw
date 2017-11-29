package play;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by navot.dako on 11/29/2017.
 */
public class TheTest extends BaseTempTest {

    @Test
    public void test() {
        int a = 0;
        int b = 2;
        int c;

        try {
            c = b / a;
        } catch (Exception e) {
           Assert.fail("ddddddddddddddddddd");
        }
//        } catch (Exception e) {
//
//            Assert.fail(e.getMessage());
//        }
    }
}
