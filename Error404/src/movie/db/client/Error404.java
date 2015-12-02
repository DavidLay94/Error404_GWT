package movie.db.client;

import java.io.Console;
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
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
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
import com.google.gwt.user.client.ui.RadioButton;
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
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart;
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
	private SimpleLayoutPanel pieChartPanel;
	private SimpleLayoutPanel columnChartPanel;
	
	private VerticalPanel rootPanel = new VerticalPanel();
	private VerticalPanel advertisementPanel1 = new VerticalPanel();
	private VerticalPanel advertisementPanel2 = new VerticalPanel();
	private DisclosurePanel disclosureSourcePanel = new DisclosurePanel("Source", false);

	private ListBox genreListBox = new ListBox();
	private ListBox countryListBox = new ListBox();
	private ListBox langListBox = new ListBox();
	private ListBox durationListBox = new ListBox();
	
	private ListBox countryCCListBox = new ListBox();
	private ListBox genreCCListBox = new ListBox();
	
	private TextBox tbYearWorldmap = new TextBox();
	private TextBox tbNameTable = new TextBox();
	private TextBox tbYearTable = new TextBox();
	
	private TextBox tbYearPieChart = new TextBox();
	private TextBox tbYearFromColumnChart = new TextBox();
	private TextBox tbYearToColumnChart = new TextBox();
	
	private ArrayList<DataResultAggregated> pieChartInputDataList = new ArrayList<DataResultAggregated>();
	///
	private Map<Integer, Integer> columnChartInputMap = new HashMap<Integer, Integer>();
	private	int selectedYearFromColumnChart = 0;
	private int selectedYearToColumnChart = 0;
	private String selectedCountryColumnChart;
	private String selectedGenreColumnChart;
	
	private ArrayList<DataResultAggregated> worldMapInputDataList = new ArrayList<DataResultAggregated>();
	private int selectedYear;
	private Map<String, Integer> worldMapInputDataListPopulation = new HashMap<String, Integer>();
	private TabPanel tabPanel = new TabPanel();
	private Map<Integer, DataResultShared> resultTableInputDataList = new HashMap<Integer, DataResultShared>();
	private RadioButton absoluteRB;
	private RadioButton percapitaRB;
	private double YEAR_OLDEST_MOVIE = 1888;
	private double CURRENT_YEAR = 2015;
	private SliderBar timeBar = new SliderBar(YEAR_OLDEST_MOVIE, CURRENT_YEAR, new LabelFormatter() {
		public String formatLabel(SliderBar slider, double value) {
			return (int) (10 * value) / 10 + "";
		}
	});

	// private final static double YEAR_OLDEST_MOVIE = 1888;
	// private final static double CURRENT_YEAR = 2015;
	private final static String TABPANELHEIGHT = "540px";
	private final static String TABPANELWIDTH = "100%";
	private final static String MAINPANELHEIGHT = "690px";

	private final static Logger logger = Logger.getLogger("Error404");
	private VerticalPanel worldMapVP;
	private VerticalPanel pieChartVP;
	private VerticalPanel columnChartVP;
	
	/**
	 * Initializes most of the important panels and buttons by calling smaller
	 * methods in its body. These methods initialize parts of the mainPanel as
	 * well. Buttons, panels etc. are visible and working at the end of the
	 * method.
	 * 
	 * @author Christoph Weber
	 * @Pre EntryPoint class must be loaded correctly
	 */
	private void initializePanels() {
		rootPanel.setSpacing(5);
		rootPanel.setSize("98.5vw", "95vh");
		mainPanel.setSize("95vw", "95vh");
		rootPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		FlexTable pieChartCriteriaTable = new FlexTable();
		FlexTable columnChartCriteriaTable = new FlexTable();
		VerticalPanel selectionCriteriaTable = new VerticalPanel();
		HorizontalPanel worldMapCriteriaTable = new HorizontalPanel();

		initializeMultiSelectionListBox(genreListBox, "genre");
		initializeMultiSelectionListBox(countryListBox, "country");
		initializeMultiSelectionListBox(langListBox, "language");
		initializeDurationSelListBox();

		initializeWorldMapCriteriaTable(worldMapCriteriaTable);
		worldMapVP = new VerticalPanel();
		worldMapVP.add(worldMapCriteriaTable);
		initializeWorldMapAndTimeBar();
		
		initializeSingleSelectionListBox(countryCCListBox, "country");
		initializeSingleSelectionListBox(genreCCListBox, "genre");
		
		initializePieChartCriteriaTable(pieChartCriteriaTable);
		pieChartVP = new VerticalPanel();
		pieChartVP.add(pieChartCriteriaTable);
		initializePieChart();

		///
		initializeColumnChartCriteriaTable(columnChartCriteriaTable);
		columnChartVP = new VerticalPanel();
		columnChartVP.add(columnChartCriteriaTable);
		initializeColumnChart();
		
		initializeSelectionCriteriaTable(selectionCriteriaTable);
		VerticalPanel tableVP = new VerticalPanel();
		tableVP.add(selectionCriteriaTable);
		tableVP.add(initializeResultTable());

		//fill tabpanel
		tabPanel.setSize("75vw", TABPANELHEIGHT);
		tabPanel.add(worldMapVP, "Worldmap");
		tabPanel.add(tableVP, "Table");		
		tabPanel.add(pieChartVP, "Pie Chart");
		tabPanel.add(columnChartVP, "Column Chart");
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
		Label introductionText = new HTML(new SafeHtmlBuilder().appendEscapedLines(introString).toSafeHtml());
		rootPanel.add(introductionText);
/*
		mainPanel.setHeight(MAINPANELHEIGHT);
		mainPanel.add(advertisementPanel1);
		mainPanel.add(tabPanel);
		mainPanel.add(advertisementPanel2);
		rootPanel.add(mainPanel);*/

		mainPanel.setHeight(MAINPANELHEIGHT);
		  mainPanel.add(advertisementPanel1);
		  mainPanel.setCellHorizontalAlignment(advertisementPanel1, HasHorizontalAlignment.ALIGN_RIGHT);
		  mainPanel.add(tabPanel);
		  mainPanel.add(advertisementPanel2);
		  mainPanel.setCellHorizontalAlignment(advertisementPanel2, HasHorizontalAlignment.ALIGN_LEFT);
		  rootPanel.add(mainPanel);
		
		initializeSourcePanel();
		rootPanel.add(disclosureSourcePanel);
		/*
		 * rootPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		 * rootPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		 * rootPanel.add(lb,DockPanel.NORTH);
		 * rootPanel.add(mainPanel,DockPanel.CENTER);
		 * rootPanel.add(advertisementPanel1,DockPanel.WEST);
		 * rootPanel.add(advertisementPanel2,DockPanel.EAST);
		 * 
		 * rootPanel.addStyleName("customCenter");
		 */
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
	private void initializeSelectionCriteriaTable(VerticalPanel selectionCriteriaTable) {
		final Button showButton = new Button();
		showButton.addClickHandler(new ClickHandler() {
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
		showButton.setText("Show");

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
		Label nameLabel = new Label("Name:");
		nameLabel.setHeight("2em");
		nameVP.add(nameLabel);
		tbNameTable.setWidth("39em");
		tbNameTable.addKeyDownHandler(new KeyDownHandler() {

		    @Override
		    public void onKeyDown(KeyDownEvent event) {
		     if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
		    	 showButton.click();
		           }
		    }
		});
		nameVP.add(tbNameTable);

		VerticalPanel yearVP = new VerticalPanel();
		yearVP.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		Label yearLabel = new Label("Year:");
		yearLabel.setHeight("2em");
		yearVP.add(yearLabel);
		tbYearTable.setWidth("4em");
		tbYearTable.addKeyDownHandler(new KeyDownHandler() {

		    @Override
		    public void onKeyDown(KeyDownEvent event) {
		     if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
		    	 showButton.click();
		           }
		    }
		});
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
		selectionHorizontal3.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		selectionHorizontal3.setSpacing(5);
		showButton.setWidth("8em");
		selectionHorizontal3.add(showButton);
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
	private void initializeWorldMapCriteriaTable(HorizontalPanel worldMapCriteriaTable) {
		worldMapCriteriaTable.setWidth(TABPANELWIDTH);
		FlexTable selectionLeft = new FlexTable();
		worldMapCriteriaTable.add(selectionLeft);
		selectionLeft.setText(0, 0, "Year:");
		tbYearWorldmap.setWidth("4em");
		final Button showMapButton = new Button();
		tbYearWorldmap.addKeyDownHandler(new KeyDownHandler() {

		    @Override
		    public void onKeyDown(KeyDownEvent event) {
		     if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
		    	 showMapButton.click();
		           }
		    }
		});
		
		selectionLeft.setWidget(1, 0, tbYearWorldmap);

		Button cleanWorldMapButton = new Button();
		showMapButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				showWorldMapClick();
			}
		});
		showMapButton.setText("Show Year on Worldmap");
		selectionLeft.setWidget(1, 1, showMapButton);

		cleanWorldMapButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cleanWorldMapClick();
			}
		});
		cleanWorldMapButton.setText("Clean Worldmap");

		selectionLeft.setWidget(1, 2, cleanWorldMapButton);

		absoluteRB = new RadioButton("valueType", "absolute numbers");
		absoluteRB.addClickListener(new ClickListener() {

			@Override
			public void onClick(Widget sender) {
				YEAR_OLDEST_MOVIE = 1888;
				CURRENT_YEAR = 2015;
				timeBar.setMinValue(YEAR_OLDEST_MOVIE);
				timeBar.setMaxValue(CURRENT_YEAR);
				initializeTimeBar();
			}
		});
		/*
		 * timeBar.addChangeListener(new ChangeListener() { public void
		 * onChange(Widget sender) { tbYearWorldmap.setText((int)
		 * timeBar.getCurrentValue() + ""); fillWorldmap(); } });
		 */
		percapitaRB = new RadioButton("valueType", "per capita amount");
		percapitaRB.addClickListener(new ClickListener() {

			@Override
			public void onClick(Widget sender) {
				YEAR_OLDEST_MOVIE = 1960;
				CURRENT_YEAR = 2014;
				try {
					int selectedYear = Integer.parseInt(tbYearWorldmap.getText());

					if (selectedYear >= (int) YEAR_OLDEST_MOVIE && selectedYear <= (int) CURRENT_YEAR) {

						timeBar.setCurrentValue(selectedYear);
						// if the given year is out of range
					} else {
						timeBar.setCurrentValue(CURRENT_YEAR);
						//Window.alert("Please insert a valid number (" + (int) YEAR_OLDEST_MOVIE + "-" + (int) CURRENT_YEAR + ")");
					}
				} catch (Exception ex) {
					Window.alert("Please insert a valid number (" + (int) YEAR_OLDEST_MOVIE + "-" + (int) CURRENT_YEAR + ")");
				}
				timeBar.setMinValue(YEAR_OLDEST_MOVIE);
				timeBar.setMaxValue(CURRENT_YEAR);

				initializeTimeBar();
			}
		});
		absoluteRB.setValue(true);
		worldMapCriteriaTable.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		worldMapCriteriaTable.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		VerticalPanel selectionRight = new VerticalPanel();
		selectionRight.add(absoluteRB);
		selectionRight.add(percapitaRB);
		worldMapCriteriaTable.add(selectionRight);
	}
	
	/** Initializes a pie chart criteria table visible on the main page. Adds a
	 * textbox and a button to the flextable. The flextable is localized
	 * underneath selection part of the website.
	 * 
	 * @author Patrick Muntwyler
	 * @Pre Parameters have to be implemented
	 * @param pieChartCriteriaTable
	 */
	private void initializePieChartCriteriaTable(FlexTable pieChartCriteriaTable) {
		final Button showPieChartButton = new Button();
		Button cleanPieChartButton = new Button();
		pieChartCriteriaTable.setText(0, 0, "Year:");
		tbYearPieChart.setWidth("4em");
		tbYearPieChart.addKeyDownHandler(new KeyDownHandler() {

		    @Override
		    public void onKeyDown(KeyDownEvent event) {
		     if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
		    	 showPieChartButton.click();
		           }
		    }
		});
		pieChartCriteriaTable.setWidget(1, 0, tbYearPieChart);



		showPieChartButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				drawPieChart();
			}
		});
		showPieChartButton.setText("Draw Pie Chart");
		pieChartCriteriaTable.setWidget(1, 1, showPieChartButton);
		
		cleanPieChartButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				pieChartInputDataList.clear();
				refreshPieChart();
			}
		});
		cleanPieChartButton.setText("Clean Pie Chart");
		pieChartCriteriaTable.setWidget(1, 2, cleanPieChartButton);
		
	}
	
	/**
	 * Initializes a column chart criteria table visible on the main page. Adds
	 * two listboxes, two textboxes and a button to the flextable. The flextable
	 * is localized underneath selection part of the website.
	 * 
	 * @author Patrick Muntwyler
	 * @Pre Parameters have to be implemented
	 * @param pieChartCriteriaTable
	 */
	private void initializeColumnChartCriteriaTable(FlexTable columnChartCriteriaTable) {
		final Button showColumnChartButton = new Button();
		Button cleanColumnChartButton = new Button();
		
		columnChartCriteriaTable.setText(0, 0, "Country:");
		columnChartCriteriaTable.setWidget(1, 0, countryCCListBox);
		columnChartCriteriaTable.setText(0, 1, "Genre:");
		columnChartCriteriaTable.setWidget(1, 1, genreCCListBox);

		columnChartCriteriaTable.setText(0, 2, "From Year:");
		tbYearFromColumnChart.setWidth("5em");
		tbYearFromColumnChart.addKeyDownHandler(new KeyDownHandler() {

		    @Override
		    public void onKeyDown(KeyDownEvent event) {
		     if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
		    	 showColumnChartButton.click();
		           }
		    }
		});
		columnChartCriteriaTable.setWidget(1, 2, tbYearFromColumnChart);
		columnChartCriteriaTable.getCellFormatter().setVerticalAlignment(1, 2, HasVerticalAlignment.ALIGN_TOP);

		columnChartCriteriaTable.setText(0, 3, "To Year:");
		tbYearToColumnChart.setWidth("5em");
		tbYearToColumnChart.addKeyDownHandler(new KeyDownHandler() {

		    @Override
		    public void onKeyDown(KeyDownEvent event) {
		     if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
		    	 showColumnChartButton.click();
		           }
		    }
		});
		columnChartCriteriaTable.setWidget(1, 3, tbYearToColumnChart);
		columnChartCriteriaTable.getCellFormatter().setVerticalAlignment(1, 3, HasVerticalAlignment.ALIGN_TOP);


		showColumnChartButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				drawColumnChart();
			}
		});
		showColumnChartButton.setText("Draw Column Chart");
		columnChartCriteriaTable.setWidget(1, 4, showColumnChartButton);
		columnChartCriteriaTable.getCellFormatter().setVerticalAlignment(1, 4, HasVerticalAlignment.ALIGN_TOP);
		
		cleanColumnChartButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				
				 // unselects all selected items of the ListBoxes
				 
				genreCCListBox.setItemSelected(0, true);
				genreCCListBox.setItemSelected(0, false);
				countryCCListBox.setItemSelected(0, true);
				countryCCListBox.setItemSelected(0, false);
				
				//cleans the TextBox
				tbYearFromColumnChart.setText("");
				tbYearToColumnChart.setText("");
				
				//removes ColumnChart
				columnChartPanel.getWidget().removeFromParent();
			}
		});
		cleanColumnChartButton.setText("Clean Column Chart");
		columnChartCriteriaTable.setWidget(1, 5, cleanColumnChartButton);
		columnChartCriteriaTable.getCellFormatter().setVerticalAlignment(1, 5, HasVerticalAlignment.ALIGN_TOP);
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
	private void initializeMultiSelectionListBox(final ListBox selectionListBox, String column) {
		dbService.getColumnEntries(column, new AsyncCallback<ArrayList<String>>() {
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
					selectionListBox.setItemSelected(0, false); // first item is
																// selected by
																// default
				} catch (Exception ex) {
					logger.log(Level.WARNING, ex.toString());
				}
			}
		});
	}
	
	/**
	 * Initializes one selection Box with the entries from the database by
	 * making an async call to Query.java class
	 * 
	 * @author Patrick Muntwyler
	 * @Pre data in database and connection
	 * @param selectionListBox
	 * @param column
	 * @param columnId
	 */
	private void initializeSingleSelectionListBox(final ListBox selectionListBox, String column) {
		dbService.getColumnEntries(column, new AsyncCallback<ArrayList<String>>() {
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
					selectionListBox.setMultipleSelect(false);
					selectionListBox.setItemSelected(0, false); // first item is
																// selected by
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
		// fillWorldmap();
		try {
			int selectedYear = Integer.parseInt(tbYearWorldmap.getText());
			if (selectedYear >= (int) YEAR_OLDEST_MOVIE && selectedYear <= (int) CURRENT_YEAR) {

				timeBar.setCurrentValue(selectedYear);
				// if the given year is out of range
			} else {
				Window.alert("Please insert a valid number (" + (int) YEAR_OLDEST_MOVIE + "-" + (int) CURRENT_YEAR + ")");
			}
		} catch (Exception ex) {
			Window.alert("Please insert a valid number (" + (int) YEAR_OLDEST_MOVIE + "-" + (int) CURRENT_YEAR + ")");
		}

		tabPanel.selectTab(0);
	}
	
	/**
	 * Unselects every selection of the ListBox
	 * @author Patrick Muntwyler
	 * @param listBox
	 * @post ListBox has no selected items
	 */
	


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
				if (selectedYear >= (int) YEAR_OLDEST_MOVIE && selectedYear <= (int) CURRENT_YEAR) {
					selection.setSelectedYear(selectedYear);
					// if the given year is out of range
				} else {
					Window.alert("Please insert a valid number (" + (int) YEAR_OLDEST_MOVIE + "-" + (int) CURRENT_YEAR + ")");
				}
			} catch (Exception ex) {
				Window.alert("Please insert a valid number (" + (int) YEAR_OLDEST_MOVIE + "-" + (int) CURRENT_YEAR + ")");
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

	private MyServiceAsync dbService = (MyServiceAsync) GWT.create(MyService.class);

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
			selectedYear = Integer.parseInt(tbYearWorldmap.getText());
			if (selectedYear >= (int) YEAR_OLDEST_MOVIE && selectedYear <= (int) CURRENT_YEAR) {

				dbService.getWorldMapData(selectedYear, new AsyncCallback<ArrayList<DataResultAggregated>>() {
					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(ArrayList<DataResultAggregated> result) {
						// refreshWorldMap(result);
						worldMapInputDataList = result;

						if (percapitaRB.getValue()) {
							// worldMapInputDataListPopulation

							dbService.getPopulation(selectedYear, new AsyncCallback<Map<String, Integer>>() {
								@Override
								public void onFailure(Throwable caught) {
									logger.log(Level.WARNING, "somefailure");
								}

								@Override
								public void onSuccess(Map<String, Integer> result) {
									// refreshWorldMap(result);
									worldMapInputDataListPopulation = result;
									/*
									 * for (Map.Entry<String, Integer> kvp :
									 * result.entrySet()) {
									 * logger.log(Level.SEVERE, kvp.getKey() +
									 * ": " + kvp.getValue()); }
									 */

									logger.log(Level.WARNING, "somesuccess");
									refreshWorldMap();
								}
							});
						} else {
							refreshWorldMap();
						}
					}
				});
				// if the given year is out of range
			} else {
				Window.alert("Please insert a valid number (" + (int) YEAR_OLDEST_MOVIE + "-" + (int) CURRENT_YEAR + ")");
			}
		} catch (Exception ex) {
			Window.alert("Please insert a valid number (" + (int) YEAR_OLDEST_MOVIE + "-" + (int) CURRENT_YEAR + ")");
		}
	}

	/**
	 * this function takes the number entered in the textbox tbYearPieChart and
	 * generates a query to get the data from the database. Then it invokes the
	 * method refreshPieChart.
	 * 
	 * @author Patrick Muntwyler
	 * @pre mainpanel has been loaded
	 * @post the pie chart is drawn
	 */
	private void drawPieChart() {
		try {
			int selectedYear = Integer.parseInt(tbYearPieChart.getText());
			if (selectedYear >= (int) YEAR_OLDEST_MOVIE && selectedYear <= (int) CURRENT_YEAR) {

				dbService.getWorldMapData(selectedYear, new AsyncCallback<ArrayList<DataResultAggregated>>() {
					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(ArrayList<DataResultAggregated> result) {

						pieChartInputDataList = result;
						refreshPieChart();
					}
				});

				// if the given year is out of range
			} else {
				Window.alert(
						"Please insert a valid number (" + (int) YEAR_OLDEST_MOVIE + "-" + (int) CURRENT_YEAR + ")");
			}
		} catch (Exception ex) {
			Window.alert("Please insert a valid number (" + (int) YEAR_OLDEST_MOVIE + "-" + (int) CURRENT_YEAR + ")");
		}
	}
	
	/**
	 * This method takes the column chart criteria and generates a query to get the considered data from
	 * the database. Then it invokes the method refreshColumnChart.
	 * @author Patrick Muntwyler
	 */
	private void drawColumnChart() {

		try {
			this.selectedYearFromColumnChart = Integer.parseInt(tbYearFromColumnChart.getText());
			this.selectedYearToColumnChart = Integer.parseInt(tbYearToColumnChart.getText());
			
			//Year From must be equal or smaller than Year To!
			if (selectedYearToColumnChart < selectedYearFromColumnChart) {
				Window.alert("Year To can not be smaller than Year From!");
			} else {

				// a country must be selected
				if (countryCCListBox.getSelectedIndex() >= 0) {
					selectedCountryColumnChart = countryCCListBox.getItemText(countryCCListBox.getSelectedIndex());

					// if no genre is selected
					if (genreCCListBox.getSelectedIndex() == -1) {
						selectedGenreColumnChart = null;
					} else {
						selectedGenreColumnChart = genreCCListBox.getItemText(genreCCListBox.getSelectedIndex());
					}

					this.selectedYearFromColumnChart = Integer.parseInt(tbYearFromColumnChart.getText());
					this.selectedYearToColumnChart = Integer.parseInt(tbYearToColumnChart.getText());
					// Window.alert(selectedYearTo + "");

					dbService.getColumnChartData(selectedCountryColumnChart, selectedGenreColumnChart, selectedYearFromColumnChart,
							selectedYearToColumnChart, new AsyncCallback<Map<Integer, Integer>>() {

								@Override
								public void onFailure(Throwable caught) {
									logger.log(Level.SEVERE, caught.getMessage());

									for (StackTraceElement t : caught.getStackTrace()) {
										logger.log(Level.SEVERE, t.toString());
									}
								}

								@Override
								public void onSuccess(Map<Integer, Integer> result) {
									columnChartInputMap = result;
									refreshColumnChart();
									/*
									 * for (Map.Entry<Integer, Integer> kvp :
									 * result.entrySet()) { String s = "year: "
									 * + kvp.getKey() + " amount: " +
									 * kvp.getValue(); logger.log(Level.SEVERE,
									 * s); }
									 */
								}
							});

				} else {
					Window.alert("Please select a country!");
				}
			}
		} catch (Exception ex) {
			Window.alert("Please select a country, enter a year from and a year to");

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

		dbService.getFilteredData(selection, new AsyncCallback<Map<Integer, DataResultShared>>() {
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
	 * 
	 * @author Patrick Muntwyler
	 * @Pre ResultTable must be implemented
	 */
	private void refreshResultTable() {
		resultTablePanel.clear();
		Runnable onLoadCallback = new Runnable() {
			public void run() {
				ResultTable resultTable = new ResultTable(resultTableInputDataList);
				resultTablePanel.add(resultTable.getResultTable());
				resultTable.getResultTable().draw(resultTable.getDataTable(), resultTable.getOptions());
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
		if (absoluteRB.getValue()) {
			Runnable onLoadCallback1 = new Runnable() {
				public void run() {
					WorldMap map = new WorldMap(worldMapInputDataList, null, TABPANELWIDTH, TABPANELHEIGHT);
					worldMapPanel.setWidget(map.getWorldMap());
					map.getWorldMap().draw(map.getDataTable(), map.getOptions());
					logger.log(Level.WARNING, "abs");
				}
			};
			VisualizationUtils.loadVisualizationApi(onLoadCallback1, GeoMap.PACKAGE);
		} else {
			Runnable onLoadCallback2 = new Runnable() {
				public void run() {
					logger.log(Level.WARNING, "cap");
					WorldMap map = new WorldMap(worldMapInputDataList, worldMapInputDataListPopulation, TABPANELWIDTH, TABPANELHEIGHT);
					worldMapPanel.setWidget(map.getWorldMap());
					map.getWorldMap().draw(map.getDataTable(), map.getOptions());
				}
			};
			VisualizationUtils.loadVisualizationApi(onLoadCallback2, GeoMap.PACKAGE);
		}
	}

	/**
	 * Refreshes the pie chart after new selection was chosen. Shows a new pie chart
	 * with the new given selection criteria.
	 * 
	 * @author Patrick Muntwyler
	 * @post the pie chart is drawn
	 *
	 */
	private void refreshPieChart() {
		Runnable onLoadCallback = new Runnable() {
			public void run() {
				PieChartWebPage pieChart = new PieChartWebPage(pieChartInputDataList);
				pieChartPanel.setWidget(pieChart.getPieChart());
				pieChart.getPieChart().draw(pieChart.getDataTable(), pieChart.getOptions());
			}
		};
		VisualizationUtils.loadVisualizationApi(onLoadCallback, CoreChart.PACKAGE);
	}
	
	/**
	 * Refreshes the column chart after new selections was chosen. Shows a new column
	 * chart with the new given selection criteria. 
	 * @author Patrick Muntwyler
	 * @post the column chart is drawn
	 */
	private void refreshColumnChart() {
		Runnable onLoadCallback = new Runnable() {
			public void run() {
				ColumnChartWebPage columnChart = new ColumnChartWebPage(columnChartInputMap, selectedYearFromColumnChart,
						selectedYearToColumnChart, selectedCountryColumnChart, selectedGenreColumnChart);
				columnChartPanel.setWidget(columnChart.getColumnChart());
				columnChart.getColumnChart().draw(columnChart.getDataTable(), columnChart.getOptions());
			}
		};
		VisualizationUtils.loadVisualizationApi(onLoadCallback, CoreChart.PACKAGE);
	}
	
	/**
	 * Creates a new Timebar object, adds a change listener to detect changes
	 * made on the timebar
	 * 
	 * @author Patrick Muntwyler
	 * @Pre WorldMap must be implemented
	 */
	private void initializeTimeBar() {
		timeBar.setStepSize(1.0);
		//timeBar.setCurrentValue(CURRENT_YEAR);
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
	 * Creates a new Timebar object, adds a change listener to detect changes
	 * made on the timebar
	 * 
	 * @author Christoph Weber
	 * @pre Mainpanel was initialized
	 * @post Worldmap and Timebar were initialized and added to the mainpanel
	 */
	private void initializeWorldMapAndTimeBar() {
		worldMapPanel = new SimpleLayoutPanel();
		worldMapPanel.setSize(TABPANELWIDTH, TABPANELHEIGHT);
		Runnable onLoadCallback = new Runnable() {
			public void run() {
				WorldMap map;
				if (absoluteRB.getValue()) {
					map = new WorldMap(worldMapInputDataList, null, TABPANELWIDTH, TABPANELHEIGHT);
				} else {
					map = new WorldMap(worldMapInputDataList, worldMapInputDataListPopulation, TABPANELWIDTH, TABPANELHEIGHT);
				}
				worldMapPanel.setWidget(map.getWorldMap());
				map.getWorldMap().draw(map.getDataTable(), map.getOptions());
				worldMapVP.add(worldMapPanel);
				initializeTimeBar();
				timeBar.setCurrentValue(CURRENT_YEAR);
			}
		};
		VisualizationUtils.loadVisualizationApi(onLoadCallback, GeoMap.PACKAGE);

	}

	/**
	 * Initializes the two advertPanels and adds pictures to it. The Pictures
	 * have all the same sizes and are saved under the images folder in war.
	 * 
	 * @author David Lay
	 * @Pre advertisement panels are implemented
	 * @post pictures shown on the website
	 */
	private void initializeAdvertPanels() {
		// initializes new images
		Image img1 = new Image("Images/header_advertise_hor.jpg");
		img1.setHeight(MAINPANELHEIGHT);
		img1.setSize("10vw", MAINPANELHEIGHT);
		
		Image img2 = new Image("Images/header_advertise_hor.jpg");
		img2.setSize("10vw", MAINPANELHEIGHT);

		// adds the images to the different panels
		advertisementPanel1.setWidth("5vw");
		advertisementPanel1.add(img1);
		advertisementPanel2.setWidth("5vw");
		advertisementPanel2.add(img2);
	}

	/**
	 * Instantiates new SimpleLayoutPanel for the pie chart, then instantiates
	 * the pie chart, adds the pie chart to the SimpleLayoutPanel and adds the
	 * SimpleLayoutPanel to the VerticalPanel where the pie chart and the
	 * criteria table for the pie chart will exist.
	 * 
	 * @author Patrick Muntwyler
	 * @Pre Mainpanel was initialized
	 * @post pie chart were initialized and added to the VerticalPanel
	 */
	private void initializePieChart() {
		pieChartPanel = new SimpleLayoutPanel();
		pieChartPanel.setSize(TABPANELWIDTH, TABPANELHEIGHT);
		Runnable onLoadCallback = new Runnable() {
			public void run() {
				PieChartWebPage pieChart = new PieChartWebPage(pieChartInputDataList);
				pieChartPanel.setWidget(pieChart.getPieChart());
				pieChart.getPieChart().draw(pieChart.getDataTable(), pieChart.getOptions());
				pieChartVP.add(pieChartPanel);
			}
		};
		VisualizationUtils.loadVisualizationApi(onLoadCallback, CoreChart.PACKAGE);
	}
	
	/**
	 * Initializes the source panel and adds an anchor as well as a new label to
	 * it.
	 * 
	 * @author David Lay
	 * @Pre SourcePanel must be implemented
	 * @post anchor sends to creativecommons website and source panel can be
	 *       opened and closed at any time
	 */
	private void initializeSourcePanel() {
		VerticalPanel sourcePanel = new VerticalPanel();

		// new anchor to the creative commons website
		Anchor anchorMovies = new Anchor("Commons Attribution-ShareAlike License", "http://creativecommons.org/licenses/by-sa/4.0/");

		// opens link in a new tab
		anchorMovies.setTarget("_blank");

		// adds simple text to the source panel
		sourcePanel.add(new Label("Source Movie Data: David Bamman, Brendan O'Connor and Noah Smith, "
				+ "\"Learning Latent Personas of Film Characters,\" in: Proceedings "
				+ "of the Annual Meeting of the Association for Computational Linguistics (ACL 2013), Sofia, Bulgaria, August 2013."));

		sourcePanel.add(anchorMovies);
		
		// new anchor to the creative commons website
		Anchor anchorPopulation = new Anchor("World Bank Global Population", "http://data.worldbank.org/indicator/SP.POP.TOTL");

		// opens link in a new tab
		anchorPopulation.setTarget("_blank");

		// adds simple text to the source panel
		sourcePanel.add(new Label("Source Population Data: World Bank yearly census. Total population per country per year from 1960-2014. License: Open"));

		sourcePanel.add(anchorPopulation);
		disclosureSourcePanel.add(sourcePanel);
		disclosureSourcePanel.setOpen(true);
	}

	/**
	 * Instantiates new SimpleLayoutPanel for the column chart and adds the SimpleLayoutPanel to the VerticalPanel
	 * where the column chart and the criteria table for the column chart will
	 * exist.
	 * 
	 * @author Patrick Muntwyler
	 * @Pre Mainpanel was initialized
	 * @post vertical panel for column chart is instantiated
	 */
	private void initializeColumnChart() {
		columnChartPanel = new SimpleLayoutPanel();
		columnChartPanel.setSize(TABPANELWIDTH, TABPANELHEIGHT);
		columnChartVP.add(columnChartPanel);
		
	}
	
	/*
	 * private void showYearsBarChartTest() {
	 * 
	 * dbService.getBarChartData("China", null, 2004, 2005, new
	 * AsyncCallback<Map<Integer, Integer>>() {
	 * 
	 * @Override public void onFailure(Throwable caught) {
	 * logger.log(Level.SEVERE, caught.getMessage());
	 * 
	 * for (StackTraceElement t : caught.getStackTrace()) {
	 * logger.log(Level.SEVERE, t.toString()); } }
	 * 
	 * @Override public void onSuccess(Map<Integer, Integer> result) { for
	 * (Map.Entry<Integer, Integer> kvp : result .entrySet()) { String s =
	 * "year: " + kvp.getKey() + " amount: " + kvp.getValue();
	 * logger.log(Level.SEVERE, s); } } }); }
	 */
}
