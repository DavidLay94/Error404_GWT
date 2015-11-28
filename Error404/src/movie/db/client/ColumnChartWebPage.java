package movie.db.client;

import java.util.ArrayList;
import java.util.Map;

import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.visualizations.corechart.AxisOptions;
import com.google.gwt.visualization.client.visualizations.corechart.ColumnChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;

import movie.db.shared.DataResultAggregated;

public class ColumnChartWebPage {

	private ColumnChart columnChart;
	private DataTable dataTable;
	private Options options;

	public ColumnChartWebPage(Map<Integer, Integer> inputMap, int selectedYearFrom, int selectedYearTo, String country,
			String genre) {
		super();
		options = Options.create();
		if (genre == null) {
			options.setTitle(
					"Number of movies from " + country + " from " + selectedYearFrom + " to " + selectedYearTo);
		} else {
			options.setTitle("Number of " + genre + " movies from " + country + " from " + selectedYearFrom + " to "
					+ selectedYearTo);
		}
		AxisOptions hAxisOption = AxisOptions.create();
		AxisOptions vAxisOption = AxisOptions.create();
		vAxisOption.setMinValue(0);
		//vAxisOption.set("format", "#");
		hAxisOption.setTextPosition("none");
		options.setHAxisOptions(hAxisOption);
		options.setVAxisOptions(vAxisOption);
		dataTable = generateDataTable(inputMap, selectedYearFrom, selectedYearTo);
		columnChart = new ColumnChart(dataTable, options);
	}

	public ColumnChart getColumnChart() {
		return columnChart;
	}

	public DataTable getDataTable() {
		return dataTable;
	}

	public Options getOptions() {
		return options;
	}

	/**
	 * Generates the DataTable which is needed for the column chart. It
	 * generates 2 columns in this DataTable. Column zero is for the year and
	 * column one is for the number of movies in this year.
	 * 
	 * @author Patrick Muntwyler
	 * @param inputMap:
	 *            a Hash Map with key as current year and value as the number of
	 *            movies in that current year
	 * @param selectedYearFrom
	 * @param selectedYearTo
	 * @return
	 */

	private DataTable generateDataTable(Map<Integer, Integer> inputMap, int selectedYearFrom, int selectedYearTo) {
		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.NUMBER, "Year");
		dataTable.addColumn(ColumnType.NUMBER, "Number of Movies");

	
		int currentRow = 0;
		int currentYear = selectedYearFrom;
		while (currentYear <= selectedYearTo) {
			dataTable.addRow();
			dataTable.setValue(currentRow, 0, currentYear);
			if (inputMap.containsKey(currentYear)) {
				dataTable.setValue(currentRow, 1, inputMap.get(currentYear));
			} else {
				dataTable.setValue(currentRow, 1, 0);
			}
			currentRow++;
			currentYear++;
		}
		// }

		return dataTable;
	}

	/**
	 * @author Patrick Muntwyler
	 * @param inputMap
	 *            Map<Integer, Integer> which is needed to fill the
	 *            2D-String-Array. This 2D-String-Array replaces the DataTable
	 *            which is needed for the GeoMap. Because JUnit Tests don't work
	 *            with this DataTable this 2D-String-Array should simulate the
	 *            DataTable. This method is only for the JUnit Test. It is not
	 *            needed for the webpage.
	 * @param selectedYearFrom
	 * @param selectedYearTo
	 * @return filled DataTable, which is needed for the column chart.
	 */

	public static int[][] generateDataTableTest(Map<Integer, Integer> inputMap, int selectedYearFrom,
			int selectedYearTo) {

		int numberOfYears = selectedYearTo - selectedYearFrom + 1;

		// this line substitutes "DataTable dataTable = DataTable.create();
		// dataTable.addColumn(ColumnType.STRING, "country name");
		// dataTable.addColumn(ColumnType.NUMBER, "number of movies");"
		int[][] testArray = new int[numberOfYears][2];

 
			int currentRow = 0;
			int currentYear = selectedYearFrom;
			while (currentYear <= selectedYearTo) {
				testArray[currentRow][0] = currentYear;
				if (inputMap.containsKey(currentYear)) {
					//this line substitutes "dataTable.setValue(currentRow, 1, inputMap.get(currentYear));"
					testArray[currentRow][1] = inputMap.get(currentYear);
				} else {
					//this line substitutes "dataTable.setValue(currentRow, 1, 0);"
					testArray[currentRow][1] = 0;
				}
				
				
				currentRow++;
				currentYear++;

			}

		
		return testArray;
	}

}
