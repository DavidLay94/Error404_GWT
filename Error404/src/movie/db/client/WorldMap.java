package movie.db.client;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.processing.RoundEnvironment;

import movie.db.shared.DataResultAggregated;

import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.visualizations.GeoMap;
import com.google.gwt.visualization.client.visualizations.GeoMap.Options;

/*
 * This class is needed for the visualization of the movies per country for a selected year on an interactive GeoMap.
 */
public class WorldMap {

	private final static int BRIGHT = 0x3db8fa;
	 private final static int DARK = 0x0f27ff;
	private GeoMap worldMap;
	private DataTable dataTable;
	private Options options;
	private final static Logger logger = Logger.getLogger("Error404");

	public WorldMap(ArrayList<DataResultAggregated> movieData, Map<String, Integer> populationData, String width, String height) {
		super();
		if (populationData == null) {
			dataTable = generateDataTable(movieData);
		} else {
			dataTable = generateDataTable(movieData, populationData);
		}
		options = Options.create();
		options.setColors(new int[] { BRIGHT, DARK });
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
	 * @author Patrick Muntwyler Generates a DataTable which will be used by the
	 *         GeoMap
	 * 
	 * @pre Database query must have delivered some results
	 * @param ArrayList
	 *            <DataResultAggregated> data
	 * @post DataTable contains the result entered in the different columns
	 */
	public DataTable generateDataTable(ArrayList<DataResultAggregated> movieData) {

		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.STRING, "Country");
		dataTable.addColumn(ColumnType.NUMBER, "Number of movies");
		dataTable.addColumn(ColumnType.STRING, "additional information");
		ArrayList<InputObjectWorldMap> inputList = CountryNameHandling.createInputArrayList(movieData, true);

		int sizeInputList = inputList.size();
		int z = 0;

		if (sizeInputList == 0) {
			dataTable.addRow();
		} else {
			while (z < sizeInputList) {
				if (movieData.get(z).getCountryName() == null) {
					z++;
				} else {
					dataTable.addRow();
					dataTable.setValue(z, 0, inputList.get(z).getOSICountryName());
					dataTable.setValue(z, 1, inputList.get(z).getNumberOfMovies());
					dataTable.setValue(z, 2, inputList.get(z).getShowedCountryName());
					z++;
				}

			}
		}
		return dataTable;
	}

	public DataTable generateDataTable(ArrayList<DataResultAggregated> movieData, Map<String, Integer> populationData) {

		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.STRING, "Country");
		dataTable.addColumn(ColumnType.NUMBER, "Number of movies per 1 mio capita");
		dataTable.addColumn(ColumnType.STRING, "additional information");
		ArrayList<InputObjectWorldMap> inputList =CountryNameHandling.createInputArrayList(movieData, true);

		int sizeInputList = inputList.size();
		int z = 0;
		try {
			if (sizeInputList == 0) {
				dataTable.addRow();
			} else {
				while (z < sizeInputList) {
					dataTable.addRow();
					if (movieData.get(z).getCountryName() == null) {
					} else {
						if (populationData.containsKey(inputList.get(z).getShowedCountryName())) {
							dataTable.addRow();
							dataTable.setValue(z, 0, inputList.get(z).getOSICountryName());

							int population = populationData.get(inputList.get(z).getShowedCountryName());
							double capitaAmount = Math.round(1000000000.0 * inputList.get(z).getNumberOfMovies() / population) / 1000d;
							dataTable.setValue(z, 1, capitaAmount);
							dataTable.setValue(z, 2, inputList.get(z).getShowedCountryName());
						}
					}
					z++;
				}
			}
		} catch (Exception ex) {
			logger.log(Level.WARNING, ex.getMessage());
		}
		return dataTable;
	}

	/**
	 * @author Patrick Muntwyler
	 * @param ArrayList
	 *            <DataResultAggregated> which is needed to fill the
	 *            2D-String-Array. This 2D-String-Array replaces the DataTable
	 *            which is needed for the GeoMap. Because JUnit Tests don't work
	 *            with this DataTable this 2D-String-Array should simulate the
	 *            DataTable. This method is only for the JUnit Test. It is not
	 *            needed for the webpage.
	 * @return
	 */
	public String[][] generateDataTableTest(ArrayList<DataResultAggregated> data) {

		ArrayList<InputObjectWorldMap> inputList = CountryNameHandling.createInputArrayList(data, true);
		int sizeInputList = inputList.size();

		// this line substitutes DataTable dataTable = DataTable.create();
		// dataTable.addColumn(ColumnType.STRING, "Country");
		// dataTable.addColumn(ColumnType.NUMBER, "Number of movies");
		// dataTable.addColumn(ColumnType.STRING, "additional information");
		String[][] testArray = new String[sizeInputList][3];

		int z = 0;
		if (sizeInputList == 0) {
			// when working with arrays it's not important to monitor this case
			// in the generateDataTable method here you find the following line
			// "dataTable.addRow();".
			// this is important because the GeoMap cannot work with a
			// DataTable, which has 0 rows.
			// So we added just a empty row.
		} else {
			while (z < sizeInputList) {

				// this line substitutes "dataTable.setValue(z, 0,
				// data.get(z).getCountryName());"
				testArray[z][0] = inputList.get(z).getOSICountryName();
				// this line substitutes "dataTable.setValue(z, 1,
				// data.get(z).getAggregatedNumberOfMovies());"
				testArray[z][1] = inputList.get(z).getNumberOfMovies() + "";
				// this line substitutes
				// "dataTable.setValue(z, 2, inputList.get(z).getShowedCountryName());"
				testArray[z][2] = inputList.get(z).getShowedCountryName();
				z++;
			}
		}
		return testArray;
	}
	
}
