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
	private final static String TABPANELWIDTH= "1200px";
	
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
		initializeButtons(showAsButton,showMapButton,cleanSelectionButton,cleanWorldMapButton);
		
		initializeGenres();
		initializeCountries();
		initializeLanguages();

		initializeSelectionCriteriaTable(selectionCriteriaTable, showAsButton);
		initializeWorldMapCriteriaTable(worldMapCriteriaTable,showMapButton,cleanSelectionButton,cleanWorldMapButton);

		selectionPanel.add(selectionCriteriaTable);
		SimpleLayoutPanel emptyPanel = new SimpleLayoutPanel();
		emptyPanel.setSize("0px", "15px");
		selectionPanel.add(emptyPanel);
		selectionPanel.add(worldMapCriteriaTable);
		
		mainPanel.add(selectionPanel);	

		tabPanel.setSize(TABPANELWIDTH, TABPANELHEIGHT);
		tabPanel.add(initializeWorldMap(),"Worldmap");
		tabPanel.add(initializeResultTable(),"Table");
		tabPanel.selectTab(0);
		mainPanel.add(tabPanel);

		// Associate the Main panel with the HTML host page.
		RootPanel.get("mainPage").add(mainPanel);
	}
	
	private void initializeSelectionCriteriaTable(FlexTable selectionCriteriaTable, Button showAsButton){
		selectionCriteriaTable.setText(0, 0, "Genre:");
		selectionCriteriaTable.setWidget(1,0, genreListBox);
		selectionCriteriaTable.setText(0, 1, "Country:");
		selectionCriteriaTable.setWidget(1, 1, countryListBox);
		selectionCriteriaTable.setText(0, 2, "Language:");
		selectionCriteriaTable.setWidget(1, 2, langListBox);
		selectionCriteriaTable.setWidget(1,3, showAsButton);
		selectionCriteriaTable.setWidget(1, 4, initializeFormats());
	}
	
	private void initializeWorldMapCriteriaTable(FlexTable worldMapCriteriaTable,Button showMapButton, Button cleanSelectionButton,Button cleanWorldMapButton){
		worldMapCriteriaTable.setText(0, 0, "Year:");
		worldMapCriteriaTable.setWidget(1, 0, tbYear);
		worldMapCriteriaTable.setWidget(1, 1, showMapButton);
		worldMapCriteriaTable.setWidget(2,0, cleanSelectionButton);
		worldMapCriteriaTable.setWidget(2,1, cleanWorldMapButton);
	}
	
	private void initializeButtons(Button showAsButton,Button showMapButton, Button cleanSelectionButton,Button cleanWorldMapButton){
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

		//Piechart and Barchart are greyed out until implemented!
		for (Formats format : Formats.values()) { 
			RadioButton rb = new RadioButton("formatsRBGroup", format.getName());
			if(format.getName().equals("Table")){
				rb.setEnabled(true);
			}else{
				rb.setEnabled(false);
			}				
			formatsPanel.add(rb);
		}
		((RadioButton) formatsPanel.getWidget(0)).setValue(true);
		
		return formatsPanel;
	}

	private final void showWorldMapClick() {
		fillWorldmap();
		tabPanel.selectTab(0);
	}

	private final void cleanSelectionClick(){
		genreListBox.setMultipleSelect(false);
		genreListBox.setItemSelected(0,true);
		genreListBox.setItemSelected(0,false);
		
		countryListBox.setMultipleSelect(false);
		countryListBox.setItemSelected(0,true);
		countryListBox.setItemSelected(0,false);
		
		langListBox.setMultipleSelect(false);
		langListBox.setItemSelected(0,true);
		langListBox.setItemSelected(0,false);

		genreListBox.setMultipleSelect(true);
		countryListBox.setMultipleSelect(true);
		langListBox.setMultipleSelect(true);
		resultTableInputDataList.clear();
		refreshResultTable();
	}
	
	private final void cleanWorldMapClick(){
		worldMapInputDataList.clear();
		refreshWorldMap();
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

		dbService.getFilteredData(selection, new AsyncCallback<Map<Integer, DataResultShared>>() {
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
	
	private HorizontalPanel initializeResultTable() {
		resultTablePanel = new HorizontalPanel();
		refreshResultTable();

		return resultTablePanel;
	}

	private void refreshResultTable(){
		resultTablePanel.clear();
		Runnable onLoadCallback = new Runnable(){
			public void run(){
				ResultTable resultTable = new ResultTable(resultTableInputDataList);
				resultTablePanel.add(resultTable.getResultTable());
				resultTable.getResultTable().draw(resultTable.getDataTable(), resultTable.getOptions());
			}
		};
		VisualizationUtils.loadVisualizationApi(onLoadCallback, Table.PACKAGE);
	}
	
	private SimpleLayoutPanel initializeWorldMap() {
		worldMapPanel = new SimpleLayoutPanel();
		worldMapPanel.setSize(TABPANELWIDTH, TABPANELHEIGHT);
		refreshWorldMap();
		
		return worldMapPanel;
	}

	private void refreshWorldMap() {
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
