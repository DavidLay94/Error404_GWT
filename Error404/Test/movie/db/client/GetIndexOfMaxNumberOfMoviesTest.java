/**
 * @author Patrick Muntwyler
 */

/*package movie.db.client;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

public class GetIndexOfMaxNumberOfMoviesTest extends GWTTestCase {
	
	private ArrayList<InputObjectWorldMap> list1 = new ArrayList<InputObjectWorldMap>();
	private ArrayList<InputObjectWorldMap> list2 = new ArrayList<InputObjectWorldMap>();
	private ArrayList<InputObjectWorldMap> list3 = new ArrayList<InputObjectWorldMap>();
	
	private static final String OSI_COUNTRY_NAME1 = "United States";
	private static final int  NUMBER_OF_MOVIES1 = 200;
	private static final String SHOWED_COUNTRY_NAME1 = "United States of America";
	
	private static final String OSI_COUNTRY_NAME2 = "Switzerland";
	private static final int  NUMBER_OF_MOVIES2 = 500;
	private static final String SHOWED_COUNTRY_NAME2 = "Switzerland";
	
	private static final String OSI_COUNTRY_NAME3 = "Aruba";
	private static final int  NUMBER_OF_MOVIES3 = 500;
	private static final String SHOWED_COUNTRY_NAME3 = "Netherlands";
	
	private static final String OSI_COUNTRY_NAME4 = "Iraq";
	private static final int  NUMBER_OF_MOVIES4 = 500;
	private static final String SHOWED_COUNTRY_NAME4 = "Iraq";
	
	private static final String OSI_COUNTRY_NAME5 = "Germany";
	private static final int  NUMBER_OF_MOVIES5 = 0;
	private static final String SHOWED_COUNTRY_NAME5 = "Germany";
	

	@Override
	public String getModuleName() {
		return "movie.db.Error404";
	}
	
	@Test
	public void test() {
		
		InputObjectWorldMap object1 = new InputObjectWorldMap(OSI_COUNTRY_NAME1, NUMBER_OF_MOVIES1, SHOWED_COUNTRY_NAME1);
		InputObjectWorldMap object2 = new InputObjectWorldMap(OSI_COUNTRY_NAME2, NUMBER_OF_MOVIES2, SHOWED_COUNTRY_NAME2);
		InputObjectWorldMap object3 = new InputObjectWorldMap(OSI_COUNTRY_NAME3, NUMBER_OF_MOVIES3, SHOWED_COUNTRY_NAME3);
		InputObjectWorldMap object4 = new InputObjectWorldMap(OSI_COUNTRY_NAME4, NUMBER_OF_MOVIES4, SHOWED_COUNTRY_NAME4);
		InputObjectWorldMap object5 = new InputObjectWorldMap(OSI_COUNTRY_NAME5, NUMBER_OF_MOVIES5, SHOWED_COUNTRY_NAME5);
		
		list1.add(object1);
		list1.add(object2);
		list1.add(object3);
		list1.add(object4);
		list1.add(object5);
		
		list2.add(object5);
		
		assertEquals(PieChartWebPage.getIndexOfMaxNumberOfMovies(list1), 1);

		assertEquals(PieChartWebPage.getIndexOfMaxNumberOfMovies(list2), 0);
		
		assertEquals(PieChartWebPage.getIndexOfMaxNumberOfMovies(list3), -1);
	}

	

}*/
