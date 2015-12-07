/**
 * This class includes the methods which are required for the country name handling for the classes WorldMap and PieChartWebPage.
 * Because both classes need the methods, it makes sense to create a new class which only includes the methods. 
 */

package movie.db.client;

import java.util.ArrayList;

import movie.db.shared.DataResultAggregated;

public abstract class CountryNameHandling {
	
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
	 * @param inputData:
	 *            An ArrayList of the type DataResultAggregated which is
	 *            provided by the query
	 * @param forWorldMap:
	 * 			  A boolean, which is true, if the method is used by the WorldMap class and is false if the method
	 * 			  is used by the PieChartWebPage class. This is because some countries are ignored by the GeoMap (in
	 * the WorldMap class) and these countries should not be ignored for the pie chart in the PieChartWebPage class.           
	 * @return An ArrayList of the type InputObjectWorldMap which is required to
	 *         build the DataTable for the GeoMap
	 */

	public static ArrayList<InputObjectWorldMap> createInputArrayList(
			ArrayList<DataResultAggregated> inputData, boolean forWorldMap) {

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
				if (country.equals("") == false) {
					if (country.equals("Aruba")) {
						outputList = handleExceptionalCountry(inputData, outputList, i, "Netherlands", "Netherlands");
					}

					else if (country.equals("Netherlands")) {
						outputList = handleExceptionalCountry(inputData, outputList, i, "Netherlands", "Netherlands");
					}

					else if (country.equalsIgnoreCase("UnitedStatesofAmerica")) {
						outputList = handleExceptionalCountry(inputData, outputList, i, "United States",
								"United States of America");

					} else if (country.equalsIgnoreCase("BosniaandHerzegovina")) {
						outputList = handleExceptionalCountry(inputData, outputList, i, "BA", "Bosnia and Herzegovina");

					} else if (country.equals("Burma")) {
						outputList = handleExceptionalCountry(inputData, outputList, i, "Myanmar",
								"Myanmar (also Burma)");

					} else if (country.equals("Congo")) {
						outputList = handleExceptionalCountry(inputData, outputList, i, "CD",
								"the Democratic Republic of the Congo");

					} else if (country.equals("DemocraticRepublicoftheCongo")) {
						outputList = handleExceptionalCountry(inputData, outputList, i, "CD",
								"the Democratic Republic of the Congo");

					} else if (country.equals("England")) {
						outputList = handleExceptionalCountry(inputData, outputList, i, "GB",
								"the United Kingdom of Great Britain and Northern Ireland");

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
						outputList = handleExceptionalCountry(inputData, outputList, i, "GB",
								"the United Kingdom of Great Britain and Northern Ireland");

					} else if (country.equals("KingdomofGreatBritain")) {
						outputList = handleExceptionalCountry(inputData, outputList, i, "GB",
								"the United Kingdom of Great Britain and Northern Ireland");

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
						outputList = handleExceptionalCountry(inputData, outputList, i, "GB",
								"the United Kingdom of Great Britain and Northern Ireland");

					} else if (country.equals("RepublicofChina")) {
						outputList = handleExceptionalCountry(inputData, outputList, i, "China", "China");

					} else if (country.equals("RepublicofMacedonia")) {
						outputList = handleExceptionalCountry(inputData, outputList, i, "MK", "Macedonia");

					} else if (country.equals("Scotland")) {
						outputList = handleExceptionalCountry(inputData, outputList, i, "GB",
								"the United Kingdom of Great Britain and Northern Ireland");

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
						outputList = handleExceptionalCountry(inputData, outputList, i, "GB",
								"the United Kingdom of Great Britain and Northern Ireland");

					} else if (country.equals("Uzbekistan")) {
						outputList = handleExceptionalCountry(inputData, outputList, i, "Uzbekistan", "Uzbekistan");

					} else if (country.equals("UzbekSSR")) {
						outputList = handleExceptionalCountry(inputData, outputList, i, "Uzbekistan", "Uzbekistan");

					} else if (country.equals("Wales")) {
						outputList = handleExceptionalCountry(inputData, outputList, i, "GB",
								"the United Kingdom of Great Britain and Northern Ireland");

					} else if (country.equals("WeimarRepublic")) {
						outputList = handleExceptionalCountry(inputData, outputList, i, "Germany", "Germany");

					} else if (country.equals("WestGermany")) {
						outputList = handleExceptionalCountry(inputData, outputList, i, "Germany", "Germany");

					} else if (country.equals("Bahrain") && forWorldMap) {
						// this country gets ignored, because it is not shown on
						// the
						// GeoMap. If it would not be ignored and also not
						// shown, then it would have influence on the color
						// scale of
						// the GeoMap.
					} else if (country.equals("Czechoslovakia") && forWorldMap) {
						// this country gets ignored, because it was split in
						// several other countries and so it can not be shown on
						// the
						// GeoMap.
						// If it would not be ignored and also not shown, then
						// it
						// would have influence on the color scale of the
						// GeoMap.
					} else if (country.equals("FederalRepublicofYugoslavia") && forWorldMap) {
						// this country gets ignored, because it was split in
						// several other countries and so it can not be shown on
						// the
						// GeoMap.
						// If it would not be ignored and also not shown, then
						// it
						// would have influence on the color scale of the
						// GeoMap.
					} else if (country.equals("Malta") && forWorldMap) {
						// this country gets ignored, because it is not shown on
						// the
						// GeoMap. If it would not be ignored and also not
						// shown, then it would have influence on the color
						// scale of
						// the GeoMap.
					} else if (country.equals("MandatoryPalestine") && forWorldMap) {
						// this country gets ignored, because it is not shown on
						// the
						// GeoMap. If it would not be ignored and also not
						// shown, then it would have influence on the color
						// scale of
						// the GeoMap.
					} else if (country.equals("Monaco") && forWorldMap) {
						// this country gets ignored, because it is not shown on
						// the
						// GeoMap. If it would not be ignored and also not
						// shown, then it would have influence on the color
						// scale of
						// the GeoMap.
					} else if (country.equals("Palestinianterritories") && forWorldMap) {
						// this country gets ignored, because it is not shown on
						// the
						// GeoMap. If it would not be ignored and also not
						// shown, then it would have influence on the color
						// scale of
						// the GeoMap.
					} else if (country.equals("SerbiaandMontenegro") && forWorldMap) {
						// this country gets ignored, because it was split in
						// several other countries and so it can not be shown on
						// the
						// GeoMap.
						// If it would not be ignored and also not shown, then
						// it
						// would have influence on the color scale of the
						// GeoMap.
					} else if (country.equals("SocialistFederalRepublicofYugoslavia") && forWorldMap) {
						// this country gets ignored, because it was split in
						// several other countries and so it can not be shown on
						// the
						// GeoMap.
						// If it would not be ignored and also not shown, then
						// it
						// would have influence on the color scale of the
						// GeoMap.
					} else if (country.equals("Sovietoccupationzone") && forWorldMap) {
						// this country gets ignored, because it was split in
						// several other countries and so it can not be shown on
						// the
						// GeoMap.
						// If it would not be ignored and also not shown, then
						// it
						// would have influence on the color scale of the
						// GeoMap.
					} else if (country.equals("SovietUnion") && forWorldMap) {
						// this country gets ignored, because it was split in
						// several other countries and so it can not be shown on
						// the
						// GeoMap.
						// If it would not be ignored and also not shown, then
						// it
						// would have influence on the color scale of the
						// GeoMap.
					} else if (country.equals("Singapore") && forWorldMap) {
						// this country gets ignored, because it is not shown on
						// the
						// GeoMap. If it would not be ignored and also not
						// shown, then it would have influence on the color
						// scale of
						// the GeoMap.
					} else if (country.equals("Yugoslavia") && forWorldMap) {
						// this country gets ignored, because it was split in
						// several other countries and so it can not be shown on
						// the
						// GeoMap.
						// If it would not be ignored and also not shown, then
						// it
						// would have influence on the color scale of the
						// GeoMap.
					}

					else {
						InputObjectWorldMap newInputObject = new InputObjectWorldMap(correctCountry,
								inputData.get(i).getAggregatedNumberOfMovies(), correctCountry);
						outputList.add(newInputObject);
					}
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

	private static boolean arrayListContainsCountry(ArrayList<InputObjectWorldMap> listToTest,
			String countryNameToTest) {

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
	static int getIndex(ArrayList<InputObjectWorldMap> list, String searchedCountryName) {

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
	 * @return ArrayList of type InputObjectWorldMap which will be used to generate the DataTable for the GeoMap
	 */

	private static ArrayList<InputObjectWorldMap> handleExceptionalCountry(ArrayList<DataResultAggregated> inputData,
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
			InputObjectWorldMap Aruba = new InputObjectWorldMap(OSICountryName,
					inputData.get(index).getAggregatedNumberOfMovies(), showedCountryName);
			outputList.add(Aruba);
		}

		return outputList;
	}
	

}
