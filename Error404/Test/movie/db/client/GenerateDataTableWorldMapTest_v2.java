/**
 * @author Patrick Muntwyler
 */
package movie.db.client;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

import movie.db.shared.DataResultAggregated;

public class GenerateDataTableWorldMapTest_v2 extends GWTTestCase {
	
	private ArrayList<DataResultAggregated> dataWorldMap = new ArrayList<DataResultAggregated>();
	private ArrayList<DataResultAggregated> emptyArrayList = new ArrayList<DataResultAggregated>();
	private String[][] testArray;
	//a country name which doesn't match the OSI3166 format
	private static final String COUNTRY1 = "United States of America";
	private static final int  NUMBER_OF_MOVIES1 = 200;
	//a country name which does match the OSI3166 format
	private static final String COUNTRY2 = "Germany";
	private static final int NUMBER_OF_MOVIES2 = 500;
	//a country name which doesn't match the OSI3166 format
	private static final String COUNTRY3 = "Kingdom of Great Britain";
	private static final int NUMBER_OF_MOVIES3 = 700;
	//a country name which doesn't match the OSI3166 format and an object with the right
	//OSI3166 format is existing (Kingdom of Great Britain gets converted to GB, Scotland will also 
	//be converted to GB)
	private static final String COUNTRY4 = "Scotland";
	private static final int NUMBER_OF_MOVIES4 = 250;
	//an object with no country name which should not be added to the 2D-Array
	private static final String COUNTRY5 = null;
	private static final int NUMBER_OF_MOVIES5 = 1000;
	//an country with numberOfMovies = 0, which should no be shown on the GeoMap and because of that
	//it should also not be added to the 2D-Array
	private static final String COUNTRY6 = "Switzerland";
	private static final int NUMBER_OF_MOVIES6 = 0;
	
	private static final DataResultAggregated OBJECT1 = new DataResultAggregated();
	private static final DataResultAggregated OBJECT2 = new DataResultAggregated();
	private static final DataResultAggregated OBJECT3 = new DataResultAggregated();
	private static final DataResultAggregated OBJECT4 = new DataResultAggregated();
	private static final DataResultAggregated OBJECT5 = new DataResultAggregated();
	private static final DataResultAggregated OBJECT6 = new DataResultAggregated();
	
	
	
	/*
	 * only three because Kingdom of Great Britain and Scotland should be united in one object, 
	 * the object with no country name and the object with numberOfMovies = 0 should not be added
	 * to the 2D-Array
	 */
	private int numberOfRows = 3;

	
	@Override
	public String getModuleName() {
		
		return "movie.db.Error404";
	}
	@Test
	public void test() {
		
		OBJECT1.setCountryName(COUNTRY1);
		OBJECT1.setAggregatedNumberOfMovies(NUMBER_OF_MOVIES1);
		OBJECT2.setCountryName(COUNTRY2);
		OBJECT2.setAggregatedNumberOfMovies(NUMBER_OF_MOVIES2);
		OBJECT3.setCountryName(COUNTRY3);
		OBJECT3.setAggregatedNumberOfMovies(NUMBER_OF_MOVIES3);
		OBJECT4.setCountryName(COUNTRY4);
		OBJECT4.setAggregatedNumberOfMovies(NUMBER_OF_MOVIES4);
		OBJECT5.setCountryName(COUNTRY5);
		OBJECT5.setAggregatedNumberOfMovies(NUMBER_OF_MOVIES5);
		OBJECT6.setCountryName(COUNTRY6);
		OBJECT6.setAggregatedNumberOfMovies(NUMBER_OF_MOVIES6);
		
		
		dataWorldMap.add(OBJECT1);
    	dataWorldMap.add(OBJECT2);
    	dataWorldMap.add(OBJECT3);
    	dataWorldMap.add(OBJECT4);
    	dataWorldMap.add(OBJECT5);
    	dataWorldMap.add(OBJECT6);
    	testArray = WorldMap.generateDataTableTest(emptyArrayList);
    	assertEquals(testArray.length, 0);
    	
    	testArray = WorldMap.generateDataTableTest(dataWorldMap);
    	assertEquals(testArray.length,numberOfRows);
    	assertEquals(testArray[0][0],"United States");
    	assertEquals(testArray[0][1], NUMBER_OF_MOVIES1 + "");
    	assertEquals(testArray[0][2],"United States of America");
    	
    	assertEquals(testArray[1][0],COUNTRY2);
    	assertEquals(testArray[1][1], NUMBER_OF_MOVIES2 + "");
    	assertEquals(testArray[1][2],COUNTRY2);
    	
    	assertEquals(testArray[2][0],"GB");
    	assertEquals(testArray[2][1], (NUMBER_OF_MOVIES3 + NUMBER_OF_MOVIES4) +  "");
    	assertEquals(testArray[2][2],"the United Kingdom of Great Britain and Northern Ireland");
    	
    	assertEquals(testArray.length, numberOfRows);
    	
    	
	}

	
}
