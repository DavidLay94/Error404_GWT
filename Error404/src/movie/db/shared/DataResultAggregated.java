package movie.db.shared;

import java.io.Serializable;
/*
 * This class provides the objects which are visualized on the worldmap.
 */
@SuppressWarnings("serial")
public class DataResultAggregated implements Serializable{
	private String countryName;
	private int aggregatedNumberOfMovies;
	
	
	public String getCountryName(){
		return countryName;		
	}
	
	public int getAggregatedNumberOfMovies(){
		return aggregatedNumberOfMovies;			
	}	
		
	public void setCountryName(String countryName){
		this.countryName = countryName;
	}
	
	public void setAggregatedNumberOfMovies(int aggregatedNumberOfMovies){
		this.aggregatedNumberOfMovies = aggregatedNumberOfMovies;
	}	
}
