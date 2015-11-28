package movie.db.client;

import java.util.ArrayList;

import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart.PieOptions;

import movie.db.shared.DataResultAggregated;

public class PieChartWebPage {

	private PieChart pieChart;
	private DataTable dataTable;
	private PieOptions options;

	public PieChartWebPage(ArrayList<DataResultAggregated> list) {
		super();
		options = PieChart.createPieOptions();
		dataTable = generateDataTable(list);
		pieChart = new PieChart(dataTable, options);
	}
	
	

	public PieChart getPieChart() {
		return pieChart;
	}



	public DataTable getDataTable() {
		return dataTable;
	}



	public PieOptions getOptions() {
		return options;
	}

	/**
	 * @author Patrick Muntwyler Generates a DataTable which will be used by the
	 *         PieChart
	 * 
	 * @pre Database query must have delivered some results
	 * @param ArrayList<DataResultAggregated>
	 *            data
	 * @post DataTable contains the result entered in the different columns
	 */

	private DataTable generateDataTable(ArrayList<DataResultAggregated> data) {
		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.STRING, "country name");
		dataTable.addColumn(ColumnType.NUMBER, "number of movies");
		ArrayList<InputObjectWorldMap> inputList = CountryNameHandling.createInputArrayList(data, false);


		if (inputList.isEmpty()==true) {
			dataTable.addRow();
		} else {
			int numberOfPieChartSegments = 8;
			int z = 0;
			while(z < numberOfPieChartSegments-1 && inputList.isEmpty() == false){
				int indexOfMaxNumberOfMovies = getIndexOfMaxNumberOfMovies(inputList);
				dataTable.addRow();
				dataTable.setValue(z, 0, inputList.get(indexOfMaxNumberOfMovies).getShowedCountryName());
				dataTable.setValue(z, 1, inputList.get(indexOfMaxNumberOfMovies).getNumberOfMovies());
				inputList.remove(indexOfMaxNumberOfMovies);
				z++;	
			}
			if(inputList.isEmpty() == false){
				dataTable.addRow();
				dataTable.setValue(numberOfPieChartSegments-1, 0, "other countries");
				dataTable.setValue(numberOfPieChartSegments-1, 1, getOverallNumberOfMovies(inputList));
			}
			
		}
		return dataTable;
	}
	
	/**
	 * @author Patrick Muntwyler
	 * @param ArrayList<DataResultAggregated>
	 *            which is needed to fill the 2D-String-Array. This
	 *            2D-String-Array replaces the DataTable which is needed for the
	 *            GeoMap. Because JUnit Tests don't work with this DataTable
	 *            this 2D-String-Array should simulate the DataTable. This
	 *            method is only for the JUnit Test. It is not needed for the
	 *            webpage.
	 * @return	filled DataTable, which is needed for the pie chart.
	 */
	
	public String[][] generateDataTableTest(ArrayList<DataResultAggregated> data) {

		ArrayList<InputObjectWorldMap> inputList = CountryNameHandling.createInputArrayList(data, false);
		int numberOfPieChartSegments = 8;

		// this line substitutes "DataTable dataTable = DataTable.create();
		// dataTable.addColumn(ColumnType.STRING, "country name");
		// dataTable.addColumn(ColumnType.NUMBER, "number of movies");"
		String[][] testArray = new String[numberOfPieChartSegments][2];

		
		if (inputList.isEmpty()==true) {
			// when working with arrays it's not important to monitor this case
			// in the generateDataTable method here you find the following line
			// "dataTable.addRow();".
			// this is important because the GeoMap cannot work with a
			// DataTable, which has 0 rows.
			// So we added just a empty row.
		} else {
			int z = 0;
			while (z < numberOfPieChartSegments-1 && inputList.isEmpty() == false) {
				int indexOfMaxNumberOfMovies = getIndexOfMaxNumberOfMovies(inputList);
						
				// this line substitutes "dataTable.setValue(z, 0, inputList.get(indexOfMaxNumberOfMovies).getShowedCountryName());"
				testArray[z][0] = inputList.get(indexOfMaxNumberOfMovies).getShowedCountryName();
				// this line substitutes "dataTable.setValue(z, 1, inputList.get(indexOfMaxNumberOfMovies).getNumberOfMovies());"
				testArray[z][1] = inputList.get(indexOfMaxNumberOfMovies).getNumberOfMovies() + "";
				inputList.remove(indexOfMaxNumberOfMovies);
				z++;
			}
			if(inputList.isEmpty() == false){
				
				//this line substitutes "dataTable.setValue(numberOfPieChartSegments-1, 0, "other countries");"
				testArray[numberOfPieChartSegments-1][0] = "other countries";
				//this line substitutes "dataTable.setValue(numberOfPieChartSegments-1, 1, getOverallNumberOfMovies(inputList));"
				testArray[numberOfPieChartSegments-1][1] = getOverallNumberOfMovies(inputList) + "";
			}
		}
		return testArray;
	}
	
	/**
	 * This method returns the index of the object in the ArrayList which has the highest numberOfMovies value.
	 * If there are two objects with the same value, the index of the first object will be returned. 
	 * If the ArrayList is empty, the method returns -1;
	 * @author Patrick Muntwyler  
	 * @param list
	 * @return int, which represents the index of the desired object.
	 */

	private int getIndexOfMaxNumberOfMovies(ArrayList<InputObjectWorldMap> list) {
		int index = -1;
		int size = list.size();
		int k = 0;
		if (size == 0) {
			return index;
		} else {
			index = 0;
			while (k < size) {
				if (list.get(k).getNumberOfMovies() > list.get(index).getNumberOfMovies()) {
					index = k;
				}
				k++;
			}
			return index;
		}

	}
	
	/**
	 * This method adds all numberOfMovies of every InputObjectWorldMap-objects which are contained in the
	 * ArrayList and returns the summed up value. If the ArrayList is empty, the method returns 0.
	 * @author Patrick Muntwyler
	 * @param list. ArrayList of Type InputObjectWorldMap
	 * @return an int
	 */
	
	private int getOverallNumberOfMovies(ArrayList<InputObjectWorldMap> list){
		
		int overallNumberOfMovies = 0;
		int k = 0;
		while(k<list.size()){
			overallNumberOfMovies = overallNumberOfMovies + list.get(k).getNumberOfMovies();
			k++;
		}
		return overallNumberOfMovies;
	}

}
