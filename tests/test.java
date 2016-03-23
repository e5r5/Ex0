import static org.junit.Assert.*;

import java.sql.Array;
import java.util.Arrays;

import org.junit.Test;

public class test {

	@Test
	public void testMinPrice_tinyEWD() {
		String name_file = "tinyEWD.txt";

		assertEquals(0.26,Graph.MinPrice(name_file,0,2),0.00001);
		assertEquals(0.6,Graph.MinPrice(name_file,0,7),0.00001);
		assertEquals(0.73,Graph.MinPrice(name_file,0,5),0.00001);
		assertEquals(0.91,Graph.MinPrice(name_file,7,6),0.00001);
		assertEquals(0.62,Graph.MinPrice(name_file,2,5),0.00001);
		assertEquals(1.83,Graph.MinPrice(name_file,1,5),0.00001);
		assertEquals(1.49,Graph.MinPrice(name_file,7,0),0.00001);

	}
	@Test
	public void testPath_tinyEWD()
	{
		String name_file = "tinyEWD.txt";
		assertTrue(Graph.GetPath(name_file, 0, 7).equals("0->2->7"));
		assertTrue(Graph.GetPath(name_file, 7, 6).equals("7->3->6"));
		assertTrue(Graph.GetPath(name_file, 2, 5).equals("2->7->5"));
		assertTrue(Graph.GetPath(name_file, 1, 5).equals("1->3->6->2->7->5"));
		assertTrue(Graph.GetPath(name_file, 5, 1).equals("5->1"));
	}

	@Test
	public void testBlackList_tinyEWD()
	{
		String name_file = "tinyEWD.txt";
		String name_file_BL = "test1.txt";
		double[]black=Graph.GetMinPriceWithBL(name_file, name_file_BL);

		assertEquals(0.35,black[0],0.00001);
		assertEquals(0.37,black[1],0.00001);
		assertEquals(0.28,black[2],0.00001);
		assertEquals(0.6000,black[3],0.00001);
		assertEquals(1.49,black[4],0.00001);

	}

	@Test
	public void testMinPrice_tinyEWG() {
		String name_file = "tinyEWG.txt";

		assertEquals(0.95,Graph.MinPrice(name_file,0,6),0.00001);
		assertEquals(1.74,Graph.MinPrice(name_file,1,4),0.00001);
		assertEquals(0.34,Graph.MinPrice(name_file,2,7),0.00001);
		assertEquals(Double.POSITIVE_INFINITY,Graph.MinPrice(name_file,7,6),0.00001);
		assertEquals(1.8,Graph.MinPrice(name_file,3,5),0.00001);
		assertEquals(0.32,Graph.MinPrice(name_file,1,5),0.00001);

	}

	@Test
	public void testPath_tinyEWG()
	{
		String name_file = "tinyEWG.txt";

		assertTrue(Graph.GetPath(name_file, 0, 7).equals("0->7"));
		assertTrue(Graph.GetPath(name_file, 3, 5).equals("3->6->4->5"));
		assertTrue(Graph.GetPath(name_file, 2, 4).equals("2->3->6->4"));
		assertTrue(Graph.GetPath(name_file, 1, 0).equals("1->3->6->0"));
		assertTrue(Graph.GetPath(name_file, 5, 6).equals("inf = no path!"));
	}

	@Test
	public void testBlackList_mediumEWD()
	{
		String name_file = "mediumEWD.txt";
		String name_file_BL = "BLmediumEWD.txt";
		try{
			double[]black=Graph.GetMinPriceWithBL(name_file, name_file_BL);
			assertEquals(0.80074,black[0],0.00001);
			assertEquals(0.14601,black[1],0.00001);
			assertEquals(0.29664,black[2],0.00001);
		}
		catch(Exception e){
			System.err.println("the number is negative");
		}
	}
	//boaz test 2
	@Test
	public void testBlackList_tinyEWG()
	{
		String name_file = "mediumEWD.txt";
		String name_file_BL = "test2.txt";
		double[]black=Graph.GetMinPriceWithBL(name_file, name_file_BL);

		assertEquals(0.71478,black[0],0.00001);
		assertEquals(0.73099,black[1],0.00001);
		assertEquals(0.71478,black[2],0.00001);
		assertEquals(0.76025,black[3],0.00001);

	}
	//boaz test 3
	public void testBlackList_largeEWD()
	{
		String name_file = "largeEWD.txt";
		String name_file_BL = "test3.txt";
		double[]black=Graph.GetMinPriceWithBL(name_file, name_file_BL);

		assertEquals(0.0279433,black[0],0.00001);
		assertEquals(0.3524417,black[1],0.00001);
		assertEquals(0.279417,black[2],0.00001);
		assertEquals(0.616441,black[3],0.00001);
		assertEquals(0.290784,black[4],0.00001);
		assertEquals(0.6127715,black[5],0.00001);
		assertEquals(0.8389454,black[6],0.00001);
		assertEquals(0.3998456,black[7],0.00001);

	}


}
