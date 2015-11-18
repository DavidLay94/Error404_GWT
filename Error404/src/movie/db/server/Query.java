package movie.db.server;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import movie.db.client.MyService;
import movie.db.shared.DataResultAggregated;
import movie.db.shared.DataResultShared;
import movie.db.shared.Duration;
import movie.db.shared.Selection;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/*
 * The class offers database access to the client package so queries can be processed and be formed into suitable 
 * Objects which are sent back to the client side 
 */
public class Query extends RemoteServiceServlet implements MyService {

	private static final Logger logger = Logger.getLogger("Query");

	/**
	 * Generates a Map<Integer, DataResultShared> with the movie-ID as key and
	 * the data-result with holding the metadata of the different movies.
	 * 
	 * @Author Christoph Weber
	 * @pre a Selection object must have been created. selection != null
	 * @param Selection selection
	 * @post Map<Integer, DataResultShared> contains every movie with it's
	 *       metadata corresponding to the selection
	 */
	@SuppressWarnings("finally")
	public Map<Integer, DataResultShared> getFilteredData(Selection selection) {
		Map<Integer, DataResultShared> dataResultMap = new HashMap<Integer, DataResultShared>();

		String sqlClause = selectionToSQLWhereClause(selection);

		// /////////////////
		/*
		 * DataResultShared errorRes = new DataResultShared();
		 * errorRes.setMovieName("TEST123"); dataResultMap.put(1, errorRes);
		 * return dataResultMap;
		 */
		// /////////////////
		logger.log(Level.INFO, sqlClause);
		try {
			Connection connection = ConnectionConfiguration.getConnection();

			Statement statement = null;

			try {
				statement = connection.createStatement();

				String sqlQuery = "SELECT id, name, year, duration, country, language, genre FROM moviesAllInOne "
						+ sqlClause + " ORDER BY name"; // LIMIT 0,500";

				ResultSet queryResult = statement.executeQuery(sqlQuery);

				int movieId;
				String movieName;
				int year;
				int duration;
				String countriesSeparated;
				String languagesSeparated;
				String genresSeparated;

				while (queryResult.next()) {

					// Retrieve data by column name
					movieId = queryResult.getInt("id");
					movieName = queryResult.getString("name");
					year = queryResult.getInt("year");
					duration = queryResult.getInt("duration");
					countriesSeparated = queryResult.getString("country");
					languagesSeparated = queryResult.getString("language");
					genresSeparated = queryResult.getString("genre");

					DataResultShared movie = new DataResultShared();
					movie.setMovieName(movieName);
					movie.setYear(year);
					movie.setDuration(duration);
					if (countriesSeparated != null) {
						ArrayList<String> countries = new ArrayList<String>();
						countries.addAll(Arrays.asList(countriesSeparated
								.split(",")));
						movie.setCountries(countries);
					}
					if (languagesSeparated != null) {
						ArrayList<String> languages = new ArrayList<String>();
						languages.addAll(Arrays.asList(languagesSeparated
								.split(",")));
						movie.setLanguages(languages);
					}
					if (genresSeparated != null) {
						ArrayList<String> genres = new ArrayList<String>();
						genres.addAll(Arrays.asList(genresSeparated.split(",")));
						movie.setGenres(genres);
					}

					dataResultMap.put(movieId, movie);
				}

			} catch (SQLException e1) {

				DataResultShared errorRes = new DataResultShared();
				errorRes.setMovieName(e1.getMessage());
				errorRes.setMovieName("SQL EXCEPTION");
				dataResultMap.put(1, errorRes);
			} catch (IndexOutOfBoundsException e2) {
				DataResultShared errorRes = new DataResultShared();
				errorRes.setMovieName(e2.getMessage());
				errorRes.setMovieName("INDEX OUT OF BOUNDS");
				dataResultMap.put(1, errorRes);
			} catch (Exception e3) {
				DataResultShared errorRes = new DataResultShared();
				// errorRes.setMovieName("NOCLEAN");
				errorRes.setMovieName(e3.toString());
				dataResultMap.put(1, errorRes);

				int j = 2;
				for (StackTraceElement t : e3.getStackTrace()) {
					DataResultShared tEle = new DataResultShared();
					tEle.setMovieName(t.toString());
					dataResultMap.put(j, tEle);
					j++;
				}
			} finally {
				try {
					statement.close();
				} catch (Exception e) {
				}
				try {
					connection.close();
				} catch (Exception e) {
				}

				return dataResultMap;
			}
		} catch (Exception ex) {
			DataResultShared errorRes = new DataResultShared();
			errorRes.setMovieName(ex.toString());
			dataResultMap.put(1, errorRes);
			DataResultShared reason = new DataResultShared();
			reason.setMovieName(ex.getCause().toString());
			dataResultMap.put(2, reason);

			int j = 3;
			for (StackTraceElement t : ex.getStackTrace()) {
				DataResultShared tEle = new DataResultShared();
				tEle.setMovieName(t.toString());
				dataResultMap.put(j, tEle);
				j++;
			}
			return dataResultMap;
		}
	}

	/**
	 * Generates a ArrayList<DataResultAggregated> which is used as input to
	 * show the number of movies per country on an interactive worldmap.
	 * 
	 * @Author Christoph Weber
	 * @pre year must be a valid int
	 * @param int selectedYear
	 * @post ArrayList<DataResultAggregated> contains every country with the
	 *       amount of movies generated in it
	 */
	@SuppressWarnings("finally")
	public ArrayList<DataResultAggregated> getWorldMapData(int selectedYear) {
		ArrayList<DataResultAggregated> resultArray = new ArrayList<DataResultAggregated>();

		String sqlQuery = "SELECT country, count(country) AS \"amount\" FROM moviesAllInOne WHERE year = "
				+ selectedYear + " group by country";

		try {
			Connection connection = ConnectionConfiguration.getConnection();
			Statement statement = null;
			try {
				statement = connection.createStatement();
				ResultSet queryResult = statement.executeQuery(sqlQuery);
				
				Map<String, Integer> aggregatedCountries = new HashMap<String, Integer>();
				while (queryResult.next()) {
					for(String singleCountry : queryResult.getString("country").split(",")){
						if(aggregatedCountries.containsKey(singleCountry)){
							aggregatedCountries.put(singleCountry,aggregatedCountries.get(singleCountry) + queryResult.getInt("amount"));
						}else{
							aggregatedCountries.put(singleCountry,queryResult.getInt("amount"));
						}
					}	
				}
				for(Map.Entry<String,Integer> kvp : aggregatedCountries.entrySet()){
					DataResultAggregated dataresult = new DataResultAggregated();
					dataresult.setCountryName(kvp.getKey());
					dataresult.setAggregatedNumberOfMovies(kvp.getValue());
					resultArray.add(dataresult);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					statement.close();
				} catch (Exception e) {
				}
				try {
					connection.close();
				} catch (Exception e) {
				}
			}
		} catch (Exception ex) {
		}
		return resultArray;
	}

	/**
	 * Generates a ArrayList<String> which is used to fill the
	 * selection-listboxes in the UI.
	 * 
	 * @Author Christoph Weber
	 * @pre Input "column" must be a valid column in the database-table
	 * @param String
	 *            column
	 * @post ArrayList<String> contains every entry in the selected column
	 */
	@SuppressWarnings("finally")
	public ArrayList<String> getColumnEntries(String column) {
		ArrayList<String> resultArrayList = new ArrayList<String>();

		String sqlQuery = "SELECT " + column + " FROM moviesAllInOne group by "
				+ column;

		try {
			Connection connection = ConnectionConfiguration.getConnection();
			Statement statement = null;
			try {
				statement = connection.createStatement();
				ResultSet queryResult = statement.executeQuery(sqlQuery);

				while (queryResult.next()) {
					ArrayList<String> aList = new ArrayList<String>();
					aList.addAll(Arrays.asList(queryResult.getString(column)
							.split(",")));

					for (String s : aList) {
						if (!resultArrayList.contains(s))
							resultArrayList.add(s);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					statement.close();
				} catch (Exception e) {
				}
				try {
					connection.close();
				} catch (Exception e) {
				}
				Collections.sort(resultArrayList);
				resultArrayList.remove(" ");
				return resultArrayList;
			}
		} catch (Exception ex) {
			return resultArrayList;
		}
	}

	/**
	 * Generates a String in the correct syntax which can be put in the
	 * WHERE-clause of an SQL-query
	 * 
	 * @Author Christoph Weber
	 * @pre every Input (including null) of type Selection is accepted.
	 * @param Selection
	 *            selection
	 * @post String must be in proper syntax to be added in the part of the
	 *       "WHERE-clause" of the sql query
	 */
	public String selectionToSQLWhereClause(Selection selection) {
		String selectionSQLWhereClause;
		if (selection == null) {
			selectionSQLWhereClause = " ";
		} else {
			selectionSQLWhereClause = " WHERE 1 = 1 ";

			if(selection.getSelectedMovieName() != null){
				if(selection.getSelectedMovieName().length() > 0){
				selectionSQLWhereClause = selectionSQLWhereClause + "AND name like '" 
						+ selection.getSelectedMovieName() + "' ";
				}				
			}
			if(selection.getSelectedYear() != null){
				selectionSQLWhereClause = selectionSQLWhereClause + "AND year = " 
						+ selection.getSelectedYear() + " ";
			}
			if (!selection.getSelectedCountries().isEmpty()) {
				selectionSQLWhereClause = selectionSQLWhereClause
						+ "AND (country LIKE '%"
						+ concatStrings("%' OR country LIKE '%",
								selection.getSelectedCountries()) + "%') ";
			}

			if (!selection.getSelectedLanguages().isEmpty()) {
				selectionSQLWhereClause = selectionSQLWhereClause
						+ "AND (language LIKE '%"
						+ concatStrings("%' OR language LIKE '%",
								selection.getSelectedLanguages()) + "%') ";
			}

			if (!selection.getSelectedGenres().isEmpty()) {
				selectionSQLWhereClause = selectionSQLWhereClause
						+ "AND (genre LIKE '%"
						+ concatStrings("%' OR genre LIKE '%",
								selection.getSelectedGenres()) + "%') ";
			}

			if (!selection.getSelectedDurations().isEmpty()) {
				selectionSQLWhereClause = selectionSQLWhereClause + "AND (";
				for (Duration d : selection.getSelectedDurations()) {
					switch (d) {
					case Short:
						selectionSQLWhereClause = selectionSQLWhereClause
								+ "(duration > 0 AND duration < 30) OR ";
						break;
					case Medium:
						selectionSQLWhereClause = selectionSQLWhereClause
								+ "(duration >= 30 AND duration < 60) OR ";
						break;
					case Long:
						selectionSQLWhereClause = selectionSQLWhereClause
								+ "(duration >= 60 AND duration < 120) OR ";
						break;
					case ExtraLong:
						selectionSQLWhereClause = selectionSQLWhereClause
								+ "(duration >= 120) OR ";
						break;
					}
				}
				selectionSQLWhereClause = selectionSQLWhereClause.substring(0,
						selectionSQLWhereClause.length() - 4) + ") ";
			}
		}
		return selectionSQLWhereClause;
	}

	/**
	 * Concatenates all the elements of an arraylist together (similar to
	 * String.join() in Java 1.8) Also replaces apostroph with dual apostroph to
	 * prevent SQL Exceptions
	 * 
	 * @Author Christoph Weber
	 * @pre separator != null && aList != null
	 * @param String separator, ArrayList<String> aList 
	 * @post all Elements are concatenated or emptystring if aList is empty.
	 *       Apostroph's are escaped
	 */
	public String concatStrings(String separator, ArrayList<String> aList) {
		if (separator != null && aList != null) {
			String returnString = "";
			for (String s : aList) {
				/*
				 * The apostroph needs to be escaped, else the sql statement
				 * throws an exception because it thinks that the string is
				 * already finished (e.g. 'Children's Fantasy' -> String is
				 * 'Children'
				 */
				returnString = returnString + s.replace("'", "''") + separator;
			}
			if (aList.size() > 0) {
				returnString = returnString.substring(0, returnString.length()
						- separator.length());
			}

			return returnString;
		} else {
			return null;
		}
	}
}
