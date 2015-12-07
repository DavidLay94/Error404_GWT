/**
 * @author: Patrick Muntwyler
 * tests the method createInputArrayList from class CountryNameHandling
 */

package movie.db.client;

import java.util.ArrayList;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

import movie.db.shared.DataResultAggregated;

public class CreateInputArrayListTest extends GWTTestCase {
	
	private ArrayList<DataResultAggregated> inputData = new ArrayList<DataResultAggregated>();
	private ArrayList<InputObjectWorldMap> outputList = new ArrayList<InputObjectWorldMap>();
	
	private static final String COUNTRY_NAME1 = "Aruba";
	private static final int AGGREGATED_NUMBER_OF_MOVIES1 = 100;
	
	private static final String COUNTRY_NAME2 = "Germany";
	private static final int AGGREGATED_NUMBER_OF_MOVIES2 = 400;
	
	private static final String COUNTRY_NAME3 = "Netherlands";
	private static final int AGGREGATED_NUMBER_OF_MOVIES3 = 200;
	
	private static final String COUNTRY_NAME4 = "Switzerland";
	private static final int AGGREGATED_NUMBER_OF_MOVIES4 = 300;
	
	private static final String COUNTRY_NAME5 = "Congo";
	private static final int AGGREGATED_NUMBER_OF_MOVIES5 = 100;
	
	//should be ignored because has no country name
	private static final String COUNTRY_NAME6 = null;
	private static final int AGGREGATED_NUMBER_OF_MOVIES6 = 100;
	
	//should be ignored because numberOfMovies = 0
	private static final String COUNTRY_NAME7 = "someCountry";
	private static final int AGGREGATED_NUMBER_OF_MOVIES7 = 0;
	
	//should be ignored if method parameter "boolean forWorldMap" is true
	private static final String COUNTRY_NAME8 = "Serbia and Montenegro";
	private static final int AGGREGATED_NUMBER_OF_MOVIES8 = 50;
	
	//should be ignored if method parameter "boolean forWorldMap" is true
	private static final String COUNTRY_NAME9 = "Yugoslavia";
	private static final int AGGREGATED_NUMBER_OF_MOVIES9 = 50;
	
	private DataResultAggregated inputObject1 = new DataResultAggregated();
	
	private static final DataResultAggregated inputObject2 = 
			new DataResultAggregated();
	
	private static final DataResultAggregated inputObject3 = 
			new DataResultAggregated();
	
	private static final DataResultAggregated inputObject4 = 
			new DataResultAggregated();
	
	private static final DataResultAggregated inputObject5 = 
			new DataResultAggregated();
	
	private static final DataResultAggregated inputObject6 = 
			new DataResultAggregated();
	
	private static final DataResultAggregated inputObject7 = 
			new DataResultAggregated();
	
	private static final DataResultAggregated inputObject8 = 
			new DataResultAggregated();
	
	private static final DataResultAggregated inputObject9 = 
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
		
		inputObject3.setCountryName(COUNTRY_NAME3);
		inputObject3.setAggregatedNumberOfMovies(AGGREGATED_NUMBER_OF_MOVIES3);
		
		inputObject4.setCountryName(COUNTRY_NAME4);
		inputObject4.setAggregatedNumberOfMovies(AGGREGATED_NUMBER_OF_MOVIES4);
		
		inputObject5.setCountryName(COUNTRY_NAME5);
		inputObject5.setAggregatedNumberOfMovies(AGGREGATED_NUMBER_OF_MOVIES5);
		
		inputObject6.setCountryName(COUNTRY_NAME6);
		inputObject6.setAggregatedNumberOfMovies(AGGREGATED_NUMBER_OF_MOVIES6);
		
		inputObject7.setCountryName(COUNTRY_NAME7);
		inputObject7.setAggregatedNumberOfMovies(AGGREGATED_NUMBER_OF_MOVIES7);
		
		inputObject8.setCountryName(COUNTRY_NAME8);
		inputObject8.setAggregatedNumberOfMovies(AGGREGATED_NUMBER_OF_MOVIES8);
		
		inputObject9.setCountryName(COUNTRY_NAME9);
		inputObject9.setAggregatedNumberOfMovies(AGGREGATED_NUMBER_OF_MOVIES9);
		
		inputData.add(inputObject1);
		inputData.add(inputObject2);
		inputData.add(inputObject3);
		inputData.add(inputObject4);
		inputData.add(inputObject5);
		inputData.add(inputObject6);
		inputData.add(inputObject7);
		inputData.add(inputObject8);
		inputData.add(inputObject9);
		
		outputList = CountryNameHandling.createInputArrayList(inputData, true);
		
		assertEquals(outputList.get(0).getOSICountryName(),"Netherlands");
		assertEquals(outputList.get(0).getNumberOfMovies(),AGGREGATED_NUMBER_OF_MOVIES1 + AGGREGATED_NUMBER_OF_MOVIES3 );
		assertEquals(outputList.get(0).getShowedCountryName(),"Netherlands");
		
		assertEquals(outputList.get(1).getOSICountryName(),"Germany");
		assertEquals(outputList.get(1).getNumberOfMovies(),AGGREGATED_NUMBER_OF_MOVIES2 );
		assertEquals(outputList.get(1).getShowedCountryName(),"Germany");
		
		assertEquals(outputList.get(2).getOSICountryName(),"Switzerland");
		assertEquals(outputList.get(2).getNumberOfMovies(),AGGREGATED_NUMBER_OF_MOVIES4 );
		assertEquals(outputList.get(2).getShowedCountryName(),"Switzerland");
		
		assertEquals(outputList.get(3).getOSICountryName(),"CD");
		assertEquals(outputList.get(3).getNumberOfMovies(),AGGREGATED_NUMBER_OF_MOVIES5 );
		assertEquals(outputList.get(3).getShowedCountryName(),"the Democratic Republic of the Congo");
		
		assertEquals(outputList.size(),4);
		
		outputList = CountryNameHandling.createInputArrayList(inputData, false);
		
		assertEquals(outputList.get(0).getOSICountryName(),"Netherlands");
		assertEquals(outputList.get(0).getNumberOfMovies(),AGGREGATED_NUMBER_OF_MOVIES1 + AGGREGATED_NUMBER_OF_MOVIES3 );
		assertEquals(outputList.get(0).getShowedCountryName(),"Netherlands");
		
		assertEquals(outputList.get(1).getOSICountryName(),"Germany");
		assertEquals(outputList.get(1).getNumberOfMovies(),AGGREGATED_NUMBER_OF_MOVIES2 );
		assertEquals(outputList.get(1).getShowedCountryName(),"Germany");
		
		assertEquals(outputList.get(2).getOSICountryName(),"Switzerland");
		assertEquals(outputList.get(2).getNumberOfMovies(),AGGREGATED_NUMBER_OF_MOVIES4 );
		assertEquals(outputList.get(2).getShowedCountryName(),"Switzerland");
		
		assertEquals(outputList.get(3).getOSICountryName(),"CD");
		assertEquals(outputList.get(3).getNumberOfMovies(),AGGREGATED_NUMBER_OF_MOVIES5 );
		assertEquals(outputList.get(3).getShowedCountryName(),"the Democratic Republic of the Congo");
		
		assertEquals(outputList.get(4).getOSICountryName(),"Serbia and Montenegro");
		assertEquals(outputList.get(4).getNumberOfMovies(),AGGREGATED_NUMBER_OF_MOVIES8 );
		assertEquals(outputList.get(4).getShowedCountryName(),"Serbia and Montenegro");
		
		assertEquals(outputList.get(5).getOSICountryName(),"Yugoslavia");
		assertEquals(outputList.get(5).getNumberOfMovies(),AGGREGATED_NUMBER_OF_MOVIES9 );
		assertEquals(outputList.get(5).getShowedCountryName(),"Yugoslavia");
		
		assertEquals(outputList.size(),6);
	}

	

}
