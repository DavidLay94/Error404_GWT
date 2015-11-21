/**
 * @author Patrick Muntwyler
 * This is the object which is needed for the method generateDataTable in the class WorldMap
 */


package movie.db.client;

public class InputObjectWorldMap {
	
	private String OSICountryName;
	private int numberOfMovies;
	private String showedCountryName;
	
	public InputObjectWorldMap(String OSIcountryName, int numberOfMovies, String showedCountryName){
		this.OSICountryName = OSIcountryName;
		this.numberOfMovies = numberOfMovies;
		this.showedCountryName = showedCountryName;
		
	}

	public String getOSICountryName() {
		return OSICountryName;
	}

	public int getNumberOfMovies() {
		return numberOfMovies;
	}

	public String getShowedCountryName() {
		return showedCountryName;
	}
	/**
	 * @author Patrick Muntwyer
	 * @param an integer which should be added to the variable numberOfMovies
	 */
	
	public void addNumberOfMovies(int numberOfMovies){
		this.numberOfMovies = this.numberOfMovies + numberOfMovies;
	}
	

}
