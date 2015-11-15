package movie.db.shared;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * This class provides the objects which are visualized in the table.
 */
@SuppressWarnings("serial")
public class DataResultShared implements Serializable{

	private String movieName;
	private int year;
	private int duration;
	private ArrayList<String> countries = new ArrayList<String>();
	private ArrayList<String> languages = new ArrayList<String>();
	private ArrayList<String> genres = new ArrayList<String>();
	
	
	public String getMovieName(){
		return movieName;		
	}
	
	public int getYear(){
		return year;			
	}	
	
	public int getDuration(){
		return duration;			
	}
	
	public ArrayList<String> getCountries(){
		return countries;		
	}
	
	public ArrayList<String> getLanguages(){
		return languages;		
	}
	
	public ArrayList<String> getGenres(){
		return genres;		
	}
		
	public void setMovieName(String movieName){
		this.movieName = movieName;
	}
	
	public void setYear(int movieYear){
		this.year = movieYear;
	}
	
	public void setDuration(int duration){
		this.duration = duration;
	}
	
	public void setCountries(ArrayList<String> countries){		
		this.countries = countries;
	}
	
	public void setLanguages(ArrayList<String> languages){		
		this.languages = languages;
	}
	
	public void setGenres(ArrayList<String> genres){		
		this.genres = genres;
	}
}


