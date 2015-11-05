package movie.db.shared;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import com.google.gwt.user.client.ui.FlexTable;

import movie.db.client.Error404;
import movie.db.server.DataResult;
import movie.db.server.Query;

public class SharedStuff {
	private Error404 e404 = new Error404();

	public void onModuleLoad() {
		e404.initializePanels();
	}

	public FlexTable someFunction() {
		Query sqlQuery = new Query();

		Map<Integer, DataResult> resultMap = null;
		resultMap = sqlQuery.getAllData();

		FlexTable resultFlexTable = new FlexTable();
		resultFlexTable.setText(0, 0, "Movie");
		resultFlexTable.setText(0, 1, "Year");
		resultFlexTable.setText(0, 2, "Countrie(s)");
		resultFlexTable.setText(0, 3, "Genre(s)");
		resultFlexTable.setText(0, 4, "Language(s)");
		int i = 1;
		for (Map.Entry<Integer, DataResult> result : resultMap.entrySet()) {
			resultFlexTable.setText(i, 0, result.getValue().getMovieName());
			resultFlexTable.setText(i, 1,
					String.valueOf(result.getValue().getYear()));
			resultFlexTable.setText(i, 2, convertArrayListToString(result
					.getValue().getCountries()));
			resultFlexTable.setText(i, 3, convertArrayListToString(result
					.getValue().getGenres()));
			resultFlexTable.setText(i, 4, convertArrayListToString(result
					.getValue().getLanguages()));
			i++;
		}
		return resultFlexTable;
	}

	private String convertArrayListToString(ArrayList<String> arrayList) {
		String returnString = "";
		for (String s : arrayList) {
			returnString = returnString + s + ", ";
		}
		returnString = returnString.substring(0, returnString.length() - 1); // removes
																				// ,
																				// at
																				// the
																				// end
		return returnString;
	}
}
