
package movie.db.client;

/* a small step for software engineering, a huge step for error404! */
//blablablatestestest ich bin ned kreativ
import movie.db.shared.FieldVerifier;

import com.google.gwt.aria.client.ListboxRole;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
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
	private VerticalPanel mainPanel = new VerticalPanel();
	
	private HorizontalPanel selectionPanel = new HorizontalPanel();
	private HorizontalPanel timebarPanel = new HorizontalPanel();
	private HorizontalPanel worldmapPanel = new HorizontalPanel();
	
	private FlexTable selectionCriteriaTable = new FlexTable();
	private Label genreLabel = new Label(); 
	private Label langLabel = new Label(); 
	private Label formatLabel = new Label(); 
	private Button showButton = new Button();
	
	private ListBox genreDropdown = new ListBox();
	private ListBox langDropdown = new ListBox();
	private ListBox formatDropdown = new ListBox();
	
	public void onModuleLoad() {
		genreLabel.setText("Genre:");
		langLabel.setText("Language:");
		formatLabel.setText("Format:");
		showButton.setText("Show Results");
		
		genreDropdown.addItem("Romance");	
		genreDropdown.addItem("Comedy");
		genreDropdown.addItem("Action");
		genreDropdown.addItem("Horror");
		genreDropdown.setVisibleItemCount(1);
		
		langDropdown.addItem("English");	
		langDropdown.addItem("German");	
		langDropdown.addItem("Suahili");	
		langDropdown.addItem("Klingonian");	
		langDropdown.setVisibleItemCount(1);
		
		formatDropdown.addItem("Heatmap");	
		formatDropdown.addItem("Piechart");	
		formatDropdown.addItem("Barchart");	
		formatDropdown.setVisibleItemCount(1);
		
		selectionCriteriaTable.setWidget(0,0,genreLabel);
		selectionCriteriaTable.setWidget(0,1,genreDropdown);
		selectionCriteriaTable.setWidget(1,0,langLabel);
		selectionCriteriaTable.setWidget(1,1,langDropdown);
		selectionCriteriaTable.setWidget(2,0,formatLabel);
		selectionCriteriaTable.setWidget(2,1,formatDropdown);
		selectionCriteriaTable.setWidget(3,0,showButton);
		

		
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
}


