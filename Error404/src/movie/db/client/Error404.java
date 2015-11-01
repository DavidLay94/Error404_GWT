
package movie.db.client;

/* a small step for software engineering, a huge step for error404! */
//blablablatestestest ich bin ned kreativ


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
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
	private VerticalPanel mainPanel = new VerticalPanel();
	
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
		
		ListBox genreDropdown = new ListBox();
		
		ListBox countryDropdown = new ListBox();
		ListBox langDropdown = new ListBox();
		ListBox formatDropdown = new ListBox();
				
		showAsButton.setText("Show as");		
		genreDropdown.setMultipleSelect(true);
		initializeGenres(genreDropdown);
		initializeCountries(countryDropdown);
		initializeLanguages(langDropdown);	

		RadioButton genreRBAnd = new RadioButton("genreRBGroup", "AND");
		genreRBAnd.setValue(true);
		RadioButton genreRBOR = new RadioButton("genreRBGroup", "OR");
		
		RadioButton countryRBAnd = new RadioButton("countryRBGroup", "AND");
		countryRBAnd.setValue(true);
		RadioButton countryRBOR = new RadioButton("countryRBGroup", "OR");
		
		RadioButton langRBAnd = new RadioButton("langRBGroup", "AND");
		langRBAnd.setValue(true);
		RadioButton langRBOR = new RadioButton("langRBGroup", "OR");
		
		selectionCriteriaTable.setText(0,0,"Genre:");
		selectionCriteriaTable.setWidget(0,1,genreDropdown);
		selectionCriteriaTable.setWidget(0,2,genreRBAnd);
		selectionCriteriaTable.setWidget(0,3,genreRBOR);
		selectionCriteriaTable.setText(1,0,"Country:");
		selectionCriteriaTable.setWidget(1,1,countryDropdown);
		selectionCriteriaTable.setWidget(1,2,countryRBAnd);
		selectionCriteriaTable.setWidget(1,3,countryRBOR);
		selectionCriteriaTable.setText(2,0,"Language:");
		selectionCriteriaTable.setWidget(2,1,langDropdown);
		selectionCriteriaTable.setWidget(2,2,langRBAnd);
		selectionCriteriaTable.setWidget(2,3,langRBOR);
		selectionCriteriaTable.setWidget(3,0,showAsButton);
		selectionCriteriaTable.setWidget(3,1,initializeFormats(formatDropdown));
			
		
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
		for(Genres genre : Genres.values()){
			genreDropdown.addItem(genre.getName());
		}
		genreDropdown.setVisibleItemCount(5);
	}
	private void initializeCountries(ListBox countryDropdown){
		for(Countries country : Countries.values()){
			countryDropdown.addItem(country.getName());
		}	
		countryDropdown.setVisibleItemCount(5);
	}
	private void initializeLanguages(ListBox langDropdown){
		for(Languages language : Languages.values()){
			langDropdown.addItem(language.getName());
		}
		langDropdown.setVisibleItemCount(5);
	}
	private VerticalPanel initializeFormats(ListBox formatDropdown){
		VerticalPanel formatsPanel = new VerticalPanel();
		for(Formats format : Formats.values()){
			formatsPanel.add(new RadioButton("formatsRBGroup", format.getName()));
		}
		((RadioButton)formatsPanel.getWidget(0)).setValue(true);
		return formatsPanel;
	}	
	private final void showAsButtonClick(){
		TextBox dummyTextBox = new TextBox();
		dummyTextBox.setText("test123");
		((HorizontalPanel)mainPanel.getWidget(1)).add(dummyTextBox);
		
	}
}




