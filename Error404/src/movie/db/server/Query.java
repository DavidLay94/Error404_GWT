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
	
	private LinkedList<DataResult> dataResultList = new LinkedList<DataResult>();
	private DataResult dataResult;
	
	private LinkedList<String> countryList = new LinkedList<String>();
	private LinkedList<String> genreList = new LinkedList<String>();
	private LinkedList<String> languageList = new LinkedList<String>();
	
	public LinkedList<DataResult> getAllData() throws SQLException{
		
		statement = connection.createStatement(); //for creating statements out of the established connection
		
		
		try{
			
		      String sql =  "SELECT movies.id, movies.name, movies.year, countries.name, languages.name, genres.name "
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
		    	  country = result.getString("countries.name");
		    	  language = result.getString("languages.name");
		    	  genre = result.getString("genres.name");
		    	  
		    	if(movieId != result.getInt("movies.id")){
		    		  
		    		 	if(movieId != 0){
		    			  
		    			  dataResult.setMovie(movie);
		    			  dataResult.setYear(year);

		    			 
		    			  
		    			  
		    			  movieId = 0;
		    			  
		    			  countryList.clear();
		    			  languageList.clear();
		    			  genreList.clear();
		    			  
		    		  }
		    		  
		    		  
		    		 	countryList.add(country);
			    	  
		    		 	languageList.add(language);
			    	  
		    		 	genreList.add(genre);
		    		 	
		    		 	 dataResultList.add(dataResult);
		    		  
		    	  }else{
		    		  
		    		  boolean addCountry = true;
		    		  boolean addLanguage = true;
		    		  boolean addGenre = true;
		    		  
		    		  int i = 0;
		    		  while(i < countryList.size()){
		    			  if(countryList.get(i).equals(country)){
				    		  i = countryList.size();
				    		  addCountry = false;
				    	  }
		    			  i++;
		    		  }
		    		  
		    		  i = 0;
		    		  while(i < languageList.size()){
		    			  if(languageList.get(i).equals(language)){
				    		  i = languageList.size();
				    		  addLanguage = false;
				    	  } 
		    			  i++;
		    		  }
		    		  
		    		  i = 0;
		    		  while(i < genreList.size()){
		    			  if(genreList.get(i).equals(genre)){
		    				  i = genreList.size();
		    				  addGenre = false;
		    				  
				    	  } 
		    			  i++;
		    		  }
		    		  
		    		  if(addCountry == true){
		    			  countryList.add(country);
		    			 
		    		  }
		    		  if(addLanguage == true){
		    			languageList.add(language);
		    			
		    		  }
		    		  if(addGenre == true){
		    			genreList.add(genre);
		    			
		    		  }
		    		  
		    	  }
		    	  
		    	 movieId  = result.getInt("movies.id");
		    	 movie = result.getString("name");
		    	 year = result.getInt("year");
		    	 
		      } 
		      

		      
		}catch (Exception exc){
			System.out.println(exc);		
		//}finally {
		  //  try { result.close(); } catch (Exception e) { /* ignored */ }
		    //try { statement.close(); } catch (Exception e) { /* ignored */ }
		   // try { connection.close(); } catch (Exception e) { /* ignored */ }
		}
		return dataResultList;
	}
}

