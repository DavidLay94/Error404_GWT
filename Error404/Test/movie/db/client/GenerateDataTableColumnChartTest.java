/**
 * @author Patrick Muntwyler
 */
package movie.db.client;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

public class GenerateDataTableColumnChartTest extends GWTTestCase {
	
	Map<Integer, Integer> testMap = new HashMap<Integer, Integer>();
	Map<Integer, Integer> emptytestMap = new HashMap<Integer, Integer>();
	int[][]testArray = new int [YEAR9-YEAR1+1][2];
	int[][]testArray2 = new int [YEAR9-YEAR1+1][2];
	
	private static final int YEAR1 = 1980;
	private static final int NUMBER_OF_MOVIES1 = 10;
	
	private static final int YEAR2 = 1981;
	private static final int NUMBER_OF_MOVIES2 = 22;
	
	private static final int YEAR3 = 1982;
	private static final int NUMBER_OF_MOVIES3 = 11;
	
	private static final int YEAR4 = 1983;
	private static final int NUMBER_OF_MOVIES4 = 5;
	
	//if the country did not produce any movie in a certain year, then the
	//query does not add an object for this year to the map, so the generateDataTable method
	//has to test on such a case a add a row to the dataTable with the value zero.
	private static final int YEAR5 = 1984;
	private static final int NUMBER_OF_MOVIES5 = 0;
	
	private static final int YEAR6 = 1985;
	private static final int NUMBER_OF_MOVIES6 = 3;
	
	private static final int YEAR7 = 1986;
	private static final int NUMBER_OF_MOVIES7 = 44;
	
	private static final int YEAR8 = 1987;
	private static final int NUMBER_OF_MOVIES8 = 50;
	
	private static final int YEAR9 = 1988;
	private static final int NUMBER_OF_MOVIES9 = 4;

	@Override
	public String getModuleName() {
		return "movie.db.Error404";
	}
	
	@Test
	public void test() {
		testMap.put(YEAR1, NUMBER_OF_MOVIES1);
		testMap.put(YEAR2, NUMBER_OF_MOVIES2);
		testMap.put(YEAR3, NUMBER_OF_MOVIES3);
		testMap.put(YEAR4, NUMBER_OF_MOVIES4);
		testMap.put(YEAR6, NUMBER_OF_MOVIES6);
		testMap.put(YEAR7, NUMBER_OF_MOVIES7);
		testMap.put(YEAR8, NUMBER_OF_MOVIES8);
		testMap.put(YEAR9, NUMBER_OF_MOVIES9);
		
		testArray = ColumnChartWebPage.generateDataTableTest(testMap, YEAR1, YEAR9);
		
		assertEquals(testArray[0][0], YEAR1);
		assertEquals(testArray[0][1], NUMBER_OF_MOVIES1);
		assertEquals(testArray[1][0], YEAR2);
		assertEquals(testArray[1][1], NUMBER_OF_MOVIES2);
		assertEquals(testArray[2][0], YEAR3);
		assertEquals(testArray[2][1], NUMBER_OF_MOVIES3);
		assertEquals(testArray[3][0], YEAR4);
		assertEquals(testArray[3][1], NUMBER_OF_MOVIES4);
		assertEquals(testArray[4][0], YEAR5);
		assertEquals(testArray[4][1], NUMBER_OF_MOVIES5);
		assertEquals(testArray[5][0], YEAR6);
		assertEquals(testArray[5][1], NUMBER_OF_MOVIES6);
		assertEquals(testArray[6][0], YEAR7);
		assertEquals(testArray[6][1], NUMBER_OF_MOVIES7);
		assertEquals(testArray[7][0], YEAR8);
		assertEquals(testArray[7][1], NUMBER_OF_MOVIES8);
		assertEquals(testArray[8][0], YEAR9);
		assertEquals(testArray[8][1], NUMBER_OF_MOVIES9);
		assertEquals(testArray.length, 9);
		
		testArray2 = ColumnChartWebPage.generateDataTableTest(emptytestMap, YEAR1, YEAR9);
		
		assertEquals(testArray2[0][0], YEAR1);
		assertEquals(testArray2[0][1], 0);
		assertEquals(testArray2[1][0], YEAR2);
		assertEquals(testArray2[1][1], 0);
		assertEquals(testArray2[2][0], YEAR3);
		assertEquals(testArray2[2][1], 0);
		assertEquals(testArray2[3][0], YEAR4);
		assertEquals(testArray2[3][1], 0);
		assertEquals(testArray2[4][0], YEAR5);
		assertEquals(testArray2[4][1], 0);
		assertEquals(testArray2[5][0], YEAR6);
		assertEquals(testArray2[5][1], 0);
		assertEquals(testArray2[6][0], YEAR7);
		assertEquals(testArray2[6][1], 0);
		assertEquals(testArray2[7][0], YEAR8);
		assertEquals(testArray2[7][1], 0);
		assertEquals(testArray2[8][0], YEAR9);
		assertEquals(testArray2[8][1], 0);
		
	}

	

}
