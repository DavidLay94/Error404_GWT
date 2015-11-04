package movie.db.server;

import java.util.Hashtable;

public class DataResult {

	private String movie;
	private int year;
	private Hashtable<Integer, String> country = new Hashtable<Integer, String>();
	private Hashtable<Integer, String> language = new Hashtable<Integer, String>();
	private Hashtable<Integer, String> genre = new Hashtable<Integer, String>();
	
	
	public String getMovie(){
		return movie;
		
	}
	
	public int getYear(){
		return year;
		
	}
	
	public Hashtable<Integer, String> getCountry(){
		return country;
		
	}
	
	public Hashtable<Integer, String> getLanguage(){
		return language;
		
	}
	
	public Hashtable<Integer, String> getGenre(){
		return genre;
		
	}
	
	
	public void setMovie(String movieName){
		movie = movieName;
	}
	
	public void setYear(int movieYear){
		year = movieYear;
	}
	
	public void setCountry(Hashtable<Integer, String> countryName){
		country = countryName;
	}
	
	public void setLanguage(Hashtable<Integer, String> languageName){
		language = languageName;
	}
	
	public void setGenre(Hashtable<Integer, String> genreName){
		country = genreName;
	}
	
}
