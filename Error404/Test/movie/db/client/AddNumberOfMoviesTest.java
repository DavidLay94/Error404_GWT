/**
 * @author Patrick Muntwyler
 */
package movie.db.client;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

public class AddNumberOfMoviesTest extends GWTTestCase {
	
	private static final String COUNTRY_OSI1 = "Netherlands";
	private static final int NUMBER_OF_MOVIES1 = 200;
	private static final String COUNTRY_NAME_SHOWED1 = "Netherlands";
	
	private static final String COUNTRY_OSI2 = "Netherlands";
	private static final int NUMBER_OF_MOVIES2 = 0;
	private static final String COUNTRY_NAME_SHOWED2 = "Netherlands";
	
	private static final String COUNTRY_OSI3 = "Netherlands";
	private static final int NUMBER_OF_MOVIES3 = -50;
	private static final String COUNTRY_NAME_SHOWED3 = "Netherlands";
	
	private static final int TO_ADD = 50;
	
	private static final InputObjectWorldMap testObject1 = 
			new InputObjectWorldMap(COUNTRY_OSI1, NUMBER_OF_MOVIES1, COUNTRY_NAME_SHOWED1);
	
	private static final InputObjectWorldMap testObject2 = 
			new InputObjectWorldMap(COUNTRY_OSI2, NUMBER_OF_MOVIES2, COUNTRY_NAME_SHOWED2);
	
	private static final InputObjectWorldMap testObject3 = 
			new InputObjectWorldMap(COUNTRY_OSI3, NUMBER_OF_MOVIES3, COUNTRY_NAME_SHOWED3);

	

	@Override
	public String getModuleName() {
		
		return "movie.db.Error404";
	}
	
	@Test
	public void test() {
		
		testObject1.addNumberOfMovies(TO_ADD);
		testObject2.addNumberOfMovies(TO_ADD);
		testObject3.addNumberOfMovies(TO_ADD);
		
		assertEquals(testObject1.getNumberOfMovies(), NUMBER_OF_MOVIES1 + TO_ADD);
		assertEquals(testObject2.getNumberOfMovies(), NUMBER_OF_MOVIES2 + TO_ADD);
		assertEquals(testObject3.getNumberOfMovies(), NUMBER_OF_MOVIES3 + TO_ADD);
	}

}
