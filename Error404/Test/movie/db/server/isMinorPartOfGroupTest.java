//package movie.db.server;
//
//import static org.junit.Assert.*;
//
//import movie.db.server.Query;
//
//import org.junit.Test;
//
//
//public class isMinorPartOfGroupTest {
//
//	@Test
//	public void test() {
//		Query q = new Query();
//		
//		//Nazi Germany is a minor Part, because Germany is higher
//		String singleCountry1 = "Nazi Germany";
//		String[] splitCountries1 = {"Nazi Germany","Italy","Germany"};
//		assertEquals(q.isMinorPartOfGroupCountries(singleCountry1, splitCountries1),true);
//		
//		//Although we have a group in "splitCountries2", the specific country is Portugal which is not part of that 
//		//group.
//		String singleCountry2 = "Portugal";
//		String[] splitCountries2 = {"Nazi Germany","Italy","Germany"};
//		assertEquals(q.isMinorPartOfGroupCountries(singleCountry2, splitCountries2),false);
//		
//		//Germany is part of the "German-Group", though it is in a higher hierarchical position than 
//		//Nazi Germany, so the function returns false.
//		String singleCountry3 = "Germany";
//		String[] splitCountries3 = {"Nazi Germany","Italy","Germany"};
//		assertEquals(q.isMinorPartOfGroupCountries(singleCountry3, splitCountries3),false);
//
//		//Weimar Republic is Part of the Group, is higher than Nazi Germany but lower than Germany, so the 
//		//function returns false.
//		String singleCountry4 = "Weimar Republic";
//		String[] splitCountries4 = {"Nazi Germany","Italy","Germany", "Weimar Republic"};
//		assertEquals(q.isMinorPartOfGroupCountries(singleCountry4, splitCountries4),true);		
//
//		//Weimar Republic is higher than Nazi Germany, so the function returns false
//		String singleCountry5 = "Weimar Republic";
//		String[] splitCountries5 = {"Italy", "Weimar Republic","Nazi Germany"};
//		assertEquals(q.isMinorPartOfGroupCountries(singleCountry5, splitCountries5),false);
//		
//		//Nazi Germany is a minor part. The positioning is different than in splitCountries6, but it mustn't 
//		//change the output.
//		String singleCountry6 = "Nazi Germany";
//		String[] splitCountries6 = {"Italy", "Weimar Republic","Nazi Germany"};
//		assertEquals(q.isMinorPartOfGroupCountries(singleCountry6, splitCountries6),true);
//		
//		//SingleCountry is an empty string
//		String singleCountry7 = "";
//		String[] splitCountries7 = {"Italy", "Weimar Republic","Nazi Germany"};
//		assertEquals(q.isMinorPartOfGroupCountries(singleCountry7, splitCountries7),false);
//		
//		//SingleCountry and splitCountries are empty. 
//		String singleCountry8 = "";
//		String[] splitCountries8 = {} ;
//		assertEquals(q.isMinorPartOfGroupCountries(singleCountry8, splitCountries8),false);
//	}
//
//}
