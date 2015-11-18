package movie.db.client;

import java.util.ArrayList;
import java.util.Map;

import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.user.client.Window;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.visualizations.Table;
import com.google.gwt.visualization.client.visualizations.Table.Options;
import com.google.gwt.visualization.client.visualizations.Table.Options.Policy;

import movie.db.shared.DataResultShared;

/**
 * This class is needed for the visualization in table format after the database delivered the movies.
 */
public class ResultTable {

	private Table resultTable;
	private DataTable dataTable;
	private Options options;

	public ResultTable(Map<Integer, DataResultShared> result) {
		super();
		dataTable = generateDataTable(result);
		options = Options.create();
		options.setPage(Policy.ENABLE);
		options.setPageSize(15);
		resultTable = new Table();
	}

	public Table getResultTable() {
		return resultTable;
	}

	public DataTable getDataTable() {
		return dataTable;
	}

	public Options getOptions() {
		return options;
	}

	/**
	 * Generates a datatable which can be used by a widget and then be shown on
	 * the user interface
	 * 
	 * @Author Christoph Weber
	 * @pre Database query must have delivered some results
	 * @param Map <Integer, DataResultShared> result
	 * @post datatable contains the result entered in the different columns
	 */
	public DataTable generateDataTable(Map<Integer, DataResultShared> result) {

		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.STRING, "Movie");
		dataTable.addColumn(ColumnType.NUMBER, "Year");
		dataTable.addColumn(ColumnType.NUMBER, "Duration");
		dataTable.addColumn(ColumnType.STRING, "Genres");
		dataTable.addColumn(ColumnType.STRING, "Languages");
		dataTable.addColumn(ColumnType.STRING, "Countries");
		dataTable.addRow();

		try {
			int size = result.size();
			int z = 0;

			if (size > 0) {
				dataTable.removeRow(0);
				for (DataResultShared entry : result.values()) {
					dataTable.addRow();
					dataTable.setValue(z, 0, entry.getMovieName());
					if (entry.getYear() != -1) {
						dataTable.setValue(z, 1, entry.getYear());
					}
					if (entry.getDuration() != -1) {
						dataTable.setValue(z, 2, entry.getDuration());
					}
					dataTable.setValue(z, 3,
							arrayListToStringConverter(entry.getGenres()));
					dataTable.setValue(z, 4,
							arrayListToStringConverter(entry.getLanguages()));
					dataTable.setValue(z, 5,
							arrayListToStringConverter(entry.getCountries()));
					z++;
				}
			}

		}

		catch (Exception ex1) {
			Window.alert(ex1.toString());
		}
		return dataTable;
	}

	/**
	 * Concatenates all String entries of an ArrayList together
	 * 
	 * @Author Christoph Weber
	 * @pre input ArrayList<String> is not null
	 * @param ArrayList <String> alist
	 * @post String contains all entries concateneted together
	 */
	private String arrayListToStringConverter(ArrayList<String> alist) {
		String returnString = " ";
		if (!alist.isEmpty()) {
			for (String s : alist) {
				returnString = returnString + s + ", ";
			}
			returnString = returnString.substring(0, returnString.length() - 2);
		}
		return returnString;
	}

}
