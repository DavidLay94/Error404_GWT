package movie.db.client;

/* a small step for software engineering, a huge step for error404! */
//blablablatestestest ich bin ned kreativ

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import movie.db.shared.DataResultAggregated;
import movie.db.shared.DataResultShared;
import movie.db.shared.Selection;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.GeoMap;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Error404 implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */

	/**
	 * This is the entry point method.
	 */

	public void onModuleLoad() {
		initializePanels();
	}

	/* Class Variables */
	private VerticalPanel mainPanel = new VerticalPanel();
	private HorizontalPanel worldmapPanel;
	private TextArea dummyTextArea = new TextArea();
	private FlexTable resultFlexTable = new FlexTable();
	private RadioButton genreRBAnd = new RadioButton("genreRBGroup", "AND");
	private RadioButton genreRBOR = new RadioButton("genreRBGroup", "OR");
	private RadioButton countryRBAnd = new RadioButton("countryRBGroup", "AND");
	private RadioButton countryRBOR = new RadioButton("countryRBGroup", "OR");
	private RadioButton langRBAnd = new RadioButton("langRBGroup", "AND");
	private RadioButton langRBOR = new RadioButton("langRBGroup", "OR");
	private ListBox genreListBox = new ListBox();
	private ListBox countryListBox = new ListBox();
	private ListBox langListBox = new ListBox();
	private TextBox tbYear = new TextBox();

	private void initializePanels() {

		HorizontalPanel selectionPanel = new HorizontalPanel();
		HorizontalPanel timebarPanel = new HorizontalPanel();

		FlexTable selectionCriteriaTable = new FlexTable();
		Button showAsButton = new Button();
		showAsButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				showAsButtonClick();
			}
		});
		showAsButton.setText("Show as");

		Button showMapButton = new Button();
		showMapButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				showWorldMapClick();
			}
		});
		showMapButton.setText("Show on Worldmap");
		initializeGenres();
		initializeCountries();
		initializeLanguages();

		genreRBAnd.setValue(true);
		countryRBAnd.setValue(true);
		langRBAnd.setValue(true);

		selectionCriteriaTable.setText(0, 0, "Genre:");
		selectionCriteriaTable.setWidget(0, 1, genreListBox);
		// selectionCriteriaTable.setWidget(0, 2, genreRBAnd);
		// selectionCriteriaTable.setWidget(0, 3, genreRBOR);
		selectionCriteriaTable.setText(1, 0, "Country:");
		selectionCriteriaTable.setWidget(1, 1, countryListBox);
		// selectionCriteriaTable.setWidget(1, 2, countryRBAnd);
		// selectionCriteriaTable.setWidget(1, 3, countryRBOR);
		selectionCriteriaTable.setText(2, 0, "Language:");
		selectionCriteriaTable.setWidget(2, 1, langListBox);
		// selectionCriteriaTable.setWidget(2, 2, langRBAnd);
		// selectionCriteriaTable.setWidget(2, 3, langRBOR);
		selectionCriteriaTable.setWidget(3, 0, showAsButton);
		selectionCriteriaTable.setWidget(3, 1, initializeFormats());
		selectionCriteriaTable.setText(4, 0, "Year");
		selectionCriteriaTable.setWidget(4, 1, tbYear);
		selectionCriteriaTable.setWidget(4, 2, showMapButton);

		selectionPanel.add(selectionCriteriaTable);

		timebarPanel.setWidth("1500px");
		timebarPanel.setHeight("250px");
		timebarPanel.setBorderWidth(3);

		/*
		 * worldmapPanel.setWidth("1500px"); worldmapPanel.setHeight("500px");
		 * worldmapPanel.setBorderWidth(3);
		 */

		mainPanel.add(selectionPanel);
		mainPanel.add(timebarPanel);
		mainPanel.add(initializeWorldMap());
		// mainPanel.add(worldmapPanel);

		// Associate the Main panel with the HTML host page.
		RootPanel.get("mainPage").add(mainPanel);
	}

	private void initializeGenres() {
		for (Genres genre : Genres.values()) {
			genreListBox.addItem(genre.getName());
		}
		genreListBox.setVisibleItemCount(5);
		genreListBox.setMultipleSelect(true);
		genreListBox.setItemSelected(0, false); // first item is selected by
												// default
	}

	private void initializeCountries() {
		for (Countries country : Countries.values()) {
			countryListBox.addItem(country.getName());
		}
		countryListBox.setVisibleItemCount(5);
		countryListBox.setMultipleSelect(true);
		countryListBox.setItemSelected(0, false); // first item is selected by
													// default
	}

	private void initializeLanguages() {
		for (Languages language : Languages.values()) {
			langListBox.addItem(language.getName());
		}
		langListBox.setVisibleItemCount(5);
		langListBox.setMultipleSelect(true);
		langListBox.setItemSelected(0, false); // first item is selected by
												// default
	}

	private VerticalPanel initializeFormats() {
		VerticalPanel formatsPanel = new VerticalPanel();
		for (Formats format : Formats.values()) {
			formatsPanel
					.add(new RadioButton("formatsRBGroup", format.getName()));
		}
		((RadioButton) formatsPanel.getWidget(0)).setValue(true);
		return formatsPanel;
	}

	private final void showWorldMapClick() {
		fillWorldmap();
	}

	private final void showAsButtonClick() {

		dummyTextArea.setWidth("500px");
		dummyTextArea.setHeight("200px");
		ArrayList<String> selectedGenres = new ArrayList<String>();
		if (genreListBox.getSelectedIndex() != -1) {
			for (int i = 0; i < genreListBox.getItemCount(); i++) {
				if (genreListBox.isItemSelected(i)) {
					selectedGenres.add(genreListBox.getItemText(i));
				}
			}
		}
		ArrayList<String> selectedCountries = new ArrayList<String>();
		if (countryListBox.getSelectedIndex() != -1) {
			for (int i = 0; i < countryListBox.getItemCount(); i++) {
				if (countryListBox.isItemSelected(i)) {
					selectedCountries.add(countryListBox.getItemText(i));
				}
			}
		}
		ArrayList<String> selectedLanguages = new ArrayList<String>();
		if (langListBox.getSelectedIndex() != -1) {
			for (int i = 0; i < langListBox.getItemCount(); i++) {
				if (langListBox.isItemSelected(i)) {
					selectedLanguages.add(langListBox.getItemText(i));
				}
			}
		}

		// String dummyTextAreaString = selectionToSQLString(selectedCountries,
		// "countries");
		Selection selection = new Selection();
		selection.setSelectedCountries(selectedCountries);
		selection.setSelectedLanguages(selectedLanguages);
		selection.setSelectedGenres(selectedGenres);
		((HorizontalPanel) mainPanel.getWidget(1)).add(resultFlexTable);
		showResults(selection);
	}

	private MyServiceAsync dbService = (MyServiceAsync) GWT
			.create(MyService.class);

	private void fillWorldmap() {
		try {
			int selectedYear = Integer.parseInt(tbYear.getText());
			if (selectedYear >= 1900 && selectedYear <= 2100) {

				dbService.getWorldMapData(selectedYear,
						new AsyncCallback<ArrayList<DataResultAggregated>>() {
							@Override
							public void onFailure(Throwable caught) {
							}

							@Override
							public void onSuccess(
									ArrayList<DataResultAggregated> result) {
								//refreshWorldMap(result);
								worldMapInputDataList = result; //	Cannot refer to a non-final variable list inside an inner class defined in a different method
							}
						});
			} else {
				Window.alert("Please insert a valid number (1900-2100)");
			}
		} catch (Exception ex) {
			Window.alert("Please insert a valid number (1900-2100)");
		}
	}

	private void showResults(Selection selection) {

		dbService.getFilteredData(selection,
				new AsyncCallback<Map<Integer, DataResultShared>>() {
					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(Map<Integer, DataResultShared> result) {
						int i = 1;
						try {
							resultFlexTable.setText(0, 0, "Movie");
							resultFlexTable.setText(0, 1, "Year");
							resultFlexTable.setText(0, 2, "Genres");
							resultFlexTable.setText(0, 3, "Languages");
							resultFlexTable.setText(0, 4, "Countries");

							for (DataResultShared entry : result.values()) {
								resultFlexTable.setText(i, 0,
										entry.getMovieName());
								resultFlexTable.setText(i, 1,
										Integer.toString(entry.getYear()));
								resultFlexTable.setText(i, 2,
										arrayListToStringConverter(entry
												.getGenres()));
								resultFlexTable.setText(i, 3,
										arrayListToStringConverter(entry
												.getLanguages()));
								resultFlexTable.setText(i, 4,
										arrayListToStringConverter(entry
												.getCountries()));
								i++;
							}
						} catch (Exception ex1) {
							resultFlexTable.setText(i, 0, ex1.toString());
						}
					}
				});
	}

	private String arrayListToStringConverter(ArrayList<String> alist) {
		String returnString = "";
		for (String s : alist) {
			returnString = returnString + s + ", ";
		}
		returnString = returnString.substring(0, returnString.length() - 2);
		return returnString;

	}

	// /////////////////////////
	private ArrayList<DataResultAggregated> worldMapInputDataList = new ArrayList<DataResultAggregated>();
/*
	private ArrayList<DataResultAggregated> generateList() { // DUMMY
		ArrayList<DataResultAggregated> dataWorldMap = new ArrayList<DataResultAggregated>();
		DataResultAggregated result1 = new DataResultAggregated();
		DataResultAggregated result2 = new DataResultAggregated();
		DataResultAggregated result3 = new DataResultAggregated();
		DataResultAggregated result4 = new DataResultAggregated();
		result1.setCountryName("United States");
		result1.setAggregatedNumberOfMovies(400);
		result2.setCountryName("Germany");
		result2.setAggregatedNumberOfMovies(200);
		result3.setCountryName("China");
		result3.setAggregatedNumberOfMovies(1000);
		result4.setCountryName("Brazil");
		result4.setAggregatedNumberOfMovies(500);
		dataWorldMap.add(result1);
		dataWorldMap.add(result2);
		dataWorldMap.add(result3);
		dataWorldMap.add(result4);
		return dataWorldMap;
	}*/

	private HorizontalPanel initializeWorldMap() {
		worldmapPanel = new HorizontalPanel();
		refreshWorldMap();
		return worldmapPanel;
	}
	private void refreshWorldMap(){
		Runnable onLoadCallback = new Runnable() {
			public void run() {
				WorldMap map = new WorldMap(worldMapInputDataList);
				worldmapPanel.add(map.getWorldMap());
				map.getWorldMap().draw(map.getData(), map.getOptions());
			}
		};
		VisualizationUtils.loadVisualizationApi(onLoadCallback, GeoMap.PACKAGE);
	}
}
