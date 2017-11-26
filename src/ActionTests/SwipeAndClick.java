package ActionTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SwipeAndClick extends BaseTest {

	@Before
	public void SetUp() 
	{
		super.SetUp();
	}
	@Test
	public void test() {
		try
		{
			System.out.println("swipeandclickkk");
			log("Enter to swipeAndClick Test");
		}
		catch(Exception e)
		{
			writeFailedLineInLog(e.toString());
		}
	}
	@After
	public void finsh() 
	{
		super.finish();
	}

}
