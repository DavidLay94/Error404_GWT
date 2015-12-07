/**
 * @author Patrick Muntwyler
 * does test the method getIndex of the class CountryNameHandling
 */
package movie.db.client;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

public class GetIndexTest extends GWTTestCase {
	
private ArrayList<InputObjectWorldMap> testList1 = new ArrayList<InputObjectWorldMap>();
	
	//testList2 is empty
	private ArrayList<InputObjectWorldMap> testList2 = new ArrayList<InputObjectWorldMap>();
	
	
	private static final String COUNTRY_OSI1 = "Netherlands";
	private static final int NUMBER_OF_MOVIES1 = 200;
	private static final String COUNTRY_NAME_SHOWED1 = "Netherlands";
	
	private static final String COUNTRY_OSI2 = "BA";
	private static final int NUMBER_OF_MOVIES2 = 400;
	private static final String COUNTRY_NAME_SHOWED2 = "Bosnia and Herzegovina";
	
	private static final String COUNTRY_OSI3 = "Myanmar";
	private static final int NUMBER_OF_MOVIES3 = 300;
	private static final String COUNTRY_NAME_SHOWED3 = "Myanmar (also Burma)";
	
	private static final String COUNTRY_OSI4 = "United States";
	private static final int NUMBER_OF_MOVIES4 = 500;
	private static final String COUNTRY_NAME_SHOWED4 = "United States of America";
	
	private static final InputObjectWorldMap testObject1 = 
			new InputObjectWorldMap(COUNTRY_OSI1, NUMBER_OF_MOVIES1, COUNTRY_NAME_SHOWED1);
	
	private static final InputObjectWorldMap testObject2 = 
			new InputObjectWorldMap(COUNTRY_OSI2, NUMBER_OF_MOVIES2, COUNTRY_NAME_SHOWED2);
	
	private static final InputObjectWorldMap testObject3 = 
			new InputObjectWorldMap(COUNTRY_OSI3, NUMBER_OF_MOVIES3, COUNTRY_NAME_SHOWED3);
	
	private static final InputObjectWorldMap testObject4 = 
			new InputObjectWorldMap(COUNTRY_OSI4, NUMBER_OF_MOVIES4, COUNTRY_NAME_SHOWED4);
	
	private static final String NOT_CONTAINED_COUNTRY1 = "Switzerland";
	private static final String NOT_CONTAINED_COUNTRY2 = null;
	
	@Override
	public String getModuleName() {
		
		return "movie.db.Error404";
	}

	@Test
	public void test() {
		
		testList1.add(testObject1);
		testList1.add(testObject2);
		testList1.add(testObject3);
		testList1.add(testObject4);
		
		assertEquals(CountryNameHandling.getIndex(testList1, COUNTRY_OSI1), 0);
		assertEquals(CountryNameHandling.getIndex(testList1, COUNTRY_OSI2), 1);
		assertEquals(CountryNameHandling.getIndex(testList1, COUNTRY_OSI3), 2);
		assertEquals(CountryNameHandling.getIndex(testList1, COUNTRY_OSI4), 3);
		assertEquals(CountryNameHandling.getIndex(testList1, NOT_CONTAINED_COUNTRY1), -1);
		assertEquals(CountryNameHandling.getIndex(testList1, NOT_CONTAINED_COUNTRY2), -1);
		
		assertEquals(CountryNameHandling.getIndex(testList2, COUNTRY_OSI1), -1);
		assertEquals(CountryNameHandling.getIndex(testList2, NOT_CONTAINED_COUNTRY1), -1);
		assertEquals(CountryNameHandling.getIndex(testList2, NOT_CONTAINED_COUNTRY2), -1);
		
		
		
	}

	

}
