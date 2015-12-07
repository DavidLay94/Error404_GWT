package movie.db.shared;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class is used to put together the selections made by the user on the GUI 
 * so they are easily transferable to the server side.
 * 
 * @Author Christoph Weber
 */
@SuppressWarnings("serial")
public class Selection implements Serializable {

	private String selectedMovieName;
	private Integer selectedYear; // Integer instead of int so that it can be
									// nullable
	private ArrayList<Duration> selectedDurations = new ArrayList<Duration>();
	private ArrayList<String> selectedCountries = new ArrayList<String>();
	private ArrayList<String> selectedLanguages = new ArrayList<String>();
	private ArrayList<String> selectedGenres = new ArrayList<String>();

	public String getSelectedMovieName() {
		return selectedMovieName;
	}

	public Integer getSelectedYear() {
		return selectedYear;
	}

	public ArrayList<Duration> getSelectedDurations() {
		return selectedDurations;
	}

	public ArrayList<String> getSelectedCountries() {
		return selectedCountries;
	}

	public ArrayList<String> getSelectedLanguages() {
		return selectedLanguages;
	}

	public ArrayList<String> getSelectedGenres() {
		return selectedGenres;
	}

	public void setSelectedMovieName(String selectedMovieName) {
		this.selectedMovieName = selectedMovieName;
	}

	public void setSelectedYear(Integer selectedYear) {
		this.selectedYear = selectedYear;
	}

	public void setSelectedDuration(ArrayList<String> selectedDurations) {
		for (String duration : selectedDurations) {
			if (duration.equals("Short (0-30min)")) {
				this.selectedDurations.add(Duration.Short);
			}
			if (duration.equals("Medium (30-60min)")) {
				this.selectedDurations.add(Duration.Medium);
			}
			if (duration.equals("Long (60-120min)")) {
				this.selectedDurations.add(Duration.Long);
			}
			if (duration.equals("Extra Long (>120min)")) {
				this.selectedDurations.add(Duration.ExtraLong);
			} else {
				this.selectedDurations.add(Duration.NA);
			}
		}

	}

	public void setSelectedCountries(ArrayList<String> selectedCountries) {
		this.selectedCountries = selectedCountries;
	}

	public void setSelectedLanguages(ArrayList<String> selectedLanguages) {
		this.selectedLanguages = selectedLanguages;
	}

	public void setSelectedGenres(ArrayList<String> selectedGenres) {
		this.selectedGenres = selectedGenres;
	}
}
