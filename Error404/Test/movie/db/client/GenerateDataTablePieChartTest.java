/**
 * @author Patrick Muntwyler
 */
/*package movie.db.client;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

import movie.db.shared.DataResultAggregated;

public class GenerateDataTablePieChartTest extends GWTTestCase {

	private ArrayList<DataResultAggregated> dataPieChart = new ArrayList<DataResultAggregated>();
	private ArrayList<DataResultAggregated> emptyArrayList = new ArrayList<DataResultAggregated>();
	private String[][] testArray;
	//a country name which doesn't match the OSI3166 format
	private static final String COUNTRY1 = "United States of America";
	private static final int NUMBER_OF_MOVIES1 = 1500;
	//a country name which does match the OSI3166 format
	private static final String COUNTRY2 = "Germany";
	private static final int NUMBER_OF_MOVIES2 = 900;
	// a country name which doesn't match the OSI3166 format
	private static final String COUNTRY3 = "Kingdom of Great Britain";
	private static final int NUMBER_OF_MOVIES3 = 700;
	// a country name which doesn't match the OSI3166 format and an object with
	// the right
	// OSI3166 format is existing (Kingdom of Great Britain gets converted to
	// GB, Scotland will also
	// be converted to GB)
	private static final String COUNTRY4 = "Scotland";
	private static final int NUMBER_OF_MOVIES4 = 300;
	//a country name which should no be ignored by the createInputArrayList method, if invoked by the PieChartWebPage class
	private static final String COUNTRY5 = "Serbia and Montenegro";
	private static final int NUMBER_OF_MOVIES5 = 800;
	//a country name which should no be ignored by the createInputArrayList method, if invoked by the PieChartWebPage class
	private static final String COUNTRY6 = "Monaco";
	private static final int NUMBER_OF_MOVIES6 = 750;

	private static final String COUNTRY7 = "Switzerland";
	private static final int NUMBER_OF_MOVIES7 = 100;

	private static final String COUNTRY8 = "Canada";
	private static final int NUMBER_OF_MOVIES8 = 50;

	private static final String COUNTRY9 = "France";
	private static final int NUMBER_OF_MOVIES9 = 99;
	
	private static final String COUNTRY10 = "Brazil";
	private static final int NUMBER_OF_MOVIES10 = 25;

	// an object with no country name which should not be added to the 2D-Array
	private static final String COUNTRY11 = null;
	private static final int NUMBER_OF_MOVIES11 = 1000;
	// an country with numberOfMovies = 0, which should no be shown on the
	// GeoMap and because of that
	// it should also not be added to the 2D-Array
	private static final String COUNTRY12 = "Spain";
	private static final int NUMBER_OF_MOVIES12 = 0;

	@Override
	public String getModuleName() {
		return "movie.db.Error404";
	}

	@Test
	public void test() {
		DataResultAggregated object1 = new DataResultAggregated();
		object1.setCountryName(COUNTRY1);
		object1.setAggregatedNumberOfMovies(NUMBER_OF_MOVIES1);
		
		DataResultAggregated object2 = new DataResultAggregated();
		object2.setCountryName(COUNTRY2);
		object2.setAggregatedNumberOfMovies(NUMBER_OF_MOVIES2);
		
		DataResultAggregated object3 = new DataResultAggregated();
		object3.setCountryName(COUNTRY3);
		object3.setAggregatedNumberOfMovies(NUMBER_OF_MOVIES3);
		
		DataResultAggregated object4 = new DataResultAggregated();
		object4.setCountryName(COUNTRY4);
		object4.setAggregatedNumberOfMovies(NUMBER_OF_MOVIES4);
		
		DataResultAggregated object5 = new DataResultAggregated();
		object5.setCountryName(COUNTRY5);
		object5.setAggregatedNumberOfMovies(NUMBER_OF_MOVIES5);
		
		DataResultAggregated object6 = new DataResultAggregated();
		object6.setCountryName(COUNTRY6);
		object6.setAggregatedNumberOfMovies(NUMBER_OF_MOVIES6);
		
		DataResultAggregated object7 = new DataResultAggregated();
		object7.setCountryName(COUNTRY7);
		object7.setAggregatedNumberOfMovies(NUMBER_OF_MOVIES7);
		
		DataResultAggregated object8 = new DataResultAggregated();
		object8.setCountryName(COUNTRY8);
		object8.setAggregatedNumberOfMovies(NUMBER_OF_MOVIES8);
		
		DataResultAggregated object9 = new DataResultAggregated();
		object9.setCountryName(COUNTRY9);
		object9.setAggregatedNumberOfMovies(NUMBER_OF_MOVIES9);
		
		DataResultAggregated object10 = new DataResultAggregated();
		object10.setCountryName(COUNTRY10);
		object10.setAggregatedNumberOfMovies(NUMBER_OF_MOVIES10);
		
		DataResultAggregated object11 = new DataResultAggregated();
		object11.setCountryName(COUNTRY11);
		object11.setAggregatedNumberOfMovies(NUMBER_OF_MOVIES11);
		
		DataResultAggregated object12 = new DataResultAggregated();
		object12.setCountryName(COUNTRY12);
		object12.setAggregatedNumberOfMovies(NUMBER_OF_MOVIES12);
		
		dataPieChart.add(object1);
		dataPieChart.add(object2);
		dataPieChart.add(object3);
		dataPieChart.add(object4);
		dataPieChart.add(object5);
		dataPieChart.add(object6);
		dataPieChart.add(object7);
		dataPieChart.add(object8);
		dataPieChart.add(object9);
		dataPieChart.add(object10);
		dataPieChart.add(object11);
		dataPieChart.add(object12);
		testArray = PieChartWebPage.generateDataTableTest(emptyArrayList);
		//When the DataTable is filled with an empty ArrayList, this case is covert.
		//But we can't handle this case with a 2D-Array, because the 2D-Array was instantiated with length = 8,
		//so every cell is empty, when not filled
		assertEquals(testArray[0][0], null);
		assertEquals(testArray[0][1], null);
		
		testArray = PieChartWebPage.generateDataTableTest(dataPieChart);
		
		assertEquals(testArray[0][0], "United States of America");
		assertEquals(testArray[0][1], "1500");
		assertEquals(testArray[1][0], "the United Kingdom of Great Britain and Northern Ireland");
		assertEquals(testArray[1][1], "1000");
		assertEquals(testArray[2][0], "Germany");
		assertEquals(testArray[2][1], "900");
		assertEquals(testArray[3][0], "Serbia and Montenegro");
		assertEquals(testArray[3][1], "800");
		assertEquals(testArray[4][0], "Monaco");
		assertEquals(testArray[4][1], "750");
		assertEquals(testArray[5][0], "Switzerland");
		assertEquals(testArray[5][1], "100");
		assertEquals(testArray[6][0], "France");
		assertEquals(testArray[6][1], "99");
		assertEquals(testArray[7][0], "other countries");
		assertEquals(testArray[7][1], "75");
		assertEquals(testArray.length, 8);

		
	}

}*/
