package movie.db.client;

import java.util.*;

import movie.db.shared.DataResultAggregated;

import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.visualizations.GeoMap;
import com.google.gwt.visualization.client.visualizations.GeoMap.Options;

/*
 * This class is needed for the visualization of the movies per country for a selected year on a interactive worldmap.
 */
public class WorldMap {

	private final static int BRIGHT = 0x87CEEB;
	private final static int DARK = 0x00008B;
	private GeoMap worldMap;
	private DataTable dataTable;
	private Options options;
	
	public WorldMap(ArrayList<DataResultAggregated> list,String width, String height){
		super();
		dataTable = generateDataTable(list);
		options = Options.create();
		options.setColors(new int[]{BRIGHT,DARK});
		options.setWidth(width);
		options.setHeight(height);
		worldMap = new GeoMap();
	}
	
	public GeoMap getWorldMap() {
		return worldMap;
	}

	public DataTable getDataTable() {
		return dataTable;
	}

	public Options getOptions() {
		return options;
	}

	/**
	 * Generates a datatable which can be used by a widget and then be shown on the user interface 
	 * 
	 * @pre Database query must have delivered some results
	 * @param ArrayList<DataResultAggregated> data
	 * @post datatable contains the result entered in the different columns
	 */
	public DataTable generateDataTable(ArrayList<DataResultAggregated> data){
		
		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.STRING, "Country");
		dataTable.addColumn(ColumnType.NUMBER, "Number of movies");
		
		int size = data.size();
		int z = 0;
		if(size == 0){
			dataTable.addRow();
		}else{
			while(z<size){
		
			dataTable.addRow();
			dataTable.setValue(z, 0, data.get(z).getCountryName());
			dataTable.setValue(z, 1, data.get(z).getAggregatedNumberOfMovies());
			z++;
		}}
		return dataTable;
	}
		
}
