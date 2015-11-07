package movie.db.client;

/* a small step for software engineering, a huge step for error404! */
//blablablatestestest ich bin ned kreativ

import java.util.ArrayList;
import java.util.HashMap;
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
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.TabPanel;
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
	private SimpleLayoutPanel worldMapPanel;
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
	private ArrayList<DataResultAggregated> worldMapInputDataList = new ArrayList<DataResultAggregated>();
	private TabPanel tabPanel = new TabPanel();
	private final static String TABPANELHEIGHT = "600px";
	private final static String TABPANELWIDTH= "1200px";
	
	private void initializePanels() {
		mainPanel.setSize("100vw", "82vh");
		mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		VerticalPanel selectionPanel = new VerticalPanel();
		HorizontalPanel timebarPanel = new HorizontalPanel();

		FlexTable selectionCriteriaTable = new FlexTable();
		FlexTable worldMapCriteriaTable = new FlexTable();
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
		selectionCriteriaTable.setText(0, 2, "Country:");
		selectionCriteriaTable.setWidget(0, 3, countryListBox);
		// selectionCriteriaTable.setWidget(1, 2, countryRBAnd);
		// selectionCriteriaTable.setWidget(1, 3, countryRBOR);
		selectionCriteriaTable.setText(0, 4, "Language:");
		selectionCriteriaTable.setWidget(0, 5, langListBox);
		// selectionCriteriaTable.setWidget(2, 2, langRBAnd);
		// selectionCriteriaTable.setWidget(2, 3, langRBOR);
		selectionCriteriaTable.setWidget(0,7, showAsButton);
		selectionCriteriaTable.setWidget(0, 8, initializeFormats());
		worldMapCriteriaTable.setText(4, 0, "Year");
		worldMapCriteriaTable.setWidget(4, 1, tbYear);
		worldMapCriteriaTable.setWidget(4, 2, showMapButton);

		selectionPanel.add(selectionCriteriaTable);
		selectionPanel.add(worldMapCriteriaTable);
		timebarPanel.setWidth("1500px");
		timebarPanel.setHeight("250px");
		timebarPanel.setBorderWidth(3);

		mainPanel.add(selectionPanel);	

		tabPanel.setSize(TABPANELWIDTH, TABPANELHEIGHT);
		tabPanel.add(initializeWorldMap(),"Worldmap");
		tabPanel.add(resultFlexTable,"Table");
		tabPanel.selectTab(0);
		mainPanel.add(tabPanel);
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
		tabPanel.selectTab(0);
	}

	private final void showAsButtonClick() {
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

		Selection selection = new Selection();
		selection.setSelectedCountries(selectedCountries);
		selection.setSelectedLanguages(selectedLanguages);
		selection.setSelectedGenres(selectedGenres);
		//((HorizontalPanel) mainPanel.getWidget(1)).add(resultFlexTable);
		showResults(selection);
		tabPanel.selectTab(1);
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
								// refreshWorldMap(result);
								worldMapInputDataList = result; 
								refreshWorldMap();
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
	private SimpleLayoutPanel initializeWorldMap() {
		worldMapPanel = new SimpleLayoutPanel();
		worldMapPanel.setSize(TABPANELWIDTH, TABPANELHEIGHT);
		refreshWorldMap();
		
		return worldMapPanel;
	}

	private void refreshWorldMap() {
		//worldMapPanel.clear();
		Runnable onLoadCallback = new Runnable() {
			public void run() {				
				WorldMap map = new WorldMap(worldMapInputDataList,TABPANELWIDTH,TABPANELHEIGHT);
				worldMapPanel.setWidget(map.getWorldMap());
				map.getWorldMap().draw(map.getDataTable(), map.getOptions());
			}
		};
		VisualizationUtils.loadVisualizationApi(onLoadCallback, GeoMap.PACKAGE);
	}
	

}
