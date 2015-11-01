
package movie.db.client;

/* a small step for software engineering, a huge step for error404! */
//blablablatestestest ich bin ned kreativ
import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
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
	
	private void initializePanels(){
		VerticalPanel mainPanel = new VerticalPanel();
		
		HorizontalPanel selectionPanel = new HorizontalPanel();
		HorizontalPanel timebarPanel = new HorizontalPanel();
		HorizontalPanel worldmapPanel = new HorizontalPanel();
		
		FlexTable selectionCriteriaTable = new FlexTable();
		Button showOnWorldmapButton = new Button();

		ListBox genreDropdown = new ListBox();
		
		ListBox countryDropdown = new ListBox();
		ListBox langDropdown = new ListBox();
		ListBox formatDropdown = new ListBox();
				
		showOnWorldmapButton.setText("Show on Worldmap");		
		genreDropdown.setMultipleSelect(true);
		initializeGenres(genreDropdown);
		initializeCountries(countryDropdown);
		initializeLanguages(langDropdown);
		initializeFormats(formatDropdown);		
		
		selectionCriteriaTable.setText(0,0,"Genre:");
		selectionCriteriaTable.setWidget(0,1,genreDropdown);
		selectionCriteriaTable.setText(1,0,"Country:");
		selectionCriteriaTable.setWidget(1,1,countryDropdown);
		selectionCriteriaTable.setText(2,0,"Language:");
		selectionCriteriaTable.setWidget(2,1,langDropdown);
		selectionCriteriaTable.setText(3,0,"Output Format:");
		selectionCriteriaTable.setWidget(3,1,formatDropdown);
		selectionCriteriaTable.setWidget(4,0,showOnWorldmapButton);
		
		selectionPanel.add(selectionCriteriaTable);
		
		timebarPanel.setWidth("1000px");
		timebarPanel.setHeight("50px");
		timebarPanel.setBorderWidth(3);

		worldmapPanel.setWidth("1000px");
		worldmapPanel.setHeight("500px");
		worldmapPanel.setBorderWidth(3);		
		
		mainPanel.add(selectionPanel);
		mainPanel.add(timebarPanel);
		mainPanel.add(worldmapPanel);
		
		// Associate the Main panel with the HTML host page.
		RootPanel.get("mainPage").add(mainPanel);
	}
	
	private void initializeGenres(ListBox genreDropdown){		
		genreDropdown.addItem("Romance");	
		genreDropdown.addItem("Comedy");
		genreDropdown.addItem("Action");
		genreDropdown.addItem("Horror");
		genreDropdown.addItem("Thriller");
		genreDropdown.addItem("Science Fiction");
		genreDropdown.addItem("Adventure");
		genreDropdown.addItem("Supernatural");
		genreDropdown.setVisibleItemCount(5);
	}
	private void initializeCountries(ListBox countryDropdown){
		countryDropdown.addItem("United States");
		countryDropdown.addItem("Switzerland");
		countryDropdown.addItem("DeathStar");	
		countryDropdown.setVisibleItemCount(1);
	}
	private void initializeLanguages(ListBox langDropdown){
		langDropdown.addItem("English");	
		langDropdown.addItem("German");	
		langDropdown.addItem("Suahili");	
		langDropdown.addItem("Klingonian");	
		langDropdown.setVisibleItemCount(1);
	}
	private void initializeFormats(ListBox formatDropdown){
		formatDropdown.addItem("Heatmap");	
		formatDropdown.addItem("Piechart");	
		formatDropdown.addItem("Barchart");	
		formatDropdown.setVisibleItemCount(1);
	}
}


