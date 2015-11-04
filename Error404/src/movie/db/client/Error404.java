
package movie.db.client;

/* a small step for software engineering, a huge step for error404! */
//blablablatestestest ich bin ned kreativ




import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

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
	private TextArea dummyTextArea = new TextArea();
	private RadioButton genreRBAnd = new RadioButton("genreRBGroup", "AND");
	private RadioButton genreRBOR = new RadioButton("genreRBGroup", "OR");
	private RadioButton countryRBAnd = new RadioButton("countryRBGroup", "AND");
	private RadioButton countryRBOR = new RadioButton("countryRBGroup", "OR");
	private RadioButton langRBAnd = new RadioButton("langRBGroup", "AND");
	private RadioButton langRBOR = new RadioButton("langRBGroup", "OR");
	private ListBox genreListBox = new ListBox();		
	private ListBox countryListBox = new ListBox();
	private ListBox langListBox = new ListBox();
	
	private void initializePanels(){
		
		HorizontalPanel selectionPanel = new HorizontalPanel();
		HorizontalPanel timebarPanel = new HorizontalPanel();
		HorizontalPanel worldmapPanel = new HorizontalPanel();
		
		FlexTable selectionCriteriaTable = new FlexTable();
		Button showAsButton = new Button();
		showAsButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {		
				showAsButtonClick();
			}
		});
		
		showAsButton.setText("Show as");		
		initializeGenres();
		initializeCountries();
		initializeLanguages();	

		genreRBAnd.setValue(true);		
		countryRBAnd.setValue(true);		
		langRBAnd.setValue(true);
		
		selectionCriteriaTable.setText(0,0,"Genre:");
		selectionCriteriaTable.setWidget(0,1,genreListBox);
		selectionCriteriaTable.setWidget(0,2,genreRBAnd);
		selectionCriteriaTable.setWidget(0,3,genreRBOR);
		selectionCriteriaTable.setText(1,0,"Country:");
		selectionCriteriaTable.setWidget(1,1,countryListBox);
		selectionCriteriaTable.setWidget(1,2,countryRBAnd);
		selectionCriteriaTable.setWidget(1,3,countryRBOR);
		selectionCriteriaTable.setText(2,0,"Language:");
		selectionCriteriaTable.setWidget(2,1,langListBox);
		selectionCriteriaTable.setWidget(2,2,langRBAnd);
		selectionCriteriaTable.setWidget(2,3,langRBOR);
		selectionCriteriaTable.setWidget(3,0,showAsButton);
		selectionCriteriaTable.setWidget(3,1,initializeFormats());
			
		
		selectionPanel.add(selectionCriteriaTable);
		
		timebarPanel.setWidth("1500px");
		timebarPanel.setHeight("250px");
		timebarPanel.setBorderWidth(3);

		worldmapPanel.setWidth("1500px");
		worldmapPanel.setHeight("500px");
		worldmapPanel.setBorderWidth(3);		
		
		mainPanel.add(selectionPanel);
		mainPanel.add(timebarPanel);
		mainPanel.add(worldmapPanel);
		
		// Associate the Main panel with the HTML host page.
		RootPanel.get("mainPage").add(mainPanel);
	}
	
	private void initializeGenres(){		
		for(Genres genre : Genres.values()){
			genreListBox.addItem(genre.getName());
		}
		genreListBox.setVisibleItemCount(5);
		genreListBox.setMultipleSelect(true);
		genreListBox.setItemSelected(0, false); //first item is selected by default
	}
	private void initializeCountries(){
		for(Countries country : Countries.values()){
			countryListBox.addItem(country.getName());
		}	
		countryListBox.setVisibleItemCount(5);
		countryListBox.setMultipleSelect(true);
		countryListBox.setItemSelected(0, false); //first item is selected by default
	}
	private void initializeLanguages(){
		for(Languages language : Languages.values()){
			langListBox.addItem(language.getName());
		}
		langListBox.setVisibleItemCount(5);
		langListBox.setMultipleSelect(true);
		langListBox.setItemSelected(0, false); //first item is selected by default
	}
	private VerticalPanel initializeFormats(){
		VerticalPanel formatsPanel = new VerticalPanel();
		for(Formats format : Formats.values()){
			formatsPanel.add(new RadioButton("formatsRBGroup", format.getName()));
		}
		((RadioButton)formatsPanel.getWidget(0)).setValue(true);
		return formatsPanel;
	}	
	private final void showAsButtonClick(){

		
		dummyTextArea.setWidth("500px");
		dummyTextArea.setHeight("200px");
		ArrayList<String> selectedGenres = new ArrayList<String>();
		if(genreListBox.getSelectedIndex() != -1){		
			for(int i = 0;i< genreListBox.getItemCount();i++){
				if(genreListBox.isItemSelected(i)){
					selectedGenres.add(genreListBox.getItemText(i));
				}
			}
		}
		ArrayList<String> selectedCountries = new ArrayList<String>();
		if(countryListBox.getSelectedIndex() != -1){		
			for(int i = 0;i< countryListBox.getItemCount();i++){
				if(countryListBox.isItemSelected(i)){
					selectedCountries.add(countryListBox.getItemText(i));
				}
			}
		}
		ArrayList<String> selectedLanguages = new ArrayList<String>();
		if(langListBox.getSelectedIndex() != -1){		
			for(int i = 0;i< langListBox.getItemCount();i++){
				if(langListBox.isItemSelected(i)){
					selectedLanguages.add(langListBox.getItemText(i));
				}
			}
		}
		
		
		
		String dummyTextAreaString = selectionToSQLString(selectedCountries,"countries");
		/*
		for(String selected : selectedGenres){
			dummyTextAreaString = dummyTextAreaString + selected + "\n";			
		}
		dummyTextAreaString = dummyTextAreaString + "Connection: ";
		if(genreRBAnd.getValue()){
			dummyTextAreaString = dummyTextAreaString + "AND";
		}else{
			dummyTextAreaString = dummyTextAreaString + "OR";
		}��
		
		dummyTextAreaString = dummyTextAreaString + "\n\nCountries:\n";
		for(String selected : selectedCountries){
			dummyTextAreaString = dummyTextAreaString + selected + "\n";			
		}
		dummyTextAreaString = dummyTextAreaString + "Connection: ";
		if(countryRBAnd.getValue()){
			dummyTextAreaString = dummyTextAreaString + "AND";
		}else{
			dummyTextAreaString = dummyTextAreaString + "OR";
		}
		
		dummyTextAreaString = dummyTextAreaString + "\n\nLanguages:\n";
		for(String selected : selectedLanguages){
			dummyTextAreaString = dummyTextAreaString + selected  + "\n";			
		}
		dummyTextAreaString = dummyTextAreaString + "Connection: ";
		if(langRBAnd.getValue()){
			dummyTextAreaString = dummyTextAreaString + "AND";
		}else{
			dummyTextAreaString = dummyTextAreaString + "OR";
		}
		*/
		
		dummyTextArea.setText(dummyTextAreaString);
		((HorizontalPanel)mainPanel.getWidget(1)).add(dummyTextArea);	
			
	}
	
	private String selectionToSQLString(ArrayList<String> selectionList, String column){
		String selectionSQL;
		if(selectionList.isEmpty()){
			selectionSQL = " ";
		}else{
			selectionSQL = "AND "+ column + ".name IN ('";
			for(String selection : selectionList){
				selectionSQL = selectionSQL + selection + "','";
			}
			selectionSQL = selectionSQL.substring(0, selectionSQL.length()-2) + ")"; //removes ,' at the end
		}
		return selectionSQL;
	}
	
	
}




