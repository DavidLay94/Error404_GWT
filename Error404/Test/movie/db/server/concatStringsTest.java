//package movie.db.server;
//
//import static org.junit.Assert.*;
//
//import java.util.ArrayList;
//
//import movie.db.server.Query;
//
//import org.junit.Test;
//
///* regression tested 06.12.2015*/
//public class concatStringsTest {
//
//	/**
//	 * This test tests these cases:
//	 * 1. concatenation happens as expected with delivered arraylist + separatorr
//	 * 2. apostroph is correctly escaped
//	 * 3. handles separator = null
//	 * 4. handles arraylist = null
//	 * 5. handles an empty arraylist
//	 * 
//	 * @author Christoph Weber
//	 * 
//	 */
//	@Test	
//	public void test() {
//		Query q = new Query();
//		
//		/* Case 1
//		 * all the elements in the array list are concatenated together, each word separated by 
//		 * the delivered separator. There is no separator at the end or at the beginning.
//		 */
//		ArrayList<String> aList1 = new ArrayList<String>();
//		aList1.add("word1");
//		aList1.add("word2");
//		aList1.add("word3");
//		String separator1 = ";";
//		assertEquals(q.concatStrings(separator1,aList1),"word1;word2;word3");
//		
//		/* Case 2
//		 * If a String in the ArrayList contains an apostroph, it is escaped by double-apostroph.
//		 */
//		ArrayList<String> aList2 = new ArrayList<String>();
//		aList2.add("Let's go to the mall!");
//		String separator2 = ";";
//		assertEquals(q.concatStrings(separator2,aList2),"Let''s go to the mall!");
//		
//		/* Case 3
//		 * Handles the case if the separator-string is null
//		 */
//		ArrayList<String> aList3 = new ArrayList<String>();
//		aList3.add("word1");
//		aList3.add("word2");
//		aList3.add("word3");
//		String separator3 = null;
//		assertEquals(q.concatStrings(separator3,aList3),null);
//		
//		/* Case 4
//		 * Handles the case if the ArrayList is null
//		 */
//		ArrayList<String> aList4 = null;
//		String separator4 = ";";
//		assertEquals(q.concatStrings(separator4,aList4),null);
//		
//		/* Case 5
//		 * Handles the case if the ArrayList contains no elements
//		 */
//		ArrayList<String> aList5 = new ArrayList<String>();
//		String separator5 = ";";
//		assertEquals(q.concatStrings(separator5,aList5),"");
//	}
//}
