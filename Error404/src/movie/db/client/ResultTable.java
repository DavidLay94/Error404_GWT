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

public class ResultTable {

	private Table resultTable;
	private DataTable dataTable;
	private Options options;

	public ResultTable(Map<Integer, DataResultShared> result) {
		super();
		dataTable = generateDataTable(result);
		options = Options.create();
		options.setPage(Policy.ENABLE);
		options.setPageSize(20);
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

	public DataTable generateDataTable(Map<Integer, DataResultShared> result) {

		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.STRING, "Movie");
		dataTable.addColumn(ColumnType.NUMBER, "Year");
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
					dataTable.setValue(z, 1, entry.getYear());
					dataTable.setValue(z, 2, arrayListToStringConverter(entry.getGenres()));
					dataTable.setValue(z, 3, arrayListToStringConverter(entry.getLanguages()));
					dataTable.setValue(z, 4, arrayListToStringConverter(entry.getCountries()));
					z++;
				}
			}

		}

		catch (Exception ex1) {
			Window.alert(ex1.toString());
		}
		return dataTable;
	}

	private String arrayListToStringConverter(ArrayList<String> alist) {
		String returnString = "";
		for (String s : alist) {
			returnString = returnString + s + ", ";
		}
		returnString = returnString.substring(0, returnString.length() - 2);
		return returnString;

	}

	

}

