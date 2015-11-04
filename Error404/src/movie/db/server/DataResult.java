package movie.db.server;

import java.util.LinkedList;

public class DataResult {

	private String movie;
	private int year;
	private LinkedList<String> country = new LinkedList<String>();
	private LinkedList<String> language = new LinkedList<String>();
	private LinkedList<String> genre = new LinkedList<String>();
	
	
	public String getMovie(){
		return movie;
		
	}
	
	public int getYear(){
		return year;
		
	}
	
	public LinkedList<String> getCountry(){
		return country;
		
	}
	
	public LinkedList<String> getLanguage(){
		return language;
		
	}
	
	public LinkedList<String> getGenre(){
		return genre;
		
	}
	
	
	public void setMovie(String movieName){
		movie = movieName;
	}
	
	public void setYear(int movieYear){
		year = movieYear;
	}
	
	public void setCountry(LinkedList<String> countryName){
		country = countryName;
	}
	
	public void setLanguage(LinkedList<String> languageName){
		language = languageName;
	}
	
	public void setGenre(LinkedList<String> genreName){
		country = genreName;
	}
	
}
