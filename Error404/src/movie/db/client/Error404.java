package movie.db.client;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.event.EventListenerList;

import movie.db.shared.DataResultAggregated;
import movie.db.shared.DataResultShared;
import movie.db.shared.Duration;
import movie.db.shared.Selection;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.GeoMap;
import com.google.gwt.visualization.client.visualizations.Table;
import com.google.gwt.widgetideas.client.SliderBar;
import com.google.gwt.widgetideas.client.SliderBar.LabelFormatter;

/**
 * This is the Main Class, containing the entry point method and managing all
 * the GUI components.
 */
@SuppressWarnings("deprecation")
public class Error404 implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		initializePanels();
		initializeTimeBar();
	}

	/* Class Variables */
	private HorizontalPanel mainPanel = new HorizontalPanel();
	private SimpleLayoutPanel worldMapPanel;
	private HorizontalPanel resultTablePanel;
	
	private VerticalPanel rootPanel = new VerticalPanel();
	private VerticalPanel advertisementPanel1 = new VerticalPanel();
	private VerticalPanel advertisementPanel2 = new VerticalPanel();
	private DisclosurePanel disclosureSourcePanel = new DisclosurePanel("Source",false);
	
	private ListBox genreListBox = new ListBox();
	private ListBox countryListBox = new ListBox();
	private ListBox langListBox = new ListBox();
	private ListBox durationListBox = new ListBox();
	private TextBox tbYearWorldmap = new TextBox();
	private TextBox tbNameTable = new TextBox();
	private TextBox tbYearTable = new TextBox();
	private ArrayList<DataResultAggregated> worldMapInputDataList = new ArrayList<DataResultAggregated>();
	private TabPanel tabPanel = new TabPanel();
	private Map<Integer, DataResultShared> resultTableInputDataList = new HashMap<Integer, DataResultShared>();

	private SliderBar timeBar = new SliderBar(YEAR_OLDEST_MOVIE, CURRENT_YEAR,
			new LabelFormatter() {
				public String formatLabel(SliderBar slider, double value) {
					return (int) (10 * value) / 10 + "";
				}
			});

	private final static double YEAR_OLDEST_MOVIE = 1888;
	private final static double CURRENT_YEAR = 2015; 
	private final static String TABPANELHEIGHT = "540px";
	private final static String TABPANELWIDTH = "1400px";
	private final static String MAINPANELHEIGHT = "690px";

	private final static Logger logger = Logger.getLogger("Error404");
	private VerticalPanel worldMapVP;
	
	/**
	 * Initializes most of the important panels and buttons by calling smaller
	 * methods in its body. These methods initialize parts of the mainPanel as
	 * well. Buttons, panels etc. are visible and working at the end of the
	 * method.
	 * @author Christoph Weber
	 * @Pre EntryPoint class must be loaded correctly
	 */
	private void initializePanels() {
		//mainPanel.setSize("100vw", "82vh");
		//rootPanel.setSize("100vw", "82vh");
		//mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		rootPanel.setSpacing(5);

		//mainPanel.add(introductionText);

		// VerticalPanel selectionPanel = new VerticalPanel();

		VerticalPanel selectionCriteriaTable = new VerticalPanel();
		FlexTable worldMapCriteriaTable = new FlexTable();


		initializeSelectionListBox(genreListBox, "genre");
		initializeSelectionListBox(countryListBox, "country");
		initializeSelectionListBox(langListBox, "language");
		initializeDurationSelListBox();

		initializeWorldMapCriteriaTable(worldMapCriteriaTable);
		worldMapVP = new VerticalPanel();
		worldMapVP.add(worldMapCriteriaTable);
		initializeWorldMapAndTimeBar();
		
		
		initializeSelectionCriteriaTable(selectionCriteriaTable);
		VerticalPanel tableVP = new VerticalPanel();
		tableVP.add(selectionCriteriaTable);
		tableVP.add(initializeResultTable());

		//tabPanel.setSize(TABPANELWIDTH, TABPANELHEIGHT);
		tabPanel.setSize("1415px", TABPANELHEIGHT);
		//tabPanel.setWidth("1215px");
		tabPanel.add(worldMapVP, "Worldmap");
		tabPanel.add(tableVP, "Table");
		tabPanel.selectTab(0);	
		
		Label lb = new Label();
		lb.setText("Movie Database by Error 404");
		lb.addStyleName("customHeader");
		initializeAdvertPanels();
		
		rootPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		rootPanel.add(lb);
		
		String introString = "Welcome to our Movie Database!\n"
				+ "Enter a year and see the number of movies in the different countries in the \"Worldmap\" panel.\n"
				+ "Switch to the tab \"Table\" and perform more detailed research based on the offered criteria.";
		Label introductionText = new HTML(new SafeHtmlBuilder()
				.appendEscapedLines(introString).toSafeHtml());		
		rootPanel.add(introductionText);
		
		mainPanel.setHeight(MAINPANELHEIGHT);
		mainPanel.add(advertisementPanel1);		
		mainPanel.add(tabPanel);
		mainPanel.add(advertisementPanel2);
		rootPanel.add(mainPanel);
		
		initializeSourcePanel();
		rootPanel.add(disclosureSourcePanel);
		/*rootPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		rootPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		rootPanel.add(lb,DockPanel.NORTH);
		rootPanel.add(mainPanel,DockPanel.CENTER);
		rootPanel.add(advertisementPanel1,DockPanel.WEST);
		rootPanel.add(advertisementPanel2,DockPanel.EAST);
		
		rootPanel.addStyleName("customCenter");*/
		// Associate the Main panel with the HTML host page.
		RootPanel.get("mainPage").add(rootPanel);
	}

	/**
	 * Initializes a table for the selection part of the website. Adds all
	 * selection criteria boxes to a flextable on the main page and makes it
	 * visible.
	 * 	 
	 * @author Christoph Weber
	 * @Pre selectionCriteria table must be implemented
	 * @param selectionCriteriaTable
	 */
	private void initializeSelectionCriteriaTable(
			VerticalPanel selectionCriteriaTable) {
		Button showAsButton = new Button();
		showAsButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				showButtonClick();
			}
		});
		Button cleanSelectionButton = new Button();
		cleanSelectionButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cleanSelectionClick();
			}
		});
		showAsButton.setText("Show");

		VerticalPanel genreVP = new VerticalPanel();
		Label genreLabel = new Label("Genre:");
		genreLabel.setHeight("2em");
		genreVP.add(genreLabel);
		genreVP.add(genreListBox);

		VerticalPanel countryVP = new VerticalPanel();
		Label countryLabel = new Label("Country:");
		countryLabel.setHeight("2em");
		countryVP.add(countryLabel);
		countryVP.add(countryListBox);

		VerticalPanel languageVP = new VerticalPanel();
		Label languageLabel = new Label("Language:");
		languageLabel.setHeight("2em");
		languageVP.add(languageLabel);
		languageVP.add(langListBox);

		VerticalPanel durationVP = new VerticalPanel();
		Label durationLabel = new Label("Duration:");
		durationLabel.setHeight("2em");
		durationVP.add(durationLabel);
		durationVP.add(durationListBox);

		VerticalPanel nameVP = new VerticalPanel();
		Label nameLabel = new Label(
				"Name: (use % as wildcard, e.g. %of the Rings%)");
		nameLabel.setHeight("2em");
		nameVP.add(nameLabel);
		tbNameTable.setWidth("39em");
		nameVP.add(tbNameTable);

		VerticalPanel yearVP = new VerticalPanel();
		yearVP.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		Label yearLabel = new Label("Year:");
		yearLabel.setHeight("2em");
		yearVP.add(yearLabel);
		tbYearTable.setWidth("4em");
		yearVP.add(tbYearTable);

		HorizontalPanel selectionHorizontal1 = new HorizontalPanel();
		selectionHorizontal1.setSpacing(5);
		selectionHorizontal1.add(nameVP);
		selectionHorizontal1.add(yearVP);

		HorizontalPanel selectionHorizontal2 = new HorizontalPanel();
		selectionHorizontal2.setSpacing(5);
		selectionHorizontal2.add(genreVP);
		selectionHorizontal2.add(countryVP);
		selectionHorizontal2.add(languageVP);
		selectionHorizontal2.add(durationVP);

		HorizontalPanel selectionHorizontal3 = new HorizontalPanel();
		selectionHorizontal3
				.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		selectionHorizontal3.setSpacing(5);
		showAsButton.setWidth("8em");
		selectionHorizontal3.add(showAsButton);
		cleanSelectionButton.setText("Clean");
		cleanSelectionButton.setWidth("8em");
		selectionHorizontal3.add(cleanSelectionButton);

		selectionCriteriaTable.setSpacing(4);
		selectionCriteriaTable.add(selectionHorizontal1);
		selectionCriteriaTable.add(selectionHorizontal2);
		selectionCriteriaTable.add(selectionHorizontal3);

	}

	/**
	 * Initializes a worldmap criteria table visible on the main page. Adds a
	 * textbox and three buttons to the flextable. The flextable is localized
	 * underneath selection part of the website.
	 * 
	 * @author Christoph Weber
	 * @Pre Parameters have to be implemented
	 * @param worldMapCriteriaTable
	 */
	private void initializeWorldMapCriteriaTable(
			FlexTable worldMapCriteriaTable) {
		worldMapCriteriaTable.setText(0, 0, "Year:");
		tbYearWorldmap.setWidth("4em");
		worldMapCriteriaTable.setWidget(1, 0, tbYearWorldmap);
		
		Button showMapButton = new Button();
		Button cleanWorldMapButton = new Button();
		showMapButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				showWorldMapClick();
			}
		});
		showMapButton.setText("Show Year on Worldmap");
		worldMapCriteriaTable.setWidget(1, 1, showMapButton);
		
		cleanWorldMapButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cleanWorldMapClick();
			}
		});
		cleanWorldMapButton.setText("Clean Worldmap");
	
		worldMapCriteriaTable.setWidget(1, 2, cleanWorldMapButton);
	}

	/**
	 * Initializes one selection Box with the entries from the database by
	 * making an async call to Query.java class
	 * 
	 * @author Christoph Weber
	 * @Pre data in database and connection
	 * @param selectionListBox
	 * @param column
	 * @param columnId
	 */
	private void initializeSelectionListBox(final ListBox selectionListBox,
			String column) {
		dbService.getColumnEntries(column,
				new AsyncCallback<ArrayList<String>>() {
					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(ArrayList<String> result) {
						try {
							for (String entry : result) {
								selectionListBox.addItem(entry);
							}
							selectionListBox.setVisibleItemCount(5);
							selectionListBox.setMultipleSelect(true);
							selectionListBox.setItemSelected(0, false); // first
																		// item
																		// is
																		// selected
																		// by
																		// default
						} catch (Exception ex) {
							logger.log(Level.WARNING, ex.toString());
						}
					}
				});
	}

	/**
	 * Initializes one selection Box with all the Entries in the Enum "Duration"
	 * 
	 * @author Christoph Weber
	 * @Pre Enum is not empty
	 */
	private void initializeDurationSelListBox() {
		for (Duration duration : Duration.values()) {
			if (!duration.getName().equals("NA")) {
				durationListBox.addItem(duration.getName());
			}
		}
		durationListBox.setVisibleItemCount(5);
		durationListBox.setMultipleSelect(true);
		durationListBox.setItemSelected(0, false);
	}

	/**
	 * After the click on the showMapButton this method is called. It calls the
	 * method fillWorldmap and selects the tab with the worldmap on it.
	 * 
	 * @author Patrick Muntwyler
	 * @Pre showMapButton must be implemented and clicked
	 */
	private final void showWorldMapClick() {
		//fillWorldmap();
		try {
			int selectedYear = Integer.parseInt(tbYearWorldmap.getText());
			if (selectedYear >= (int) YEAR_OLDEST_MOVIE
					&& selectedYear <= (int) CURRENT_YEAR) {

				timeBar.setCurrentValue(selectedYear);
				// if the given year is out of range
			} else {
				Window.alert("Please insert a valid number ("
						+ (int) YEAR_OLDEST_MOVIE + "-" + (int) CURRENT_YEAR
						+ ")");
			}
		} catch (Exception ex) {
			Window.alert("Please insert a valid number ("
					+ (int) YEAR_OLDEST_MOVIE + "-" + (int) CURRENT_YEAR + ")");
		}

		tabPanel.selectTab(0);
	}

	/**
	 * Cleans the selection of the selection boards and sets it back to the
	 * standard value. Will be called after the clickhandler event of the
	 * cleanSelectionButton.
	 * 
	 * @author Christoph Weber
	 * @Pre cleanSelectionButton must be implemented and clicked
	 * @post no item of the selection is selected
	 */
	private final void cleanSelectionClick() {
		tbNameTable.setText("");
		tbYearTable.setText("");
		
		/*
		 * First only one selection is allowed by now then selects the first
		 * item, first item will be removed from the selection multiple
		 * selection is allowed again.
		 */
		genreListBox.setMultipleSelect(false);
		genreListBox.setItemSelected(0, true);
		genreListBox.setItemSelected(0, false);
		genreListBox.setMultipleSelect(true);

		/*
		 * First only one selection is allowed by now then selects the first
		 * item, first item will be removed from the selection multiple
		 * selection is allowed again.
		 */
		countryListBox.setMultipleSelect(false);
		countryListBox.setItemSelected(0, true);
		countryListBox.setItemSelected(0, false);
		countryListBox.setMultipleSelect(true);

		/*
		 * First only one selection is allowed by now then selects the first
		 * item, first item will be removed from the selection multiple
		 * selection is allowed again.
		 */
		langListBox.setMultipleSelect(false);
		langListBox.setItemSelected(0, true);
		langListBox.setItemSelected(0, false);
		langListBox.setMultipleSelect(true);

		/*
		 * First only one selection is allowed by now then selects the first
		 * item, first item will be removed from the selection multiple
		 * selection is allowed again.
		 */
		durationListBox.setMultipleSelect(false);
		durationListBox.setItemSelected(0, true);
		durationListBox.setItemSelected(0, false);
		durationListBox.setMultipleSelect(true);

		resultTableInputDataList.clear();
		refreshResultTable();
	}

	/**
	 * Cleans the worldmap from entries and shows an empty map again. Will be
	 * called after the cleanWorldMapButton is clicked.
	 * 
	 * @author Christoph Weber
	 * @Pre worldmap must be implemented
	 * @post worldmap must be empty
	 */
	private final void cleanWorldMapClick() {
		worldMapInputDataList.clear();
		refreshWorldMap();
	}

	/**
	 * Takes the selection of the selected genre, countries and languages and
	 * builds an table in a separated tab. Only shows the filtered part of all
	 * movies in the table. Will be executed after clicking the showAsButton.
	 * 
	 * @author Christoph Weber
	 * @Pre showAsButton Implemented and connection to database exists
	 */
	private final void showButtonClick() {
		// gets all items selected in the genre box
		ArrayList<String> selectedGenres = new ArrayList<String>();
		if (genreListBox.getSelectedIndex() != -1) {
			for (int i = 0; i < genreListBox.getItemCount(); i++) {
				if (genreListBox.isItemSelected(i)) {
					selectedGenres.add(genreListBox.getItemText(i));
				}
			}
		}

		// gets all items selected in the countries
		ArrayList<String> selectedCountries = new ArrayList<String>();
		if (countryListBox.getSelectedIndex() != -1) {
			for (int i = 0; i < countryListBox.getItemCount(); i++) {
				if (countryListBox.isItemSelected(i)) {
					selectedCountries.add(countryListBox.getItemText(i));
				}
			}
		}

		// gets all items selected in the language box
		ArrayList<String> selectedLanguages = new ArrayList<String>();
		if (langListBox.getSelectedIndex() != -1) {
			for (int i = 0; i < langListBox.getItemCount(); i++) {
				if (langListBox.isItemSelected(i)) {
					selectedLanguages.add(langListBox.getItemText(i));
				}
			}
		}

		// gets all items selected in the duration box
		ArrayList<String> selectedDurations = new ArrayList<String>();
		if (durationListBox.getSelectedIndex() != -1) {
			for (int i = 0; i < durationListBox.getItemCount(); i++) {
				if (durationListBox.isItemSelected(i)) {
					selectedDurations.add(durationListBox.getItemText(i));
				}
			}
		}

		Selection selection = new Selection(); // makes a new selection object
		selection.setSelectedMovieName(tbNameTable.getText());
		
		if (tbYearTable.getText().length() > 0) {
			try {
				int selectedYear = Integer.parseInt(tbYearTable.getText());
				if (selectedYear >= (int) YEAR_OLDEST_MOVIE
						&& selectedYear <= (int) CURRENT_YEAR) {
					selection.setSelectedYear(selectedYear);
					// if the given year is out of range
				} else {
					Window.alert("Please insert a valid number ("
							+ (int) YEAR_OLDEST_MOVIE + "-"
							+ (int) CURRENT_YEAR + ")");
				}
			} catch (Exception ex) {
				Window.alert("Please insert a valid number ("
						+ (int) YEAR_OLDEST_MOVIE + "-" + (int) CURRENT_YEAR
						+ ")");
			}
		}
		selection.setSelectedCountries(selectedCountries);
		selection.setSelectedLanguages(selectedLanguages);
		selection.setSelectedGenres(selectedGenres);
		selection.setSelectedDuration(selectedDurations);

		// gets all with the selected parameters and gives them back in a table
		// format
		showResults(selection);
		tabPanel.selectTab(1);

	}

	private MyServiceAsync dbService = (MyServiceAsync) GWT
			.create(MyService.class);

	/**
	 * Called by the showWorldMapClick method, this method fills the countries
	 * of the worldmap by calling the async method getWorldMapData, which gets
	 * the data from the database, filters the movies per year and gives the
	 * back.
	 * 
	 * @author Patrick Muntwyler
	 * @Pre database connection
	 * @post worldmap filled
	 */
	private void fillWorldmap() {
		try {
			int selectedYear = Integer.parseInt(tbYearWorldmap.getText());
			if (selectedYear >= (int) YEAR_OLDEST_MOVIE
					&& selectedYear <= (int) CURRENT_YEAR) {

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

				// if the given year is out of range
			} else {
				Window.alert("Please insert a valid number ("
						+ (int) YEAR_OLDEST_MOVIE + "-" + (int) CURRENT_YEAR
						+ ")");
			}
		} catch (Exception ex) {
			Window.alert("Please insert a valid number ("
					+ (int) YEAR_OLDEST_MOVIE + "-" + (int) CURRENT_YEAR + ")");
		}
	}

	/**
	 * Gets the data of the selection, searches the database for the given input
	 * and returns a table with the selected data.
	 * 
	 * @author Christoph Weber
	 * @Pre connection to database with data in it
	 * @param selection
	 * @post table with output printed
	 */
	private void showResults(Selection selection) {

		dbService.getFilteredData(selection,
				new AsyncCallback<Map<Integer, DataResultShared>>() {
					@Override
					public void onFailure(Throwable caught) {
						logger.log(Level.SEVERE, caught.getMessage());

						for (StackTraceElement t : caught.getStackTrace()) {
							logger.log(Level.SEVERE, t.toString());
						}
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
	 * @author Christoph Weber
	 * @return HorizontalPanel resultTable
	 */
	private HorizontalPanel initializeResultTable() {
		resultTablePanel = new HorizontalPanel();
		refreshResultTable();

		return resultTablePanel;
	}

	/**
	 * Clears the table and loads a new one with the refreshed data.
	 * @author Patrick Muntwyler
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
	 * Refreshes the worldmap after new selection was chosen. Shows a new map
	 * with the new given selection.
	 * 
	 * @author Patrick Muntwyler
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
	
	/**
	 * Creates a new Timebar object, adds a change listener to detect changes made on the timebar
	 * 
	 * @author Patrick Muntwyler
	 * @Pre WorldMap must be implemented
	 */
	private void initializeTimeBar() {
		timeBar.setStepSize(1.0);
		timeBar.setCurrentValue(2015.0);
		timeBar.setNumTicks((int) (CURRENT_YEAR - YEAR_OLDEST_MOVIE));
		timeBar.setNumLabels(13);
		timeBar.addChangeListener(new ChangeListener() {
			public void onChange(Widget sender) {				
				tbYearWorldmap.setText((int) timeBar.getCurrentValue() + "");
				fillWorldmap();
			}
		});
		worldMapVP.add(timeBar);
	}
 
	/**
	 * Creates a new Timebar object, adds a change listener to detect changes made on the timebar
	 * 
	 * @author Christoph Weber
	 * @pre Mainpanel was initialized
	 * @post Worldmap and Timebar were initialized and added to the mainpanel
	 */
	private void initializeWorldMapAndTimeBar(){
		worldMapPanel = new SimpleLayoutPanel();
		worldMapPanel.setSize(TABPANELWIDTH, TABPANELHEIGHT);
		Runnable onLoadCallback = new Runnable() {
			public void run() {
				WorldMap map = new WorldMap(worldMapInputDataList,
						TABPANELWIDTH, TABPANELHEIGHT);
				worldMapPanel.setWidget(map.getWorldMap());
				map.getWorldMap().draw(map.getDataTable(), map.getOptions());
				worldMapVP.add(worldMapPanel);
				initializeTimeBar();
			}
		};
		VisualizationUtils.loadVisualizationApi(onLoadCallback, GeoMap.PACKAGE);

	}
	
	/**
	 * Initializes the two advertPanels and adds pictures to it.
	 * The Pictures have all the same sizes and are saved under the images
	 * folder in war.
	 * 
	 * @author David Lay 
	 * @Pre advertisement panels are implemented
	 * @post pictures shown on the website 
	 */
	private void initializeAdvertPanels(){		
		//initializes new images
		Image img1 = new Image("Images/header_advertise_hor.jpg");
		img1.setHeight(MAINPANELHEIGHT);
		Image img2 = new Image("Images/header_advertise_hor.jpg");		
		img2.setHeight(MAINPANELHEIGHT);

		//adds the images to the different panels
		advertisementPanel1.setWidth("5vw");
		advertisementPanel1.add(img1);
		advertisementPanel2.setWidth("5vw");
		advertisementPanel2.add(img2);
	}
	
	/**
	 * Initializes the source panel and adds an anchor as well as a new label to it.
	 * 
	 * @author David Lay 
	 * @Pre SourcePanel must be implemented
	 * @post anchor sends to creativecommons website and source panel can
	 * 		 be opened and closed at any time
	 */
	private void initializeSourcePanel(){
		VerticalPanel sourcePanel = new VerticalPanel();
		
		//new anchor to the creative commons website
		Anchor anchor = new Anchor("Commons Attribution-ShareAlike License",
				"http://creativecommons.org/licenses/by-sa/4.0/");
		
		//opens link in a new tab
		anchor.setTarget("_blank");
		
		// adds simple text to the source panel
		sourcePanel.add(new Label("Source: David Bamman, Brendan O'Connor and Noah Smith, "
				+ "\"Learning Latent Personas of Film Characters,\" in: Proceedings "
				+ "of the Annual Meeting of the Association for Computational Linguistics (ACL 2013), Sofia, Bulgaria, August 2013."));
		
		sourcePanel.add(anchor);		
		disclosureSourcePanel.add(sourcePanel);
		disclosureSourcePanel.setOpen(true);
	}
}
