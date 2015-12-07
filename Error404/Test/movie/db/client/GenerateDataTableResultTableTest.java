/**
 * @author Patrick Muntwyler
 */
/* package movie.db.client;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

import movie.db.shared.DataResultShared;

public class GenerateDataTableResultTableTest extends GWTTestCase{
	
	private Map<Integer, DataResultShared> dataResultMapTest = new HashMap<Integer, DataResultShared>();

	private String[][] testArray;
	
	private String movieName1 = "Brun Bitter";
	private int year1 = 1988;
	private int duration1 = 63;
	private String country1 = "Norway";
	private String language1 = "Norwegian";
	private String genre1_1 = "Crime";
	private String genre1_2 = "Drama";
	private DataResultShared testResult1 = new DataResultShared();
	ArrayList<String> countries1 = new ArrayList<String>();
	ArrayList<String> languages1 = new ArrayList<String>();
	ArrayList<String> genres1 = new ArrayList<String>();
	
	

	private String movieName2 = "LMFAO";
	private int duration2 = 107;
	private String country2 = "Canada";
	private String language2_1 = "English";
	private String language2_2 = "German";
	private String language2_3 = "French";
	private String genre2 = "action";
	private DataResultShared testResult2 = new DataResultShared();
	ArrayList<String> countries2 = new ArrayList<String>();
	ArrayList<String> languages2 = new ArrayList<String>();
	ArrayList<String> genres2 = new ArrayList<String>();
	
	private String movieName3 = "Django";
	private String genre3 = "action";
	ArrayList<String> genres3 = new ArrayList<String>();
	private DataResultShared testResult3 = new DataResultShared();
	

	@Override
	public String getModuleName() {
		return "movie.db.Error404";
	}
	
	@Test
	public void test() {
		testResult1.setMovieName(movieName1);
		testResult1.setYear(year1);
		testResult1.setDuration(duration1);
		countries1.add(country1);
		testResult1.setCountries(countries1);
		languages1.add(language1);
		testResult1.setLanguages(languages1);
		genres1.add(genre1_1);
		genres1.add(genre1_2);
		testResult1.setGenres(genres1);
		
		testResult2.setMovieName(movieName2);
		testResult2.setDuration(duration2);
		countries2.add(country2);
		testResult2.setCountries(countries2);
		languages2.add(language2_1);
		languages2.add(language2_2);
		languages2.add(language2_3);
		testResult2.setLanguages(languages2);
		genres2.add(genre2);
		testResult2.setGenres(genres2);
		
		testResult3.setMovieName(movieName3);
		genres3.add(genre3);
		testResult3.setGenres(genres3);
		
		dataResultMapTest.put(0, testResult1);
		dataResultMapTest.put(1, testResult2);
		dataResultMapTest.put(2, testResult3);
		
		testArray = ResultTable.generateDataTableTest(dataResultMapTest);
		assertEquals(testArray[0][0],movieName1);
		assertEquals(testArray[0][1], year1 + "");
		assertEquals(testArray[0][2], duration1 + "");
		assertEquals(testArray[0][3], " " + genre1_1 + ", " + genre1_2 );
		assertEquals(testArray[0][4], " " + language1 );
		assertEquals(testArray[0][5], " " + country1);
		
		assertEquals(testArray[1][0],movieName2);
		assertEquals(testArray[1][1], "0");
		assertEquals(testArray[1][2], duration2 + "");
		assertEquals(testArray[1][3], " " + genre2);
		assertEquals(testArray[1][4], " " + language2_1 + ", " + language2_2 + ", " + language2_3);
		assertEquals(testArray[1][5], " " + country2);
		
		assertEquals(testArray[2][0], movieName3);
		assertEquals(testArray[2][1], "0");
		assertEquals(testArray[2][2], "0");
		assertEquals(testArray[2][3], " " + genre3);
		assertEquals(testArray[2][4], " ");
		assertEquals(testArray[2][5], " ");
		
		
		
	}

}*/
