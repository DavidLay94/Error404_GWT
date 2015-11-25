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

	private final static int BRIGHT = 0x87CEEB;
	private final static int DARK = 0x00008B;
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
		ArrayList<InputObjectWorldMap> inputList = createWorldMapInputArrayList(movieData);

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
		// Window.alert(data.get(0).getCountryName().replaceAll(" ", "1 "));
		// Window.alert(data.get(0).getAggregatedNumberOfMovies() + "");
		// Window.alert(data.get(1).getCountryName().replaceAll(" ", "1"));
		// Window.alert(data.get(1).getAggregatedNumberOfMovies() + "");

		return dataTable;
	}

	public DataTable generateDataTable(ArrayList<DataResultAggregated> movieData, Map<String, Integer> populationData) {

		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.STRING, "Country");
		dataTable.addColumn(ColumnType.NUMBER, "Number of movies per 1 mio capita");
		dataTable.addColumn(ColumnType.STRING, "additional information");
		ArrayList<InputObjectWorldMap> inputList = createWorldMapInputArrayList(movieData);

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

		ArrayList<InputObjectWorldMap> inputList = createWorldMapInputArrayList(data);
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

	/**
	 * @author Patrick Muntwyler This method takes an ArrayList of the type
	 *         DataResultAggregated and converts it to an ArrayList of the type
	 *         InputObjectWorldMap. The method also checks the exceptional
	 *         countries which are not shown on the GeoMap because they don't
	 *         correlate with the needed format. So this exceptions must be
	 *         treated specially. Their country name must be changed or ignored
	 *         if they were split in the past or not shown on the GeoMap because
	 *         they are too small.
	 * 
	 * @param inputData
	 *            : An ArrayList of the type DataResultAggregated which is
	 *            provided by the query
	 * @return An ArrayList of the type InputObjectWorldMap which is required to
	 *         build the DataTable for the GeoMap
	 */

	private ArrayList<InputObjectWorldMap> createWorldMapInputArrayList(ArrayList<DataResultAggregated> inputData) {

		ArrayList<InputObjectWorldMap> outputList = new ArrayList<InputObjectWorldMap>();

		int size = inputData.size();
		int i = 0;

		while (i < size) {
			if (inputData.get(i).getCountryName() == null || inputData.get(i).getAggregatedNumberOfMovies() == 0) {
				i++;
			} else {
				String correctCountry = inputData.get(i).getCountryName();
				String country = inputData.get(i).getCountryName();
				country.trim();
				country = country.replaceAll("\\s+", "");
				if (country.equals(" ")) {
				} else if (country.equals("Aruba")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "Netherlands", "Netherlands");
				} else if (country.equals("Netherlands")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "Netherlands", "Netherlands");
				}

				else if (country.equalsIgnoreCase("UnitedStatesofAmerica")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "United States", "United States of America");

				} else if (country.equalsIgnoreCase("BosniaandHerzegovina")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "BA", "Bosnia and Herzegovina");

				} else if (country.equals("Burma")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "Myanmar", "Myanmar (also Burma)");

				} else if (country.equals("Congo")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "CD", "the Democratic Republic of the Congo");

				} else if (country.equals("DemocraticRepublicoftheCongo")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "CD", "the Democratic Republic of the Congo");

				} else if (country.equals("England")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "GB", "the United Kingdom of Great Britain and Northern Ireland");

				} else if (country.equals("Georgia")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "Georgia", "Georgia");

				} else if (country.equals("GeorgianSSR")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "Georgia", "Georgia");

				} else if (country.equals("GermanDemocraticRepublic")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "Germany", "Germany");

				} else if (country.equals("Germany")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "Germany", "Germany");

				} else if (country.equals("HongKong")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "China", "China");

				} else if (country.equals("China")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "China", "China");

				} else if (country.equals("IraqiKurdistan")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "Iraq", "Iraq");

				} else if (country.equals("Iraq")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "Iraq", "Iraq");

				} else if (country.equals("IsleofMan")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "GB", "the United Kingdom of Great Britain and Northern Ireland");

				} else if (country.equals("KingdomofGreatBritain")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "GB", "the United Kingdom of Great Britain and Northern Ireland");

				} else if (country.equals("KingdomofItaly")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "Italy", "Italy");

				} else if (country.equals("Italy")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "Italy", "Italy");

				} else if (country.equals("Korea")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "KR", "South Korea");

				} else if (country.equals("Macau")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "China", "China");

				} else if (country.equals("NaziGermany")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "Germany", "Germany");

				} else if (country.equals("NorthernIreland")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "GB", "the United Kingdom of Great Britain and Northern Ireland");

				} else if (country.equals("RepublicofChina")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "China", "China");

				} else if (country.equals("RepublicofMacedonia")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "MK", "Macedonia");

				} else if (country.equals("Scotland")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "GB", "the United Kingdom of Great Britain and Northern Ireland");

				} else if (country.equals("SlovakRepublic")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "Slovakia", "Slovakia");

				} else if (country.equals("Slovakia")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "Slovakia", "Slovakia");

				} else if (country.equals("SouthKorea")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "KR", "South Korea");

				} else if (country.equals("UkrainianSSR")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "Ukraine", "Ukraine");

				} else if (country.equals("UkranianSSR")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "Ukraine", "Ukraine");

				} else if (country.equals("Ukraine")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "Ukraine", "Ukraine");

				} else if (country.equals("UnitedKingdom")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "GB", "the United Kingdom of Great Britain and Northern Ireland");

				} else if (country.equals("Uzbekistan")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "Uzbekistan", "Uzbekistan");

				} else if (country.equals("UzbekSSR")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "Uzbekistan", "Uzbekistan");

				} else if (country.equals("Wales")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "GB", "the United Kingdom of Great Britain and Northern Ireland");

				} else if (country.equals("WeimarRepublic")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "Germany", "Germany");

				} else if (country.equals("WestGermany")) {
					outputList = handleExceptionalCountry(inputData, outputList, i, "Germany", "Germany");

				} else if (country.equals("Bahrain")) {
					// this country gets ignored, because it is not shown on the
					// GeoMap. If it would not be ignored and also not
					// shown, then it would have influence on the color scale of
					// the GeoMap.
				} else if (country.equals("Czechoslovakia")) {
					// this country gets ignored, because it was split in
					// several other countries and so it can not be shown on the
					// GeoMap.
					// If it would not be ignored and also not shown, then it
					// would have influence on the color scale of the GeoMap.
				} else if (country.equals("FederalRepublicofYugoslavia")) {
					// this country gets ignored, because it was split in
					// several other countries and so it can not be shown on the
					// GeoMap.
					// If it would not be ignored and also not shown, then it
					// would have influence on the color scale of the GeoMap.
				} else if (country.equals("Malta")) {
					// this country gets ignored, because it is not shown on the
					// GeoMap. If it would not be ignored and also not
					// shown, then it would have influence on the color scale of
					// the GeoMap.
				} else if (country.equals("MandatoryPalestine")) {
					// this country gets ignored, because it is not shown on the
					// GeoMap. If it would not be ignored and also not
					// shown, then it would have influence on the color scale of
					// the GeoMap.
				} else if (country.equals("Monaco")) {
					// this country gets ignored, because it is not shown on the
					// GeoMap. If it would not be ignored and also not
					// shown, then it would have influence on the color scale of
					// the GeoMap.
				} else if (country.equals("Palestinianterritories")) {
					// this country gets ignored, because it is not shown on the
					// GeoMap. If it would not be ignored and also not
					// shown, then it would have influence on the color scale of
					// the GeoMap.
				} else if (country.equals("SerbiaandMontenegro")) {
					// this country gets ignored, because it was split in
					// several other countries and so it can not be shown on the
					// GeoMap.
					// If it would not be ignored and also not shown, then it
					// would have influence on the color scale of the GeoMap.
				} else if (country.equals("SocialistFederalRepublicofYugoslavia")) {
					// this country gets ignored, because it was split in
					// several other countries and so it can not be shown on the
					// GeoMap.
					// If it would not be ignored and also not shown, then it
					// would have influence on the color scale of the GeoMap.
				} else if (country.equals("Sovietoccupationzone")) {
					// this country gets ignored, because it was split in
					// several other countries and so it can not be shown on the
					// GeoMap.
					// If it would not be ignored and also not shown, then it
					// would have influence on the color scale of the GeoMap.
				} else if (country.equals("SovietUnion")) {
					// this country gets ignored, because it was split in
					// several other countries and so it can not be shown on the
					// GeoMap.
					// If it would not be ignored and also not shown, then it
					// would have influence on the color scale of the GeoMap.
				} else if (country.equals("Singapore")) {
					// this country gets ignored, because it is not shown on the
					// GeoMap. If it would not be ignored and also not
					// shown, then it would have influence on the color scale of
					// the GeoMap.
				} else if (country.equals("Yugoslavia")) {
					// this country gets ignored, because it was split in
					// several other countries and so it can not be shown on the
					// GeoMap.
					// If it would not be ignored and also not shown, then it
					// would have influence on the color scale of the GeoMap.
				}

				else {
					InputObjectWorldMap newInputObject = new InputObjectWorldMap(correctCountry, inputData.get(i).getAggregatedNumberOfMovies(),
							correctCountry);
					outputList.add(newInputObject);
				}

				i++;
			}
		}

		return outputList;
	}

	/**
	 * @author: Patrick Muntwyler this method tests if there is an
	 *          InputObjectWorldMap-Object in the ArrayList, which has the same
	 *          OSIcountryName compared to the input String countryNameToTest.
	 *          If there is such an Object, this method returns true, else it
	 *          returns false.
	 * 
	 * @param the
	 *            ArrayList in which you search the object and a String which
	 *            represents the searched country
	 * @return true if such an object exist, else false
	 */

	private boolean arrayListContainsCountry(ArrayList<InputObjectWorldMap> listToTest, String countryNameToTest) {

		int size = listToTest.size();
		int i = 0;

		// returns false if the ArrayList<InputObjectWorldMap> does NOT contain
		// an object of the tested country
		boolean result = false;

		while (i < size && result == false) {
			if (listToTest.get(i).getOSICountryName().equals(countryNameToTest)) {
				result = true;
			}
			i++;
		}

		return result;
	}

	/**
	 * @author: Patrick Muntwyler this method tests if there is an
	 *          InputObjectWorldMap-Object in the ArrayList, which has the same
	 *          OSIcountryName compared to the input String searchedCountryName.
	 *          If there is such an Object, this method returns the index of
	 *          this object, else it returns -1.
	 * 
	 * @param the
	 *            ArrayList in which you search the object and a String which
	 *            represents the searched country
	 * @return an int which represents the index of the searched object in the
	 *         ArrayList. If there is no such object, it returns -1
	 */
	private int getIndex(ArrayList<InputObjectWorldMap> list, String searchedCountryName) {

		int size = list.size();
		int z = 0;
		// return -1 if the searched object is not in the arrayList
		int searchedIndex = -1;

		while (z < size && searchedIndex == -1) {
			if (list.get(z).getOSICountryName().equals(searchedCountryName)) {
				searchedIndex = z;
			}
			z++;
		}

		return searchedIndex;
	}

	/**
	 * @author: Patrick Muntwyler
	 * 
	 *          this method takes the object X from the "inputData" ArrayList at
	 *          index "index" and tests if the "outputList" ArrayList contains
	 *          an object Y with the OSICountryName which you put as the forth
	 *          parameter in this method "OSICountryName". If such an object Y
	 *          exists, then the method adds the aggregatedNumberOfMovies of
	 *          object X to the numberOfMovies of object Y. If such an object Y
	 *          does not exist, then this method constructs an
	 *          InputObjectWorldMap-object, with OSICountryName which you put in
	 *          as forth parameter "OSICountryName", with numberOfMovies which
	 *          is equal to the aggregatedNumberOfMovies from object X and with
	 *          showedCountryName which you put in as fifth parameter
	 *          "showedCountryName" and adds it to the "outputList" ArrayList.
	 * 
	 * 
	 * @param inputData
	 *            is an ArrayList<DataResultAggregated> which contains the
	 *            object which is an
	 * @param outputList
	 * @param index
	 * @param OSICountryName
	 * @param showedCountryName
	 * @return
	 */

	private ArrayList<InputObjectWorldMap> handleExceptionalCountry(ArrayList<DataResultAggregated> inputData,
			ArrayList<InputObjectWorldMap> outputList, int index, String OSICountryName, String showedCountryName) {

		/*
		 * if in the ArrayList<InputObjectWorldMap> an object with the entered
		 * OSICountryName already exists, then the aggregatedNumberOfMovies of
		 * the object in the ArrayList<DataResultAggregated> are added to the
		 * numberOfMovies of the ArrayList<InputObjectWorldMap> object.
		 */
		if (arrayListContainsCountry(outputList, OSICountryName)) {
			int indexOfSearchedObject = getIndex(outputList, OSICountryName);
			int numberOfMoviesToAdd = inputData.get(index).getAggregatedNumberOfMovies();
			outputList.get(indexOfSearchedObject).addNumberOfMovies(numberOfMoviesToAdd);
		}
		/*
		 * if no such an object in the ArrayList<InputObjectWorldMap> exists,
		 * then a new object will be instantiated with the entered
		 * OSICountryName, the aggregatedNumberOfMovies of the
		 * ArrayList<DataResultAggregated> inputData object and the entered
		 * showedCountryName. This new object will be added to the
		 * ArrayList<InputObjectWorldMap>.
		 */
		else {
			InputObjectWorldMap Aruba = new InputObjectWorldMap(OSICountryName, inputData.get(index).getAggregatedNumberOfMovies(), showedCountryName);
			outputList.add(Aruba);
		}

		return outputList;
	}

}
