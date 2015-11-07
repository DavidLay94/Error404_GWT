package movie.db.shared;

import java.io.Serializable;

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
