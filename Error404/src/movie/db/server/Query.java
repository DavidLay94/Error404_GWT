package movie.db.server;

import java.sql.*;
import java.util.*;

public class Query {

	Connection connection = ConnectionConfiguration.getConnection();
	private Statement statement;
	private ResultSet result;
	private String movie;
	private int year;
	private String country;
	private String language;
	private String genre;
	
	private int movieId = 0;
	private int movieIndex = 0;
	private int countryId;
	private int languageId;
	private int genreId;
	
	private Map<Integer, DataResult> dataResultList = new HashMap<Integer, DataResult>();
	private DataResult dataResult;
	
	private Hashtable<Integer, String> countryList = new Hashtable<Integer, String>();
	private Hashtable<Integer, String> genreList = new Hashtable<Integer, String>();
	private Hashtable<Integer, String> languageList = new Hashtable<Integer, String>();
	
	@SuppressWarnings("finally")
	public Map<Integer, DataResult> getAllData() throws SQLException{
		
		statement = connection.createStatement(); //for creating statements out of the established connection
		
		
		try{
			
		      String sql =  "SELECT movies.id, countries.id, languages.id, genres.id, movies.name, movies.year, countries.name, languages.name, genres.name "
		    		  		+"FROM movies "
		    		  		+"JOIN movies_countries "
		    		  		+"ON movies.id=movies_countries.movie_id "
		    		  		+"JOIN countries "
		    		  		+"ON movies_countries.country_id=countries.id "
		    		  		+"JOIN movies_languages "
		    		  		+"ON movies.id=movies_languages.movie_id "
		    		  		+"JOIN languages "
		    		  		+"ON movies_languages.language_id=languages.id "
		    		  		+"JOIN movies_genres "
		    		  		+"ON movies.id=movies_genres.movie_id "
		    		  		+"JOIN genres "
		    		  		+"ON movies_genres.genre_id=genres.id "
		    		  		//+"WHERE genres.name = 'Horror' "
		    		  		//+"AND languages.name = 'Deutsch'"
		    		  		+"ORDER BY movies.name ";
		      
		      result = statement.executeQuery(sql);

		      while(result.next()){
		    	  
		    	  //Retrieve data by column name
		    	  movieId  = result.getInt("movies.id");
		    	  countryId  = result.getInt("countries.id");
		    	  languageId  = result.getInt("languages.id");
		    	  genreId  = result.getInt("genres.id");
		    	  movie = result.getString("name");
		    	  year = result.getInt("year");
		    	  country = result.getString("countries.name");
		    	  language = result.getString("languages.name");
		    	  genre = result.getString("genres.name");
		    	    
		    	  if(dataResultList.containsKey(movieId)){
		    		  
		    		  if(!countryList.containsKey(countryId)){
		    			  countryList.put(countryId, country); 
		    		  }
		    		  if(!languageList.containsKey(languageId)){
		    			  languageList.put(languageId, language); 
		    		  }
		    		  if(!genreList.containsKey(genreId)){
		    			  genreList.put(genreId, genre); 
		    		  }
		    		  
		    	  }else{
		    		  
		    		  if(movieIndex != 0){
		    			  dataResultList.put(movieIndex, dataResult);
		    			  movieIndex++;
		    		  }
		    		  dataResult.setMovie(movie);
		    		  dataResult.setYear(year);
		    		  countryList.put(countryId, country);
		    		  languageList.put(languageId, language);
		    		  genreList.put(genreId, genre);
		    		  
		    	  } 
		      }
		      
		}catch (Exception exc){
			System.out.println(exc);		
		}finally {
		  try { statement.close(); } catch (Exception e) { /* ignored */ }
		  try { connection.close(); } catch (Exception e) { /* ignored */ }
		  
		return dataResultList;
		}
	}
}
	


