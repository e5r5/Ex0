import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.Assert;

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
		String name_file_BL = "BLtinyEWD.txt";
		double[]black=Graph.GetMinPriceWithBL(name_file, name_file_BL, 1);
		
		assertEquals(2.09,black[0],0.00001);
		assertEquals(2.18,black[1],0.00001);
		assertEquals(0.99,black[2],0.00001);
		assertEquals(0.75,black[3],0.00001);
		assertEquals(1.49,black[4],0.00001);
		
	}
	
	@Test
	public void testMinPrice_tinyEWG() {
		String name_file = "tinyEWG.txt";
		
		assertEquals(0.95,Graph.MinPrice(name_file,0,6),0.00001);
		assertEquals(1.74,Graph.MinPrice(name_file,1,4),0.00001);
		assertEquals(0.34,Graph.MinPrice(name_file,2,7),0.00001);
		assertEquals(Integer.MAX_VALUE,Graph.MinPrice(name_file,7,6),0.00001);
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
		assertTrue(Graph.GetPath(name_file, 5, 6).equals(null));
	}
	
	@Test
	public void testBlackList_tinyEWG()
	{
		String name_file = "tinyEWG.txt";
		String name_file_BL = "BLtinyEWG.txt";
		double[]black=Graph.GetMinPriceWithBL(name_file, name_file_BL, 1);
		
		assertEquals(1.26,black[0],0.00001);
		
	}
	
	

}
