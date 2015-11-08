package movie.db.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import movie.db.shared.DataResultAggregated;
import movie.db.shared.DataResultShared;
import movie.db.shared.Selection;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.GeoMap;
import com.google.gwt.visualization.client.visualizations.Table;

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
	private SimpleLayoutPanel worldMapPanel;
	private HorizontalPanel resultTablePanel;
	private ListBox genreListBox = new ListBox();
	private ListBox countryListBox = new ListBox();
	private ListBox langListBox = new ListBox();
	private TextBox tbYear = new TextBox();
	private ArrayList<DataResultAggregated> worldMapInputDataList = new ArrayList<DataResultAggregated>();
	private TabPanel tabPanel = new TabPanel();
	private Map<Integer, DataResultShared> resultTableInputDataList = new HashMap<Integer, DataResultShared>();

	private final static String TABPANELHEIGHT = "540px";
	private final static String TABPANELWIDTH = "1200px";

	/**
	 * Initializes most of the important panels and buttons by calling smaller
	 * methods in its body. These methods initialize parts of the mainPanel as
	 * well. Buttons, panels etc. are visible and working at the end of the
	 * method.
	 * 
	 * @Pre EntryPoint class must be loaded correctly
	 */
	private void initializePanels() {
		mainPanel.setSize("100vw", "82vh");
		mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		VerticalPanel selectionPanel = new VerticalPanel();

		FlexTable selectionCriteriaTable = new FlexTable();
		FlexTable worldMapCriteriaTable = new FlexTable();

		Button showAsButton = new Button();
		Button showMapButton = new Button();
		Button cleanSelectionButton = new Button();
		Button cleanWorldMapButton = new Button();
		initializeButtons(showAsButton, showMapButton, cleanSelectionButton,
				cleanWorldMapButton);

		initializeSelectionListBox(genreListBox,"genres","genre");
		initializeSelectionListBox(countryListBox,"countries","country");
		initializeSelectionListBox(langListBox,"languages","language");

		initializeSelectionCriteriaTable(selectionCriteriaTable, showAsButton);
		initializeWorldMapCriteriaTable(worldMapCriteriaTable, showMapButton,
				cleanSelectionButton, cleanWorldMapButton);

		selectionPanel.add(selectionCriteriaTable);
		SimpleLayoutPanel emptyPanel = new SimpleLayoutPanel();
		emptyPanel.setSize("0px", "15px");
		selectionPanel.add(emptyPanel);
		selectionPanel.add(worldMapCriteriaTable);

		mainPanel.add(selectionPanel);

		tabPanel.setSize(TABPANELWIDTH, TABPANELHEIGHT);
		tabPanel.add(initializeWorldMap(), "Worldmap");
		tabPanel.add(initializeResultTable(), "Table");
		tabPanel.selectTab(0);
		mainPanel.add(tabPanel);

		// Associate the Main panel with the HTML host page.
		RootPanel.get("mainPage").add(mainPanel);
	}

	/**
	 * Initializes a table for the selection part of the website. Adds all
	 * selection criteria boxes to a flextable on the main page and makes it
	 * visible.
	 * 
	 * @Pre selectionCriteria table and showAsButton must be implemented
	 * @param selectionCriteriaTable
	 * @param showAsButton
	 */
	private void initializeSelectionCriteriaTable(
			FlexTable selectionCriteriaTable, Button showAsButton) {
		selectionCriteriaTable.setText(0, 0, "Genre:");
		selectionCriteriaTable.setWidget(1, 0, genreListBox);
		selectionCriteriaTable.setText(0, 1, "Country:");
		selectionCriteriaTable.setWidget(1, 1, countryListBox);
		selectionCriteriaTable.setText(0, 2, "Language:");
		selectionCriteriaTable.setWidget(1, 2, langListBox);
		selectionCriteriaTable.setWidget(1, 3, showAsButton);
		selectionCriteriaTable.setWidget(1, 4, initializeFormats());
	}

	/**
     * Initializes a worldmap criteria table visible on the main page.
     * Adds a textbox and three buttons to the flextable. 
     * The flextable is localized underneath selection part of the website.
     * 
     * @Pre Parameters have to be implemented
     * @param worldMapCriteriaTable
     * @param showMapButton
     * @param cleanSelectionButton
     * @param cleanWorldMapButton
     */
	private void initializeWorldMapCriteriaTable(
			FlexTable worldMapCriteriaTable, Button showMapButton,
			Button cleanSelectionButton, Button cleanWorldMapButton) {
		worldMapCriteriaTable.setText(0, 0, "Year:");
		worldMapCriteriaTable.setWidget(1, 0, tbYear);
		worldMapCriteriaTable.setWidget(1, 1, showMapButton);
		worldMapCriteriaTable.setWidget(2, 0, cleanSelectionButton);
		worldMapCriteriaTable.setWidget(2, 1, cleanWorldMapButton);
	}

	/**
     * Initializes all the buttons visible on the main page.
     * Adds an click handler to every button and calls a specific method when it's clicked.
     * 
     * @Pre Buttons have to be implemented in initalizePanelMethod.
     * @param showAsButton
     * @param showMapButton
     * @param cleanSelectionButton
     * @param cleanWorldMapButton
     * @Post every clcikHandlerMethod must be correctly implemented.
     */
	private void initializeButtons(Button showAsButton, Button showMapButton,
			Button cleanSelectionButton, Button cleanWorldMapButton) {
		showAsButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				showAsButtonClick();
			}
		});
		showAsButton.setText("Show as");

		showMapButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				showWorldMapClick();
			}
		});
		showMapButton.setText("Show Year on Worldmap");

		cleanSelectionButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cleanSelectionClick();
			}
		});
		cleanSelectionButton.setText("Clean Selection");

		cleanWorldMapButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cleanWorldMapClick();
			}
		});
		cleanWorldMapButton.setText("Clean Worldmap");
	}
	
	/**
	 * Initializes one selection Box with the entries from the database
	 * by making an async call to Query.java class
	 * 
	 * @Pre data in database and connection
	 * @param selectionListBox
	 * @param column
	 * @param columnId
	 */
	private void initializeSelectionListBox(final ListBox selectionListBox, String column, String columnId){		
		dbService.getColumnEntries(column, columnId,
				new AsyncCallback<ArrayList<String>>() {
					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(ArrayList<String> result) {
						for (String entry : result) {
							selectionListBox.addItem(entry);
						}
						selectionListBox.setVisibleItemCount(5);
						selectionListBox.setMultipleSelect(true);
						selectionListBox.setItemSelected(0, false); //first item is selected by default
					}
				});	
	}

	/**
     * Initializes a vertical panel with a single selection.
     * At this time only "table" is selectable since its working progress.
     * 
     * @Pre the criteria table of the main page must be implemented
     * @return VerticalPanel with content
     */
	private VerticalPanel initializeFormats() {
		VerticalPanel formatsPanel = new VerticalPanel();

		// Piechart and Barchart are greyed out until implemented!
		for (Formats format : Formats.values()) {
			RadioButton rb = new RadioButton("formatsRBGroup", format.getName());
			if (format.getName().equals("Table")) {
				rb.setEnabled(true);
			} else {
				rb.setEnabled(false);
			}
			formatsPanel.add(rb);
		}
		((RadioButton) formatsPanel.getWidget(0)).setValue(true);

		return formatsPanel;
	}
	
	/**
     * After the click on the showMapButton this method is called.
     * It calls the method fillWorldmap and selects the tab with the worldmap on it.
     * 
     * @Pre showMapButton must be implemented and clicked
     */
	private final void showWorldMapClick() {
		fillWorldmap();
		tabPanel.selectTab(0);
	}

	/**
	 * Cleans the selection of the selection boards and sets it back
	 * to the standard value. Will be called after the clickhandler event 
	 * of the cleanSelectionButton.
	 * 
	 * @Pre cleanSelectionButton must be implemented and clicked
	 * @post no item of the selection is selected
	 */
	private final void cleanSelectionClick(){
		
		/*
		 * First only one selection is allowed by now
		 * then selects the first item, 
		 * first item will be removed from the selection
		 * multiple selection is allowed again.
		 */
		genreListBox.setMultipleSelect(false); 
		genreListBox.setItemSelected(0,true);
		genreListBox.setItemSelected(0,false);
		genreListBox.setMultipleSelect(true);
		
		/*
		 * First only one selection is allowed by now
		 * then selects the first item, 
		 * first item will be removed from the selection
		 * multiple selection is allowed again.
		 */
		countryListBox.setMultipleSelect(false);
		countryListBox.setItemSelected(0,true);
		countryListBox.setItemSelected(0,false);
		countryListBox.setMultipleSelect(true);
		
		/*
		 * First only one selection is allowed by now
		 * then selects the first item, 
		 * first item will be removed from the selection
		 * multiple selection is allowed again.
		 */
		langListBox.setMultipleSelect(false);
		langListBox.setItemSelected(0,true);
		langListBox.setItemSelected(0,false);
		langListBox.setMultipleSelect(true);

		resultTableInputDataList.clear();
		refreshResultTable();
	}
	
	/**
	 * Cleans the worldmap from entries and shows an empty map again.
	 * Will be called after the cleanWorldMapButton is clicked. 
	 * 
	 * @Pre worldmap must be implemented
	 * @post worldmap must be empty
	 */
	private final void cleanWorldMapClick() {
		worldMapInputDataList.clear();
		refreshWorldMap();
	}

	/**
	 * Takes the selection of the selected genre, countries and languages and
	 * builds an table in a separated tab. Only shows the filtered part of all movies 
	 * in the table. Will be executed after clicking the showAsButton.
	 * 
	 * @Pre showAsButton Implemented and connection to database exists
	 */
	private final void showAsButtonClick() {
		//gets all items selected in the genre box
		ArrayList<String> selectedGenres = new ArrayList<String>();
		if (genreListBox.getSelectedIndex() != -1) {
			for (int i = 0; i < genreListBox.getItemCount(); i++) {
				if (genreListBox.isItemSelected(i)) {
					selectedGenres.add(genreListBox.getItemText(i));
				}
			}
		}
		
		//gets all items selected in the countries 
		ArrayList<String> selectedCountries = new ArrayList<String>();
		if (countryListBox.getSelectedIndex() != -1) {
			for (int i = 0; i < countryListBox.getItemCount(); i++) {
				if (countryListBox.isItemSelected(i)) {
					selectedCountries.add(countryListBox.getItemText(i));
				}
			}
		}
		
		//gets all items selected in the language box
		ArrayList<String> selectedLanguages = new ArrayList<String>();
		if (langListBox.getSelectedIndex() != -1) {
			for (int i = 0; i < langListBox.getItemCount(); i++) {
				if (langListBox.isItemSelected(i)) {
					selectedLanguages.add(langListBox.getItemText(i));
				}
			}
		}		
		
		Selection selection = new Selection(); //makes a new selection object
		selection.setSelectedCountries(selectedCountries);
		selection.setSelectedLanguages(selectedLanguages);
		selection.setSelectedGenres(selectedGenres);
		
		//gets all with the selected parameters and gives them back in a table format
		showResults(selection);
		tabPanel.selectTab(1);
	}

	
	private MyServiceAsync dbService = (MyServiceAsync) GWT
			.create(MyService.class);

	/**
	 * Called by the showWorldMapClick method, this method fills
	 * the countries of the worldmap by calling the async method getWorldMapData, 
	 * which gets the data from the database, filters the movies per year and gives
	 * the back.
	 * 
	 * @Pre database connection 
	 * @post worldmap filled
	 */
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
								// refreshWorldMap(result);
								worldMapInputDataList = result;
								refreshWorldMap();
							}
					});
			
			//if the given year is out of range	
			} else {
				Window.alert("Please insert a valid number (1900-2100)");
			}
		} catch (Exception ex) {
			Window.alert("Please insert a valid number (1900-2100)");
		}
	}

	/**
	 * Gets the data of the selection, searches the database
	 * for the given input and returns a table with the selected data. 
	 * 
	 * @Pre connection to database with data in it
	 * @param selection
	 * @post table with output printed
	 */
	private void showResults(Selection selection) {

		dbService.getFilteredData(selection,
				new AsyncCallback<Map<Integer, DataResultShared>>() {
					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(Map<Integer, DataResultShared> result) {
						resultTableInputDataList = result;
						refreshResultTable();
					}
				});
	}

	/**
	 * Initializes a new horizontal panel and returns it.
	 * 
	 * @return HorizontalPanel resultTable
	 */
	private HorizontalPanel initializeResultTable() {
		resultTablePanel = new HorizontalPanel();
		refreshResultTable();

		return resultTablePanel;
	}

	/**
	 * Clears the table and loads a new one with the refreshed 
	 * data.
	 * 
	 * @Pre ResultTable must be implemented
	 */
	private void refreshResultTable() {
		resultTablePanel.clear();
		Runnable onLoadCallback = new Runnable() {
			public void run() {
				ResultTable resultTable = new ResultTable(
						resultTableInputDataList);
				resultTablePanel.add(resultTable.getResultTable());
				resultTable.getResultTable().draw(resultTable.getDataTable(),
						resultTable.getOptions());
			}
		};
		VisualizationUtils.loadVisualizationApi(onLoadCallback, Table.PACKAGE);
	}

	/**
	 * Initializes a worldMapPanel where the worldmap will be sent to
	 * 
	 * @return SimpleLayoutPanel worldMapPanel
	 */
	private SimpleLayoutPanel initializeWorldMap() {
		worldMapPanel = new SimpleLayoutPanel();
		worldMapPanel.setSize(TABPANELWIDTH, TABPANELHEIGHT);
		refreshWorldMap();

		return worldMapPanel;
	}

	/**
	 * Refreshes the worldmap after new selection was chosen.
	 * Shows a new map with the new given selection.
	 * 
	 * @Pre WorldMap must be implemented
	 */
	private void refreshWorldMap() {
		Runnable onLoadCallback = new Runnable() {
			public void run() {
				WorldMap map = new WorldMap(worldMapInputDataList,
						TABPANELWIDTH, TABPANELHEIGHT);
				worldMapPanel.setWidget(map.getWorldMap());
				map.getWorldMap().draw(map.getDataTable(), map.getOptions());
			}
		};
		VisualizationUtils.loadVisualizationApi(onLoadCallback, GeoMap.PACKAGE);
	}
}



