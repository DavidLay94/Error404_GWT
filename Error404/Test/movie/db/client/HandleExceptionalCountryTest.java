/**
 * @author Patrick Muntwyler
 * tests the method handleExceptionalCountry of class CountryNameHandling
 */
/*package movie.db.client;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

import movie.db.shared.DataResultAggregated;

public class HandleExceptionalCountryTest extends GWTTestCase {
	
	private ArrayList<InputObjectWorldMap> outputList1 = new ArrayList<InputObjectWorldMap>();
	private ArrayList<InputObjectWorldMap> outputList2 = new ArrayList<InputObjectWorldMap>();
	
	private ArrayList<DataResultAggregated> inputData1 = new ArrayList<DataResultAggregated>();
	
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
	
	private static final String COUNTRY_NAME1 = "Aruba";
	private static final int AGGREGATED_NUMBER_OF_MOVIES1 = 100;
	
	private static final String COUNTRY_NAME2 = "Congo";
	private static final int AGGREGATED_NUMBER_OF_MOVIES2 = 100;
	
	private static final DataResultAggregated inputObject1 = 
			new DataResultAggregated();
	
	private static final DataResultAggregated inputObject2 = 
			new DataResultAggregated();
	
	
	

	@Override
	public String getModuleName() {                                        
	    return "movie.db.Error404";
	  }
	
	@Test
	public void test() {
		
		inputObject1.setCountryName(COUNTRY_NAME1);
		inputObject1.setAggregatedNumberOfMovies(AGGREGATED_NUMBER_OF_MOVIES1);
		inputObject2.setCountryName(COUNTRY_NAME2);
		inputObject2.setAggregatedNumberOfMovies(AGGREGATED_NUMBER_OF_MOVIES2);
		
		outputList1.add(testObject1);
		outputList1.add(testObject2);
		outputList1.add(testObject3);
		outputList1.add(testObject4);
		
		inputData1.add(inputObject1);
		inputData1.add(inputObject2);
		
		outputList1 = CountryNameHandling.handleExceptionalCountry(inputData1, outputList1, 0, "Netherlands", "Netherlands");
		outputList1 = CountryNameHandling.handleExceptionalCountry(inputData1, outputList1, 1, "CD", "the Democratic Republic of the Congo");
		
		//if object is existing, then the method should add only the aggregated number of movies
		assertEquals(outputList1.get(0).getOSICountryName(), COUNTRY_OSI1);
		assertEquals(outputList1.get(0).getNumberOfMovies(), NUMBER_OF_MOVIES1 + AGGREGATED_NUMBER_OF_MOVIES1);
		assertEquals(outputList1.get(0).getShowedCountryName(), COUNTRY_NAME_SHOWED1);
		
		//if object is not existing, such an object should be generated and added to the outputList
		assertEquals(outputList1.get(4).getOSICountryName(), "CD");
		assertEquals(outputList1.get(4).getNumberOfMovies(), AGGREGATED_NUMBER_OF_MOVIES2);
		assertEquals(outputList1.get(4).getShowedCountryName(), "the Democratic Republic of the Congo");
		
		outputList2 = CountryNameHandling.handleExceptionalCountry(inputData1, outputList2, 0, "Netherlands" , "Netherlands");
		outputList2 = CountryNameHandling.handleExceptionalCountry(inputData1, outputList2, 1, "CD" , "the Democratic Republic of the Congo");
		
		//empty outputList should add the tested object of inputData
		assertEquals(outputList2.get(0).getOSICountryName(), "Netherlands");
		assertEquals(outputList2.get(0).getNumberOfMovies(), AGGREGATED_NUMBER_OF_MOVIES1);
		assertEquals(outputList2.get(0).getShowedCountryName(), "Netherlands");
		
		assertEquals(outputList2.get(1).getOSICountryName(), "CD");
		assertEquals(outputList2.get(1).getNumberOfMovies(), AGGREGATED_NUMBER_OF_MOVIES2);
		assertEquals(outputList2.get(1).getShowedCountryName(), "the Democratic Republic of the Congo");
		
		
		
	}

	

}*/
