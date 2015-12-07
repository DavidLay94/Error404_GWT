//package movie.db.server;
//
//import static org.junit.Assert.*;
//
//import java.util.ArrayList;
//
//import movie.db.server.Query;
//import movie.db.shared.Selection;
//
//import org.junit.Test;
///* regression tested 06.12.2015*/
//public class selectionToSQLWhereClauseTest {
//
//	@Test
//	public void test() {
//		Query query = new Query();
//		//Every Category has something selected
//		////////////////////////////////////////////////////////////////////////////////////
//		Selection testSelectionAllSelected = new Selection();	
//		
//		ArrayList<String> selectedCountriesAllSelected = new ArrayList<String>();
//		selectedCountriesAllSelected.add("Romania");
//		selectedCountriesAllSelected.add("Canada");
//		selectedCountriesAllSelected.add("United States of America");
//		
//		ArrayList<String> selectedLanguagesAllSelected = new ArrayList<String>();
//		selectedLanguagesAllSelected.add("Spanish");
//		selectedLanguagesAllSelected.add("Greek");
//		selectedLanguagesAllSelected.add("Chinese");
//		
//		ArrayList<String> selectedGenresAllSelected = new ArrayList<String>();
//		selectedGenresAllSelected.add("Horror");
//		selectedGenresAllSelected.add("Comedy");
//		selectedGenresAllSelected.add("Action");
//
//		testSelectionAllSelected.setSelectedCountries(selectedCountriesAllSelected);
//		testSelectionAllSelected.setSelectedLanguages(selectedLanguagesAllSelected);
//		testSelectionAllSelected.setSelectedGenres(selectedGenresAllSelected);
//		testSelectionAllSelected.setSelectedMovieName("Jumanji"); 
//		testSelectionAllSelected.setSelectedYear(1995); 
//		
//		String expectedResult1 = " WHERE 1 = 1 AND name like 'Jumanji' AND year = 1995 AND (country LIKE '%Romania%' OR country LIKE '%Canada%' OR country LIKE '%United States of America%') AND (language LIKE '%Spanish%' OR language LIKE '%Greek%' OR language LIKE '%Chinese%') AND (genre LIKE '%Horror%' OR genre LIKE '%Comedy%' OR genre LIKE '%Action%') ";
//		assertEquals(expectedResult1,query.selectionToSQLWhereClause(testSelectionAllSelected));
//		
//		//Not every Category has something selected
//		////////////////////////////////////////////////////////////////////////////////////
//		Selection testSelectionSomeSelected = new Selection();	
//		
//		ArrayList<String> selectedCountriesSomeSelected = new ArrayList<String>();
//		selectedCountriesSomeSelected.add("Romania");
//		
//		ArrayList<String> selectedLanguagesSomeSelected = new ArrayList<String>();
//		
//		ArrayList<String> selectedGenresSomeSelected = new ArrayList<String>();
//
//		testSelectionSomeSelected.setSelectedCountries(selectedCountriesSomeSelected);
//		testSelectionSomeSelected.setSelectedLanguages(selectedLanguagesSomeSelected);
//		testSelectionSomeSelected.setSelectedGenres(selectedGenresSomeSelected);
//		testSelectionSomeSelected.setSelectedMovieName(null);
//		testSelectionSomeSelected.setSelectedYear(null); 
//		
//		String expectedResult2 = " WHERE 1 = 1 AND (country LIKE '%Romania%') ";
//		assertEquals(expectedResult2,query.selectionToSQLWhereClause(testSelectionSomeSelected));
//		
//		//Selection properties are empty
//		////////////////////////////////////////////////////////////////////////////////////
//		Selection testSelectionEmpty = new Selection();	
//		
//		String expectedResult3 = " WHERE 1 = 1 ";
//		assertEquals(expectedResult3,query.selectionToSQLWhereClause(testSelectionEmpty));
//		
//		//Selection is null
//		////////////////////////////////////////////////////////////////////////////////////	
//		String expectedResult4 = " ";
//		assertEquals(expectedResult4,query.selectionToSQLWhereClause(null));
//	}
//}
